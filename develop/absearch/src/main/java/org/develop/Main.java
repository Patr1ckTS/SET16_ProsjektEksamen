package org.develop;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    
    public static void main(String[] args) {

        ArrayList<Stop> stops = new ArrayList<>();

        stops.add(new Stop("FS001", "Fredrikstad sentrum", "Fredrikstad bussterminal", 
            new ArrayList<>(Arrays.asList("08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00"))));
        stops.add(new Stop("FS002", "Fredrikstad øst", "Cicignon skole", 3));
        stops.add(new Stop("FS003", "Fredrikstad øst", "Kråkerøy terminal", 7));
        stops.add(new Stop("FS004", "Borg kommune", "Borgehavn", 12));
        stops.add(new Stop("FS005", "Borg kommune", "Tune stasjon", 18));
        stops.add(new Stop("FS006", "Sarpsborg vest", "Grålum", 25));
        stops.add(new Stop("FS007", "Sarpsborg sentrum", "Sarpsborg bussterminal", 30));
        stops.add(new Stop("FS008", "Sarpsborg øst", "Sarpsborg sykehus", 35));
        stops.add(new Stop("FS009", "Halden", "Remmen", 90));
        
        Transport transport = new Transport("T101", 50, "Fredrikstad sentrum", "Sarpsborg sentrum", "08:00", "08:30");
        
        String avreisetid = "08:05";
        Stop startStopp = Stop.finnStoppMedNavn(stops, "Cicignon skole");
        Stop sluttStopp = Stop.finnStoppMedNavn(stops, "Remmen");

        // Legg til null-sjekk
        if (startStopp == null || sluttStopp == null) {
            System.out.println("Feil: Et eller flere stopp ble ikke funnet!");
            return;
        }

        // Bruk den nye metoden
        Stop.BussResultat resultat = Stop.finnNesteBuss(avreisetid, startStopp, sluttStopp, stops);
        System.out.println(resultat);
    }
}