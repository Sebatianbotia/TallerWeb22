package com.example.airline.Services;
import com.example.airline.DTO.AirlaneDTO.*;
import java.util.List;

public interface AirlineService {
public airlineDtoResponse create(airlineCreateRequest Req);
public airlineDtoResponse get(Long id);
public List<airlineDtoResponse> list();
public void delete(Long id);
public airlineDtoResponse update(long id, airlineUpdateRequest Req);
}


