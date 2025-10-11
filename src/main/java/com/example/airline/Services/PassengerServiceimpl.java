package com.example.airline.Services;

import com.example.airline.API.Error.NotFoundException;
import com.example.airline.DTO.PassengerDTO;
import com.example.airline.Mappers.PassengerMapper;
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
        var save = passengerRepository.save(passenger);
        return passengerMapper.toDTO(save);
    }

    @Override
    @Transactional
    public PassengerDTO.passengerResponse update(Long id, PassengerDTO.passengerUpdateRequest updateRequest) {
        var m = this.getObject(id);
        passengerMapper.updateEntity(m, updateRequest);
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
        var m = this.getObject(id);
        passengerRepository.delete(m);
    }


    @Override
    public List<PassengerDTO.passengerResponse> findAll() {
        var passenger =  passengerRepository.findAll();
        return passenger.stream().map(passengerMapper::toDTO).toList();
    }

    @Override
    public Passenger getObject(Long id){
        var p =  passengerRepository.findById(id).orElseThrow(() -> new NotFoundException("Passenger with id " + id + " not found"));
        return p;
    }

    @Override
    public PassengerDTO.passengerResponse get(Long id) {
        return passengerMapper.toDTO(this.getObject(id));
    }




}
