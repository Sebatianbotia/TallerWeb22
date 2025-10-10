package com.example.airline.services;

import com.example.airline.DTO.*;
import com.example.airline.Mappers.*;
import com.example.airline.Services.*;
import com.example.airline.entities.*;
import com.example.airline.repositories.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;



@ExtendWith(MockitoExtension.class)
public class FlightServiceImplTest {
    @Mock
    private FlightRepository flightRepository;
    @InjectMocks
    private FlightServiceImpl  flightService;
    @Mock
    private FlightMapper flightMapper;
    @Mock
    private SeatInventoryServiceImpl  seatInventoryService;
    @Mock
    private AirlineServiceImpl airlineService;
    @Mock
    private AirportServiceImpl airportService;
    @Mock
    private TagServiceImpl tagService;
    @Mock
    private AirlineMapper airlineMapper;
    @Mock
    private AirportMapper airportMapper;
    @Mock
    private TagMapper tagMapper;
    @Mock
    private SeatInventoryMapper seatInventoryMapper;

    Airline airline = Airline.builder().name("hola Aerolinea").code("2222FFF").id(1L).build();
    Airport originAirport = Airport.builder().code("airportOrigne").name("el dorado").id(2L).build();
    Airport destinationAirport = Airport.builder().code("airportdestine").name("el blanco").id(3L).build();
    Tag tag1 = Tag.builder().id(1L).name("economico").build();
    Tag tag2 = Tag.builder().id(2L).name("Caro").build();
    SeatInventory seatInventory1Object = SeatInventory.builder().id(1L).cabin(Cabin.ECONOMY).availableSeats(6).totalSeats(100).build();
    SeatInventory seatInventory2Object = SeatInventory.builder().id(2L).cabin(Cabin.PREMIUM).availableSeats(3).totalSeats(100).build();



