package com.example.airline.Services;
import com.example.airline.DTO.AirlaneDTO.*;
import com.example.airline.entities.Airline;

import java.util.List;

public interface AirlineService {
public airlineResponse create(airlineCreateRequest Req);
public airlineResponse get(Long id);
public Airline getObjectById(Long id);
public List<airlineResponse> list();
public void delete(Long id);
public airlineResponse update(Long id, airlineUpdateRequest Req);
}


