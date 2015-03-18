package com.jdbernard.nlsongs.model;

public enum ServiceType {
    SUN_AM("Sunday AM"), SUN_PM("Sunday PM"), WED("Wednesday");
    
    private String displayName;

    ServiceType(String displayName) { this.displayName = displayName; }

    @Override public String toString() { return this.displayName; }
}
