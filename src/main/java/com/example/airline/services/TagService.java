package com.example.airline.services;

import com.example.airline.DTO.TagDTO;
import com.example.airline.entities.Tag;

import java.util.List;

public interface TagService {

    TagDTO.tagResponse create(TagDTO.tagCreateRequest createRequest);

    TagDTO.tagResponse get(Long id);

    TagDTO.tagResponse update(Long id, TagDTO.tagUpdateRequest updateRequest);

    List<TagDTO.tagResponse> findAll();

    void delete(Long id);

    Tag getObject(Long id);

    Tag getObjectByName(String name);

    TagDTO.tagResponse getByName(String name);
}
