package com.example.airline.repositories;

import com.example.airline.entities.SeatInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatInventoryRepository extends JpaRepository<SeatInventory,Long> {
SeatInventory findSeatInventoryById(Long id);
}
