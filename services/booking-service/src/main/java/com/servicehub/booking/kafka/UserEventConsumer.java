package com.servicehub.booking.kafka;

import com.servicehub.booking.event.UserRegisteredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserEventConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserEventConsumer.class);

    @KafkaListener(topics = "user-registered-topic", groupId = "booking-group")
    public void consume(UserRegisteredEvent event) {

        LOGGER.info("Received event in Booking Service: {}", event.getEmail());

    }
}