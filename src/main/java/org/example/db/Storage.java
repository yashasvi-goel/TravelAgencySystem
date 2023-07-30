package org.example.db;

import org.example.models.*;

import java.util.*;

public class Storage {

    Map<Integer, TravelPackage> packageMap;//package_id: package
    Map<Integer, Set<PackageUserMapping>> userPackage;//user_id:Set<PackageUserMapping>
    Map<Integer, Set<PackageUserMapping>> packageUserMapping; // package_id:Set<PackageUserMapping>
    Map<Integer, PackageUserMapping> packageUserMap; // bookind_id:PackageUserMapping
    Map<Integer, Set<ActivityUserMapping>> userActivity;//user_id:Set<PackageUserMapping>
    Map<Integer, Set<ActivityUserMapping>> activityUserMapping; // act_id:Set<PackageUserMapping>
    Map<Integer, ActivityUserMapping> activityUserMap; // bookind_id:PackageUserMapping
    Map<Integer, Destination> destinationMap;// dest_id:Destination
    Map<Integer, Activity> activityMap;// act_id:Activity
    Map<Integer, User> userMap;// user_id: User
    Set<String> activities;
    public Storage(){
        this.packageMap=new HashMap<>();
        this.userPackage=new HashMap<>();
        this.userMap=new HashMap<>();
        this.packageUserMapping=new HashMap<>();
        this.packageUserMap=new HashMap<>();
        this.destinationMap=new HashMap<>();
        this.activityMap=new HashMap<>();
        this.activities=new HashSet<>();
        this.activityUserMapping=new HashMap<>();
        this.activityUserMap=new HashMap<>();
        this.userActivity=new HashMap<>();
    }

    public TravelPackage AddPackage(TravelPackage travelPackage) {
        // equivalent of a SQL (insert)
        Integer primaryKey=this.packageMap.size(); //Would be done using SQL functionality
        travelPackage.setId(primaryKey);
        this.packageMap.put(primaryKey,travelPackage);
        this.packageUserMapping.put(travelPackage.getId(),new HashSet<>());
        return travelPackage;
    }

    public User AddUser(User user) {
        // equivalent of a SQL (insert)
        Integer primaryKey=this.userMap.size(); //Would be done using SQL functionality
        user.setId(primaryKey);
        this.userMap.put(primaryKey,user);
        this.userPackage.put(user.getId(),new HashSet<>());
        this.userActivity.put(user.getId(),new HashSet<>());
        return user;
    }
    public boolean UpdateUser(User user) {
        // equivalent of a SQL (update)
        this.userMap.put(user.getId(), user);
        return true;
    }

    public User GetUser(Integer userId) {
        return this.userMap.get(userId);
    }

    public List<Integer> AddDestinations(List<Destination> destination) {
        // equivalent of a SQL (insert in bulk)
        List<Integer> ans=new ArrayList<>();
        destination.forEach(dest->{
            Integer primaryKey=this.destinationMap.size(); //Would be done using SQL functionality
            dest.setId(primaryKey);
            this.destinationMap.put(primaryKey,dest);
            ans.add(primaryKey);
        });
        return ans;
    }

    public void UpdateDestination(Destination destination) {
        // equivalent of a SQL (update)
        this.destinationMap.put(destination.getId(),destination);
    }


    public Integer AddActivity(Activity activity) {
        // equivalent of a SQL (insert)
        if(CheckActivity(activity.getName())){
            return -1;
        }
        Integer primaryKey=this.activityMap.size(); //Would be done using SQL functionality
        activity.setId(primaryKey);
        this.activityMap.put(primaryKey,activity);
        this.activities.add(activity.getName());
        this.activityUserMapping.put(activity.getId(), new HashSet<>());
        return primaryKey;
    }

    public boolean CheckActivity(String activityName) {
        // equivalent of a SQL (primary key constraint on the PK)
        return this.activities.contains(activityName);
    }
    public TravelPackage GetPackage(Integer id) {
        // equivalent of a SQL (select * from package where id ={id})
        return this.packageMap.get(id);
    }

    public Map<Integer, TravelPackage> GetPackages() {
        // equivalent of a SQL (select * from package where id ={id})
        return this.packageMap;
    }

    public Set<PackageUserMapping> GetPackageByUser(Integer userId) {
        // equivalent of a SQL (select * from package_user_mapping where user_id ={userId})
        return this.userPackage.get(userId);
    }
    public Set<PackageUserMapping> GetUsersByPackage(Integer id) {
        // equivalent of a SQL (select * from package_user_mapping where package_id ={id})
        return this.packageUserMapping.get(id);
    }
    public Set<ActivityUserMapping> GetActivityByUser(Integer userId) {
        // equivalent of a SQL (select * from package_user_mapping where user_id ={userId})
        return this.userActivity.get(userId);
    }
    public Set<ActivityUserMapping> GetUsersByActivity(Integer id) {
        // equivalent of a SQL (select * from package_user_mapping where package_id ={id})
        return this.activityUserMapping.get(id);
    }

    public PackageUserMapping BookPackage(PackageUserMapping packageUserMapping, Integer capacity, Integer userId) {
        // equivalent of a SQL optimistic locking inside a transaction.

        Integer primaryKey=this.packageMap.size(); //Would be done using SQL functionality
        packageUserMapping.setId(primaryKey);
        packageUserMap.put(primaryKey,packageUserMapping);
        Set<PackageUserMapping> packageUserMappings = this.packageUserMapping.get(packageUserMapping.getPackageId());
        if(packageUserMappings.size()<capacity){
            packageUserMappings.add(packageUserMapping);
            this.packageUserMapping.put(packageUserMapping.getPackageId(),packageUserMappings);
        }else{
            System.out.println("There is no more space left on the package.");
            return null;
        }
        this.userPackage.get(userId).add(packageUserMapping);
        return packageUserMapping;
    }
    
    public ActivityUserMapping BookActivity(ActivityUserMapping activityUserMapping, Integer capacity, Integer userId) {
        // equivalent of a SQL optimistic locking inside a transaction.

        Integer primaryKey=this.activityMap.size(); //Would be done using SQL functionality
        activityUserMapping.setId(primaryKey);
        activityUserMap.put(primaryKey,activityUserMapping);
        Set<ActivityUserMapping> activityUserMappings = this.activityUserMapping.get(activityUserMapping.getActivity());
        if(activityUserMappings.size()<capacity){
            activityUserMappings.add(activityUserMapping);
            this.activityUserMapping.put(activityUserMapping.getActivity(),activityUserMappings);
        }else{
            System.out.println("There is no more space left on the activity.");
            return null;
        }
        this.userActivity.get(userId).add(activityUserMapping);
        return activityUserMapping;
    }

    public Destination GetDestination(Integer id) {
        // equivalent of a SQL (select * from destination where id ={id})
        return this.destinationMap.get(id);
    }
    public Activity GetActivity(Integer id) {
        // equivalent of a SQL (select * from activity where id ={id})
        return this.activityMap.get(id);
    }

    public Map<Integer, Activity> GetActivityMap(List<Integer> ids) {
        // equivalent of a SQL (select * from activity where id in (ids))
        Map<Integer,Activity> ans=new HashMap<>();
        ids.forEach(id->{
            ans.put(id,this.activityMap.get(id));
        });
        return ans;
    }
}
