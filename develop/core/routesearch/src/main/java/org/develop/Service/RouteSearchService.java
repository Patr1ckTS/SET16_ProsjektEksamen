package org.develop.Service;

import java.util.ArrayList;
import org.develop.Port.EnturRepository;
import org.develop.TravelEnteties.Route;
import org.develop.TravelEnteties.Stop;
import org.develop.Interface.StopService;

public class RouteSearchService {
    private final EnturRepository enturRepository;
    private final RouteLogic routeLogic;
    private final StopService stopService;

    public RouteSearchService(EnturRepository enturRepository) {
        this.enturRepository = enturRepository;
        this.stopService = new StopLogic();
        this.routeLogic = new RouteLogic(stopService);
    }

    public ArrayList<Route> loadAllRoutes() {
        return enturRepository.getAllRoutes();
    }

    public void printAvailableRoutes() {
        ArrayList<Route> routes = loadAllRoutes();
        System.out.println("Tilgjengelige ruter:");
        for (int i = 0; i < routes.size(); i++) {
            Route route = routes.get(i);
            System.out.println((i + 1) + ". " + route.getRouteName() + " (" + route.getRouteId() + ")");
            if (route.getTransport() != null) {
                System.out.println("   Type: " + route.getTransport().getTransportType());
            }
            System.out.println("   Pris: " + route.getPrice() + " NOK");
            System.out.println("   Stopp: " + route.getStops().size());
        }
        System.out.println();
    }

    public RouteLogic.Result searchRoute(String departureTime, String startLocation,
            String endLocation, Route route) {
        Stop startStop = stopService.findStopByName(route.getStops(), startLocation);
        Stop endStop = stopService.findStopByName(route.getStops(), endLocation);

        if (startStop == null || endStop == null) {
            return new RouteLogic.Result(false, null, null,
                "Stoppested ikke funnet", null, null, null, null, null, 0);
        }

        return routeLogic.findBestTransport(departureTime, startStop, endStop, route);
    }

    public RouteLogic.Result validateAndTestRoute(Route route) {
        if (route == null) {
            return new RouteLogic.Result(false, null, null,
                "Rute er null", null, null, null, null, null, 0);
        }

        ArrayList<Stop> stops = route.getStops();
        if (!RouteLogic.validateRoute(stops)) {
            return new RouteLogic.Result(false, null, null,
                "Rute validering feilet", null, null, null, null, null, 0);
        }

        System.out.println("Rute '" + route.getRouteName() + "' validert OK");
        System.out.println("Antall stopp: " + stops.size());
        return new RouteLogic.Result(true,
            route.getTransport() != null ? route.getTransport().getTransportType() : "Ukjent",
            route.getRouteName(),
            "Rute validert", null, null, null, null, null, 0);
    }

    public void printRouteDetails(Route route) {
        if (route == null) {
            System.out.println("Rute: null\n");
            return;
        }

        System.out.println("Ruteinformasjon:");
        System.out.println("ID: " + route.getRouteId());
        System.out.println("Navn: " + route.getRouteName());
        System.out.println("Pris: " + route.getPrice() + " NOK");

        if (route.getTransport() != null) {
            System.out.println("Transport: " + route.getTransport().getTransportType());
        }

        ArrayList<Stop> stops = route.getStops();
        System.out.println("Antall stopp: " + stops.size());
        System.out.println("Stopp:");
        for (Stop stop : stops) {
            System.out.println("  - " + stop.toDetailedString());
        }
        System.out.println();
    }

    public RouteLogic getRouteLogic() {
        return routeLogic;
    }
}
