package in.levyashvin.ticketbooking.modules.booking.dto;

import in.levyashvin.ticketbooking.modules.booking.model.BookingStatus;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class BookingResponse {
    private Long bookingId;
    private String movieTitle;
    private String theaterName;
    private String screenName;
    private LocalDateTime showTime;
    private double totalAmount;
    private BookingStatus status;
}