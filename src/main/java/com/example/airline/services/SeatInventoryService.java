package com.example.airline.services;

import com.example.airline.DTO.SeatInventoryDTO;
import com.example.airline.entities.SeatInventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SeatInventoryService {

    SeatInventoryDTO.seatInventoryDtoResponse create(SeatInventoryDTO.seatInventoryCreateRequest inventoryCreateRequest);

    SeatInventoryDTO.seatInventoryDtoResponse get(Long id);

    SeatInventory getObject(Long id);

    SeatInventoryDTO.seatInventoryDtoResponse update(Long id, SeatInventoryDTO.seatInventoryUpdateRequest updateRequest);

    SeatInventoryDTO.seatInventoryDtoResponse update(SeatInventory entity, SeatInventoryDTO.seatInventoryUpdateRequest updateRequest);

    Page<SeatInventoryDTO.seatInventoryDtoResponse> list(Pageable pageable);

    void delete(Long id);
}
