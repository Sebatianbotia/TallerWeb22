package com.example.airline.Services;

import com.example.airline.DTO.FlightDto;
import com.example.airline.Services.Mappers.FlightMapper;
import com.example.airline.entities.Flight;
import com.example.airline.repositories.FlightRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Override
    public FlightDto.flightResponse create(FlightDto.flightCreateRequest createRequest) {
        var flight = FlightMapper.toEntity(createRequest);
        var originAirport = airportService.getObjectById(createRequest.originAirportCode());
        var destinationAirport = airportService.getObjectById(createRequest.destinationAirportCode());
        var airline = airlineService.getObjectById(createRequest.airlineId());
        var tag = createRequest.tags().stream().map(tagService::findTagByName).collect(Collectors.toSet());
        var seatInventories = createRequest.seatInventories().stream().map(seatInventoryService::createAndReturn).toList();


        flight.setOriginAirport(originAirport);
        flight.setDestinationAirport(destinationAirport);
        flight.setAirline(airline);
        flight.setTags(tag);
        flight.setSeatInventories(seatInventories);


        originAirport.addFlightOrigin(flight);
        destinationAirport.addFlightDestination(flight);
        airline.addFlight(flight);
        tag.forEach(tag2 -> tag2.addFlight(flight));
        seatInventories.forEach(seat -> seat.setFlight(flight));


        var saveFlight = flightRepository.save(flight);
        return FlightMapper.toDTO(saveFlight);

    }

    @Override
    public FlightDto.flightResponse update(Long id, FlightDto.flightUpdateRequest updateRequest) {
        var flight = getFlightObject(id);
        FlightMapper.path(flight, updateRequest);
        if (updateRequest.airlineId() != null){
            var airline = airlineService.getObjectById(updateRequest.airlineId());
            flight.getAirline().getFlights().remove(flight);
            flight.setAirline(airline);
            airline.addFlight(flight);
        }
        if (updateRequest.originAirportId() != null){
            var originAirport = airportService.getObjectById(updateRequest.originAirportId());
            flight.getOriginAirport().getFlightsOrigin().remove(flight);
            flight.setOriginAirport(originAirport);
            originAirport.addFlightOrigin(flight);
        }
        if (updateRequest.destinationAirportId() != null){
            var destinationAirport = airportService.getObjectById(updateRequest.destinationAirportId());
            flight.getDestinationAirport().getFlightsDestination().remove(flight);
            flight.setDestinationAirport(destinationAirport);
            destinationAirport.addFlightDestination(flight);
        }
        if (updateRequest.tags() != null){
            var newTags = updateRequest.tags().stream().map(tagService::findTagByName).collect(Collectors.toSet());
            var oldTags = flight.getTags();
            oldTags.forEach(oldTag -> oldTag.getFlights().remove(flight));
            flight.setTags(newTags);
            newTags.forEach(newTag -> newTag.getFlights().add(flight));
        }
        return FlightMapper.toDTO(flightRepository.save(flight));
    }

    @Override
    public FlightDto.flightResponse find(Long id) {
        return FlightMapper.toDTO(getFlightObject(id));
    }

    @Override
    public List<FlightDto.flightResponse> findAll() {
        return flightRepository.findAll().stream().map(FlightMapper::toDTO).toList();
    }

    @Override
    public void delete(Long id) {
        flightRepository.deleteById(id);
    }

    @Override
    public Flight getFlightObject(Long id) {
        var f =  flightRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "flight with id: " + id + " not found"
        ));
        return f;
    }
}
