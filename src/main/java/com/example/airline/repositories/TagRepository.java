package com.example.airline.repositories;

import com.example.airline.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Long> {
Tag FindTagByName(String name);
}
