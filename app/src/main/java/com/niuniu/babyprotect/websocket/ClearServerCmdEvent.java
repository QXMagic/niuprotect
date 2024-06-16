package com.niuniu.babyprotect.websocket;
public class ClearServerCmdEvent {
    private String memberId;

    public ClearServerCmdEvent(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberId() {
        return this.memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
