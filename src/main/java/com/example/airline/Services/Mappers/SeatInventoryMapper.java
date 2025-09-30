package com.example.airline.Services.Mappers;

import com.example.airline.DTO.SeatInventoryDTO;
import com.example.airline.entities.Cabin;
import com.example.airline.entities.Flight;
import com.example.airline.entities.SeatInventory;
import org.springframework.stereotype.Component;


@Component
public class SeatInventoryMapper {

    public static SeatInventory toEntity(SeatInventoryDTO.seatInventoryCreateRequest createRequest){
       return SeatInventory.builder().totalSeats(createRequest.totalSeats()).availableSeats(createRequest.availableSeats()).cabin(createRequest.cabin()).build();
    }

    public static void path(SeatInventory entity,SeatInventoryDTO.seatInventoryUpdateRequest updateRequest){
        if (updateRequest.totalSeats() != null){
            entity.setTotalSeats(updateRequest.totalSeats());
        }
        if (updateRequest.availableSeats() != null){
            entity.setAvailableSeats(updateRequest.availableSeats());
        }
        if (updateRequest.cabin() != null){
            entity.setCabin(updateRequest.cabin());
        }

    }

    public static SeatInventoryDTO.seatInventoryDtoResponse toDTO(SeatInventory entity){
        String flightNumber = null;
        if (entity.getFlight() != null){
            flightNumber = entity.getFlight().getNumber();
        }
        return new SeatInventoryDTO.seatInventoryDtoResponse(entity.getId(), entity.getTotalSeats(), entity.getAvailableSeats(), entity.getCabin(),flightNumber);

    }

    public static SeatInventoryDTO.seatInventoryFlightView seatInventoryFlightView(SeatInventory entity){
        return new SeatInventoryDTO.seatInventoryFlightView(entity.getId(), entity.getTotalSeats(), entity.getAvailableSeats(), entity.getCabin());
    }


}
