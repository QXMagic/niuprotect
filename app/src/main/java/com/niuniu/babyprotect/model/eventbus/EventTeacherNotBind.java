package com.niuniu.babyprotect.model.eventbus;
public class EventTeacherNotBind {
    private String message;

    public EventTeacherNotBind(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
