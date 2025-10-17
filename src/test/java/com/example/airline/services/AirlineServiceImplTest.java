package com.example.airline.services;

import com.example.airline.DTO.AirlaneDTO.*;
import com.example.airline.Mappers.AirlineMapper;
import com.example.airline.entities.Airline;
import com.example.airline.repositories.AirlineRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
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

    @Test
    void shouldFindAllAndReturPage(){
        var airline1 = Airline.builder().id(1L).code("1").name("areod").build();
        var airline2 = Airline.builder().id(2L).code("3").name("eol").build();
        Page page = new PageImpl<>(List.of(airline1, airline2));


        when(airlineRepository.findAll(PageRequest.of(0,2))).thenReturn(page);

        when(mapper.toDTO(any())).thenAnswer(inv -> {
           Airline airline = inv.getArgument(0);

           return new airlineResponse(airline.getId(),airline.getName(),airline.getCode(),Collections.emptyList());

        });

        Page<airlineResponse> pages = airlineServiceImpl.list(PageRequest.of(0, 2));

        assertNotNull(pages);
        assertThat(pages.getTotalElements()).isEqualTo(2);
        assertThat(pages.getTotalPages()).isEqualTo(1);
        assertThat(pages.getContent()).hasSize(2);
        assertThat(pages.getContent().get(1).id()).isEqualTo(2L);
        assertThat(pages.getContent().get(1).name()).isEqualTo("eol");
        assertThat(pages.getContent().get(0).id()).isEqualTo(1L);
        assertThat(pages.getContent().get(0).name()).isEqualTo("areod");






    }
}