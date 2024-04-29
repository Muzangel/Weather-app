package com.example.angel_s2110961.Domains;

public class Hourly {
    private String Hour;
    private int  temp;
    private String pic;

    public Hourly(String hour, int temp, String picPath){
        hour =hour;
        this.temp = temp;
        this.pic = picPath;
    }

    public String getHour() {
        return Hour;
    }

    public void setHour(String hour) {
        Hour = hour;
    }

    public String getPicPath() {
        return pic;
    }

    public void setPicPath(String picPath) {
        this.pic = pic;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }
}
