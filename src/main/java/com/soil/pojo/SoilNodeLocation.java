package com.soil.pojo;

/**
 * 查询结点位置信息
 * @author ymj
 * @Date： 2019/12/3 21:27
 */
public class SoilNodeLocation {
    /** 结点编号*/
    private int node;
    /** 经度 */
    private double lng;
    /** 纬度 */
    private double lat;

    public SoilNodeLocation() {
        super();
    }

    public SoilNodeLocation(int node, double lng, double lat) {
        this.node = node;
        this.lng = lng;
        this.lat = lat;
    }

    public int getNode() {
        return node;
    }

    public void setNode(int node) {
        this.node = node;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "SoilNodeLocation{" +
                "node=" + node +
                ", lng=" + lng +
                ", lat=" + lat +
                '}';
    }
}
