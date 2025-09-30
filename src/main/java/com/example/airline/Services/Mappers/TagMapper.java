package com.example.airline.Services.Mappers;

import com.example.airline.DTO.TagDTO;
import com.example.airline.entities.Flight;
import com.example.airline.entities.Tag;
import jakarta.persistence.EntityNotFoundException;

import java.util.HashSet;
import java.util.Set;


public class TagMapper {



    public static void updateRequest(Tag foundTag, TagDTO.tagUpdateRequest request) {
        if (request == null) {
            return;
        }
        if (request.name() != null) {
            foundTag.setName(request.name());
        }
    }
    // Se termina cuando se tenga toDTO de flight
    public static TagDTO.tagResponse toDTO(Tag tag){
        return new TagDTO.tagResponse(tag.getId(), tag.getName());
    }
    // este toDTO para que es????????????????????
    public static Set<TagDTO.tagResponse> toDTO(Set<Tag> tags){
        if (tags == null || tags.isEmpty()) return null;
        Set<TagDTO.tagResponse> tagResponseList = new HashSet<>();
        for (Tag tag : tags) {
            tagResponseList.add(toDTO(tag));
        }
        return tagResponseList;
    }
}
