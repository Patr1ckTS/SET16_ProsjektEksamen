package org.develop.Entur.DTO;

import java.util.ArrayList;

/**
 * DTO for Entur Route data
 * Inneholder ALL data fra Entur API: route, transport og stops
 * Bruker kun primitiver og inner classes - INGEN domene-objekter
 */
public class EnturRouteDTO {

    // Route data
    private String routeId;
    private String routeName;
    private double price;

    // Transport data (nested objekt som matcher JSON-strukturen)
    private TransportData transport;

    // Stop data (liste med inner class)
    private ArrayList<StopData> stops;

    // Inner class for transport-informasjon
    public static class TransportData {
        private String transportId;
        private String transportType;
        private String startLocation;
        private String endLocation;
        private String departureTime;
        private String arrivalTime;

        // Json konstruktør for Jackson
        public TransportData() {}

        public TransportData(String transportId, String transportType, String startLocation,
                            String endLocation, String departureTime, String arrivalTime) {
            this.transportId = transportId;
            this.transportType = transportType;
            this.startLocation = startLocation;
            this.endLocation = endLocation;
            this.departureTime = departureTime;
            this.arrivalTime = arrivalTime;
        }

        // Getters and Setters
        public String getTransportId() {
            return transportId;
        }

        public void setTransportId(String transportId) {
            this.transportId = transportId;
        }

        public String getTransportType() {
            return transportType;
        }

        public void setTransportType(String transportType) {
            this.transportType = transportType;
        }

        public String getStartLocation() {
            return startLocation;
        }

        public void setStartLocation(String startLocation) {
            this.startLocation = startLocation;
        }

        public String getEndLocation() {
            return endLocation;
        }

        public void setEndLocation(String endLocation) {
            this.endLocation = endLocation;
        }

        public String getDepartureTime() {
            return departureTime;
        }

        public void setDepartureTime(String departureTime) {
            this.departureTime = departureTime;
        }

        public String getArrivalTime() {
            return arrivalTime;
        }

        public void setArrivalTime(String arrivalTime) {
            this.arrivalTime = arrivalTime;
        }
    }

    // Inner class for stop-informasjon
    public static class StopData {
        private String stopId;
        private String location;
        private String name;
        private String departureTime;
        private ArrayList<String> departureTimes;
        private int minutesAfterDeparture;

        // Json konstruktør for Jackson
        public StopData() {}

        public StopData(String stopId, String location, String name, int minutesAfterDeparture) {
            this.stopId = stopId;
            this.location = location;
            this.name = name;
            this.minutesAfterDeparture = minutesAfterDeparture;
        }

        public StopData(String stopId, String location, String name, ArrayList<String> departureTimes) {
            this.stopId = stopId;
            this.location = location;
            this.name = name;
            this.departureTimes = departureTimes;
        }

        // Getters and Setters
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

        public String getDepartureTime() {
            return departureTime;
        }

        public void setDepartureTime(String departureTime) {
            this.departureTime = departureTime;
        }

        public ArrayList<String> getDepartureTimes() {
            return departureTimes;
        }

        public void setDepartureTimes(ArrayList<String> departureTimes) {
            this.departureTimes = departureTimes;
        }

        public int getMinutesAfterDeparture() {
            return minutesAfterDeparture;
        }

        public void setMinutesAfterDeparture(int minutesAfterDeparture) {
            this.minutesAfterDeparture = minutesAfterDeparture;
        }
    }

    // Json konstruktør for Jackson
    public EnturRouteDTO() {}

    public EnturRouteDTO(String routeId, String routeName, double price,
                        TransportData transport, ArrayList<StopData> stops) {
        this.routeId = routeId;
        this.routeName = routeName;
        this.price = price;
        this.transport = transport;
        this.stops = stops;
    }

    // Getters and Setters
    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public TransportData getTransport() {
        return transport;
    }

    public void setTransport(TransportData transport) {
        this.transport = transport;
    }

    public ArrayList<StopData> getStops() {
        return stops;
    }

    public void setStops(ArrayList<StopData> stops) {
        this.stops = stops;
    }
}
