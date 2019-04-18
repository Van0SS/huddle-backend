package com.orange.huddle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.Instant;
import java.util.Map;
import java.util.Set;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
public class Preferences {
    @Id
    String userId;
    @ElementCollection
    Set<String> interests;
    String bio;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    Map<ActivityType, Activity> activities;
    //TODO default activity prefs?
    Gender gender;
    Instant lastLogin;
    boolean enabled;
}
