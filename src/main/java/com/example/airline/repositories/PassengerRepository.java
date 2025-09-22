package com.example.airline.repositories;

import com.example.airline.entities.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
Passenger findPassengerById(Long id);
Passenger findPassengerByName(String name);
Passenger FindPassengerByEmailIgnoreCase(String email);

}
