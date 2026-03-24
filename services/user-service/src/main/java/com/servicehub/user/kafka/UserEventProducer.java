package com.servicehub.user.kafka;

import com.servicehub.common.event.UserRegisteredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserEventProducer {

    private static final Logger LOGGER =  LoggerFactory.getLogger(UserEventProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public UserEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishUserRegisteredEvent(UserRegisteredEvent event) {

        LOGGER.info("Publishing UserRegisteredEvent for userId {} and email {} to Kafka",
                event.getUserId(), event.getEmail());
        kafkaTemplate.send("user-registered-topic", event);

    }
}