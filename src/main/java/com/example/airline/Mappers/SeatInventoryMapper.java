package com.example.airline.Mappers;

import com.example.airline.DTO.SeatInventoryDTO;
import com.example.airline.entities.Flight;
import com.example.airline.entities.SeatInventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SeatInventoryMapper {

    SeatInventory toEntity(SeatInventoryDTO.seatInventoryCreateRequest inventoryCreateRequest);

    @Mapping(target = "flightNumber", source = "flight")
    @Mapping(target = "seatInventoryId", source = "id")
    SeatInventoryDTO.seatInventoryDtoResponse toDTO(SeatInventory entity);

    default String mapFlightToNumber(Flight flight) {
        if (flight == null) {
            return null;
        }
        return flight.getNumber();
    }

    @Mapping(target = "seatInventoryId", source = "id") // Lo que va a ver el response de flight de seatInventory
    SeatInventoryDTO.seatInventoryFlightView toFlightView(SeatInventory entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "flight", ignore = true) // El servicio se encarga de flight
    void updateEntity(SeatInventoryDTO.seatInventoryUpdateRequest updateRequest, @MappingTarget SeatInventory entity);
}
