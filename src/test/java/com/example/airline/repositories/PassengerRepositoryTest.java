package com.example.airline.repositories;

import com.example.airline.AbstractRepositoryPSQL;
import com.example.airline.entities.Passenger;
import com.example.airline.entities.PassengerProfile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
class PassengerRepositoryTest extends AbstractRepositoryPSQL {
    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private PassengerProfileRepository passengerProfileRepository;

    @Test
    @DisplayName("BuscarPorEmailIgnorecase")
    void findPassengerByEmailIgnoreCase() {
        var passenger = Passenger.builder().fullName("maicol").email("maicol@123").build();
        passengerRepository.save(passenger);

        Optional<Passenger> findPassenger = passengerRepository.findPassengerByEmailIgnoreCase(passenger.getEmail());

        Assertions.assertThat(findPassenger).isPresent();
        Assertions.assertThat(findPassenger.get()).isEqualTo(passenger);
    }

    @Test
    @DisplayName("D<evuelve el perfil")
    void findPassengerByEmail() {
        var passengerProfile = PassengerProfile.builder().phone("32323232").countryCode("12").build();
        passengerProfileRepository.save(passengerProfile);

        var passenger =  Passenger.builder().fullName("maicol valencia").email("maicol@123.com").profile(passengerProfile).build();
        passengerRepository.save(passenger);

        Optional<Passenger> findPassenger = passengerRepository.findPassengerByEmailIgnoreCase(passenger.getEmail());
        Assertions.assertThat(findPassenger).isPresent();
        Assertions.assertThat(findPassenger.get().getProfile()).isEqualTo(passenger.getProfile());
    }

}