package com.example.airline.Mappers;

import com.example.airline.DTO.AirlaneDTO.*;

import com.example.airline.entities.Airline;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = FlightMapper.class)
public interface AirlineMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "flights", ignore = true)
    Airline toEntity(airlineCreateRequest request);


    airlineResponse toDTO(Airline airline);

    airlineFlightView toFlightView(Airline airline);

    @Mapping(target = "flights", ignore = true)
    void updateEntity(@MappingTarget Airline  airline, airlineUpdateRequest request);



}
