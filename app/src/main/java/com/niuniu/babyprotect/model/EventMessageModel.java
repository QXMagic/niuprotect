package com.niuniu.babyprotect.model;

import com.google.gson.annotations.SerializedName;
import com.taobao.accs.common.Constants;
public class EventMessageModel {
    @SerializedName("eventName")
    private String eventName;
    @SerializedName("eventTime")
    private long eventTime;
    @SerializedName("eventType")
    private String eventType;
    @SerializedName("memberId")
    private String memberId;
    @SerializedName(Constants.KEY_PACKAGE_NAME)
    private String packageName;

    public EventMessageModel(String memberId, String eventType, String eventName, long eventTime, String packageName) {
        this.memberId = memberId;
        this.eventType = eventType;
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.packageName = packageName;
    }

    public String getMemberId() {
        return this.memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getEventType() {
        return this.eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventName() {
        return this.eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public long getEventTime() {
        return this.eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
