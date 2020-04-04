package com.brain2.demo.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;

@Entity
public class Topicsections {

  @OneToMany(mappedBy = "section", fetch = FetchType.EAGER)
  private List<Topic> topics;

  @NotNull
  @Id
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Topic> getTopics() {
    return topics;
  }

  public void setTopics(List<Topic> topics) {
    this.topics = topics;
  }

  @Override
  public String toString() {
    return "Topicsections [name=" + name + ", topics=" + topics + "]";
  }
}
