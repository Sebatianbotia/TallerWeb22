package com.example.airline.Mappers;

import com.example.airline.DTO.SeatInvetoryDTO;
import com.example.airline.entities.Flight;
import com.example.airline.entities.SeatInventory;
import com.example.airline.repositories.FlightRepository;
import com.example.airline.repositories.SeatInventoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class SeatInventoryMapper {
    private SeatInventoryRepository seatInventoryRepository;
    private FlightRepository flightRepository;

    public SeatInventory toEntity(SeatInvetoryDTO.seatInventoryCreateRequest createRequest){
        Flight foundFlight = flightRepository.findById(createRequest.flightId())
                .orElseThrow(() -> new EntityNotFoundException("Vuelo con el ID: " + createRequest.flightId() + "no encontrado"));

        return SeatInventory.builder().availableSeats(createRequest.availableSeats()).totalSeats(createRequest.totalSeats()).cabin(createRequest.cabin()).flight(foundFlight).build();
    }

    public SeatInventory updateSeatInventory(SeatInvetoryDTO.seatInventoryUpdateRequest updateRequest){
        SeatInventory foundSeatInventory = seatInventoryRepository.findById(updateRequest.seatInventoryId())
                .orElseThrow(() -> new EntityNotFoundException("Seat inventoru con ID: " + updateRequest.seatInventoryId() + "no encontrado"));

       if (updateRequest.availableSeats()!= null){
           foundSeatInventory.setAvailableSeats(updateRequest.availableSeats());
       }
       if (updateRequest.totalSeats()!= null){
           foundSeatInventory.setTotalSeats(updateRequest.totalSeats());
       }
       if (updateRequest.cabin()!= null){
           foundSeatInventory.setCabin(updateRequest.cabin());
       }
       if (updateRequest.flightID()!= null){
           Flight foundFlight = flightRepository.findById(updateRequest.flightID())
                   .orElseThrow(() -> new EntityNotFoundException("Vuelo con ID: " + updateRequest.flightID() + "no encontrado"));
           foundSeatInventory.setFlight(foundFlight);
       }
       return seatInventoryRepository.save(foundSeatInventory);

    }

    public static SeatInvetoryDTO.seatInventoryDtoResponse seatInventoryResponse(SeatInventory seatInventory){
        if (seatInventory.getAvailableSeats() == null){
            return null;
        }
        return new SeatInvetoryDTO.seatInventoryDtoResponse(seatInventory.getId(),
                seatInventory.getTotalSeats(), seatInventory.getAvailableSeats(), seatInventory.getCabin(),
                seatInventory.getFlight().getId());
    }

}
