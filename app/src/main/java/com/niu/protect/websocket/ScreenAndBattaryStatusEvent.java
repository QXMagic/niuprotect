package com.niu.protect.websocket;

import com.google.gson.annotations.SerializedName;
public class ScreenAndBattaryStatusEvent {
    @SerializedName("electricQuantity")
    private String electricQuantity;
    @SerializedName("memberId")
    private String memberId;
    @SerializedName("screenStatus")
    private int screenStatus;

    public ScreenAndBattaryStatusEvent(String electricQuantity, int screenStatus, String memberId) {
        this.electricQuantity = electricQuantity;
        this.screenStatus = screenStatus;
        this.memberId = memberId;
    }

    public String getElectricQuantity() {
        return this.electricQuantity;
    }

    public void setElectricQuantity(String electricQuantity) {
        this.electricQuantity = electricQuantity;
    }

    public int getScreenStatus() {
        return this.screenStatus;
    }

    public void setScreenStatus(int screenStatus) {
        this.screenStatus = screenStatus;
    }

    public String getMemberId() {
        return this.memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
