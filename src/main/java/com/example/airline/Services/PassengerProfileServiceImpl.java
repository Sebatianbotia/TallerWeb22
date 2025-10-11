package com.example.airline.Services;

import com.example.airline.API.Error.NotFoundException;
import com.example.airline.DTO.PassengerProfileDTO;
import com.example.airline.Mappers.PassengerProfileMapper;
import com.example.airline.entities.PassengerProfile;
import com.example.airline.repositories.PassengerProfileRepository;
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
    private final PassengerProfileMapper passengerProfileMapper;

    @Override
    public PassengerProfileDTO.passengerProfileResponse create(PassengerProfileDTO.passengerProfileCreateRequest createRequest) {
        var profile = passengerProfileMapper.toEntity(createRequest);
        return passengerProfileMapper.toDTO(passengerProfileRepository.save(profile));
    }

    @Override
    public PassengerProfile createObject(PassengerProfileDTO.passengerProfileCreateRequest createRequest) {
        var profile = passengerProfileMapper.toEntity(createRequest);
        profile =  passengerProfileRepository.save(profile);
        return profile;
    }

    @Override
    public PassengerProfileDTO.passengerProfileResponse get(Long id) {
        var profile = this.getObject(id);
        return passengerProfileMapper.toDTO(profile);
    }

    @Override
    public PassengerProfile getObject(Long id) {
        PassengerProfile profile = passengerProfileRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "Profile with id " + id + " not found"
        ));
        return profile;
    }

    @Override
    @Transactional
    public PassengerProfileDTO.passengerProfileResponse update(Long id, PassengerProfileDTO.passengerProfileUpdateRequest updateRequest) {
        var profile = this.getObject(id);
        passengerProfileMapper.updateEntity(updateRequest, profile);
        return passengerProfileMapper.toDTO(profile);
    }

    @Override
    public PassengerProfileDTO.passengerProfileResponse update(PassengerProfile profile, PassengerProfileDTO.passengerProfileUpdateRequest updateRequest) {
        passengerProfileMapper.updateEntity(updateRequest, profile);
        return passengerProfileMapper.toDTO(profile);
    }

    @Override
    public List<PassengerProfileDTO.passengerProfileResponse> findAll() {
        var profiles = passengerProfileRepository.findAll();
        return profiles.stream().map(passengerProfileMapper::toDTO).toList();
    }

    @Override
    public void delete(Long id) {
        var profile = this.getObject(id);
        passengerProfileRepository.delete(profile);
    }
}
