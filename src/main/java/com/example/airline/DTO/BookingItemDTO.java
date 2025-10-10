package com.example.airline.DTO;

import com.example.airline.entities.Cabin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.math.BigDecimal;

public class BookingItemDTO {

    public record bookingItemCreateRequest(
            @NotNull
            @Positive
            BigDecimal price,
            @NotNull
            @Positive
            Integer segmentOrder,
            @NotNull
            Cabin cabin,
            @NotNull
            Long bookingId,
            @NotNull
            Long fligthId
    ) implements Serializable {}

    public record bookingItemUpdateRequest(
            @Positive
            BigDecimal price,
            @Positive
            Integer segmentOrder,
            Cabin cabin,
            Long bookingId,
            Long flightId
    ) implements Serializable {}

    public record bookingItemReponse(
            Long bookingitemsId,
            Cabin cabin,
            Long bookingId,
            FlightDto.flightResponse flight
    ) implements Serializable {}


}
