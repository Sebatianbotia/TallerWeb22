package com.example.airline.services;

import com.example.airline.DTO.PassengerDTO;
import com.example.airline.entities.Passenger;

import java.util.List;

public interface PassengerService {

    PassengerDTO.passengerResponse create(PassengerDTO.passengerCreateRequest createRequest);

    PassengerDTO.passengerResponse update(Long id, PassengerDTO.passengerUpdateRequest updateRequest);

    void delete(Long id);

    List<PassengerDTO.passengerResponse> findAll();

    Passenger getObject(Long id);

    PassengerDTO.passengerResponse get(Long passengerId);
}
