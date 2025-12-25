package in.levyashvin.ticketbooking.modules.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.levyashvin.ticketbooking.modules.movie.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
