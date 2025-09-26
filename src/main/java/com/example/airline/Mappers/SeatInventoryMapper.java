package com.example.airline.Mappers;

import com.example.airline.DTO.SeatInvetoryDTO;
import com.example.airline.entities.Flight;
import com.example.airline.entities.SeatInventory;
import com.example.airline.repositories.FlightRepository;
import com.example.airline.repositories.SeatInventoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class SeatInventoryMapper {
    private SeatInventoryRepository seatInventoryRepository;
    private FlightRepository flightRepository;

    public SeatInventory toEntity(SeatInvetoryDTO.seatInventoryCreateRequest createRequest){
        Flight foundFlight = flightRepository.findById(createRequest.flightId())
                .orElseThrow(() -> new EntityNotFoundException("Vuelo con el ID: " + createRequest.flightId() + "no encontrado"));

        SeatInventory seatInventory = new SeatInventory().builder().availableSeats(createRequest.availableSeats()).totalSeats(createRequest.totalSeats()).cabin(createRequest.cabin()).flight(foundFlight).build();
        return seatInventoryRepository.save(seatInventory);
    }

    public boolean updateSeatInventory(SeatInvetoryDTO.seatInventoryUpdateRequest updateRequest){
        Flight foundFlight = flightRepository.findById(updateRequest.flightID())
                .orElseThrow(() -> new EntityNotFoundException("El vuelo con el ID: " + updateRequest.flightID() + "no fue encontrado"));
        // Continuar, ahora se evalua si cada parametro que entra es diferente a null, si lo es se edita esa informacion en el objeto
    }

}
