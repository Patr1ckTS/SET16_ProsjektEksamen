package org.develop.Entities.Calendar;

import java.util.ArrayList;

public class Calendar {
    private String name;
    private int id;
    private ArrayList<Event> events;

    //Json konstruktør for Jackson
    public Calendar() {}

    public Calendar(String name, int id, ArrayList<Event> events) {
        this.name = name;
        this.id = id;
        this.events = events;
    }

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

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
