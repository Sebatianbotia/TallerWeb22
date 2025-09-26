package com.example.airline.repositories;

import com.example.airline.entities.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AirlineRepository extends JpaRepository<Airline, Long> {
Optional<Airline> findAirlineByCodeIgnoreCase(String code);

    Optional<Airline> findAirlineById(long id);
}
