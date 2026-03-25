package com.servicehub.booking.config;

import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@EnableKafka
public class KafkaConfig {

    Logger LOGGER = LoggerFactory.getLogger(KafkaConfig.class);

    private static final long INTERVAL = 2000L;
    private static final long MAX_ATTEMPT = 3;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
            ConsumerFactory<String, Object> consumerFactory,
            KafkaTemplate<String, Object> kafkaTemplate) {

        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);

        // 🔥 DLQ Recoverer (sends failed messages to .DLT topic)
        DeadLetterPublishingRecoverer recoverer =
                new DeadLetterPublishingRecoverer(kafkaTemplate,
                        (record, ex) -> new TopicPartition(
                                record.topic() + ".DLT", record.partition()));
        // 🔁 Retry config: 3 retries, 2 sec delay
        DefaultErrorHandler errorHandler =
                new DefaultErrorHandler(recoverer, new FixedBackOff(INTERVAL, MAX_ATTEMPT));
        errorHandler.setRetryListeners((record, ex, attempt) ->{
            LOGGER.info("Retrying record: {}, attempt: {}", record.value(), attempt);
                });

        // (Optional but important)
        errorHandler.addNotRetryableExceptions(IllegalArgumentException.class);

        // 🔥 VERY IMPORTANT FIX TO AVOID INFINITE RETRY
        errorHandler.setCommitRecovered(true);

        factory.setCommonErrorHandler(errorHandler);

        return factory;
    }
}
