package org.develop.Entur;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import org.develop.Port.EnturRepository;
import org.develop.TravelEnteties.Route;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EnturRepositoryAdapter - Read and map data via port")
public class EnturRepositoryAdapterTest {

    @Test
    @DisplayName("Adapter should implement EnturRepository port")
        void enturRepositoryAdapter_ImplementsEnturRepositoryInterface() {
        // Arrange
        var adapter = new EnturRepositoryAdapter();
        
        // Act
        EnturRepository port = adapter;
        
        // Assert
        assertInstanceOf(EnturRepository.class, port);
    }

    @Test
    @DisplayName("Adapter should read and map JSON to Route objects")
    void getAllRoutes_ReturnsMappedRouteObjectsFromJson() {
        // Arrange
        var adapter = new EnturRepositoryAdapter();
        // Act
        ArrayList<Route> routes = adapter.getAllRoutes();
        // Assert
        assertNotNull(routes);
        assertFalse(routes.isEmpty());
        assertTrue(routes.get(0) instanceof Route);
    }

    @Test
    @DisplayName("Routes should have valid data after mapping")
    void mappedRoute_HasValidRouteNameAndRouteId() {
        // Arrange
        var adapter = new EnturRepositoryAdapter();
        // Act
        var routes = adapter.getAllRoutes();
        Route route = routes.get(0);
        // Assert
        assertNotNull(route.getRouteName());
        assertNotNull(route.getRouteId());
        assertFalse(route.getRouteName().isEmpty());
        assertFalse(route.getRouteId().isEmpty());
    }

    @Test
    @DisplayName("Routes should have stops from JSON data")
    void mappedRoute_HasNonEmptyStopsList() {
        // Arrange
        var adapter = new EnturRepositoryAdapter();
        // Act
        var routes = adapter.getAllRoutes();
        Route route = routes.get(0);
        // Assert
        assertNotNull(route.getStops());
        assertFalse(route.getStops().isEmpty());
    }

    @Test
    @DisplayName("getRoute should return a single Route")
    void getRoute_WithValidId_ReturnsSingleRouteObject() {
        // Arrange
        var adapter = new EnturRepositoryAdapter();
        // Act
        Route route = adapter.getRoute("R101");
        // Assert
        assertNotNull(route);
        assertInstanceOf(Route.class, route);
        assertNotNull(route.getRouteName());
    }

    @Test
    @DisplayName("Data should pass through port interface without loss")
    void enturRepositoryPortInterface_PassesRouteDataWithoutLoss() {
        // Arrange
        EnturRepository port = new EnturRepositoryAdapter();
        // Act
        var routes = port.getAllRoutes();
        // Assert
        assertNotNull(routes);
        for (Route route : routes) {
            assertNotNull(route);
            assertNotNull(route.getRouteId());
            assertNotNull(route.getRouteName());
            assertNotNull(route.getStops());
        }
    }

    @Test
    @DisplayName("Adapter should map all available routes")
    void getAllRoutes_ReturnsAllAvailableRoutes() {
        // Arrange
        var adapter = new EnturRepositoryAdapter();
        // Act
        var routes = adapter.getAllRoutes();
        // Assert
        assertTrue(routes.size() >= 1);
    }
}
