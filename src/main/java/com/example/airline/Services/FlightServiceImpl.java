package com.example.airline.Services;

import com.example.airline.DTO.FlightDto;
import com.example.airline.entities.Flight;
import com.example.airline.repositories.FlightRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
@Transactional
public class FlightServiceImpl implements FlightService{

    private final FlightRepository flightRepository;

    @Override
    public FlightDto.flightResponse create(FlightDto.flightCreateRequest createRequest) {
        return null;
    }

    @Override
    public FlightDto.flightResponse find(Long id) {
        return null;
    }

    @Override
    public Flight findFlightObject(Long id) {
        var f =  flightRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "flight with id: " + id + " not found"
        ));
        return f;
    }

    @Override
    public List<FlightDto.flightResponse> findAll() {
        return List.of();
    }

    @Override
    public void delete(Long id) {

    }
}
