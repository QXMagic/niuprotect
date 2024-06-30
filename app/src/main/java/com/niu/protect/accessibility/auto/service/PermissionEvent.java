package com.niu.protect.accessibility.auto.service;

import android.os.Bundle;

public class PermissionEvent {
    public static final int PERMISSION_PAIR_RESULT = 102;
    public static final int PERMISSION_RESULT = 100;
    public static final int PERMISSION_SCREEN_RESULT = 101;
    private final Bundle bundle = new Bundle();
    private int code;

    public PermissionEvent() {
    }

    public Bundle getBundle() {
        return this.bundle;
    }

    public int getCode() {
        return this.code;
    }

    public void post() {
//        EventBus.m20451().m20455(this);
    }

    public void putData(String str, boolean z) {
        this.bundle.putBoolean(str, z);
    }

    public void setCode(int i) {
        this.code = i;
    }

    public void putData(String str, int i) {
        this.bundle.putInt(str, i);
    }

    public PermissionEvent(int i) {
        this.code = i;
    }

    public void putData(String str, String str2) {
        this.bundle.putString(str, str2);
    }
}
