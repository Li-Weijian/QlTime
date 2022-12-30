package com.qltime.model.dto;

/**
 * @auther: liweijian
 * @Date: 2020/5/22 23:11
 * @Description: 图表节点
 */
public class CharNode {

    private String date;
    private String type;
    private Double value;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
