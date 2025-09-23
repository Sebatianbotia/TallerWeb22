package com.example.airline.repositories;


import com.example.airline.AbstractRepositoryPSQL;
import com.example.airline.entities.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class TagRepositoryTest extends AbstractRepositoryPSQL {

    @Autowired
    private TagRepository tagRepository;

    @Test
    @DisplayName("Buscar etiqueta por nombre")
    void shouldFindTagByNameIgnoringCase() {
        String tagName = "Wi-Fi";
        tagRepository.save(Tag.builder().name(tagName).build());

        Optional<Tag> foundTag = tagRepository.findTagByNameIgnoreCase("wi-fi");

        assertThat(foundTag).isPresent();
        assertThat(foundTag.get().getName()).isEqualTo(tagName);
    }



    @Test
    @DisplayName("Retorna todos lso tags que estén en una lista dada")
    void shouldFindTagsByNamesInCollection() {
        Tag tag1 = Tag.builder().name("on-time").build();
        Tag tag2 = Tag.builder().name("pet-friendly").build();
        Tag tag3 = Tag.builder().name("wifi").build();
        tagRepository.saveAll(List.of(tag1, tag2, tag3));

        Collection<String> namesToFind = List.of("on-time", "wifi");
        List<Tag> foundTags = tagRepository.findTagsByNameIn(namesToFind);

        assertThat(foundTags).hasSize(2);
        assertThat(foundTags).extracting(Tag::getName).containsExactlyInAnyOrder("on-time", "wifi");
    }
}