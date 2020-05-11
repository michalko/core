package com.brain1.core.services;

import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import com.brain1.core.repos.PostRepo;
import com.brain1.core.transport.WronglyAnsweredRecord;
import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NextRetestPostService {
    @Autowired
    PostRepo postRepo;

    public WronglyAnsweredRecord getNextPost(@Nonnull final String uid, final Set<String> postsToSkip) {
        return null;
        // return postRepo.findRandomsWithinCorrectRatio(uid, postsToOmit);
    }
}