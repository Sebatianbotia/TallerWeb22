package com.example.airline.repositories;

import com.example.airline.AbstractRepositoryPSQL;
import com.example.airline.entities.Booking;
import com.example.airline.entities.BookingItems;
import com.example.airline.entities.Cabin;
import com.example.airline.entities.Flight;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.list;

import java.math.BigDecimal;
import java.util.List;

class BookingItemsRepositoryTest extends AbstractRepositoryPSQL {
    @Autowired
    BookingItemsRepository repository;
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    FlightRepository flightRepository;

    @Test
    @DisplayName("BookingItem: buscar las maletas por id de pasajero y ordenarlas")
    void shouldFindBookingItemsByBookingIdAndBeOrdered() {
        // Creamos los objeto de prueba
        var booking = Booking.builder().build();
        Booking savedBooking = bookingRepository.save(booking);

        var bookingItems1 = BookingItems.builder().booking(savedBooking).segmentOrder(4).build();
        var bookingItems2 = BookingItems.builder().booking(savedBooking).segmentOrder(2).build();

        // Se guardan los bookingItems
        repository.saveAll(List.of(bookingItems1, bookingItems2));


        List<BookingItems> byBookingId = repository.findByBookingId(savedBooking.getId());

        // Se comprueba que el objeto no este vacio y que tenga la longitud de los bookings realizados
        assertThat(byBookingId).isNotNull().hasSize(2);

        // Se comprueba el ordenamiento
        assertThat(byBookingId.get(0).getSegmentOrder()).isEqualTo(2);
        assertThat(byBookingId.get(1).getSegmentOrder()).isEqualTo(4);
    }

    @Test
    @DisplayName("BookingItem: Suma de todos los items cargados")
    void shouldSumaDeTodosLosItemsCargados() {
        // Datos de prueba
        var booking = Booking.builder().build(); // No necesitas id(1L), JPA lo genera
        Booking savedBooking = bookingRepository.save(booking);

        var bookingItems1 = BookingItems.builder()
                .booking(savedBooking)
                .price(new BigDecimal("100"))
                .build();
        var bookingItems2 = BookingItems.builder()
                .booking(savedBooking)
                .price(new BigDecimal("200"))
                .build();

        repository.saveAll(List.of(bookingItems1, bookingItems2));

        // LLamo al metodo del repositorio
        BigDecimal totalCalculado = repository.totalPriceOfBookingItems(savedBooking.getId());

        // Verificamos la suma
        assertThat(totalCalculado).isEqualTo(new BigDecimal("300"));
    }

    @Test
    @DisplayName("BookingItem: Contar los asientos reservados de un tipo")
    void shouldContarAsientosReservados() {
        var fligh = Flight.builder().build();
        Flight savedFlight = flightRepository.save(fligh);

        var bookingItem1 =  BookingItems.builder().flight(savedFlight).
                cabin(Cabin.BUSINESS).build();
        var bookingItem2 =  BookingItems.builder().flight(savedFlight).
                cabin(Cabin.BUSINESS).build();

        repository.saveAll(List.of(bookingItem1, bookingItem2));

        Long asientosReservados = repository.howManyBookingWasSellByFlightIdAndCabin(savedFlight.getId(),
        Cabin.BUSINESS.toString()
        );

        assertThat(asientosReservados).isNotNull();
        assertThat(asientosReservados).isEqualTo(2);



    }


}