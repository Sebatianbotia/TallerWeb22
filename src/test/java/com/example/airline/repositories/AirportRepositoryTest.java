package com.example.airline.repositories;

import com.example.airline.AbstractRepositoryPSQL;
import com.example.airline.entities.Airport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AirportRepositoryTest extends AbstractRepositoryPSQL {
@Autowired
    AirportRepository airportRepository;

@Test
@DisplayName("Busca aeropuerto por codigo")
    void shouldFindAirportByCode() {
        Airport airport = new Airport();
        airport.setCode("1L");
        airport.setName("Aeropuerto whose");
        airportRepository.save(airport);

        Optional<Airport> foundAirport = airportRepository.findByCode("1L");

        assertThat(foundAirport).isPresent();
        assertThat(foundAirport.get().getCode()).isEqualTo("1L");
    }

}