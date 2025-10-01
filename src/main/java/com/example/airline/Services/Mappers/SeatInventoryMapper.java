package com.example.airline.Services.Mappers;

import com.example.airline.DTO.SeatInventoryDTO;
import com.example.airline.entities.Flight;
import com.example.airline.entities.SeatInventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SeatInventoryMapper {
    SeatInventory toEntity(SeatInventoryDTO.seatInventoryCreateRequest inventoryCreateRequest);

    @Mapping(target = "flightNumber", source = "flight", qualifiedByName = "mapFlightToNumber")
    @Mapping(target = "seatInventoryId", source = "id")
    SeatInventoryDTO.seatInventoryDtoResponse toDTO(SeatInventory entity);

    @Mapping(target = "seatInventoryId", source = "id")
    SeatInventoryDTO.seatInventoryFlightView toFlightView(SeatInventory entity);

    default String mapFlightToNumber(Flight flight) {
        if (flight == null) {
            return null;
        }
        return flight.getNumber();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "flight", ignore = true) // Ignoramos la relación, el servicio la manejaría si fuera necesario.
    void path(SeatInventoryDTO.seatInventoryUpdateRequest updateRequest, @MappingTarget SeatInventory entity);
}
