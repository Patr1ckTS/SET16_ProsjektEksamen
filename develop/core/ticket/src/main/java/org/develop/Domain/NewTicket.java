package org.develop.Domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NewTicket {
// Kan alternativt også være brukerId    
private LocalDateTime startTime = LocalDateTime.now();
private LocalDateTime endTime;
private int region;
private int userId;

    public NewTicket(LocalDateTime endTime, int region, int userId) {
        this.endTime = endTime;
        this.region = region;
        this.userId = userId;
    }

    public NewTicket() {
    };

// Gettere for potensiell UI presentasjon    
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

        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(currentTime, endTime);

        long days = duration.toDays();
        long hours = duration.toHours();
        long minutes = duration.toMinutes();
        long seconds = duration.toSeconds();
        return("" + days + " days, " + (hours % 24) + " hours, " + (minutes % 60) + " minutes, " + (seconds % 60) + " seconds remaining.");
    }
    
    public void getTicketInformation(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedStart = startTime.format(formatter);
        String formattedEnd = endTime.format(formatter);

        System.out.printf("""

            Ticket Information:
            Start Time: %s
            End Time: %s
            Region: %d
            User ID: %d
            
                """, formattedStart, formattedEnd, region, userId);
    }
}