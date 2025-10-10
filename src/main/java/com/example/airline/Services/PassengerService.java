package com.example.airline.Services;

import com.example.airline.DTO.PassengerDTO;
import com.example.airline.entities.Passenger;

import java.util.List;

public interface PassengerService {

    PassengerDTO.passengerResponse create(PassengerDTO.passengerCreateRequest createRequest);

    PassengerDTO.passengerResponse update(Long id, PassengerDTO.passengerUpdateRequest updateRequest);

    void delete(Long id);

    PassengerDTO.passengerResponse find(Long id);

    List<PassengerDTO.passengerResponse> findAll();

    Passenger get(Long id);
}
