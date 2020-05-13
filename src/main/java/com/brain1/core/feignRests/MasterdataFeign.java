package com.brain1.core.feignRests;

import java.util.List;

import javax.annotation.Nonnull;

import com.brain1.core.transport.WronglyAnsweredRecord;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("masterdata")
public interface MasterdataFeign {

    @RequestMapping(method = RequestMethod.GET, value = "/wrongly/{uid}")
    List<WronglyAnsweredRecord> getWronglyAnswered(@PathVariable @Nonnull String uid);
}