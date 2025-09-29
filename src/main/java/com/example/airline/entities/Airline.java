package com.example.airline.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Airline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    private String code;

    @OneToMany(mappedBy = "airline")
    List<Flight> flights;
    public List<Flight> getFlights() {
        List<Flight> flights = new ArrayList<>();
        for(Flight flight : flights){
            flights.add(flight);
        }
        return flights;
    }

    public void setFlights(List<Flight> flights) {}
    public void addFlight(Flight fly){
        this.flights.add(fly);
    }
}
