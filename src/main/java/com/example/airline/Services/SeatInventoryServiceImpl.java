package com.example.airline.Services;

import com.example.airline.DTO.SeatInventoryDTO;
import com.example.airline.Services.Mappers.SeatInventoryMapper;
import com.example.airline.entities.Flight;
import com.example.airline.entities.SeatInventory;
import com.example.airline.repositories.SeatInventoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
@Transactional
public class SeatInventoryServiceImpl implements  SeatInventoryService {

    private final SeatInventoryRepository seatInventoryRepository;

    @Override
    public SeatInventoryDTO.seatInventoryDtoResponse create(Flight flight, SeatInventoryDTO.seatInventoryCreateRequest inventoryCreateRequest) {
        SeatInventory seatInventory = SeatInventoryMapper.toEntity(inventoryCreateRequest);
        seatInventory.setFlight(flight);
        seatInventoryRepository.save(seatInventory);
        return SeatInventoryMapper.toDTO(seatInventory);
    }

    @Override
    public SeatInventoryDTO.seatInventoryDtoResponse find(Long id) {
        var s = findSeatInventoryObject(id);
        return SeatInventoryMapper.toDTO(s);
    }

    @Override
    public SeatInventory findSeatInventoryObject(Long id) {
        var s = seatInventoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "SeatInventory with id " + id + " not found."
        ));
        return s;
    }

    @Override
    public SeatInventoryDTO.seatInventoryDtoResponse update(Long id, SeatInventoryDTO.seatInventoryUpdateRequest updateRequest) {
        var seatInventory = findSeatInventoryObject(id);
        SeatInventoryMapper.path(seatInventory, updateRequest);
        seatInventoryRepository.save(seatInventory);
        return SeatInventoryMapper.toDTO(seatInventory);
    }

    @Override
    public SeatInventoryDTO.seatInventoryDtoResponse update(SeatInventory entity, SeatInventoryDTO.seatInventoryUpdateRequest updateRequest) {
        SeatInventoryMapper.path(entity, updateRequest);
        seatInventoryRepository.save(entity);
        return SeatInventoryMapper.toDTO(entity);
    }

    @Override
    public List<SeatInventoryDTO.seatInventoryDtoResponse> findAll() {
        return seatInventoryRepository.findAll().stream().map(SeatInventoryMapper::toDTO).toList();
    }

    @Override
    public void delete(Long id) {
        var p = findSeatInventoryObject(id);
        seatInventoryRepository.delete(p);
    }
}
