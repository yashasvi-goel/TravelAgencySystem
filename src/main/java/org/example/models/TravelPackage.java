package org.example.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class TravelPackage {
    Integer Id;
    String Name;
    Integer Capacity;
    List<Integer> Destinations;
    Integer Cost;

    @Override
    public String toString() {
        return "TravelPackage{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Capacity=" + Capacity +
                ", Destinations=" + Destinations +
                ", Cost=" + Cost +
                '}';
    }
}
