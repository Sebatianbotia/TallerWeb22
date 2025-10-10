package com.example.airline.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;
import com.example.airline.DTO.AirportDTO.*;
import com.example.airline.Mappers.AirportMapper;
import com.example.airline.Services.AirportServiceImpl;
import com.example.airline.entities.Airport;
import com.example.airline.repositories.AirportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class AirportServiceImplTest {

    @Mock
    private AirportRepository airportRepository;
    @Mock
    private AirportMapper mapper;

    @InjectMocks
    private AirportServiceImpl airportService;

    private final Long TEST_ID = 10L;
    @Test
    void shouldCreateAndReturnResponse() {
        var createRequest = new AirportCreateRequest("El Dorado", "BOG", "Bogotá");
        Airport createdAirport = Airport.builder()
                .name(createRequest.name())
                .code(createRequest.code())
                .city(createRequest.city())
                .build();
        AirportResponse expectedRespose = new AirportResponse(TEST_ID,"El Dorado", "BOG", "Bogotá", null, null);

        when(mapper.toEntity(any(AirportCreateRequest.class))).thenReturn(createdAirport);
        when(airportRepository.save(any(Airport.class))).thenAnswer(invocation -> {
            Airport airportEntity = invocation.getArgument(0);
            airportEntity.setId(TEST_ID); // Asigna el ID de prueba
            return airportEntity;
        });
        when(mapper.toDTO(any(Airport.class))).thenReturn(expectedRespose);

        AirportResponse actualResponse = airportService.create(createRequest);

        assertEquals(TEST_ID, actualResponse.id(), "El ID debe ser el asignado por la BD (mock)");
        assertEquals("El Dorado", actualResponse.name());
        assertEquals("BOG", actualResponse.code());
        assertEquals("Bogotá", actualResponse.city());

    }

    @Test
    void shouldUpdateAirportAndReturnUpdatedResponse() {

        Airport existingAirport = Airport.builder()
                .id(TEST_ID)
                .name("Old Name")
                .code("OLD")
                .city("Old City")
                .build();
        AirportResponse expectedResponse = new AirportResponse(TEST_ID,"New Name","NEW","New City",null,null);

        var updateRequest = new AirportUpdateRequest("New Name", "NEW", "New City");

        when(airportRepository.findById(TEST_ID)).thenReturn(Optional.of(existingAirport));
        doAnswer(invocationOnMock -> {
            AirportUpdateRequest req = invocationOnMock.getArgument(0);
            Airport airportEntity = invocationOnMock.getArgument(1);
            airportEntity.setId(TEST_ID);
            airportEntity.setName(req.name());
            airportEntity.setCode(req.code());
            airportEntity.setCity(req.city());
            return null; //retorna nulo porque el metodo es void
        }).when(mapper).updateEntity(any(AirportUpdateRequest.class), any(Airport.class));
        when(mapper.toDTO(any(Airport.class))).thenReturn(expectedResponse);

        AirportResponse actualResponse = airportService.update(TEST_ID, updateRequest);


        assertEquals("New Name", existingAirport.getName(), "El nombre de la entidad debe ser actualizado");
        assertEquals("NEW", existingAirport.getCode(), "El código de la entidad debe ser actualizado");
        assertEquals("New City", existingAirport.getCity(), "La ciudad de la entidad debe ser actualizada");

        assertEquals(TEST_ID, actualResponse.id());
        assertEquals("New Name", actualResponse.name());
        assertEquals("NEW", actualResponse.code());
        assertEquals("New City", actualResponse.city());

    }

}