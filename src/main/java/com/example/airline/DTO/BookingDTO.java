package com.example.airline.DTO;

import com.example.airline.entities.Passenger;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

public class BookingDTO {
    public record bookingCreateRequest(Long passengerId, List<BookingDTO.bookingCreateRequest> items) implements Serializable{}
    public record bookingResponse(Long id, OffsetDateTime createdAt, PassengerDTO.passengerResponse passenger, List<BookingItemDTO.bookingItemReponse> bookingItems) implements Serializable{}

}
