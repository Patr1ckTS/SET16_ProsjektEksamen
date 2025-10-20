package com.database;

public class WeatherController {

    Boolean isWeatherGood = false;

    public String checkWeather() {
        if(isWeatherGood) {
            return "Været er bra for reise!";
        } else {
            return "Været er dårlig, vær forsiktig!";
        }
    }

}
