package com.example.airline.Services;

import com.example.airline.DTO.FlightDto;
import com.example.airline.entities.Flight;

import java.util.List;

public interface FlightService {
    FlightDto.flightResponse create(FlightDto.flightCreateRequest createRequest);
    FlightDto.flightResponse find(Long id);
    FlightDto.flightResponse update(Long id, FlightDto.flightUpdateRequest updateRequest);
    Flight getFlightObject(Long id);
    List<FlightDto.flightResponse> findAll();
    void delete(Long id);
}