    @Test
    void shoulCreateFlightAndReturnFlightResponse(){
        Set<String> tags = new HashSet<>(List.of(tag1, tag2).stream().map(Tag::getName).collect(Collectors.toSet()));
        SeatInventoryDTO.seatInventoryCreateRequest seatinventory1 = new SeatInventoryDTO.seatInventoryCreateRequest(100, 6, Cabin.ECONOMY);
        SeatInventoryDTO.seatInventoryCreateRequest seatinventory2 = new SeatInventoryDTO.seatInventoryCreateRequest(100, 3, Cabin.PREMIUM);
        List<SeatInventoryDTO.seatInventoryCreateRequest>  seatInventoryList = new ArrayList<>(List.of(seatinventory1, seatinventory2));
        var request = new FlightDto.flightCreateRequest("304", OffsetDateTime.parse("2025-10-08T14:30:00-05:00"), OffsetDateTime.parse("2025-10-10T14:30:00-05:00"), airline.getId(), originAirport.getCode(), destinationAirport.getCode(), tags, seatInventoryList);

        when(flightMapper.toEntity(any())).thenAnswer(inb ->{
            FlightDto.flightCreateRequest request2 = inb.getArgument(0);
            return Flight.builder().number(request2.number()).departureTime(request2.departureTime()).arrivalTime(request2.arrivalTime()).build();
        });

        when(airlineService.getObjectById(any())).thenReturn(airline);
        when(airportService.getAirportByCode(any())).thenAnswer(inv -> {
            String code = inv.getArgument(0);
            if (code.equals(originAirport.getCode())) {
                return originAirport;
            }
            if (code.equals(destinationAirport.getCode())) {
                return destinationAirport;
            }
            return null;
        });
        when(tagService.getObjectByName(any())).thenAnswer(inv -> {
            String tagName = inv.getArgument(0);
            if (tagName.equals(tag1.getName())) {
                return tag1;
            }
            if (tagName.equals(tag2.getName())) {
                return tag2;
            }
            return null;
        });
        when(seatInventoryService.createAndReturn(any())).thenAnswer(inv ->{
            SeatInventoryDTO.seatInventoryCreateRequest seatInventoryCreateRequest = inv.getArgument(0);
            if (seatInventoryCreateRequest.cabin().equals(Cabin.ECONOMY)) {
                return seatInventory1Object;
            }
            if (seatInventoryCreateRequest.cabin().equals(Cabin.PREMIUM)) {
                return seatInventory2Object;
            }
            return null;
        });

        when(flightRepository.save(any())).thenAnswer(inv -> {
            Flight  flight = inv.getArgument(0);
            flight.setId(1L);
            return flight;
        });

        when(airlineMapper.toFlightView(any())).thenAnswer(inv -> {
            Airline airline = inv.getArgument(0);
            return new AirlaneDTO.airlineFlightView(airline.getId(), airline.getName(), airline.getCode());
        });

        when(airportMapper.toFlightView(any())).thenAnswer(inv -> {
            Airport airport = inv.getArgument(0);
            return new AirportDTO.AirportFlightView(airport.getId(), airport.getName(), airport.getCode(), airport.getCity());
        });

        when(seatInventoryMapper.toFlightView(any())).thenAnswer(inv -> {
            SeatInventory seatInventory = inv.getArgument(0);
            return new SeatInventoryDTO.seatInventoryFlightView(seatInventory.getId(), seatInventory.getTotalSeats(), seatInventory.getAvailableSeats(), seatInventory.getCabin());
        });

        when(tagMapper.toDTO(any())).thenAnswer(inv -> {
            Tag tag = inv.getArgument(0);
            return new TagDTO.tagResponse(tag.getId(), tag.getName());
        });


        when(flightMapper.toDTO(any())).thenAnswer(inv -> {
            Flight flight = inv.getArgument(0);
            AirlaneDTO.airlineFlightView arlineView = airlineMapper.toFlightView(flight.getAirline());
            AirportDTO.AirportFlightView originAirportView = airportMapper.toFlightView(flight.getOriginAirport());
            AirportDTO.AirportFlightView destinationAirportView = airportMapper.toFlightView(flight.getDestinationAirport());
            Set<TagDTO.tagResponse> tagResponse = flight.getTags().stream().map(tagMapper::toDTO).collect(Collectors.toSet());
            List<SeatInventoryDTO.seatInventoryFlightView> seatInventory = flight.getSeatInventories().stream().map(seatInventoryMapper::toFlightView).toList();


            return new FlightDto.flightResponse(
                flight.getId(), flight.getNumber(), flight.getArrivalTime(), flight.getDepartureTime(), arlineView , originAirportView, destinationAirportView, tagResponse, seatInventory);
        });




        var saved = flightService.create(request);

        assertThat(saved).isNotNull();
        assertThat(saved.number()).isEqualTo("304");
        assertThat(saved.arrivalTime()).isEqualTo(OffsetDateTime.parse("2025-10-08T14:30:00-05:00"));
        assertThat(saved.departureTime()).isEqualTo(OffsetDateTime.parse("2025-10-10T14:30:00-05:00"));
        assertThat(saved.airline().code()).isEqualTo("2222FFF");
        assertThat(saved.originAirport().code()).isEqualTo("airportOrigne");
        assertThat(saved.destinationAirport().code()).isEqualTo("airportdestine");
        assertThat(saved.seatInventories().getFirst().seatInventoryId()).isEqualTo(1L);
        assertThat(saved.seatInventories().getFirst().cabin()).isEqualTo(Cabin.ECONOMY);
        assertThat(saved.seatInventories().get(1).seatInventoryId()).isEqualTo(2L);
        assertThat(saved.seatInventories().get(1).cabin()).isEqualTo(Cabin.PREMIUM);
        assertThat(saved.tags().size()).isEqualTo(2);
        assertThat(saved.tags().stream().toList().getFirst().name()).isEqualTo("economico");
        assertThat(saved.tags().stream().toList().get(1).name()).isEqualTo("Caro");
        verify(flightRepository).save(any(Flight.class));


    }

