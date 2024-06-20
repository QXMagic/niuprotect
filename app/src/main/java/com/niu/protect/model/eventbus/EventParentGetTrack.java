package com.niu.protect.model.eventbus;
public class EventParentGetTrack {
    private String message;

    public EventParentGetTrack(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
