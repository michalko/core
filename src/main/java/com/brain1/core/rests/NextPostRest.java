package com.brain1.core.rests;

import java.util.Optional;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import com.brain1.core.services.NextPostService;
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
    com.brain1.core.rests.NextPostRest.MyConfiguration.LastReadPosts lastReadPosts;

    @Autowired
    NextPostService nextPostService;

    // @Cacheable(value = "postsNearRank")
    @GetMapping(value = "/{topic}")
    @ResponseStatus(code = HttpStatus.OK)
    public @NotNull Integer getNextPost(@NotNull @PathVariable(value = "topic") final String topic,
            @RequestParam("sub") final Optional<String> sub, @RequestParam("topRank") final int topRank) {
        final var postsToOmit = Sets.union(lastReadPosts.getLastPostsIds(), Set.of(lastReadPosts.getLastPid()));
        final var list = nextPostService.getNextPost(topic, sub, topRank, postsToOmit);
        final var listSize = list.size();
        if (lastReadPosts.getPostsNum() == 0 || Math.abs(lastReadPosts.getTopRank() - topRank) > 10) {
            lastReadPosts.setPostsNum(listSize);
            lastReadPosts.setTopRank(topRank);
        }

        final var gauss = nextPostIndex(listSize);
        System.out.println(list);
        final var postToReturn = list.get(gauss);
        lastReadPosts.addPost(postToReturn.getId());
        lastReadPosts.setLastPid(postToReturn.getId());

        return postToReturn.getRealPostsInTopics();
    }

    private int nextPostIndex(final int listSize) {
        final var gauss = (int) ((listSize / 4) * (random.nextGaussian()) + (listSize / 2));
        return gauss < 0 || gauss >= listSize ? listSize / 2 : gauss;
    }

    @Configuration
    public class MyConfiguration {
        @Bean
        @SessionScope
        public LastReadPosts lastReadPosts() {
            return new LastReadPosts();
        }

        @Component
        public class LastReadPosts {
            private final Set<String> lastPosts;
            private int postsNum;
            private int topRank;
            private String lastPid = "0";

            LastReadPosts() {
                lastPosts = Sets.newHashSet("1");
            }

            void clear() {
                lastPosts.clear();
                postsNum = 0;
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

            public String getLastPid() {
                return lastPid;
            }

            public void setLastPid(final String lastPid) {

                System.out.println("last posts ids 2: " + lastPid);
                this.lastPid = lastPid;
            }
        }

    }
}