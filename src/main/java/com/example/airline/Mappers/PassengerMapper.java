package com.example.airline.Mappers;

import com.example.airline.DTO.PassengerDTO;
import com.example.airline.entities.Passenger;
import com.example.airline.entities.PassengerProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {PassengerProfileMapper.class})
public interface PassengerMapper {
    @Mapping(target = "passengerProfile", ignore = true)
    @Mapping(target = "id", ignore = true)
    Passenger toEntity(PassengerDTO.passengerCreateRequest dto);

    @Mapping(target ="passengerProfile" ,source = "profile")
    PassengerDTO.passengerResponse toDTO(Passenger entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profile", ignore = true)
    void updateEntity(@MappingTarget Passenger passenger, PassengerDTO.passengerUpdateRequest updateRequest);
}
