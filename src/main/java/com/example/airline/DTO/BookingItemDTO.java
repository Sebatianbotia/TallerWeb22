package com.example.airline.DTO;

import com.example.airline.entities.Cabin;

import java.io.Serializable;
import java.math.BigDecimal;

public class BookingItemDTO {
    public record bookingItemCreateRequest(BigDecimal price, Integer segmentOrder, Cabin cabin, Long bookingId, Long fligthId) implements Serializable {}
    public record bookingItemUpdateRequest(Long bookingitemsId,BigDecimal price, Integer segmentOrder, Cabin cabin, Long bookingId, Long flightId) implements Serializable {}
    public record bookingItemReponse(Long bookingitemsId, Cabin cabin, Long bookingId, Long flightId) implements Serializable {}


}
