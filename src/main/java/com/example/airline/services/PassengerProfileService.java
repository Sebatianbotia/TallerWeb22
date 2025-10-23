package com.example.airline.services;

import com.example.airline.DTO.PassengerProfileDTO;
import com.example.airline.entities.PassengerProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PassengerProfileService {

    PassengerProfileDTO.passengerProfileResponse create(PassengerProfileDTO.passengerProfileCreateRequest createRequest);

    PassengerProfile createObject(PassengerProfileDTO.passengerProfileCreateRequest createRequest);
    PassengerProfileDTO.passengerProfileResponse get(Long id);
    PassengerProfile getObject(Long id);
    PassengerProfileDTO.passengerProfileResponse update(Long id, PassengerProfileDTO.passengerProfileUpdateRequest updateRequest);
    PassengerProfileDTO.passengerProfileResponse update(PassengerProfile profile, PassengerProfileDTO.passengerProfileUpdateRequest updateRequest);
    Page<PassengerProfileDTO.passengerProfileResponse> list(Pageable pageable);
    void delete(Long id);
}
