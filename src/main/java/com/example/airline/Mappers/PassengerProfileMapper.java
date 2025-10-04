package com.example.airline.Mappers;

import com.example.airline.DTO.PassengerDTO;
import com.example.airline.DTO.PassengerProfileDTO;
import com.example.airline.entities.PassengerProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PassengerProfileMapper {

    PassengerProfile toEntity(PassengerProfileDTO.passengerProfileCreateRequest dto);

    PassengerProfileDTO.passengerProfileResponse toDTO(PassengerProfile entity);

    @Mapping(target = "id", ignore = true)
    void updateEntity(PassengerProfileDTO.passengerProfileUpdateRequest dto, @MappingTarget PassengerProfile entity);

    PassengerDTO.passengerProfileView toPassengerProfileView(PassengerProfile passengerProfile);
}
