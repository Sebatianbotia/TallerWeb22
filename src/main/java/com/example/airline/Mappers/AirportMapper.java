package com.example.airline.Mappers;

import com.example.airline.DTO.AirportDTO;
import com.example.airline.entities.Airport;
import com.example.airline.repositories.AirportRepository;
import jakarta.persistence.EntityNotFoundException;

public class AirportMapper {
    AirportRepository airportRepository;
    public Airport toEntity(AirportDTO.AirportCreateRequest createRequest) {
        Airport airport = Airport.builder()
                .name(createRequest.name())
                .code(createRequest.code())
                .city(createRequest.city())
                .build();
        return airport;
    }

    public Airport updateEntity(AirportDTO.AirportUpdateRequest updateRequest) {
        if (updateRequest == null) return null;
        Airport foundAirport = airportRepository.findById(updateRequest.id()).orElseThrow(()-> new EntityNotFoundException("Airport id: " + updateRequest.id() + " no encontrado"));
        if (updateRequest.name() != null) foundAirport.setName( updateRequest.name());
        if (updateRequest.code() != null) foundAirport.setCode( updateRequest.code());
        if (updateRequest.city() != null) foundAirport.setCity( updateRequest.city());
        return foundAirport;
    }

    public AirportDTO.AirportResponse toDTO(Long id) {
        Airport foundAirport = airportRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Airport id: " + id + " no encontrado"));
        return new AirportDTO.AirportResponse(id, foundAirport.getName(), foundAirport.getCode(), foundAirport.getCity());

    }
}
