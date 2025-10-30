package org.develop.Entur;

import org.develop.Port.EnturRepository;
import org.develop.TravelEnteties.Route;
import org.develop.Service.RouteLogic;
import org.develop.Service.StopLogic;
import java.util.ArrayList;

public class EnturAdapterTest {

    public static void main(String[] args) {
        EnturRepository enturRepo = new EnturRepositoryAdapter();
        ArrayList<Route> allRoutes = enturRepo.getAllRoutes();

        if (allRoutes == null || allRoutes.isEmpty()) {
            System.err.println("Feil: Ingen ruter lastet");
            return;
        }

        RouteLogic routeLogic = new RouteLogic(new StopLogic());

        for (int i = 0; i < allRoutes.size(); i++) {
            Route route = allRoutes.get(i);
            boolean isValid = RouteLogic.validateRoute(route.getStops());
            System.out.println((i + 1) + ". " + route.getRouteName() + " - Validert: " + (isValid ? "Ja" : "Nei"));
        }

        Route rute101 = allRoutes.get(0);
        System.out.println("\nTest rutesøk:");
        RouteLogic.Result result1 = routeLogic.searchRouteByName(
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

        System.out.println("\nTest av total reisetidsberegning:");
        if (rute101.getStops().size() >= 2) {
            int totalTime = routeLogic.calculateTotalTravelTime(
                rute101.getStops(),
                "08:00"
            );
            System.out.println("Total reisetid for " + rute101.getRouteName() + ": " + totalTime + " minutter\n");
        }
    }
}
