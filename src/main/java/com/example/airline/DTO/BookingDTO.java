package com.example.airline.DTO;

import com.example.airline.entities.Passenger;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

public class BookingDTO {
    public record bookingCreateRequest(Long passengerId) implements Serializable{}
    public record bookingResponse(long id, OffsetDateTime createdAt, PassengerDTO.passengerResponse passenger, List<BookingItemDTO.bookingItemReponse> bookingItems) implements Serializable{}

}
