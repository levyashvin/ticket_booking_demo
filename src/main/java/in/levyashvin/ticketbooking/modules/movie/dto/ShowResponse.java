package in.levyashvin.ticketbooking.modules.movie.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class ShowResponse {
    private Long showId;
    private LocalDateTime startTime;
    private String movieTitle;
    private String screenName;
    private String theaterName;
    private Long theaterId;
}