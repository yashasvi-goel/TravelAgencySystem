package org.example.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Activity {
    Integer Id;
    String Name;
    String Description;
    Integer Cost;
    Integer MaxCapacity;
    String Destination;

    @Override
    public String toString() {
        return "Activity{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Description='" + Description + '\'' +
                ", Cost=" + Cost +
                ", MaxCapacity=" + MaxCapacity +
                ", Destination='" + Destination + '\'' +
                '}';
    }
}
