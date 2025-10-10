package com.example.airline.Services;

import com.example.airline.DTO.AirportDTO;
import com.example.airline.entities.Airport;

import java.util.List;

public interface AirportService {

    AirportDTO.AirportResponse create(AirportDTO.AirportCreateRequest request);

    AirportDTO.AirportResponse get(Long id);

    Airport getObjectById(Long id);

    AirportDTO.AirportResponse update(Long id, AirportDTO.AirportUpdateRequest request);
    void delete(Long id);
    List<AirportDTO.AirportResponse> list();
}
