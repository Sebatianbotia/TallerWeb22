package com.example.airline.repositories;

import com.example.airline.AbstractRepositoryPSQL;
import com.example.airline.entities.Booking;
import com.example.airline.entities.Passenger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookingRepositoryTest extends AbstractRepositoryPSQL {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    PassengerRepository passengerRepository;

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

}