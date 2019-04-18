package com.orange.huddle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Map;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class MatchView {
    @NotNull
    String userId;
    Gender gender;
    String bio;
    Map<ActivityType, Activity> commonActivities;
    @NotNull
    Instant date;
}
