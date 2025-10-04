package com.example.airline.Services;

import com.example.airline.DTO.AirlaneDTO;
import com.example.airline.entities.Airline;

import java.util.List;

public interface AirlineService {
    public AirlaneDTO.airlineResponse create(AirlaneDTO.airlineCreateRequest Req);
    public AirlaneDTO.airlineResponse get(Long id);
    public Airline getObjectById(Long id);
    public List<AirlaneDTO.airlineResponse> list();
    public void delete(Long id);
    public AirlaneDTO.airlineResponse update(Long id, AirlaneDTO.airlineUpdateRequest Req);
}
