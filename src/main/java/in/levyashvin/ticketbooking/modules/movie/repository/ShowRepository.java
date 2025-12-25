package in.levyashvin.ticketbooking.modules.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.levyashvin.ticketbooking.modules.movie.model.Show;

public interface ShowRepository extends JpaRepository<Show, Long> {

}
