package com.example.airline.Mappers;

import com.example.airline.DTO.TagDTO;
import com.example.airline.entities.Flight;
import com.example.airline.entities.Tag;
import com.example.airline.repositories.FlightRepository;
import com.example.airline.repositories.TagRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class TagMapper {
    private FlightRepository flightRepository;
    private TagRepository tagRepository;

    public Tag toEntity(TagDTO.tagCreateRequest createRequest) {
        Tag tag = Tag.builder().name(createRequest.name()).flights(getSetFlightsWithID(createRequest.flightIds())).build();
        return tag;
    }

    public Tag updateRequest(TagDTO.tagUpdateRequest updateRequest) {
        if (updateRequest == null) {
            return null;
        }
        Tag foundTag = tagRepository.findById(updateRequest.tagId()).orElseThrow(() -> new EntityNotFoundException("Tag con id: " + updateRequest.tagId() + " no encontrado"));
        if (updateRequest.name() != null) {
            foundTag.setName(updateRequest.name());
        }
        if (updateRequest.flightIds() != null) {
            foundTag.setFlights(getSetFlightsWithID(updateRequest.flightIds()));
        }
        return foundTag;
    }
    // Se termina cuando se tenga toDTO de flight
    public TagDTO.tagResponse toDTO(Long tagId){
        Tag foundTag = tagRepository.findById(tagId).orElseThrow(() -> new EntityNotFoundException("Tag con id: " + tagId + " no encontrado"));
        return new TagDTO.tagResponse(foundTag.getId(), foundTag.getName(), foundTag.getFlights());
    }

    private Set<Flight> getSetFlightsWithID(Set<Long> flightsIds) {
        if (flightsIds == null || flightsIds.isEmpty()) {
            return new HashSet<>();
        }
        List<Flight> foundFlights = flightRepository.findAllById(flightsIds);

        if (foundFlights.size() != flightsIds.size()) {
            System.out.println("Algunos vuelos no se encontraron, verifica las IDS de los vuelos");
        }

        return new HashSet<>(foundFlights);
    }
}
