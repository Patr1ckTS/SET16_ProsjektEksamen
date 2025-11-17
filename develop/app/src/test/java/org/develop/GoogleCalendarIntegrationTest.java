package org.develop;

import static org.junit.jupiter.api.Assertions.*;

import org.develop.Calendar.GoogleCalendarAdapter;
import org.develop.Port.CalendarPort;
import org.develop.Port.EnturPort;
import org.develop.Calendar.Reader.CalendarReader;
import org.develop.Entities.Calendar.Calendar;
import org.develop.Entities.Calendar.Event;
import org.develop.Calendar.Mapper.CalendarMapper;
import org.develop.Calendar.DTO.CalendarDTO;
import org.develop.Calendar.DTO.EventDTO;
import org.develop.Service.RouteLogic;
import org.develop.Service.StopLogic;
import org.develop.Entur.EnturAdapter;
import org.develop.Entur.Reader.EnturRouteReader;
import org.develop.Entur.Mapper.EnturRouteMapper;
import org.develop.Entur.DTO.EnturRouteDTO;
import org.develop.Entities.Route;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

/**
 * Integrasjonstest for Google Calendar-adapter i semi-heksagonal arkitektur.
 * Tester at kalenderdata kan brukes som input til rutesøk (end-to-end
 * dataflyt).
 * Inkluderer både positive og negative testscenarioer.
 */
@DisplayName("Google Calendar Integrasjonstest - Dataflyt JSON -> Domain -> App")
class GoogleCalendarIntegrationTest {

    private CalendarPort calendarAdapter;
    private EnturPort enturAdapter;
    private RouteLogic routeLogic;

    @BeforeEach
    void setUp() {
        CalendarReader calendarReader = new StubCalendarReader();
        CalendarMapper calendarMapper = new CalendarMapper();
        calendarAdapter = new GoogleCalendarAdapter(calendarReader, calendarMapper);

        EnturRouteReader enturReader = new StubEnturRouteReader();
        EnturRouteMapper enturMapper = new EnturRouteMapper();
        enturAdapter = new EnturAdapter(enturReader, enturMapper);

        StopLogic stopLogic = new StopLogic();
        routeLogic = new RouteLogic(stopLogic);
    }

    // Stub som returnerer kontrollerte kalenderdata
    private static class StubCalendarReader extends CalendarReader {
        @Override
        public ArrayList<CalendarDTO> getIda() {
            ArrayList<CalendarDTO> testData = new ArrayList<>();
            CalendarDTO calendar = new CalendarDTO();
            calendar.setName("Ida");
            calendar.setId(1);

            ArrayList<EventDTO> events = new ArrayList<>();
            EventDTO event = new EventDTO();
            event.setEventName("Møte i Halden");
            event.setEventDate(Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            event.setDesiredDepartureTime("14:00");
            event.setEventStartTime("16:00");
            event.setStartLocation("Fredrikstad bussterminal");
            event.setEndLocation("Sarpsborg bussterminal");
            events.add(event);

            calendar.setEvents(events);
            testData.add(calendar);
            return testData;
        }
    }

    // Stub som returnerer kontrollerte rutedata
    private static class StubEnturRouteReader extends EnturRouteReader {
        @Override
        public EnturRouteDTO getRute203() {
            EnturRouteDTO route = new EnturRouteDTO();
            route.setRouteName("Rute 203");
            route.setRouteId("R203");
            route.setPrice(120.0);

            EnturRouteDTO.TransportData transport = new EnturRouteDTO.TransportData();
            transport.setTransportType("Buss");
            transport.setTransportId("Buss203");
            route.setTransport(transport);

            ArrayList<EnturRouteDTO.StopData> stops = new ArrayList<>();

            EnturRouteDTO.StopData terminal = new EnturRouteDTO.StopData();
            terminal.setName("Fredrikstad bussterminal");
            terminal.setMinutesAfterDeparture(0);
            ArrayList<String> departureTimes = new ArrayList<>();
            departureTimes.add("13:00");
            departureTimes.add("14:00");
            departureTimes.add("15:00");
            terminal.setDepartureTimes(departureTimes);
            stops.add(terminal);

            EnturRouteDTO.StopData stop1 = new EnturRouteDTO.StopData();
            stop1.setName("Grålum");
            stop1.setMinutesAfterDeparture(12);
            stops.add(stop1);

            EnturRouteDTO.StopData destination = new EnturRouteDTO.StopData();
            destination.setName("Sarpsborg bussterminal");
            destination.setMinutesAfterDeparture(20);
            stops.add(destination);

            route.setStops(stops);
            return route;
        }
    }

    // En såkalt "No match" test for å sikre at rutesøk feiler med ugyldig lokasjon
    @Test
    @DisplayName("Rutesøk feiler når kalender-event har ugyldig lokasjon")
    void testNoMatchWhenInvalidLocation() {
        // Arrange
        Calendar calendar = calendarAdapter.getCalendar("Ida");
        Event event = calendar.getEvents().get(0);
        Route route = enturAdapter.getRoute("R203");

        // Act - Søk med ugyldig destinasjon som ikke finnes i ruten
        RouteLogic.Result result = routeLogic.searchRouteByName(
                event.getDesiredDepartureTime(),
                event.getStartLocation(),
                "Ugyldig lokasjon",
                route);

        // Assert
        assertFalse(result.isSuccess(), "Rutesøk skal feile når destinasjon ikke finnes");
    }

    // Denne testen er selve integrasjonstesten som sjekker hele flyten fra kalender
    // til rutesøk og resultat
    @Test
    @DisplayName("Kalender-event data brukes gjennom hele reisen: JSON -> Domain -> Rutesøk -> Resultat")
    void testFullIntegrationFromCalendarToRouteSearch() {
        // Arrange - Hent kalenderdata (JSON -> DTO -> Domain)
        Calendar calendar = calendarAdapter.getCalendar("Ida");
        Event event = calendar.getEvents().get(0);
        Route route = enturAdapter.getRoute("R203");

        // Act - Bruk event-data som input til rutesøk (hele dataflyten)
        RouteLogic.Result result = routeLogic.searchRouteByName(
                event.getDesiredDepartureTime(),
                event.getStartLocation(),
                event.getEndLocation(),
                route);

        // Assert
        assertAll(
                () -> assertTrue(result.isSuccess(), "Rutesøk skal lykkes med gyldige kalenderdata"),
                () -> assertEquals(20, result.getTravelTime(), "Reisetid skal beregnes korrekt"),
                () -> assertEquals("14:00", result.getDepartureFromTerminal(),
                        "Avgang skal matche ønsket tid fra event"));
    }

}