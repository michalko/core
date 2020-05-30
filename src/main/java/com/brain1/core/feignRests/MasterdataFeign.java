package com.brain1.core.feignRests;

import java.util.List;

import javax.annotation.Nonnull;

import com.brain1.core.transport.StartTestSessionData;
import com.brain1.core.transport.WronglyAnsweredRecord;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import feign.FeignException;
import feign.hystrix.FallbackFactory;

@FeignClient(name = "masterdata", fallbackFactory = MasterdataFeignFallbackFactory.class)
public interface MasterdataFeign {
    @RequestMapping(method = RequestMethod.GET, value = "/initSession/start/{uid}/{topic}")
    StartTestSessionData initSession(@PathVariable @Nonnull String uid, @PathVariable @Nonnull String topic);

    @RequestMapping(method = RequestMethod.GET, value = "/wrongly/{uid}/{topic}")
    List<WronglyAnsweredRecord> getWronglyAnswered(@PathVariable @Nonnull String uid,
            @PathVariable @Nonnull String topic);
}


@Component
class MasterdataFeignFallbackFactory implements FallbackFactory<MasterdataFeign> {
    @Override
    public MasterdataFeign create(Throwable cause) {

        String httpStatus = cause instanceof FeignException ? Integer.toString(((FeignException) cause).status()) : "";

        return new MasterdataFeign() {

            @Override
            public List<WronglyAnsweredRecord> getWronglyAnswered(String uid, String topic) {
                // TODO Auto-generated method stub
                System.out.println("Fallback for Master feigh 1 " + httpStatus);
                System.out.println("Fallback for Master feigh 2 " + cause.getMessage());
                return null;
            }

            @Override
            public StartTestSessionData initSession(String uid, String topic) {
                // TODO Auto-generated method stub
                System.out.println("fuck");
                return null;
            }

        };
    }
}