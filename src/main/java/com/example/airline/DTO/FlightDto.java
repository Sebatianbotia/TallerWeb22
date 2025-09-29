package com.example.airline.DTO;


import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

public class FlightDto {
    public record flightCreateRequest(String number, OffsetDateTime arrivalTime,
                                      OffsetDateTime departureTime, Long airlineId, Long originAirportId,
                                      Long destinationAirpotId, Set<Long> tagsId
    ) implements Serializable {
    }

    public record flightUpdateRequest(Long flightId, String number, OffsetDateTime arrivalTime,
                                      OffsetDateTime departureTime, Long airlineId, Long originAirportId,
                                      Long destinationAirpotId, Set<Long> tagsId) implements Serializable {
    }

    public record flightResponse(Long flightId, String number, OffsetDateTime arrivalTime,
                                 OffsetDateTime departureTime, Long airlineId, Long originAirportId,
                                 Long destinationAirpotId, Set<TagDTO.tagResponse> tags, List<SeatInventoryDTO.seatInventoryDtoResponse> seatInventory) implements Serializable {}

}
