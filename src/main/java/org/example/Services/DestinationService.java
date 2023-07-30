package org.example.Services;

import org.example.db.Storage;
import org.example.models.Activity;
import org.example.models.ActivityUserMapping;
import org.example.models.Destination;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DestinationService {

    Storage storage;
    ActivityService activityService;
    UserService userService;

    public DestinationService(Storage storage, ActivityService activityService, UserService userService) {
        this.storage = storage;
        this.activityService = activityService;
        this.userService = userService;
    }

    public Destination GetDestinationsById(Integer destId){
        return storage.GetDestination(destId);
    }
    public List<Destination> GetDestinationsByIds(List<Integer> destIds){
        List<Destination> ans=new ArrayList<>();
        destIds.forEach(id->{
            ans.add(storage.GetDestination(id));
        });

        return ans;
    }

    public Integer AddActivityToDestination(Activity activity,Integer destId){
        Destination destinationById = GetDestinationsById(destId);
        if(Objects.isNull(destinationById)){
            System.out.println("Destination doens't exist");
            return null;
        }
        List<Integer> activites = destinationById.getActivites();
        if(Objects.isNull(activites)){
            activites=new ArrayList<>();
        }
        activity.setDestination(destinationById.getName());
        Integer activityPk = activityService.AddActivity(activity,destId);
        if(activityPk==-1){
            System.out.println("Activity with same name already exists");
            return -1;
        }
        activites.add(activityPk);
        destinationById.setActivites(activites);
        storage.UpdateDestination(destinationById);
        return activityPk;
    }

    public void ListAllActivities(Integer destId){
        Destination destination = storage.GetDestination(destId);
        Map<Integer, Activity> activitiesAtDest = storage.GetActivityMap(destination.getActivites());
        System.out.println("The Destination "+destination.getName()+" has the following activities");
        activitiesAtDest.forEach((key,value)->{
            System.out.println(value.toString());
        });
    }

    public Integer ReserveActivityForUser(Integer userId, Integer activityId, Integer destinationId){
        Activity activity = activityService.GetActivityById(activityId);
        Destination destination = storage.GetDestination(destinationId);
        if(Objects.isNull(destination)){
            System.out.println("destination doesn't exist");
            return -1;
        }
        List<Integer> activites = destination.getActivites();
        if(!activites.contains(activityId)){
            System.out.println("destination doesn't have this activity ID");
            return -1;
        }
        if(!userService.CheckBalance(userId,activity.getCost())){
            System.out.println("User doesn't have enough funds to enroll for this activity");
        }
        ActivityUserMapping activityUserMapping = activityService.ReserveActivityForUser(activity, userId);
        if(!Objects.isNull(activityUserMapping)){
        userService.UpdateBalance(userId,activity.getCost());
        }
        return activityUserMapping.getId();
    }
}
