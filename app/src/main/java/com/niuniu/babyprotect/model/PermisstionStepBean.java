package com.niuniu.babyprotect.model;

import java.io.Serializable;
public class PermisstionStepBean implements Serializable {
    private String permissionName;
    private int status;
    private int stepNo;

    public String getPermissionName() {
        return this.permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStepNo() {
        return this.stepNo;
    }

    public void setStepNo(int stepNo) {
        this.stepNo = stepNo;
    }
}
