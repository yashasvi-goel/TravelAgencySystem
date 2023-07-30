package org.example;

import org.example.Services.*;
import org.example.db.Storage;
import org.example.models.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Storage storage = new Storage();
        ActivityUserMappingService activityUserMappingS=new ActivityUserMappingService(storage);
        ActivityService activityService = new ActivityService(storage,activityUserMappingS);
        UserService userService = new UserService(storage);
        DestinationService destinationService = new DestinationService(storage,activityService,userService);
        TravelPackageService travelPackageService=new TravelPackageService(storage,activityService,
                destinationService,userService);

        List<Activity> activityList=new ArrayList<>();
        Activity activity=new Activity();
        activity.setName("sightseeing");
        activity.setCost(50);
        activity.setMaxCapacity(5);
        activity.setDescription("Sightseeing of qutub minar");
        activityList.add(activity);

        List<Destination> destinationList=new ArrayList<>();
        Destination dest=new Destination();
        dest.setCity("Delhi");
        dest.setName("Qutub Minar");
        destinationList.add(dest);

        User user=User.builder().
                Id(0).
                Name("User").
                Balance(5000).
                Blood_group(BloodGroups.A_POSITIVE).
                Membership(MembershipType.STANDARD).
                Nationality("Indian")
                .build();
        user=userService.CreateUser(user);

        //create an activity for each destination
        travelPackageService.CreatePackage("Testing",1, destinationList,500);
        travelPackageService.CreatePackage("Testing1",5, destinationList,5000);
        destinationService.AddActivityToDestination(activity,0);
        destinationService.AddActivityToDestination(activity,1);
        travelPackageService.GetPackageById(0);

        System.out.println();
        System.out.println();
        destinationService.ListAllActivities(0);
        System.out.println();
        activityService.GetActivitiesForUser(0);

        System.out.println("Booking Id is:"+travelPackageService.BookPackage(user.getId(),0));
        System.out.println("Booking Id is:"+travelPackageService.BookPackage(user.getId(),1));
        System.out.println("Booking Id for activity is:"+destinationService.ReserveActivityForUser(0,1,0));
        System.out.println();
        System.out.println();
        travelPackageService.GetUserDetails(0);
        System.out.println();
        travelPackageService.GetPackages();
        travelPackageService.GetPackageById(0);


        //TODO
        // Write Junit tests
    }
}