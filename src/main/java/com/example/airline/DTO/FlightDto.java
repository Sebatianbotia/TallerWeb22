package com.example.airline.DTO;


import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

public class FlightDto {
    public record flightCreateRequest(String number,
                                      OffsetDateTime arrivalTime,
                                      OffsetDateTime departureTime,
                                      Long aerlineId,
                                      String originAirportCode,
                                      String destinationAirportCode,
                                      Set<String> tags,
                                      List<SeatInventoryDTO.seatInventoryCreateRequest> seatInventories
    ) implements Serializable {}

    public record flightUpdateRequest(String number, OffsetDateTime arrivalTime,
                                      OffsetDateTime departureTime, Long airlineId, String originAirportCode,
                                      String destinationAirportCode, Set<String> tags) implements Serializable {
    }



    public record flightResponse(Long flightId,
                                 String number,
                                 OffsetDateTime arrivalTime,
                                 OffsetDateTime departureTime,
                                 AirlaneDTO.airlineFlightView airline,
                                 AirportDTO.AirportFlightView originAirport,
                                 AirportDTO.AirportFlightView destinationAirport,
                                 Set<TagDTO.tagResponse> tags,
                                 List<SeatInventoryDTO.seatInventoryFlightView> seatInventories) implements Serializable {}


    public record flightAirportView(Long flightId, String number, OffsetDateTime arrivalTime,
                                    OffsetDateTime departureTime, AirlaneDTO.airlineFlightView airline, Set<TagDTO.tagResponse> tags) implements Serializable {}

    public record flightAirlineView(Long flightId, String number, OffsetDateTime arrivalTime,
                                    OffsetDateTime departureTime, Set<TagDTO.tagResponse> tags) implements Serializable {}
}
