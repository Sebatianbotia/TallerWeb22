package com.example.airline.Services;

import com.example.airline.DTO.SeatInventoryDTO;
import com.example.airline.entities.SeatInventory;

import java.util.List;

public interface SeatInventoryService {

    SeatInventoryDTO.seatInventoryDtoResponse create(SeatInventoryDTO.seatInventoryCreateRequest inventoryCreateRequest);

    SeatInventoryDTO.seatInventoryDtoResponse get(Long id);

    SeatInventory getObject(Long id);

    SeatInventoryDTO.seatInventoryDtoResponse update(Long id, SeatInventoryDTO.seatInventoryUpdateRequest updateRequest);

    SeatInventoryDTO.seatInventoryDtoResponse update(SeatInventory entity, SeatInventoryDTO.seatInventoryUpdateRequest updateRequest);

    List<SeatInventoryDTO.seatInventoryDtoResponse> findAll();

    void delete(Long id);
}
