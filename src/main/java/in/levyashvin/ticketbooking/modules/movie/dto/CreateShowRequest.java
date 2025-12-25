package in.levyashvin.ticketbooking.modules.movie.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CreateShowRequest {
    private Long movieId;
    private Long screenId;
    private LocalDateTime startTime;
    // Split the price into tiers Premium and Regular
    private double premiumPrice;
    private double regularPrice;
}