package com.orange.huddle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
public class Activity {
    @Id
    String id;
    @NotNull
    ActivityType activity;
    String activityName;
    boolean sameGender;
    @ElementCollection(fetch = FetchType.EAGER)
    Set<TimeOfDay> times;
    String details;
}
