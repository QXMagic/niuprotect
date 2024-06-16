package com.niuniu.babyprotect.model.eventbus;
public class EventScreenOnOrOff {
    private int status;

    public EventScreenOnOrOff(int status) {
        this.status = status;
    }

    public int getMessage() {
        return this.status;
    }

    public void setMessage(int message) {
        this.status = message;
    }
}
