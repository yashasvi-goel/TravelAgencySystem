package org.example.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class User {
    Integer Id ;
    String Name;
    String Nationality;
//    DOB(date) TODO
    BloodGroups Blood_group;
    MembershipType Membership;
    Integer Balance;
}

