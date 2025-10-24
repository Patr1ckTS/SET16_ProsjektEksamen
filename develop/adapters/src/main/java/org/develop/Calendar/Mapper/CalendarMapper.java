package org.develop.Calendar.Mapper;

import org.develop.Calendar.DTO.CalendarEventDTO;
import org.develop.Port.CalendarRepository.CalendarEvent;
import java.util.ArrayList;

public class CalendarMapper {
    
    // Konverterer fra DTO til domene-objekt
    public CalendarEvent toDomain(CalendarEventDTO dto) {
        return new CalendarEvent(
            dto.getEventName(),
            dto.getStartLocation(),
            dto.getEndLocation(),
            dto.getEventDate(),
            dto.getDesiredDepartureTime()
        );
    }
    
    // Konverterer liste av DTOer til domene-objekter
    public ArrayList<CalendarEvent> toDomainEvents(ArrayList<CalendarEventDTO> dtos) {
        ArrayList<CalendarEvent> events = new ArrayList<>();
        for (CalendarEventDTO dto : dtos) {
            events.add(toDomain(dto));
        }
        return events;
    }
    
    // Konverterer fra domene-objekt til DTO
    public CalendarEventDTO toDTO(CalendarEvent event) {
        return new CalendarEventDTO(
            0, // eventId kan genereres av Reader
            event.getEventName(),
            event.getStartLocation(),
            event.getEndLocation(),
            event.getEventDate(),
            event.getDesiredDepartureTime()
        );
    }
}