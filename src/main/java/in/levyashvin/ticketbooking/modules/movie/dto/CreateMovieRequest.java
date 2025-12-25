package in.levyashvin.ticketbooking.modules.movie.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CreateMovieRequest {
    private String title;
    private String genre;
    private int durationMinutes;
    private String language;
    private LocalDate releaseDate;
}