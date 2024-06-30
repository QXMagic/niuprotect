package com.niu.protect.accessibility.auto.service.tmp;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import com.blankj.utilcode.util.AppUtils;
import com.niu.protect.accessibility.auto.service.App;
import com.niu.protect.accessibility.auto.service.Tools;
import com.niu.protect.core.Constants;

import java.util.Set;

public class SecureUtils {
        public static DevicePolicyManager m24088() {
            return (DevicePolicyManager) Constants.MainInstance.getContext().getSystemService(Context.DEVICE_POLICY_SERVICE);
        }

    public static void m24081(String[] strArr, boolean z) {
        m24088().setPackagesSuspended(m24082(), strArr, z);
    }
    public static boolean m14880(String str) {
        return m24093() && Build.VERSION.SDK_INT >= 24 && m24088().getUserRestrictions(m24082()).get(str) != null;
    }
    public static void m24083() {
        if (m24093()) {
            try {
                int i = Build.VERSION.SDK_INT;
                if (i >= 29) {
                    m24088().setAlwaysOnVpnPackage(m24082(), null, false, null);
                } else if (i >= 24) {
                    m24088().setAlwaysOnVpnPackage(m24082(), null, false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void m24089(boolean z) {
        if (m24093()) {
            try {
                if (z) {
                    m24078("no_add_user");
                } else {
                    m14876("no_add_user");
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }


    public static boolean m24093() {
            try {
                String appPackageName = Constants.APPLICATION_ID;
                DevicePolicyManager m24088 = m24088();
                if (m24088.isDeviceOwnerApp(appPackageName)) {
                    return true;
                }
                return m24088.isProfileOwnerApp(appPackageName);
            } catch (Throwable unused) {
                return false;
            }
        }

        public static boolean m24077() {
            try {
                return m24088().isDeviceOwnerApp(Constants.APPLICATION_ID);
            } catch (Throwable unused) {
                return false;
            }
        }
        public static boolean m24084() {
            try {
                return m24088().isProfileOwnerApp(Constants.MainInstance.getContext().getPackageName());
            } catch (Throwable unused) {
                return false;
            }
        }




        public static boolean m24086() {
            try {
                return m24088().isAdminActive(m24082());
            } catch (Throwable unused) {
                return false;
            }
        }
        public static ComponentName m24082() {

            return Tools.m22771(Constants.MainInstance.getContext());
        }
        public static void m24078(String str) {
            m24088().addUserRestriction(m24082(), str);
        }
        public static void m14876(String str) {
            m24088().clearUserRestriction(m24082(), str);
        }

        private static void m14877(String str){

        }

        public static void m24095(boolean z) {
            try {
                if (z) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        m24078("no_safe_boot");
                    }
                } else if (Build.VERSION.SDK_INT >= 23) {
                    m14876("no_safe_boot");
                }
                m14877("setSafeBootDisallow disallow=" + z);
            } catch (Exception e) {
                m14877("setSafeBootDisallow Exception disallow=" + e);
            }

        }

    public static boolean m24091(Context context) {
        return Build.VERSION.SDK_INT >= 23 && context.checkSelfPermission("android.permission.WRITE_SECURE_SETTINGS") == PackageManager.PERMISSION_GRANTED;
    }
    public static void m14879(int i) {
        try {
            //TODO ERROR
            Settings.Secure.putString(App.mContext.getContentResolver(), "enabled_accessibility_services",
                    Settings.Secure.getString(App.mContext.getContentResolver(),
                            "enabled_accessibility_services") + ":" + Constants.APPLICATION_ID
                            + "/com.zlfc.child.mvp.service.accessibility.AccessibilityHelperService");
            Settings.Secure.putInt(App.mContext.getContentResolver(), "accessibility_enabled", i);
        } catch (Throwable unused) {
            App.showToast("开启无障碍失败");
        }
    }

    public static void m14878(boolean z) {
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                m24088().setStatusBarDisabled(m24082(), z);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void m24096(Set<String> set) {
        if (m24093()) {
            try {
                int i = Build.VERSION.SDK_INT;
                if (i >= 29) {
                    m24088().setAlwaysOnVpnPackage(m24082(), AppUtils.getAppPackageName(), false, set);
                } else if (i >= 24) {
                    m24088().setAlwaysOnVpnPackage(m24082(), AppUtils.getAppPackageName(), false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void m24094(boolean z) {
        if (m24077()) {
            try {
                if (z) {
                    m24078("no_factory_reset");
                } else {
                    m14876("no_factory_reset");
                }
            } catch (Exception e) {
                m14877("setFactoryResetDisallow disallow=" + z + ",e = " + e);
            }
        }
    }

    public static void m24092(String str, String str2, int i) {
        try {
            if (m24088().getPermissionGrantState(m24082(), str, str2) != DevicePolicyManager.PERMISSION_GRANT_STATE_GRANTED) {
                m24088().setPermissionGrantState(m24082(), str, str2, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void m24098(String str, boolean z) {
        if (m24093()) {
            m24088().setUninstallBlocked(m24082(), str, z);
        }
    }
    public static void m24097() {
        try {
            m24088().clearDeviceOwnerApp(App.mContext.getPackageName());
            m14877("clearDeviceOwnerApp success");
        } catch (Throwable th) {
            m14877("clearDeviceOwnerApp e = " + th);
        }
    }
    public static void m24085() {
        try {
            m24088().clearProfileOwner(m24082());
            m14877("clearDeviceOwnerApp success");
        } catch (Throwable th) {
            m14877("clearDeviceOwnerApp e = " + th);
        }
    }

    public static void m24090() {
        try {
            m24088().removeActiveAdmin(m24082());
            m14877("clearActiveAdmin success");
        } catch (Throwable th) {
            m14877("clearActiveAdmin e = " + th);
        }
    }


    public static void m24087() {
        if (m24077()) {
            m24097();
        }
        if (m24084()) {
            m24085();
        }
        if (m24086()) {
            m24090();
        }
    }


}