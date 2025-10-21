package org.develop.Service.dto;

public class ResultDTO {
    private boolean success;
    private String transportType;
    private String routeName;
    private String message;
    private String departureFromTerminal;
    private String startLocation;
    private String endLocation;
    private String arrivalAtStartStop;
    private String arrivalAtEndStop;
    private int travelTime;

    //konstruktør for jackson og deserialisering.
    public ResultDTO(){}

    public ResultDTO(boolean success, String transportType, String routeName,
                String message, String departureFromTerminal, String startLocation,
                String endLocation, String arrivalAtStartStop, String arrivalAtEndStop,
                int travelTime){
            this.success = success;
            this.transportType = transportType;
            this.routeName = routeName;
            this.message = message;
            this.departureFromTerminal = departureFromTerminal;
            this.startLocation = startLocation;
            this.endLocation = endLocation;
            this.arrivalAtStartStop = arrivalAtStartStop;
            this.arrivalAtEndStop = arrivalAtEndStop;
            this.travelTime = travelTime;
                }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDepartureFromTerminal() {
        return departureFromTerminal;
    }

    public void setDepartureFromTerminal(String departureFromTerminal) {
        this.departureFromTerminal = departureFromTerminal;
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

    public String getArrivalAtStartStop() {
        return arrivalAtStartStop;
    }

    public void setArrivalAtStartStop(String arrivalAtStartStop) {
        this.arrivalAtStartStop = arrivalAtStartStop;
    }

    public String getArrivalAtEndStop() {
        return arrivalAtEndStop;
    }

    public void setArrivalAtEndStop(String arrivalAtEndStop) {
        this.arrivalAtEndStop = arrivalAtEndStop;
    }

    public int getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(int travelTime) {
        this.travelTime = travelTime;
    }

                
    
}
