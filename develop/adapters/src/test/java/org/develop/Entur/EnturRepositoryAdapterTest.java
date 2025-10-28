package org.develop.Entur;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import org.develop.Port.EnturRepository;
import org.develop.TravelEnteties.Route;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EnturRepositoryAdapter - Les og mappe data via port")
public class EnturRepositoryAdapterTest {

    @Test
    @DisplayName("Adapter skal implementere EnturRepository port")
    void adapterSkalImplementerePort() {
        var adapter = new EnturRepositoryAdapter();
        assertInstanceOf(EnturRepository.class, adapter);
    }

    @Test
    @DisplayName("Adapter skal lese og mappe JSON til Route objekter")
    void adapterSkalLeseOgMappeData() {
        var adapter = new EnturRepositoryAdapter();
        ArrayList<Route> ruter = adapter.getAllRoutes();

        assertNotNull(ruter);
        assertFalse(ruter.isEmpty());
        assertTrue(ruter.get(0) instanceof Route);
    }

    @Test
    @DisplayName("Ruter skal ha gyldig data etter mapping")
    void ruterSkalHaGyldigData() {
        var adapter = new EnturRepositoryAdapter();
        var ruter = adapter.getAllRoutes();

        Route rute = ruter.get(0);

        assertNotNull(rute.getRouteName());
        assertNotNull(rute.getRouteId());
        assertFalse(rute.getRouteName().isEmpty());
        assertFalse(rute.getRouteId().isEmpty());
    }

    @Test
    @DisplayName("Ruter skal ha stopp fra JSON dataen")
    void ruterSkalHaStopp() {
        var adapter = new EnturRepositoryAdapter();
        var ruter = adapter.getAllRoutes();

        Route rute = ruter.get(0);

        assertNotNull(rute.getStops());
        assertFalse(rute.getStops().isEmpty());
    }

    @Test
    @DisplayName("getRoute skal returnere en enkelt Route")
    void getRouteSkalReturnereRoute() {
        var adapter = new EnturRepositoryAdapter();
        Route rute = adapter.getRoute();

        assertNotNull(rute);
        assertInstanceOf(Route.class, rute);
        assertNotNull(rute.getRouteName());
    }

    @Test
    @DisplayName("Data skal passere gjennom port interface uten tap")
    void dataSkalPassereGjennemPort() {
        EnturRepository port = new EnturRepositoryAdapter();
        var ruter = port.getAllRoutes();

        assertNotNull(ruter);
        for (Route rute : ruter) {
            assertNotNull(rute);
            assertNotNull(rute.getRouteId());
            assertNotNull(rute.getRouteName());
            assertNotNull(rute.getStops());
        }
    }

    @Test
    @DisplayName("Adapter skal mappe alle tilgjengelige ruter")
    void adapterSkalMappAlleTilgjengeligeRuter() {
        var adapter = new EnturRepositoryAdapter();
        var ruter = adapter.getAllRoutes();

        assertTrue(ruter.size() >= 1);
    }
}
