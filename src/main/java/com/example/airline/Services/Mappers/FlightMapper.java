package com.example.airline.Services.Mappers;

import com.example.airline.DTO.FlightDto;
import com.example.airline.DTO.SeatInventoryDTO;
import com.example.airline.DTO.TagDTO;
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



    public static Flight toEntity(FlightDto.flightCreateRequest createRequest) {
        return Flight.builder().number(createRequest.number()).arrivalTime(createRequest.arrivalTime()).departureTime(createRequest.departureTime()).build();
    }

    public static void path(Flight entity,FlightDto.flightUpdateRequest updateRequest) {
        if (updateRequest.number() != null) {
            entity.setNumber(updateRequest.number());
        }
        if (updateRequest.arrivalTime() != null) {
            entity.setArrivalTime(updateRequest.arrivalTime());
        }
        if (updateRequest.departureTime() != null) {
            entity.setDepartureTime(updateRequest.departureTime());
        }
    }

    public static FlightDto.flightResponse toDTO(Flight entity) {
        var tag = entity.getTags() == null ? Set.<TagDTO.tagResponse>of():
                entity.getTags().stream().map(TagMapper::toDTO).collect(Collectors.toSet());
        var seatInventories = entity.getSeatInventories() == null ? List.<SeatInventoryDTO.seatInventoryFlightView>of():
                entity.getSeatInventories().stream().map(SeatInventoryMapper::toDTO).toList();
        return new FlightDto.flightResponse(entity.getId(), entity.getNumber(), entity.getArrivalTime(), entity.getDepartureTime(), AirlineMapper.airlineFlightView(entity.getAirline()),tag,seatInventories
                );
    }

    public static FlightDto.flightAirportView flightAirportView(Flight entity) {
        var tag = entity.getTags() == null ? Set.<TagDTO.tagResponse>of():
                entity.getTags().stream().map(TagMapper::toDTO).collect(Collectors.toSet());
        return new FlightDto.flightAirportView(entity.getId(), entity.getNumber(), entity.getArrivalTime(), entity.getDepartureTime(),
                AirlineMapper.airlineFlightView(entity.getAirline()), tag
                );
    }

    public static FlightDto.flightAirlineView flightAirlineView(Flight entity) {
        return new FlightDto.flightAirlineView(entity.getId(), entity.getNumber(), entity.getArrivalTime(), entity.getDepartureTime(),
                entity.getTags().stream().map(TagMapper::toDTO).collect(Collectors.toSet())
                );
    }
    }


