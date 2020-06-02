package com.soil.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author dell
 */
public class SoilWater {

    /** 结点号 */
    private Integer node;
    /** 水势*/
    private Double humidity;
    /** 时间*/
    private Date times;


    public SoilWater(Integer id, Integer node, Double humidity, Date times, Date updateTime) {
        this.node = node;
        this.humidity = humidity;
        this.times = times;
    }

    public SoilWater() {
        super();
    }

    public void setNode(Integer node) {
        this.node = node;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public void setTimes(Date times) {
        this.times = times;
    }

    public Integer getNode() {
        return node;
    }

    public Double getHumidity() {
        return humidity;
    }

    @JsonFormat(pattern = "yyyy-MM-dd- HH:mm:ss", timezone = "GMT")
    public Date getTimes() {
        return times;
    }

    @Override
    public String toString() {
        return "Soil{" +
                "node=" + node +
                ", humidity=" + humidity +
                ", createTime=" + times +
                '}';
    }
}
