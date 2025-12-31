package in.levyashvin.ticketbooking.modules.booking.controller;

import in.levyashvin.ticketbooking.modules.booking.dto.BookingRequest;
import in.levyashvin.ticketbooking.modules.booking.dto.BookingResponse;
import in.levyashvin.ticketbooking.modules.booking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @Operation(summary = "Adds new booking for the user and returns ok if valid")
    @PostMapping
    public ResponseEntity<BookingResponse> bookTickets(
            @RequestBody BookingRequest request,
            @AuthenticationPrincipal UserDetails userDetails // Extract user from JWT
    ) {
        return ResponseEntity.ok(bookingService.bookTickets(request, userDetails.getUsername()));
    }

    @Operation(summary = "Returns list of bookings made for the current user")
    @GetMapping
    public ResponseEntity<List<BookingResponse>> getMyBookings(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(bookingService.getUserBookings(userDetails.getUsername()));
    }
}