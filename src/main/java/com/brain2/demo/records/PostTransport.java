package com.brain2.demo.records;

import java.util.List;

public record PostTransport(final String id, final int realPostsInTopics, final Long topicID,
        final List<String> tags) {
}