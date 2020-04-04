package com.brain1.core.repos;

import java.util.ArrayList;
import java.util.Set;

import com.brain1.core.models.Post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PostRepo extends CrudRepository<Post, String> {

    @Query(value = "SELECT * FROM Post p join Topic top on top.id = p.topic_id where top.name = ?1 and p.id not in (?3) ORDER BY ABS(correct_precent - ?2) LIMIT 40", nativeQuery = true)
    ArrayList<Post> findRandomsWithinCorrectRatio(String topic, Integer correctPrecent, Set<String> ids);

    @Query(value = "SELECT * FROM Post p join post_tags pt on p.id = pt.post_id join tag t on pt.tag_id = t.id join Topic top on top.id = p.topic_id  where top.name = ?1 and p.id not in (?3) and t.name = ?4 ORDER BY ABS(correct_precent - ?2) LIMIT 40", nativeQuery = true)
    ArrayList<Post> findRandomsWithinCorrectRatio(String topic, Integer correctPrecent, Set<String> ids, String tag);
}