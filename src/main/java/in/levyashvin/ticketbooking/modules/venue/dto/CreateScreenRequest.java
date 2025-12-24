package in.levyashvin.ticketbooking.modules.venue.dto;

import lombok.Data;

@Data
public class CreateScreenRequest {
    private String name;
    private Long theaterId;
    private int totalRows;
    private int totalColumns;
}