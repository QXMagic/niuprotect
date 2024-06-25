package com.niu.protect.model;

import android.app.Activity;

public class ItemInfo {
    public Class<? extends Activity> clazz;
    public int descId;
    public int titleIconId;
    public int titleId;

    public ItemInfo(int titleIconId, int titleId, int descId, Class<? extends Activity> clazz) {
        this.titleIconId = titleIconId;
        this.titleId = titleId;
        this.descId = descId;
        this.clazz = clazz;
    }
}
