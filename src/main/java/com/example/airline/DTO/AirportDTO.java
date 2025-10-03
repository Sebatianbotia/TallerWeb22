package com.example.airline.DTO;


import java.io.Serializable;
import java.util.List;

public class AirportDTO {
    public record AirportCreateRequest(String name, String code, String city) implements Serializable {}
    public record AirportUpdateRequest(String name, String code, String city) implements Serializable {}
    public record AirportFlightView(Long id, String name, String code, String city) implements Serializable {}
    public record AirportResponse(Long id,
                                  String name,
                                  String code,
                                  String city,
                                  List<FlightDto.flightAirportView> flightsOrigin,
                                  List<FlightDto.flightAirportView> flightsDestination
                                    ) implements Serializable {}
}
