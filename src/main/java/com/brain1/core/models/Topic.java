package com.brain1.core.models;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.collect.Sets;

@Entity
public class Topic implements Serializable {
  /**
   *
   */
  private static final long serialVersionUID = -7713400997390445013L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToMany(mappedBy = "topic", fetch = FetchType.EAGER)
  @JsonManagedReference
  private Set<TopicTags> tags;

  private String section;

  @NotNull
  private String name;

  @NotNull
  private Integer activeCurrently;

  @NotNull
  Long currentPosts = 0l;

  public Long getCurrentPosts() {
    return currentPosts;
  }

  public void setCurrentPosts(Long currentPosts) {
    this.currentPosts = currentPosts;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSection() {
    return section;
  }

  public void setSection(String section) {
    this.section = section;
  }

  public Integer getActiveCurrently() {
    return activeCurrently;
  }

  public void setActiveCurrently(Integer activeCurrently) {
    this.activeCurrently = activeCurrently;
  }

  public Set<TopicTags> getTags() {
    return tags;
  }

  public void setTags(List<TopicTags> tags) {
    this.tags = Sets.newHashSet(tags);
  }

  public void setTags(Set<TopicTags> tags) {
    this.tags = tags;
  }

  @Override
  public String toString() {
    return "Topic [activeCurrently=" + activeCurrently + ", currentPosts=" + currentPosts + ", id=" + id + ", name="
        + name + ", section=" + section + ", tags=" + tags + "]";
  }

}
