package com.example.airline.services;

import com.example.airline.API.Error.NotFoundException;
import com.example.airline.DTO.AirlaneDTO;
import com.example.airline.Mappers.AirlineMapper;
import com.example.airline.entities.Airline;
import com.example.airline.repositories.AirlineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        Airline entity = Mapper.toEntity(req);
        Airline saved = airlineRepository.save(entity);
        return Mapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AirlaneDTO.airlineResponse get(Long id) {
        return Mapper.toDTO(getObjectById(id));
    }

    @Override
    public Airline getObjectById(Long id) {
        return airlineRepository.findById(id).orElseThrow(()-> new NotFoundException("Airline with id " + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AirlaneDTO.airlineResponse> list(Pageable pageable) {
        return airlineRepository.findAll(pageable).map(Mapper::toDTO);
    }

    @Override
    public void delete(Long id) {
        airlineRepository.deleteById(id);
    }

    @Override
    public AirlaneDTO.airlineResponse update(Long id, AirlaneDTO.airlineUpdateRequest req) {
        var a = airlineRepository.findById(id).orElseThrow(()-> new NotFoundException("Airline not found"));
        Mapper.updateEntity(a,req);
        return  Mapper.toDTO(a);
    }
}
