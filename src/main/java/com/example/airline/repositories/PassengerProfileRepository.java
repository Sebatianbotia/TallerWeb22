package com.example.airline.repositories;

import com.example.airline.entities.PassengerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerProfileRepository extends JpaRepository<PassengerProfile,Long> {
}
