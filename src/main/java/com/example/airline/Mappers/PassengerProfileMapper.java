package com.example.airline.Services.Mappers;

import com.example.airline.DTO.PassengerProfileDTO;
import com.example.airline.entities.PassengerProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PassengerProfileMapper {
    PassengerProfile toEntity(PassengerProfileDTO.passengerProfileCreateRequest dto);

    @Mapping(target = "passengerProfileID", source = "id")
    PassengerProfileDTO.passengerProfileResponse toDTO(PassengerProfile entity);

    @Mapping(target = "id", ignore = true)
    void updateEntity(PassengerProfileDTO.passengerProfileUpdateRequest dto, @MappingTarget PassengerProfile entity);

    @Mapping(target = "id", ignore = true)
    void path(PassengerProfile entity, PassengerProfileDTO.passengerProfileUpdateRequest updateRequest);
}
