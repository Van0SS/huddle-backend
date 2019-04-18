package com.orange.huddle.model;

public enum ActivityType {
    CITY_BIKE("Quick Spin - City Bike Ride"),
    ESCOOTER("Electric scooter City Ride"),
    WALK("Walk around Downtown"),
    RUN("Raleigh Runners"),
    CLIMB("Climbers"),
    EAT("Eat and Refuel"),
    MOVIE("Movies"),
    GYM("Wellness at Work - Gym");

    private final String name;

    ActivityType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
