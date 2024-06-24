package com.niu.protect.model;

import android.view.View;
public class QxInfo {
    String des;
    String img;
    int isok;
    String title;
    View view;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return this.des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getIsok() {
        return this.isok;
    }

    public void setIsok(int isok) {
        this.isok = isok;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public View getView() {
        return this.view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
