package com.example.airline.DTO;

import com.example.airline.entities.Passenger;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class BookingDTO {
    public record bookingCreateRequest(passengerDTO passenger) implements Serializable{}
    public record passengerDTO(String fullName, String email )implements Serializable{}
    public record bookingUpdateRequest(passengerDTO passenger) implements Serializable{}
    public record bookingResponse(long id, OffsetDateTime createdAt, Passenger passenger) implements Serializable{}

}
