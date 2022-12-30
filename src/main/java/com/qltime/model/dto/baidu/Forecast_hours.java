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
public class Forecast_hours {

    private String text;
    private int temp_fc;
    private String wind_class;
    private String wind_dir;
    private int rh;
    private int prec_1h;
    private int clouds;
    private Date data_time;
    public void setText(String text) {
         this.text = text;
     }
     public String getText() {
         return text;
     }

    public void setTemp_fc(int temp_fc) {
         this.temp_fc = temp_fc;
     }
     public int getTemp_fc() {
         return temp_fc;
     }

    public void setWind_class(String wind_class) {
         this.wind_class = wind_class;
     }
     public String getWind_class() {
         return wind_class;
     }

    public void setWind_dir(String wind_dir) {
         this.wind_dir = wind_dir;
     }
     public String getWind_dir() {
         return wind_dir;
     }

    public void setRh(int rh) {
         this.rh = rh;
     }
     public int getRh() {
         return rh;
     }

    public void setPrec_1h(int prec_1h) {
         this.prec_1h = prec_1h;
     }
     public int getPrec_1h() {
         return prec_1h;
     }

    public void setClouds(int clouds) {
         this.clouds = clouds;
     }
     public int getClouds() {
         return clouds;
     }

    public void setData_time(Date data_time) {
         this.data_time = data_time;
     }
     public Date getData_time() {
         return data_time;
     }

}
