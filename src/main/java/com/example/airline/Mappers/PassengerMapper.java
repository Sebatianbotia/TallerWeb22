package com.example.airline.Services.Mappers;

import com.example.airline.DTO.PassengerDTO;
import com.example.airline.entities.Passenger;
import com.example.airline.entities.PassengerProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PassengerMapper {
    @Mapping(target = "profile", ignore = true)
    Passenger toEntity(PassengerDTO.passengerCreateRequest dto);

    @Mapping(target = "passengerProfile", source = "profile")
    PassengerDTO.passengerResponse toDTO(Passenger entity);

    default PassengerDTO.passengerProfileView mapProfileToView(PassengerProfile profile) {
        if (profile == null) {
            return null;
        }
        return new PassengerDTO.passengerProfileView(
                profile.getPhone(),
                profile.getCountryCode()
        );
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profile", ignore = true)
    void path(Passenger passenger, PassengerDTO.passengerUpdateRequest updateRequest);
}
