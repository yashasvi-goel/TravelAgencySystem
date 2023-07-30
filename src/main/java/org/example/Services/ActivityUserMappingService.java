package org.example.Services;

import org.example.db.Storage;
import org.example.models.Activity;
import org.example.models.ActivityUserMapping;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ActivityUserMappingService {
    Storage storage;

    public ActivityUserMappingService(Storage storage) {
        this.storage = storage;
    }

    public Map<Integer, Activity> GetActivityByIds(List<Integer> Ids){
        return storage.GetActivityMap(Ids);
    }

    public Set<ActivityUserMapping> GetActivityByUser(Integer userId){
        return storage.GetActivityByUser(userId);
    }

    public ActivityUserMapping AddMapping(Activity activity, Integer user) {
        Set<ActivityUserMapping> ActivityUserMappings = storage.GetUsersByActivity(activity.getId());
        if(Objects.isNull(ActivityUserMappings)){
            System.out.println("ActivityUserMapping doesn't exist");
            return null;
        }
        ActivityUserMapping mapping= ActivityUserMapping.builder().
                activity(activity.getId()).
                UserId(user).
                build();
        return storage.BookActivity(mapping, activity.getMaxCapacity(), user);
    }
}
