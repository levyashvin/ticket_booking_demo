package in.levyashvin.ticketbooking.modules.movie.service;

import in.levyashvin.ticketbooking.modules.movie.dto.CreateMovieRequest;
import in.levyashvin.ticketbooking.modules.movie.dto.CreateShowRequest;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final ScreenRepository screenRepository;

    public Movie addMovie(CreateMovieRequest request) {
        Movie movie = Movie.builder()
                .title(request.getTitle())
                .genre(request.getGenre())
                .durationMinutes(request.getDurationMinutes())
                .language(request.getLanguage())
                .releasDate(request.getReleaseDate())
                .build();
        return movieRepository.save(movie);
    }

    @Transactional // Ensures all seats are created or none at all atomic operation
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

        // We iterate over the PHYSICAL seats of the screen
        List<ShowSeat> showSeats = new ArrayList<>();
        
        for (Seat seat : screen.getSeats()) {
            ShowSeat showSeat = ShowSeat.builder()
                    .show(savedShow)
                    .seat(seat) // Link to physical seat
                    .status(ShowSeatStatus.AVAILABLE)
                    .price(request.getPrice()) // Set price
                    .build();
            showSeats.add(showSeat);
        }

        showSeatRepository.saveAll(showSeats);

        return savedShow;
    }
}