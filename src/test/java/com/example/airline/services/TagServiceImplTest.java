package com.example.airline.services;

import com.example.airline.DTO.AirlaneDTO;
import com.example.airline.DTO.TagDTO;
import com.example.airline.Mappers.TagMapper;
import com.example.airline.entities.Airline;
import com.example.airline.entities.Tag;
import com.example.airline.repositories.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {
    @Mock
    private TagRepository tagRepository;
    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private TagMapper tagMapper;

    @Test
    void shouldCreateAndReturnTagResponse(){
        TagDTO.tagCreateRequest request = new TagDTO.tagCreateRequest("Economico");
        when(tagRepository.save(any())).thenAnswer(i -> {
            Tag tag = i.getArgument(0);
            tag.setId(1L);
            return tag;
        });

        when(tagMapper.toEntity(any())).thenAnswer(i -> {
            TagDTO.tagCreateRequest request1 = i.getArgument(0);
            Tag object = Tag.builder().name(request1.name()).build();
            return object;
        });

        when(tagMapper.toDTO(any())).thenAnswer(i -> {
            Tag object = i.getArgument(0);
            return new TagDTO.tagResponse(object.getId(), object.getName());
        });

        var saved = tagService.create(request);

        assertThat(saved).isNotNull();
        assertThat(saved.name()).isEqualTo("Economico");
        assertThat(saved.tagId()).isEqualTo(1L);
        verify(tagRepository).save(any(Tag.class));

    }

    @Test
    void shouldUpdatetagAndReturnTagResponse(){
        Tag tag = Tag.builder().name("Economico").id(2).build();

        // datos a actualizar

        TagDTO.tagUpdateRequest request = new TagDTO.tagUpdateRequest("Premium");

        when(tagRepository.findById(any())).thenReturn(Optional.of(tag));

        doAnswer(inv -> {
            TagDTO.tagUpdateRequest request1 = inv.getArgument(0);
            Tag object = inv.getArgument(1);

            if (request1.name() != null){
                object.setName(request1.name());
            }
            return null;

        }).when(tagMapper).updateRequest(any(), any());



        tagService.update(tag.getId(), request);

        assertThat(tag).isNotNull();
        assertThat(tag.getName()).isEqualTo("Premium");




    }

    @Test
    void shouldFindAllAndReturPage(){
        var tag1 = Tag.builder().id(1L).name("Economico").build();
        var tag2 = Tag.builder().id(2L).name("Gratis").build();
        Page<Tag> page = new PageImpl<>(List.of(tag1, tag2));


        when(tagRepository.findAll(PageRequest.of(0,2))).thenReturn(page);

        when(tagMapper.toDTO(any())).thenAnswer(i -> {
            Tag object = i.getArgument(0);
            return new TagDTO.tagResponse(object.getId(), object.getName());
        });

        Page<TagDTO.tagResponse> pages = tagService.list(PageRequest.of(0, 2));

        assertNotNull(pages);
        assertThat(pages.getTotalElements()).isEqualTo(2);
        assertThat(pages.getTotalPages()).isEqualTo(1);
        assertThat(pages.getContent()).hasSize(2);
        assertThat(pages.getContent().get(1).tagId()).isEqualTo(2L);
        assertThat(pages.getContent().get(1).name()).isEqualTo("Gratis");
        assertThat(pages.getContent().get(0).tagId()).isEqualTo(1L);
        assertThat(pages.getContent().get(0).name()).isEqualTo("Economico");






    }

}
