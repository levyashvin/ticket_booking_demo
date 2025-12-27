package in.levyashvin.ticketbooking.modules.booking.dto;

import java.util.List;

import lombok.Data;

@Data
public class BookingRequest {
    
    private Long showId;
    private List<Long> showSeatIds;
}
