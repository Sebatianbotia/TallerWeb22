package com.example.airline.repositories;

import com.example.airline.entities.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirlineRepository extends JpaRepository<Airline, Long> {
Airline findAirlineByName(String name);
Airline findAirlineById(Long id);
Airline findAirlineByCode(String code);
}
