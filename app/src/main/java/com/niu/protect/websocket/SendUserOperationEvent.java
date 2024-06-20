package com.niu.protect.websocket;

import com.google.gson.annotations.SerializedName;

import atmp.consts.Constants;

public class SendUserOperationEvent {
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

    public SendUserOperationEvent(String eventName, long eventTime, String eventType, String memberId, String packageName) {
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.eventType = eventType;
        this.memberId = memberId;
        this.packageName = packageName;
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

    public String getEventType() {
        return this.eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getMemberId() {
        return this.memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
