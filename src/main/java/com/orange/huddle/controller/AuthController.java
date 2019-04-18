package com.orange.huddle.controller;

import com.orange.huddle.model.Preferences;
import com.orange.huddle.model.Signup;
import com.orange.huddle.repository.PreferencesRepo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final PreferencesRepo preferencesRepo;

    public AuthController(PreferencesRepo preferencesRepo) {
        this.preferencesRepo = preferencesRepo;
    }

    @PostMapping("/login")
    public String login() {
        return "";
    }

    @PostMapping("/signup")
    public void signup(@RequestBody Signup signup) {
        preferencesRepo.saveAndFlush(Preferences.builder().gender(signup.getGender()).userId(signup.getEmail()).enabled(true).lastLogin(Instant.now()).build());
    }
}
