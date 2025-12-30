package in.levyashvin.ticketbooking;

import in.levyashvin.ticketbooking.modules.booking.dto.BookingRequest;
import in.levyashvin.ticketbooking.modules.movie.model.Movie;
import in.levyashvin.ticketbooking.modules.movie.model.Show;
import in.levyashvin.ticketbooking.modules.movie.repository.MovieRepository;
import in.levyashvin.ticketbooking.modules.movie.repository.ShowRepository;
import in.levyashvin.ticketbooking.modules.movie.repository.ShowSeatRepository;
import in.levyashvin.ticketbooking.modules.user.dto.RegisterRequest;
import in.levyashvin.ticketbooking.modules.user.service.AuthenticationService;
import in.levyashvin.ticketbooking.modules.venue.model.Screen;
import in.levyashvin.ticketbooking.modules.venue.model.Seat;
import in.levyashvin.ticketbooking.modules.venue.model.SeatType;
import in.levyashvin.ticketbooking.modules.venue.repository.ScreenRepository;
import in.levyashvin.ticketbooking.modules.venue.repository.SeatRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BookingFlowTest extends BaseIntegrationTest {

    @LocalServerPort
    private Integer port;

    @Autowired private AuthenticationService authService;
    @Autowired private MovieRepository movieRepository;
    @Autowired private ScreenRepository screenRepository;
    @Autowired private SeatRepository seatRepository;
    @Autowired private ShowRepository showRepository;
    @Autowired private ShowSeatRepository showSeatRepository;
    
    @MockitoBean
    private RabbitTemplate rabbitTemplate; // Mock RabbitMQ to avoid errors

    private String userToken;
    private Long showId;
    private Long showSeatId;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;

        // 1. Create User & Get Token
        var authResponse = authService.register(new RegisterRequest("Test User", "test@test.com", "pass123"));
        userToken = authResponse.getToken();

        // 2. Setup Data (Screen, Seat, Movie, Show)
        Screen screen = screenRepository.save(Screen.builder().name("Screen 1").build());
        
        Seat seat = Seat.builder().rowChar("A").seatNumber(1).type(SeatType.REGULAR).screen(screen).build();
        seatRepository.save(seat);

        Movie movie = movieRepository.save(Movie.builder().title("Test Movie").releaseDate(LocalDate.now()).build());

        // 3. Create Show
        Show show = Show.builder().movie(movie).screen(screen).startTime(LocalDateTime.now()).build();
        showRepository.save(show);
        
        // 4. Create Inventory manually
        var showSeat = in.levyashvin.ticketbooking.modules.movie.model.ShowSeat.builder()
                .show(show)
                .seat(seat)
                .status(in.levyashvin.ticketbooking.modules.movie.model.ShowSeatStatus.AVAILABLE)
                .price(100.0)
                .build();
        showSeatRepository.save(showSeat);
        
        showId = show.getId();
        showSeatId = showSeat.getId();
    }

    @Test
    void shouldBookTicketSuccessfully() {
        BookingRequest request = new BookingRequest();
        request.setShowId(showId);
        request.setShowSeatIds(Collections.singletonList(showSeatId));

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + userToken)
            .body(request)
        .when()
            .post("/api/v1/bookings")
        .then()
            .statusCode(200)
            .body("status", equalTo("CONFIRMED"))
            .body("totalAmount", equalTo(100.0f));
    }
}