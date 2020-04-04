package com.brain1.core.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class TopicTags {

  @EmbeddedId
  @JsonIgnore
  TopicTagKey id;

  @ManyToOne
  @MapsId("topic_id")
  @JoinColumn(name = "topic_id")
  @JsonIgnore
  Topic topic;

  @ManyToOne
  @MapsId("tag_id")
  @JoinColumn(name = "tag_id")
  Tag tag;

  Long currentPosts = 0l;

  public Long getCurrentPosts() {
    return currentPosts;
  }

  public void setCurrentPosts(Long currentPosts) {
    this.currentPosts = currentPosts;
  }

  public TopicTagKey getId() {
    return id;
  }

  public void setId(TopicTagKey id) {
    this.id = id;
  }

  public Topic getTopic() {
    return topic;
  }

  public void setTopic(Topic topic) {
    this.topic = topic;
  }

  public Tag getTag() {
    return tag;
  }

  public void setTag(Tag tag) {
    this.tag = tag;
  }

  @Embeddable
  public static class TopicTagKey implements Serializable {
    private static final long serialVersionUID = 4285788116889366644L;

    static public TopicTagKey newTopicTagKey(Long topicId, Long tagId) {
      var topicTagKey = new TopicTagKey();
      topicTagKey.setTagId(tagId);
      topicTagKey.setTopicId(topicId);
      return topicTagKey;
    }

    @Column(name = "topic_id")
    Long topicId;

    @Column(name = "tag_id")
    Long tagId;

    public Long getTopicId() {
      return topicId;
    }

    public void setTopicId(Long topicId) {
      this.topicId = topicId;
    }

    public Long getTagId() {
      return tagId;
    }

    public void setTagId(Long tagId) {
      this.tagId = tagId;
    }

    // standard constructors, getters, and setters
    // hashcode and equals implementation
  }

}