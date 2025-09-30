package com.example.airline.Services.Mappers;

import com.example.airline.DTO.BookingDTO;
import com.example.airline.DTO.BookingItemDTO;
import com.example.airline.entities.Booking;
import com.example.airline.entities.BookingItem;


import java.util.List;

public class BookingMapper {


    public static BookingDTO.bookingResponse toDTO(Booking booking) {
        var bookingItems =  booking.getItems()==null? List.<BookingItemDTO.bookingItemReponse>of():
                booking.getItems().stream().map(BookingMapper::bookingItemResponse).toList();
        return new BookingDTO.bookingResponse(
                booking.getId(),
                booking.getCreatedAt(),
                PassengerMapper.toDTO(booking.getPassenger()),
                bookingItems);
    }
    private static BookingItemDTO.bookingItemReponse bookingItemResponse(BookingItem i) {
        return new BookingItemDTO.bookingItemReponse(
                i.getId(),
                i.getCabin(),
                i.getBooking().getId(),
                FlightMapper.toDTO(i.getFlight())
        );
    }
}
