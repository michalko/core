package com.brain1.core.rests;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;

import com.brain1.core.records.PostStat;
import com.brain1.core.records.StartTestSession;
import com.brain1.core.services.NextPostService;
import com.brain1.core.sessionScoped.UserTestMaintenanceOldSession;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private UserTestMaintenanceOldSession userTestSession;

    @Autowired
    private NextPostService nextPostService;

    private final record ReplyStartTestSession(int wronglyAnsweredQuestions, long noTopicQuestions)
            implements Serializable {
        /**
         *
         */
        private static final long serialVersionUID = 3813261463226331186L;

        public int getWronglyAnsweredQuestions() {
            return wronglyAnsweredQuestions;
        }

        public long getNoTopicQuestions() {
            return noTopicQuestions;
        }

        public static long getSerialversionuid() {
            return serialVersionUID;
        }
    }

    @PostMapping("/startForUser")
    @ResponseStatus(code = HttpStatus.OK)
    public ReplyStartTestSession createUserTestSession(@NotNull @RequestBody final StartTestSession sts) {
        userTestSession.init(sts.uid(), sts.topic());
        return new ReplyStartTestSession(userTestSession.getWronglyAnsweredRecords().size(),
                userTestSession.getTopicQuestionNum());
    }

    // @Cacheable(value = "postsNearRank")
    /**
     * 
     * @param topic
     * @param sub
     * @param topRank
     * @param lastCorrect
     * @return pid or -1 if there are no more posts in current topic / sub
     */
    @GetMapping(value = "/{topic}")
    @ResponseStatus(code = HttpStatus.OK)
    public @NotNull Integer getNextPost(@NotNull @PathVariable(value = "topic") final String topic,
            @RequestParam("sub") final Optional<String> sub, @RequestParam("topRank") final int topRank,
            @RequestParam("lastCorrect") final boolean lastCorrect) {
        maintainUserSession(sub, lastCorrect);
        return getNextQuestion(topic, sub, topRank);
    }

    private void maintainUserSession(final Optional<String> sub, final boolean lastCorrect) {
        maintainPrevQuestion(lastCorrect);
        updateLastSub(sub);
    }

    private void maintainPrevQuestion(final boolean lastCorrect) {
        if (lastCorrect)
            updateCorrectAnswerInSession();
        else
            addToWrongAnswers();
    }

    private void updateLastSub(final Optional<String> sub) {
        if (subHasChanged(sub)) {
            userTestSession.clear();
            userTestSession.setCurrentSub(sub.orElse(null));
        }
    }

    private boolean subHasChanged(final Optional<String> sub) {
        return !Objects.equal(sub.orElse(null), userTestSession.getCurrentSub());
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
        System.out.println(userTestSession.getWronglyAnsweredRecords().toString());
        final var post = userTestSession.getWronglyAnsweredRecords().poll();
        updateUserSession(post.pid(), post.realId());
        return post.realId();
    }

    private boolean isWronglyAnsweredQueueNotEmpty() {
        return userTestSession.getWronglyAnsweredRecords() != null
                && !userTestSession.getWronglyAnsweredRecords().isEmpty();
    }

    private void addToWrongAnswers() {
        if (userTestSession.getLastPost() != null) {
            userTestSession.getCurrentWrongAnswers().put(userTestSession.getLastPost().pid(),
                    new PostStat(userTestSession.getLastPost().pid(), userTestSession.getLastPost().realId(), 2));
        }
    }

    private boolean timeToRepeatWronglyAnsweredInSession() {
        return userTestSession.getCurrentWrongAnswers().size() > 1 && userTestSession.getAnswerCount() % 4 == 0;
    }

    /**
     * Get wrongly answered in this session
     * 
     * @return realId
     */
    private Integer getRandomWrongAnswerFromThisSession() {
        final Random generator = new Random();
        final var values = userTestSession.getCurrentWrongAnswers().values().toArray();
        PostStat nextRandomPost = null;
        do {
            nextRandomPost = (PostStat) values[generator.nextInt(values.length)];
        } while (nextRandomPost.realId() == userTestSession.getLastPost().realId());

        updateUserSession(nextRandomPost.pid(), nextRandomPost.realId());
        return nextRandomPost.realId();
    }

    private Integer getPostIdFromDB(final String topic, final Optional<String> sub, final int topRank) {
        var list = nextPostService.getNextPost(topic, sub, topRank, getPidsToOmit());
        final int listSize = list.size();
        if (noMoreQuestionsInCurrentSub(listSize)) {
            userTestSession.setCurrentSub(null);
            return -1;
        }

        if (userTestSession.getPostsNum() == 0 || Math.abs(userTestSession.getTopRank() - topRank) > 10) {
            userTestSession.setPostsNum(listSize);
            userTestSession.setTopRank(topRank);
        }
        final var postToReturn = list.get(NextPostId.nextPostIndex(listSize));

        updateUserSession(postToReturn.getId(), postToReturn.getRealPostsInTopics());
        return postToReturn.getRealPostsInTopics();
    }

    private boolean noMoreQuestionsInCurrentSub(final int listSize) {
        return listSize == 0 && userTestSession.getCurrentSub() != null;
    }

    private Set<String> getPidsToOmit() {
        var r = Optional.ofNullable(userTestSession.getLastPost()).map(lp -> {
            return ImmutableSet
                    .of(Set.of(lp.pid()), userTestSession.getCorrectAnswers(), userTestSession.getLastPostsIds())
                    .stream().flatMap(Collection::stream).collect(Collectors.toSet());

        }).orElseGet(() -> null);
        if (r != null) {
            System.out.println("posts to omit " + r.toString());
        }

        return r;
    }

    private void updateUserSession(@Nonnull final String pid, @Nonnull final int realId) {
        userTestSession.addPost(pid);
        userTestSession.setLastPost(new PostStat(pid, realId, 0));
        userTestSession.setAnswerCount(userTestSession.getAnswerCount() + 1);
    }

    private void updateCorrectAnswerInSession() {
        Optional.ofNullable(userTestSession.getLastPost()).ifPresent(lastPost -> {
            final var postStat = userTestSession.getCurrentWrongAnswers().get(lastPost.pid());

            final Consumer<PostStat> decrementWrongAnswerForAQuestion = ps -> userTestSession.getCurrentWrongAnswers()
                    .put(lastPost.pid(), new PostStat(ps.pid(), ps.realId(), ps.count() - 1));
            final Runnable addToCorrectlyAnswered = () -> userTestSession.getCorrectAnswers().add(lastPost.pid());
            Optional.ofNullable(postStat).ifPresentOrElse(decrementWrongAnswerForAQuestion, addToCorrectlyAnswered);

        });
    }

    private static class NextPostId {
        /**
         * calcs id of next pid
         */
        static private Function<Integer, Integer> mean = size -> size / 2;
        static private Function<Integer, Integer> stdDeviation = size -> size / 4;

        public static int nextPostIndex(final int listSize) {
            final var ng = random.nextGaussian();
            final var gauss = (int) (stdDeviation.apply(listSize) * (ng) + mean.apply(listSize));
            return gauss < 0 || gauss >= listSize ? listSize / 2 : gauss;
        }
    }
}