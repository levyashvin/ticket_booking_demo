package in.levyashvin.ticketbooking.modules.notification;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import in.levyashvin.ticketbooking.config.RabbitMQConfig;
import in.levyashvin.ticketbooking.modules.booking.dto.BookingConfirmationEvent;

@Service
public class NotificationService {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void sendEmailConfirmation(BookingConfirmationEvent event) {
        System.out.println("======================================");
        System.out.println("Sending email to: " + event.getUserEmail());
        System.out.println("Subject: Booking Confirmed for " + event.getMovieTitle());
        System.out.println("Booking ID: " + event.getBookingId());
        System.out.println("======================================");
    }
}
