package com.example.airline.DTO;

import com.example.airline.entities.Cabin;

import java.io.Serializable;

public class SeatInvetoryDTO {
    public record seatInventoryCreateRequest(Integer totalSeats, Integer availableSeats,
                                            Cabin cabin, Long flightId) implements Serializable {}
    public record seatInventoryUpdateRequest(Long seatInventoryId,  Integer totalSeats, Integer availableSeats,
                                             Cabin cabin, Long flightId) implements Serializable {}
    public record seatInventoryDtoResponse(Long seatInventoryId,Integer totalSeats, Integer availableSeats,
                                           Cabin cabin) implements Serializable{}

}
