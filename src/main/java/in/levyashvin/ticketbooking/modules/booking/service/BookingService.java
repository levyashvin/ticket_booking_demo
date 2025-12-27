package in.levyashvin.ticketbooking.modules.booking.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import in.levyashvin.ticketbooking.modules.booking.dto.BookingRequest;
import in.levyashvin.ticketbooking.modules.booking.dto.BookingResponse;
import in.levyashvin.ticketbooking.modules.booking.model.Booking;
import in.levyashvin.ticketbooking.modules.booking.model.BookingStatus;
import in.levyashvin.ticketbooking.modules.booking.repository.BookingRepository;
import in.levyashvin.ticketbooking.modules.movie.repository.ShowSeatRepository;
import in.levyashvin.ticketbooking.modules.movie.model.Show;
import in.levyashvin.ticketbooking.modules.movie.model.ShowSeat;
import in.levyashvin.ticketbooking.modules.movie.model.ShowSeatStatus;
import in.levyashvin.ticketbooking.modules.movie.repository.ShowRepository;
import in.levyashvin.ticketbooking.modules.user.model.User;
import in.levyashvin.ticketbooking.modules.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final UserRepository userRepository;

    @Transactional
    public BookingResponse bookTickets(BookingRequest request, String userEmail){
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Show show = showRepository.findById(request.getShowId())
                .orElseThrow(() -> new RuntimeException("Show not found"));

        // Fetches with lock
        List<ShowSeat> selectedSeats = showSeatRepository.findAllByIdWithLock(request.getShowSeatIds());

        // Seat validation
        if (selectedSeats.size() != request.getShowSeatIds().size()) {
            throw new RuntimeException("Some seats not found");
        }

        for (ShowSeat seat : selectedSeats) {
            if (seat.getStatus() != ShowSeatStatus.AVAILABLE) {
                throw new RuntimeException("Seat " + seat.getSeat().getRowChar() + seat.getSeat().getSeatNumber() + " is already booked!");
            }
        }

        double totalAmount = selectedSeats.stream().mapToDouble(ShowSeat::getPrice).sum();

        // Marking seats as booked
        for (ShowSeat seat : selectedSeats) {
            seat.setStatus(ShowSeatStatus.BOOKED);
            // seat.setBooking(booking); // If you added a relationship in ShowSeat
        }
        showSeatRepository.saveAll(selectedSeats);

        Booking booking = Booking.builder()
                .user(user)
                .show(show)
                .bookingTime(LocalDateTime.now())
                .status(BookingStatus.CONFIRMED)
                .totalAmount(totalAmount)
                .seatIds(request.getShowSeatIds().toString())
                .build();

        Booking savedBooking = bookingRepository.save(booking);

        return BookingResponse.builder()
                .bookingId(savedBooking.getId())
                .movieTitle(show.getMovie().getTitle())
                .theaterName(show.getScreen().getTheater().getName())
                .screenName(show.getScreen().getName())
                .showTime(show.getStartTime())
                .totalAmount(savedBooking.getTotalAmount())
                .status(savedBooking.getStatus())
                .build();
    }
}