    @Test
    void shoulUpdateFlightAndResponse(){
        Set<String> tags = new HashSet<>(List.of(tag1, tag2).stream().map(Tag::getName).collect(Collectors.toSet()));
        Flight flightToUpdate = Flight.builder().number("ssss").id(1L).build();
        FlightDto.flightUpdateRequest updateRequest = new FlightDto.flightUpdateRequest("numero", null, null, airline.getId(), originAirport.getCode(),
                destinationAirport.getCode(), tags);

        when(flightRepository.findById(flightToUpdate.getId())).thenReturn(Optional.of(flightToUpdate));

        doAnswer(inv -> {
            FlightDto.flightUpdateRequest  updateRequest1 = inv.getArgument(0);
            Flight f = inv.getArgument(1);

            if (updateRequest.number() != null){
                f.setNumber(updateRequest.number());
            }
            if (updateRequest.arrivalTime() != null){
                f.setArrivalTime(updateRequest.arrivalTime());
            }
            if (updateRequest.departureTime() != null){
                f.setDepartureTime(updateRequest.departureTime());
            }
            return null;
        }).when(flightMapper).patch(any(), any());

        when(airlineService.getObjectById(any())).thenReturn(airline);
        when(airportService.getAirportByCode(any())).thenAnswer(inv -> {
            String code = inv.getArgument(0);
            if (code.equals(originAirport.getCode())) {
                return originAirport;
            }
            if (code.equals(destinationAirport.getCode())) {
                return destinationAirport;
            }
            return null;
        });
        when(tagService.getObjectByName(any())).thenAnswer(inv -> {
            String tagName = inv.getArgument(0);
            if (tagName.equals(tag1.getName())) {
                return tag1;
            }
            if (tagName.equals(tag2.getName())) {
                return tag2;
            }
            return null;
        });

        when(flightRepository.save(any())).thenAnswer(inv -> {
            Flight  flight = inv.getArgument(0);
            flight.setId(1L);
            return flight;
        });

        when(airlineMapper.toFlightView(any())).thenAnswer(inv -> {
            Airline airline = inv.getArgument(0);
            return new AirlaneDTO.airlineFlightView(airline.getId(), airline.getName(), airline.getCode());
        });

        when(airportMapper.toFlightView(any())).thenAnswer(inv -> {
            Airport airport = inv.getArgument(0);
            return new AirportDTO.AirportFlightView(airport.getId(), airport.getName(), airport.getCode(), airport.getCity());
        });


        when(tagMapper.toDTO(any())).thenAnswer(inv -> {
            Tag tag = inv.getArgument(0);
            return new TagDTO.tagResponse(tag.getId(), tag.getName());
        });


        when(flightMapper.toDTO(any())).thenAnswer(inv -> {
            Flight flight = inv.getArgument(0);
            AirlaneDTO.airlineFlightView arlineView = airlineMapper.toFlightView(flight.getAirline());
            AirportDTO.AirportFlightView originAirportView = airportMapper.toFlightView(flight.getOriginAirport());
            AirportDTO.AirportFlightView destinationAirportView = airportMapper.toFlightView(flight.getDestinationAirport());
            Set<TagDTO.tagResponse> tagResponse = null;
            if (flight.getTags() != null) {
                tagResponse = flight.getTags().stream().map(tagMapper::toDTO).collect(Collectors.toSet());
            }
            List<SeatInventoryDTO.seatInventoryFlightView> seatInventory = null;
            if (flight.getSeatInventories() != null) {
                seatInventory = flight.getSeatInventories().stream().map(seatInventoryMapper::toFlightView).toList();
            }

            return new FlightDto.flightResponse(
                    flight.getId(), flight.getNumber(), flight.getArrivalTime(), flight.getDepartureTime(), arlineView , originAirportView, destinationAirportView, tagResponse, seatInventory);
        });

        flightService.update(flightToUpdate.getId(), updateRequest);

        assertThat(flightToUpdate).isNotNull();
        assertThat(flightToUpdate.getNumber()).isEqualTo("numero");
        assertThat(flightToUpdate.getAirline().getCode()).isEqualTo("2222FFF");
        assertThat(flightToUpdate.getOriginAirport().getCode()).isEqualTo("airportOrigne");
        assertThat(flightToUpdate.getDestinationAirport().getCode()).isEqualTo("airportdestine");
        assertThat(flightToUpdate.getTags().size()).isEqualTo(2);
        assertThat(flightToUpdate.getTags().stream().toList().getFirst().getName()).isEqualTo("economico");
        assertThat(flightToUpdate.getTags().stream().toList().get(1).getName()).isEqualTo("Caro");

    }

