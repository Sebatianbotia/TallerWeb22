package com.example.airline.Mappers;

import com.example.airline.DTO.AirlaneDTO;
import com.example.airline.DTO.AirlaneDTO.airlineCreateRequest;
import com.example.airline.DTO.AirlaneDTO.airlineResponse;
import com.example.airline.DTO.AirlaneDTO.airlineUpdateRequest;
import com.example.airline.entities.Airline;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AirlineMapper {

    @Mapping(target = "id", ignore = true)
    Airline toEntity(airlineCreateRequest request);

    airlineResponse toDTO(Airline airline);

    AirlaneDTO.airlineFlightView toFlightView(Airline airline);

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Airline  airline, airlineUpdateRequest request);



}
