package com.brain2.demo.rests;

import java.util.List;
import java.util.Optional;

import com.brain2.demo.models.Topic;
import com.brain2.demo.models.TopicTags;
import com.brain2.demo.models.Topicsections;
import com.brain2.demo.repos.SectionRepo;
import com.brain2.demo.repos.TopicRepo;
import com.brain2.demo.repos.TopicTagsRepo;
import com.google.common.collect.Iterables;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topics")
@CrossOrigin(origins = { "http://localhost:3000", "https://brainmatter.xyz" })
public class TopicRest {

    @Autowired
    TopicRepo topicRepo;

    @Autowired
    TopicTagsRepo topicTagsRepo;

    @Autowired
    SectionRepo sectionRepo;

    @GetMapping("/sections")
    public Iterable<Topicsections> getSections() {
        return sectionRepo.findAll();
    }

    @GetMapping
    public Iterable<Topic> getAll() {
        Iterable<Topic> findAll = topicRepo.findAll();
        return findAll;
    }

    @GetMapping("/get/{id}")
    public Optional<Topic> getTopic(@PathVariable Long id) {
        return topicRepo.findById(id);
    }

    @GetMapping("/topicsWithTags")
    public Iterable<TopicTags> getTopicsWithTags() {
        return topicTagsRepo.findAll();
    }

    @GetMapping("/topicsWithTagsFor")
    public List<TopicTags> getTopicsWithTagsFor() {
        return topicTagsRepo.findByTopic_Name("Java");
    }

    @PutMapping(value = "/incActive")
    @ResponseStatus(value = HttpStatus.OK)
    void incrementActive(@RequestBody List<String> topics) {
        topicRepo.incrementActiveForTopics(topics);
    }

}