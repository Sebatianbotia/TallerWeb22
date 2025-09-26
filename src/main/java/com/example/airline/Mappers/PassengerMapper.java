package com.example.airline.Mappers;

import com.example.airline.DTO.PassengerDTO;
import com.example.airline.entities.Passenger;
import com.example.airline.repositories.PassengerRepository;
import jakarta.persistence.EntityNotFoundException;

public class PassengerMapper {
    PassengerRepository passengerRepository;
    public Passenger toEntity(PassengerDTO.passengerCreateRequest request) {
        if (request == null)
            return null;

        Passenger passenger = Passenger.builder().fullName(request.fullName()).email(request.email()).build();
        return passenger;
    }
    public Passenger updateRequest(PassengerDTO.passengerUpdateRequest request) {
        if (request == null)
            return null;
        Passenger foundPassenger = passengerRepository.findById(request.id()).orElseThrow(()-> new EntityNotFoundException("Passenger not found with id: " + request.id()));
        if (request.email()!=null)
            foundPassenger.setEmail(request.email());
        if (request.fullName()!=null)
            foundPassenger.setFullName(request.fullName());
        return foundPassenger;
    }

    public PassengerDTO.passengerResponse toDTO(long id) {
        Passenger foundPassenger = passengerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Passenger not found"));
        return new PassengerDTO.passengerResponse(foundPassenger.getFullName(), foundPassenger.getEmail());


    }
}
