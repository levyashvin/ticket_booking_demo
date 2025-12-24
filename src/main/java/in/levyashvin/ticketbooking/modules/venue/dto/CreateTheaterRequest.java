package in.levyashvin.ticketbooking.modules.venue.dto;

import lombok.Data;

@Data
public class CreateTheaterRequest {
    private String name;
    private String address;
    private Long cityId;
}