package org.develop.Calendar.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonCalendarDTO {
    private String name;
    private int id;
    private ArrayList<CalendarEventDTO> events;

    public PersonCalendarDTO() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<CalendarEventDTO> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<CalendarEventDTO> events) {
        this.events = events;
    }

    

}
