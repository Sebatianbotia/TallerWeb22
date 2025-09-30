package com.example.airline.Services.Mappers;

import com.example.airline.DTO.FlightDto;
import com.example.airline.entities.*;
import com.example.airline.repositories.AirlineRepository;
import com.example.airline.repositories.AirportRepository;
import com.example.airline.repositories.FlightRepository;
import com.example.airline.repositories.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class FlightMapper {

    @Autowired
    private FlightRepository flightRepository;
    private AirlineRepository airlineRepository;
    private AirportRepository airportRepository;
    private TagRepository tagRepository;



    public Flight toEntity(FlightDto.flightCreateRequest createRequest) {
        if (createRequest == null) return null;
        Airline foundAirline = airlineRepository.findById(createRequest.airlineId()).orElseThrow(() -> new EntityNotFoundException("Aerolinea con id: " + createRequest.airlineId() + " no encontrado"));
        Airport foundOriginAirport = airportRepository.findById(createRequest.originAirportId()).orElseThrow(()-> new EntityNotFoundException("Aeropuerto de origen no encontrado"));
        Airport foundDestinationAirport = airportRepository.findById(createRequest.destinationAirpotId()).orElseThrow(()-> new EntityNotFoundException("Aeropuerto de origen no encontrado"));
        Set<Tag> tags = createRequest.tagsId().stream()
                .map(tagId -> tagRepository.findById(tagId)
                        .orElseThrow(() -> new NoSuchElementException("Tag con ID " + tagId + " no encontrado.")))
                .collect(Collectors.toSet());


        return Flight.builder()
                .number(createRequest.number())
                .arrivalTime(createRequest.arrivalTime())
                .departureTime(createRequest.departureTime())
                .airline(foundAirline)
                .originAirport(foundOriginAirport)
                .destinationAirport(foundDestinationAirport)
                .tags(tags)
                .build();
    }

    public Flight updateEntity(FlightDto.flightUpdateRequest updateRequest) {
        if (updateRequest == null) return null;
        Flight foundFlight = flightRepository.findById(updateRequest.flightId()).orElseThrow(()-> new EntityNotFoundException("Flight con id: " + updateRequest.flightId() + " no encontrado"));
        if(updateRequest.number() != null) foundFlight.setNumber(updateRequest.number());
        if (updateRequest.arrivalTime() != null) foundFlight.setArrivalTime(updateRequest.arrivalTime());
        if (updateRequest.departureTime() != null) foundFlight.setDepartureTime(updateRequest.departureTime());
        if (updateRequest.airlineId() != null) {
            Airline foundAirline = airlineRepository.findById(updateRequest.airlineId()).orElseThrow(() -> new EntityNotFoundException("Aerolinea con id: " + updateRequest.airlineId() + " no encontrado"));
            foundFlight.setAirline(foundAirline);
        }
        if (updateRequest.originAirportId() != null) {
            Airport foundOriginAirport = airportRepository.findById(updateRequest.originAirportId()).orElseThrow(()-> new EntityNotFoundException("Aeropuerto de origen no encontrado"));
            foundFlight.setOriginAirport(foundOriginAirport);
        }
        if (updateRequest.destinationAirpotId() != null) {
            Airport foundDestinationAirport = airportRepository.findById(updateRequest.destinationAirpotId()).orElseThrow(()-> new EntityNotFoundException("Aeropuerto de origen no encontrado"));
            foundFlight.setDestinationAirport(foundDestinationAirport);
        }
        if (updateRequest.tagsId() != null) {
            Set<Tag> tags = updateRequest.tagsId().stream()
                    .map(tagId -> tagRepository.findById(tagId)
                            .orElseThrow(() -> new NoSuchElementException("Tag con ID " + tagId + " no encontrado.")))
                    .collect(Collectors.toSet());
            foundFlight.setTags(tags);
        }
        return foundFlight;
    }

    public static FlightDto.flightResponse toDTO(Flight foundFlight) {
        return new FlightDto.flightResponse(
                foundFlight.getId(),
                foundFlight.getNumber(),
                foundFlight.getArrivalTime(),
                foundFlight.getDepartureTime(),
                foundFlight.getAirline().getId(),
                foundFlight.getOriginAirport().getId(),
                foundFlight.getDestinationAirport().getId(),
                TagMapper.toDTO(foundFlight.getTags()),SeatInventoryMapper.toDTO(foundFlight.getSeatInventories()));
    }
    }


