package com.example.airline.DTO;

import com.example.airline.entities.Passenger;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class BookingDTO {
    public record bookingCreateRequest(Long passengerId) implements Serializable{}
    public record bookingUpdateRequest(Long passengerId) implements Serializable{}
    public record bookingResponseBasic(long id, OffsetDateTime createdAt, PassengerDTO.passengerResponse passenger) implements Serializable{}

}
