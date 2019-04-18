package com.orange.huddle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Set;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
public class ConfirmedMatch {
    @Id
    String id;
    @ElementCollection
    @NotNull
    Set<String> users;
    @NotNull
    MatchStatus status;
    @NotNull
    String usersConcat;
    int score;
    @NotNull
    Instant date;
}
