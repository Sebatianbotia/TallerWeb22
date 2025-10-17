package com.example.airline.services;

import com.example.airline.DTO.PassengerDTO;
import com.example.airline.entities.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PassengerService {

    PassengerDTO.passengerResponse create(PassengerDTO.passengerCreateRequest createRequest);

    PassengerDTO.passengerResponse update(Long id, PassengerDTO.passengerUpdateRequest updateRequest);

    void delete(Long id);

    Page<PassengerDTO.passengerResponse> list(Pageable pageable);

    Passenger getObject(Long id);

    PassengerDTO.passengerResponse get(Long passengerId);
}
