package com.brain1.core.transport;

import java.io.Serializable;

public record WronglyAnsweredRecord(final String topic, final Integer realId, final String pid)
        implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 379803706784441476L;

    public String getTopic() {
        return topic;
    }

    public Integer getRealId() {
        return realId;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getPid() {
        return pid;
    }
}