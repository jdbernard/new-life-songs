package com.jdbernard.nlsongs.model;

public enum ServiceType {
    SUN_AM("Sunday AM"), SUN_PM("Sunday PM"), WED("Wednesday");
    
    private final String displayName;

    ServiceType(String displayName) { this.displayName = displayName; }

    public String getDisplayName() { return this.displayName; }
}
