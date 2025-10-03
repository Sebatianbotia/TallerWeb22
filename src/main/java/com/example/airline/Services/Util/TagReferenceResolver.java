package com.example.airline.Services.Util;

import com.example.airline.entities.Tag;
import com.example.airline.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class TagReferenceResolver {
    private final TagRepository tagRepository;

    public Set<Tag> resolveTagsByName(Set<String> tagNames) {
        if(tagNames==null || tagNames.isEmpty())
            return new HashSet<>(tagRepository.findAll());
        return new HashSet<Tag>(tagRepository.findTagsByNameIn(tagNames));
    }

}

