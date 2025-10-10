package com.example.airline.Services;

import com.example.airline.DTO.BookingItemDTO.*;
import com.example.airline.entities.BookingItem;

import java.util.List;

public interface BookingItemService {

    bookingItemReponse create(bookingItemCreateRequest bookingItemCreateRequest);
    bookingItemReponse update(BookingItem bookingItem,bookingItemUpdateRequest bookingItemUpdateRequest);
    void delete(Long id);
    BookingItem findBookingItem(Long id);
    List<bookingItemReponse> findAll();
}
