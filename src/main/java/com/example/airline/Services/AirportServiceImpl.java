package com.example.airline.Services;

import com.example.airline.DTO.AirportDTO.*;
import com.example.airline.Services.Mappers.AirportMapper;
import com.example.airline.entities.Airport;
import com.example.airline.repositories.AirportRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AirportServiceImpl implements AirportService {
    public AirportRepository airportRepository;
    public final AirportMapper Mapper;

    @Override
    public AirportResponse create(AirportCreateRequest request) {
        return Mapper.toDTO(airportRepository.save(Mapper.toEntity(request)));
    }

    @Override
    @Transactional(readOnly = true)
    public AirportResponse get(Long id) {
        var a = airportRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Airport not found"));
        return Mapper.toDTO(a);
    }
    @Override
    public Airport getObjectById(Long id) {
        return airportRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Airport with code " + id + " not found"));
    }

    @Override
    public AirportResponse update(Long id, AirportUpdateRequest request) {
        var a = airportRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Airport not found"));
        Mapper.updateEntity(request,a);
        return Mapper.toDTO(a);
    }

    @Override
    public void delete(Long id) {
        airportRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AirportResponse> list() {
        return airportRepository.findAll().stream().map(Mapper::toDTO).toList();
    }
}
