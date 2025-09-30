package com.example.airline.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Tags")

public class Tag {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long id;
private String name;
@ManyToMany(mappedBy = "tags")
private Set<Flight> flights;

public void  addFlight(Flight flight) {
this.flights.add(flight);
}
}
