package com.example.airline.Services.Mappers;

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


    public static PassengerProfile toEntity(Passenger passenger, PassengerProfileDTO.passengerProfileCreateRequest createRequest) {
        PassengerProfile passengerProfile = PassengerProfile.builder().phone(createRequest.phoneNumber()).countryCode(createRequest.countryCode()).passenger(passenger).build();
        return passengerProfile;
    }

    public static PassengerProfile toEntity(PassengerProfileDTO.passengerProfileResponse createRequest) {
        PassengerProfile passengerProfile = PassengerProfile.builder().phone(createRequest.phoneNumber()).countryCode(createRequest.countryCode()).build();
        return passengerProfile;
    }

    public static void path(PassengerProfile profile,PassengerProfileDTO.passengerProfileUpdateRequest updateRequest) {
        if (updateRequest.phoneNumber() != null){profile.setPhone(updateRequest.phoneNumber());}
        if (updateRequest.countryCode() != null){profile.setCountryCode(updateRequest.countryCode());}
    }

    public static PassengerProfileDTO.passengerProfileResponse toDTO(PassengerProfile profile) {
        return new PassengerProfileDTO.passengerProfileResponse(profile.getId(), profile.getPhone(), profile.getCountryCode());
    }
}
