package com.niuniu.babyprotect.model.eventbus;
public class EventParentGetLocation {
    private String message;

    public EventParentGetLocation(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
