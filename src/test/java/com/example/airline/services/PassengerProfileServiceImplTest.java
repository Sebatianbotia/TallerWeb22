package com.example.airline.services;

import com.example.airline.DTO.PassengerDTO;
import com.example.airline.DTO.PassengerProfileDTO;
import com.example.airline.Services.PassengerProfileServiceImpl;
import com.example.airline.entities.PassengerProfile;
import com.example.airline.repositories.PassengerProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PassengerProfileServiceImplTest {
    @Mock
    PassengerProfileRepository passengerProfileRepository;
    @InjectMocks
    PassengerProfileServiceImpl passengerProfileServiceImpl;

    @Test
    void shouldCreateAndreturnResponseDTO(){
        var p = new PassengerProfileDTO.passengerProfileCreateRequest("222", "+3");
        when(passengerProfileRepository.save(any())).thenAnswer(inv -> {
            PassengerProfile pp = inv.getArgument(0);
            pp.setId(1L);
            return pp;
        });

        var saved = passengerProfileServiceImpl.create(p);
        assertThat(saved).isNotNull();
        assertThat(saved.passengerProfileID()).isEqualTo(1L);
        assertThat(saved.phoneNumber()).isEqualTo("222");
        assertThat(saved.countryCode()).isEqualTo("+3");
        verify(passengerProfileRepository).save(any(PassengerProfile.class));
    }

    @Test
    void shouldUpdateReturnResponseDTO(){
        var p = PassengerProfile.builder().id(1L).phone("222").build();
        when(passengerProfileRepository.findById(1L)).thenReturn(Optional.of(p));

        var passengerProfileUpdate = new PassengerProfileDTO.passengerProfileUpdateRequest("33", "+34");
        var update = passengerProfileServiceImpl.update(1L, passengerProfileUpdate);

        assertThat(update.passengerProfileID()).isEqualTo(1L);
        assertThat(update.phoneNumber()).isEqualTo("33");
        assertThat(update.countryCode()).isEqualTo("+34");
    }

    @Test
    void shouldFindAllAndReturnResponseDTO(){
        var pp = PassengerProfile.builder().id(1L).phone("222").build();
        List<PassengerProfile> passengerProfiles = new ArrayList<>(
                List.of(pp)
        );
        when(passengerProfileRepository.findAll()).thenReturn(passengerProfiles);

        var saved = passengerProfileServiceImpl.findAll();

        assertThat(saved.getFirst().passengerProfileID()).isEqualTo(1L);
        assertThat(saved.getFirst().phoneNumber()).isEqualTo("222");
    }
}