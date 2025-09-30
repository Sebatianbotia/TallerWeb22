package com.example.airline.Services.Mappers;

import com.example.airline.DTO.PassengerProfileDTO;
import com.example.airline.DTO.TagDTO;
import com.example.airline.entities.Flight;
import com.example.airline.entities.Tag;
import jakarta.persistence.EntityNotFoundException;

import java.util.HashSet;
import java.util.Set;


public class TagMapper {

    public static Tag toEntity(TagDTO.tagCreateRequest createRequest) {
        return Tag.builder().name(createRequest.name()).build();
    }


    public static void updateRequest(Tag entity, TagDTO.tagUpdateRequest request) {
        if (request.name() != null) {
            entity.setName(request.name());
        }
    }


    public static TagDTO.tagResponse toDTO(Tag tag){
        return new TagDTO.tagResponse(tag.getId(), tag.getName());
    }
}
