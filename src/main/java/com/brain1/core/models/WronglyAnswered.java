package com.brain1.core.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@AllArgsConstructor
@Builder
public class WronglyAnswered implements Serializable {
  /**
   *
   */
  private static final long serialVersionUID = 7244312382849947232L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "uid", referencedColumnName = "firebaseId")
  private User user;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "pid")
  private Post post;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "topic", referencedColumnName = "name")
  private Topic topic;

  @NotNull
  private long answeredWronglyTimes;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Post getPost() {
    return post;
  }

  public void setPost(Post post) {
    this.post = post;
  }

  public Topic getTopic() {
    return topic;
  }

  public void setTopic(Topic topic) {
    this.topic = topic;
  }

  public long getAnsweredWronglyTimes() {
    return answeredWronglyTimes;
  }

  public void setAnsweredWronglyTimes(long answeredWronglyTimes) {
    this.answeredWronglyTimes = answeredWronglyTimes;
  }

  @Override
  public String toString() {
    return "WronglyAnswered [answeredWronglyTimes=" + answeredWronglyTimes + ", id=" + id + ", post=" + post
        + ", topic=" + topic + ", user=" + user + "]";
  }
}
