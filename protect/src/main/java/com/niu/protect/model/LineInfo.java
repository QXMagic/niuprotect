package com.niu.protect.model;
public class LineInfo {
    private static final double EARTH_RADIUS = 6378137.0d;
    double des;
    LatLngInfo latLng1;
    LatLngInfo latLng2;

    public LatLngInfo getLatLng1() {
        return this.latLng1;
    }

    public void setLatLng1(LatLngInfo latLng1) {
        this.latLng1 = latLng1;
    }

    public LatLngInfo getLatLng2() {
        return this.latLng2;
    }

    public void setLatLng2(LatLngInfo latLng2) {
        this.latLng2 = latLng2;
    }

    public double getDes() {
        return this.des;
    }

    public void setDes(double des) {
        this.des = des;
    }

    private static double rad(double d) {
        return (3.141592653589793d * d) / 180.0d;
    }

    public double getDistance() {
        double radLat1 = rad(this.latLng1.getLatLng().latitude);
        double radLat2 = rad(this.latLng2.getLatLng().latitude);
        double a = radLat1 - radLat2;
        double b = rad(this.latLng1.getLatLng().longitude) - rad(this.latLng2.getLatLng().longitude);
        double s = Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2.0d), 2.0d) + (Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2.0d), 2.0d)))) * 2.0d * EARTH_RADIUS;
        this.des = s;
        return s;
    }

    public double getSecSpeed() {
        long timeout = Math.abs(this.latLng1.getLocaltime().longValue() - this.latLng2.getLocaltime().longValue());
        if (timeout == 0) {
            return 0.0d;
        }
        double speed = getDistance() / (((float) timeout) / 1000.0f);
        return speed;
    }
}
