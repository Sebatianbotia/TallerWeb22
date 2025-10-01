package com.example.airline.Services;

import com.example.airline.DTO.PassengerProfileDTO;
import com.example.airline.Services.Mappers.PassengerProfileMapper;
import com.example.airline.entities.Passenger;
import com.example.airline.entities.PassengerProfile;
import com.example.airline.repositories.PassengerProfileRepository;
import com.example.airline.repositories.PassengerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class PassengerProfileServiceImpl implements PassengerProfileService {

    private final PassengerProfileRepository passengerProfileRepository;
    private final PassengerRepository passengerRepository;
    private final PassengerProfileMapper passengerProfileMapper;

    @Override
    public PassengerProfileDTO.passengerProfileResponse create(PassengerProfileDTO.passengerProfileCreateRequest createRequest) {
        var profile = passengerProfileMapper.toEntity(createRequest);
        return passengerProfileMapper.toDTO(passengerProfileRepository.save(profile));
    }

    @Override
    public PassengerProfile createObject(PassengerProfileDTO.passengerProfileCreateRequest createRequest) {
        var profile = PassengerProfile.builder().countryCode(createRequest.countryCode()).phone(createRequest.phoneNumber()).build();
        profile =  passengerProfileRepository.save(profile);
        return profile;
    }

    @Override
    public PassengerProfileDTO.passengerProfileResponse findById(Long id) {
        var profile = get(id);
        return passengerProfileMapper.toDTO(profile);
    }

    @Override
    public PassengerProfile get(Long id) {
        var profile = passengerProfileRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Profile with id " + id + " not found"
        ));
        return profile;
    }

    @Override
    @Transactional
    public PassengerProfileDTO.passengerProfileResponse update(Long id, PassengerProfileDTO.passengerProfileUpdateRequest updateRequest) {
        var profile = get(id);
        passengerProfileMapper.updateEntity(updateRequest, profile);
        return passengerProfileMapper.toDTO(profile);
    }

    @Override
    public PassengerProfileDTO.passengerProfileResponse update(PassengerProfile profile, PassengerProfileDTO.passengerProfileUpdateRequest updateRequest) {
        passengerProfileMapper.path(profile, updateRequest);
        return passengerProfileMapper.toDTO(profile);
    }

    @Override
    public List<PassengerProfileDTO.passengerProfileResponse> findAll() {
        var profiles = passengerProfileRepository.findAll();
        return profiles.stream().map(passengerProfileMapper::toDTO).toList();
    }

    @Override
    public void delete(Long id) {
        var profile = get(id);
        passengerProfileRepository.delete(profile);
    }
    public Passenger findPassenger(Long passengerID) {
        return passengerRepository.findByid(passengerID).orElseThrow(() -> new EntityNotFoundException("PAsseneger not found"));
    }
}
