package com.niuniu.babyprotect.model.eventbus;
public class EventParentChangeLimtTime {
    private String message;

    public EventParentChangeLimtTime(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
