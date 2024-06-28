package com.niu.protect.model;

public class LatLngInfo {
    int index;
    boolean isdel;
    Long localtime;
    double longitude;
    double latitude;
//    altitude //海拔
//            accuracy //精度

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


    public boolean isIsdel() {
        return this.isdel;
    }

    public void setIsdel(boolean isdel) {
        this.isdel = isdel;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Long getLocaltime() {
        return this.localtime;
    }

    public void setLocaltime(Long localtime) {
        this.localtime = localtime;
    }
}
