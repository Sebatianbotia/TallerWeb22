package com.example.airline.Mappers;

import com.example.airline.DTO.TagDTO;
import com.example.airline.entities.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TagMapper {

    Tag toEntity(TagDTO.tagCreateRequest createRequest);

    @Mapping(target = "tagId", source = "id")
    TagDTO.tagResponse toDTO(Tag entity);

    @Mapping(target = "id", ignore = true)
    void updateRequest(TagDTO.tagUpdateRequest updateRequest, @MappingTarget Tag tag);

}
