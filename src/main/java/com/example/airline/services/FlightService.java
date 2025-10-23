package com.example.airline.services;

import com.example.airline.DTO.FlightDto;
import com.example.airline.entities.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FlightService {

    FlightDto.flightResponse create(FlightDto.flightCreateRequest createRequest);

    FlightDto.flightResponse update(Long id, FlightDto.flightUpdateRequest updateRequest);

    FlightDto.flightResponse get(Long id);

    Page<FlightDto.flightResponse> list(Pageable pageable);

    void delete(Long id);

    Flight getFlightObject(Long id);
}
