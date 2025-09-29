package com.example.airline.Services.Mappers;

import com.example.airline.DTO.AirlaneDTO;
import com.example.airline.DTO.FlightDto;
import com.example.airline.DTO.SeatInvetoryDTO;
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


    public static AirlaneDTO.airlineDtoResponse toDTO(Airline airline) {
        var flights = airline.getFlights()==null?List.<FlightDto.flightResponse>of():
                airline.getFlights().stream().map(AirlineMapper::flightResponse).toList();
        return new AirlaneDTO.airlineDtoResponse(airline.getId(), airline.getName(), airline.getCode(), flights);
    }



    private static FlightDto.flightResponse flightResponse(Flight i) {
        var tags = i.getTags()==null?List.<TagDTO.tagResponse>of():
                i.getTags().stream().map(AirlineMapper::tagResponse).toList();
        var seatInventories = i.getSeatInventories() == null ? List.<SeatInvetoryDTO.seatInventoryDtoResponse>of():
                i.getSeatInventories().stream().map(AirlineMapper::seatInventoryResponse).toList();
        return new FlightDto.flightResponse(
                i.getId(),
                i.getNumber(),
                i.getArrivalTime(),
                i.getDepartureTime(),
                i.getAirline().getId(),
                i.getOriginAirport().getId(),
                i.getDestinationAirport().getId(),
                tags,
                seatInventories);

    }

    private static TagDTO.tagResponse tagResponse(Tag i) {
        return new TagDTO.tagResponse(
                i.getId(),
                i.getName());
    }

    private static SeatInvetoryDTO.seatInventoryDtoResponse seatInventoryResponse(SeatInventory i){
        return new SeatInvetoryDTO.seatInventoryDtoResponse(
                i.getId(),
                i.getTotalSeats(),
                i.getAvailableSeats(),
                i.getCabin()
        );
    }
}
