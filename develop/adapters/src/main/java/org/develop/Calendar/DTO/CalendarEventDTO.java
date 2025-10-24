package org.develop.Calendar.DTO;

import java.util.Date;

public class CalendarEventDTO {
    private int eventId;
    private String eventName;
    private String startLocation;
    private String endLocation;
    private Date eventDate;
    private String desiredDepartureTime;

    // Tom konstruktør for Jackson deserialisering
    public CalendarEventDTO() {}

    public CalendarEventDTO(int eventId, String eventName, String startLocation, String endLocation, Date eventDate, String desiredDepartureTime) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.eventDate = eventDate;
        this.desiredDepartureTime = desiredDepartureTime;
        }
        
        public int getEventId() {
            return eventId;
        }
        
        public void setEventId(int eventId) {
            this.eventId = eventId;
        }

        public String getEventName() {
            return eventName;
        }

        public void setEventName(String eventName) {
            this.eventName = eventName;
        }

        public String getStartLocation() {
            return startLocation;
        }

        public void setStartLocation(String startLocation) {
            this.startLocation = startLocation;
        }

        public String getEndLocation() {
            return endLocation;
        }

        public void setEndLocation(String endLocation) {
            this.endLocation = endLocation;
        }

        public Date getEventDate() {
            return eventDate;
        }

        public void setEventDate(Date eventDate) {
            this.eventDate = eventDate;
        }

        public String getDesiredDepartureTime() {
            return desiredDepartureTime;
        }

        public void setDesiredDepartureTime(String desiredDepartureTime) {
            this.desiredDepartureTime = desiredDepartureTime;
        }

        
}
