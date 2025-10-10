package com.example.airline.Mappers;

import com.example.airline.DTO.AirportDTO;
import com.example.airline.DTO.AirportDTO.AirportCreateRequest;
import com.example.airline.DTO.AirportDTO.AirportFlightView;
import com.example.airline.DTO.AirportDTO.AirportResponse;
import com.example.airline.DTO.AirportDTO.AirportUpdateRequest;
import com.example.airline.entities.Airport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",
        uses = {FlightMapper.class})
public interface AirportMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "flightsOrigin", ignore = true)
    @Mapping(target = "flightsDestination", ignore = true)
    Airport toEntity(AirportCreateRequest request);

    AirportResponse toDTO(Airport entity);

    @Mapping(target = "flightsOrigin", ignore = true)
    @Mapping(target = "flightsDestination", ignore = true)
    AirportResponse toDTOSimple(Airport entity);

    AirportFlightView toFlightView(Airport airport);//lo uda mapstruct automaticamente en flight

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "flightsOrigin", ignore = true)
    @Mapping(target = "flightsDestination", ignore = true)
    void updateEntity(AirportUpdateRequest request, @MappingTarget Airport entity);
}