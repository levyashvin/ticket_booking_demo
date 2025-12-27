package in.levyashvin.ticketbooking.modules.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.levyashvin.ticketbooking.modules.booking.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long>{

}
