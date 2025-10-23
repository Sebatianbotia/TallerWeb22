package com.example.airline.services;

import com.example.airline.DTO.AirlaneDTO;
import com.example.airline.entities.Airline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AirlineService {
    public AirlaneDTO.airlineResponse create(AirlaneDTO.airlineCreateRequest Req);
    public AirlaneDTO.airlineResponse get(Long id);
    public Airline getObjectById(Long id);
    public Page<AirlaneDTO.airlineResponse> list(Pageable pageable);
    public void delete(Long id);
    public AirlaneDTO.airlineResponse update(Long id, AirlaneDTO.airlineUpdateRequest Req);
}
