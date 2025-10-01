package org.develop.abSearch;

public class Stop {
    private String stopId;
    private String location;
    private String name;

    public Stop(String stopId, String location, String name) {
        this.stopId = stopId;
        this.location = location;
        this.name = name;
    }

    // Getters and setters
    public String getStopId() {
        return stopId;
    }

    public void setStopId(String stopId) {
        this.stopId = stopId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}