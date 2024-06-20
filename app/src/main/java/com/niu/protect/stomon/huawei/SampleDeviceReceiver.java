package com.niu.protect.stomon.huawei;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import com.niu.protect.R;
public class SampleDeviceReceiver extends DeviceAdminReceiver {
    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        return context.getString(R.string.disable_warning);
    }
}
