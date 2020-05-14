package com.brain1.core.rests;

import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import javax.annotation.Nonnull;
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

    // @Cacheable(value = "postsNearRank")
    @GetMapping(value = "/{topic}")
    @ResponseStatus(code = HttpStatus.OK)
    public @NotNull Integer getNextPost(@NotNull @PathVariable(value = "topic") final String topic,
            @RequestParam("sub") final Optional<String> sub, @RequestParam("topRank") final int topRank,
            @RequestParam("lastCorrect") final boolean lastCorrect) {
        System.out.println("current user");

        maintainPrevQuestion(lastCorrect);
        return getNextQuestion(topic, sub, topRank);
    }

    @GetMapping(value = "/startForUser/{uid}")
    @ResponseStatus(code = HttpStatus.OK)
    public void createUserTestSession(@NotNull @PathVariable(value = "uid") final String uid) {
        userTestMaintenance.init(uid);
    }

    private void maintainPrevQuestion(final boolean lastCorrect) {
        if (lastCorrect)
            removeFromWrongAnswers();
        else
            addToWrongAnswers();
    }

    private Integer getNextQuestion(final String topic, final Optional<String> sub, final int topRank) {
        if (isWronglyAnsweredQueueNotEmpty())
            return getNextWronglyAnswered();
        else if (timeToRepeatWronglyAnsweredInSession())
            return getRandomWrongAnswerFromThisSession();
        else
            return getPostIdFromDB(topic, sub, topRank);
    }

    private Integer getNextWronglyAnswered() {
        System.out.println("returning wrongly ans");
        System.out.println(userTestMaintenance.getWronglyAnsweredRecords().toString());
        var post = userTestMaintenance.getWronglyAnsweredRecords().poll();
        updateUserSession(post.pid(), post.realId());
        return post.realId();
    }

    private boolean isWronglyAnsweredQueueNotEmpty() {
        return !userTestMaintenance.getWronglyAnsweredRecords().isEmpty();
    }

    private void addToWrongAnswers() {
        userTestMaintenance.getWrongAnswers().put(userTestMaintenance.getLastPost().pid(),
                new PostStat(userTestMaintenance.getLastPost().pid(), userTestMaintenance.getLastPost().realId(), 2));
    }

    private boolean timeToRepeatWronglyAnsweredInSession() {
        return userTestMaintenance.getWrongAnswers().size() > 1 && userTestMaintenance.getAnswerCount() % 2 == 0;
    }

    /**
     * Get wrongly answered in this session
     * 
     * @return realId
     */
    private Integer getRandomWrongAnswerFromThisSession() {
        final Random generator = new Random();
        final var values = userTestMaintenance.getWrongAnswers().values().toArray();
        PostStat nextRandomPost = null;
        do {
            nextRandomPost = (PostStat) values[generator.nextInt(values.length)];
        } while (nextRandomPost.realId() == userTestMaintenance.getLastPost().realId());

        updateUserSession(nextRandomPost.pid(), nextRandomPost.realId());
        return nextRandomPost.realId();
    }

    private Integer getPostIdFromDB(final String topic, final Optional<String> sub, final int topRank) {

        final var postsToOmit = Optional.ofNullable(userTestMaintenance.getLastPost()).map((lastPost) -> {
            return Sets.union(userTestMaintenance.getLastPostsIds(), Set.of(lastPost.pid()));
        }).orElseGet(() -> null);

        System.out.format("postsToOmit ", postsToOmit);
        final var list = nextPostService.getNextPost(topic, sub, topRank, postsToOmit);
        final var listSize = list.size();
        if (userTestMaintenance.getPostsNum() == 0 || Math.abs(userTestMaintenance.getTopRank() - topRank) > 10) {
            userTestMaintenance.setPostsNum(listSize);
            userTestMaintenance.setTopRank(topRank);
        }

        System.out.format("list 1 ", list);
        final var gauss = nextPostIndex(listSize);
        System.out.println(list);
        final var postToReturn = list.get(gauss);

        updateUserSession(postToReturn.getId(), postToReturn.getRealPostsInTopics());
        return postToReturn.getRealPostsInTopics();
    }

    private void updateUserSession(@Nonnull final String pid, @Nonnull final int realId) {
        userTestMaintenance.addPost(pid);
        userTestMaintenance.setLastPost(new PostStat(pid, realId, 0));
        userTestMaintenance.setAnswerCount(userTestMaintenance.getAnswerCount() + 1);
    }

    private void removeFromWrongAnswers() {
        Optional.ofNullable(userTestMaintenance.getLastPost()).ifPresent(lastPost -> {
            final var postStat = userTestMaintenance.getWrongAnswers().get(lastPost.pid());
            if (postStat != null) {
                userTestMaintenance.getWrongAnswers().put(lastPost.pid(),
                        new PostStat(postStat.pid(), postStat.realId(), postStat.count() - 1));
            }
        });
    }

    static private Function<Integer, Integer> mean = size -> size / 2;
    static private Function<Integer, Integer> stdDeviation = size -> size / 4;

    private int nextPostIndex(final int listSize) {
        final var ng = random.nextGaussian();
        final var gauss = (int) (stdDeviation.apply(listSize) * (ng) + mean.apply(listSize));
        return gauss < 0 || gauss >= listSize ? listSize / 2 : gauss;
    }
}