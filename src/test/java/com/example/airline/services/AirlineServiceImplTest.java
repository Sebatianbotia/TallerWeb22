package com.example.airline.services;

import com.example.airline.DTO.AirlaneDTO.*;
import com.example.airline.Services.AirlineServiceImpl;
import com.example.airline.entities.Airline;
import com.example.airline.repositories.AirlineRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AirlineServiceImplTest {
@Mock
AirlineRepository airlineRepository;

@InjectMocks
AirlineServiceImpl airlineServiceImpl;

    private final Long TEST_ID = 42L;

    @Test
    void shouldCreateAndReturnResponse() {
        var createRequest = new airlineCreateRequest("Aeroda", "232323");

        when(airlineRepository.save(any(Airline.class))).thenAnswer(invocation -> {
            Airline airlineToSave = invocation.getArgument(0);
            airlineToSave.setId(11L);
            return airlineToSave;
        });

        airlineResponse result = airlineServiceImpl.create(createRequest);

        assertNotNull(result);
        assertEquals(11L, result.id());
        assertEquals("Aeroda", result.name());
        assertEquals("232323", result.code());

    }

    @Test
    void shouldUpdateAndReturnCorrectResponse() {

        Airline existingAirline = Airline.builder()
                .id(TEST_ID)
                .name("Maicol")
                .code("123")
                .flights(Collections.emptyList())
                .build();

        var updateRequest = new airlineUpdateRequest(TEST_ID, "Jose", "456");

        when(airlineRepository.findById(TEST_ID)).thenReturn(Optional.of(existingAirline));

        airlineResponse actualResponse = airlineServiceImpl.update(TEST_ID, updateRequest);

        assertEquals("Jose", existingAirline.getName(), "El nombre de la entidad debe ser actualizado");
        assertEquals("456", existingAirline.getCode(), "El código de la entidad debe ser actualizado");

        assertEquals(TEST_ID, actualResponse.id());
        assertEquals("Jose", actualResponse.name());
        assertEquals("456", actualResponse.code());

    }
}