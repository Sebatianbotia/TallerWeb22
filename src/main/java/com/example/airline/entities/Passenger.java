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

public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fullName;
    private String email;
    @OneToOne
    @JoinColumn(name = "passengerProfileId")
    private PassengerProfile profile;
    @OneToMany(mappedBy = "passenger")
    private List<Booking> bookings;

}
