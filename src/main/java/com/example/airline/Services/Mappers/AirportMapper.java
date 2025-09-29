package com.example.airline.Services.Mappers;

import com.example.airline.DTO.AirportDTO;
import com.example.airline.entities.Airport;
import com.example.airline.repositories.AirportRepository;

public class AirportMapper {
    private AirportRepository airportRepository;
    public static Airport toEntity(AirportDTO.AirportCreateRequest createRequest) {
        return Airport.builder()
                .name(createRequest.name())
                .code(createRequest.code())
                .city(createRequest.city())
                .build();
    }

    public static void updateEntity(Airport airport, AirportDTO.AirportUpdateRequest updateRequest) {
        if (updateRequest == null) return;
        if (updateRequest.name() != null) airport.setName( updateRequest.name());
        if (updateRequest.code() != null) airport.setCode( updateRequest.code());
        if (updateRequest.city() != null) airport.setCity( updateRequest.city());
    }

    public static AirportDTO.AirportResponse toDTO(Airport airport) {
        return new AirportDTO.AirportResponse(airport.getId(), airport.getName(), airport.getCode(), airport.getCity());

    }
}
