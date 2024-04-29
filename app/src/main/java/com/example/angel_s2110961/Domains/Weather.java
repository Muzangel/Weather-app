package com.example.angel_s2110961.Domains;

public class Weather {
        private String cityName;
        private String date;
        private String temperature;
        private String minTemp;
        private String maxTemp;

    public Weather() {
    }

    // Getter and Setter for cityName
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String title) {
        this.date = title;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String description) {
        this.temperature = description;
    }


   /* public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }*/

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "cityName='" + cityName + '\'' +
                ", date='" + date + '\'' +
                ", temperature='" + temperature + '\'' +
                ", minTemp='" + minTemp + '\'' +
                ", maxTemp='" + maxTemp + '\'' +
                '}';
    }

    public void setPubDate(String pubDate) {
    }
}