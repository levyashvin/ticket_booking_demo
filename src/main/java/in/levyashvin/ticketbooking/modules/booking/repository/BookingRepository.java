package in.levyashvin.ticketbooking.modules.booking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.levyashvin.ticketbooking.modules.booking.model.Booking;
import in.levyashvin.ticketbooking.modules.user.model.User;

public interface BookingRepository extends JpaRepository<Booking, Long>{
    List<Booking> findByUserOrderByBookingTimeDesc(User user);
}
