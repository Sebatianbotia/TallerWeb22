package com.example.airline.DTO;
import com.example.airline.entities.Flight;

import java.io.Serializable;
import java.util.List;

public class AirlaneDTO {
    public record airlineCreateRequest(String name, String code) implements Serializable {}
    public record airlineUpdateRequest(String name, String code) implements Serializable {}
    public record airlineDeleteRequest(String code) implements Serializable {}
    public record airlineDtoResponse(long id, String name, String code, List<Flight> flights) implements Serializable{}
}
