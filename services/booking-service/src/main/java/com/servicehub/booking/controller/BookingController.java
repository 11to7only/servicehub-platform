package com.servicehub.booking.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @GetMapping("/health")
    public String healthCheck() {
        return "Booking Service Running";
    }
}
