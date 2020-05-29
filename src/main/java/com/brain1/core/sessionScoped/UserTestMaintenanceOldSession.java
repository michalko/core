package com.brain1.core.sessionScoped;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import javax.annotation.Nonnull;

import com.brain1.core.feignRests.MasterdataFeign;
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
    private Queue<WronglyAnsweredRecord> wronglyAnsweredRecords;
    private String currentSub;
    private Set<String> correctAnswers = new HashSet<>(); // don't include correctly answered questions in current test anymore

    public void init(@Nonnull final String uid, @Nonnull final String topic) {
        this.uid = uid;
        this.topic = topic;
        loadWronglyAnswered();
    }

    public Set<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(final Set<String> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    private void loadWronglyAnswered() {
        this.wronglyAnsweredRecords = new LinkedList<>(masterdataFeign.getWronglyAnswered(uid, topic));
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

}
