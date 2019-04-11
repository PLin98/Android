package com.example.myapplication;

/**
 * Created by Rin on 2019/3/26.
 */
/* {
         "date": "14",
         "sunrise": "06:31",
         "high": "高温 16.0℃",
         "low": "低温 4.0℃",
         "sunset": "18:19",
         "aqi": 41.0,
         "ymd": "2019-03-14",
         "week": "星期四",
         "fx": "西北风",
         "fl": "3-4级",
         "type": "多云",
         "notice": "阴晴之间，谨防紫外线侵扰"
         }*/
public class Forecast {
    private String date;
    private String sunrise;
    private String high;

    private String low;
    private String sunset;
    private int api;
    private String ymd;
    private String week;
    private String fx;
    private String fl;
    private String type;
    private String notice;

    public Forecast(String date, String sunrise, String high, String low, String sunset, int api, String ymd, String week, String fx, String fl, String type, String notice) {
        this.date = date;
        this.sunrise = sunrise;
        this.high = high;
        this.low = low;
        this.sunset = sunset;
        this.api = api;
        this.ymd = ymd;
        this.week = week;
        this.fx = fx;
        this.fl = fl;
        this.type = type;
        this.notice = notice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public int getApi() {
        return api;
    }

    public void setApi(int api) {
        this.api = api;
    }

    public String getYmd() {
        return ymd;
    }

    public void setYmd(String ymd) {
        this.ymd = ymd;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    @Override
    public String toString() {
        return "date:'" + date + '\'' +
                ", sunrise:'" + sunrise + '\'' +
                ", high:'" + high + '\'' +
                ", low:'" + low + '\'' +
                ", sunset:'" + sunset + '\'' +
                ", api:" + api +
                ", ymd:'" + ymd + '\'' +
                ", week:'" + week + '\'' +
                ", fx:'" + fx + '\'' +
                ", fl:'" + fl + '\'' +
                ", type:'" + type + '\'' +
                ", notice:'" + notice + '\'' +
                '}';
    }
}
