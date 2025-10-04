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

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {
    @Mock
    private TagRepository tagRepository;
    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    void shouldCreateAndReturnTagResponse(){
        TagDTO.tagCreateRequest request = new TagDTO.tagCreateRequest("Economico");
        when(tagRepository.save(any())).thenAnswer(i -> {
            Tag tag = i.getArgument(0);
            tag.setId(1L);
            return tag;
        });

        var saved = tagService.create(request);

        assertThat(saved).isNotNull();
        assertThat(saved.name()).isEqualTo("Economico");
        verify(tagRepository.save(any()));

    }
}
