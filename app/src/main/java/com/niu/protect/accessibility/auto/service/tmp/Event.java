package com.niu.protect.accessibility.auto.service.tmp;

import android.os.Bundle;

public class Event {
    public static final int APP_COMPARE = 137;
    public static final int APP_INTERCEPT = 110;
    public static final int APP_LIMIT_GROUP_SET = 131;
    public static final int APP_MANAGER_SWITCH = 145;
    public static final int APP_REWARD = 130;
    public static final int APP_USAGE_INFO = 136;
    public static final int CHECK_PERMISSION = 118;
    public static final int CHILD_CONFIG = 141;
    public static final int CHILD_UPDATE = 129;
    public static final int CLOSE_PLUGIN = 112;
    public static final int CONNECT_CHILD_EVENT = 115;
    public static final int CONNECT_PARENT_EVENT = 116;
    public static final int GROUP_LIMIT_TIME = 127;
    public static final int LEAVE_MESSAGE = 128;
    public static final int LIFT_LOCK = 107;
    public static final int LOCKSCREEEN = 101;
    public static final int LOCK_SETTING = 132;
    public static final int LOCK_TIME_CANCEL = 122;
    public static final int LOCK_TIME_EVENT = 121;
    public static final int LOCK_TIME_V2_EVENT = 140;
    public static final int OPEN_PLUGIN = 111;
    public static final int PAUSE_MANAGER = 126;
    public static final int PERMISSION_AUTO = 133;
    public static final int RESTART_CHILD_PHONE = 143;
    public static final int SCREENSHOT = 102;
    public static final int SCREEN_SUCCESS = 113;
    public static final int STUDY_MODEL = 108;
    public static final int TEMP_UNLOCK = 138;
    public static final int TIME_GAP = 117;
    public static final int UNBIND = 105;
    public static final int UNBIND_SUCCESS = 112;
    public static final int UPLOAD_APP_LIST = 134;
    public static final int UPLOAD_LOG = 135;
    public static final int VPN_SWITCH = 144;
    public static final int WEBSITE_EVENT = 142;
    private Bundle bundle = new Bundle();
    private int code;
    private String display;
    private String extra;
    private String guard_alias;
    private boolean isOffLine;
    private String messageId;
    private int uid;

    public Bundle getBundle() {
        return this.bundle;
    }

    public int getCode() {
        return this.code;
    }

    public String getDisplay() {
        return this.display;
    }

    public String getExtra() {
        return this.extra;
    }

    public String getGuard_alias() {
        return this.guard_alias;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public int getUid() {
        return this.uid;
    }

    public boolean isOffLine() {
        return this.isOffLine;
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

    public void setDisplay(String str) {
        this.display = str;
    }

    public void setExtra(String str) {
        this.extra = str;
    }

    public void setGuard_alias(String str) {
        this.guard_alias = str;
    }

    public void setMessageId(String str) {
        this.messageId = str;
    }

    public void setOffLine(boolean z) {
        this.isOffLine = z;
    }

    public void setUid(int i) {
        this.uid = i;
    }

    public String toString() {
        return "Event{code=" + this.code + ", display='" + this.display + "', uid=" + this.uid + ", extra='" + this.extra + "', guard_alias='" + this.guard_alias + "', messageId='" + this.messageId + "', isOffLine=" + this.isOffLine + '}';
    }

    public void putData(String str, int i) {
        this.bundle.putInt(str, i);
    }

    public void putData(String str, String str2) {
        this.bundle.putString(str, str2);
    }
}
