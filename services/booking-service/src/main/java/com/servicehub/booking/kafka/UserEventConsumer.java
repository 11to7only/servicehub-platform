package com.servicehub.booking.kafka;

import com.servicehub.booking.constant.BookingStatus;
import com.servicehub.booking.model.Booking;
import com.servicehub.booking.repository.BookingRepository;
import com.servicehub.common.event.UserRegisteredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserEventConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserEventConsumer.class);
    private static BookingRepository bookingRepository;

    public UserEventConsumer(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @KafkaListener(topics = "user-registered-topic", groupId = "booking-group")
    public void consume(UserRegisteredEvent event) {

        LOGGER.info("Processing event for user: {}", event.getEmail());
        Booking booking = new Booking();
        booking.setUserId(event.getUserId());
        booking.setStatus(BookingStatus.CREATED);

        bookingRepository.save(booking);

        LOGGER.info("Booking record created for user: {}", event.getUserId());

        //throw new RuntimeException("Test failure"); //For testing of DLQ (Dead-Letter-Queue)

    }
}