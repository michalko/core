package com.brain2.demo.services;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

/**
 * Merges updates to object, used for for patch rest for example
 */
@Service
public class MergePrimitiveUpdatesService {

    public <T> void mergeUpdates(T object, Map<String, Object> updates) {

        System.out.println("updates" + updates.toString());
        removeIDs(updates);

        updates.forEach((k, v) -> {
            // use reflection to get field k on object and set it to value v
            // Change Claim.class to whatver your object is: Object.class
            var field = ReflectionUtils.findField(object.getClass(), k); // find field in the object class
            field.setAccessible(true);
            ReflectionUtils.setField(field, object, v); // set given field for defined object to value V
        });

        System.out.println(object.toString());
    }

    /* for security remove ids from updates */
    private void removeIDs(Map<String, Object> updates) {
        updates.remove("id");
        updates.remove("firebase_id");
    }

}