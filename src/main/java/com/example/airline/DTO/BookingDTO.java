package com.example.airline.DTO;

import com.example.airline.entities.Passenger;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

public class BookingDTO {

    public record bookingCreateRequest(
            @NotNull
            Long passengerId,
            List<BookingDTO.bookingCreateRequest> items
    ) implements Serializable{}
    public record bookingResponse(Long id, OffsetDateTime createdAt, PassengerDTO.passengerResponse passenger, List<BookingItemDTO.bookingItemReponse> bookingItems) implements Serializable{}

}
