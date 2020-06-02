package com.soil.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 哪几天有数据
 * @author ymj
 * @Date： 2019/11/14 10:32
 */
public class SoilTimeList {
    private Date timeList;
    private int node;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT")
    public Date getTimeList() {
        return timeList;
    }

    public void setTimeList(Date timeList) {
        this.timeList = timeList;
    }

    public int getNode() {
        return node;
    }

    public void setNode(int node) {
        this.node = node;
    }

    public SoilTimeList(){
        super();
    }
    public SoilTimeList(Date timeList, int node) {
        this.timeList = timeList;
        this.node = node;
    }
}
