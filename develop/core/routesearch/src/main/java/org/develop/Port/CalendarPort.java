package org.develop.Port;

import org.develop.Calendar.Calendar;

// Calendar Repository Port - definerer kontrakten for data-aksess til kalenderdata
public interface CalendarPort {
    
    // Henter alle events fra kalenderen
    Calendar getCalendar(String personName);

    
}

