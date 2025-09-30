package com.example.airline.Services;

import com.example.airline.DTO.BookingDTO;

public interface BookingService {
    public BookingDTO.bookingResponse create(BookingDTO.bookingCreateRequest request);
    public BookingDTO.bookingResponse get(long id);
    public void delete(long id);
}
