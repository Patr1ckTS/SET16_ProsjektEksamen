package org.develop.Calendar;

import org.develop.Port.CalendarPort;
import org.develop.Calendar.Reader.CalendarReader;
import org.develop.Entities.Calendar.Calendar;
import org.develop.Calendar.Mapper.CalendarMapper;
import org.develop.Calendar.DTO.CalendarDTO;
import java.util.ArrayList;

public class GoogleCalendarAdapter implements CalendarPort {

    private final CalendarReader reader;
    private final CalendarMapper mapper;

    // Konstruktør injection for testing
    public GoogleCalendarAdapter(CalendarReader reader, CalendarMapper mapper) {
        this.reader = reader;
        this.mapper = mapper;
    }

    // Konstruktør for bruk i produksjon
    public GoogleCalendarAdapter() {
        this(new CalendarReader(), new CalendarMapper());
    }

    private Calendar mapCalendar(ArrayList<CalendarDTO> dtos, String personName) {
        if (dtos == null || dtos.isEmpty()) {
            return new Calendar(personName, 0, new ArrayList<>());
        }
        return mapper.toDomainCalendar(dtos.get(0));
    }

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
