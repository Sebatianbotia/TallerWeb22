package com.example.airline.repositories;

import com.example.airline.entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepository extends JpaRepository<Airport, Long> {
    Airport findAirportByName(String name);
    Airport findAirportById(Long id);
}
