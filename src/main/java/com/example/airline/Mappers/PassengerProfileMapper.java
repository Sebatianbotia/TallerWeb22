package com.example.airline.Mappers;

import com.example.airline.DTO.PassengerDTO;
import com.example.airline.DTO.PassengerProfileDTO;
import com.example.airline.entities.PassengerProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PassengerProfileMapper {

    @Mapping(target = "phone", source = "phoneNumber")
    PassengerProfile toEntity(PassengerProfileDTO.passengerProfileCreateRequest dto);

    @Mapping(target = "phoneNumber", source = "phone")
    @Mapping(target = "passengerProfileID", source = "id")
    PassengerProfileDTO.passengerProfileResponse toDTO(PassengerProfile entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phone", source = "phoneNumber" )
    void updateEntity(PassengerProfileDTO.passengerProfileUpdateRequest dto, @MappingTarget PassengerProfile entity);

    PassengerProfileDTO.passengerProfileView toPassengerProfileView(PassengerProfile passengerProfile);
}
