package com.brain1.core.sessionScoped;

import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import com.brain1.core.feignRests.MasterdataFeign;
import com.brain1.core.models.WronglyAnswered;
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
    private List<WronglyAnsweredRecord> wronglyAnsweredRecords;

    public void init(@Nonnull String uid) {
        this.uid = uid;
        loadWronglyAnswered();
    }

    private void loadWronglyAnswered() {
        System.out.println("loading for user " + uid);
        final var waList = masterdataFeign.getWronglyAnswered(uid);
        System.out.println(waList);
        this.wronglyAnsweredRecords = waList;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<WronglyAnsweredRecord> getWronglyAnsweredRecords() {
        return wronglyAnsweredRecords;
    }

    public void setWronglyAnsweredRecords(List<WronglyAnsweredRecord> wronglyAnsweredRecords) {
        this.wronglyAnsweredRecords = wronglyAnsweredRecords;
    }
}
