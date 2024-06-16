package com.niuniu.babyprotect.model;
public class AppUseTimeModel {
    private String appName;
    private long limitUseTime;
    private String packageName;
    private long useTime;

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public long getUseTime() {
        return this.useTime;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }

    public long getLimitUseTime() {
        return this.limitUseTime;
    }

    public void setLimitUseTime(long limitUseTime) {
        this.limitUseTime = limitUseTime;
    }

    public boolean isOverLimitTime() {
        return this.useTime >= this.limitUseTime;
    }

    public void addUseTime(int mUseTime) {
        this.useTime += mUseTime;
    }
}
