package com.example.airline.Mappers;

import com.example.airline.DTO.FlightDto;
import com.example.airline.DTO.SeatInventoryDTO;
import com.example.airline.DTO.TagDTO;
import com.example.airline.entities.Flight;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FlightMapper {

    public static Flight toEntity(FlightDto.flightCreateRequest createRequest) {
        return Flight.builder().number(createRequest.number()).arrivalTime(createRequest.arrivalTime()).departureTime(createRequest.departureTime()).build();
    }

    public static void path(Flight entity,FlightDto.flightUpdateRequest updateRequest) {
        if (updateRequest.number() != null) {
            entity.setNumber(updateRequest.number());
        }
        if (updateRequest.arrivalTime() != null) {
            entity.setArrivalTime(updateRequest.arrivalTime());
        }
        if (updateRequest.departureTime() != null) {
            entity.setDepartureTime(updateRequest.departureTime());
        }
    }

    public static FlightDto.flightResponse toDTO(Flight entity) {
        var tag = entity.getTags() == null ? Set.<TagDTO.tagResponse>of():
                entity.getTags().stream().map(TagMapper::toDTO).collect(Collectors.toSet());

        var seatInventories = entity.getSeatInventories() == null ? List.<SeatInventoryDTO.seatInventoryFlightView>of():
                entity.getSeatInventories().stream().map(SeatInventoryMapper::seatInventoryFlightView).toList();

        return new FlightDto.flightResponse(entity.getId(), entity.getNumber(), entity.getArrivalTime(), entity.getDepartureTime(), AirlineMapper.airlineFlightView(entity.getAirline()), AirportMapper.AirportFlightView(entity.getOriginAirport()),
                AirportMapper.AirportFlightView(entity.getDestinationAirport()), tag, seatInventories
                );
    }

    public static FlightDto.flightAirportView flightAirportView(Flight entity) {
        var tag = entity.getTags() == null ? Set.<TagDTO.tagResponse>of():
                entity.getTags().stream().map(TagMapper::toDTO).collect(Collectors.toSet());
        return new FlightDto.flightAirportView(entity.getId(), entity.getNumber(), entity.getArrivalTime(), entity.getDepartureTime(),
                AirlineMapper.airlineFlightView(entity.getAirline()), tag
                );
    }

    public static FlightDto.flightAirlineView flightAirlineView(Flight entity) {
        return new FlightDto.flightAirlineView(entity.getId(), entity.getNumber(), entity.getArrivalTime(), entity.getDepartureTime(),
                entity.getTags().stream().map(TagMapper::toDTO).collect(Collectors.toSet())
                );
    }
    }


