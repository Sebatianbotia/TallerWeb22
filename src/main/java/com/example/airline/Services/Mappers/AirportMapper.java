package com.example.airline.Services.Mappers;

import com.example.airline.DTO.AirportDTO;
import com.example.airline.DTO.AirportDTO.*;
import com.example.airline.entities.Airport;
import com.example.airline.repositories.AirportRepository;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AirportMapper {
    Airport toEntity(AirportCreateRequest request);
    AirportResponse toDTO(Airport entity);
    AirportFlightView toFlightView(AirportResponse entity);
    void  updateEntity(AirportUpdateRequest request, @MappingTarget Airport entity);
}
