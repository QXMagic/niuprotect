package accessibility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.VpnService;
import android.os.Build;
import android.view.accessibility.AccessibilityNodeInfo;

import com.blankj.utilcode.util.RomUtils;
import com.hjq.permissions.XXPermissions;

import accessibility.lib.AccessibilityUtils;
import accessibility.lib.DataKV;

public class PermissionAutoManager {
    public static boolean m22513(Activity activity, PermissionBean permissionBean, boolean z) {
        int id = permissionBean.getId();
        if (id == 1) {
            Tools.m22482(activity);
        } else if (id != 2) {
            if (id == 3) {
                Tools.m22469(activity);
            } else {
                if (id == 4) {
                    return Tools.m14276(activity);
                }
                if (id == 8) {
                    Tools.m22493(activity);
                } else if (id == 12) {
                    Tools.m22476(activity);
                } else if (id == 40) {
                    Tools.m22448(activity);
                } else if (id != 41) {
                    switch (id) {
                        case 14:
                            return Tools.m22491(activity);
                        case 15:
                            return Tools.m22501(activity);
                        case 16:
                            Tools.m14280(activity);
                            break;
                        case 17:
                            return Tools.m22481(activity);
                        case 18:
                            Tools.m22480();
                            break;
                        case 19:
                            Tools.m22463(activity);
                            break;
                        case 20:
//                            return Tools.m22478(activity);
                        case 21:
                            if (RomUtils.isXiaomi()) {
                                Tools.m22467(activity);
                                break;
                            } else {
                                AccessibilityHelperService.m19966();
                                break;
                            }
                        case 22:
//                            Tools.m22468(activity);
                            break;
                        case 23:
//                            ContextCompat.startForegroundService(activity, new Intent(activity, (Class<?>) ScreenOpenService.class));
                            break;
                        case 24:
                            Tools.m22511(activity);
                            break;
                        case 25:
                            if (Tools.m22483()) {
                                Tools.m22510(activity);
                                return true;
                            }
                            return Tools.m22466(activity);
                        default:
                            switch (id) {
                                case 28:
                                    Tools.m22510(activity);
                                    break;
                                case 29:
                                    Tools.m22487(activity);
                                    break;
                                case 30:
                                    int i = Build.VERSION.SDK_INT;
                                    if ((i >= 29 && RomUtils.isVivo()) || (i >= 30 && (Tools.m22457() || RomUtils.isSamsung() || Tools.m22455()))) {
                                        Tools.m22465(activity, 10020, activity.getPackageName());
                                        break;
                                    } else {
                                        Tools.m22461(activity);
                                        break;
                                    }
                                default:
                                    switch (id) {
                                        case 33:
                                            Tools.m22496(activity);
                                            break;
                                        case 34:
                                            Tools.m22465(activity, 10020, activity.getPackageName());
                                            break;
                                    }
                                case 31:
                                    Tools.m22510(activity);
                                    break;
                            }
                    }
                } else {
                    Intent prepare = VpnService.prepare(activity);
                    if (prepare != null) {
                        activity.startActivityForResult(prepare, 1985);
                        m22515(41, 500L);
                    }
                }
            }
        } else if (!z) {
            Tools.m22450(activity);
        } else if (!Tools.m22455() && ((!RomUtils.isXiaomi() || Tools.version() < 14.0f) && !Tools.isXiaomi())) {
            Tools.m22450(activity);
        } else {
            Tools.m22453(activity);
        }
        return true;
    }

    public static void m22515(int i, long j) {
        m14282(new RunnableC6160(i), j);
    }
    public static void m14282(Runnable runnable, long j) {
        AccessibilityUtils.m24452().postDelayed(runnable, j);
    }




    //m22512
    public static boolean checkIndex(Context context, int i) {
        boolean m22451;
        if (i == 1) {
            return Tools.isOwner(context);
        }
        if (i == 2) {
            return Tools.canDrawOverlays(context);
        }
        if (i != 3) {
            if (i == 4) {
                return Tools.m22479(context);
            }
            if (i == 8) {
                return Tools.m14279(context);
            }
            if (i != 12) {
                if (i == 15) {
                    return Tools.m22464(context);
                }
                if (i == 21) {
                    if (Tools.m22455()) {
                        //mmkv set
                        return DataKV.m25858("oppo_lock_recent", false);
                    }
                    return false;
                }
                if (i != 23) {
                    if (i == 18) {
                        m22451 = Tools.adbEnable(context);
                    } else {
                        if (i == 19) {
                            return Tools.m22470(context);
                        }
                        if (i == 30) {
                            return Tools.m22488(context);
                        }
                        if (i != 31) {
                            if (i == 40) {
                                return XXPermissions.hasPermission(context, "android.permission.REQUEST_INSTALL_PACKAGES");
                            }
                            if (i != 41) {
                                return false;
                            }
//                            if (LocalVpnService.f19400) {
//                                return true;
//                            }
//                            if (VpnService.prepare(context) != null) {
//                                return false;
//                            }
//                            context.startService(new Intent(context, (Class<?>) LocalVpnService.class));
                            return true;
                        }
                        m22451 = Tools.m22473(context);
                    }
                    return !m22451;
                }
//                if (ScreenRecorder.f19342 == null) {
//                    return false;
//                }
            } else if (!Tools.m22495(context) || !AccessibilityHelperService.m19972()) {
                return false;
            }
        } else {
                return false;
        }
        return true;
    }

    public static AccessibilityNodeInfo m24454(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo m24454;
        if (accessibilityNodeInfo.isCheckable()) {
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child.isCheckable()) {
                return child;
            }
            if (child.getChildCount() > 0 && (m24454 = m24454(child)) != null) {
                return m24454;
            }
        }
        return null;
    }
    //m22514
    public static boolean setItem(int i) {
        XiaoMiPermission permission = (XiaoMiPermission) CommonPermission.getPermission();
        if (i == 1) {
            return permission.setAppManager();
        }
        if (i == 2) {
            return permission.mo16171();
        }
        if (i == 3) {
            return permission.mo27650();
        }
        if (i == 4) {
            return permission.mo27652();
        }
        if (i != 8) {
            if (i != 24 && i != 25) {
                switch (i) {
                    case 14:
                    case 16:
                        break;
                    case 15:
                        break;
                    case 17:
                        return permission.mo27705();
                    case 18:
                        return permission.m27706();
                    case 19:
                        return permission.mo27624();
                    case 20:
                        return permission.mo27708();
                    case 21:
                        return permission.mo27618();
                    default:
                        switch (i) {
                            case 28:
                                return permission.mo27703();
                            case 29:
                                return permission.mo27700();
                            case 30:
                                return permission.setAllowAll();
                            case 31:
                                return permission.mo27701();
                            default:
                                switch (i) {
                                    case 34:
                                        break;
                                    case 35:
                                        return permission.mo27647();
                                    case 36:
                                        return permission.mo27642();
                                    case 37:
                                    case 39:
                                        return permission.clickMiUI(i);
                                    case 38:
                                        return permission.openDebuger();
                                    case 40:
                                        return permission.mo16179();
                                    case 41:
                                        return permission.m27702();
                                    default:
                                        return false;
                                }
                        }
                }
                return permission.setPopWindow();
            }
            return permission.mo27643();
        }
        return permission.allowSee();
    }

    public static boolean m14283(int id) {
        return true;
    }
}
