package com.example.airline.DTO;
import com.example.airline.entities.Flight;

import java.io.Serializable;
import java.util.List;

public class AirlaneDTO {
    public record airlineCreateRequest(String name, String code) implements Serializable {}
    public record airlineUpdateRequest(Long airlineId,String name, String code) implements Serializable {}
    public record airlineDtoResponse(Long id, String name, String code, List<FlightDto.flightResponse> flights) implements Serializable{}
}
