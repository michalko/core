package com.brain1.core.rests;

import java.util.Optional;
import java.util.Random;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.brain1.core.records.PostStat;
import com.brain1.core.services.NextPostService;
import com.brain1.core.sessionScoped.UserTestMaintenanceOldSession;
import com.google.common.collect.Sets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nextpost")
@CrossOrigin(origins = { "http://localhost:3000", "https://brainmatter.xyz" })
public class NextPostRest {
    private static Random random;

    static {
        random = new Random();
    }

    @Autowired
    private UserTestMaintenanceOldSession userTestMaintenance;

    @Autowired
    private NextPostService nextPostService;

    @GetMapping(value = "/startForUser/{uid}")
    @ResponseStatus(code = HttpStatus.OK)
    public void createUserTestSession(@NotNull @PathVariable(value = "uid") final String uid) {
        System.out.println("creating ses for " + uid);
        userTestMaintenance.init(uid);
    }

    // @Cacheable(value = "postsNearRank")
    @GetMapping(value = "/{topic}")
    @ResponseStatus(code = HttpStatus.OK)
    public @NotNull Integer getNextPost(@NotNull @PathVariable(value = "topic") final String topic,
            @RequestParam("sub") final Optional<String> sub, @RequestParam("topRank") final int topRank,
            @RequestParam("lastCorrect") final boolean lastCorrect) {

        // SecurityContextHolder.getContext();
        System.out.println("current user");

        maintainPrevQuestion(lastCorrect);
        return getNextQuestion(topic, sub, topRank);
    }

    private void maintainPrevQuestion(final boolean lastCorrect) {
        if (lastCorrect)
            removeFromWrongAnswersIfPossible();
        else
            addToWrongAnswers();

    }

    private Integer getNextQuestion(final String topic, final Optional<String> sub, final int topRank) {

        if (timeToRepeatWronglyAnsweredInSession())
            return getRandomWrongAnswer();
        else
            return getPostIdFromDB(topic, sub, topRank);
    }

    private void addToWrongAnswers() {
        userTestMaintenance.getWrongAnswers().put(userTestMaintenance.getLastPid(),
                new PostStat(userTestMaintenance.getRealId(), 2));
    }

    private boolean timeToRepeatWronglyAnsweredInSession() {
        return userTestMaintenance.getWrongAnswers().size() > 1 && userTestMaintenance.getAnswerCount() % 2 == 0;
    }

    /**
     * Get wrongly answered in this session
     * 
     * @return realId
     */
    private Integer getRandomWrongAnswer() {
        Random generator = new Random();
        var values = userTestMaintenance.getWrongAnswers().values().toArray();
        PostStat nextRandomPost = null;
        do {
            nextRandomPost = (PostStat) values[generator.nextInt(values.length)];
        } while (nextRandomPost.realPostsInTopics() == userTestMaintenance.getRealId());

        userTestMaintenance.setRealId(nextRandomPost.realPostsInTopics());
        userTestMaintenance.setAnswerCount(userTestMaintenance.getAnswerCount() + 1);
        return nextRandomPost.realPostsInTopics();
    }

    private Integer getPostIdFromDB(final String topic, final Optional<String> sub, final int topRank) {
        final var postsToOmit = Sets.union(userTestMaintenance.getLastPostsIds(),
                Set.of(userTestMaintenance.getLastPid()));

        System.out.format("postsToOmit %s %s %s %n", postsToOmit, userTestMaintenance.getLastPostsIds(),
                userTestMaintenance.getLastPid());
        final var list = nextPostService.getNextPost(topic, sub, topRank, postsToOmit);
        final var listSize = list.size();
        if (userTestMaintenance.getPostsNum() == 0 || Math.abs(userTestMaintenance.getTopRank() - topRank) > 10) {
            userTestMaintenance.setPostsNum(listSize);
            userTestMaintenance.setTopRank(topRank);
        }

        final var gauss = nextPostIndex(listSize);
        System.out.println(list);
        final var postToReturn = list.get(gauss);

        userTestMaintenance.addPost(postToReturn.getId());
        userTestMaintenance.setLastPid(postToReturn.getId());
        userTestMaintenance.setRealId(postToReturn.getRealPostsInTopics());
        userTestMaintenance.setAnswerCount(userTestMaintenance.getAnswerCount() + 1);
        return postToReturn.getRealPostsInTopics();
    }

    private void removeFromWrongAnswersIfPossible() {
        if (userTestMaintenance.getWrongAnswers().containsKey(userTestMaintenance.getLastPid())) {
            var postStat = userTestMaintenance.getWrongAnswers().get(userTestMaintenance.getLastPid());
            PostStat newStat = new PostStat(postStat.realPostsInTopics(), postStat.count() - 1);
            userTestMaintenance.getWrongAnswers().put(userTestMaintenance.getLastPid(), newStat);
        }
    }

    private int nextPostIndex(final int listSize) {
        final var gauss = (int) ((listSize / 4) * (random.nextGaussian()) + (listSize / 2));
        return gauss < 0 || gauss >= listSize ? listSize / 2 : gauss;
    }

}