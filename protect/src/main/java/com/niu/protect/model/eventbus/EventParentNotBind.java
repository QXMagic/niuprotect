package com.niu.protect.model.eventbus;
public class EventParentNotBind {
    private String message;

    public EventParentNotBind(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
