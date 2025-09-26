package com.example.airline.Mappers;

import com.example.airline.DTO.PassengerProfileDTO;
import com.example.airline.entities.Passenger;
import com.example.airline.entities.PassengerProfile;
import com.example.airline.repositories.PassengerProfileRepository;
import com.example.airline.repositories.PassengerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PassengerProfileMapper {

    @Autowired
    private PassengerRepository passengerRepository;
    private PassengerProfileRepository passengerProfileRepository;

    public PassengerProfile toEntity(PassengerProfileDTO.passengerProfileCreateRequest createRequest) {
        Passenger foundpassenger = passengerRepository.findByid(createRequest.passengerId()).orElseThrow(() -> new EntityNotFoundException("Passenger con id: " + createRequest.passengerId() + " no encontrado"));
        PassengerProfile passengerProfile = PassengerProfile.builder().phone(createRequest.phoneNumber()).countryCode(createRequest.countryCode()).passenger(foundpassenger).build();
        return passengerProfile;
    }

    public PassengerProfile updatePassengerProfile(PassengerProfileDTO.passengerProfileUpdateRequest updateRequest) {
        if (updateRequest == null) {
            return null;
        }

        PassengerProfile foundPassengerProfile = passengerProfileRepository.findById(updateRequest.passengerId()).orElseThrow(() -> new EntityNotFoundException("Passenger id: " + updateRequest.passengerId() + " no encontrado"));
        if (updateRequest.phoneNumber() != null) {
            foundPassengerProfile.setPhone(updateRequest.phoneNumber());
        }

        if (updateRequest.countryCode() != null) {
            foundPassengerProfile.setCountryCode(updateRequest.countryCode());
        }

        return foundPassengerProfile;

    }

    public PassengerProfileDTO.passengerProfileResponse toDTO(Long passengerProfileID) {
        PassengerProfile foundPassengerProfile = passengerProfileRepository.findById(passengerProfileID).orElseThrow(() -> new EntityNotFoundException("Passenger id: " + passengerProfileID() + " no encontrado"));
        return new PassengerProfileDTO.passengerProfileResponse(foundPassengerProfile.getId(), foundPassengerProfile.getPhone(), foundPassengerProfile.getCountryCode());
    }
}