    @Test
    void findAllFlightAndReturResponse(){

        Flight flight1 =Flight.builder().id(1L).number("numerito").airline(airline).originAirport(originAirport).destinationAirport(destinationAirport).seatInventories(List.of(seatInventory1Object)).build();
        Flight flight2 = Flight.builder().id(2L).number("numeron").airline(airline).originAirport(destinationAirport).destinationAirport(originAirport).seatInventories(List.of(seatInventory2Object)).build();

        List<Flight> flighCreated = List.of(flight1, flight2);

        when(flightRepository.findAll()).thenReturn(flighCreated);

        when(airlineMapper.toFlightView(any())).thenAnswer(inv -> {
            Airline airline = inv.getArgument(0);
            return new AirlaneDTO.airlineFlightView(airline.getId(), airline.getName(), airline.getCode());
        });

        when(airportMapper.toFlightView(any())).thenAnswer(inv -> {
            Airport airport = inv.getArgument(0);
            return new AirportDTO.AirportFlightView(airport.getId(), airport.getName(), airport.getCode(), airport.getCity());
        });

        when(seatInventoryMapper.toFlightView(any())).thenAnswer(inv -> {
            SeatInventory seatInventory = inv.getArgument(0);
            return new SeatInventoryDTO.seatInventoryFlightView(seatInventory.getId(), seatInventory.getTotalSeats(), seatInventory.getAvailableSeats(), seatInventory.getCabin());
        });



        when(flightMapper.toDTO(any())).thenAnswer(inv -> {
            Flight flight = inv.getArgument(0);
            AirlaneDTO.airlineFlightView arlineView = airlineMapper.toFlightView(flight.getAirline());
            AirportDTO.AirportFlightView originAirportView = airportMapper.toFlightView(flight.getOriginAirport());
            AirportDTO.AirportFlightView destinationAirportView = airportMapper.toFlightView(flight.getDestinationAirport());
            Set<TagDTO.tagResponse> tagResponse = null;
            if (flight.getTags() != null) {
                tagResponse = flight.getTags().stream().map(tagMapper::toDTO).collect(Collectors.toSet());
            }
            List<SeatInventoryDTO.seatInventoryFlightView> seatInventory = null;
            if (flight.getSeatInventories() != null) {
                seatInventory = flight.getSeatInventories().stream().map(seatInventoryMapper::toFlightView).toList();
            }

            return new FlightDto.flightResponse(
                    flight.getId(), flight.getNumber(), flight.getArrivalTime(), flight.getDepartureTime(), arlineView , originAirportView, destinationAirportView, tagResponse, seatInventory);
        });

        List<FlightDto.flightResponse> flights = flightService.findAll();

        assertThat(flights).isNotNull();
        assertThat(flights).isNotEmpty();
        assertThat(flights.size()).isEqualTo(2);
        assertThat(flights.getFirst().flightId()).isEqualTo(1L);
        assertThat(flights.getFirst().number()).isEqualTo("numerito");
        assertThat(flights.getFirst().originAirport().code()).isEqualTo("airportOrigne");
        assertThat(flights.getFirst().destinationAirport().code()).isEqualTo("airportdestine");
        assertThat(flights.getFirst().seatInventories().size()).isEqualTo(1);
        assertThat(flights.get(1).flightId()).isEqualTo(2L);
        assertThat(flights.get(1).number()).isEqualTo("numeron");
        assertThat(flights.get(1).originAirport().code()).isEqualTo("airportdestine");
        assertThat(flights.get(1).destinationAirport().code()).isEqualTo("airportOrigne");
        assertThat(flights.get(1).seatInventories().size()).isEqualTo(1);

    }
}
