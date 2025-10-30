package org.develop;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.develop.Entur.EnturAdapter;
import org.develop.Port.EnturPort;
import org.develop.Service.RouteLogic;
import org.develop.Service.StopLogic;
import org.develop.TravelEnteties.Route;
import org.develop.TravelEnteties.Stop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EnturIntergration {

    private EnturPort adapter;
    private RouteLogic routeLogic;
    private StopLogic stopLogic;

    // Vi må sette opp adapter og logikk før hver test
    // Slik at vi har et rent miljø for hver test
    @BeforeEach
    void setUp() {
        adapter = new EnturAdapter();
        stopLogic = new StopLogic();
        routeLogic = new RouteLogic(stopLogic);
    }


    @Test
    @DisplayName("Adapter dataflyt: Rute101 søk mellom to stopp med konkret beregning")
    void testAdapterDataFlow_Rute101_SearchFromTerminalToFirstStop() {
        // Arrange
        Route route = ((EnturAdapter) adapter).getRoute("R101");
        Stop stop1 = route.getStops().get(1);
        Stop stop2 = route.getStops().get(2);

        // Act
        RouteLogic.Result result = routeLogic.searchRouteByName(
            "08:00",
            stop1.getName(),
            stop2.getName(),
            route
        );

        // Assert
        assertTrue(result.isSuccess());
        assertEquals(4, result.getTravelTime());
    }

    @Test
    @DisplayName("Adapter dataflyt: Rute203 søk mellom to stopp med konkret beregning")
    void testAdapterDataFlow_Rute203_SearchFromTerminalToLastStop() {
        // Arrange - Tester getAllRoutes() i tillegg til getRoute() for å verifisere begge metodene
        Route route = adapter.getAllRoutes().get(3);
        Stop stop1 = route.getStops().get(1);
        Stop stop2 = route.getStops().get(2);

        // Act
        RouteLogic.Result result = routeLogic.searchRouteByName(
            "07:00",
            stop1.getName(),
            stop2.getName(),
            route
        );

        // Assert
        assertTrue(result.isSuccess());
        assertEquals(8, result.getTravelTime());
    }

    @Test
    @DisplayName("Adapter dataflyt: Validering av alle ruter og beregning av reisetid")
    void testAdapterDataFlow_AllRoutes_ValidateAndCalculateTravelTime() {
        // Arrange
        ArrayList<Route> allRoutes = adapter.getAllRoutes();

        // Act & Assert - Itererer for å verifisere at alle ruter fra JSON er korrekt mappet
        for (Route route : allRoutes) {
            ArrayList<Stop> stops = route.getStops();

            assertTrue(RouteLogic.validateRoute(stops));

            int totalTime = routeLogic.calculateTotalTravelTime(stops, "07:00");
            int expectedTime = stops.get(stops.size() - 1).getMinutesAfterDeparture();
            assertEquals(expectedTime, totalTime);
        }
    }

    @Test
    @DisplayName("Adapter dataflyt: Standardrute via portgrensesnitt med logikk")
    void testAdapterDataFlow_GetDefaultRoute_ExecuteLogic() {
        // Arrange - Tester at port-interface fungerer riktig med dependency injection
        Route route = adapter.getRoute("R20");
        ArrayList<Stop> stops = route.getStops();

        // Act
        boolean isValid = RouteLogic.validateRoute(stops);
        int totalTime = routeLogic.calculateTotalTravelTime(stops, "08:00");

        // Assert
        assertTrue(isValid);
        int expectedTime = stops.get(stops.size() - 1).getMinutesAfterDeparture();
        assertEquals(expectedTime, totalTime);
    }

    @Test
    @DisplayName("Brukerscenario: Bruker ankommer stopp 08:30 og finner neste bussavgang")
    void testUserScenario_FindNextBusArrivalTime() {
        // Arrange - Bruker ønsker å reise fra Cicignon skole til Kråkerøy terminal, ankommer 08:30
        Route route = ((EnturAdapter) adapter).getRoute("R101");
        Stop currentStop = route.getStops().get(1); // Cicignon skole - minutesAfterDeparture = 5
        Stop destination = route.getStops().get(2);  // Kråkerøy terminal - minutesAfterDeparture = 9

        String userDesiredDepartureTime = "08:30";

        // Act - Søker etter rute med brukerens ønskede avgangstid
        RouteLogic.Result result = routeLogic.searchRouteByName(
            userDesiredDepartureTime,
            currentStop.getName(),
            destination.getName(),
            route
        );

        // Assert - Verifiserer at brukeren får korrekt informasjon om neste tilgjengelige buss
        assertTrue(result.isSuccess());
        assertEquals(4, result.getTravelTime()); // 4 minutter mellom stoppene
    }
}
