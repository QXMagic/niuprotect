package com.niu.protect.model;

import com.niu.protect.tools.Tools;
import java.util.Date;
import org.json.JSONArray;
public class UsePackageInfo {
    private int appType;
    private long changeTimes;
    private long endTime;
    private int errTimes;
    private String mAppName;
    private String mPackageName;
    private int mUsedCount;
    private long mUsedTime;
    JSONArray patternTimeScopes = new JSONArray();
    private long staTime;
    private String timeStr;

    public UsePackageInfo(int mUsedCount, long mUsedTime, String mPackageName, String appName) {
        this.mUsedCount = mUsedCount;
        this.mUsedTime = (int) (mUsedTime / 1000);
        this.mPackageName = mPackageName;
        this.mAppName = appName;
    }

    public long getStaTime() {
        return this.staTime;
    }

    public void setStaTime(long staTime) {
        this.staTime = staTime;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public UsePackageInfo() {
    }

    public void addCount() {
        this.mUsedCount++;
    }

    public int getmUsedCount() {
        return this.mUsedCount;
    }

    public void setmUsedCount(int mUsedCount) {
        this.mUsedCount = mUsedCount;
    }

    public long getmUsedTime() {
        return this.mUsedTime;
    }

    public void setmUsedTime(long mUsedTime) {
        this.mUsedTime = mUsedTime;
    }

    public String getmPackageName() {
        return this.mPackageName;
    }

    public void setmPackageName(String mPackageName) {
        this.mPackageName = mPackageName;
    }

    public String getmAppName() {
        return this.mAppName;
    }

    public void setmAppName(String mAppName) {
        this.mAppName = mAppName;
    }

    public String getTimeStr() {
        return this.timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public void makeTime(long intTime) {
        this.timeStr = Tools.timeFormat(new Date(intTime), "yyyy-MM-dd HH:mm");
    }

    public int getAppType() {
        return this.appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }

    public long getChangeTimes() {
        return this.changeTimes;
    }

    public void setChangeTimes(long changeTimes) {
        this.changeTimes = changeTimes;
    }

    public JSONArray getPatternTimeScopes() {
        return this.patternTimeScopes;
    }

    public void setPatternTimeScopes(JSONArray patternTimeScopes) {
        this.patternTimeScopes = patternTimeScopes;
    }

    public int getErrTimes() {
        return this.errTimes;
    }

    public void setErrTimes(int errTimes) {
        this.errTimes = errTimes;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        UsePackageInfo standardDetail = (UsePackageInfo) o;
        if (!standardDetail.getmPackageName().equals(this.mPackageName)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (this.mPackageName + this.mUsedTime).hashCode();
    }

    public String toString() {
        return "PackageInfo{mUsedCount=" + this.mUsedCount + ", mUsedTime=" + this.mUsedTime + ", mPackageName='" + this.mPackageName + "', mAppName='" + this.mAppName + "'}";
    }
}
