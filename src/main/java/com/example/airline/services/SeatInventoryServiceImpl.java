package com.example.airline.services;

import com.example.airline.API.Error.NotFoundException;
import com.example.airline.DTO.SeatInventoryDTO;
import com.example.airline.Mappers.SeatInventoryMapper;
import com.example.airline.entities.SeatInventory;
import com.example.airline.repositories.SeatInventoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
@Transactional
public class SeatInventoryServiceImpl implements SeatInventoryService {

    private final SeatInventoryRepository seatInventoryRepository;
    private final SeatInventoryMapper seatInventoryMapper;

    @Override
    public SeatInventoryDTO.seatInventoryDtoResponse create(SeatInventoryDTO.seatInventoryCreateRequest inventoryCreateRequest) {
        SeatInventory seatInventory = seatInventoryMapper.toEntity(inventoryCreateRequest);
        seatInventoryRepository.save(seatInventory);
        return seatInventoryMapper.toDTO(seatInventory);
    }

    public SeatInventory createAndReturn(SeatInventoryDTO.seatInventoryCreateRequest inventoryCreateRequest){
        SeatInventory seatInventory = seatInventoryMapper.toEntity(inventoryCreateRequest);
        return seatInventoryRepository.save(seatInventory);
    }

    @Override
    public SeatInventoryDTO.seatInventoryDtoResponse get(Long id) {
        var s = getObject(id);
        return seatInventoryMapper.toDTO(s);
    }

    @Override
    public SeatInventory getObject(Long id) {
        var s = seatInventoryRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "SeatInventory with id " + id + " not found."
        ));
        return s;
    }

    @Override
    @Transactional
    public SeatInventoryDTO.seatInventoryDtoResponse update(Long id, SeatInventoryDTO.seatInventoryUpdateRequest updateRequest) {
        var seatInventory = getObject(id);
        seatInventoryMapper.updateEntity(updateRequest, seatInventory);
        return seatInventoryMapper.toDTO(seatInventory);
    }

    @Override
    @Transactional
    public SeatInventoryDTO.seatInventoryDtoResponse update(SeatInventory entity, SeatInventoryDTO.seatInventoryUpdateRequest updateRequest) {
        seatInventoryMapper.updateEntity(updateRequest, entity);
        return seatInventoryMapper.toDTO(entity);
    }

    @Override
    public Page<SeatInventoryDTO.seatInventoryDtoResponse> list(Pageable  pageable) {
        return seatInventoryRepository.findAll(pageable).map(seatInventoryMapper::toDTO);
    }

    @Override
    public void delete(Long id) {
        var p = getObject(id);
        seatInventoryRepository.delete(p);
    }
}