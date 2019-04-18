package com.orange.huddle.model;

import lombok.Data;

@Data
public class Signup {
    String email;
    String password;
    Gender gender;
}
