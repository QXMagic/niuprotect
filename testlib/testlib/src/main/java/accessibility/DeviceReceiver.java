package accessibility;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

public class DeviceReceiver extends DeviceAdminReceiver {

    /* renamed from: 肌緭 */
    public final DeviceLifeMgr f12104 = new DeviceLifeMgr();

    @Override // android.app.admin.DeviceAdminReceiver
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
        this.f12104.m15023(context, intent);
    }

    @Override // android.app.admin.DeviceAdminReceiver
    public void onEnabled(Context context, Intent intent) {
        super.onEnabled(context, intent);
        this.f12104.m24463(context, intent);
    }

    @Override // android.app.admin.DeviceAdminReceiver
    public void onLockTaskModeEntering(Context context, Intent intent, String str) {
        super.onLockTaskModeEntering(context, intent, str);
        this.f12104.m24465(context, intent, str);
    }

    @Override // android.app.admin.DeviceAdminReceiver
    public void onLockTaskModeExiting(Context context, Intent intent) {
        super.onLockTaskModeExiting(context, intent);
        this.f12104.m24466(context, intent);
    }

    @Override // android.app.admin.DeviceAdminReceiver
    public void onProfileProvisioningComplete(Context context, Intent intent) {
        super.onProfileProvisioningComplete(context, intent);
        this.f12104.m24464(context, intent);
    }

    @Override // android.app.admin.DeviceAdminReceiver, android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        this.f12104.m15022(context, intent);
    }
}

