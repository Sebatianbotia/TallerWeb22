package com.example.airline.Services.Mappers;

import com.example.airline.DTO.AirlaneDTO;
import com.example.airline.DTO.FlightDto;
import com.example.airline.DTO.SeatInventoryDTO;
import com.example.airline.DTO.TagDTO;
import com.example.airline.entities.Airline;
import com.example.airline.entities.Flight;
import com.example.airline.entities.SeatInventory;
import com.example.airline.entities.Tag;
import com.example.airline.repositories.AirlineRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AirlineMapper {
    private AirlineRepository airlineRepository;

    public static Airline toEntity(AirlaneDTO.airlineCreateRequest createrequest) {
        Airline airline = Airline.builder().name(createrequest.name()).code(createrequest.code()).build();
        return airline;
    }

    public static void updateEntity(Airline airline, AirlaneDTO.airlineUpdateRequest updateRequest) {

    if (updateRequest.name() != null) {
        airline.setName(updateRequest.name());
    }
    if (updateRequest.code() != null) {
        airline.setCode(updateRequest.code());
    }
    }


    public static AirlaneDTO.airlineResponse toDTO(Airline airline) {
        var flights = airline.getFlights() == null ? List.<FlightDto.flightAirlineView>of():
                airline.getFlights().stream().map(FlightMapper::flightAirlineView).toList();
        return new AirlaneDTO.airlineResponse(airline.getId(), airline.getName(), airline.getCode(), flights);
    }

    public static AirlaneDTO.airlineFlightView airlineFlightView(Airline airline) {
        return  new AirlaneDTO.airlineFlightView(airline.getId(), airline.getName(), airline.getCode());
    }



}
