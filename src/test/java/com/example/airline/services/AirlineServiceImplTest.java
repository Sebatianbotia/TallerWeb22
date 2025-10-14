package com.example.airline.services;

import com.example.airline.DTO.AirlaneDTO.*;
import com.example.airline.Mappers.AirlineMapper;
import com.example.airline.entities.Airline;
import com.example.airline.repositories.AirlineRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AirlineServiceImplTest {
    @Mock
    AirlineRepository airlineRepository;
    @Mock
    AirlineMapper mapper;
    @InjectMocks
    AirlineServiceImpl airlineServiceImpl;

    private final Long TEST_ID = 42L;

    @Test
    void shouldCreateAndReturnResponse() {
        var createRequest = new airlineCreateRequest("Aeroda", "232323");

        Airline airlineEntity = new Airline();
        airlineEntity.setName("Aeroda");
        airlineEntity.setCode("232323");

        airlineResponse expectedResponse = new airlineResponse( 11L, "Aeroda", "232323", Collections.emptyList());

        when(mapper.toEntity(createRequest)).thenReturn(airlineEntity);

        when(airlineRepository.save(any(Airline.class))).thenAnswer(invocation -> {
            Airline airline = invocation.getArgument(0);
            airline.setId(11L);
            airline.setFlights(Collections.emptyList());
            return airline;
        });

        when(mapper.toDTO(any(Airline.class))).thenReturn(expectedResponse);

        airlineResponse result = airlineServiceImpl.create(createRequest);

        assertNotNull(result);
        assertEquals(11L, result.id());
        assertEquals("Aeroda", result.name());
        assertEquals("232323", result.code());
        assertEquals(Collections.emptyList(), result.flights());

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
        var expectedResponse = new airlineResponse(TEST_ID, "Jose", "456", Collections.emptyList());

        when(airlineRepository.findById(TEST_ID)).thenReturn(Optional.of(existingAirline));

        doAnswer(invocation -> {
            Airline airline = invocation.getArgument(0);
            airlineUpdateRequest req = invocation.getArgument(1);

            airline.setName(req.name());
            airline.setCode(req.code());

            return null;
        }).when(mapper).updateEntity(any(Airline.class), any(airlineUpdateRequest.class));

        when(mapper.toDTO(any())).thenReturn(expectedResponse);


        airlineResponse actualResponse = airlineServiceImpl.update(TEST_ID, updateRequest);

        assertEquals("Jose", existingAirline.getName(), "El nombre de la entidad debe ser actualizado");
        assertEquals("456", existingAirline.getCode(), "El código de la entidad debe ser actualizado");



        assertEquals(TEST_ID, actualResponse.id());
        assertEquals("Jose", actualResponse.name());
        assertEquals("456", actualResponse.code());

    }
}