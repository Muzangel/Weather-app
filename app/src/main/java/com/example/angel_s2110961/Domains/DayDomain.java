package com.example.angel_s2110961.Domains;

public class DayDomain {
    private String cityName;
    private String date;
    private String temperature;
    private String description;
    private String windSpeed;
    private String pressure;
    private String visibility;
    private String humidity;
    private String uvRisk;
    private String sunset;
    private String windDirection;

    public DayDomain(String cityName, String date, String temperature, String description, String windSpeed,
                     String pressure, String visibility, String humidity) {
        this.cityName = cityName;
        this.date = date;
        this.temperature = temperature;
        this.description = description;
        this.windSpeed = windSpeed;
        this.pressure = pressure;
        this.visibility = visibility;
        this.humidity = humidity;
        this.uvRisk = uvRisk;
        this.sunset = getSunset();
        this.windDirection = windDirection;
    }

    public String getCityName() {
        return cityName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getUvRisk() {
        return uvRisk;
    }

    public void setUvRisk(String uvRisk) {
        this.uvRisk = uvRisk;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindSpeed() {

        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }
}
