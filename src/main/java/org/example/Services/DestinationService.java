package org.example.Services;

import org.example.db.Storage;
import org.example.models.Activity;
import org.example.models.Destination;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DestinationService {

    Storage storage;
    ActivityService activityService;

    public DestinationService(Storage storage, ActivityService activityService) {
        this.storage = storage;
        this.activityService = activityService;
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
}
