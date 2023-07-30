package org.example.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ActivityUserMapping {
    Integer Id;
    Integer UserId;
    Integer activity;

    @Override
    public String toString() {
        return "ActivityUserMapping{" +
                "Id=" + Id +
                ", UserId=" + UserId +
                ", activity=" + activity +
                '}';
    }
}
