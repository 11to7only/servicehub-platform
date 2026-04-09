package com.servicehub.booking.controller;

import com.servicehub.booking.dto.BookingResponseDTO;
import com.servicehub.booking.service.BookingService;
import com.servicehub.user.dto.UserResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    Logger LOGGER = LoggerFactory.getLogger(BookingController.class);

    @GetMapping("/health")
    public String healthCheck() {
        return "Booking Service Running";
    }

    @GetMapping("/{id}")
    public BookingResponseDTO getUserById(@PathVariable Long id) {
        LOGGER.info("Fetching user with ID {}", id);
        return bookingService.getBookingById(id);
    }
}
