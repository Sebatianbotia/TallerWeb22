package com.example.airline.Services.Mappers;

import com.example.airline.DTO.BookingItemDTO.*;
import com.example.airline.entities.Booking;
import com.example.airline.entities.BookingItem;
import com.example.airline.entities.Flight;
import com.example.airline.repositories.BookingItemsRepository;
import com.example.airline.repositories.BookingRepository;
import com.example.airline.repositories.FlightRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingItemMapper {

    @Autowired
    private BookingRepository bookingRepository;
    private BookingItemsRepository bookingItemsRepository;
    private static FlightRepository flightRepository;

    public static BookingItem toEntity(Booking booking, Flight flight, bookingItemCreateRequest bookingItemCreateRequest) {
        return BookingItem.builder().price(bookingItemCreateRequest.price()).segmentOrder(bookingItemCreateRequest.segmentOrder())
                .cabin(bookingItemCreateRequest.cabin()).booking(booking).flight(flight).build();

    }

    public static void updateBookingItem(BookingItem foundBookingItem, bookingItemUpdateRequest bookingItemUpdateRequest) {
        if (bookingItemUpdateRequest == null) {return;}

        if (bookingItemUpdateRequest.segmentOrder() != null) {
            foundBookingItem.setSegmentOrder(bookingItemUpdateRequest.segmentOrder());
        }
        if (bookingItemUpdateRequest.cabin() != null) {
            foundBookingItem.setCabin(bookingItemUpdateRequest.cabin());
        }
        if (bookingItemUpdateRequest.price() != null) {
            foundBookingItem.setPrice(bookingItemUpdateRequest.price());
        }
        if (bookingItemUpdateRequest.flightId() != null) {
            Flight foundFlight = flightRepository.findById(bookingItemUpdateRequest.flightId()).orElseThrow(() -> new EntityNotFoundException(
                    "Vuelo con id: " + bookingItemUpdateRequest.flightId() + " no encontrado"
            ));
            foundBookingItem.setFlight(foundFlight);
        }
    }

    public static bookingItemReponse toDTO(BookingItem foundBookingItem) {
        return new bookingItemReponse(foundBookingItem.getId(), foundBookingItem.getCabin(), foundBookingItem.getBooking().getId(), FlightMapper.toDTO(foundBookingItem.getFlight()));
    }
}
