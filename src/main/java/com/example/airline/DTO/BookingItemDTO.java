package com.example.airline.DTO;

import com.example.airline.entities.Cabin;

import java.io.Serializable;
import java.math.BigDecimal;

public class BookingItemDTO {
    public record bookingItemCreateRequest(BigDecimal price, Integer segmentOrder, Cabin cabin, bookingDTO booking, flightDTO flight) implements Serializable {}
    public record bookingDTO(String code) implements Serializable{}
    public record flightDTO(String number) implements Serializable{}
}
