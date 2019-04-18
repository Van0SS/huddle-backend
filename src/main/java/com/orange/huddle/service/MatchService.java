package com.orange.huddle.service;

import com.google.common.collect.ImmutableSet;
import com.orange.huddle.model.Activity;
import com.orange.huddle.model.ActivityType;
import com.orange.huddle.model.ConfirmedMatch;
import com.orange.huddle.model.Gender;
import com.orange.huddle.model.MatchStatus;
import com.orange.huddle.model.PotentialMatch;
import com.orange.huddle.model.TimeOfDay;
import com.orange.huddle.repository.MatchRepo;
import com.orange.huddle.repository.PreferencesRepo;
import com.orange.huddle.util.MatchUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class MatchService {
    private final PreferencesRepo preferencesRepo;
    private final MatchRepo matchRepo;
    private final long newMatchesAmount;
    private final AtomicReference<List<PotentialMatch>> potentialMatches;

    PeriodicTrigger periodicTrigger;

    @Autowired
    public MatchService(PreferencesRepo preferencesRepo, MatchRepo matchRepo) {
        this.preferencesRepo = preferencesRepo;
        this.matchRepo = matchRepo;
        this.newMatchesAmount = 3;
        this.potentialMatches = new AtomicReference<>(new ArrayList<>());
    }

    public void updatePotentialMatches() {
        Set<PotentialMatch> newPotentialMatches = new HashSet<>();
        preferencesRepo.findAll().forEach(pref -> {
            preferencesRepo.findAll().forEach(pref2 -> {
                if (!pref.getUserId().equals(pref2.getUserId()) &&
                        matchRepo.findAllByUsersConcat(MatchUtil.getUsersConcat(pref.getUserId(), pref2.getUserId())).isEmpty()) {
                    Map<ActivityType, Activity> activities = pref.getActivities();

                    activities.values().forEach(a -> {
                        Map<ActivityType, Activity> activities2 = pref2.getActivities();
                        activities2.values().forEach(a2 -> {
                            if (((a.isSameGender() && pref.getGender() != Gender.OTHER) || (a2.isSameGender() && pref2.getGender() != Gender.OTHER)) && pref.getGender() != pref2.getGender()) {
                                return;
                            }
                            Set<TimeOfDay> times = new HashSet<>();
                            if (a.getActivity() == a2.getActivity()) {
                                a.getTimes().forEach(t -> {
                                    a2.getTimes().forEach(t2 -> {
                                        if (t == t2) {
                                            times.add(t);
                                        }
                                    });
                                });
                            }
                            if (times.size() > 0) {
                                newPotentialMatches.add(PotentialMatch.builder().users(ImmutableSet.of(pref.getUserId(), pref2.getUserId())).build());
                            }
                        });
                    });
                }

            });

        });
        log.info("potentialMatches=" + newPotentialMatches);
        potentialMatches.set(new ArrayList<>(newPotentialMatches));

    }

    public Collection<ConfirmedMatch> getMatches(String userId) {
        Map<String, ConfirmedMatch> matches = getMatchesByUserId(userId);

        long freshMatches = matches.values().stream()
                .filter(mat -> mat.getDate().isAfter(Instant.now().minus(Duration.ofDays(7))))
                .count();
        long needMore = newMatchesAmount - freshMatches;

        if (needMore > 0) {
            Map<String, ConfirmedMatch> moreMatches = potentialMatches.get().stream()
                    .filter(mat -> mat.getUsers().contains(userId))
                    .filter(mat -> !matches.containsKey(MatchUtil.getUsersConcat(mat.getUsers())))
                    .limit(needMore)
                    .map(mat -> ConfirmedMatch.builder()
                            .id(UUID.randomUUID().toString())
                            .date(Instant.now())
                            .status(MatchStatus.MATCHED)
                            .users(mat.getUsers())
                            .usersConcat(MatchUtil.getUsersConcat(mat.getUsers()))
                            .build()
                    )
                    .collect(Collectors.toMap(ConfirmedMatch::getUsersConcat, Function.identity()));
            matchRepo.saveAll(moreMatches.values());
            matches.putAll(moreMatches);
        }
        return matches.values();
    }

    public void unmatch(String userId1, String userId2) {
        Set<String> users = ImmutableSet.of(userId1, userId2);

        ConfirmedMatch match = Stream.generate(matchRepo.findAll().iterator()::next)
                .limit(matchRepo.count())
                .filter(mat -> mat.getUsers().equals(users))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        match.setStatus(MatchStatus.UNMATCHED);
        matchRepo.saveAndFlush(match);
    }

    private Map<String, ConfirmedMatch> getMatchesByUserId(String id) {
        return Stream.generate(matchRepo.findAll().iterator()::next)
                .limit(matchRepo.count())
                .filter(mat -> mat.getUsers().contains(id))
                .collect(Collectors.toMap(ConfirmedMatch::getUsersConcat, Function.identity()));

    }


}
