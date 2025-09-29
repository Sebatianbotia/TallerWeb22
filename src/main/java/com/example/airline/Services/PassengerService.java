package com.example.airline.Services;

import com.example.airline.DTO.PassengerDTO;

import java.util.List;

public interface PassengerService {
    PassengerDTO.passengerResponse createPassenger(PassengerDTO.passengerCreateRequest createRequest);
    PassengerDTO.passengerResponse updatePassenger(Long id, PassengerDTO.passengerUpdateRequest updateRequest);
    void deletePassenger(Long id);
    PassengerDTO.passengerResponse findPassengerById(Long id);
    List<PassengerDTO.passengerResponse> findAllPassengers();
}
