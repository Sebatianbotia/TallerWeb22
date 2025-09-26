package com.example.airline.Mappers;

import com.example.airline.DTO.FlightDto;
import com.example.airline.DTO.SeatInvetoryDTO;
import com.example.airline.entities.Flight;
import com.example.airline.entities.SeatInventory;
import com.example.airline.repositories.FlightRepository;
import com.example.airline.repositories.SeatInventoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SeatInventoryMapper {
    @Autowired
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
       if (updateRequest.flightId()!= null){
           Flight foundFlight = flightRepository.findById(updateRequest.flightId())
                   .orElseThrow(() -> new EntityNotFoundException("Vuelo con ID: " + updateRequest.flightId() + "no encontrado"));
           foundSeatInventory.setFlight(foundFlight);
       }
       return foundSeatInventory;

    }
// Falta el toDTO de flight en el ultimo parametro
    public SeatInvetoryDTO.seatInventoryDtoResponse toDTO(Long airlineId){
        SeatInventory foundSeatInventory = seatInventoryRepository.findById(airlineId).orElseThrow(() -> new EntityNotFoundException("Seat inventory con id: " + airlineId + " no encontrado"));
        return new SeatInvetoryDTO.seatInventoryDtoResponse(foundSeatInventory.getId(), foundSeatInventory.getTotalSeats(), foundSeatInventory.getAvailableSeats(), foundSeatInventory.getCabin(), )
    }

    private FlightDto.flightResponse getFlightResponse(Flight flight){
        return new FlightDto.flightResponse(flight.getId(), flight.getNumber(), flight.getArrivalTime(), flight.getDepartureTime(), flight.getAirline().getId(), flight.getOriginAirport().getId(),
                flight)
    }


}
