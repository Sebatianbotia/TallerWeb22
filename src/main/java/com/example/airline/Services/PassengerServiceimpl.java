package com.example.airline.Services;

import com.example.airline.DTO.PassengerDTO;
import com.example.airline.Services.Mappers.PassengerMapper;
import com.example.airline.entities.Passenger;
import com.example.airline.entities.PassengerProfile;
import com.example.airline.repositories.PassengerRepository;
import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PassengerServiceimpl implements PassengerService{
    private final PassengerRepository passengerRepository;
    private final PassengerProfileServiceImpl passengerProfileService;


    @Override
    public PassengerDTO.passengerResponse createPassenger(PassengerDTO.passengerCreateRequest createRequest) {
        Passenger passenger = PassengerMapper.toEntity(createRequest);
        if (createRequest.passengerProfile() != null) {
           var profile =  passengerProfileService.createObject(createRequest.passengerProfile());
           passenger.setProfile(profile);
        }
        passengerRepository.save(passenger);
        return PassengerMapper.toDTO(passenger);
    }

    @Override
    public PassengerDTO.passengerResponse updatePassenger(Long id, PassengerDTO.passengerUpdateRequest updateRequest) {
        var m = passengerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Member %d not found".formatted(id)));
        PassengerMapper.path(m, updateRequest);
        if (updateRequest.passengerProfile() != null) { // Si no tiene informacion nueva se toma passengerProfile como NULL
            if (m.getProfile() == null) { // Crea un perfil si entra informacion nueva y el usuario no tiene un perfil
                m.setProfile(new PassengerProfile());
            }
            passengerProfileService.update(m.getProfile(), updateRequest.passengerProfile());
        }
        return PassengerMapper.toDTO(m);
    }

    @Override
    public void deletePassenger(Long id) {
        var m = passengerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Member %d not found".formatted(id)));
        passengerRepository.delete(m);
    }

    @Override
    public PassengerDTO.passengerResponse findPassengerById(Long id) {
        return PassengerMapper.toDTO(passengerRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("Passenger with id " + id + " not found")
                ));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PassengerDTO.passengerResponse> findAllPassengers() {
        var passenger =  passengerRepository.findAll();
        return passenger.stream().map(PassengerMapper::toDTO).toList();
    }
}
