package com.cfs.Weather_App.dto;

public class Daytamp {
         private String date;
        private double maxtemp;
        private double mintemp;
        private double avgtamp;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAvgtamp() {
        return avgtamp;
    }

    public void setAvgtamp(double avgtamp) {
        this.avgtamp = avgtamp;
    }

    public double getMintemp() {
        return mintemp;
    }

    public void setMintemp(double mintemp) {
        this.mintemp = mintemp;
    }

    public double getMaxtemp() {
        return maxtemp;
    }

    public void setMaxtemp(double maxtemp) {
        this.maxtemp = maxtemp;
    }
}