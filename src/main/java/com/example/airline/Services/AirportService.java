package com.example.airline.Services;

import com.example.airline.DTO.AirportDTO.*;
import com.example.airline.entities.Airport;

import java.util.List;

public interface   AirportService {
    public AirportResponse create(AirportCreateRequest request);
    public AirportResponse get(Long id);
    public Airport getObjectById(Long id);
    public AirportResponse update(Long id, AirportUpdateRequest request);
    public void delete(Long id);
    public List<AirportResponse> list();
}
