package com.brain1.core.transport;

import java.util.List;

public final record ReplyStartTestSession(List<WronglyAnsweredRecord> wa, long noTopicQuestions) {
}