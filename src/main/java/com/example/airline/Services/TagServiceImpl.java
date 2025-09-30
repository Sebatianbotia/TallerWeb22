package com.example.airline.Services;

import com.example.airline.DTO.TagDTO;
import com.example.airline.Services.Mappers.TagMapper;
import com.example.airline.entities.Tag;
import com.example.airline.repositories.TagRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public class TagServiceImpl implements TagService {
    TagRepository tagRepository;
    @Override
    public TagDTO.tagResponse create(TagDTO.tagCreateRequest createRequest) {

        return TagMapper.toDTO(tagRepository.save(Tag.builder().name(createRequest.name()).build()));
    }

    @Override
    public TagDTO.tagResponse find(Long id) {

        return TagMapper.toDTO(tagRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Tag no encontrado")));
    }
    @Override
    public TagDTO.tagResponse update(TagDTO.tagUpdateRequest updateRequest) {
        Tag tag = findById(updateRequest.tagId());
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
