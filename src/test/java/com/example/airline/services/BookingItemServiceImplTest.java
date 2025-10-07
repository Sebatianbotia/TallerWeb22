package com.example.airline.services;

import com.example.airline.DTO.BookingItemDTO.*;
import com.example.airline.DTO.FlightDto;
import com.example.airline.Mappers.BookingItemMapper;
import com.example.airline.Services.BookingItemServiceImpl;
import com.example.airline.Services.BookingServiceImpl;
import com.example.airline.Services.FlightServiceImpl;
import com.example.airline.entities.Booking;
import com.example.airline.entities.BookingItem;
import com.example.airline.entities.Cabin;
import com.example.airline.entities.Flight;
import com.example.airline.repositories.BookingItemsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingItemServiceImplTest {

    @Mock
    private BookingItemsRepository bookingItemsRepository;

    @Mock
    private BookingItemMapper bookingItemMapper;

    @Mock
    private FlightServiceImpl flightServiceImpl;

    @Mock
    private BookingServiceImpl bookingServiceImpl;

    @InjectMocks
    private BookingItemServiceImpl bookingItemService;

    private static final Long BOOKING_ITEM_ID = 1L;
    private static final Long BOOKING_ID = 1L;
    private static final Long FLIGHT_ID = 1L;

    @Test
    void shouldCreateAndReturnResponseSuccessfully() {
        // Preparar datos
        Booking mockBooking = new Booking();
        mockBooking.setId(BOOKING_ID);
        mockBooking.setItems(new ArrayList<>());

        Flight mockFlight = Flight.builder()
                .id(FLIGHT_ID)
                .number("FL101")
                .bookingItems(new ArrayList<>())
                .build();

        var createRequest = new bookingItemCreateRequest(
                new BigDecimal("500.00"), 1, Cabin.ECONOMY, BOOKING_ID, FLIGHT_ID
        );

        BookingItem mockBookingItem = BookingItem.builder()
                .id(BOOKING_ITEM_ID)
                .price(createRequest.price())
                .segmentOrder(createRequest.segmentOrder())
                .cabin(createRequest.cabin())
                .booking(mockBooking)
                .flight(mockFlight)
                .build();

        when(bookingItemMapper.toEntity(any(bookingItemCreateRequest.class)))
                .thenReturn(mockBookingItem);

        when(flightServiceImpl.getFlightObject(FLIGHT_ID))
                .thenReturn(mockFlight);

        when(bookingServiceImpl.getObject(BOOKING_ID))
                .thenReturn(mockBooking);

        when(bookingItemsRepository.save(any(BookingItem.class)))
                .thenReturn(mockBookingItem);

        when(bookingItemMapper.toDTO(any(BookingItem.class)))
                .thenReturn(
                        new bookingItemReponse(
                                BOOKING_ITEM_ID,
                                Cabin.ECONOMY,
                                BOOKING_ID,
                                new FlightDto.flightResponse(FLIGHT_ID, "FL101", null, null, null, null, null, null, null)
                        )
                );

        bookingItemReponse actualResponse = bookingItemService.create(createRequest);

        assertNotNull(actualResponse);
        assertEquals(BOOKING_ITEM_ID, actualResponse.bookingitemsId());
        assertEquals(BOOKING_ID, actualResponse.bookingId());
        assertEquals(Cabin.ECONOMY, actualResponse.cabin());
        assertEquals(FLIGHT_ID, actualResponse.flight().flightId());

    }
}