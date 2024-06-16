package com.niuniu.babyprotect.model.eventbus;
public class EventLoadUserInfo {
    private String message;

    public EventLoadUserInfo(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
