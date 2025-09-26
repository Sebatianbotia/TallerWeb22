package com.example.airline.DTO;

import com.example.airline.entities.Airline;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class FlightDto {
public record flightCreateRequest(String number, OffsetDateTime arrivalTime,
OffsetDateTime departureTime, Long airlineId, Long originAirportId,
                                  Long destinationAirpotId
) implements Serializable {}

}
