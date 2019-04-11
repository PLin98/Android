package com.example.myapplication;

import java.util.List;

public class weather {
    private String city;
    private String updateTime;
    private String weatherdate;
    private String shidu;
    private String pm25;
    private String quality;
    private String wendu;
    private String ganmao;
    private List<forecast> forecastList;

    public weather(String city, String updateTime, String weatherdate, String shidu, String pm25, String quality, String wendu, String ganmao, List<forecast> forecastList) {
        this.city = city;
        this.updateTime = updateTime;
        this.weatherdate = weatherdate;
        this.shidu = shidu;
        this.pm25 = pm25;
        this.quality = quality;
        this.wendu = wendu;
        this.ganmao = ganmao;
        this.forecastList = forecastList;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getWeatherdate() {
        return weatherdate;
    }

    public void setWeatherdate(String weatherdate) {
        this.weatherdate = weatherdate;
    }

    public String getShidu() {
        return shidu;
    }

    public void setShidu(String shidu) {
        this.shidu = shidu;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public String getGanmao() {
        return ganmao;
    }

    public void setGanmao(String ganmao) {
        this.ganmao = ganmao;
    }

    public List<forecast> getForecastList() {
        return forecastList;
    }

    public void setForecastList(List<forecast> forecastList) {
        this.forecastList = forecastList;
    }
}
