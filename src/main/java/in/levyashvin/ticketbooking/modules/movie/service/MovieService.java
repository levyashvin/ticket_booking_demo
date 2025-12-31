package in.levyashvin.ticketbooking.modules.movie.service;

import in.levyashvin.ticketbooking.modules.movie.dto.CreateMovieRequest;
import in.levyashvin.ticketbooking.modules.movie.dto.CreateShowRequest;
import in.levyashvin.ticketbooking.modules.movie.dto.ShowResponse;
import in.levyashvin.ticketbooking.modules.movie.dto.ShowSeatResponse;
import in.levyashvin.ticketbooking.modules.movie.model.Movie;
import in.levyashvin.ticketbooking.modules.movie.model.Show;
import in.levyashvin.ticketbooking.modules.movie.model.ShowSeat;
import in.levyashvin.ticketbooking.modules.movie.model.ShowSeatStatus;
import in.levyashvin.ticketbooking.modules.movie.repository.MovieRepository;
import in.levyashvin.ticketbooking.modules.movie.repository.ShowRepository;
import in.levyashvin.ticketbooking.modules.movie.repository.ShowSeatRepository;
import in.levyashvin.ticketbooking.modules.venue.model.Screen;
import in.levyashvin.ticketbooking.modules.venue.model.Seat;
import in.levyashvin.ticketbooking.modules.venue.repository.ScreenRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final ScreenRepository screenRepository;

    @CacheEvict(value = "movies", allEntries = true)
    public Movie addMovie(CreateMovieRequest request) {
        Movie movie = Movie.builder()
                .title(request.getTitle())
                .genre(request.getGenre())
                .durationMinutes(request.getDurationMinutes())
                .language(request.getLanguage())
                .releaseDate(request.getReleaseDate())
                .build();
        return movieRepository.save(movie);
    }

    @Transactional // Ensures all seats are created or none at all atomic operation
    @CacheEvict(value = "shows", key = "request.movieId")
    public Show createShow(CreateShowRequest request) {
        
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        
        Screen screen = screenRepository.findById(request.getScreenId())
                .orElseThrow(() -> new RuntimeException("Screen not found"));

        // Creating show
        Show show = Show.builder()
                .movie(movie)
                .screen(screen)
                .startTime(request.getStartTime())
                .build();
        Show savedShow = showRepository.save(show);

        List<ShowSeat> showSeats = new ArrayList<>();
        
        for(Seat seat : screen.getSeats()) {
            double ticketPrice;
            
            // Determine price based on seat type
            if(seat.getType().name().equals("PREMIUM"))
                ticketPrice = request.getPremiumPrice();
            else
                ticketPrice = request.getRegularPrice();

            ShowSeat showSeat = ShowSeat.builder()
                    .show(savedShow)
                    .seat(seat) // Link to physical seat
                    .status(ShowSeatStatus.AVAILABLE)
                    .price(ticketPrice)
                    .build();
            showSeats.add(showSeat);
        }
        
        showSeatRepository.saveAll(showSeats);

        return savedShow;
    }

    @Cacheable(value = "movies")
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Cacheable(value = "shows", key = "#movieId")
    public List<ShowResponse> getShowsByMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                        .orElseThrow(() -> new RuntimeException("Movie not found"));
        List<Show> shows = showRepository.findByMovie(movie);

        return shows.stream()
                .map(show -> ShowResponse.builder()
                    .showId(show.getId())
                    .startTime(show.getStartTime())
                    .movieTitle(show.getMovie().getTitle())
                    .screenName(show.getScreen().getName())
                    .theaterName(show.getScreen().getTheater().getName())
                    .theaterId(show.getScreen().getTheater().getId())
                    .build())
                .collect(Collectors.toList());
                
    }

    public List<ShowSeatResponse> getShowSeats(Long showId) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));

        return showSeatRepository.findByShow(show).stream()
                .map(showSeat -> ShowSeatResponse.builder()
                        .id(showSeat.getId())
                        .row(showSeat.getSeat().getRowChar())
                        .number(showSeat.getSeat().getSeatNumber())
                        .price(showSeat.getPrice())
                        .status(showSeat.getStatus())
                        .type(showSeat.getSeat().getType().name())
                        .build())
                .collect(Collectors.toList());
    }
}