package org.example.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Destination {
    Integer Id;
    String Name;
    String City;
    List<Integer> Activites;

    @Override
    public String toString() {
        return "Destination{" +
                ", Name='" + Name + '\'' +
                ", City='" + City + '\'' +
                ", Activites=" + Activites ;
    }
}
