package org.example.models;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
//@RequiredArgsConstructor
public class PackageUserMapping {
    Integer Id;
    Integer UserId;
    Integer PackageId;
    Boolean active;

    @Override
    public String toString() {
        return "PackageUserMapping{" +
                "Id=" + Id +
                ", UserId=" + UserId +
                ", PackageId=" + PackageId +
                ", active=" + active +
                '}';
    }
}
