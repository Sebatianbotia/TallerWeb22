package com.example.airline.services;

import com.example.airline.DTO.BookingItemDTO;
import com.example.airline.DTO.FlightDto;
import com.example.airline.Services.BookingItemServiceImpl;
import com.example.airline.Services.Mappers.BookingItemMapper;
import com.example.airline.Services.Mappers.FlightMapper;
import com.example.airline.entities.Booking;
import com.example.airline.entities.BookingItem;
import com.example.airline.entities.Cabin;
import com.example.airline.entities.Flight;
import com.example.airline.repositories.BookingItemsRepository;
import com.example.airline.repositories.BookingRepository;
import com.example.airline.repositories.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BookingItemServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private FlightRepository flightRepository;
    @Mock
    private BookingItemsRepository bookingItemsRepository;

    @Mock
    private BookingItemMapper bookingItemMapper;

    @InjectMocks
    private BookingItemServiceImpl bookingItemService;

    private final Long BOOKING_ID = 1L;
    private final Long FLIGHT_ID = 2L;
    private final Long BOOKING_ITEM_ID = 3L;

    @Mock
    private FlightMapper flightMapper;

    @BeforeEach
    void setUp() {

        Flight mockFlight = Flight.builder().id(FLIGHT_ID).number("FL101").build();
        FlightDto.flightResponse mockFlightResponse = new FlightDto.flightResponse(FLIGHT_ID, "FL101", null, null, null, null, null, null, null);

    }



    @Test
    void shouldCreateAndReturnResponseSuccessfully() {

        Booking mockBooking = new Booking();
        mockBooking.setId(BOOKING_ID);
        Booking spyBooking = org.mockito.Mockito.spy(mockBooking);

        Flight mockFlight = Flight.builder().id(FLIGHT_ID).number("FL101").build();

        var createRequest = new BookingItemDTO.bookingItemCreateRequest(
                new BigDecimal("500.00"), 1, Cabin.ECONOMY, BOOKING_ID, FLIGHT_ID
        );

        when(bookingRepository.findBookingById(any())).thenReturn(Optional.of(spyBooking));
        when(flightRepository.findById(FLIGHT_ID)).thenReturn(Optional.of(mockFlight));

        BookingItem mockBookingItem = BookingItem.builder()
                .id(BOOKING_ITEM_ID)
                .price(createRequest.price())
                .segmentOrder(createRequest.segmentOrder())
                .cabin(createRequest.cabin())
                .booking(spyBooking)
                .flight(mockFlight)
                .build();

        when(bookingItemMapper.toDTO(any(BookingItem.class))).thenReturn(
                new BookingItemDTO.bookingItemReponse(
                        BOOKING_ITEM_ID,
                        Cabin.ECONOMY,
                        BOOKING_ID,
                        new FlightDto.flightResponse(FLIGHT_ID, "FL101", null, null, null, null, null, null, null)
                )
        );

        BookingItemDTO.bookingItemReponse actualResponse = bookingItemService.create(createRequest);


        verify(bookingRepository).findBookingById(BOOKING_ID);
        verify(flightRepository).findById(FLIGHT_ID);


        assertEquals(BOOKING_ITEM_ID, actualResponse.bookingitemsId());
        assertEquals(BOOKING_ID, actualResponse.bookingId());
        assertEquals(Cabin.ECONOMY, actualResponse.cabin());
        assertEquals(FLIGHT_ID, actualResponse.flight().flightId());
    }

}