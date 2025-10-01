package org.develop;

import java.util.ArrayList;

// Utility klasse for avanserte ruteberegninger
public class RuteLogikk {
    
    public static Stop.BussResultat finnBesteBuss(String ønsketAvreisetid, Stop startStopp, Stop sluttStopp, ArrayList<Stop> alleStops) {
        return Stop.finnNesteBuss(ønsketAvreisetid, startStopp, sluttStopp, alleStops);
    }
    
    // TODO: Legg til flere utility-metoder for ruteberegning
    public static ArrayList<Stop.BussResultat> finnAlleAlternativer(String ønsketAvreisetid, Stop startStopp, Stop sluttStopp, ArrayList<Stop> alleStops) {
        // Placeholder for fremtidig implementasjon
        ArrayList<Stop.BussResultat> alternativer = new ArrayList<>();
        alternativer.add(finnBesteBuss(ønsketAvreisetid, startStopp, sluttStopp, alleStops));
        return alternativer;
    }
    
    // Metode for å beregne total reisetid for en rute
    public static int beregnTotalReisetid(ArrayList<Stop> rute, String avgangstid) {
        if (rute == null || rute.size() < 2) {
            return 0;
        }
        
        Stop startStopp = rute.get(0);
        Stop sluttStopp = rute.get(rute.size() - 1);
        
        return startStopp.beregnReisetid(avgangstid, sluttStopp);
    }
    
    // Metode for å validere rutedata
    public static boolean validerRute(ArrayList<Stop> stops) {
        if (stops == null || stops.isEmpty()) {
            return false;
        }
        
        // Sjekk at det finnes minst ett terminalStopp
        boolean harTerminal = false;
        for (Stop stop : stops) {
            if (stop.getAvgangstider() != null && !stop.getAvgangstider().isEmpty()) {
                harTerminal = true;
                break;
            }
        }
        
        return harTerminal;
    }
}

