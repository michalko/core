package com.brain1.core.rests;

import java.util.Random;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import com.brain1.core.services.NextRetestPostService;
import com.brain1.core.transport.WronglyAnsweredRecord;
import com.google.common.collect.Sets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nextretestpost")
@CrossOrigin(origins = { "http://localhost:3000", "https://brainmatter.xyz" })
public class NextRetestPostRest {
    @Autowired
    NextRetestPostService nextRetestPostService;

    @GetMapping(value = "/{uid}")
    @ResponseStatus(code = HttpStatus.OK)
    public @NotNull WronglyAnsweredRecord getNextRetestPostForUser(@NotNull String uid,
            @RequestParam("lastCorrect") final boolean lastCorrect) {


        return nextRetestPostService.getNextPost(uid, Sets.newHashSet());
    }

}