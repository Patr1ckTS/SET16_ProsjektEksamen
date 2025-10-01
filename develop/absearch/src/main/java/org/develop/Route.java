package org.develop;

import java.util.ArrayList;

public class Route {
    private String routeId;
    private String routeName;
    private Transport transport;
    private double price;
    private ArrayList<Stop> stops;

    public Route(String routeId, String routeName, Transport transport, double price, ArrayList<Stop> stops) {
        this.routeId = routeId;
        this.routeName = routeName;
        this.transport = transport;
        this.price = price;
        this.stops = stops;
    }
    
    
    public static Route calculateRoute(ArrayList<Route> availableRoutes, String startLocation, String endLocation) {
        return null; // TODO: Implementer logikk
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


    public Transport getTransport() {
        return transport;
    }


    public void setTransport(Transport transport) {
        this.transport = transport;
    }


    public double getPrice() {
        return price;
    }


    public void setPrice(double price) {
        this.price = price;
    }


    public ArrayList<Stop> getStops() {
        return stops;
    }


    public void setStops(ArrayList<Stop> stops) {
        this.stops = stops;
    }
    
}
