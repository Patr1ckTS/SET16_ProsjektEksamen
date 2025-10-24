package org.develop.Calendar.Reader;

import org.develop.Calendar.DTO.CalendarEventDTO;
import java.util.ArrayList;
import java.util.Date;

public class CalendarReader {
    
    // Leser alle events fra fil/database/API
    public ArrayList<CalendarEventDTO> readAllEvents() {
        // Logikk for å lese fra ekstern kilde
        // (JSON fil, database, API, etc.)
        return new ArrayList<>();
    }
    
    // Leser events for spesifikk dato
    public ArrayList<CalendarEventDTO> readEventsForDate(Date date) {
        // Filtreringslogikk
        return new ArrayList<>();
    }
    
    // Skriver event til ekstern kilde
    public void writeEvent(CalendarEventDTO dto) {
        // Logikk for å skrive til fil/database/API
    }
    
    // Sletter event fra ekstern kilde
    public void deleteEvent(CalendarEventDTO dto) {
        // Logikk for å slette
    }
}