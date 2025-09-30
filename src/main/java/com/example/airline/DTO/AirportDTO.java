package com.example.airline.DTO;


import java.io.Serializable;

public class AirportDTO {
    public record AirportCreateRequest(String name, String code, String city) implements Serializable {}
    public record AirportUpdateRequest(Long id, String name, String code, String city) implements Serializable {}
    public record AirportFlightView(Long id, String name, String code, String city) implements Serializable {}
    public record AirportResponse(Long id, String name, String code, String city) implements Serializable {}
}
