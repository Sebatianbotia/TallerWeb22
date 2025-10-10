package com.example.airline.Services;

import com.example.airline.DTO.AirportDTO.*;
import com.example.airline.Mappers.AirportMapper;
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
    public AirportMapper airportMapper;

    @Override
    public AirportResponse create(AirportCreateRequest request) {
        return airportMapper.toDTO(airportRepository.save(airportMapper.toEntity(request)));
    }

    @Override
    @Transactional(readOnly = true)
    public AirportResponse get(Long id) {
        var a = airportRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Airport not found"));
        return airportMapper.toDTO(a);
    }
    @Override
    public Airport getObjectById(Long id) {
        return airportRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Airport with code " + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public AirportResponse update(Long id, AirportUpdateRequest request) {
        var a = airportRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Airport not found"));
        airportMapper.updateEntity(request,a);
        return airportMapper.toDTO(a);
    }

    @Override
    public void delete(Long id) {
        airportRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AirportResponse> list() {
        return airportRepository.findAll().stream().map(airportMapper::toDTO).toList();
    }

    public Airport getAirportByCode(String code) {
        return airportRepository.findByCode(code).orElseThrow(()-> new EntityNotFoundException("Airport with id: " + code + " not found"));
    }
}
