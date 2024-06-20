package com.niu.protect.model;
public class AppRecInfo {
    String appId;
    String appImage;
    String appName;
    String packageName;
    int useCount;
    int useTime;

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppImage() {
        return this.appImage;
    }

    public void setAppImage(String appImage) {
        this.appImage = appImage;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getUseCount() {
        return this.useCount;
    }

    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }

    public int getUseTime() {
        return this.useTime;
    }

    public void setUseTime(int useTime) {
        this.useTime = useTime;
    }
}
