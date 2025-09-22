package com.example.airline.repositories;

import com.example.airline.entities.BookingItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface BookingItemsRepository extends JpaRepository<BookingItems, Long> {

    @Query("""
select bi from BookingItems bi where bi.booking.id = :bookingId order by bi.segmentOrder asc
""")
    List<BookingItems> findByBookingId(@Param("bookingId") Long bookingId);

    @Query("""
Select sum(coalesce(bi.price,0)) from BookingItems bi where bi.booking.id = :bookingId
""")
    BigDecimal totalPriceOfBookingItems(@Param("bookingId") Long bookingId);

    @Query("""
select count(bi) from BookingItems bi where bi.flight.id = :flightId and bi.cabin = :cabin
""")
    Long howManyBookingWasSellByFlightIdAndCabin(@Param("flightId")  Long flightId,
                                                 @Param("cabin") String cabin);
}
