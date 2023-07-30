package org.example.Services;

import org.example.db.Storage;
import org.example.models.Activity;

import java.util.List;
import java.util.Map;

public class ActivityService {
    Storage storage;

    public ActivityService(Storage storage) {
        this.storage = storage;
    }

    public Map<Integer, Activity> GetActivityByIds(List<Integer> Ids){
        return storage.GetActivityMap(Ids);
    }

    public Integer AddActivity(Activity activity,Integer destId) {
        return storage.AddActivity(activity);
    }
}
