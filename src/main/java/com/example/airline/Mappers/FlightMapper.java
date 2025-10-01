package com.example.airline.Mappers;

import com.example.airline.DTO.FlightDto;
import com.example.airline.entities.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {AirlineMapper.class, AirportMapper.class, TagMapper.class, com.example.airline.Services.Mappers.SeatInventoryMapper.class})
public interface FlightMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "airline", ignore = true)
    @Mapping(target = "originAirport", ignore = true)
    @Mapping(target = "destinationAirport", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "seatInventories", ignore = true)
    Flight toEntity(FlightDto.flightCreateRequest createRequest);
    @Mapping(target = "flightId", source = "id")
    @Mapping(target = "number", source = "flightNumber") // Asumo que el campo de la entidad es 'flightNumber'
    @Mapping(target = "originAirport", source = "originAirport")
    @Mapping(target = "destinationAirport", source = "destinationAirport")
    @Mapping(target = "airline", source = "airline")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "seatInventories", source = "seatInventories")
    FlightDto.flightResponse toDTO(Flight entity);

    @Mapping(target = "flightId", source = "id")
    @Mapping(target = "number", source = "flightNumber")
    @Mapping(target = "airline", source = "airline")
    @Mapping(target = "tags", source = "tags")
    FlightDto.flightAirportView toAirportView(Flight entity);

    @Mapping(target = "flightId", source = "id")
    @Mapping(target = "number", source = "flightNumber")
    @Mapping(target = "tags", source = "tags")
    FlightDto.flightAirlineView toAirlineView(Flight entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "airline", ignore = true)
    @Mapping(target = "originAirport", ignore = true)
    @Mapping(target = "destinationAirport", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "seatInventories", ignore = true)
    void path(FlightDto.flightUpdateRequest updateRequest, @MappingTarget Flight flight);
}