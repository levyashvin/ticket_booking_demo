package in.levyashvin.ticketbooking.modules.movie.dto;

import in.levyashvin.ticketbooking.modules.movie.model.ShowSeatStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShowSeatResponse {
    private Long id;
    private String row;
    private int number;
    private double price;
    private ShowSeatStatus status;
    private String type;
}