package com.niu.protect.model.eventbus;
public class EventParentChangeLimtAPPTime {
    private String message;

    public EventParentChangeLimtAPPTime(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
