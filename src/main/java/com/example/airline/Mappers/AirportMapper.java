package com.example.airline.Mappers;

import com.example.airline.DTO.AirportDTO.AirportCreateRequest;
import com.example.airline.DTO.AirportDTO.AirportFlightView;
import com.example.airline.DTO.AirportDTO.AirportResponse;
import com.example.airline.DTO.AirportDTO.AirportUpdateRequest;
import com.example.airline.entities.Airport;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AirportMapper {
    Airport toEntity(AirportCreateRequest request);
    AirportResponse toDTO(Airport entity);
    AirportFlightView toFlightView(AirportResponse entity);
    void  updateEntity(AirportUpdateRequest request, @MappingTarget Airport entity);
}
