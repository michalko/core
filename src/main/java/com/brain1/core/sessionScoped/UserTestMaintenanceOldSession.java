package com.brain1.core.sessionScoped;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import javax.annotation.Nonnull;

import com.brain1.core.feignRests.MasterdataFeign;
import com.brain1.core.transport.StartTestSessionData;
import com.brain1.core.transport.WronglyAnsweredRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserTestMaintenanceOldSession extends UserTestMaintenance {
    @Autowired
    MasterdataFeign masterdataFeign;

    private String uid;
    private String topic;
    private long topicQuestionNum;
    private Queue<WronglyAnsweredRecord> wronglyAnsweredRecords;
    private Set<String> correctAnswers = new HashSet<>(); // don't include correctly answered questions in current test
    private String currentSub; // anymore

    @Override
    public void clear() {
        super.clear();
        correctAnswers.clear();
    }

    public void init(@Nonnull final String uid, @Nonnull final String topic) {
        clear();
        this.uid = uid;
        this.topic = topic;
        initUserSession();
    }

    public Set<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(final Set<String> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    private void initUserSession() {
        StartTestSessionData replyStartTestSession = masterdataFeign.initSession(uid, topic);
        this.wronglyAnsweredRecords = new LinkedList<WronglyAnsweredRecord>(replyStartTestSession.wa());
        this.topicQuestionNum = replyStartTestSession.noTopicQuestions();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(final String uid) {
        this.uid = uid;
    }

    public Queue<WronglyAnsweredRecord> getWronglyAnsweredRecords() {
        return wronglyAnsweredRecords;
    }

    public void setWronglyAnsweredRecords(final Queue<WronglyAnsweredRecord> wronglyAnsweredRecords) {
        this.wronglyAnsweredRecords = wronglyAnsweredRecords;
    }

    public String getCurrentSub() {
        return currentSub;
    }

    public void setCurrentSub(final String currentSub) {
        this.currentSub = currentSub;
    }

    public long getTopicQuestionNum() {
        return topicQuestionNum;
    }

    public void setTopicQuestionNum(long topicQuestionNum) {
        this.topicQuestionNum = topicQuestionNum;
    }

}
