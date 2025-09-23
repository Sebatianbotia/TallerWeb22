package com.example.airline.repositories;

import com.example.airline.AbstractRepositoryPSQL;
import com.example.airline.entities.Cabin;
import com.example.airline.entities.Flight;
import com.example.airline.entities.SeatInventory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
class SeatInventoryRepositoryTest extends AbstractRepositoryPSQL {
    @Autowired
    private SeatInventoryRepository seatInventoryRepository;

    @Autowired
    private FlightRepository flightRepository;

    private Flight createAndSaveFlight() {
        var flight = Flight.builder().build();
        return flightRepository.save(flight);
    }

    @Test
    @DisplayName("INventario de asientos para cabina especifica")
    void shouldFindSeatInventoryByFlightIdAndCabinType() {
        Flight flight = createAndSaveFlight();
        var inventory = SeatInventory.builder().flight(flight).cabin(Cabin.ECONOMY).totalSeats(100).availableSeats(50).build();
        seatInventoryRepository.save(inventory);

        Optional<SeatInventory> foundInventory = seatInventoryRepository.findSeatInventoryByFlightIdAndCabinType(flight.getId(), Cabin.ECONOMY);

        assertThat(foundInventory).isPresent();
        assertThat(foundInventory.get().getFlight().getId()).isEqualTo(flight.getId());
        assertThat(foundInventory.get().getCabin()).isEqualTo(Cabin.ECONOMY);
    }
    @Test
    @DisplayName("Verifica si Asientos disponibles >= min para ese vuelo y cabina")
    void shouldReturnTrueIfAvailableSeatsGreaterThanMin() {
        Flight flight = createAndSaveFlight();
        var inventory = SeatInventory.builder().flight(flight).cabin(Cabin.BUSINESS).totalSeats(30).availableSeats(20).build();
        seatInventoryRepository.save(inventory);

        Boolean hasEnoughSeats = seatInventoryRepository.existsBySeatIdAndCabinTypeGreaterThanMin(flight.getId(), Cabin.BUSINESS, 10
        );

        assertThat(hasEnoughSeats).isTrue();
    }
}