package com.example.airline.Services;

import com.example.airline.DTO.TagDTO;
import com.example.airline.entities.Tag;

import java.util.List;

public class TagServiceImpl implements TagService {
    @Override
    public TagDTO.tagResponse create(TagDTO.tagCreateRequest createRequest) {
        return null;
    }

    @Override
    public TagDTO.tagResponse find(Long id) {
        return null;
    }

    @Override
    public Tag findTagObject(Long id) {
        return null;
    }

    @Override
    public TagDTO.tagResponse update(TagDTO.tagUpdateRequest updateRequest) {
        return null;
    }

    @Override
    public List<TagDTO.tagResponse> findAll() {
        return List.of();
    }

    @Override
    public void delete(Long id) {

    }
}
