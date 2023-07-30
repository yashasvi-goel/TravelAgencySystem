package org.example.Services;

import org.example.db.Storage;
import org.example.models.Activity;
import org.example.models.PackageUserMapping;
import org.example.models.TravelPackage;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class PackageUserMappingService {
    Storage storage;

    public PackageUserMappingService(Storage storage) {
        this.storage = storage;
    }

    public Map<Integer, Activity> GetActivityByIds(List<Integer> Ids){
        return storage.GetActivityMap(Ids);
    }

    public PackageUserMapping AddMapping(TravelPackage travelPackage, Integer user) {
        Set<PackageUserMapping> packageUserMappings = storage.GetUsersByPackage(travelPackage.getId());
        if(Objects.isNull(packageUserMappings)){
            System.out.println("packageUserMapping doesn't exist");
            return null;
        }
        PackageUserMapping mapping= PackageUserMapping.builder().
                PackageId(travelPackage.getId()).
                UserId(user).
                active(true).build();
        return storage.BookPackage(mapping, travelPackage.getCapacity(), user);
    }
}
