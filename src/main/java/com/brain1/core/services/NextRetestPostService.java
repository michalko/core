package com.brain1.core.services;

import java.util.Set;

import javax.annotation.Nonnull;

import com.brain1.core.models.WronglyAnswered;
import com.brain1.core.repos.WronglyAnsweredRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NextRetestPostService {
    @Autowired
    WronglyAnsweredRepo wronglyAnsweredRepo;

    public WronglyAnswered getNextPost(@Nonnull final String uid, final Set<String> postsToSkip) {
        if (postsToSkip.isEmpty())
            return wronglyAnsweredRepo.findRandomForUser(uid);
        else
            return wronglyAnsweredRepo.findRandomForUser(uid, postsToSkip);
    }
}