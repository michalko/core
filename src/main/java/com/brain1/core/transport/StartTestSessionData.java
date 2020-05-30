package com.brain1.core.transport;

import java.io.Serializable;
import java.util.List;

public final record StartTestSessionData(List<WronglyAnsweredRecord> wa, long noTopicQuestions)
        implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -8110357785437479267L;

    public List<WronglyAnsweredRecord> getWa() {
        return wa;
    }

    public long getNoTopicQuestions() {
        return noTopicQuestions;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}