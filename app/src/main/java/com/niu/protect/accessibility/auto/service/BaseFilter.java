package com.niu.protect.accessibility.auto.service;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.RomUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public abstract class BaseFilter {

    /* renamed from: 肌緭 */
    public Context f14238;

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
    public final ThreadSafeHashSet<String> f23368 = new ThreadSafeHashSet<>();

    /* renamed from: 祴嚚橺谋肬鬧舘 */
    public final ThreadSafeHashSet<String> f23365 = new ThreadSafeHashSet<>();

    /* renamed from: 垡玖 */
    public final ThreadSafeHashSet<String> f14237 = new ThreadSafeHashSet<>();

    /* renamed from: 镐藻 */
    public final ThreadSafeHashSet<String> f14239 = new ThreadSafeHashSet<>();

    /* renamed from: 灞酞輀攼嵞漁綬迹 */
    public final ConcurrentHashMap<String, Boolean> f23363 = new ConcurrentHashMap<>();

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
    public final ThreadSafeHashSet<String> f23366 = new ThreadSafeHashSet<>();

    /* renamed from: 旞莍癡 */
    public final ThreadSafeHashSet<String> f23362 = new ThreadSafeHashSet<>();

    /* renamed from: 刻槒唱镧詴 */
    public final ThreadSafeHashSet<String> f23361 = new ThreadSafeHashSet<>();

    /* renamed from: 酸恚辰橔纋黺 */
//    public final AppArraySet f23367 = new AppArraySet(7);

    /* renamed from: 偣炱嘵蟴峗舟轛 */
    public final ThreadSafeHashSet<String> f23360 = new ThreadSafeHashSet<>();

    /* renamed from: 睳堋弗粥辊惶 */
    public final ThreadSafeHashSet<String> f23364 = new ThreadSafeHashSet<>();

    public BaseFilter(Context context) {
        this.f14238 = context;
        m25907();
        m25910();
        m25914();
        m25920();
        m25922();
        m15542();
        m25908();
        new Thread(new Runnable() { // from class: 褣餐驫喸嶷.葋申湋骶映鍮秄憁鎓羭
            @Override // java.lang.Runnable
            public final void run() {
                BaseFilter.this.m25915();
            }
        }).start();
    }

    /* renamed from: 偣炱嘵蟴峗舟轛 */
    public final boolean m25903(String str) {
        return this.f14239.m23901(str);
    }

    /* renamed from: 刻槒唱镧詴 */
    public final void m25904(Intent intent) {
        for (ResolveInfo resolveInfo : this.f14238.getPackageManager().queryIntentActivities(intent, 0)) {
            if (m25932(resolveInfo.activityInfo.packageName)) {
                this.f23361.m14793(resolveInfo.activityInfo.name);
                if (!TextUtils.isEmpty(resolveInfo.activityInfo.targetActivity)) {
                    this.f23361.m14793(resolveInfo.activityInfo.targetActivity);
                }
            }
        }
    }

    /* renamed from: 卝閄侸靤溆鲁扅 */
    public final boolean m25905(String str) {
        return this.f23361.m23901(str);
    }

    /* renamed from: 哠畳鲜郣新剙鳰活茙郺嵝 */
    public boolean m25906(CharSequence charSequence) {
        if (charSequence == null) {
            return false;
        }
        return m25928(m25930(), charSequence.toString());
    }

    /* renamed from: 唌橅咟 */
    public final void m25907() {
        Iterator<ResolveInfo> it = this.f14238.getPackageManager().queryIntentActivities(new Intent("android.settings.SETTINGS"), 0).iterator();
        while (it.hasNext()) {
            this.f23366.m14793(it.next().activityInfo.name);
        }
        Iterator<ResolveInfo> it2 = m25919(new Intent("android.settings.VPN_SETTINGS")).iterator();
        while (it2.hasNext()) {
            this.f23366.m14793(it2.next().activityInfo.name);
        }
        this.f23366.m14793("com.android.settings.Settings$AppDrawOverlaySettingsActivity");
        this.f23366.m14793("com.android.settings.MasterClear");
        this.f23366.m14793("com.android.settings.MediaFormat");
        this.f23366.m14793("com.android.settings.intelligence.search.SearchActivity");
        if (RomUtils.isXiaomi()) {
            this.f23366.m14793("com.android.settings.accessibility.MiuiAccessibilitySettingsActivity");
            this.f23366.m14793("com.miui.powercenter.PowerMainActivity");
            this.f23366.m14793("com.miui.powercenter.legacypowerrank.PowerDetailActivity");
            this.f23366.m14793("com.miui.powerkeeper.ui.HiddenAppsConfigActivity");
            this.f23366.m14793("com.miui.powerkeeper.ui.HiddenAppsContainerManagementActivity");
            this.f23366.m14793("com.miui.permcenter.permissions.AppPermissionsTabActivity");
            this.f23366.m14793("com.miui.home.settings.MiuiHomeSettingActivity");
            this.f23366.m14793("com.miui.securityspace.ui.activity.PrivateSpaceMainActivity");
            this.f23366.m14793("com.android.settings.Settings$DisplaySettingsActivity");
            return;
        }
        if (RomUtils.isVivo()) {
            this.f23366.m14793("com.vivo.permissionmanager.activity.FloatWindowManagerActivity");
            this.f23366.m14793("com.vivo.permissionmanager.activity.BgStartUpManagerActivity");
            this.f23366.m14793("com.vivo.permissionmanager.activity.StartBgActivityControlActivity");
            this.f23366.m14793("com.bbk.launcher2.settings.LauncherSettingsPreference");
            this.f23366.m14793("com.vivo.settings.applications.InstalledAppDetailsTop");
            return;
        }
        if (!RomUtils.isOppo() && !Tools.m22497()) {
            if (RomUtils.isHuawei()) {
                this.f23366.m14793("com.android.packageinstaller.permission.ui.AppPermissionActivity");
                this.f23366.m14793("com.huawei.android.launcher.HomeMoreSettings");
                this.f23366.m14793("com.huawei.hidisk.view.activity.category.CategoryFileListActivity");
                this.f23366.m14793("com.huawei.appmarket.service.appmgr.view.activity.AppUnInstallActivity");
                return;
            }
            if (Tools.m22457()) {
                this.f23366.m14793("com.hihonor.android.launcher.HomeMoreSettings");
                return;
            }
            return;
        }
        this.f23366.m14793("com.coloros.recents.locksetting.ui.LockSettingActivity");
        this.f23366.m14793("com.coloros.notificationmanager.AppNotificationSettingsActivity");
        this.f23366.m14793("com.coloros.networkcontrol.ui.NetworkControlAppActivity");
        this.f23366.m14793("com.coloros.networkcontrol.ui.AppControlActivity");
        this.f23366.m14793("com.coloros.networkcontrol.NetworkAppControlActivity");
        m25924("com.coloros.quickstep.locksetting.ui.LockSettingActivity");
        this.f23366.m14793("com.oplusos.securitypermission.permission.singlepage.PermissionTabActivity");
        this.f23366.m14793("com.oplus.safecenter.startupapp.view.StartupAppListActivity");
        this.f23366.m14793("com.oplus.safecenter.startupapp.view.AssociateStartActivity");
        this.f23366.m14793("com.oplusos.securitypermission.permission.ui.handheld.PermissionAppsActivityNew");
        this.f23366.m14793("com.heytap.speechassist.home.settings.ui.SettingsActivity");
        this.f23366.m14793("com.coloros.powermanager.fuelgaue.PowerUsageModelActivity");
        this.f23366.m14793("com.coloros.powermanager.fuelgaue.PowerConsumptionOptimizationActivity");
        this.f23366.m14793("com.coloros.powermanager.fuelgaue.IntellPowerSaveScence");
        this.f23366.m14793("com.oppo.launcher.settings.LauncherSettingsActivity");
    }

    /* renamed from: 垡玖 */
    public final void m15538(Intent intent) {
        Iterator<ResolveInfo> it = this.f14238.getPackageManager().queryIntentActivities(intent, 0).iterator();
        while (it.hasNext()) {
            String str = it.next().activityInfo.packageName;
            if (m25932(str)) {
                mo25883(str);
            }
        }
    }

    /* renamed from: 壋劘跆貭澴綄秽攝煾訲 */
    public final void m25908() {
        this.f23361.m14793("com.android.settings.applications.InstalledAppDetailsTop");
        if (RomUtils.isVivo()) {
            this.f23361.m14793("com.vivo.settings.applications.InstalledAppDetailsTop");
        } else if (RomUtils.isXiaomi()) {
            this.f23361.m14793("com.miui.appmanager.ApplicationsDetailsActivity");
        }
    }

    /* renamed from: 彻薯铏螙憣欖愡鼭 */
    public boolean m25909(String str) {
        return str.startsWith("com.android") && (str.endsWith("contacts") || str.endsWith("incallui") || str.endsWith("mms") || str.endsWith("camera"));
    }

    /* renamed from: 攏瑹迀虚熂熋卿悍铒誦爵 */
    public final void m25910() {
        if (RomUtils.isXiaomi()) {
            this.f14239.m14793("com.miui.permcenter.install.AdbInstallActivity");
            this.f14239.m14793("com.miui.wakepath.ui.ConfirmStartActivity");
            this.f14239.m14793("com.miui.gamebooster.GameBoosterRichWebActivity");
            this.f14239.m14793("com.miui.permcenter.permissions.SystemAppPermissionDialogActivity");
            this.f14239.m14793("com.xiaomi.gamecenter.sdk.ui.actlayout.ViewForLogin");
            this.f14239.m14793("com.miui.applicationlock.ConfirmAccessControl");
        }
        m25921("com.android.settings.ShowAdminSupportDetailsDialog");
        m25921("com.android.settings.enterprise.ActionDisabledByAdminDialog");
        if (RomUtils.isOneplus()) {
            this.f14239.m14793("net.oneplus.launcher.dragndrop.AddItemActivity");
        }
        if (RomUtils.isVivo()) {
            this.f14239.m14793("com.vivo.camerascan.ui.WifiCameraActivity");
            this.f14239.m14793("com.android.systemui.globalactions.VivoGlobalActionsDialog");
            this.f14239.m14793("com.vivo.systemui.statusbar.globalactions.VivoGlobalActionsDialog");
            this.f14239.m14793("com.vivo.settings.secret.PasswordActivity");
            this.f14239.m14793("com.vivo.settings.secret.softwarelock.base.view.dialog.PinSystemDialog");
        }
        if (!RomUtils.isOppo() && !Tools.m22497()) {
            if (RomUtils.isHuawei() || Tools.m22483()) {
                this.f14239.m14793("com.huawei.securitycenter.applock.password.AuthLaunchLockedAppActivity");
                this.f14239.m14793("com.huawei.securitycenter.applock.password.LockScreenLaunchLockedAppActivity");
                this.f14239.m14793("com.huawei.securitycenter.applock.password.setting.PasswordProtectVerifyActivity");
                this.f14239.m14793("com.android.settings.thememanager.ringtone.LocalRingtoneManagerActivity");
                this.f14239.m14793("com.android.settings.thememanager.ringtone.VibratorTypeSelectActivity");
                this.f14239.m14793("com.android.settings.Settings$ZonePickerActivity");
            }
        } else {
            this.f14239.m14793("com.oppo.settings.OppoPhoneNameSettings");
            this.f14239.m14793("com.coloros.wirelesssettings.wifi.OppoWifiEditor");
            this.f14239.m14793("com.coloros.wirelesssettings.OppoWirelessSettingsActivity");
            this.f14239.m14793("com.coloros.networkcontrol.ui.NetworkControlAppActivity");
            this.f14239.m14793("com.coloros.networkcontrol.ui.AppControlActivity");
            this.f14239.m14793("com.coloros.simsettings.OppoSimSettingsActivity");
            this.f14239.m14793("com.coloros.wirelesssettings.bluetooth.BluetoothPairingDialog");
            this.f14239.m14793("com.coloros.datamonitor.datasaver.DataSaverSummaryActivity");
            this.f14239.m14793("com.android.bluetoothsettings.bluetooth.BluetoothPairingDialog");
            this.f14239.m14793("com.coloros.safecenter.verification.login.AccountActivity");
            this.f14239.m14793("com.coloros.safecenter.privacy.view.password.AppUnlockPasswordActivity");
        }
        this.f14239.m14793("com.android.settings.wifi.dpp.MiuiWifiDppConfiguratorActivity");
        this.f14239.m14793("com.android.settings.wifi.WifiConfigActivity");
        this.f14239.m14793("com.android.packageinstaller.permission.ui.GrantPermissionsActivity");
        this.f14239.m14793("com.android.settings.deviceinfo.Status");
        this.f14239.m14793("com.android.settings.wifi.WifiNoInternetDialog");
        this.f14239.m14793("com.android.settings.Settings$DeviceInfoSettingsActivity");
        this.f14239.m14793("com.android.bluetoothsettings.bluetooth.BluetoothPairingDialog");
        this.f14239.m14793("com.android.bluetooth.ble.DevicePickerActivity");
        this.f14239.m14793("com.android.settings.connecteddevice.usb.UsbModeChooserActivity");
        this.f14239.m14793("com.android.settings.Settings$SoundSettingsActivity");
        this.f14239.m14793("com.android.settings.Settings$StatusActivity");
        this.f14239.m14793("com.android.settings.RegulatoryInfoDisplayActivity");
        this.f14239.m14793("com.android.settings.Settings$ImeiInformationActivity");
        this.f14239.m14793("com.android.settings.Settings$SimStatusActivity");
        this.f14239.m14793("com.android.settings.bluetooth.BluetoothPairingDialog");
        this.f14239.m14793("com.android.settings.deviceinfo.SimStatus");
        this.f14239.m14793("com.android.settings.deviceinfo.ImeiInformation");
        this.f14239.m14793("com.android.settings.Settings$BluetoothSettingsActivity");
        this.f14239.m14793("com.android.settings.deviceinfo.UsbConnModeChooserActivity");
        this.f14239.m14793("com.android.settings.Settings$WifiApSettingsActivity");
        this.f14239.m14793("com.android.settings.Settings$WifiSettingsActivity");
        this.f14239.m14793("com.android.settings.Settings$DeviceInfoSettingsActivity");
        this.f14239.m14793("com.android.bluetoothsettings.bluetooth.BluetoothPairingDialog");
        this.f14239.m14793("com.android.wifisettings.SubSettings");
        this.f14239.m14793("com.coloros.safecenter.verification.login.AccountActivity");
        this.f14239.m14793("com.android.incallui");
        this.f14239.m14793("com.android.settings.password.ConfirmLockPattern");
        this.f14239.m14793("com.android.settings.Settings$DeviceProfilesSettingsActivity");
        this.f14239.m14793("com.android.settings.password.ConfirmDeviceCredentialActivity");
        this.f14239.m14793("com.android.settings.Settings$WifiSettings2Activity");
        this.f14239.m14793("com.android.settings.bluetooth.RequestPermissionHelperActivity");
        this.f14239.m14793("com.android.settings.RemoteBugreportActivity");
        this.f14239.m14793("com.android.wifisettings.Settings$SmartWifiSettingsActivity");
        this.f14239.m14793("com.android.settings.Settings$ManageAppExternalSourcesActivity");
        this.f14239.m14793("com.android.settings.Settings$DeviceProfilesSettingsSSActivity");
        this.f14239.m14793("com.android.settings.Settings$WifiAddActivity");
        this.f14239.m14793("com.android.settings.wifi.WifiHelpActivity");
        this.f14239.m14793("com.android.settings.Settings$WifiPlusSettingsActivity");
        this.f14239.m14793("com.android.settings.Settings$ManageExternalSourcesActivity");
    }

    /* renamed from: 斃燸卺驼暲各撟嫺眧樬硱 */
    public final boolean m25911(String str) {
        return this.f23366.m23901(str);
    }

    /* renamed from: 旞莍癡 */
    public abstract void mo25883(@NonNull String str);

    /* renamed from: 朽劔蚁灋嵿齩鶴琓麃沼瀙缹 */
    public final ActivityInfo m25912() {
        Intent intent = new Intent("android.intent.action.DIAL");
        intent.setData(Uri.parse("tel:10086"));
        List<ResolveInfo> queryIntentActivities = this.f14238.getPackageManager().queryIntentActivities(intent, 0);
        if (queryIntentActivities.isEmpty() || queryIntentActivities.get(0).activityInfo == null) {
            return null;
        }
        return queryIntentActivities.get(0).activityInfo;
    }

    /* renamed from: 杹藗瀶姙笻件稚嵅蔂 */
    public final boolean m25913(String str) {
        if (ObjectUtils.equals(str, this.f14238.getPackageName())) {
            return true;
        }
        return !m25928(this.f23368.m23902(), str) && m25928(this.f23365.m23902(), str);
    }

    /* renamed from: 枩棥钰蕎睨領喀镎遣跄 */
    public final void m25914() {
        this.f23362.m14793("com.miui.voiceassist");
        this.f23362.m14793("com.heytap.speechassist");
        this.f23362.m14793("com.coloros.speechassist");
        this.f23362.m14793("com.oppo.speechassist");
        this.f23362.m14793("com.vivo.agent");
        this.f23362.m14793("com.vivo.puresearch");
        this.f23362.m14793("com.huawei.vassistant");
        this.f23362.m14793("com.xiaomi.xiaoailite");
    }

    /* renamed from: 櫓昛刓叡賜 */
    public final void m25915() {
        try {
            ActivityInfo m25923 = m25923();
            if (m25923 != null) {
                mo25883(m25923.packageName);
            }
            Intent intent = new Intent("android.intent.action.DIAL");
            intent.setData(Uri.parse("tel:10086"));
            Iterator<ResolveInfo> it = m25919(intent).iterator();
            while (it.hasNext()) {
                String str = it.next().activityInfo.packageName;
                if (m25932(str)) {
                    mo25883(str);
                }
            }
            Intent intent2 = new Intent("android.intent.action.PICK");
            intent2.addCategory("android.intent.category.DEFAULT");
            intent2.setType("vnd.android.cursor.dir/phone_v2");
            Iterator<ResolveInfo> it2 = m25919(intent2).iterator();
            while (it2.hasNext()) {
                String str2 = it2.next().activityInfo.packageName;
                if (m25932(str2)) {
                    mo25883(str2);
                }
            }
            m15538(new Intent("android.intent.action.SENDTO"));
            m15538(new Intent("android.intent.action.CREATE_SHORTCUT"));
            m15538(new Intent("android.intent.action.CHOOSER"));
            m15538(new Intent("android.settings.WIFI_SETTINGS"));
            m15538(new Intent("android.settings.DATA_ROAMING_SETTINGS"));
            m15538(new Intent("android.settings.BLUETOOTH_SETTINGS"));
            m15538(new Intent("android.settings.NETWORK_OPERATOR_SETTINGS"));
            m15538(new Intent("android.settings.DEVICE_INFO_SETTINGS"));
            m15538(new Intent("android.settings.LOCALE_SETTINGS"));
            m15538(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
            m25931(new Intent("android.settings.ACCESSIBILITY_SETTINGS"));
            Intent intent3 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent3.setData(Uri.fromParts("package", this.f14238.getPackageName(), null));
            m25931(intent3);
            m25904(intent3);
            m25931(new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS"));
            m25931(new Intent("android.settings.APPLICATION_SETTINGS"));
            int i = Build.VERSION.SDK_INT;
            if (i >= 31) {
                m25931(new Intent("android.settings.APP_OPEN_BY_DEFAULT_SETTINGS"));
            }
            if (i >= 29) {
                m25931(new Intent("android.settings.action.APP_USAGE_SETTINGS"));
            }
            if (i >= 22) {
                m25931(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
            }
            if (i >= 30) {
                m25931(new Intent("android.settings.NOTIFICATION_LISTENER_DETAIL_SETTINGS"));
            }
            if (i >= 22) {
                m25931(new Intent("android.settings.BATTERY_SAVER_SETTINGS"));
            }
            m25931(new Intent("android.settings.DATE_SETTINGS"));
            m25931(new Intent("android.settings.HOME_SETTINGS"));
            m25931(new Intent("android.settings.MANAGE_ALL_APPLICATIONS_SETTINGS"));
            if (i >= 24) {
                m25931(new Intent("android.settings.MANAGE_DEFAULT_APPS_SETTINGS"));
            }
            m25931(new Intent("android.settings.MANAGE_APPLICATIONS_SETTINGS"));
            if (i >= 23) {
                m25931(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION"));
            }
            m25931(new Intent("android.settings.SECURITY_SETTINGS"));
            m25931(new Intent("android.intent.action.MANAGE_DEFAULT_APP"));
            if (i >= 24) {
                m25931(new Intent("android.settings.VPN_SETTINGS"));
            }
            m15541(new Intent("android.settings.WIFI_DPP_ENROLLEE_QR_CODE_SCANNER"));
            m15541(new Intent("android.bluetooth.devicepicker.action.LAUNCH"));
            m15541(new Intent("android.settings.APN_SETTINGS"));
            m15541(new Intent("android.settings.WIFI_IP_SETTINGS"));
            m15541(new Intent("android.settings.WIRELESS_SETTINGS"));
            m15541(new Intent("android.settings.AIRPLANE_MODE_SETTINGS"));
            m15541(new Intent("android.settings.NFC_SETTINGS"));
            m15541(new Intent("android.settings.INPUT_METHOD_SETTINGS"));
            m15541(new Intent("android.settings.INPUT_METHOD_SUBTYPE_SETTINGS"));
            m15541(new Intent("android.settings.SOUND_SETTINGS"));
            m15541(new Intent("android.settings.DISPLAY_SETTINGS"));
            m15541(new Intent("android.settings.DEVICE_INFO_SETTINGS"));
            m15541(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
            m15541(new Intent("android.settings.SECURITY_SETTINGS"));
            m15541(new Intent("android.settings.PRIVACY_SETTINGS"));
            m15541(new Intent("android.settings.INTERNAL_STORAGE_SETTINGS"));
            m15541(new Intent("com.android.net.wifi.SETUP_WIFI_NETWORK"));
            m15541(new Intent("android.net.wifi.PICK_WIFI_NETWORK"));
            m15541(new Intent("android.intent.action.CREATE_SHORTCUT"));
            m15541(new Intent("com.android.settings.DISPLAY_SETTINGS"));
            m15541(new Intent("android.settings.LICENSE"));
            m15541(new Intent("android.settings.SAFETY"));
            m15541(new Intent("android.credentials.UNLOCK"));
            m15541(new Intent("android.app.action.SET_NEW_PASSWORD"));
            m15541(new Intent("android.settings.STORAGE_USB_SETTINGS"));
            m15541(new Intent("com.android.settings.STORAGE_USB_SETTINGS"));
            m15541(new Intent("android.bluetooth.device.action.PAIRING_REQUEST"));
            m15541(new Intent("android.bluetooth.adapter.action.REQUEST_DISCOVERABLE"));
            m15541(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"));
            m15541(new Intent("android.net.wifi.action.REQUEST_SCAN_ALWAYS_AVAILABLE"));
            m15541(new Intent("android.bluetooth.device.action.PAIRING_REQUEST"));
            m15541(new Intent("android.bluetooth.device.action.CONNECTION_ACCESS_REQUEST"));
            m15541(new Intent("android.intent.action.PICK_ACTIVITY"));
            m15541(new Intent("android.settings.NFCSHARING_SETTINGS"));
            m15541(new Intent("android.settings.WIFI_DISPLAY_SETTINGS"));
            m15541(new Intent("android.appwidget.action.APPWIDGET_PICK"));
            m15541(new Intent("android.appwidget.action.APPWIDGET_BIND"));
            m15541(new Intent("android.appwidget.action.KEYGUARD_APPWIDGET_PICK"));
            m15541(new Intent("android.settings.ACCOUNT_SYNC_SETTINGS"));
            m15541(new Intent("android.settings.SYNC_SETTINGS"));
            m15541(new Intent("android.settings.ADD_ACCOUNT_SETTINGS"));
            m15541(new Intent("com.android.launcher3.dragndrop.AddItemActivity"));
            m15541(new Intent("com.android.settings.AllowBindAppWidgetActivity"));
            m25921("com.android.settings.Settings$WifiP2pSettingsActivity");
            mo25883(this.f14238.getPackageName());
            mo25883("com.android.phone");
            mo25883("com.android.server.telecom");
            mo25883("android");
            mo25883("com.android.incallui");
            mo25883("com.android.packageinstaller");
            mo25883("com.android.permissioncontroller");
            mo25883("com.google.android.packageinstaller");
            mo25883("com.android.systemui");
            mo25883("com.google.android.gms");
            mo25883("com.android.htmlviewer");
            mo25883("com.google.android.gsf");
            mo25883("com.android.bluetooth");
            mo25883("com.android.settings.intelligence");
            mo25883("com.google.android.documentsui");
            if (RomUtils.isVivo()) {
                mo25883("com.bbk.account");
                mo25883("com.vivo.sdkplugin");
                mo25883("com.vivo.vtouch");
                mo25883("com.vivo.smartshot");
                mo25883("com.vivo.scanner");
                mo25883("com.bbk.cloud");
                m25921("com.vivo.settings.secret.PasswordActivity");
                m25921("com.bbk.SuperPowerSave.SuperPowerSaveActivity");
                m25921("com.vivo.upslide.qs.switcher.SwitcherOrderActivity");
            } else if (RomUtils.isXiaomi()) {
                mo25883("com.xiaomi.account");
                mo25883("com.miui.packageinstaller");
                mo25883("com.miui.screenshot");
                mo25883("miui.systemui.plugin");
                mo25883("com.miui.systemAdSolution");
                mo25883("com.lbe.security.miui");
                mo25883("com.milink.service");
                m25921("com.miui.permcenter.privacymanager.SpecialPermissionInterceptActivity");
                m25921("com.miui.applicationlock.ConfirmAccessControl");
                m25921("com.miui.optimizecenter.deepclean.DeepCleanActivity");
                m25921("com.miui.cleanmaster.LowMemoryIntentDispatchActivity");
                m25921("com.miui.optimizecenter.deepclean.video.VideoListActivity");
                m25921("com.miui.optimizecenter.similarimage.ImageCategoryListActivity");
                m25921("com.miui.optimizecenter.deepclean.largefile.LargeFilesActivity");
                m25921("com.miui.optimizecenter.appcleaner.AppCleanerActivity");
            } else if (RomUtils.isHuawei()) {
                mo25883("com.huawei.secime");
                mo25883("com.huawei.hwid");
                mo25883("com.huawei.gamebox");
                mo25883("com.huawei.gameassistant");
                mo25883("com.huawei.tips");
                mo25883("com.huawei.android.wfdft");
                mo25883("com.huawei.hwvoipservice");
                mo25883("com.huawei.android.tips");
                mo25883("com.huawei.mediacontroller");
                mo25883("com.huawei.controlcenter");
                mo25883("com.huawei.intelligent");
                m25921("com.huawei.securitycenter.applock.password.AuthLaunchLockedAppActivity");
                m25921("com.huawei.appgallery.systeminstalldistservice.ui.activity.InstallDistActivity");
                Intent intent4 = new Intent();
                intent4.setAction("com.huawei.android.internal.app.RESOLVER");
                Iterator<ResolveInfo> it3 = this.f14238.getPackageManager().queryBroadcastReceivers(intent4, 0).iterator();
                while (it3.hasNext()) {
                    mo25883(it3.next().activityInfo.packageName);
                }
                m25921("com.huawei.smartnetwork.DataControllActivity");
            } else if (Tools.m22455()) {
                mo25883("com.oneplus.account");
                mo25883("com.coloros.wirelesssettings");
                mo25883("com.coloros.oshare");
                mo25883("com.coloros.screenshot");
                mo25883("com.coloros.gamespaceui");
                mo25883("com.heytap.mcs");
                mo25883("com.coloros.directui");
                mo25883("com.coloros.ocrscanner");
                m25921("com.coloros.safecenter.privacy.view.password.AppUnlockPasswordActivity");
            } else if (RomUtils.isMeizu()) {
                mo25883("com.meizu.media.video");
            }
            Intent intent5 = new Intent("android.intent.action.VIEW");
            intent5.addCategory("android.intent.category.DEFAULT");
            intent5.addCategory("android.intent.category.BROWSABLE");
//            intent5.setDataAndType(Uri.parse(JPushConstants.HTTP_PRE), null);
            try {
                Iterator<ResolveInfo> it4 = this.f14238.getPackageManager().queryIntentActivities(intent5, 32).iterator();
                while (it4.hasNext()) {
                    String str3 = it4.next().activityInfo.packageName;
                    if (m25932(str3)) {
                        m25917(str3);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            InputMethodManager inputMethodManager = (InputMethodManager) this.f14238.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                Iterator<InputMethodInfo> it5 = inputMethodManager.getInputMethodList().iterator();
                while (it5.hasNext()) {
                    mo25883(it5.next().getPackageName());
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* renamed from: 灞酞輀攼嵞漁綬迹 */
    public void m25916(String str) {
//        this.f23367.mo23319(str);
    }

    /* renamed from: 癎躑選熁 */
    public final void m25917(String str) {
        this.f23365.m14792(str);
    }

    /* renamed from: 睳堋弗粥辊惶 */
//    public boolean m25918(String str) {
//        if (str == null) {
//            return false;
//        }
//        return this.f23367.m25902(str);
//    }

    /* renamed from: 瞙餃莴埲 */
    public final List<ResolveInfo> m25919(Intent intent) {
        return this.f14238.getPackageManager().queryIntentActivities(intent, 0);
    }

    /* renamed from: 礱咄頑 */
    public final void m25920() {
        if (RomUtils.isVivo()) {
            m25916("com.iqoo.powersaving");
            m25916("com.vivo.agent");
            m25916("com.vivo.puresearch");
            m25916("com.vivo.vivoconsole");
            m25916("com.vivo.childrenmode");
            m25916("com.bbk.appstore");
            m25916("com.android.bbk.lockscreen3");
            m25916("com.iqoo.secure");
            m25916("com.vivo.globalsearch");
            m25916("com.vivo.hybrid");
            m25916("com.bbk.SuperPowerSave");
            m25916("com.bbk.launcheR");
            m25916("com.vivo.childrenmode");
            m25916("com.vivo.easyshare");
            m25916("com.vivo.hybrid");
            m25916("com.vivo.findphone");
            m25916("com.vivo.globalsearch");
            m25916("com.android.bbk.lockscreen3");
            m25916("com.vivo.familycare");
        } else if (RomUtils.isXiaomi()) {
            m25916("com.miui.securitycenter");
            m25916("com.xiaomi.market");
            m25916("com.miui.voiceassist");
            m25916("com.miui.cleanmaster");
            m25916("com.miui.hybrid");
            m25916("com.xiaomi.xiaoailite");
        } else if (!RomUtils.isOneplus() && !RomUtils.isOppo()) {
            if (RomUtils.isHuawei()) {
                m25916("com.huawei.vassistant");
                m25916("com.huawei.appmarket");
                m25916("com.huawei.android.findmyphone");
                m25916("com.huawei.hidisk");
                m25916("com.huawei.systemmanager");
                m25916("com.huawei.fastapp");
            } else if (RomUtils.isSamsung()) {
                m25916("com.wingui.safemgr");
                m25916("com.sec.android.app.samsungapps");
            }
        } else {
            m25916("com.coloros.personalassistant");
            m25916("com.heytap.speechassist");
            m25916("com.coloros.speechassist");
            m25916("com.coloros.safecenter");
            m25916("com.oppo.speechassist");
            m25916("com.oplus.securitypermission");
            m25916("com.oppo.market");
            m25916("com.coloros.backuprestore");
            m25916("com.coloros.onekeylockscreen");
            m25916("com.coloros.findphone.client2");
            m25916("com.coloros.findphone.client");
            m25916("com.coloros.shortcuts");
            m25916("com.oppo.quicksearchbox");
            m25916("com.coloros.speechassist");
            m25916("com.coloros.filemanager");
            m25916("com.coloros.phonemanager");
        }
        m25916("com.coolapk.market");
        m25916("com.broaddeep.safe.childrennetguard");
        m25916("com.gwchina.lssw.child");
        m25916("cn.imyfone.famiguard.child");
        m25916("com.qihoo360.mobilesafe");
        m25916("com.tencent.qqpimsecure");
        m25916("com.qihoo.cleandroid_cn");
        m25916("com.leapteen.child");
        m25916("com.coloros.familyguard");
        m25916("com.tencent.android.qqdownloader");
        m25916("com.qihoo.appstore");
        m25916("com.baidu.appsearch");
        m25916("com.android.vending");
        m25916("com.meizu.mstore");
        m25916("com.wandoujia.phoenix2");
        m25916("com.dragon.android.pandaspace");
        m25916("com.pp.assistant");
        m25916("com.sogou.androidtool");
        m25916("com.lenovo.leos.appstore");
        m25916("zte.com.market");
        m25916("com.hiapk.marketpho");
        m25916("com.yingyonghui.market");
        m25916("com.mappn.gfan");
        m25916("cn.goapk.market");
        m25916("com.yulong.android.coolmart");
        m25916("com.coolapk.market");
        m25916("com.gionee.aora.market");
        m25916("com.hicloud.android.clone");
        m25916("com.nearme.instant.platform");
        m25916("com.android.fileexplorer");
        m25916("com.heytap.quicksearchbox");
        m25916("com.meizu.flyme.directservice");
        m25916("com.zui.findmyphoneweb");
        m25916("com.heytap.speechassist");
        m25916("com.heytap.market");
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("market://details?id=" + this.f14238.getPackageName()));
        try {
            for (ResolveInfo resolveInfo : this.f14238.getPackageManager().queryIntentActivities(intent, 0)) {
                if (m25932(resolveInfo.activityInfo.packageName)) {
                    m25916(resolveInfo.activityInfo.packageName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: 祴嚚橺谋肬鬧舘 */
    public final void m25921(String str) {
        this.f14239.m14793(str);
    }

    /* renamed from: 綏牽躵糽稰烳俠垳襨捈桏鷋 */
    public final void m25922() {
        this.f23364.m14793("com.android.packageinstaller");
        Iterator<ResolveInfo> it = this.f14238.getPackageManager().queryIntentActivities(new Intent("android.intent.action.INSTALL_PACKAGE"), 0).iterator();
        while (it.hasNext()) {
            this.f23364.m14793(it.next().activityInfo.packageName);
        }
    }

    /* renamed from: 綩私 */
    public String m15539() {
        return (RomUtils.isOppo() || Tools.m22497()) ? "com.oppo.launcher" : RomUtils.isHuawei() ? "com.huawei.android.launcher" : RomUtils.isXiaomi() ? "com.miui.home" : RomUtils.isVivo() ? "com.bbk.launcher2" : RomUtils.isOneplus() ? "net.oneplus.launcher" : RomUtils.isMeizu() ? "com.meizu.flyme.launcher" : RomUtils.isSamsung() ? "com.sec.android.app.launcher" : Tools.m22457() ? "com.hihonor.android.launcher" : "";
    }

    /* renamed from: 纩慐 */
    public boolean m15540(String str) {
        return m25928(this.f23360.m23902(), str);
    }

    /* renamed from: 耣怳匮色紝参凵蛴纆勚躄 */
    public ActivityInfo m25923() {
        List<ResolveInfo> list;
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setType("vnd.android-dir/mms-sms");
        List<ResolveInfo> queryIntentActivities = this.f14238.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : queryIntentActivities) {
            if (m25932(resolveInfo.activityInfo.packageName)) {
                return resolveInfo.activityInfo;
            }
        }
        if (queryIntentActivities.isEmpty()) {
            Intent intent2 = new Intent("android.intent.action.SENDTO");
            intent2.setData(Uri.parse("smsto:" + Uri.encode("10086")));
            list = this.f14238.getPackageManager().queryIntentActivities(intent2, 0);
        } else {
            list = null;
        }
        if (list == null || list.isEmpty() || list.get(0).activityInfo == null) {
            return null;
        }
        return list.get(0).activityInfo;
    }

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
    public final void m25924(String str) {
        this.f23366.m14793(str);
    }

    /* renamed from: 蝸餺閃喍 */
    public String m25925() {
        String m15539 = m15539();
        if (!ObjectUtils.isEmpty((CharSequence) m15539)) {
            return m15539;
        }
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        ResolveInfo resolveActivity = this.f14238.getPackageManager().resolveActivity(intent, 0);
        ActivityInfo activityInfo = resolveActivity.activityInfo;
        if (activityInfo == null) {
            return m15539();
        }
        if (activityInfo.packageName.equals("android")) {
            return m15539();
        }
        return resolveActivity.activityInfo.packageName;
    }

    /* renamed from: 辒迳圄袡皪郞箟 */
    public boolean m25926(String str) {
        if (ObjectUtils.equals(str, this.f14238.getPackageName()) || m25930().contains(str)) {
            return false;
        }
        if (ObjectUtils.equals(m25925(), str)) {
            return true;
        }
        if (this.f23368.m23900()) {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.HOME");
            Iterator<ResolveInfo> it = m25919(intent).iterator();
            while (it.hasNext()) {
                String str2 = it.next().activityInfo.packageName;
                if (!ObjectUtils.equals(str2, this.f14238.getPackageName())) {
                    this.f23368.m14793(str2);
                } else {
                    m25916(str2);
                }
            }
        }
        return m25928(this.f23368.m23902(), str);
    }

    /* renamed from: 酸恚辰橔纋黺 */
    public final boolean m25927(CharSequence charSequence) {
        if (charSequence == null) {
            return false;
        }
        return m25928(this.f23362.m23902(), charSequence.toString());
    }

    /* renamed from: 鑭撇糁綖浓緗轟鱼萟磿焈 */
    public <T> boolean m25928(Set<T> set, T t) {
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            if (ObjectUtils.equals(it.next(), t)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: 销薞醣戔攖餗 */
    public final boolean m25929(String str) {
        return this.f23364.m23901(str);
    }

    /* renamed from: 镐藻 */
    public final void m15541(Intent intent) {
        for (ResolveInfo resolveInfo : this.f14238.getPackageManager().queryIntentActivities(intent, 0)) {
            if (m25932(resolveInfo.activityInfo.packageName)) {
                String str = resolveInfo.activityInfo.name;
                if (!this.f23366.m23901(str)) {
                    this.f14239.m14793(str);
                }
                if (!TextUtils.isEmpty(resolveInfo.activityInfo.targetActivity) && !this.f23366.m23901(str)) {
                    this.f14239.m14793(str);
                }
            }
        }
    }

    /* renamed from: 陟瓠魒踱褢植螉嚜 */
    public final Set<String> m25930() {
        if (!this.f14237.m23900()) {
            return this.f14237.m23902();
        }
        try {
            for (ResolveInfo resolveInfo : this.f14238.getPackageManager().queryIntentActivities(new Intent("android.settings.SETTINGS"), 0)) {
                this.f14237.m14793(resolveInfo.activityInfo.packageName);
                this.f23365.m14792(resolveInfo.activityInfo.packageName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogHelper.m27986().m16303("拦截", "获取本机系统设置相关包名失败:" + e.getMessage());
        }
        if (this.f14237.m23900()) {
            this.f14237.m14793("com.android.settings");
        }
        return this.f14237.m23902();
    }

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
    public final void m25931(Intent intent) {
        for (ResolveInfo resolveInfo : this.f14238.getPackageManager().queryIntentActivities(intent, 0)) {
            if (m25932(resolveInfo.activityInfo.packageName)) {
                this.f23366.m14793(resolveInfo.activityInfo.name);
                String str = resolveInfo.activityInfo.targetActivity;
                if (!TextUtils.isEmpty(str)) {
                    this.f23366.m14793(str);
                }
            }
        }
    }

    /* renamed from: 鞲冇 */
    public final void m15542() {
        this.f23360.m14793("com.oplus.securitypermission");
        this.f23360.m14793("com.iqoo.secure");
        this.f23360.m14793("com.miui.securitycenter");
        this.f23360.m14793("com.huawei.systemmanager");
        this.f23360.m14793("com.coloros.safecenter");
    }

    /* renamed from: 韐爮幀悖罤噩钼遑杯盇 */
    public boolean m25932(String str) {
        PackageManager packageManager = null;
        if (str == null) {
            return false;
        }
        Boolean bool = this.f23363.get(str);
        if (bool != null) {
            return bool.booleanValue();
        }
        try {
            packageManager = this.f14238.getPackageManager();

            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 0);
                boolean z = true;
                if (applicationInfo == null || (applicationInfo.flags & 1) == 0) {
                    z = false;
                }
                this.f23363.put(str, Boolean.valueOf(z));
                return z;
            }
        } catch (Exception unused) {
        }
        this.f23363.put(str, Boolean.FALSE);
        return false;
    }

    /* renamed from: 駭鑈趘薑衈講堍趃軏 */
    public void m25933(String str) {
//        this.f23367.remove(str);
    }
}



