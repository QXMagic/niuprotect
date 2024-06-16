package com.niuniu.babyprotect.stomon.huawei;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import im.niu.protect.R;
public class SampleDeviceReceiver extends DeviceAdminReceiver {
    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        return context.getString(R.string.disable_warning);
    }
}
