package com.niu.protect.manager;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
public class MineDevicePolicyManager {
    private static MineDevicePolicyManager mDeviceMethod;
    private ComponentName componentName;
    private DevicePolicyManager devicePolicyManager;
    private Context mContext;

    private MineDevicePolicyManager(Context context) {
        this.mContext = context;
        this.devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);

    }

    public static MineDevicePolicyManager getInstance(Context context) {
        return getInstance(context,null);
    }
    public static MineDevicePolicyManager getInstance(Context context,Class cls) {
        if (mDeviceMethod == null) {
            if(cls==null){
                return null;
            }
            synchronized (MineDevicePolicyManager.class) {
                if (mDeviceMethod == null) {
                    mDeviceMethod = new MineDevicePolicyManager(context);
                    mDeviceMethod.componentName = new ComponentName(context, Class.class);
                }
            }
        }
        return mDeviceMethod;
    }

    public void onActivate() {
        if (!this.devicePolicyManager.isAdminActive(this.componentName)) {
            Intent intent = new Intent("android.app.action.ADD_DEVICE_ADMIN");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("android.app.extra.DEVICE_ADMIN", this.componentName);
            intent.putExtra("android.app.extra.ADD_EXPLANATION", "提示文字");
            this.mContext.startActivity(intent);
            return;
        }
        Toast.makeText(this.mContext, "设备已经激活,请勿重复激活", Toast.LENGTH_SHORT).show();
    }

    public void onRemoveActivate() {
        this.devicePolicyManager.removeActiveAdmin(this.componentName);
    }

    public void startLockMethod() {
        Intent intent = new Intent("android.app.action.SET_NEW_PASSWORD");
        this.mContext.startActivity(intent);
    }

    public void setLockMethod() {
        if (this.devicePolicyManager.isAdminActive(this.componentName)) {
            Intent intent = new Intent("android.app.action.SET_NEW_PASSWORD");
            this.devicePolicyManager.setPasswordQuality(this.componentName, 131072);
            this.mContext.startActivity(intent);
            return;
        }
        Toast.makeText(this.mContext, "请先激活设备", Toast.LENGTH_SHORT).show();
    }

    public void LockNow() {
        if (this.devicePolicyManager.isAdminActive(this.componentName)) {
            this.devicePolicyManager.lockNow();
        } else {
            Toast.makeText(this.mContext, "请先激活设备", Toast.LENGTH_SHORT).show();
        }
    }

    public void LockByTime(long time) {
        if (this.devicePolicyManager.isAdminActive(this.componentName)) {
            this.devicePolicyManager.setMaximumTimeToLock(this.componentName, time);
        } else {
            Toast.makeText(this.mContext, "请先激活设备", Toast.LENGTH_SHORT).show();
        }
    }

    public void WipeData() {
        if (this.devicePolicyManager.isAdminActive(this.componentName)) {
            this.devicePolicyManager.wipeData(1);
        } else {
            Toast.makeText(this.mContext, "请先激活设备", Toast.LENGTH_SHORT).show();
        }
    }

    public void setPassword(String password) {
        if (this.devicePolicyManager.isAdminActive(this.componentName)) {
            this.devicePolicyManager.resetPassword(password, 1);
        } else {
            Toast.makeText(this.mContext, "请先激活设备", Toast.LENGTH_SHORT).show();
        }
    }
}
