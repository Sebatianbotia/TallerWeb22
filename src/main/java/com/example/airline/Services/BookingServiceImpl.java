package com.example.airline.Services;

import com.example.airline.DTO.BookingDTO;
import com.example.airline.DTO.PassengerDTO;
import com.example.airline.Services.Mappers.BookingMapper;
import com.example.airline.Services.Mappers.PassengerMapper;
import com.example.airline.entities.Booking;
import com.example.airline.entities.Passenger;
import com.example.airline.repositories.BookingRepository;
import com.example.airline.repositories.PassengerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

public class BookingServiceImpl implements BookingService{
    PassengerRepository passengerRepository;
    BookingRepository bookingRepository;
    @Override
    public BookingDTO.bookingResponse create(BookingDTO.bookingCreateRequest request) {
        Passenger p = findPassenger(request.passengerId());
        Booking b = Booking.builder().passenger(p).createdAt(OffsetDateTime.now()).build();
        return BookingMapper.toDTO(b);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingDTO.bookingResponse get(long id) {
        return bookingRepository.findBookingById(id).map(BookingMapper::toDTO).orElseThrow(()-> new EntityNotFoundException("Booking not found"));
    }

    @Override
    public void delete(long id) {
        bookingRepository.deleteById(id);
    }
    public Passenger findPassenger(long id) {
        return passengerRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Passenger not found"));
    }
}
