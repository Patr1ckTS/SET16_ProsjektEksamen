package org.develop.Calendar.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CalendarDTO {
    private String name;
    private int id;
    private ArrayList<EventDTO> events;

    public CalendarDTO() {}

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

    public ArrayList<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<EventDTO> events) {
        this.events = events;
    }

    

}
