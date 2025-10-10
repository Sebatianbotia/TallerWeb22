package com.example.airline.Services;

import com.example.airline.DTO.BookingDTO;

public interface BookingService {
    public BookingDTO.bookingResponse create(BookingDTO.bookingCreateRequest request);
    public BookingDTO.bookingResponse get(Long id);
    public void delete(Long id);
}
