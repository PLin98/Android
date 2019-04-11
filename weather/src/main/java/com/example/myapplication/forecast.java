package com.example.myapplication;


public class forecast {
    private String forecastdate;
    private String sunrise;
    private String high;
    private String low;
    private String sunset;
    private String aqi;
    private String ymd;
    private String week;
    private String fx;
    private String fl;
    private String type;
    private String notice;

    public forecast(String forecastdate, String sunrise, String high, String low, String sunset, String aqi, String ymd, String week, String fx, String fl, String type, String notice) {
        this.forecastdate = forecastdate;
        this.sunrise = sunrise;
        this.high = high;
        this.low = low;
        this.sunset = sunset;
        this.aqi = aqi;
        this.ymd = ymd;
        this.week = week;
        this.fx = fx;
        this.fl = fl;
        this.type = type;
        this.notice = notice;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getYmd() {
        return ymd;
    }

    public void setYmd(String ymd) {
        this.ymd = ymd;
    }

    public String getForecastdate() {
        return forecastdate;
    }

    public void setForecastdate(String forecastdate) {
        this.forecastdate = forecastdate;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getFx() {
        return fx;
    }

    public void setFx(String fx) {
        this.fx = fx;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String gettype() {
        return type;
    }

    public void settype(String type) {
        this.type = type;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
}
