package com.example.airline.Services.Util;

import com.example.airline.entities.Airline;
import com.example.airline.repositories.AirlineRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AirlineReferenceResolver {
    private final AirlineRepository airlineRepository;

    public Airline resolveAirlineById (Long airlineId){
        if(airlineId == null) return null;
        return  airlineRepository.findById(airlineId).orElseThrow(()-> new EntityNotFoundException("Airline with id: "+airlineId+" not found"));
    }


}
