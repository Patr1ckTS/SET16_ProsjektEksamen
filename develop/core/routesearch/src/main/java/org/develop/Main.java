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

        boolean ok = StopListeSkriver.skrivStopTilFil("SET16_ProsjektEksamen\\develop\\core\\routesearch\\src\\main\\resources\\Holdeplasser1.json", stops);

        //ArrayList<Stop> stops = StopListeLeser.lesStopFraFil("SET16_ProsjektEksamen\\\\develop\\\\core\\\\routesearch\\\\src\\\\main\\\\resources\\\\Holdeplasser1.json");
       
        Route rute101 = new Route("101", "Fredrikstad-Halden", new Transport("T101", "Buss"), 100.0, stops);

        String avreisetid = "08:05";
        Stop startStopp = Stop.finnStoppMedNavn(rute101.getStops(), "Cicignon skole");
        Stop sluttStopp = Stop.finnStoppMedNavn(rute101.getStops(), "Remmen");
        
        RuteLogikk.Resultat resultat = RuteLogikk.finnBesteTransport(avreisetid, startStopp, sluttStopp, rute101);
        System.out.println(resultat);
    }
}