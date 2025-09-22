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
    private long id;
    private String name;
    private String code;
    private String city;

    @OneToMany(mappedBy = "airport")
    private List<Flight> flights;

    @OneToMany(mappedBy = "destination")
    private List<Flight> flightsDestination;
}
