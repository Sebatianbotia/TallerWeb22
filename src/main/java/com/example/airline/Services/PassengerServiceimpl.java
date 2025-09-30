package com.example.airline.Services;

import com.example.airline.DTO.PassengerDTO;
import com.example.airline.Services.Mappers.PassengerMapper;
import com.example.airline.entities.Passenger;
import com.example.airline.entities.PassengerProfile;
import com.example.airline.repositories.PassengerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PassengerServiceimpl implements PassengerService{
    private final PassengerRepository passengerRepository;
    private final PassengerProfileServiceImpl passengerProfileService;


    @Override
    public PassengerDTO.passengerResponse create(PassengerDTO.passengerCreateRequest createRequest) {
        Passenger passenger = PassengerMapper.toEntity(createRequest);
        if (createRequest.passengerProfile() != null) {
           var profile =  passengerProfileService.createObject(createRequest.passengerProfile());
           passenger.setProfile(profile);
           profile.setPassenger(passenger);
        }
        passengerRepository.save(passenger);
        return PassengerMapper.toDTO(passenger);
    }

    @Override
    public PassengerDTO.passengerResponse update(Long id, PassengerDTO.passengerUpdateRequest updateRequest) {
        var m = get(id);
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
    public void delete(Long id) {
        var m = get(id);
        passengerRepository.delete(m);
    }

    @Override
    public PassengerDTO.passengerResponse find(Long id) {
        return PassengerMapper.toDTO(get(id));
    }

    @Override
    public List<PassengerDTO.passengerResponse> findAll() {
        var passenger =  passengerRepository.findAll();
        return passenger.stream().map(PassengerMapper::toDTO).toList();
    }

    @Override
    public Passenger get(Long id){
        var p =  passengerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Passenger with id " + id + " not found"));
        return p;
    }
}
