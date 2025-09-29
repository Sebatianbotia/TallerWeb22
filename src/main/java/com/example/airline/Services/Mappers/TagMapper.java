package com.example.airline.Services.Mappers;

import com.example.airline.DTO.TagDTO;
import com.example.airline.entities.Flight;
import com.example.airline.entities.Tag;
import jakarta.persistence.EntityNotFoundException;

import java.util.HashSet;
import java.util.Set;


public class TagMapper {

    public static Tag toEntity(TagDTO.tagCreateRequest createRequest, Flight flight) {
        Tag tag = Tag.builder().name(createRequest.name()).flights.add(flight).build();
        return tag;
    }

    public static Tag updateRequest(TagDTO.tagUpdateRequest updateRequest) {
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
    public static TagDTO.tagResponse toDTO(Long tagId){
        Tag foundTag = tagRepository.findById(tagId).orElseThrow(() -> new EntityNotFoundException("Tag con id: " + tagId + " no encontrado"));
        return new TagDTO.tagResponse(foundTag.getId(), foundTag.getName());
    }

    public static TagDTO.tagResponse toDTO(Tag tag){
        return new TagDTO.tagResponse(tag.getId(), tag.getName());
    }

    public static Set<TagDTO.tagResponse> toDTO(Set<Tag> tags){
        if (tags == null || tags.isEmpty()) return null;
        Set<TagDTO.tagResponse> tagResponseList = new HashSet<>();
        for (Tag tag : tags) {
            tagResponseList.add(toDTO(tag));
        }
        return tagResponseList;
    }
}
