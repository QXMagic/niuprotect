package com.niuniu.babyprotect.model;
public class UploadLocationInfo {
    private String indoor;
    private String latitude;
    private String locationModel;
    private String longitude;
    private double speed;
    private long timeStamp;

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getIndoor() {
        return this.indoor;
    }

    public void setIndoor(String indoor) {
        this.indoor = indoor;
    }

    public String getLocationModel() {
        return this.locationModel;
    }

    public void setLocationModel(String locationModel) {
        this.locationModel = locationModel;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
