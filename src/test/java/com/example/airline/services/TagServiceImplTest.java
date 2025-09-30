package com.example.airline.services;

import com.example.airline.DTO.TagDTO;
import com.example.airline.Services.TagServiceImpl;
import com.example.airline.entities.Tag;
import com.example.airline.repositories.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {
    @Mock
    private TagRepository tagRepository;
    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    void shouldCreateAndReturnResponseDTO(){
        var tagRequest = new TagDTO.tagCreateRequest("Economico");
        when(tagRepository.save(any())).thenAnswer(i -> {
            Tag tag = i.getArgument(0);
            tag.setId(22L);
            return tag;
        });

        var saved = tagService.create(tagRequest);

        assertThat(saved).isNotNull();
        assertThat(saved.tagId()).isEqualTo(22L);
        assertThat(saved.name()).isEqualTo("Economico");
        verify(tagRepository).save(any(Tag.class));
    }

    @Test
    void shouldUpdateAndReturnResponseDTO(){
        var tagObject = Tag.builder().id(1L).name("Economico").build();
        when(tagRepository.findById(1L)).thenReturn(Optional.of(tagObject));

        var updateRequest = new TagDTO.tagUpdateRequest("Privado");
        var saved = tagService.update(1L, updateRequest);

        assertThat(saved).isNotNull();
        assertThat(saved.name()).isEqualTo("Privado");
    }

    @Test
    void shouldFindAllAndReturnResponseDTO(){
        var tag1 = Tag.builder().id(1L).name("Economico").build();
        var tag2 = Tag.builder().id(2L).name("VIP").build();

        List<Tag> tags = List.of(tag1, tag2);

        when(tagRepository.findAll()).thenReturn(tags);
        var savedTags = tagService.findAll();

        assertThat(savedTags).isNotNull();
        assertThat(savedTags).hasSize(2);

        assertThat(savedTags.get(0).tagId()).isEqualTo(1L);
        assertThat(savedTags.get(0).name()).isEqualTo("Economico");

        assertThat(savedTags.get(1).tagId()).isEqualTo(2L);
        assertThat(savedTags.get(1).name()).isEqualTo("VIP");
    }
}
