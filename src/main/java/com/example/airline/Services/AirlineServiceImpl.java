package com.example.airline.Services;

import com.example.airline.DTO.AirlaneDTO;
import com.example.airline.Mappers.AirlineMapper;
import com.example.airline.entities.Airline;
import com.example.airline.repositories.AirlineRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService{

    private final AirlineRepository airlineRepository;
    private final AirlineMapper Mapper;

    @Override
    public AirlaneDTO.airlineResponse create(AirlaneDTO.airlineCreateRequest req) {
        return Mapper.toDTO(airlineRepository.save(Mapper.toEntity(req)));
    }

    @Override
    @Transactional(readOnly = true)
    public AirlaneDTO.airlineResponse get(Long id) {
        return Mapper.toDTO(getObjectById(id));
    }

    @Override
    public Airline getObjectById(Long id) {
        return airlineRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Airline with id " + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AirlaneDTO.airlineResponse> list() {
        return airlineRepository.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public void delete(Long id) {
        airlineRepository.deleteById(id);
    }

    @Override
    public AirlaneDTO.airlineResponse update(Long id, AirlaneDTO.airlineUpdateRequest req) {
        var a = airlineRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Airline not found"));
        Mapper.updateEntity(a,req);
        return  Mapper.toDTO(a);
    }
}
