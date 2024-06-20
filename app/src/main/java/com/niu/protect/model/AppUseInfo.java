package com.niu.protect.model;
public class AppUseInfo {
    private String endTimeStamp;
    private String packageName;
    private String startTimeStamp;
    private long useTime;

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getEndTimeStamp() {
        return this.endTimeStamp;
    }

    public void setEndTimeStamp(String endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public String getStartTimeStamp() {
        return this.startTimeStamp;
    }

    public void setStartTimeStamp(String startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public long getUseTime() {
        return this.useTime;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }
}
