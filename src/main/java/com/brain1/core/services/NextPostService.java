package com.brain1.core.services;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nullable;

import com.brain1.core.models.Post;
import com.brain1.core.repos.PostRepo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NextPostService {
    @Autowired
    PostRepo postRepo;

    public ArrayList<Post> getNextPost(final String topic, final Optional<String> sub, final int topRank,
            @Nullable final Set<String> postsToOmit) {
        ArrayList<Post> list = Lists.newArrayList();
        var po = postsOrEmptySet(postsToOmit);
        if (sub.isPresent()) {
            System.out.println("1 po " + po.toString());
            list = postRepo.findRandomsWithinCorrectRatio(topic, topRank, po, sub.get());
        } else {
            System.out.println("2 po " + po.toString());
            list = postRepo.findRandomsWithinCorrectRatio(topic, topRank, po);
        }
        return list;
    }

    private Set<String> postsOrEmptySet(@Nullable final Set<String> postsToOmit) {
        // https://stackoverflow.com/questions/2488930/passing-empty-list-as-parameter-to-jpa-query-throws-error
        return Optional.ofNullable(postsToOmit).orElseGet(() -> Sets.newHashSet("-1"));
    }

}