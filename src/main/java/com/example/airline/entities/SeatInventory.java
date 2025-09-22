package com.example.airline.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class SeatInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Integer totalSeats;
    private enum cabin {
        FirstClass,
        bussines,
        economy
    }
    private Integer availableSeats;
    @ManyToOne
    @JoinColumn(name="flightId")
    private Flight flight;

}
