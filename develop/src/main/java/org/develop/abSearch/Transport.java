package org.develop.abSearch;

class Transport implements LocationHandling {
    String transportId;
    int capacity;
    String startLocation;
    String endLocation;
    String departureTime;
    String arrivalTime;

    public Transport(String transportId, int capacity, String startLocation, String endLocation, String departureTime, String arrivalTime) {
        this.transportId = transportId;
        this.capacity = capacity;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    @Override
    public double calculateDistance() {
        // TODO: Implementer logikk
        return 0;
    }

    @Override
    public double estimateTravelTime() {
        // TODO: Implementer logikk
        return 0;
    }
}