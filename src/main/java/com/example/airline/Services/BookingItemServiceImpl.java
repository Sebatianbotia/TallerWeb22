package com.example.airline.Services;

import com.example.airline.DTO.BookingItemDTO.*;
import com.example.airline.Services.Mappers.BookingItemMapper;
import com.example.airline.Services.Mappers.BookingMapper;
import com.example.airline.entities.Booking;
import com.example.airline.entities.BookingItem;
import com.example.airline.entities.Flight;
import com.example.airline.repositories.BookingItemsRepository;
import com.example.airline.repositories.BookingRepository;
import com.example.airline.repositories.FlightRepository;
import jakarta.persistence.EntityNotFoundException;

public class BookingItemServiceImpl implements BookingItemService {
    BookingRepository bookingRepository;
    FlightRepository flightRepository;
    BookingItemsRepository bookingItemsRepository;
    @Override
    public bookingItemReponse create( bookingItemCreateRequest request) {
        Booking booking = findBooking(request.bookingId());
        Flight flight = findFlight(request.fligthId());
        BookingItem bookingItem = BookingItemMapper.toEntity(booking, flight, request);
        booking.addItem(bookingItem);
        return BookingItemMapper.toDTO(bookingItem);
    }

    @Override
    public bookingItemReponse update(bookingItemUpdateRequest request) {
        BookingItem bookingItem = findBookingItem(request.bookingItemsId());
        BookingItemMapper.updateBookingItem(bookingItem, request);
        return BookingItemMapper.toDTO(bookingItem);
    }

    @Override
    public void delete(long id) {
        bookingRepository.deleteById(id);
    }

    public Booking findBooking(Long id) {
        return bookingRepository.findBookingById(id).orElseThrow(()-> new EntityNotFoundException("Booking not found"));
    }
    public Flight findFlight(Long flightId) {
        return flightRepository.findById(flightId).orElseThrow(()-> new EntityNotFoundException("Flight not found"));
    }
    public BookingItem findBookingItem(Long id) {
        return bookingItemsRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Booking Item not found"));
    }
}
