package com.example.airline.repositories;

import com.example.airline.entities.BookingItem;
import com.example.airline.entities.Cabin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface BookingItemsRepository extends JpaRepository<BookingItem, Long> {

    @Query("""
select bi from BookingItem bi where bi.booking.id = :bookingId order by bi.segmentOrder asc
""")
    List<BookingItem> findByBookingId(@Param("bookingId") Long bookingId);

    @Query("""
Select sum(coalesce(bi.price,0)) from BookingItem bi where bi.booking.id = :bookingId
""")
    BigDecimal totalPriceOfBookingItems(@Param("bookingId") Long bookingId);

    @Query("""
select count(bi) from BookingItem bi where bi.flight.id = :flightId and bi.cabin = :cabin
""")
    Long howManyBookingWasSellByFlightIdAndCabin(@Param("flightId")  Long flightId,
                                                 @Param("cabin") Cabin cabin);
}
