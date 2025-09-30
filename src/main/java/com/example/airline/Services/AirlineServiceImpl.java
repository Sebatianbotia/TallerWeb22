package com.example.airline.Services;

import com.example.airline.DTO.AirlaneDTO.*;
import com.example.airline.Services.Mappers.AirlineMapper;
import com.example.airline.entities.Airline;
import com.example.airline.repositories.AirlineRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository airlineRepository;
    private final AirlineMapper airlineMapper;

    @Override
    public airlineResponse create(airlineCreateRequest req) {
        return AirlineMapper.toDTO(airlineRepository.save(AirlineMapper.toEntity(req)));
    }

    @Override
    @Transactional(readOnly = true)
    public airlineResponse get(Long id) {
        return airlineRepository.findAirlineById(id).map(AirlineMapper::toDTO).orElseThrow(()-> new EntityNotFoundException("Airline not found"));

    }

    @Override
    public Airline getObjectById(Long id) {
        return airlineRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Airline with id " + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<airlineResponse> list() {
        return airlineRepository.findAll().stream().map(AirlineMapper::toDTO).toList();
    }

    @Override
    public void delete(Long id) {
        airlineRepository.deleteById(id);
    }

    @Override
    public airlineResponse update(long id, airlineUpdateRequest req) {
        var a = airlineRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Airline not found"));
        AirlineMapper.updateEntity(a,req);
        return  AirlineMapper.toDTO(a);
    }
}
