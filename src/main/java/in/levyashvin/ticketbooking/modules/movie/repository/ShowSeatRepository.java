package in.levyashvin.ticketbooking.modules.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import in.levyashvin.ticketbooking.modules.movie.model.Show;
import in.levyashvin.ticketbooking.modules.movie.model.ShowSeat;
import jakarta.persistence.LockModeType;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    List<ShowSeat> findByShow(Show show);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM ShowSeat s WHERE s.id IN :seatIds")
    List<ShowSeat> findAllByIdWithLock(@Param("seatIds") List<Long> seatIds);
}
