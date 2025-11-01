package org.develop.Service;

import java.util.Scanner;

public class TicketInputHandler {
    // Potensielt midlertidige metoder for å vise intensjon og langsiktig funksjonalitet:
// Presenterer potensielle verdier for billettvalg (Egentlig burde dette vært en del av UI fra dropdown meny el.)     
// TicketService klasse?
    public static int ticketDurationChoice(Scanner scanner){    
        System.out.print(
            """

            Hvilken billetttype ønsker du? 
            1. Engangsbillett (Varighet: 2 timer)
            2. Dagsbillett (Varighet: 24 timer)
            3. Ukesbillett (Varighet: 7 dager)
            4. Månedsbillett
            5. Årsbillett
            Svar med tallet 
            som korresponderer med ønsket billettype.
        
            """);
        
        int durationChoice = scanner.nextInt();
        
    return durationChoice;
    }

// Presenterer potensielle verdier for billettvalg (Egentlig burde dette vært en del av UI fra dropdown meny el.)     
// TicketService klasse?    
    public static int ticketRegionChoice(Scanner scanner){        
        System.out.print(
            """
            
            Hvilken region ønsker du at billetten skal gjelde for? 
            1. Kommune
            2. Fylke
            Svar med tallet som korresponderer
            med din ønskede billettype.
            
            """);
            
        int regionChoice = scanner.nextInt(); 

        return regionChoice;
    }
}
