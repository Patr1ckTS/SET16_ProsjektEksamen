package org.develop.Calendar;

import org.develop.Port.CalendarRepository;
import org.develop.Calendar.Reader.CalendarReader;
import org.develop.Calendar.Mapper.CalendarMapper;
import org.develop.Calendar.DTO.CalendarDTO;
import java.util.ArrayList;

public class CalendarRepositoryAdapter implements CalendarRepository {

    private final CalendarReader reader;
    private final CalendarMapper mapper;

    public CalendarRepositoryAdapter() {
        this.reader = new CalendarReader();
        this.mapper = new CalendarMapper();
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
