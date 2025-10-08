package org.develop;

import java.util.ArrayList;

// Utility klasse for avanserte ruteberegninger
public class RuteLogikk {

    // Ny overloaded metode som tar imot Route-objektet
    public static RuteLogikk.Resultat finnBesteTransport(String ønsketAvreisetid, Stop startStopp, Stop sluttStopp, Route rute) {
        return finnNesteTransport(ønsketAvreisetid, startStopp, sluttStopp, rute.getStops(), rute);
    }

    // Gammel metode for bakoverkompatibilitet
    public static RuteLogikk.Resultat finnBesteTransport(String ønsketAvreisetid, Stop startStopp, Stop sluttStopp, ArrayList<Stop> alleStops) {
        return finnNesteTransport(ønsketAvreisetid, startStopp, sluttStopp, alleStops, null);
    }
    
    // Hovedmetode for å finne neste transport mellom to stopp - nå med optional Route parameter
    private static RuteLogikk.Resultat finnNesteTransport(String ønsketAvreisetid, Stop startStopp, Stop sluttStopp, ArrayList<Stop> alleStops, Route rute) {
        // Finn terminal (første stopp med avgangstid)
        Stop startPunkt = null;
        for (Stop stop : alleStops) {
            if (stop.getAvgangstider() != null && !stop.getAvgangstider().isEmpty()) {
                startPunkt = stop;
                break;
            }
        }

        if (startPunkt == null) {
            return new RuteLogikk.Resultat(false, "Ingen terminal funnet", null, null, null, null, null, 0);
        }
        
        // Finn neste avgangstid fra startPunkt
        String nesteAvgang = startPunkt.finnNesteAvgangstid(ønsketAvreisetid);

        if (nesteAvgang == null) {
            return new RuteLogikk.Resultat(false, "Ingen passende transport funnet", null, null, null, null, null, 0);
        }
        
        // Beregn ankomsttider
        String ankomstStartStopp = startStopp.beregnTransportPåStopp(nesteAvgang);
        String ankomstSluttStopp = sluttStopp.beregnTransportPåStopp(nesteAvgang);

        // Beregn reisetid
        int reisetid = startStopp.beregnReisetid(nesteAvgang, sluttStopp);
        
        // Hent transport info fra Route hvis tilgjengelig, ellers bruk default verdier
        String transportType = (rute != null && rute.getTransport() != null) 
            ? rute.getTransport().getTransportType()
            : "Ukjent transport";
        String ruteNavn = (rute != null) 
            ? rute.getRouteName() 
            : "Ukjent rute";
        
        return new RuteLogikk.Resultat(true, transportType, ruteNavn, "Transport funnet", 
            nesteAvgang, startStopp.getName(), sluttStopp.getName(), 
            ankomstStartStopp, ankomstSluttStopp, reisetid);
    }
    
    // TODO: Legg til flere utility-metoder for ruteberegning
    public static ArrayList<RuteLogikk.Resultat> finnAlleAlternativer(String ønsketAvreisetid, Stop startStopp, Stop sluttStopp, ArrayList<Stop> alleStops) {
        // Placeholder for fremtidig implementasjon
        ArrayList<RuteLogikk.Resultat> alternativer = new ArrayList<>();
        alternativer.add(finnBesteTransport(ønsketAvreisetid, startStopp, sluttStopp, alleStops));
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



    // Klasse for å returnere transportinformasjon
    public static class Resultat {
        private boolean success;
        private String transportType;
        private String ruteNavn;
        private String melding;
        private String avgangFraTerminal;
        private String startLocation;
        private String endLocation;
        private String ankomstStartStopp;
        private String ankomstSluttStopp;
        private int reisetid;

        public Resultat(boolean success, String transportType, String ruteNavn,
                        String melding, String avgangFraTerminal, String startLocation, 
                        String endLocation, String ankomstStartStopp, String ankomstSluttStopp, 
                        int reisetid) {
            this.success = success;
            this.transportType = transportType;
            this.ruteNavn = ruteNavn;
            this.melding = melding;
            this.avgangFraTerminal = avgangFraTerminal;
            this.startLocation = startLocation;
            this.endLocation = endLocation;
            this.ankomstStartStopp = ankomstStartStopp;
            this.ankomstSluttStopp = ankomstSluttStopp;
            this.reisetid = reisetid;
        }

        public Resultat(boolean success, String melding, String ruteId, String ruteNavn,
                        String avgangFraTerminal, String ankomstStartStopp, String ankomstSluttStopp, 
                        int reisetid) {
            this.success = success;
            this.melding = melding;
            this.ruteNavn = ruteNavn;
            this.avgangFraTerminal = avgangFraTerminal;
            this.ankomstStartStopp = ankomstStartStopp;
            this.ankomstSluttStopp = ankomstSluttStopp;
            this.reisetid = reisetid;
        }
        
        // Getters
        public boolean isSuccess() {
            return success;
        }
        
        public String getAvgangFraTerminal() {
            return avgangFraTerminal;
        }
        
        public int getReisetid() {
            return reisetid;
        }

        @Override
        public String toString() { 
            if (!success) {
                return String.format("\n=== Ingen Transport Funnet ===\n%s\n============================\n", melding);
            }
            
            try {
                return String.format(
                    "\n=== Transport Informasjon ===\n" +
                    "Type: %s\n" +
                    "Rute Navn: %s\n" +
                    "Fra: %s - Avgangstid: %s\n" +
                    "Til: %s - Ankomsttid: %s\n" +
                    "Reisetid: %d minutter\n" +
                    "============================\n",
                    transportType,
                    ruteNavn,
                    startLocation, ankomstStartStopp,
                    endLocation, ankomstSluttStopp,
                    reisetid
                );
            } catch (Exception e) {
                return String.format("\n Feil: %s\n", e.getMessage());
            }
        }
    }

}

