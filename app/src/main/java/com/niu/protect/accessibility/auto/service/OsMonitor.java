package com.niu.protect.accessibility.auto.service;

import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.NonNull;

import java.util.Iterator;
import java.util.List;

public abstract class OsMonitor extends BaseAccessibilityMonitor implements InterfaceC6489 {
    /* renamed from: 刻槒唱镧詴 */
    public boolean mo22210(@NonNull AccessibilityEventImpl accessibilityEventImpl) {
        SafeScreenLockView m23580 = SafeScreenLockView.m23580();
        if (m23580.m25772()) {
            LogHelper.m27987("isShowing");
            return true;
        }
        if (ObjectUtils.isEmpty((CharSequence) accessibilityEventImpl.m24457())) {
            return false;
        }
        String m24457 = accessibilityEventImpl.m24457();
        String m24458 = accessibilityEventImpl.m24458();
        AppFilter m15534 = AppFilter.m15534(App.mContext);
        if (accessibilityEventImpl.m24459() == 32) {
            if (m15534.m25927(m24458)) {
                AccessibilityHelperService.m19968("语音助手拦截");
                AccessibilityHelperService.m19967();
                App.showToast("应用不被允许运行");
                return true;
            }
            if (!m15534.m25905(m24457) && !m15534.m25929(m24458)) {
                if (m15534.m25911(m24457)) {
                    if (m22258(accessibilityEventImpl)) {
                        ModelManager.m22768(null);
                        return true;
                    }
                    AccessibilityHelperService.m19968("isBlackPage非系统拦截");
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    AccessibilityHelperService.m19967();
                    return true;
                }
                if (!m15534.m15540(accessibilityEventImpl.m24458()) && m15534.m25918(accessibilityEventImpl.m24458())) {
                    AccessibilityHelperService.m19968("禁止App拦截");
                    AccessibilityHelperService.m19967();
                    return true;
                }
            } else {
                m14190(this.f12779.getRootInActiveWindow());
                return true;
            }
        }
        if (m15534.m25903(m24457)) {
            return true;
        }
        if (this.f12779.getRootInActiveWindow() == null) {
            return false;
        }
        if (accessibilityEventImpl.m24459() == 2) {
            if (m22256().contentEquals(m24458)) {
                return false;
            }
            if (accessibilityEventImpl.m24462() != null && accessibilityEventImpl.m24462().toString().contains(m22253())) {
                AccessibilityHelperService.m19968("App名称拦截");
                AccessibilityHelperService.m19967();
                m23580.mo23566();
                m23580.m23588("禁止操作", "该行为已被禁止", "code:-1");
                return true;
            }
            List<CharSequence> m15021 = accessibilityEventImpl.m15021();
            if (m15021 != null && m15021.contains(m22253())) {
                AccessibilityHelperService.m19968("App名称文字拦截");
                AccessibilityHelperService.m19967();
                m23580.mo23566();
                m23580.m23588("禁止操作", "该行为已被禁止", "code:-2");
                return true;
            }
        }
        return ((accessibilityEventImpl.m24459() == 32 || accessibilityEventImpl.m24459() == 2048 || accessibilityEventImpl.m24459() == 2 || accessibilityEventImpl.m24459() == 1) && !this.f12779.m19977("卸载").isEmpty() && m14190(accessibilityEventImpl.m24460())) || m15534.m25903(m24457) || m22223(accessibilityEventImpl) || m22224(accessibilityEventImpl) || m22222(accessibilityEventImpl) || m22226(accessibilityEventImpl) || m22227(accessibilityEventImpl);
    }

    /* renamed from: 彻薯铏螙憣欖愡鼭 */
    public final boolean m22222(AccessibilityEventImpl accessibilityEventImpl) {
        if (accessibilityEventImpl.m24459() == 32) {
            if (ObjectUtils.equals("com.android.systemui", accessibilityEventImpl.m15019())) {
                return false;
            }
            if (m22258(accessibilityEventImpl)) {
                if ("android.app.Dialog".contentEquals(accessibilityEventImpl.m15020())) {
                    return false;
                }
                AccessibilityNodeInfo m24460 = accessibilityEventImpl.m24460();
                if (m24460 != null && !m24460.findAccessibilityNodeInfosByText(m22253()).isEmpty()) {
                    AccessibilityHelperService.m19968("checkUninstall->长按前往设置拦截");
                    ModelManager.m22790(null);
                    return true;
                }
            }
            for (CharSequence charSequence : accessibilityEventImpl.m15021()) {
                if (charSequence != null && charSequence.toString().contains("恢复出厂设置")) {
                    AccessibilityHelperService.m19968("恢复出厂设置");
                    return true;
                }
            }
        }
        return (accessibilityEventImpl.m24459() == 32 || accessibilityEventImpl.m24459() == 2048) && m14190(this.f12779.m19976()) && AppFilter.m15534(this.f12779).m25929(accessibilityEventImpl.m15019().toString());
    }

    /* renamed from: 朽劔蚁灋嵿齩鶴琓麃沼瀙缹 */
    public final boolean m22223(AccessibilityEventImpl accessibilityEventImpl) {
        String charSequence = accessibilityEventImpl.m15019() != null ? accessibilityEventImpl.m15019().toString() : "";
        return AppFilter.m15534(this.f12779).m25930().contains(charSequence) && charSequence.equals("android.app.Dialog");
    }

