package com.example.airline.Services;

import com.example.airline.DTO.TagDTO;
import com.example.airline.Mappers.TagMapper;
import com.example.airline.Services.Mappers.TagMapper;
import com.example.airline.entities.Tag;
import com.example.airline.repositories.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public TagDTO.tagResponse create(TagDTO.tagCreateRequest createRequest) {
        var tag = tagMapper.toEntity(createRequest);
        return tagMapper.toDTO(tagRepository.save(tag));
    }

    @Override
    public TagDTO.tagResponse find(Long id) {
        return tagMapper.toDTO(findById(id));
    }

    @Override
    public TagDTO.tagResponse update(Long id, TagDTO.tagUpdateRequest updateRequest) {
        var tag = findById(id);
        tagMapper.updateRequest(updateRequest, tag);
        return tagMapper.toDTO(tag);
    }

    @Override
    public List<TagDTO.tagResponse> findAll() {
        var tags = tagRepository.findAll();
        return tags.stream().map(tagMapper::toDTO).toList();
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
