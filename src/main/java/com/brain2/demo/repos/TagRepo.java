package com.brain2.demo.repos;

import java.util.Optional;

import com.brain2.demo.models.Tag;

import org.springframework.data.repository.CrudRepository;

public interface TagRepo extends CrudRepository<Tag, Long> {

    Optional<Tag> findByName(String name);
    Iterable<Tag> findAllByNameIn(Iterable<String> names);
}