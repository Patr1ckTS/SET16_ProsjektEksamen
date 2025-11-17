package org.develop.Calendar;

import org.develop.Port.CalendarPort;
import org.develop.Calendar.Reader.CalendarReader;
import org.develop.Entities.Calendar.Calendar;
import org.develop.Calendar.Mapper.CalendarMapper;
import org.develop.Calendar.DTO.CalendarDTO;
import java.util.ArrayList;

/**
 * Adapter for Google Calendar-systemet (kalender-data for brukere).
 * Modulær oppdeling av Reader/Mapper og DTO
 *
 * Denne adapteren orkestrerer kommunikasjonen med kalender-datakilden:
 * 1. CalendarReader henter kalender-data (fra JSON/fil)
 * 2. CalendarMapper konverterer DTOer til domeneobjekter
 * 3. Adapteren presenterer et rent interface for systemet
 */
public class GoogleCalendarAdapter implements CalendarPort {

    private final CalendarReader reader;
    private final CalendarMapper mapper;

    // Konstruktør injection for testing
    public GoogleCalendarAdapter(CalendarReader reader, CalendarMapper mapper) {
        this.reader = reader;
        this.mapper = mapper;
    }

    // Default-konstruktør: Ikke i bruk per nå, men tilgjengelig for fremtidig bruk i Main-klasser
    public GoogleCalendarAdapter() {
        this(new CalendarReader(), new CalendarMapper());
    }

    // Hjelpemetode: konverter DTO til domeneobjekt (Mapper gjør transformasjonen)
    private Calendar mapCalendar(ArrayList<CalendarDTO> dtos, String personName) {
        if (dtos == null || dtos.isEmpty()) {
            return new Calendar(personName, 0, new ArrayList<>());
        }
        return mapper.toDomainCalendar(dtos.get(0));
    }

    // Hent kalender for person: Reader henter data, Mapper konverterer til domeneobjekt
    @Override
    public Calendar getCalendar(String personName) {
        ArrayList<CalendarDTO> dto = switch(personName.toLowerCase()) {
            case "ida" -> reader.getIda();
            case "ole" -> reader.getOle();
            default -> null;
        };
        return mapCalendar(dto, personName);
    }
}
