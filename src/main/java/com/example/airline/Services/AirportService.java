package com.example.airline.Services;

import com.example.airline.DTO.AirportDTO.*;

import java.util.List;

public interface AirportService {
    public AirportResponse create(AirportCreateRequest request);
    public AirportResponse get(Long id);
    public AirportResponse update(long id, AirportUpdateRequest request);
    public void delete(Long id);
    public List<AirportResponse> list();
}
