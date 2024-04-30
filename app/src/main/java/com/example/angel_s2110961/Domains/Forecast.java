package com.example.angel_s2110961.Domains;

public class Forecast {
    private String cityName;
    private String date;
    private String status;
    private String temperature;
    private String minTemperature;
    private String maxTemperature;
    private String windSpeed;
    private String humidity;
    private String pressure;
    private String visibility;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(String maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public String getMinTemperature() {
        return minTemperature;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public void setMinTemperature(String minTemperature) {
        this.minTemperature = minTemperature;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "cityName='" + cityName + '\'' +
                ", date='" + date + '\'' +
                ", status='" + status + '\'' +
                ", temperature='" + temperature + '\'' +
                ", minTemperature='" + minTemperature + '\'' +
                ", maxTemperature='" + maxTemperature + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                ", humidity='" + humidity + '\'' +
                ", pressure='" + pressure + '\'' +
                ", visibility='" + visibility + '\'' +
                '}';
    }
}