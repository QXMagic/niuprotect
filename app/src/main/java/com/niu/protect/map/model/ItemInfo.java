package com.niu.protect.map.model;

import com.niu.protect.ui.base.BaseActivity;
public class ItemInfo {
    public Class<? extends BaseActivity> clazz;
    public int descId;
    public int titleIconId;
    public int titleId;

    public ItemInfo(int titleIconId, int titleId, int descId, Class<? extends BaseActivity> clazz) {
        this.titleIconId = titleIconId;
        this.titleId = titleId;
        this.descId = descId;
        this.clazz = clazz;
    }
}
