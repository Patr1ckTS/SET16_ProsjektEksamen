package org.develop.TicketComponent.Service;

import java.time.LocalDateTime;

public class TicketService {
/*
    Denne definerer tall-verdier knyttet til de ulike billettypene
    som leses av senere for å opprette riktig varighet på billetten.
    Dette sikter til eventuell databaseimplementasjon.
*/    
    public LocalDateTime ticketDuration(int durationChoice){
        LocalDateTime presentTime = LocalDateTime.now();
        LocalDateTime endTime;

        switch (durationChoice){
            case 1 -> endTime = presentTime.plusHours(2);
            case 2 -> endTime = presentTime.plusDays(1);
            case 3 -> endTime = presentTime.plusDays(7);
            case 4 -> endTime = presentTime.plusDays(30);
            case 5 -> endTime = presentTime.plusDays(365);
            default -> throw new IllegalArgumentException("Ugyldig billettype valgt.");
        }
        return endTime;
    }

/*
    Denne definerer hva tall-verdiene knyttes til.
    Dette sikter til eventuell databaseimplementasjon.
*/
    public String ticketRegion(int regionChoice){    
        String region; 

        switch (regionChoice) {
            case 1 -> region = "By";
            case 2 -> region = "Fylke";
            case 3 -> region = "Landsdel";
            case 4 -> region = "Nasjonalt";
            default -> throw new IllegalArgumentException("Ugyldig region valgt.");
        }
        return region;
    }
}
