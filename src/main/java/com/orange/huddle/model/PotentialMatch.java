package com.orange.huddle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class PotentialMatch {
    Set<String> users;
}
