package com.example.airline.Services;

import com.example.airline.DTO.BookingDTO.*;
import com.example.airline.DTO.BookingItemDTO;
import com.example.airline.DTO.BookingItemDTO.*;
import com.example.airline.Services.Mappers.BookingMapper;
import com.example.airline.entities.Booking;

public interface BookingItemService {

    bookingItemReponse create(bookingItemCreateRequest bookingItemCreateRequest);
    public bookingItemReponse update( bookingItemUpdateRequest bookingItemUpdateRequest);
    public void delete(long id);
}
