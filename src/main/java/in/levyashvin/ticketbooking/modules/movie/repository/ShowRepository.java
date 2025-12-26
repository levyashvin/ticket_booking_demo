package in.levyashvin.ticketbooking.modules.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.levyashvin.ticketbooking.modules.movie.model.Movie;
import in.levyashvin.ticketbooking.modules.movie.model.Show;
import java.util.List;


public interface ShowRepository extends JpaRepository<Show, Long> {
    List<Show> findByMovie(Movie movie);
}
