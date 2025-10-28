package org.develop.TravelEnteties;

// Denne kan gjøres abstrakt om vi skal ha flere transportmidler og da definere egne klasser for de
    // F.eks. Buss, Tog, T-bane, Trikk, Ferge osv.
// For MVP så holder det med en generell Transport klasse, siden fokuset ikke er på de ulike transportmidlene, men på logikken og data håndteringen
public class Transport  { 
    private String transportId;
    private String transportType;

    //Json konstruktør for Jackson
    public Transport() {}

    public Transport(String transportId, String transportType) {
        this.transportId = transportId;
        this.transportType = transportType;
    }

    
    // Getters og Setters
    public String getTransportId() {
        return transportId;
    }

    public void setTransportId(String transportId) {
        this.transportId = transportId;
    }


    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }
}