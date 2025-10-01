package com.example.airline.Services.Mappers;

import com.example.airline.DTO.AirlaneDTO.*;
import com.example.airline.DTO.FlightDto;
import com.example.airline.DTO.SeatInventoryDTO;
import com.example.airline.DTO.TagDTO;
import com.example.airline.entities.Airline;
import com.example.airline.entities.Flight;
import com.example.airline.entities.SeatInventory;
import com.example.airline.entities.Tag;
import com.example.airline.repositories.AirlineRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.*;

@Mapper(componentModel = "spring")
public interface AirlineMapper {

    Airline toEntity(airlineCreateRequest request);
    airlineResponse toDTO(Airline airline);
    airlineCreateRequest toFlightView(Airline airline);
    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Airline  airline, airlineUpdateRequest request);



}
