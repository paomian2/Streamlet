package com.streamlet.module.entity.bean;

import java.io.Serializable;

/**
 * Created by streamlet2 on 2016/10/20.
 *
 * @Description
 */
public class LBSAddress implements Serializable{
    private double latitude;
    private double longitude;
    private String address;

    public LBSAddress() {
    }

    public LBSAddress(double latitude, double longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
