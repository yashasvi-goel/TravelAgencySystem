package org.example.Services;

import org.example.db.Storage;
import org.example.models.Activity;
import org.example.models.ActivityUserMapping;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ActivityService {
    Storage storage;
    ActivityUserMappingService activityUserMapping;

    public ActivityService(Storage storage, ActivityUserMappingService activityUserMapping) {
        this.storage = storage;
        this.activityUserMapping = activityUserMapping;
    }

    public Map<Integer, Activity> GetActivityByIds(List<Integer> Ids){
        return storage.GetActivityMap(Ids);
    }
    public Activity GetActivityById(Integer id){
        return storage.GetActivity(id);
    }

    public Integer AddActivity(Activity activity,Integer destId) {
        return storage.AddActivity(activity);
    }

    public ActivityUserMapping ReserveActivityForUser(Activity activity,Integer userId){
        return activityUserMapping.AddMapping(activity, userId);
    }
    public void GetActivitiesForUser(Integer userId){
        Set<ActivityUserMapping> activityUserMappings = activityUserMapping.GetActivityByUser(userId);
        if(activityUserMappings.size()==0){
        System.out.println("There are no activities for this user.");
            return;
        }
        System.out.println("These are the activities for the user:");
        activityUserMappings.forEach(activityUser -> {
            Activity activity = GetActivityById(activityUser.getActivity());
            System.out.println(activity.getName()+" @ "+activity.getDestination()+" for INR:"+activity.getCost());
        });
    }
}
