package com.example.airline.repositories;

import com.example.airline.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findBookingById(Long id);
}
