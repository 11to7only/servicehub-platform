package com.servicehub.user.service.impl;

import com.servicehub.user.dto.UserRequestDTO;
import com.servicehub.user.dto.UserResponseDTO;
import com.servicehub.common.event.UserRegisteredEvent;
import com.servicehub.user.kafka.UserEventProducer;
import com.servicehub.user.model.User;
import com.servicehub.user.repository.UserRepository;
import com.servicehub.user.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserEventProducer producer;

    public UserServiceImpl(UserRepository userRepository, UserEventProducer producer) {
        this.userRepository = userRepository;
        this.producer = producer;
    }

    @Override
    public UserResponseDTO registerUser(UserRequestDTO request) {

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        user = userRepository.save(user);

        // Send Even to kafka
        UserRegisteredEvent event =
                new UserRegisteredEvent(user.getId(), user.getEmail());

        //producer.publishUserRegisteredEvent(event); stopping for few days due to low space in laptop

        // Prepare response
        UserResponseDTO response = new UserResponseDTO();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());

        return response;
    }

    @CircuitBreaker(name = "userServiceCB", fallbackMethod = "userFallback")
    @Retry(name = "userServiceRetry")
    @TimeLimiter(name = "userServiceTimeout")
    @Override
    public CompletableFuture<UserResponseDTO> getUserById(Long id) {

        return CompletableFuture.supplyAsync(() -> {
            User user = userRepository.findById(id).orElseThrow();

            UserResponseDTO response = new UserResponseDTO();
            response.setId(user.getId());
            response.setName(user.getName());
            response.setEmail(user.getEmail());
            return response;
        });
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // 🔁 Fallback method
    public CompletableFuture<UserResponseDTO> userFallback(Exception ex) {
        UserResponseDTO response = new UserResponseDTO();
        response.setError("User service is down. Order saved for later processing.");
        return CompletableFuture.completedFuture(response);
    }
}