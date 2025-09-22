package com.example.airline.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String number;
    private OffsetDateTime arrivalTime;
    private OffsetDateTime departureTime;
    @ManyToOne
    @JoinColumn(name = "AirlineId")
    private Airline airline;
    @ManyToOne
    @JoinColumn(name="originAirportId")
    private Airport originAirport;
    @ManyToOne
    @JoinColumn(name="destinationAirportId")
    private Airport destinationAirport;
    @ManyToMany
    @JoinTable(
            name="AirportTag",
            joinColumns = @JoinColumn(name="FlightId"),
            inverseJoinColumns = @JoinColumn(name="TagId")
    )
    private Set<Tag> tags;
    @OneToMany(mappedBy = "flight")
    private List<SeatInventory> seatInventories;
    @OneToMany(mappedBy = "flight")
    private List<BookingItems> bookingItems;



}
