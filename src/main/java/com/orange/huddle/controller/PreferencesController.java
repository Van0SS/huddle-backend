package com.orange.huddle.controller;

import com.google.common.collect.ImmutableMap;
import com.orange.huddle.model.Activity;
import com.orange.huddle.model.ActivityType;
import com.orange.huddle.model.Preferences;
import com.orange.huddle.repository.ActivityRepo;
import com.orange.huddle.repository.PreferencesRepo;
import com.orange.huddle.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/prefs")
public class PreferencesController {
    private final PreferencesRepo preferencesRepo;
    private final ActivityRepo activityRepo;
    private final MatchService matchService;

    @Autowired
    public PreferencesController(PreferencesRepo preferencesRepo, ActivityRepo activityRepo, MatchService matchService) {
        this.preferencesRepo = preferencesRepo;
        this.activityRepo = activityRepo;
        this.matchService = matchService;
    }

    @GetMapping
    public Preferences get(String userId) {
        Preferences preferences = preferencesRepo.findById(userId).orElseThrow(IllegalArgumentException::new);
        Map<ActivityType, Activity> disabled = Stream.of(ActivityType.values())
                .filter(a -> !preferences.getActivities().keySet().contains(a))
                .map(a -> Activity.builder()
                        .activity(a)
                        .activityName(a.getName())
                        .times(Collections.emptySet())
                        .build())
                .collect(Collectors.toMap(Activity::getActivity, Function.identity()));
        Map<ActivityType, Activity> all = new ImmutableMap.Builder<ActivityType, Activity>().putAll(preferences.getActivities()).putAll(disabled).build();
        return preferences.toBuilder().activities(all).build();
    }

    @PostMapping
    public void update(@RequestBody Preferences preferences) {

        Map<ActivityType, Activity> enabled = preferences.getActivities().values().stream()
                .filter(act -> !act.getTimes().isEmpty())
                .map(act -> act.toBuilder()
                        .id(act.getActivity().toString() + "+" + preferences.getUserId())
                        .activityName(act.getActivity().getName())
                        .build()
                )
                .collect(Collectors.toMap(Activity::getActivity, Function.identity()));

        activityRepo.saveAll(enabled.values());

        Preferences checked = preferences.toBuilder().activities(enabled).lastLogin(Instant.now()).build();
        preferencesRepo.saveAndFlush(checked);

        matchService.updatePotentialMatches();
    }
}
