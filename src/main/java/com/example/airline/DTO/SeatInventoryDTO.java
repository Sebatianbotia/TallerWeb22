package com.example.airline.DTO;

import com.example.airline.entities.Cabin;

import java.io.Serializable;

public class SeatInventoryDTO {
    public record seatInventoryCreateRequest(Integer totalSeats, Integer availableSeats,
                                            Cabin cabin) implements Serializable {}
    public record seatInventoryUpdateRequest(Integer totalSeats, Integer availableSeats,
                                             Cabin cabin) implements Serializable {}
    public record seatInventoryDtoResponse(Long seatInventoryId,Integer totalSeats, Integer availableSeats,
                                           Cabin cabin, String flightNumber) implements Serializable{}

}
