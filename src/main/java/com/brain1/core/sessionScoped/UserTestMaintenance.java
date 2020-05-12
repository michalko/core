package com.brain1.core.sessionScoped;

import java.util.HashMap;
import java.util.Set;

import com.brain1.core.records.PostStat;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
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

    public Set<String> getLastPostsIds() {
        return lastPosts;
    }

    public void addPost(final String pid) {
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

    public void setAnswerCount(final int answerCount) {
        this.answerCount = answerCount;
    }

}

