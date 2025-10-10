package com.example.airline.services;

import com.example.airline.DTO.SeatInventoryDTO;
import com.example.airline.Mappers.SeatInventoryMapper;
import com.example.airline.Services.SeatInventoryServiceImpl;
import com.example.airline.entities.Cabin;
import com.example.airline.entities.Flight;
import com.example.airline.entities.SeatInventory;
import com.example.airline.repositories.SeatInventoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SeatInventoryServiceImplTest {
    @Mock
    SeatInventoryRepository seatInventoryRepository;
    @InjectMocks
    SeatInventoryServiceImpl seatInventoryServiceImpl;
    @Mock
    SeatInventoryMapper seatInventoryMapper;


    @Test
    void shouldCreateAndReturnResponseDTO() {
        var seatRequest = new SeatInventoryDTO.seatInventoryCreateRequest(
                150,
                150,
                Cabin.ECONOMY
        );

        when(seatInventoryRepository.save(any())).thenAnswer(inv -> {
            SeatInventory seatInventory = inv.getArgument(0);
            seatInventory.setId(55L);

            return seatInventory;
        });

        when(seatInventoryMapper.toEntity(any())).thenAnswer(inv -> {
            SeatInventoryDTO.seatInventoryCreateRequest request = inv.getArgument(0);
            Flight flight = Flight.builder().id(55L).number("HOOOLLLLL").build();
            return SeatInventory.builder().totalSeats(request.totalSeats()).availableSeats(request.availableSeats()).cabin(request.cabin())
                    .flight(flight).build();
        });

        when(seatInventoryMapper.toDTO(any())).thenAnswer(inv -> {
            SeatInventory object =  inv.getArgument(0);
            String flightNumber = "No hay flight";
            if (object.getFlight() != null) {
                flightNumber = object.getFlight().getNumber();
            }
            return new SeatInventoryDTO.seatInventoryDtoResponse(object.getId(), object.getTotalSeats(),
                    object.getAvailableSeats(), object.getCabin(), flightNumber
            );
        });

        var saved = seatInventoryServiceImpl.create(seatRequest);

        assertThat(saved).isNotNull();
        assertThat(saved.seatInventoryId()).isEqualTo(55L);
        assertThat(saved.totalSeats()).isEqualTo(150);
        assertThat(saved.availableSeats()).isEqualTo(150);
        assertThat(saved.cabin()).isEqualTo(Cabin.ECONOMY);
        assertThat(saved.flightNumber()).isNotNull();
        assertThat(saved.flightNumber()).isEqualTo("HOOOLLLLL");
        verify(seatInventoryRepository).save(any(SeatInventory.class));
    }

    @Test
    void shouldUpdateReturnResponseDTO() {
        var initialSeatInventory = new SeatInventory(55L, 150, 150, Cabin.ECONOMY, null);
        when(seatInventoryRepository.findById(55L)).thenReturn(Optional.of(initialSeatInventory));
        var seatUpdateRequest = new SeatInventoryDTO.seatInventoryUpdateRequest(
                100,
                90,
                Cabin.BUSINESS
        );

        doAnswer(inv -> {
            SeatInventoryDTO.seatInventoryUpdateRequest request = inv.getArgument(0);
            SeatInventory seatInventory = inv.getArgument(1);
            if (request.totalSeats() != null &&  request.totalSeats() >= 0) {
                seatInventory.setTotalSeats(request.totalSeats());
            }
            if (request.cabin() != null) {
                seatInventory.setCabin(request.cabin());
            }
            if (request.availableSeats() != null &&  request.availableSeats() >= 0) {
                seatInventory.setAvailableSeats(request.availableSeats());
            }
            return null;
        }).when(seatInventoryMapper).updateEntity(any(), any());

        when(seatInventoryMapper.toDTO(any())).thenAnswer(inv -> {
            SeatInventory object =  inv.getArgument(0);
            String flightNumber = "No hay flight";
            if (object.getFlight() != null) {
                flightNumber = object.getFlight().getNumber();
            }
            return new SeatInventoryDTO.seatInventoryDtoResponse(object.getId(), object.getTotalSeats(),
                    object.getAvailableSeats(), object.getCabin(), flightNumber
            );
        });

        var updated = seatInventoryServiceImpl.update(55L, seatUpdateRequest);

        assertThat(updated).isNotNull();
        assertThat(updated.seatInventoryId()).isEqualTo(55L);
        assertThat(updated.totalSeats()).isEqualTo(100);
        assertThat(updated.availableSeats()).isEqualTo(90);
        assertThat(updated.cabin()).isEqualTo(Cabin.BUSINESS);
        assertThat(updated.flightNumber()).isNull();
    }

    @Test
    void shouldFindAllAndReturnResponseDTO(){
        var seat1 = new SeatInventory(10L, 50, 40, Cabin.ECONOMY, null);
        var seat2 = new SeatInventory(20L, 100, 95, Cabin.BUSINESS, null);

        List<SeatInventory> seatInventories = List.of(seat1, seat2);

        when(seatInventoryRepository.findAll()).thenReturn(seatInventories);

        when(seatInventoryMapper.toDTO(any())).thenAnswer(inv -> {
            SeatInventory object =  inv.getArgument(0);
            String flightNumber = "No hay flight";
            if (object.getFlight() != null) {
                flightNumber = object.getFlight().getNumber();
            }
            return new SeatInventoryDTO.seatInventoryDtoResponse(object.getId(), object.getTotalSeats(),
                    object.getAvailableSeats(), object.getCabin(), flightNumber
            );
        });


        var savedSeats = seatInventoryServiceImpl.findAll();

        assertThat(savedSeats).isNotNull();
        assertThat(savedSeats).hasSize(2);

        assertThat(savedSeats.getFirst().seatInventoryId()).isEqualTo(10L);
        assertThat(savedSeats.getFirst().totalSeats()).isEqualTo(50);
        assertThat(savedSeats.getFirst().availableSeats()).isEqualTo(40);
        assertThat(savedSeats.getFirst().cabin()).isEqualTo(Cabin.ECONOMY);
        assertThat(savedSeats.getFirst().flightNumber()).isNotNull();

        assertThat(savedSeats.get(1).seatInventoryId()).isEqualTo(20L);
        assertThat(savedSeats.get(1).totalSeats()).isEqualTo(100);
        assertThat(savedSeats.get(1).availableSeats()).isEqualTo(95);
        assertThat(savedSeats.get(1).cabin()).isEqualTo(Cabin.BUSINESS);
    }
}