package com.brain1.core.sessionScoped;

import java.util.HashMap;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.brain1.core.records.PostStat;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserTestMaintenance {
    private final Set<String> lastPostPids;
    private final HashMap<String, PostStat> currentWrongAnswers;
    private int postsNum;
    private int topRank;
    private PostStat lastPost;
    private int answerCount = 22;

    @PostConstruct
    public void onSessionCreate() {

        System.out.println("!!!! Session created !!!!!!!");
        // clean stuff up...
    }

    @PreDestroy
    public void onSessionDestroyed() {

        System.out.println("!!!! Session destroyed !!!!!!!");
        // clean stuff up...
    }

    UserTestMaintenance() {
        lastPostPids = Sets.newHashSet("1");
        currentWrongAnswers = Maps.newHashMap();
    }

    void clear() {
        lastPostPids.clear();
        postsNum = 0;
    }

    public void clearCurrentWrongAnswers() {
        currentWrongAnswers.clear();
    }

    public HashMap<String, PostStat> getCurrentWrongAnswers() {
        return currentWrongAnswers;
    }

    public Set<String> getLastPostsIds() {
        return lastPostPids;
    }

    public void addPost(final String pid) {
        lastPostPids.add(pid);
        if (lastPostPids.size() > (postsNum / 4)) {
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

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(final int answerCount) {
        this.answerCount = answerCount;
    }

    public PostStat getLastPost() {
        return lastPost;
    }

    public void setLastPost(PostStat lastPost) {
        this.lastPost = lastPost;
    }

}
