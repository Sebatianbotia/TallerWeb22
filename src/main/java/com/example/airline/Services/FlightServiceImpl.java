package com.example.airline.Services;

import com.example.airline.DTO.FlightDto;
import com.example.airline.Mappers.FlightMapper;
import com.example.airline.entities.Airline;
import com.example.airline.entities.Airport;
import com.example.airline.entities.Flight;
import com.example.airline.entities.Tag;
import com.example.airline.repositories.FlightRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Transactional
public class FlightServiceImpl implements FlightService{

    private final FlightRepository flightRepository;
    private final AirportServiceImpl airportService;
    private final AirlineServiceImpl airlineService;
    private final TagServiceImpl tagService;
    private final SeatInventoryServiceImpl seatInventoryService;
    private final FlightMapper flightMapper;

    @Override
    public FlightDto.flightResponse create(FlightDto.flightCreateRequest request) {
        var flight = flightMapper.toEntity(request);
        if (request.aerlineId() != null) {
            Airline airline = airlineService.getObjectById(request.aerlineId());
            flight.setAirline(airline);
            airline.addFlight(flight);
        }

        if (request.originAirportCode() != null) {
            Airport origin = airportService.getAirportByCode(request.originAirportCode());
            flight.setOriginAirport(origin);
            origin.addFlightOrigin(flight);
        }

        if (request.destinationAirportCode()!= null) {
            Airport destination = airportService.getAirportByCode(request.destinationAirportCode());
            flight.setDestinationAirport(destination);
            destination.addFlightDestination(flight);
        }
        if (request.tags() != null) {
            Set<Tag> tags = request.tags().stream().map(tagService::getObjectByName).collect(Collectors.toSet());
            flight.setTags(tags);
            tags.forEach(tag -> tag.addFlight(flight));
        }
        var seatInventories = request.seatInventories().stream().map(seatInventoryService::createAndReturn).toList();
        flight.setSeatInventories(seatInventories);
        flight.getTags().forEach(tag2 -> tag2.addFlight(flight));
        seatInventories.forEach(seat -> seat.setFlight(flight));

        var saveFlight = flightRepository.save(flight);
        return flightMapper.toDTO(saveFlight);

    }

    @Override
    @Transactional
    public FlightDto.flightResponse update(Long id, FlightDto.flightUpdateRequest updateRequest) {
        Flight flight = getFlightObject(id);

        flightMapper.patch(updateRequest, flight);

        if (updateRequest.airlineId() != null) {
            Airline airline = airlineService.getObjectById(updateRequest.airlineId());
            flight.setAirline(airline);
            airline.addFlight(flight);
        }

        if (updateRequest.originAirportCode() != null) {
            Airport origin = airportService.getAirportByCode(updateRequest.originAirportCode());
            flight.setOriginAirport(origin);
            origin.addFlightOrigin(flight);
        }

        if (updateRequest.destinationAirportCode() != null) {
            Airport destination = airportService.getAirportByCode(updateRequest.destinationAirportCode());
            flight.setDestinationAirport(destination);
            destination.addFlightDestination(flight);
        }

        if (updateRequest.tags() != null) {
            if (updateRequest.tags().isEmpty()) {
                flight.clearTags();
            } else {
                Set<Tag> tags = updateRequest.tags().stream().map(tagService::getObjectByName).collect(Collectors.toSet());
                flight.setTags(tags);
                tags.forEach(tag -> tag.addFlight(flight));
            }
        }
        Flight updatedFlight = flightRepository.save(flight);
        return flightMapper.toDTO(updatedFlight);
    }

    @Override
    @Transactional(readOnly = true)
    public FlightDto.flightResponse get(Long id) {
        return flightMapper.toDTO(getFlightObject(id));
    }

    @Override
    public List<FlightDto.flightResponse> findAll() {
        return flightRepository.findAll().stream().map(flightMapper::toDTO).toList();
    }

    @Override
    public void delete(Long id) {
        flightRepository.deleteById(id);//Revisar el borrado en cascada
    }

    @Override
    public Flight getFlightObject(Long id) {
        var f =  flightRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "flight with id: " + id + " not found"
        ));
        return f;
    }
}