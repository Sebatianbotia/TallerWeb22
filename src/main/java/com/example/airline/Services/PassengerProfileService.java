package com.example.airline.Services;

import com.example.airline.DTO.PassengerProfileDTO;
import com.example.airline.entities.Passenger;
import com.example.airline.entities.PassengerProfile;

import java.util.List;

public interface PassengerProfileService {

    PassengerProfileDTO.passengerProfileResponse create(PassengerProfileDTO.passengerProfileCreateRequest createRequest);

    PassengerProfile createObject(PassengerProfileDTO.passengerProfileCreateRequest createRequest);
    PassengerProfileDTO.passengerProfileResponse findById(Long id);
    PassengerProfile get(Long id);
    PassengerProfileDTO.passengerProfileResponse update(Long id, PassengerProfileDTO.passengerProfileUpdateRequest updateRequest);
    PassengerProfileDTO.passengerProfileResponse update(PassengerProfile profile, PassengerProfileDTO.passengerProfileUpdateRequest updateRequest);
    List<PassengerProfileDTO.passengerProfileResponse> findAll();
    void delete(Long id);
}
