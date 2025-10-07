package com.example.airline.services;

import com.example.airline.DTO.SeatInventoryDTO;
import com.example.airline.Mappers.SeatInventoryMapper;
import com.example.airline.Services.SeatInventoryServiceImpl;
import com.example.airline.entities.Cabin;
import com.example.airline.entities.SeatInventory;
import com.example.airline.repositories.SeatInventoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SeatInventoryServiceImplTest {
    @Mock
    SeatInventoryRepository seatInventoryRepository;
    @InjectMocks
    SeatInventoryServiceImpl seatInventoryServiceImpl;
    @Spy
    SeatInventoryMapper seatInventoryMapper = Mappers.getMapper(SeatInventoryMapper.class);


    @Test
    void shouldCreateAndReturnResponseDTO() {
        var seatRequest = new SeatInventoryDTO.seatInventoryCreateRequest(
                150,
                150,
                Cabin.ECONOMY // Asumo que Cabin es un Enum
        );

        when(seatInventoryRepository.save(any())).thenAnswer(inv -> {
            SeatInventory seatInventory = inv.getArgument(0);
            seatInventory.setId(55L);

            return seatInventory;
        });

        var saved = seatInventoryServiceImpl.create(seatRequest);

        assertThat(saved).isNotNull();
        assertThat(saved.seatInventoryId()).isEqualTo(55L);
        assertThat(saved.totalSeats()).isEqualTo(150);
        assertThat(saved.availableSeats()).isEqualTo(150);
        assertThat(saved.cabin()).isEqualTo(Cabin.ECONOMY);
        assertThat(saved.flightNumber()).isNull();
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

        var savedSeats = seatInventoryServiceImpl.findAll();

        assertThat(savedSeats).isNotNull();
        assertThat(savedSeats).hasSize(2);

        assertThat(savedSeats.getFirst().seatInventoryId()).isEqualTo(10L);
        assertThat(savedSeats.getFirst().totalSeats()).isEqualTo(50);
        assertThat(savedSeats.getFirst().availableSeats()).isEqualTo(40);
        assertThat(savedSeats.getFirst().cabin()).isEqualTo(Cabin.ECONOMY);
        assertThat(savedSeats.getFirst().flightNumber()).isNull();

        assertThat(savedSeats.get(1).seatInventoryId()).isEqualTo(20L);
        assertThat(savedSeats.get(1).totalSeats()).isEqualTo(100);
        assertThat(savedSeats.get(1).availableSeats()).isEqualTo(95);
        assertThat(savedSeats.get(1).cabin()).isEqualTo(Cabin.BUSINESS);
    }
}