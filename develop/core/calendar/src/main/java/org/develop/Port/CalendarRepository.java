package org.develop.Port;

import java.util.ArrayList;
import java.util.Date;

// Calendar Repository Port - definerer kontrakten for data-aksess til kalenderdata
public interface CalendarRepository {
    
    // Henter alle events fra kalenderen
    ArrayList<CalendarRepository.CalendarEvent> getAllEvents();
    
    // Henter events for en spesifikk dato
    ArrayList<CalendarRepository.CalendarEvent> getEventsForDate(Date date);
    
    // Legger til et event i kalenderen
    void addEvent(CalendarRepository.CalendarEvent event);
    
    // Fjerner et event fra kalenderen
    void removeEvent(CalendarRepository.CalendarEvent event);
    
    // DTO for kalender-hendelser
    // Krever start og end location for vi skal unngå å regne ut dette i service laget
    class CalendarEvent {
        private String eventName;
        private String startLocation;
        private String endLocation;
        private Date eventDate;
        private String desiredDepartureTime;
        
        public CalendarEvent(String eventName, String startLocation, String endLocation, Date eventDate, String desiredDepartureTime) {
            this.eventName = eventName;
            this.startLocation = startLocation;
            this.endLocation = endLocation;
            this.eventDate = eventDate;
            this.desiredDepartureTime = desiredDepartureTime;
        }
        
        public String getEventName() { return eventName; }
        public String getStartLocation() { return startLocation; }
        public String getEndLocation() { return endLocation; }
        public Date getEventDate() { return eventDate; }
        public String getDesiredDepartureTime() { return desiredDepartureTime; }
    }
}

