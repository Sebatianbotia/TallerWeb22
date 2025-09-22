package com.example.airline.repositories;

import com.example.airline.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag,Long> {
Optional<Tag> FindTagByNameIgnoreCase(String name);

List<Tag> FindTagsByNameIn(Collection<String> names);
}
