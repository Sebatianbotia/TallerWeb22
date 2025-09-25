package com.example.airline.Mappers;

import com.example.airline.DTO.AirlaneDTO;
import com.example.airline.entities.Airline;
import com.example.airline.repositories.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AirlineMapper {
    private static AirlineRepository repository;

    public static boolean airlineCreateRequest(AirlaneDTO.airlineCreateRequest createRequest) {
        boolean check = false;
        if (createRequest.name() != null && !createRequest.name().isEmpty() && createRequest.code() != null && !createRequest.code().isEmpty()) {
            Airline airline = Airline.builder().name(createRequest.name()).code(createRequest.code()).build();
            repository.save(airline);
            check = true;
        }
        return check;
    }

}
