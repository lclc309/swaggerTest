package com.lclc.test.util;

import java.io.Serializable;

/**
 * 
 * @name Coordinate
 * @discription 坐标
 * @author lichao
 * @date 2016年1月13日
 */
public class Coordinate implements Serializable {

    private static final long serialVersionUID = -669490367193057389L;

    private int longitude;// 经度

    private int dimension;// 维度

    public Coordinate() {
        super();
    }

    public Coordinate(int longitude, int dimension) {
        super();
        this.longitude = longitude;
        this.dimension = dimension;
    }

    public int getLongitude() {

        return longitude;
    }

    public void setLongitude(int longitude) {

        this.longitude = longitude;
    }

    public int getDimension() {

        return dimension;
    }

    public void setDimension(int dimension) {

        this.dimension = dimension;
    }

    public Coordinate getMinCoordinate(int meters) {

        double k = meters / 1000d;
        double myLat = this.getDimension() / 1000000d;
        double myLng = this.getLongitude() / 1000000d;
        double degree = (24901 * 1609) / 360.0;
        double dpmLat = 1 / degree;
        double radiusLat = dpmLat * k;
        Double minLat = (myLat - radiusLat) * 1000000d;// 最小纬度
        Double maxLat = (myLat + radiusLat) * 1000000d;// 最大纬度
        double dpmLng = 1 / (degree * Math.cos(myLat * (Math.PI / 180)));
        double radiusLng = dpmLng * k;
        Double minLng = (myLng - radiusLng) * 1000000d;// 最小经度
        Double maxLng = (myLng + radiusLng) * 1000000d;// 最大经度
        Coordinate coor = new Coordinate(min(minLng, maxLng).intValue(), min(minLat, maxLat).intValue());
        return coor;
    }

    public Coordinate getMaxCoordinate(int meters) {

        double k = meters / 1000d;
        double myLat = this.getDimension() / 1000000d;
        double myLng = this.getLongitude() / 1000000d;
        double degree = (24901 * 1609) / 360.0;
        double dpmLat = 1 / degree;
        double radiusLat = dpmLat * k;
        Double minLat = (myLat - radiusLat) * 1000000d;// 最小纬度
        Double maxLat = (myLat + radiusLat) * 1000000d;// 最大纬度
        double dpmLng = 1 / (degree * Math.cos(myLat * (Math.PI / 180)));
        double radiusLng = dpmLng * k;
        Double minLng = (myLng - radiusLng) * 1000000d;// 最小经度
        Double maxLng = (myLng + radiusLng) * 1000000d;// 最大经度
        Coordinate coor = new Coordinate(max(maxLng, minLng).intValue(), max(maxLat, minLat).intValue());
        return coor;
    }

    private Double max(Double double1, Double double2) {

        return double1.compareTo(double2) > 0 ? double1 : double2;
    }

    private Double min(Double double1, Double double2) {

        return double1.compareTo(double2) < 0 ? double1 : double2;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();
        builder.append("Coordinate [longitude=");
        builder.append(longitude);
        builder.append(", dimension=");
        builder.append(dimension);
        builder.append("]");
        return builder.toString();
    }

}
