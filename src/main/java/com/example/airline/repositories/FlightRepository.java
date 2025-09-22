package com.example.airline.repositories;

import com.example.airline.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    Flight findFlightsByid(long id);
}
