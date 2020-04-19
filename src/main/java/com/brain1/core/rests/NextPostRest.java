package com.brain1.core.rests;

import java.util.HashMap;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import com.brain1.core.services.NextPostService;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;

@RestController
@RequestMapping("/nextpost")
@CrossOrigin(origins = { "http://localhost:3000", "https://brainmatter.xyz" })
public class NextPostRest {
    static Random random = new Random();

    @Resource(name = "lastReadPosts")
    MyConfiguration.UserTestMaintenance userTestMaintenance;

    @Autowired
    NextPostService nextPostService;

    // @Cacheable(value = "postsNearRank")
    @GetMapping(value = "/{topic}")
    @ResponseStatus(code = HttpStatus.OK)
    public @NotNull Integer getNextPost(@NotNull @PathVariable(value = "topic") final String topic,
            @RequestParam("sub") final Optional<String> sub, @RequestParam("topRank") final int topRank,
            @RequestParam("lastCorrect") final boolean lastCorrect) {

        if (lastCorrect) {
            removeFromWrongAnswersIfPossible();
        } else {
            userTestMaintenance.getWrongAnswers().put(userTestMaintenance.getLastPid(),
                    new PostStat(userTestMaintenance.getRealId(), 2));
        }

        Integer idToReturn = null;
        if (timeToRepeatWronglyAnswered()) {
            idToReturn = getRandomWrongAnswer();
        } else {
            idToReturn = getPostIdFromDB(topic, sub, topRank);
        }
        return idToReturn;

    }
    
    private boolean timeToRepeatWronglyAnswered() {
        return userTestMaintenance.getWrongAnswers().size() > 1 && userTestMaintenance.getAnswerCount() % 2 == 0;
    }

    private Integer getRandomWrongAnswer() {
        Random generator = new Random();
        var values = userTestMaintenance.getWrongAnswers().values().toArray();
        PostStat nextRandomPost = null;
        do {
            nextRandomPost = (PostStat) values[generator.nextInt(values.length)];
        } while (nextRandomPost.realPostsInTopics == userTestMaintenance.getRealId());

        userTestMaintenance.setRealId(nextRandomPost.realPostsInTopics);
        userTestMaintenance.setAnswerCount(userTestMaintenance.getAnswerCount() + 1);
        return nextRandomPost.realPostsInTopics;
    }

    private Integer getPostIdFromDB(final String topic, final Optional<String> sub, final int topRank) {
        final var postsToOmit = Sets.union(userTestMaintenance.getLastPostsIds(),
                Set.of(userTestMaintenance.getLastPid()));
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
            PostStat newStat = new PostStat(postStat.realPostsInTopics, postStat.count - 1);
            userTestMaintenance.getWrongAnswers().put(userTestMaintenance.getLastPid(), newStat);
        }
    }

    private int nextPostIndex(final int listSize) {
        final var gauss = (int) ((listSize / 4) * (random.nextGaussian()) + (listSize / 2));
        return gauss < 0 || gauss >= listSize ? listSize / 2 : gauss;
    }

    record PostStat(int realPostsInTopics, int count) {
    }

    @Configuration
    public class MyConfiguration {
        @Bean
        @SessionScope
        public UserTestMaintenance lastReadPosts() {
            return new UserTestMaintenance();
        }

        @Component
        public class UserTestMaintenance {
            private final Set<String> lastPosts;
            private final HashMap<String, PostStat> wrongAnswers;
            private int postsNum;
            private int topRank;
            private String lastPid = "0";
            private int lastReadId = 0;
            private int answerCount = 22;

            UserTestMaintenance() {
                lastPosts = Sets.newHashSet("1");
                wrongAnswers = Maps.newHashMap();
            }

            void clear() {
                lastPosts.clear();
                postsNum = 0;
            }

            public HashMap<String, PostStat> getWrongAnswers() {
                return wrongAnswers;
            }

            Set<String> getLastPostsIds() {
                return lastPosts;
            }

            void addPost(final String pid) {
                lastPosts.add(pid);
                if (lastPosts.size() > (postsNum / 2)) {
                    clear();
                }
            }

            public int getPostsNum() {
                return postsNum;
            }

            public void setPostsNum(final int postsNum) {
                this.postsNum = postsNum;
            }

            public int getTopRank() {
                return topRank;
            }

            public void setTopRank(final int topRank) {
                this.topRank = topRank;
            }

            public int getRealId() {
                return lastReadId;
            }

            public void setRealId(final int lastReadId) {
                this.lastReadId = lastReadId;
            }

            public String getLastPid() {
                return lastPid;
            }

            public void setLastPid(final String lastPid) {
                this.lastPid = lastPid;
            }

            public int getAnswerCount() {
                return answerCount;
            }

            public void setAnswerCount(int answerCount) {
                this.answerCount = answerCount;
            }

        }

    }
}