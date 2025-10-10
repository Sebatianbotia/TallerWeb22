package com.example.airline.DTO;

import java.io.Serializable;
import java.util.List;

public class AirlaneDTO {
    public record airlineCreateRequest(String name, String code) implements Serializable {}
    public record airlineUpdateRequest(Long airlineId, String name, String code) implements Serializable {}
    public record airlineFlightView(Long id, String name, String code) implements Serializable{} // para usar en flightMapper
    public record airlineResponse(Long id, String name, String code, List<FlightDto.flightAirlineView> flights) implements Serializable{}
    public record airlineResponseBasic(Long id, String name, String code) implements Serializable{}
}
