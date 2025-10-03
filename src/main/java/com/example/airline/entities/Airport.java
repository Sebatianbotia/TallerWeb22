package com.example.airline.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    private String city;

    @OneToMany(mappedBy = "originAirport")
    private List<Flight> flightsOrigin;

    @OneToMany(mappedBy = "destinationAirport")
    private List<Flight> flightsDestination;

    public void addFlightOrigin(Flight flight) {
        flightsOrigin.add(flight);
    }
    public void addFlightDestination(Flight flight) {
        flightsDestination.add(flight);
    }
}
