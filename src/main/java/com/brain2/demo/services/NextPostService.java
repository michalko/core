package com.brain2.demo.services;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import com.brain2.demo.models.Post;
import com.brain2.demo.repos.PostRepo;
import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NextPostService {
    @Autowired
    PostRepo postRepo;

    public ArrayList<Post> getNextPost(final String topic, final Optional<String> sub, final int topRank,
            final Set<String> postsToOmit) {
        ArrayList<Post> list = Lists.newArrayList();
        if (sub.isPresent()) {
            list = postRepo.findRandomsWithinCorrectRatio(topic, topRank, postsToOmit, sub.get());
        } else {
            list = postRepo.findRandomsWithinCorrectRatio(topic, topRank, postsToOmit);
        }
        return list;
    }
}