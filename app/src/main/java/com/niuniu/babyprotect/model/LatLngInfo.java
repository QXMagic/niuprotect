package com.niuniu.babyprotect.model;

import com.baidu.mapapi.model.LatLng;
public class LatLngInfo {
    int index;
    boolean isdel;
    LatLng latLng;
    Long localtime;

    public LatLng getLatLng() {
        return this.latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public boolean isIsdel() {
        return this.isdel;
    }

    public void setIsdel(boolean isdel) {
        this.isdel = isdel;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Long getLocaltime() {
        return this.localtime;
    }

    public void setLocaltime(Long localtime) {
        this.localtime = localtime;
    }
}
