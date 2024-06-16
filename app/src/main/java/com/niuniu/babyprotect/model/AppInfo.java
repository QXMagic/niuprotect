package com.niuniu.babyprotect.model;

import android.content.Intent;
import android.graphics.drawable.Drawable;
public class AppInfo {
    String appId;
    String appImage;
    String appName;
    private Drawable ico;
    String id;
    private Intent intent;
    boolean isDefault;
    String name;
    String packageName;
    String studentId;
    int type;
    int useTime;

    public boolean isDefault() {
        return this.isDefault;
    }

    public void setDefault(boolean aDefault) {
        this.isDefault = aDefault;
    }

    public String getAppImage() {
        return this.appImage;
    }

    public void setAppImage(String appImage) {
        this.appImage = appImage;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return this.studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public int getUseTime() {
        return this.useTime;
    }

    public void setUseTime(int useTime) {
        this.useTime = useTime;
    }

    public Drawable getIco() {
        return this.ico;
    }

    public void setIco(Drawable ico) {
        this.ico = ico;
    }

    public Intent getIntent() {
        return this.intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}
