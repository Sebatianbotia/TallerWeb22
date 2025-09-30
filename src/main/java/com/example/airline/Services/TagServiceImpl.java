package com.example.airline.Services;

import com.example.airline.DTO.TagDTO;
import com.example.airline.Services.Mappers.TagMapper;
import com.example.airline.entities.Tag;
import com.example.airline.repositories.TagRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public class TagServiceImpl implements TagService {
    TagRepository tagRepository;
    @Override
    public TagDTO.tagResponse create(TagDTO.tagCreateRequest createRequest) {
        return TagMapper.toDTO(tagRepository.save(TagMapper.toEntity(createRequest)));
    }

    @Override
    public TagDTO.tagResponse find(Long id) {
        return TagMapper.toDTO(findById(id));
    }
    @Override
    public TagDTO.tagResponse update(Long id,TagDTO.tagUpdateRequest updateRequest) {
        var tag = findById(id);
        TagMapper.updateRequest(tag, updateRequest);
        return TagMapper.toDTO(tag);
    }

    @Override
    public List<TagDTO.tagResponse> findAll() {
        return tagRepository.findAll().stream().map(TagMapper::toDTO).toList();
    }

    @Override
    public void delete(Long id) {
        tagRepository.deleteById(id);
    }


    public Tag findById(Long id) {
        return tagRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tag no encontrado"));
    }

    public Tag findTagByName(String name) {
        return tagRepository.findTagByNameIgnoreCase(name).orElseThrow(() -> new EntityNotFoundException("Tag no encontrado"));
    }
}
