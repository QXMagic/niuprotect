package com.niu.protect.model.eventbus;
public class EventParentChangeBindMode {
    private String message;

    public EventParentChangeBindMode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
