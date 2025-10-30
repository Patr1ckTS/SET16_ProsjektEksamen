package org.develop;

import static org.junit.jupiter.api.Assertions.*;

import org.develop.Calendar.Calendar;
import org.develop.Calendar.GoogleCalendarAdapter;
import org.develop.Calendar.Event;
import org.develop.Port.CalendarPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CalendarIntergration {

    private CalendarPort adapter;

    @BeforeEach
    void setUp() {
        adapter = new GoogleCalendarAdapter();
    }

    @Test
    @DisplayName("Adapter dataflyt: Hent kalendere via adapter og validér mapping")
    void testAdapterDataFlow_GetCalendarsAndValidateMapping() {
        // Arrange

        // Act
        Calendar idaCalendar = adapter.getCalendar("Ida");
        Calendar oleCalendar = adapter.getCalendar("Ole");

        // Assert - Verifiserer at JSON -> DTO -> Mapper -> Calendar fungerer korrekt
        assertNotNull(idaCalendar);
        assertEquals("Ida", idaCalendar.getName());
        assertEquals(1, idaCalendar.getId());
        assertEquals(3, idaCalendar.getEvents().size());

        assertNotNull(oleCalendar);
        assertEquals("Ole", oleCalendar.getName());
        assertEquals(2, oleCalendar.getId());
        assertEquals(3, oleCalendar.getEvents().size());

        // Tester ett event fra hver for å verifisere fullstendig mapping
        Event idaEvent = idaCalendar.getEvents().get(0);
        assertEquals("Klappe Katten", idaEvent.getEventName());
        assertEquals("Fredrikstad stasjon", idaEvent.getStartLocation());
        assertEquals("Halden stasjon", idaEvent.getEndLocation());
        assertEquals("14:00", idaEvent.getDesiredDepartureTime());
        assertEquals("14:30", idaEvent.getEventStartTime());
        assertNotNull(idaEvent.getEventDate());

        Event oleEvent = oleCalendar.getEvents().get(1);
        assertEquals("Monster med venner", oleEvent.getEventName());
        assertEquals("Halden bussterminal", oleEvent.getStartLocation());
        assertEquals("Fredrikstad bussterminal", oleEvent.getEndLocation());
        assertNotNull(oleEvent.getEventDate());
    }

    @Test
    @DisplayName("Adapter dataflyt: Validér at alle events har påkrevde felt")
    void testAdapterDataFlow_ValidateRequiredFields() {
        // Arrange

        // Act
        Calendar calendar = adapter.getCalendar("Ida");

        // Assert - Verifiserer at JSON-mapping ikke mister obligatoriske felt
        for (Event event : calendar.getEvents()) {
            assertNotNull(event.getEventName());
            assertNotNull(event.getStartLocation());
            assertNotNull(event.getEndLocation());
            assertNotNull(event.getDesiredDepartureTime());
            assertNotNull(event.getEventStartTime());
            assertNotNull(event.getEventDate());
        }
    }

    @Test
    @DisplayName("Adapter dataflyt: Test switch case med case-insensitive og error handling")
    void testAdapterDataFlow_SwitchCaseAndErrorHandling() {
        // Arrange

        // Act
        Calendar ida = adapter.getCalendar("ida");
        Calendar ole = adapter.getCalendar("OLE");
        Calendar unknown = adapter.getCalendar("UnknownPerson");

        // Assert - Verifiserer at switch case håndterer case-insensitive input
        assertNotNull(ida);
        assertEquals("Ida", ida.getName());
        assertNotNull(ole);
        assertEquals("Ole", ole.getName());

        // Verifiserer at ukjente personer ikke kaster exception, men returnerer tom kalender
        assertNotNull(unknown);
        assertEquals("UnknownPerson", unknown.getName());
        assertEquals(0, unknown.getId());
        assertTrue(unknown.getEvents().isEmpty());
    }

    @Test
    @DisplayName("Brukerscenario: Finn event for rutesøk basert på lokasjoner")
    void testUserScenario_FindEventForRouteSearch() {
        // Arrange
        Calendar calendar = adapter.getCalendar("Ida");

        // Act - Finner event som matcher spesifikke lokasjoner for rutesøk-integrasjon
        Event targetEvent = calendar.getEvents().stream()
            .filter(e -> e.getStartLocation().equals("Sarpsborg bussterminal")
                      && e.getEndLocation().equals("Kråkerøy terminal"))
            .findFirst()
            .orElse(null);

        // Assert
        assertNotNull(targetEvent);
        assertEquals("Plukke Epler", targetEvent.getEventName());
        assertEquals("14:00", targetEvent.getDesiredDepartureTime());
        assertEquals("14:30", targetEvent.getEventStartTime());
    }
}
