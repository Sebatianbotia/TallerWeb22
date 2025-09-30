package com.example.airline.services;

import com.example.airline.DTO.PassengerDTO;
import com.example.airline.DTO.PassengerProfileDTO;
import com.example.airline.Services.PassengerProfileServiceImpl;
import com.example.airline.Services.PassengerServiceimpl;
import com.example.airline.entities.Passenger;
import com.example.airline.entities.PassengerProfile;
import com.example.airline.repositories.PassengerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PassengerServiceImplTest {
    @Mock
    PassengerRepository passengerRepository;
    @Mock
    PassengerProfileServiceImpl passengerProfileService;
    @InjectMocks
    PassengerServiceimpl passengerServiceimpl;



    @Test
    void shouldCreateAndReturnResponseDTO() {
        var p = new PassengerDTO.passengerCreateRequest("Jose", "notiene@gmail.com", new PassengerProfileDTO.passengerProfileCreateRequest("222", "+33"));
        when(passengerRepository.save(any(Passenger.class))).thenAnswer(inv -> {
            Passenger p2 = inv.getArgument(0);
            p2.setId(11L);
            return p2;
        });

        PassengerProfile expectedProfile = PassengerProfile.builder()
                .countryCode("+33")
                .phone("222")
                .id(2L)
                .build();

        when(passengerProfileService.createObject(any()))
                .thenReturn(expectedProfile);

        var res = passengerServiceimpl.create(p);

        assertThat(res.id()).isEqualTo(11L);
        assertThat(res.fullName()).isEqualTo("Jose");
        assertThat(res.email()).isEqualTo("notiene@gmail.com");
        assertThat(res.passengerProfile().phone()).isEqualTo("222");
        assertThat(res.passengerProfile().countryCode()).isEqualTo("+33");
        verify(passengerRepository).save(any(Passenger.class));
    }

    @Test
    void shouldUpdateAndReturnResponseDTO() {
        PassengerProfile profile = new PassengerProfile(2L, "222", "+34",null);
        var passenger = Passenger.builder().id(1L).email("notiene@gmail.com").fullName("Holita").profile(profile).build();

        when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger));



        var passengerUpdate = new PassengerDTO.passengerUpdateRequest("Que tal", "yatengo@gmail.com", new PassengerProfileDTO.passengerProfileUpdateRequest(null,"+57"));
        var update = passengerServiceimpl.update(1L, passengerUpdate);



        assertThat(update.fullName()).isEqualTo("Que tal");
        assertThat(update.email()).isEqualTo("yatengo@gmail.com");
        assertThat(update.passengerProfile().countryCode()).isEqualTo("+57");

    }
}
