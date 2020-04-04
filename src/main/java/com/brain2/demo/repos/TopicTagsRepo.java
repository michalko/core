package com.brain2.demo.repos;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.transaction.Transactional;
import javax.validation.constraints.NotEmpty;

import com.brain2.demo.models.TopicTags;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TopicTagsRepo extends CrudRepository<TopicTags, Long> {
    List<TopicTags> findByTopic_Name(String name);

    Optional<TopicTags> findByTag_IdAndTopic_Id(Long tagID, Long topicID);

    @Transactional
    @Modifying
    @Query("update TopicTags t set t.currentPosts = t.currentPosts - 1 where t.id.topicId = ?1 and t.tag.id in (?2)")
    void decrementActivePostsForThisTopicTags(@Nonnull Long topicId, @NotEmpty List<Long> tagIds);

}