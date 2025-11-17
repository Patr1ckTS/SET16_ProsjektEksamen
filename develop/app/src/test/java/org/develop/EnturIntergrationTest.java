package org.develop;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.develop.Entities.Route;
import org.develop.Entities.Stop;
import org.develop.Entur.EnturAdapter;
import org.develop.Entur.Reader.EnturRouteReader;
import org.develop.Entur.Mapper.EnturRouteMapper;
import org.develop.Entur.DTO.EnturRouteDTO;
import org.develop.Port.EnturPort;
import org.develop.Service.RouteLogic;
import org.develop.Service.StopLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Integrasjonstest for Entur-adapter i semi-heksagonal arkitektur.
 * Tester at adapter, domain-objekter og domenelogikken/kjernen kommuniserer
 * korrekt.
 * Bruker stubs for kontrollerte testdata.
 */
@DisplayName("Entur Integrasjonstest - Dataflyt JSON -> Domain -> Logikk")
public class EnturIntergrationTest {

    private EnturPort adapter;
    private RouteLogic routeLogic;
    private StopLogic stopLogic;

    @BeforeEach
    void setUp() {
        // Stub I/O for kontrollerte testdata
        EnturRouteReader stubReader = new StubEnturRouteReader();
        EnturRouteMapper mapper = new EnturRouteMapper();
        adapter = new EnturAdapter(stubReader, mapper);

        stopLogic = new StopLogic();
        routeLogic = new RouteLogic(stopLogic);
    }

    // Stub som returnerer kontrollerte rutedata
    private static class StubEnturRouteReader extends EnturRouteReader {
        @Override
        public EnturRouteDTO getRute101() {
            EnturRouteDTO route = new EnturRouteDTO();
            route.setRouteName("TestRoute");
            route.setRouteId("R101");
            route.setPrice(50.0);

            EnturRouteDTO.TransportData transport = new EnturRouteDTO.TransportData();
            transport.setTransportType("Buss");
            transport.setTransportId("TestBuss");
            route.setTransport(transport);

            ArrayList<EnturRouteDTO.StopData> stops = new ArrayList<>();

            // Terminal stop med avganger (nødvendig for rutesøk)
            EnturRouteDTO.StopData stop1 = new EnturRouteDTO.StopData();
            stop1.setName("Stop A");
            stop1.setMinutesAfterDeparture(0);
            ArrayList<String> departureTimes = new ArrayList<>();
            departureTimes.add("08:00");
            departureTimes.add("09:00");
            departureTimes.add("10:00");
            stop1.setDepartureTimes(departureTimes);
            stops.add(stop1);

            EnturRouteDTO.StopData stop2 = new EnturRouteDTO.StopData();
            stop2.setName("Stop B");
            stop2.setMinutesAfterDeparture(5);
            stops.add(stop2);

            EnturRouteDTO.StopData stop3 = new EnturRouteDTO.StopData();
            stop3.setName("Stop C");
            stop3.setMinutesAfterDeparture(9);
            stops.add(stop3);

            route.setStops(stops);
            return route;
        }
    }

    /**
     * Test som validerer at DTO-data transformeres korrekt til domain-objekter.
     * Tester hele flyten: Stub Reader → DTO → Mapper → Domain
     * Dette sikrer at mapping-logikken fungerer før data brukes i
     * applikasjonslogikken.
     */
    @Test
    @DisplayName("JSON transformeres korrekt til domain-objekter")
    void testDataTransformation() {
        // Arrange & Act
        Route route = adapter.getRoute("R101");
        ArrayList<Stop> stops = route.getStops();

        // Assert
        assertAll(
                () -> assertEquals("TestRoute", route.getRouteName()),
                () -> assertEquals("R101", route.getRouteId()),
                () -> assertEquals(3, stops.size()),
                () -> assertEquals("Stop A", stops.get(0).getName()),
                () -> assertEquals(0, stops.get(0).getMinutesAfterDeparture()),
                () -> assertEquals("Stop C", stops.get(2).getName()),
                () -> assertEquals(9, stops.get(2).getMinutesAfterDeparture()));
    }

    /**
     * Test av integrasjon mellom Adapter -> Domain -> RouteLogic.
     * Verifiserer at rutevalidering og reisetidsberegning fungerer med
     * domain-objekter fra adapteren.
     */
    @Test
    @DisplayName("Rutevalidering fungerer korrekt")
    void testRouteValidation() {
        // Arrange
        Route route = adapter.getRoute("R101");
        ArrayList<Stop> stops = route.getStops();

        // Act & Assert
        assertTrue(RouteLogic.validateRoute(stops));

        int totalTime = routeLogic.calculateTotalTravelTime(stops, "08:00");
        int expectedTime = stops.get(stops.size() - 1).getMinutesAfterDeparture();
        assertEquals(expectedTime, totalTime);
    }

    /**
     * Hovedtest for integrasjon: Entur-data -> Rutesøk -> Resultat.
     * Tester at adapter, domain-objekter og rutesøk-logikk fungerer sammen
     * end-to-end.
     * Verifiserer at rutesøk finner korrekt reisetid mellom to stopp basert på
     * rute-data.
     */
    @Test
    @DisplayName("Søk mellom to stopp beregner korrekt reisetid")
    void testRouteSearch() {
        // Arrange
        Route route = adapter.getRoute("R101");
        Stop startStop = route.getStops().get(1); // Stop B
        Stop endStop = route.getStops().get(2); // Stop C

        // Act
        RouteLogic.Result result = routeLogic.searchRouteByName(
                "08:00",
                startStop.getName(),
                endStop.getName(),
                route);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals(4, result.getTravelTime());
    }

}