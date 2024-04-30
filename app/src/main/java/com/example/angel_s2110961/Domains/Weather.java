package com.example.angel_s2110961.Domains;

public class Weather {
    private String cityName;
    private String temperature;
    private String description;
    private String windDirection;

    public Weather() {
    }

    // Getter and Setter for cityName
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "cityName='" + cityName + '\'' +
                ", temperature='" + temperature + '\'' +
                ", description='" + description + '\'' +
                ", windDirection='" + windDirection + '\'' +
                '}';
    }
}