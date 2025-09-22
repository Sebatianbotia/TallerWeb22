package com.example.airline.repositories;

import com.example.airline.entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirportRepository extends JpaRepository<Airport, Long> {

Optional<Airport> findByCode(String code);
}
