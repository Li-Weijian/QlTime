/**
  * Copyright 2022 json.cn
  */
package com.qltime.model.dto.baidu;
import java.util.Date;

/**
 * Auto-generated: 2022-12-29 18:41:36
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class Forecasts {

    private Date date;
    private String week;
    private int high;
    private int low;
    private String wc_day;
    private String wc_night;
    private String wd_day;
    private String wd_night;
    private String text_day;
    private String text_night;
    private int aqi;
    public void setDate(Date date) {
         this.date = date;
     }
     public Date getDate() {
         return date;
     }

    public void setWeek(String week) {
         this.week = week;
     }
     public String getWeek() {
         return week;
     }

    public void setHigh(int high) {
         this.high = high;
     }
     public int getHigh() {
         return high;
     }

    public void setLow(int low) {
         this.low = low;
     }
     public int getLow() {
         return low;
     }

    public void setWc_day(String wc_day) {
         this.wc_day = wc_day;
     }
     public String getWc_day() {
         return wc_day;
     }

    public void setWc_night(String wc_night) {
         this.wc_night = wc_night;
     }
     public String getWc_night() {
         return wc_night;
     }

    public void setWd_day(String wd_day) {
         this.wd_day = wd_day;
     }
     public String getWd_day() {
         return wd_day;
     }

    public void setWd_night(String wd_night) {
         this.wd_night = wd_night;
     }
     public String getWd_night() {
         return wd_night;
     }

    public void setText_day(String text_day) {
         this.text_day = text_day;
     }
     public String getText_day() {
         return text_day;
     }

    public void setText_night(String text_night) {
         this.text_night = text_night;
     }
     public String getText_night() {
         return text_night;
     }

    public void setAqi(int aqi) {
         this.aqi = aqi;
     }
     public int getAqi() {
         return aqi;
     }

}
