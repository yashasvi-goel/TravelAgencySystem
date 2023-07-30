package org.example.Services;

import org.example.db.Storage;
import org.example.models.Activity;
import org.example.models.Destination;
import org.example.models.PackageUserMapping;
import org.example.models.TravelPackage;

import java.util.*;

//@RequiredArgsConstructor
public class TravelPackageService {
    public TravelPackageService(Storage storage, ActivityService activityService, DestinationService destinationService, UserService userService) {
        this.storage = storage;
        this.activityService = activityService;
        this.destinationService = destinationService;
        this.userService = userService;
        this.packageUserMappingService = new PackageUserMappingService(storage);
    }

    Storage storage;
    DestinationService destinationService;
    ActivityService activityService;
    UserService userService;
    PackageUserMappingService packageUserMappingService;


    public TravelPackage CreatePackage(String packageName, Integer totalCapacity, List<Destination> destinations, Integer cost) {
        TravelPackage travelPackage = new TravelPackage();
        travelPackage.setName(packageName);
        travelPackage.setCapacity(totalCapacity);
        travelPackage.setCost(cost);


        if (Objects.isNull(destinations) || destinations.size() == 0) {
            System.out.println("There must be a minimum of one destination in each package");
            return null;
        }
        List<Integer> destinationIds = storage.AddDestinations(destinations);
        travelPackage.setDestinations(destinationIds);
        travelPackage = storage.AddPackage(travelPackage);
        return travelPackage;
    }

    public void GetPackageById(Integer id) {//this is for searching, not for the user to explore
        TravelPackage packageById = storage.GetPackage(id);
        if (Objects.isNull(packageById)) {
            System.out.println("This package ID doesn't exist.");
            return;
        }
        List<Destination> destinationList = destinationService.GetDestinationsByIds(packageById.getDestinations());
        if (Objects.isNull(destinationList) || destinationList.size() == 0) {
            System.out.println("This package doesn't have any destinations.");
            return;
        }
        List<Integer> activityList = new ArrayList<>();
        destinationList.forEach(dest -> {
            if (!Objects.isNull(dest.getActivites()))
                activityList.addAll(dest.getActivites());
        });
        if (activityList.size() == 0) {
            System.out.println("This package ID doesn't have any activities.");
            return;
        }
        Map<Integer, Activity> activityMap = activityService.GetActivityByIds(activityList);
        System.out.println("Below are the details for package: " + packageById.getName() + " With ID: " + packageById.getId());
        System.out.println(packageById.toString());
        System.out.println("Below are the destinations covered in this package: " + destinationList);
        destinationList.forEach(destination -> {
            List<Activity> activities = new ArrayList<>();
            destination.getActivites().forEach(act -> {
                activities.add(activityMap.get(act));
            });
            System.out.println(destination.toString() + " " + activities);
        });
        System.out.println(activityMap.toString());
    }// TODO create a search/explore

    public Integer BookPackage(Integer userId, Integer packageId) {
        TravelPackage travelPackage = storage.GetPackage(packageId);
        // This would be the start of a transaction block if we used an actual DB
        if (!userService.CheckBalance(userId, travelPackage.getCost())) {
            System.out.println("The user can't book package: " + travelPackage.getName());
            return -1;
        }
        PackageUserMapping packageUserMapping=packageUserMappingService.AddMapping(travelPackage, userId);
        if(!Objects.isNull(packageUserMapping)){
            userService.UpdateBalance(userId, travelPackage.getCost());
        }
        return packageUserMapping.getId();

    }

}
