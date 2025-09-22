package com.example.airline.repositories;

import com.example.airline.entities.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findFlightByAirlineByNameIgnoreCase(String airlineName, Pageable pageable);

    @Query("""
    select f from Flight f where f.originAirport.code = :origen and f.destinationAirport.code = :destino
    and f.departureTime between :fechaInicio and :fechaSalida
""")
    Page<Flight> findFlightByOriginAndDestinationFlighs(@Param("origen") String origen,
                                                        @Param("destino") String destino,
                                                        @Param("fechaInicio") OffsetDateTime fechaInicio,
                                                        @Param("fechaSalida") OffsetDateTime fechaSalida , Pageable pageable
    );
    
        @Query("""
        select f from Flight f 
        left join fetch f.airline 
        left join fetch f.originAirport 
        left join fetch f.destinationAirport 
        left join fetch f.tags 
        where (:origen is null or f.originAirport.code = :origen)
          and (:destino is null or f.destinationAirport.code = :destino)
          and f.departureTime between :fechaInicio and :fechaSalida
    """)
        List<Flight> findFlightsByOptionalOriginAndDestination(
                @Param("origen") String origen,
                @Param("destino") String destino,
                @Param("fechaInicio") OffsetDateTime fechaInicio,
                @Param("fechaSalida") OffsetDateTime fechaSalida
        );


        @Query(value = """
select f.* from Flights f join AirportTag at on f.id = at.FlightId 
join Tags t on at.TagId = t.id
group by f.id
    where t.name in (:tags) 
having count(distinct t.id) = :tamañoTags
""", nativeQuery = true)
        List<Flight> findFlightsWithTags(@Param("tags") Collection<String> tags,
                                         @Param("tamañoTags") int sizeTags);
    }

