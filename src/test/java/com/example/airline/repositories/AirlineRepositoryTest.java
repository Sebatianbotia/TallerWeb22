package com.example.airline.repositories;
import com.example.airline.AbstractRepositoryPSQL;
import com.example.airline.entities.Airline;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

public class AirlineRepositoryTest extends AbstractRepositoryPSQL {
    @Autowired
    private AirlineRepository airlineRepository;

    @Test
    @DisplayName("Buscar por codigo existente zzz")
    void shouldfindAirlineByCodeIgnoreCase() {
        //Creamos y guardamos una aerolínea
        Airline airline = new Airline();
        airline.setCode("AA");
        airline.setName("American Airlines");
        airlineRepository.save(airline);

        //Buscamos la aerolínea por su código en minúsculas
        Optional<Airline> foundAirline = airlineRepository.findAirlineByCodeIgnoreCase("aa");

        //Verificamos que la aerolínea fue encontrada y sus datos coinciden
        assertThat(foundAirline).isPresent();
        assertThat(foundAirline.get().getCode()).isEqualTo("AA");
    }

    @Test
    @DisplayName("buscar por codigo inexistente")
    void shouldFindAirlineByInexistentCode() {
        Optional<Airline> notFoundAirline = airlineRepository.findAirlineByCodeIgnoreCase("bb");
        assertThat(notFoundAirline).isEmpty();
    }
}