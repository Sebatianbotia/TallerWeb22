package com.example.airline.repositories;

import com.example.airline.entities.BookingItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingItemsRepository extends JpaRepository<BookingItems, Long> {
    BookingItems findBookingItemsById(Long id);
}
