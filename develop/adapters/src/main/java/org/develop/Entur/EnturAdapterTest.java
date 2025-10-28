package org.develop.Entur;

import org.develop.Port.EnturRepository;
import org.develop.Service.RouteSearchService;
import org.develop.TravelEnteties.Route;
import org.develop.Service.RouteLogic;
import java.util.ArrayList;

public class EnturAdapterTest {

    public static void main(String[] args) {
        System.out.println("\n========================================");
        System.out.println("ENTUR ADAPTER TEST - DI og Metodetester");
        System.out.println("========================================\n");

        System.out.println("Steg 1: Instantiering av EnturRepositoryAdapter");
        EnturRepository enturRepo = new EnturRepositoryAdapter();
        System.out.println("Adapter opprettet\n");

        System.out.println("Steg 2: Dependency Injection av porten til RouteSearchService");
        RouteSearchService routeSearchService = new RouteSearchService(enturRepo);
        System.out.println("Service mottok porten via DI\n");

        System.out.println("Steg 3: Last alle ruter via porten (uten direkte adapter-kall)");
        ArrayList<Route> allRoutes = routeSearchService.loadAllRoutes();
        if (allRoutes == null || allRoutes.isEmpty()) {
            System.err.println("Feil: Ingen ruter lastet");
            return;
        }
        System.out.println("Lastet " + allRoutes.size() + " ruter via porten\n");

        System.out.println("Steg 4: Vis alle tilgjengelige ruter");
        routeSearchService.printAvailableRoutes();

        System.out.println("Steg 5: Validering av rutene (RouteLogic.validateRoute test)");
        for (int i = 0; i < allRoutes.size(); i++) {
            Route route = allRoutes.get(i);
            boolean isValid = RouteLogic.validateRoute(route.getStops());
            System.out.println((i + 1) + ". " + route.getRouteName() + " - Validert: " + (isValid ? "OK" : "FEIL"));
        }
        System.out.println();

        System.out.println("Steg 6: Vis detaljer for første rute");
        Route rute101 = allRoutes.get(0);
        routeSearchService.printRouteDetails(rute101);

        System.out.println("Steg 7: Test rutesøk operasjoner (RouteLogic.findBestTransport)");
        System.out.println("Test 1 - Søk fra Fredrikstad bussterminal til Sarpsborg bussterminal kl 08:15:");
        RouteLogic.Result result1 = routeSearchService.searchRoute(
            "08:15",
            "Fredrikstad bussterminal",
            "Sarpsborg bussterminal",
            rute101
        );

        if (result1 != null && result1.isSuccess()) {
            System.out.println(result1);
        } else {
            System.out.println("Ingen transport funnet\n");
        }

        System.out.println("Test 2 - Søk med senere tidspunkt kl 14:30:");
        RouteLogic.Result result2 = routeSearchService.searchRoute(
            "14:30",
            "Fredrikstad bussterminal",
            "Sarpsborg bussterminal",
            rute101
        );

        if (result2 != null && result2.isSuccess()) {
            System.out.println(result2);
        } else {
            System.out.println("Ingen transport funnet\n");
        }

        System.out.println("Steg 8: Test calculateTotalTravelTime metode");
        if (rute101.getStops().size() >= 2) {
            int totalTime = routeSearchService.getRouteLogic().calculateTotalTravelTime(
                rute101.getStops(),
                "08:00"
            );
            System.out.println("Total reisetid for " + rute101.getRouteName() + ": " + totalTime + " minutter\n");
        }

        System.out.println("========================================");
        System.out.println("TEST OPPSUMMERING");
        System.out.println("========================================");
        System.out.println("Adapter: EnturRepositoryAdapter");
        System.out.println("Port: EnturRepository");
        System.out.println("Service: RouteSearchService med DI");
        System.out.println("Metodetester: RouteLogic og StopLogic");
        System.out.println("\nData flow: JSON -> Adapter -> Port -> Service -> Operasjoner");
        System.out.println("========================================\n");
    }
}
