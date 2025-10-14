package com.example.airline.services;

import com.example.airline.DTO.BookingDTO.*;
import com.example.airline.DTO.PassengerDTO;
import com.example.airline.Mappers.BookingMapper;
import com.example.airline.entities.Booking;
import com.example.airline.entities.Passenger;
import com.example.airline.repositories.BookingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceImplTest {
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private BookingMapper bookingMapper;
    @InjectMocks
    private BookingServiceImpl bookingServiceImpl;
    @Mock
    private PassengerServiceimpl passengerService;


    private final Long bookingId = 1L;
    private final Long passengerId = 2L;

    @Test
    void shouldCreateAndReturnBooking() {
        Passenger passenger = Passenger.builder().
                id(passengerId).
                fullName("Josesito crack7770").
                email("mamamaa@gmail.com").
                bookings(new ArrayList<>()).
                profile(null).
                build();

        bookingCreateRequest createRequest = new bookingCreateRequest(passengerId,null);

        PassengerDTO.passengerResponse passengerResponse= new PassengerDTO.passengerResponse(passengerId,passenger.getFullName(),passenger.getEmail(),null);
        Booking booking = new Booking();
            booking.setCreatedAt(OffsetDateTime.now());
            booking.setId(bookingId);
            booking.setItems(null);
            booking.setPassenger(passenger);


        when(passengerService.getObject(any())).thenReturn(passenger);
        when(bookingMapper.toEntity(any(bookingCreateRequest.class))).thenReturn(booking);
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);
        when(bookingMapper.toDTO(any(Booking.class))).thenAnswer(invocation -> {
            Booking b = invocation.getArgument(0);
            return new bookingResponse(
                    b.getId(),
                    b.getCreatedAt(),
                    passengerResponse,
                    null
            );
        });

        bookingResponse response = bookingServiceImpl.create(createRequest);

        assertEquals(bookingId, response.id());
        assertNotNull(response.passenger());
        assertEquals(passengerId, response.passenger().id());
        assertNull(response.bookingItems());

    }

    @Test
    void shouldGetBookingById() {
        Passenger passenger = Passenger.builder()
                .id(passengerId)
                .fullName("Josesito crack7770")
                .email("mamamaa@gmail.com")
                .bookings(new ArrayList<>())
                .profile(null)
                .build();

        Booking booking = new Booking();
        booking.setId(bookingId);
        booking.setCreatedAt(OffsetDateTime.now());
        booking.setPassenger(passenger);
        booking.setItems(new ArrayList<>());

        PassengerDTO.passengerResponse passengerResponse = new PassengerDTO.passengerResponse(
                passengerId,
                passenger.getFullName(),
                passenger.getEmail(),
                null
        );

        bookingResponse expectedResponse = new bookingResponse(
                bookingId,
                booking.getCreatedAt(),
                passengerResponse,
                null
        );

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        when(bookingMapper.toDTO(booking)).thenReturn(expectedResponse);

        bookingResponse response = bookingServiceImpl.get(bookingId);

        assertNotNull(response);
        assertEquals(bookingId, response.id());
        assertNotNull(response.passenger());
        assertEquals(passengerId, response.passenger().id());
        assertEquals("Josesito crack7770", response.passenger().fullName());
        assertEquals("mamamaa@gmail.com", response.passenger().email());
    }
}
