package com.servicehub.booking.service.impl;

import com.servicehub.booking.dto.BookingResponseDTO;
import com.servicehub.booking.model.Booking;
import com.servicehub.booking.repository.BookingRepository;
import com.servicehub.booking.service.BookingService;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {

    private BookingRepository bookingRepository;
    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }
    @Override
    public BookingResponseDTO getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow();
        BookingResponseDTO response = new BookingResponseDTO();
        response.setId(booking.getId());
        response.setUserId(booking.getUserId());
        response.setStatus(booking.getStatus());
        return response;
    }
}
