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
    private final PassengerMapper passengerMapper;

    @Override
    public PassengerDTO.passengerResponse create(PassengerDTO.passengerCreateRequest createRequest) {
        Passenger passenger = passengerMapper.toEntity(createRequest);
        if (createRequest.passengerProfile() != null) {
            var profile =  passengerProfileService.createObject(createRequest.passengerProfile());
            passenger.setProfile(profile);
            profile.setPassenger(passenger);
        }
        passengerRepository.save(passenger);
        return passengerMapper.toDTO(passenger);
    }

    @Override
    @Transactional
    public PassengerDTO.passengerResponse update(Long id, PassengerDTO.passengerUpdateRequest updateRequest) {
        var m = get(id);
        passengerMapper.path(m, updateRequest);
        if (updateRequest.passengerProfile() != null) {
            if (m.getProfile() == null) {
                m.setProfile(new PassengerProfile());
            }
            passengerProfileService.update(m.getProfile(), updateRequest.passengerProfile());
        }
        return passengerMapper.toDTO(m);
    }

    @Override
    public void delete(Long id) {
        var m = get(id);
        passengerRepository.delete(m);
    }

    @Override
    public PassengerDTO.passengerResponse find(Long id) {
        return passengerMapper.toDTO(get(id));
    }

    @Override
    public List<PassengerDTO.passengerResponse> findAll() {
        var passenger =  passengerRepository.findAll();
        return passenger.stream().map(passengerMapper::toDTO).toList();
    }

    @Override
    public Passenger get(Long id){
        var p =  passengerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Passenger with id " + id + " not found"));
        return p;
    }
}
