package com.orange.huddle.controller;

import com.google.common.collect.Sets;
import com.orange.huddle.model.Activity;
import com.orange.huddle.model.ActivityType;
import com.orange.huddle.model.MatchStatus;
import com.orange.huddle.model.MatchView;
import com.orange.huddle.model.Preferences;
import com.orange.huddle.repository.PreferencesRepo;
import com.orange.huddle.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/match")
public class MatchController {

    private final MatchService matchService;
    private final PreferencesRepo preferencesRepo;

    @Autowired
    public MatchController(MatchService matchService, PreferencesRepo preferencesRepo) {
        this.matchService = matchService;
        this.preferencesRepo = preferencesRepo;
    }

    @GetMapping
    public List<MatchView> get(String userId) {
        return matchService.getMatches(userId).stream()
                .filter(mat -> mat.getStatus() != MatchStatus.UNMATCHED)
                .map(m -> {
                    String userId2 = m.getUsers().stream()
                            .filter(user -> !userId.equals(user))
                            .findFirst().orElseThrow(IllegalArgumentException::new);
                    Preferences prefs = preferencesRepo.findById(userId).orElseThrow(IllegalArgumentException::new);
                    Preferences prefs2 = preferencesRepo.findById(userId2).orElseThrow(IllegalArgumentException::new);
                    Map<ActivityType, Activity> commonActivities = prefs2.getActivities().values().stream()
                            .filter(act2 -> prefs.getActivities().containsKey(act2.getActivity()))
                            .map(act2 -> {
                                Activity act1 = prefs.getActivities().get(act2.getActivity());
                                return act2.toBuilder()
                                        .times(Sets.intersection(act1.getTimes(), act2.getTimes()))
                                        .build();
                            })
                            .collect(Collectors.toMap(Activity::getActivity, Function.identity()));

                    return MatchView.builder()
                            .bio(prefs2.getBio())
                            .gender(prefs2.getGender())
                            .commonActivities(commonActivities)
                            .date(m.getDate())
                            .userId(userId2)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @PostMapping("/unmatch")
    public void unmatch(String userId, String unmatchUserId) {
        matchService.unmatch(userId, unmatchUserId);
    }
}
