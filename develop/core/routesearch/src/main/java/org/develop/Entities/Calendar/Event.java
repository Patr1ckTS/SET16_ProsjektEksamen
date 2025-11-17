package org.develop.Entities.Calendar;

import java.util.Date;

public class Event {
        private String eventName;
        private String startLocation;
        private String endLocation;
        private Date eventDate;
        private String desiredDepartureTime;
        private String eventStartTime;
        
        //Json konstruktør for Jackson
        public Event() {}

        public Event(String eventName, String startLocation, String endLocation, Date eventDate, String desiredDepartureTime,String eventStartTime) {
            this.eventName = eventName;
            this.startLocation = startLocation;
            this.endLocation = endLocation;
            this.eventDate = eventDate;
            this.desiredDepartureTime = desiredDepartureTime;
            this.eventStartTime = eventStartTime;
        }
        
        public String getEventStartTime() {
            return eventStartTime;
        }

        public void setEventStartTime(String eventStartTime) {
            this.eventStartTime = eventStartTime;
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
