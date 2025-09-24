package com.example.airline.DTO;

import com.example.airline.entities.Airport;

import java.io.Serializable;

public class AirportDTO {
    public record AirportCreateRequest(String name, String code, String city) implements Serializable {}
    public record AirportUpdateRequest(String name, String code, String city) implements Serializable {}
    public record AirportDeleteRequest(String code) implements Serializable {}
    public record AirportResponse(Long id, String name, String code, String city) implements Serializable {}
}
