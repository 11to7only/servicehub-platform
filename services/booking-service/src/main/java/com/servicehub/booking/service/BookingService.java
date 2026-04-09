package com.servicehub.booking.service;

import com.servicehub.booking.dto.BookingResponseDTO;

public interface BookingService {
    BookingResponseDTO getBookingById(Long id);
}
