package com.brain1.core.repos;

import java.util.Set;

import com.brain1.core.models.WronglyAnswered;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface WronglyAnsweredRepo extends CrudRepository<WronglyAnswered, String> {

    @Query(value = "SELECT * FROM wrongly_answered wa where wa.uid = ?1 and wa.pid not in (?2) LIMIT 40", nativeQuery = true)
    WronglyAnswered findRandomForUser(String uid, Set<String> pids);

    @Query(value = "SELECT * FROM wrongly_answered wa where wa.uid = ?1 LIMIT 40", nativeQuery = true)
    WronglyAnswered findRandomForUser(String uid);

}