package in.levyashvin.ticketbooking.modules.venue.service;

import in.levyashvin.ticketbooking.modules.venue.dto.*;
import in.levyashvin.ticketbooking.modules.venue.model.*;
import in.levyashvin.ticketbooking.modules.venue.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VenueService {

    private final CityRepository cityRepository;
    private final TheaterRepository theaterRepository;
    private final ScreenRepository screenRepository;
    private final SeatRepository seatRepository;

    public City addCity(CreateCityRequest request) {
        City city = City.builder().name(request.getName()).build();
        return cityRepository.save(city);
    }

    public Theater addTheater(CreateTheaterRequest request) {
        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new RuntimeException("City not found"));

        Theater theater = Theater.builder()
                .name(request.getName())
                .address(request.getAddress())
                .city(city)
                .build();
        
        return theaterRepository.save(theater);
    }

    public Screen addScreen(CreateScreenRequest request) {
        Theater theater = theaterRepository.findById(request.getTheaterId())
                .orElseThrow(() -> new RuntimeException("Theater not found"));

        Screen screen = Screen.builder()
                .name(request.getName())
                .theater(theater)
                .build();
        Screen savedScreen = screenRepository.save(screen);

        // generating rows using a grid format
        List<Seat> seats = new ArrayList<>();
        for (int row = 1; row <= request.getTotalRows(); row++) {
            char rowChar = (char) ('A' + row - 1); // 1 -> A, 2 -> B
            
            for (int col = 1; col <= request.getTotalColumns(); col++) {
                Seat seat = Seat.builder()
                        .rowChar(String.valueOf(rowChar))
                        .seatNumber(col)
                        .type((row <= 4) ? SeatType.REGULAR : SeatType.PREMIUM) // first 4 rows are regular
                        .screen(savedScreen)
                        .build();
                seats.add(seat);
            }
        }
        
        // batch save all seats
        seatRepository.saveAll(seats);
        
        return savedScreen;
    }
}