package com.example.airline.Mappers;

import com.example.airline.DTO.FlightDto;

import com.example.airline.entities.*;
import org.mapstruct.*;


@Mapper(componentModel = "spring",
        uses = {AirlineMapper.class,
                AirportMapper.class, SeatInventoryMapper.class, TagMapper.class,}
)
public interface FlightMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "airline", ignore = true)
    @Mapping(target = "originAirport", ignore = true)
    @Mapping(target = "destinationAirport", ignore = true)
    @Mapping(target = "seatInventories", ignore = true)
    @Mapping(target = "bookingItems", ignore = true)
    @Mapping(target = "tags", ignore = true)
    Flight toEntity(FlightDto.flightCreateRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "airline", ignore = true)
    @Mapping(target = "originAirport", ignore = true)
    @Mapping(target = "destinationAirport", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "seatInventories", ignore = true)
    @Mapping(target = "bookingItems", ignore = true)
    void patch(FlightDto.flightUpdateRequest updateRequest, @MappingTarget Flight flight);

    @Mapping(target = "flightId", source = "id")
    FlightDto.flightResponse toDTO(Flight entity);

    @Mapping(target = "flightId", source = "id")
    FlightDto.flightAirportView toAirportView(Flight entity); //Lo usa mapstruc automaticamenre en airport

    @Mapping(target = "flightId", source = "id")
    FlightDto.flightAirlineView toAirlineView(Flight entity); //Lo usa mapstruc automaticamenre en airline


}