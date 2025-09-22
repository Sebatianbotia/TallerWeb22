package com.example.airline.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class BookingItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private BigDecimal price;
    private Integer segmentOrder;
    private enum cabin {
        ECONOMY,
        PREMIUM,
        BUSINESS
    }
    @ManyToOne
    @JoinColumn(name = "bookingId")
    private Booking booking;
    @ManyToOne
    @JoinColumn(name="flightId")
    private Flight flight;

}
