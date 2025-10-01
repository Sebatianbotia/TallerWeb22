package com.example.airline.Mappers;

import com.example.airline.DTO.PassengerDTO;
import com.example.airline.entities.Passenger;
import org.springframework.stereotype.Component;

@Component
public class PassengerMapper {

    public static Passenger toEntity(PassengerDTO.passengerCreateRequest request) {
       return Passenger.builder().fullName(request.fullName()).email(request.email()).build();
    }

    public static void path(Passenger passenger, PassengerDTO.passengerUpdateRequest request) {
        if (request.email()!=null)
            passenger.setEmail(request.email());
        if (request.fullName()!=null)
            passenger.setFullName(request.fullName());
    }

    public static PassengerDTO.passengerResponse toDTO(Passenger passenger) {
        PassengerDTO.passengerProfileView profileDTO = null;
        if (passenger.getProfile() != null){
            profileDTO = new PassengerDTO.passengerProfileView(passenger.getProfile().getPhone(), passenger.getProfile().getCountryCode());
        }
        return new PassengerDTO.passengerResponse(passenger.getId(), passenger.getFullName(), passenger.getEmail(), profileDTO);

    }
}
