package in.levyashvin.ticketbooking.modules.movie.controller;

import in.levyashvin.ticketbooking.modules.movie.dto.CreateMovieRequest;
import in.levyashvin.ticketbooking.modules.movie.dto.CreateShowRequest;
import in.levyashvin.ticketbooking.modules.movie.dto.ShowResponse;
import in.levyashvin.ticketbooking.modules.movie.dto.ShowSeatResponse;
import in.levyashvin.ticketbooking.modules.movie.model.Movie;
import in.levyashvin.ticketbooking.modules.movie.model.Show;
import in.levyashvin.ticketbooking.modules.movie.service.MovieService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody CreateMovieRequest request) {
        return ResponseEntity.ok(movieService.addMovie(request));
    }

    @PostMapping("/shows")
    public ResponseEntity<Show> createShow(@RequestBody CreateShowRequest request) {
        return ResponseEntity.ok(movieService.createShow(request));
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/{movieId}/shows")
    public ResponseEntity<List<ShowResponse>> getShows(@PathVariable Long movieId) {
        return ResponseEntity.ok(movieService.getShowsByMovie(movieId));
    }

    @GetMapping("/shows/{showId}/seats")
    public ResponseEntity<List<ShowSeatResponse>> getSeats(@PathVariable Long showId) {
        return ResponseEntity.ok(movieService.getShowSeats(showId));
    }
}