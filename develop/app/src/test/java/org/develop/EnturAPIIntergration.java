package org.develop;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.develop.Entur.EnturRepositoryAdapter;
import org.develop.Port.EnturRepository;
import org.develop.Service.RouteLogic;
import org.develop.Service.StopLogic;
import org.develop.TravelEnteties.Route;
import org.develop.TravelEnteties.Stop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EnturAPIIntergration {

    private EnturRepository adapter;
    private RouteLogic routeLogic;
    private StopLogic stopLogic;

    @BeforeEach
    void setUp() {
        adapter = new EnturRepositoryAdapter();
        stopLogic = new StopLogic();
        routeLogic = new RouteLogic(stopLogic);
    }

    @Test
    @DisplayName("Adapter dataflyt: Rute101 søk mellom to stopp med konkret beregning")
    void testAdapterDataFlow_Rute101_SearchFromTerminalToFirstStop() {
        // Arrange - Hent rute via adapteret (JSON -> DTO -> Mapper -> Route)
        // JSON: Rute101_Stops_Fredrikstad_Sarpsborg.json
        Route route = ((EnturRepositoryAdapter) adapter).getRoute("R101");
        Stop stop1 = route.getStops().get(1);  // "Cicignon skole"
        Stop stop2 = route.getStops().get(2);  // "Kråkerøy terminal"

        // Act - Kjør domenelogikk på data fra adapteret
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
        // Arrange - Hent rute203 via getAllRoutes() (Rute203 er på indeks 3)
        // JSON: Rute203_Stops_Fredrikstad_Halden.json
        Route route = adapter.getAllRoutes().get(3);
        Stop stop1 = route.getStops().get(1);  // "Grålum" - Minutter etter avgang: 12
        Stop stop2 = route.getStops().get(2);  // "Sarpsborg bussterminal" - Minutter etter avgang: 20

        // Act - Kjør domenelogikk på data fra adapteret
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
        // Arrange - Hent alle ruter via adapteret
        ArrayList<Route> allRoutes = adapter.getAllRoutes();

        // Act & Assert - Gå gjennom alle ruter fra adapteret
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
        // Arrange - Hent standardrute via adapteret portgrensesnitt
        Route route = adapter.getRoute("R20");
        ArrayList<Stop> stops = route.getStops();

        // Act - Kjør validering og reiseberegning
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
        // Arrange - Bruker ankommer Cicignon skole kl 08:30 og vil til Kråkerøy terminal
        // JSON: Rute101_Stops_Fredrikstad_Sarpsborg.json
        Route route = ((EnturRepositoryAdapter) adapter).getRoute("R101");
        Stop currentStop = route.getStops().get(1);       // "Cicignon skole" - minutesAfterDeparture: 3
        Stop destination = route.getStops().get(2);       // "Kråkerøy terminal" - minutesAfterDeparture: 7

        String userArrivalAtCurrentStop = "08:30";

        String nextDeparture = stopLogic.findNextDepartureTime(currentStop, userArrivalAtCurrentStop);

        String arrivalAtCurrentStop = stopLogic.calculateTransportAtStop(currentStop, nextDeparture);
        String arrivalAtDestination = stopLogic.calculateTransportAtStop(destination, nextDeparture);
        int travelTimeBetweenStops = stopLogic.calculateTravelTime(currentStop, nextDeparture, destination);
        

        // Act
        RouteLogic.Result result = routeLogic.searchRouteByName(
            nextDeparture,
            currentStop.getName(),
            destination.getName(),
            route
        );

        // Assert 
        assertTrue(result.isSuccess());
        assertEquals("09:00", nextDeparture);             
        assertEquals("09:03", arrivalAtCurrentStop);         
        assertEquals("09:07", arrivalAtDestination);      
        assertEquals(4, travelTimeBetweenStops);                         
    }
}
