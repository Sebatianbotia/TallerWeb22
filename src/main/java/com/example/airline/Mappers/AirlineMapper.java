package com.example.airline.Mappers;

import com.example.airline.DTO.AirlaneDTO;
import com.example.airline.entities.Airline;
import com.example.airline.entities.Flight;
import com.example.airline.repositories.AirlineRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AirlineMapper {
    private AirlineRepository airlineRepository;

    public Airline toEntity(AirlaneDTO.airlineCreateRequest createrequest) {
        Airline airline = Airline.builder().name(createrequest.name()).code(createrequest.code()).build();
        return airline;
    }

    public Airline updateEntity(AirlaneDTO.airlineUpdateRequest updateRequest) {
    Airline foundAirline = airlineRepository.findById(updateRequest.airlineId()).orElseThrow(() -> new EntityNotFoundException("Aerolinea con id: " + id + " no encontrado"));

    if (updateRequest.name() != null) {
        foundAirline.setName(updateRequest.name());
    }
    if (updateRequest.code() != null) {
        foundAirline.setCode(updateRequest.code());
    }
    return foundAirline;
    }

    public AirlaneDTO.airlineDtoResponse toDTO(Long airlineId) {
        Airline foundAirline = airlineRepository.findById(airlineId).orElseThrow(() -> new EntityNotFoundException("Aerolinea con id: " + airlineId + " no encontrado"));
        return new AirlaneDTO.airlineDtoResponse(foundAirline.getId(), foundAirline.getName(), foundAirline.getCode(), getFlightIds(foundAirline.getFlights()));
    }

    // Luego de que se tenga toDTO de flight se continua
    private List<Long> getFlightDTOS(List<Flight> flights) {
        if (flights == null || flights.isEmpty()) {
            return new ArrayList<>();
        }
        List<Long> flightIds = new ArrayList<>();
        for (Flight flight : flights) {
        }
    }

}
