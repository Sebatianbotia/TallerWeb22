package com.example.airline.repositories;

import com.example.airline.AbstractRepositoryPSQL;
import com.example.airline.entities.Booking;
import com.example.airline.entities.BookingItem;
import com.example.airline.entities.Flight;
import com.example.airline.entities.Passenger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class BookingRepositoryTest extends AbstractRepositoryPSQL {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private BookingItemsRepository bookingItemsRepository;


    @Test
    @DisplayName("Booking: Reservas de un pasajero por email")
    void shouldFindBookingByEmail() {
        // creando un dato de prueba
        var passenger = Passenger.builder().email("Test@gmail.com").build();
        Passenger savedPassenger = passengerRepository.save(passenger);

        var booking1 = Booking.builder().passenger(savedPassenger).createdAt(OffsetDateTime.now().minusDays(3)).build();
        var booking2 = Booking.builder().passenger(savedPassenger).createdAt(OffsetDateTime.now().minusDays(2)).build();
        var booking3 = Booking.builder().passenger(savedPassenger).createdAt(OffsetDateTime.now().minusDays(1)).build();
        bookingRepository.saveAll(List.of(booking1, booking2, booking3));

        Pageable pageable = PageRequest.of(0, 2);

        Page<Booking> pageOfUserBookings = bookingRepository.pageOfReservationsAboutPassenger(
                "Test@gmail.com",
                pageable
        );

        // Revisando condiciones
        assertThat(pageOfUserBookings).isNotNull();
        assertThat(pageOfUserBookings.getTotalElements()).isEqualTo(3);
        assertThat(pageOfUserBookings.getNumberOfElements()).isEqualTo(2);

        // se verifica que los datos esten ordenados por fecha
        assertThat(pageOfUserBookings.getContent().get(0).getCreatedAt())
                .isEqualTo(booking3.getCreatedAt());
        assertThat(pageOfUserBookings.getContent().get(1).getCreatedAt())
                .isEqualTo(booking2.getCreatedAt());

    }

    @Test
    @DisplayName("Booking: Trae reserva por ID")
    void shouldFindBookingById() {

        // 1. Arrange: Create and save all related entities

        var flight = Flight.builder().build();
        Flight saveFlight = flightRepository.save(flight);

        var passenger = Passenger.builder().fullName("John Doe").email("john.doe@test.com").build();
        Passenger savePassenger = passengerRepository.save(passenger);

        var bookingItem = BookingItem.builder().flight(flight).build();
        BookingItem saveBookingItem = bookingItemsRepository.save(bookingItem);

        var booking = Booking.builder().passenger(savePassenger).items(
                List.of(saveBookingItem)
        ).build();
        Booking saveBooking = bookingRepository.save(booking);



        Optional<Booking> foundBooking = bookingRepository.findBookingById(saveBooking.getId());

        assertThat(foundBooking).isPresent();
        assertThat(foundBooking.get().getPassenger()).isNotNull();
        assertThat(foundBooking.get().getItems()).isNotEmpty();
        assertThat(foundBooking.get().getItems().getFirst().getFlight()).isNotNull();

    }
}