package in.levyashvin.ticketbooking.modules.movie.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CreateShowRequest {
    private Long movieId;
    private Long screenId;
    private LocalDateTime startTime;
    private double price; // Base price for all seats in this show
}