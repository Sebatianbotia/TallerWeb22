package com.example.airline.services;

import com.example.airline.DTO.TagDTO;
import com.example.airline.Mappers.TagMapper;
import com.example.airline.Services.TagServiceImpl;
import com.example.airline.entities.Tag;
import com.example.airline.repositories.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.CollectionUtils;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

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

}
