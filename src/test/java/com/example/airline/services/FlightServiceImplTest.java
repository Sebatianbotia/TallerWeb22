package com.example.airline.services;

import com.example.airline.Mappers.FlightMapper;
import com.example.airline.Services.FlightServiceImpl;
import com.example.airline.Services.Util.AirlineReferenceResolver;
import com.example.airline.Services.Util.AirportReferenceResolver;
import com.example.airline.entities.Airline;
import com.example.airline.repositories.AirlineRepository;
import com.example.airline.repositories.FlightRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FlightServiceImplTest {
    @Mock
    private FlightRepository flightRepository;
    @Mock
    private FlightMapper flightMapper;
    @Mock
    private AirlineReferenceResolver airlineReferenceResolver;
    @Mock
    private AirportReferenceResolver airportReferenceResolver;
    @InjectMocks
    private FlightServiceImpl flightService;
}
