package org.develop.Calendar;

import org.develop.Port.CalendarRepository;
import org.develop.Calendar.Reader.CalendarReader;
import org.develop.Calendar.Mapper.CalendarMapper;
import org.develop.Calendar.DTO.CalendarEventDTO;
import java.util.ArrayList;
import java.util.Date;

public class CalendarRepositoryAdapter implements CalendarRepository {
    
    private final CalendarReader reader;
    private final CalendarMapper mapper;
    
    public CalendarRepositoryAdapter() {
        this.reader = new CalendarReader();
        this.mapper = new CalendarMapper();
    }
    
    @Override
    public ArrayList<CalendarEvent> getAllEvents() {
        // Reader henter DTOer fra eksterne kilder
        ArrayList<CalendarEventDTO> dtos = reader.readAllEvents();
        
        // Mapper konverterer DTOer til domene-objekter
        return mapper.toDomainEvents(dtos);
    }
    
    @Override
    public ArrayList<CalendarEvent> getEventsForDate(Date date) {
        ArrayList<CalendarEventDTO> dtos = reader.readEventsForDate(date);
        return mapper.toDomainEvents(dtos);
    }
    
    @Override
    public void addEvent(CalendarEvent event) {
        // Mapper konverterer domene-objekt til DTO
        CalendarEventDTO dto = mapper.toDTO(event);
        
        // Reader skriver til ekstern kilde
        reader.writeEvent(dto);
    }
    
    @Override
    public void removeEvent(CalendarEvent event) {
        CalendarEventDTO dto = mapper.toDTO(event);
        reader.deleteEvent(dto);
    }
}