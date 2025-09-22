package com.example.airline.repositories;

import com.example.airline.entities.SeatInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SeatInventoryRepository extends JpaRepository<SeatInventory,Long> {

    @Query("""
select s from SeatInventory s where s.flight.id = :flightId and s.cabin = :cabinType
""")
    Optional<SeatInventory> findSeatInventoryByFlightIdAndCabinType(@Param("flightId")
                                                                Long flightId, @Param("cabinType")String cabinType);

    @Query("""

select s.availableSeats >= :minNum from SeatInventory s where s.flight.id = :flightId and s.cabin = :cabinType
""")
    Boolean existsBySeatIdAndCabinTypeGreaterThanMin(@Param("flightId")
                                                     Long flightId, @Param("cabinType")String cabinType,
                                                     @Param("minNum") int minNum);
}
