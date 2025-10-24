package org.develop.Calendar;

import org.develop.Port.CalendarRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Calendar Repository Implementation - implementerer CalendarRepository
 * 
 * Heksagonal arkitektur adapter som håndterer data-aksess til kalenderdata.
 * Lagrer events i minne og gjør dem tilgjengelig gjennom CalendarRepository interfacet.
 */
public class CalendarDataAdapter implements CalendarRepository {
    
    // Liste med alle events
    private final ArrayList<CalendarRepository.CalendarEvent> events;
    
    // Konstruktor initialiserer tom event-liste
    public CalendarDataAdapter() {
        this.events = new ArrayList<>();
    }
    
    // Returnerer en kopi av alle events
    @Override
    public ArrayList<CalendarRepository.CalendarEvent> getAllEvents() {
        return new ArrayList<>(events);
    }
    
    // Returnerer events for en spesifikk dato
    @Override
    public ArrayList<CalendarRepository.CalendarEvent> getEventsForDate(Date date) {
        return events.stream()
                .filter(event -> isSameDay(event.getEventDate(), date))
                .collect(Collectors.toCollection(ArrayList::new));
    }
    
    // Legger til et event i kalenderen
    @Override
    public void addEvent(CalendarRepository.CalendarEvent event) {
        events.add(event);
    }
    
    // Fjerner et event fra kalenderen
    @Override
    public void removeEvent(CalendarRepository.CalendarEvent event) {
        events.remove(event);
    }
    
    // Hjelpemetode for å sammenligne datoer
    private boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) return false;
        
        java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("yyyyMMdd");
        return fmt.format(date1).equals(fmt.format(date2));
    }
}
