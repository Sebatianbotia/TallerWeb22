package com.example.airline.repositories;

import com.example.airline.entities.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findFlightByByAirlinebyNameIgnoreCase(String airlineName, Pageable pageable);

    @Query("""
    select f from Flight f where f.originAirport.code = :Origen and f.destinationAirport.code = :Destino
""")
    Page<Flight> findFlightByOriginAndDestinationFlighs(@Param("Origen") String codeOrigen,
                                                        @Param("Destino") String codeDestino, Pageable pageable
    );

    // Revisar la segunda consulta
}
