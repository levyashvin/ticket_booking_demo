package in.levyashvin.ticketbooking.modules.venue.controller;

import in.levyashvin.ticketbooking.modules.venue.dto.*;
import in.levyashvin.ticketbooking.modules.venue.model.*;
import in.levyashvin.ticketbooking.modules.venue.service.VenueService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/venues")
@RequiredArgsConstructor
public class VenueController {

    private final VenueService venueService;

    @Operation(summary = "Adds a new city to the DB")
    @PostMapping("/cities")
    public ResponseEntity<City> addCity(@RequestBody CreateCityRequest request) {
        return ResponseEntity.ok(venueService.addCity(request));
    }

    @Operation(summary = "Adds a new theater to the selected city")
    @PostMapping("/theaters")
    public ResponseEntity<Theater> addTheater(@RequestBody CreateTheaterRequest request) {
        return ResponseEntity.ok(venueService.addTheater(request));
    }

    @Operation(summary = "Adds a new screen to the selected theater")
    @PostMapping("/screens")
    public ResponseEntity<Screen> addScreen(@RequestBody CreateScreenRequest request) {
        return ResponseEntity.ok(venueService.addScreen(request));
    }
}