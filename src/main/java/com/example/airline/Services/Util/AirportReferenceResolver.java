package com.example.airline.Services.Util;

import com.example.airline.entities.Airport;
import com.example.airline.repositories.AirportRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AirportReferenceResolver {
    private final AirportRepository airportRepository;

    public Airport resolveAirportByCode(String code) {
        if(code == null) return null;
        return airportRepository.findByCode(code).orElseThrow(()-> new EntityNotFoundException("Airport not found"));

    }
}
