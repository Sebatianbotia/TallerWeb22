package com.example.airline.Services;

import com.example.airline.DTO.FlightDto;
import com.example.airline.entities.Flight;

import java.util.List;

public interface FlightService {

    FlightDto.flightResponse create(FlightDto.flightCreateRequest createRequest);

    FlightDto.flightResponse update(Long id, FlightDto.flightUpdateRequest updateRequest);

    FlightDto.flightResponse get(Long id);

    List<FlightDto.flightResponse> findAll();

    void delete(Long id);

    Flight getFlightObject(Long id);
}
