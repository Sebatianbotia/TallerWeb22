package com.example.airline.services;

import com.example.airline.DTO.BookingItemDTO.*;
import com.example.airline.entities.BookingItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingItemService {

    bookingItemReponse create(bookingItemCreateRequest bookingItemCreateRequest);
    bookingItemReponse update(Long id ,bookingItemUpdateRequest bookingItemUpdateRequest);
    void delete(Long id);
    BookingItem findBookingItem(Long id);
    Page<bookingItemReponse> list(Pageable pageable);

    bookingItemReponse get(Long l);
}
