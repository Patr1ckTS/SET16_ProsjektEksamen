package org.develop.Domain;

import java.time.Duration;
import java.time.LocalDateTime;

public class Ticket {
// Kan alternativt også være brukerId    
    private final int ticketId;
    private final LocalDateTime startTime;
    private LocalDateTime endTime;
    private  int region;
    private int userId;
    Boolean isActive  = true;

    public Ticket(int ticketId, LocalDateTime startTime, LocalDateTime endTime, int region, int userId) {
        this.ticketId = ticketId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.region = region;
        this.userId = userId;
    }

// Gettere for potensiell UI presentasjon    
    public int getTicketId() {
        return ticketId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public int getRegion() {
        return region;
    }

    public int getUserId() {
        return userId;
    }

    public int getRemainingDays(){
        Duration duration = Duration.between(LocalDateTime.now(), endTime);
        return (int) duration.toDays();
    }

    public int getRemainingHours(){
        Duration duration = Duration.between(LocalDateTime.now(), endTime);
        return (int) duration.toHours();
    }

    public int getRemainingMinutes(){
        Duration duration = Duration.between(LocalDateTime.now(), endTime);
        return (int) duration.toMinutes();
    }

    public int getRemainingSeconds(){
        Duration duration = Duration.between(LocalDateTime.now(), endTime);
        return (int) duration.toSeconds();
    }

    public String timeRemaining(){
        if(LocalDateTime.now().isAfter(endTime)){
            isActive = false;
            return "Ticket expired.";
        }   

        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(currentTime, endTime);

        long days = duration.toDays();
        long hours = duration.toHours();
        long minutes = duration.toMinutes();
        long seconds = duration.toSeconds();
        return("" + days + " days, " + (hours % 24) + " hours, " + (minutes % 60) + " minutes, " + (seconds % 60) + " seconds remaining.");
    }
}