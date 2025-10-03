package com.example.airline.Mappers;

import com.example.airline.DTO.PassengerDTO;
import com.example.airline.DTO.PassengerProfileDTO;
import com.example.airline.entities.PassengerProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PassengerProfileMapper {
    @Mapping(target = "id", ignore = true )
    PassengerProfile toEntity(PassengerProfileDTO.passengerProfileCreateRequest dto);

    @Mapping(target = "passengerProfileID", source = "id")
    PassengerProfileDTO.passengerProfileResponse toDTO(PassengerProfile entity);

    @Mapping(target = "id", ignore = true)
    void updateEntity(PassengerProfileDTO.passengerProfileUpdateRequest dto, @MappingTarget PassengerProfile entity);

    PassengerDTO.passengerProfileView toPassengerProfileView(PassengerProfile passengerProfile);
}
