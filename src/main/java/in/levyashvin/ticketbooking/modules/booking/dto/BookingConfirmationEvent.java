package in.levyashvin.ticketbooking.modules.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingConfirmationEvent {
    private String userEmail;
    private String movieTitle;
    private Double amount;
    private Long bookingId;
}