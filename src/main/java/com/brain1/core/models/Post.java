package com.brain1.core.models;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Post {

  @Id
  private String id;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "post_tags", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
  @JsonManagedReference
  private List<Tag> tags;

  private Double correctPrecent = 0.0;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "topic_id")
  private Topic topic;

  @NotNull
  private Integer realPostsInTopics;

  @CreationTimestamp
  private LocalDateTime createDateTime;

  public Integer getRealPostsInTopics() {
    return realPostsInTopics;
  }

  public void setRealPostsInTopics(final Integer realPostsInTopics) {
    this.realPostsInTopics = realPostsInTopics;
  }

  public Topic getTopic() {
    return topic;
  }

  public void setTopic(Topic topic) {
    this.topic = topic;
  }

  @Override
  public String toString() {
    return "Post [correctPrecent=" + correctPrecent + ", id=" + id + ", realPostsInTopics=" + realPostsInTopics
        + ", topic=" + topic + "]";
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Double getCorrectPrecent() {
    return correctPrecent;
  }

  public void setCorrectPrecent(Double correctPrecent) {
    this.correctPrecent = correctPrecent;
  }

  public List<Tag> getTags() {
    return tags;
  }

  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }

}
