package com.example.airline.Mappers;

import com.example.airline.DTO.BookingDTO;
import com.example.airline.entities.Booking;
import com.example.airline.entities.Passenger;
import com.example.airline.repositories.BookingRepository;
import com.example.airline.repositories.PassengerRepository;
import jakarta.persistence.EntityNotFoundException;

public class BookingMapper {
    PassengerRepository passengerRepository;
    Passenger passenger;
    BookingRepository bookingRepository;
    public Booking toEntity(BookingDTO.bookingCreateRequest request) {
        Passenger foundPassenger = passengerRepository.findById(request.passengerId()).orElseThrow(() -> new EntityNotFoundException("Passenger not found"));
        Booking booking = Booking.builder().passenger(foundPassenger).build():
        return booking;
    }

    public BookingDTO.bookingResponseBasic toDTO(long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        return new BookingDTO.bookingResponseBasic(
                booking.getId(),
                booking.getCreatedAt(),
                booking.)
    }
}
