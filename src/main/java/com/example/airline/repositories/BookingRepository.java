package com.example.airline.repositories;

import com.example.airline.entities.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("""
select b from Booking b where lower(b.passenger.email) = lower(:email) 
order by b.createdAt desc

""")
    Page<Booking> pageOfReservationsAboutPassenger(@Param("email") String email,
                                                   Pageable pageable);

    @Query("""
select b from Booking b left join fetch b.passenger left join fetch 
b.items i left join fetch i.flight where b.id = :bookingId
""")
    Optional<Booking> findBookingById(@Param("bookingId") Long bookingId);

}
