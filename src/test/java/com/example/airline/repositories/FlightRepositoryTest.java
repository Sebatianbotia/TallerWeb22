package com.example.airline.repositories;
import com.example.airline.AbstractRepositoryPSQL;
import com.example.airline.entities.Airline;
import com.example.airline.entities.Airport;
import com.example.airline.entities.Flight;
import com.example.airline.entities.Tag;
import com.example.airline.repositories.AirlineRepository;
import com.example.airline.repositories.AirportRepository;
import com.example.airline.repositories.FlightRepository;
import com.example.airline.repositories.TagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FlightRepositoryTest extends AbstractRepositoryPSQL {
    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private TagRepository tagRepository;

    private Airline createAirline(String name, String code) {
        Airline airline = Airline.builder().name(name).code(code).build();
        return airlineRepository.save(airline);
    }

    private Airport createAirport(String code, String name, String city) {
        Airport airport = Airport.builder().code(code).name(name).city(city).build();
        return airportRepository.save(airport);
    }
    @Test
    @DisplayName("Encontrar vuelo por nombre de aerolinea ignorecase")
    void shouldFindFlightByAirlineNameIgnoringCase() {
        Airline airline = createAirline("American Airlines", "AA");
        Flight flight = Flight.builder().airline(airline).build();
        flightRepository.save(flight);
        Pageable pageable = PageRequest.of(0, 10);

        List<Flight> foundFlights = flightRepository.findFlightByAirline_NameIgnoreCase("american airlines", pageable);

        assertThat(foundFlights).isNotEmpty();
        assertThat(foundFlights.size()).isEqualTo(1);
        assertThat(foundFlights.getFirst().getAirline().getName()).isEqualTo("American Airlines");
    }

    @Test
    @DisplayName("encontar por origen, destino y rango de fechas")
    void shouldFindFlightByOriginAndDestinationAndDateRange() {
        Airport origin = createAirport("S12", "smPort", "Bquilla");
        Airport destination = createAirport("S13", "BmanPort", "Bucaramanga");
        OffsetDateTime departureTime = OffsetDateTime.now();

        Flight flight = Flight.builder()
                .originAirport(origin)
                .destinationAirport(destination)
                .departureTime(departureTime)
                .build();
        flightRepository.save(flight);

        OffsetDateTime dateRangeStart = departureTime.minusMinutes(1);
        OffsetDateTime dateRangeEnd = departureTime.plusMinutes(1);

        Pageable pageable = PageRequest.of(0, 10);

        Page<Flight> foundFlights = flightRepository.findFlightByOriginAndDestinationFlighs(
                "S12", "S13", dateRangeStart, dateRangeEnd, pageable);

        assertThat(foundFlights).isNotEmpty();
        assertThat(foundFlights.getTotalElements()).isEqualTo(1);
        assertThat(foundFlights.getContent().getFirst().getOriginAirport().getCode()).isEqualTo("S12");
    }


    @Test
    @DisplayName("Filtra por origen/destino(op), y ventana de salida precargando airline...")
    void shouldFindFlightsWithOptionalOriginAndDestinationAndEagerlyLoadAssociations() {
        Airline airline = createAirline("United", "UA");
        Airport origin = createAirport("ORD", "ORD Airport", "Chicago");
        Airport destination = createAirport("MIA", "MIA Airport", "Miami");
        Tag tag = tagRepository.save(Tag.builder().name("On-time").build());
        OffsetDateTime departureTime = OffsetDateTime.now();

        Flight flight = Flight.builder()
                .airline(airline)
                .originAirport(origin)
                .destinationAirport(destination)
                .departureTime(departureTime)
                .tags(Set.of(tag))
                .build();
        flightRepository.save(flight);

        OffsetDateTime dateRangeStart = departureTime.minusMinutes(1);
        OffsetDateTime dateRangeEnd = departureTime.plusMinutes(1);

        List<Flight> foundFlightsAllFilters = flightRepository.findFlightsByOptionalOriginAndDestination(
                "ORD", "MIA", dateRangeStart, dateRangeEnd);

        assertThat(foundFlightsAllFilters).isNotEmpty();
        Flight foundFlight = foundFlightsAllFilters.getFirst();
        assertThat(foundFlight.getAirline()).isNotNull();
        assertThat(foundFlight.getOriginAirport()).isNotNull();
        assertThat(foundFlight.getDestinationAirport()).isNotNull();
        assertThat(foundFlight.getTags()).isNotEmpty();
        //caso2 filtros nulos origen y destino
        List<Flight> foundFlightsOptionalFilters = flightRepository.findFlightsByOptionalOriginAndDestination(
                null, null, dateRangeStart, dateRangeEnd);

        assertThat(foundFlightsOptionalFilters).isNotEmpty();
    }


    @Test
    @DisplayName("devuelve vuelos que poseen las tags indicadas")
    void shouldFindFlightsWithSpecificTags() {

        Tag tag1 = tagRepository.save(Tag.builder().name("on-time").build());
        Tag tag2 = tagRepository.save(Tag.builder().name("wifi").build());

        Flight flight1 = Flight.builder().tags(Set.of(tag1, tag2)).build();
        flightRepository.save(flight1);

        Flight flight2 = Flight.builder().tags(Set.of(tag1)).build();//este es para que no lo encuentre, ya que tiene un solo tag
        flightRepository.save(flight2);

        List<String> tagsToFind = List.of("on-time", "wifi");
        List<Flight> foundFlights = flightRepository.findFlightsWithTags(tagsToFind, tagsToFind.size());

        assertThat(foundFlights).isNotEmpty();
        assertThat(foundFlights).hasSize(1);
        assertThat(foundFlights.getFirst().getId()).isEqualTo(flight1.getId());
    }
}