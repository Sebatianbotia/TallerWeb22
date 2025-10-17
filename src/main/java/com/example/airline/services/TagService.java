package com.example.airline.services;

import com.example.airline.DTO.TagDTO;
import com.example.airline.entities.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    TagDTO.tagResponse create(TagDTO.tagCreateRequest createRequest);

    TagDTO.tagResponse get(Long id);

    TagDTO.tagResponse update(Long id, TagDTO.tagUpdateRequest updateRequest);

    Page<TagDTO.tagResponse> list(Pageable  pageable);

    void delete(Long id);

    Tag getObject(Long id);

    Tag getObjectByName(String name);

    TagDTO.tagResponse getByName(String name);
}
