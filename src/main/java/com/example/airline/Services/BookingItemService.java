package com.example.airline.Services;

import com.example.airline.DTO.BookingItemDTO.*;

public interface BookingItemService {

    bookingItemReponse create(bookingItemCreateRequest bookingItemCreateRequest);
    public bookingItemReponse update( bookingItemUpdateRequest bookingItemUpdateRequest);
    public void delete(long id);
}
