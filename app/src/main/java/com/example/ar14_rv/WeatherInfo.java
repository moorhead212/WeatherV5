package com.example.ar14_rv;

public class WeatherInfo {

    private String city;
    private String currentCondition;
    private String forecast1;
    private String forecast2;
    private String forecast3;
    private double high1;
    private double high2;
    private double high3;
    private double low1;
    private double low2;
    private double low3;
    private double currentTemp;
    private double lowTemp;
    private double highTemp;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCurrentCondition() {
        return currentCondition;
    }

    public void setCurrentCondition(String currentCondition) {
        this.currentCondition = currentCondition;
    }

    public double getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(double currentTemp) {
        this.currentTemp = currentTemp;
    }

    public double getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(double lowTemp) {
        this.lowTemp = lowTemp;
    }

    public double getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(double highTemp) {
        this.highTemp = highTemp;
    }


    public String getForecast1() {
        return forecast1;
    }

    public String getForecast2() {
        return forecast2;
    }

    public String getForecast3() {
        return forecast3;
    }

    public void setForecast1(String forecast1) {
        this.forecast1 = forecast1;
    }

    public void setForecast2(String forecast2) {
        this.forecast2 = forecast2;
    }

    public void setForecast3(String forecast3) {
        this.forecast3 = forecast3;
    }

    public double getHigh1() { return high1; }

    public double getHigh2() { return high2; }

    public double getHigh3() { return high3; }

    public void setHigh1(double high1) {this.high1 = high1;}

    public void setHigh2(double high2) {this.high2 = high2;}

    public void setHigh3(double high3) {this.high3 = high3;}

    public double getLow1() { return low1; }

    public double getLow2() { return low2; }

    public double getLow3() { return low3; }

    public void setLow1(double low1) {this.low1 = low1;}

    public void setLow2(double low2) {this.low2 = low2;}

    public void setLow3(double low3) {this.low3 = low3;}


}