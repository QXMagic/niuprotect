package com.niu.protect.tools.easypermission;
public enum GrantResult {
    GRANT(0),
    DENIED(-1),
    IGNORE(-2);
    
    private int type;

    GrantResult(int type) {
        this.type = type;
    }

    public int getValue() {
        return this.type;
    }
}
