package com.niuniu.babyprotect.model;

import com.google.gson.annotations.SerializedName;
import com.umeng.analytics.AnalyticsConfig;
public class PatternTimeScopesDTO {
    @SerializedName("endTime")
    private String endTime;
    @SerializedName(AnalyticsConfig.RTD_START_TIME)
    private String startTime;

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
