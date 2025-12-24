package in.levyashvin.ticketbooking.modules.venue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.levyashvin.ticketbooking.modules.venue.model.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long>{

}
