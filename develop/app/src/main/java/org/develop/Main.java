package org.develop;

import java.util.ArrayList;

import org.develop.Entur.EnturAdapter;
import org.develop.Port.EnturPort;
import org.develop.Service.RouteLogic;
import org.develop.Service.StopLogic;
import org.develop.TravelEnteties.Route;
import org.develop.TravelEnteties.Stop;

public class Main {

    public static void main(String[] args) {
        EnturPort adapter = new EnturAdapter();
        ArrayList<Route> routes = adapter.getAllRoutes();

        if (routes == null || routes.isEmpty()) {
            System.err.println("Feil: Ingen ruter lastet");
            return;
        }

        RouteLogic routeLogic = new RouteLogic(new StopLogic());
        testTravelSearch(routeLogic, routes);
    }

    private static void testTravelSearch(RouteLogic routeLogic, ArrayList<Route> routes) {
        Route rute = routes.get(0);
        ArrayList<Stop> stops = rute.getStops();

        Stop fromStop = stops.get(0);
        Stop toStop = stops.get(stops.size() - 1);

        RouteLogic.Result result = routeLogic.searchRouteByName("08:00", fromStop.getName(), toStop.getName(), rute);

        if (result != null && result.isSuccess()) {
            System.out.println("\n" + "Reisesøk OK - Reisetid: " + result.getTravelTime() + " minutter" );
            
        } else {
            System.out.println("Reisesøk feilet");
        }

        boolean isValid = RouteLogic.validateRoute(stops);
        System.out.println("Rute validert: " + (isValid ? "JA" : "NEI") + " (" + stops.size() + " stopp)");

        int totalTime = routeLogic.calculateTotalTravelTime(stops, "07:00");
        if (totalTime >= 0) {
            System.out.println("Total reisetid: " + totalTime + " minutter");
        }

        RouteLogic.Result fullResult = routeLogic.findBestTransport("08:05", fromStop, toStop, rute);
        System.out.println(fullResult);
    }
}