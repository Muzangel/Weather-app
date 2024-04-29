package com.example.angel_s2110961.Domains;

import java.io.Serializable;

public class CityDomain implements Serializable {

    private String name;
    private double temperature;
    private String weatherScene;
    private double highTemperature;
    private double lowTemperature;
    private String weatherIcon;

    public CityDomain(String name, double temperature, String weatherScene, double highTemperature, double lowTemperature, String weatherIcon) {
        this.name = name;
        this.temperature = temperature;
        this.weatherScene = weatherScene;
        this.highTemperature = highTemperature;
        this.lowTemperature = lowTemperature;
        this.weatherIcon = weatherIcon;
    }

    public String getName() {
        return name;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getWeatherScene() {
        return weatherScene;
    }

    public double getHighTemperature() {
        return highTemperature;
    }

    public double getLowTemperature() {
        return lowTemperature;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }
}