    /* renamed from: 瞙餃莴埲 */
    public final boolean m22224(AccessibilityEventImpl accessibilityEventImpl) {
        if (accessibilityEventImpl.m15019() != null && accessibilityEventImpl.m24459() == 32 && m22258(accessibilityEventImpl) && this.f12779.getRootInActiveWindow() != null) {
            if (this.f12779.m13527("使用情况访问权限", "通知使用权") && this.f12779.m13527(m22253())) {
                AccessibilityHelperService.m19968("使用情况访问权限，通知使用权返回拦截");
                return true;
            }
            if (ObjectUtils.equals("com.android.settings.Settings.NotificationAccessSettingsActivity", accessibilityEventImpl.m15020())) {
                AccessibilityHelperService.m19968("通知Activity返回拦截");
                return true;
            }
            Iterator<CharSequence> it = accessibilityEventImpl.m15021().iterator();
            while (it.hasNext()) {
                if (ObjectUtils.equals("开发者选项", it.next())) {
                    AccessibilityHelperService.m19968("开发者拦截");
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: 綩私 */
    public final boolean m14190(AccessibilityNodeInfo accessibilityNodeInfo) {
        boolean z;
        boolean z2;
        if (accessibilityNodeInfo == null) {
            return false;
        }
        List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(m22253());
        if ((findAccessibilityNodeInfosByText == null || findAccessibilityNodeInfosByText.isEmpty()) ? false : true) {
            Iterator<AccessibilityNodeInfo> it = findAccessibilityNodeInfosByText.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                if (it.next().isVisibleToUser()) {
                    z = true;
                    break;
                }
            }
            if (!z) {
                return false;
            }
            List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText2 = accessibilityNodeInfo.findAccessibilityNodeInfosByText("卸载");
            if ((findAccessibilityNodeInfosByText2 == null || findAccessibilityNodeInfosByText2.isEmpty()) ? false : true) {
                Iterator<AccessibilityNodeInfo> it2 = findAccessibilityNodeInfosByText2.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        z2 = false;
                        break;
                    }
                    if (it2.next().isVisibleToUser()) {
                        z2 = true;
                        break;
                    }
                }
                if (!z2) {
                    return false;
                }
                for (int i = 3; i > 0; i--) {
                    AccessibilityHelperService.m19968("backWithUninstall");
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                SafeScreenLockView m23580 = SafeScreenLockView.m23580();
                m23580.mo23566();
                m23580.m23588("禁止操作", "该行为已被禁止", "opera: Uninstall");
                return true;
            }
        }
        return false;
    }

    /* renamed from: 耣怳匮色紝参凵蛴纆勚躄 */
    public final void m22225(AccessibilityNodeInfo accessibilityNodeInfo) {
        AppFilter m15534 = AppFilter.m15534(this.f12779);
        if (m15534.m25918(accessibilityNodeInfo.getPackageName().toString())) {
            AccessibilityHelperService.m19968("禁用应用拦截");
            return;
        }
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || !m15534.m25911(className.toString())) {
            return;
        }
        AccessibilityHelperService.m19968("黑名单页面拦截");
    }

    /* renamed from: 肌緭 */
    public /* synthetic */ boolean mo14189(AccessibilityEventImpl accessibilityEventImpl) {
//        return C6486.m15015(this, accessibilityEventImpl);
        return true;
    }

    /* renamed from: 鑭撇糁綖浓緗轟鱼萟磿焈 */
    public final boolean m22226(AccessibilityEventImpl accessibilityEventImpl) {
        if (accessibilityEventImpl.m24459() == 32) {
            if (!m22258(accessibilityEventImpl)) {
                return false;
            }
            if (!this.f12779.m19977("使用网络提供的时间", "使用网络提供的时区", "语言", "日期和时间", "恢复出厂设置").isEmpty()) {
                AccessibilityHelperService.m19968("checkSettingConfig->时间设置拦截");
                return true;
            }
            if (this.f12779.m19977("English", "中文（简体）", "中文（中国）", "简体中文（中国）", "简体中文").isEmpty()) {
                return false;
            }
            AccessibilityHelperService.m19968("checkSettingConfig->语言设置拦截");
            return true;
        }
        if (accessibilityEventImpl.m24459() == 1 && m22258(accessibilityEventImpl)) {
            for (CharSequence charSequence : accessibilityEventImpl.m15021()) {
                if ("日期和时间".contentEquals(charSequence)) {
                    AccessibilityHelperService.m19968("checkSettingConfig->日期和时间设置拦截");
                    return true;
                }
                if ("语言".contentEquals(charSequence)) {
                    AccessibilityHelperService.m19968("checkSettingConfig->语言设置拦截");
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: 陟瓠魒踱褢植螉嚜 */
    public final boolean m22227(AccessibilityEventImpl accessibilityEventImpl) {
        List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId;
        if (2048 != accessibilityEventImpl.m24459() || this.f12779.m13528(m22253()).isEmpty() || (findAccessibilityNodeInfosByViewId = this.f12779.getRootInActiveWindow().findAccessibilityNodeInfosByViewId("android:id/button1")) == null || findAccessibilityNodeInfosByViewId.isEmpty()) {
            return false;
        }
        CharSequence text = findAccessibilityNodeInfosByViewId.get(0).getText();
        if (!ObjectUtils.equals(text, "允许") && !ObjectUtils.equals(text, "授权")) {
            if (text == null || !text.toString().contains("卸载")) {
                return false;
            }
            AccessibilityHelperService.m19968("checkOpenOther->卸载字段拦截");
            return true;
        }
        findAccessibilityNodeInfosByViewId.get(0).performAction(16);
        return true;
    }
}
