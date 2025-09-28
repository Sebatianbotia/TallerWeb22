package com.example.airline.Services.Mappers;

import com.example.airline.DTO.BookingItemDTO;
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
    private FlightRepository flightRepository;

    public BookingItem toEntity(BookingItemDTO.bookingItemCreateRequest bookingItemCreateRequest) {
        if (bookingItemCreateRequest == null) {return null;}
        Booking foundBooking = bookingRepository.findById(bookingItemCreateRequest.bookingId()).orElseThrow(() -> new EntityNotFoundException(
                "Booking con id: " + bookingItemCreateRequest.bookingId() + " no encontrado"
        ));
        Flight foundFlight = flightRepository.findById(bookingItemCreateRequest.fligthId()).orElseThrow(() -> new EntityNotFoundException(
                "Vuelo con id: " + bookingItemCreateRequest.fligthId() + " no encontrado"
        ));
        BookingItem bookingItem = BookingItem.builder().price(bookingItemCreateRequest.price()).segmentOrder(bookingItemCreateRequest.segmentOrder())
                .cabin(bookingItemCreateRequest.cabin()).booking(foundBooking).flight(foundFlight).build();
        return bookingItem;
    }

    public BookingItem updateBookingItem(BookingItemDTO.bookingItemUpdateRequest bookingItemUpdateRequest) {
        if (bookingItemUpdateRequest == null) {return null;}
        BookingItem foundBookingItem = bookingItemsRepository.findById(bookingItemUpdateRequest.bookingId()).orElseThrow(() -> new EntityNotFoundException(
                "BookingItem con id: " + bookingItemUpdateRequest.bookingId() + " no encontrado"
        ));
        if (bookingItemUpdateRequest.segmentOrder() != null) {
            foundBookingItem.setSegmentOrder(bookingItemUpdateRequest.segmentOrder());
        }
        if (bookingItemUpdateRequest.cabin() != null) {
            foundBookingItem.setCabin(bookingItemUpdateRequest.cabin());
        }
        if (bookingItemUpdateRequest.bookingId() != null) {
            Booking foundBooking = bookingRepository.findById(bookingItemUpdateRequest.bookingId()).orElseThrow(() -> new EntityNotFoundException(
                    "Booking con id: " + bookingItemUpdateRequest.bookingId() + " no encontrado"
            ));
            foundBookingItem.setBooking(foundBooking);
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
        return foundBookingItem;
    }

    public BookingItemDTO.bookingItemReponse toDTO(Long bookingItemId) {
        BookingItem foundBookingItem = bookingItemsRepository.findById(bookingItemId).orElseThrow(() -> new EntityNotFoundException(
                "BookingItem con id: " + bookingItemId + " no encontrado"
        ));
        return new BookingItemDTO.bookingItemReponse(foundBookingItem.getId(), foundBookingItem.getCabin(), foundBookingItem.getBooking().getId(), foundBookingItem.getFlight().getId());
    }
}
