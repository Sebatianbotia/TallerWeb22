package com.example.airline.Services;

import com.example.airline.DTO.TagDTO;
import com.example.airline.entities.Tag;

import java.util.List;

public interface TagService {
    TagDTO.tagResponse create(TagDTO.tagCreateRequest createRequest);
    TagDTO.tagResponse find(Long id);
    TagDTO.tagResponse update(Long id,TagDTO.tagUpdateRequest updateRequest);
    List<TagDTO.tagResponse> findAll();
    void delete(Long id);

}
