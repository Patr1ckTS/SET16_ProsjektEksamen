package org.develop;

import java.util.ArrayList;
import java.util.Random;

import org.develop.Port.EnturRepository;
import org.develop.Service.RouteSearchService;
import org.develop.Service.RouteLogic;
import org.develop.TravelEnteties.Route;
import org.develop.TravelEnteties.Stop;
import org.develop.Entur.EnturRepositoryAdapter;

public class Main {
    public static void main(String[] args) {
        EnturRepository adapter = new EnturRepositoryAdapter();
        RouteSearchService service = new RouteSearchService(adapter);

        System.out.println("\n========================================");
        System.out.println("ROUTESEARCH - DI OG TILFELDIGE METODETESTER");
        System.out.println("========================================\n");

        System.out.println("Steg 1: Dependency Injection av EnturRepository port");
        System.out.println("Adapter (EnturRepositoryAdapter) instansiert i launcher");
        System.out.println("Port injisert i RouteSearchService via konstruktor\n");

        System.out.println("Steg 2: Last alle ruter via porten");
        ArrayList<Route> allRoutes = service.loadAllRoutes();
        if (allRoutes == null || allRoutes.isEmpty()) {
            System.err.println("Feil: Ingen ruter lastet\n");
            return;
        }
        System.out.println("Lastet " + allRoutes.size() + " ruter fra adapteret via porten\n");

        System.out.println("Steg 3: Vis alle tilgjengelige ruter");
        service.printAvailableRoutes();

        System.out.println("Steg 4: Tilfeldige metodetester på rutene");
        runRandomTests(service, allRoutes);

        System.out.println("========================================");
        System.out.println("TEST FULLFORT");
        System.out.println("========================================\n");
        System.out.println("Hexagonal Architecture:");
        System.out.println("- RouteSearch-domenet avhenger BARE av porten (EnturRepository)");
        System.out.println("- Adapter implementerer porten og henter data fra JSON");
        System.out.println("- Main (launcher) i adapters kobler domain + adapter sammen");
        System.out.println("- RouteSearch-domenet vet INGENTING om adapteret");
        System.out.println("- Data passerer porten som påkrevd");
        System.out.println();
    }

    private static void runRandomTests(RouteSearchService service, ArrayList<Route> allRoutes) {
        Random random = new Random();
        int numberOfTests = Math.min(3, allRoutes.size());

        for (int i = 0; i < numberOfTests; i++) {
            int randomIndex = random.nextInt(allRoutes.size());
            Route selectedRoute = allRoutes.get(randomIndex);

            System.out.println("TEST " + (i + 1) + ": Tilfeldig rute - " + selectedRoute.getRouteName());

            testValidateRoute(selectedRoute);
            testSearchOnRoute(service, selectedRoute);
            testCalculateTravelTime(service, selectedRoute);

            System.out.println();
        }
    }

    private static void testValidateRoute(Route route) {
        System.out.println("  [1] Validering av rute: ");
        boolean isValid = RouteLogic.validateRoute(route.getStops());
        System.out.println("      Resultat: " + (isValid ? "GYLDIG" : "UGYLDIG"));

        if (isValid) {
            System.out.println("      Antall stopp: " + route.getStops().size());
        }
    }

    private static void testSearchOnRoute(RouteSearchService service, Route route) {
        System.out.println("  [2] Rutesøk test: ");

        ArrayList<Stop> stops = route.getStops();
        if (stops.size() < 2) {
            System.out.println("      Ruten har for få stopp for søk");
            return;
        }

        Stop fromStop = stops.get(0);
        Stop toStop = stops.get(stops.size() - 1);
        String departureTime = "08:00";

        System.out.println("      Fra: " + fromStop.getName() + " til " + toStop.getName());
        System.out.println("      Ønsket tid: " + departureTime);

        RouteLogic.Result result = service.searchRoute(
            departureTime,
            fromStop.getName(),
            toStop.getName(),
            route
        );

        if (result != null && result.isSuccess()) {
            System.out.println("      Transport funnet - Reisetid: " + result.getTravelTime() + " minutter");
        } else {
            System.out.println("      Ingen transport funnet");
        }
    }

    private static void testCalculateTravelTime(RouteSearchService service, Route route) {
        System.out.println("  [3] Beregning av total reisetid: ");

        ArrayList<Stop> stops = route.getStops();
        if (stops.size() < 2) {
            System.out.println("      Ruten har for få stopp");
            return;
        }

        int totalTime = service.getRouteLogic().calculateTotalTravelTime(stops, "07:00");

        if (totalTime >= 0) {
            System.out.println("      Total tid fra første til siste stopp: " + totalTime + " minutter");
        } else {
            System.out.println("      Kunne ikke beregne traveltime");
        }
    }
}
