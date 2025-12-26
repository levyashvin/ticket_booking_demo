package in.levyashvin.ticketbooking.modules.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.levyashvin.ticketbooking.modules.movie.model.Show;
import in.levyashvin.ticketbooking.modules.movie.model.ShowSeat;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    List<ShowSeat> findByShow(Show show);
}
