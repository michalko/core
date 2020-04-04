package com.brain1.core.models;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Tag {

  @ManyToMany
  @JsonIgnore
  private Set<Post> posts;

  @OneToMany(mappedBy = "tag")
  @JsonIgnore
  private Set<TopicTags> topics;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  private Long id;

  @NotNull
  private String name;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Post> getPosts() {
    return posts;
  }

  public void setPosts(Set<Post> posts) {
    this.posts = posts;
  }

  public Set<TopicTags> getTopics() {
    return topics;
  }

  public void setTopics(Set<TopicTags> topics) {
    this.topics = topics;
  }

  public Tag(@NotNull String name) {
    this.name = name;
  }

  public Tag() {
  }

}
