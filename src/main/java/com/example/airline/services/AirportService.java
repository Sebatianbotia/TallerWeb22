package com.example.airline.services;

import com.example.airline.DTO.AirportDTO;
import com.example.airline.entities.Airport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AirportService {

    AirportDTO.AirportResponse create(AirportDTO.AirportCreateRequest request);

    AirportDTO.AirportResponse get(Long id);

    Airport getObjectById(Long id);

    AirportDTO.AirportResponse update(Long id, AirportDTO.AirportUpdateRequest request);
    void delete(Long id);
    Page<AirportDTO.AirportResponse> list(Pageable pageable);
}
