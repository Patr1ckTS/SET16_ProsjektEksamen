package org.develop.Calendar.Mapper;

import org.develop.Calendar.DTO.EventDTO;
import org.develop.Calendar.DTO.CalendarDTO;
import org.develop.Calendar.Event;
import org.develop.Calendar.Calendar;
import java.util.ArrayList;

public class CalendarMapper {

    // Konverterer fra EventDTO til Event (domene-objekt)
    public Event toDomainEvent(EventDTO dto) {
        return new Event(
            dto.getEventName(),
            dto.getStartLocation(),
            dto.getEndLocation(),
            dto.getEventDate(),
            dto.getDesiredDepartureTime(),
            dto.getEventStartTime()
        );
    }

    // Konverterer liste av EventDTOer til Event-objekter
    public ArrayList<Event> toDomainEvents(ArrayList<EventDTO> dtos) {
        ArrayList<Event> events = new ArrayList<>();
        for (EventDTO dto : dtos) {
            events.add(toDomainEvent(dto));
        }
        return events;
    }

    // Konverterer fra CalendarDTO til Calendar (domene-objekt)
    public Calendar toDomainCalendar(CalendarDTO dto) {
        ArrayList<Event> events = toDomainEvents(dto.getEvents());
        return new Calendar(
            dto.getName(),
            dto.getId(),
            events
        );
    }

    // Konverterer fra Event til EventDTO
    public EventDTO toDTO(Event event) {
        return new EventDTO(
            0, // eventId kan genereres av Reader
            event.getEventName(),
            event.getStartLocation(),
            event.getEndLocation(),
            event.getEventDate(),
            event.getDesiredDepartureTime(),
            event.getEventStartTime()
        );
    }
}
