package com.niu.protect.accessibility.auto.service;

import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.NonNull;

import com.niu.protect.accessibility.auto.service.tmp.C6891;
import com.niu.protect.accessibility.auto.service.tmp.ChildConfig;

import java.util.Iterator;
import java.util.List;

public class XiaoMiMonitor extends OsMonitor {

    /* renamed from: 旞莍癡 */
    public static XiaoMiMonitor f20708;

    /* renamed from: 垡玖 */
    public String f12777 = "";

    /* renamed from: 灞酞輀攼嵞漁綬迹 */
    public SafeScreenLockView f20709;

    public XiaoMiMonitor(SafeScreenLockView safeScreenLockView) {
        this.f20709 = safeScreenLockView;
    }

    /* renamed from: 唌橅咟 */
    public static XiaoMiMonitor m22235(SafeScreenLockView safeScreenLockView) {
        if (f20708 == null) {
            f20708 = new XiaoMiMonitor(safeScreenLockView);
        }
        return f20708;
    }

    /* renamed from: 彻薯铏螙憣欖愡鼭 */
    private boolean m22236(AccessibilityEventImpl accessibilityEventImpl) {
        CharSequence text;
        CharSequence text2;
        CharSequence m15019 = accessibilityEventImpl.m15019();
        AccessibilityNodeInfo rootInActiveWindow = this.f12779.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return false;
        }
        if (TextUtils.isEmpty(m15019.toString())) {
            m15019 = rootInActiveWindow.getPackageName();
        }
        String m22253 = m22253();
        boolean m22242 = m22242(accessibilityEventImpl);
        if (m22242 && (ObjectUtils.equals("com.miui.securitycenter", m15019) || ObjectUtils.equals("com.xiaomi.market", m15019))) {
            for (CharSequence charSequence : accessibilityEventImpl.m15021()) {
                if (ObjectUtils.equals(charSequence, m22253)) {
                    AccessibilityHelperService.m19968("checkUninstall1");
                    SafeScreenLockView safeScreenLockView = this.f20709;
                    if (safeScreenLockView != null) {
                        safeScreenLockView.mo23566();
                    }
                    return true;
                }
                if (ObjectUtils.equals("安全中心", charSequence)) {
                    SafeScreenLockView safeScreenLockView2 = this.f20709;
                    if (safeScreenLockView2 != null) {
                        safeScreenLockView2.mo23566();
                    }
                    AccessibilityHelperService.m19968("checkUninstall2");
                    return true;
                }
                if (charSequence.toString().contains("一键省电")) {
                    AccessibilityHelperService.m19968("checkUninstall3");
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Iterator<AccessibilityNodeInfo> it = rootInActiveWindow.findAccessibilityNodeInfosByText("确定").iterator();
                    while (it.hasNext()) {
                        this.f12779.m13526(it.next());
                    }
                    return true;
                }
            }
        } else if (accessibilityEventImpl.m24459() == 2048 && ObjectUtils.equals("com.miui.home", m15019)) {
            if (ObjectUtils.equals("miui.app.AlertDialog", accessibilityEventImpl.m15020())) {
                List<CharSequence> m15021 = accessibilityEventImpl.m15021();
                if (m15021.contains("卸载") && m15021.contains(m22253)) {
                    AccessibilityHelperService.m19968("检测到桌面卸载弹窗");
                }
                return true;
            }
            AccessibilityNodeInfo m24460 = accessibilityEventImpl.m24460();
            if (m24460 != null) {
                List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText = m24460.findAccessibilityNodeInfosByText("卸载");
                if (findAccessibilityNodeInfosByText != null) {
                    for (AccessibilityNodeInfo accessibilityNodeInfo : findAccessibilityNodeInfosByText) {
                        if (accessibilityNodeInfo.isVisibleToUser() && (text2 = accessibilityNodeInfo.getText()) != null && text2.toString().contains(m22253)) {
                            AccessibilityHelperService.m19968("检测到桌面卸载文字");
                            return true;
                        }
                    }
                }
                List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText2 = m24460.findAccessibilityNodeInfosByText("移除");
                if (findAccessibilityNodeInfosByText2 != null) {
                    for (AccessibilityNodeInfo accessibilityNodeInfo2 : findAccessibilityNodeInfosByText2) {
                        if (accessibilityNodeInfo2.isVisibleToUser() && (text = accessibilityNodeInfo2.getText()) != null && text.toString().contains(m22253)) {
                            AccessibilityHelperService.m19968("检测到桌面移除文字");
                            return true;
                        }
                    }
                }
            }
        }
        if (m22242 && "com.android.permissioncontroller".contentEquals(m15019)) {
            Iterator<CharSequence> it2 = accessibilityEventImpl.m15021().iterator();
            while (it2.hasNext()) {
                if (it2.next().equals(m22253)) {
                    AccessibilityHelperService.m19968("checkUninstall4");
                    return true;
                }
            }
        }
        if (accessibilityEventImpl.m24459() == 32) {
            if (ObjectUtils.equals("com.miui.home.launcher.uninstall.DeleteDialog", accessibilityEventImpl.m15020())) {
                Iterator<CharSequence> it3 = accessibilityEventImpl.m15021().iterator();
                while (it3.hasNext()) {
                    if (it3.next().toString().contains(m22253)) {
                        AccessibilityHelperService.m19968("checkUninstall5");
                        return true;
                    }
                }
            } else if (ObjectUtils.equals("com.xiaomi.market.ui.LocalAppsActivity", accessibilityEventImpl.m15020())) {
                Iterator<CharSequence> it4 = accessibilityEventImpl.m15021().iterator();
                while (it4.hasNext()) {
                    if (it4.next().toString().contains("应用卸载")) {
                        AccessibilityHelperService.m19968("应用市场防卸载");
                        return true;
                    }
                }
            } else if (ObjectUtils.equals("com.miui.optimizecenter.deepclean.installedapp.InstalledAppsActivity", accessibilityEventImpl.m15020())) {
                Iterator<CharSequence> it5 = accessibilityEventImpl.m15021().iterator();
                while (it5.hasNext()) {
                    if (it5.next().toString().contains("应用卸载")) {
                        AccessibilityHelperService.m19968("应用市场防卸载");
                        return true;
                    }
                }
            }
            if (ObjectUtils.equals("com.xiaomi.misettings", m15019)) {
                if (!this.f12779.m13528(m22253).isEmpty()) {
                    AccessibilityHelperService.m19968("checkUninstall6");
                    return true;
                }
            } else {
                if (ObjectUtils.equals("com.miui.cleanmaster", m15019) && ObjectUtils.equals("com.miui.optimizecenter.deepclean.installedapp.InstalledAppsActivity", accessibilityEventImpl.m15020())) {
                    AccessibilityHelperService.m19968("checkUninstall7");
                    return true;
                }
                if ("com.miui.securitycenter".contentEquals(m15019)) {
                    if (!this.f12779.m19977(m22253).isEmpty()) {
                        AccessibilityHelperService.m19968("checkUninstall8");
                        return true;
                    }
                    if (!ObjectUtils.equals("com.miui.powercenter.PowerMainActivity", accessibilityEventImpl.m15020()) && !ObjectUtils.equals("com.miui.permcenter.settings.PrivacySettingsActivity", accessibilityEventImpl.m15020()) && !ObjectUtils.equals("com.miui.appmanager.AppManagerMainActivity", accessibilityEventImpl.m15020())) {
                        if ("com.miui.appmanager.ApplicationsDetailsActivity".contentEquals(accessibilityEventImpl.m15020())) {
                            if (!rootInActiveWindow.findAccessibilityNodeInfosByText(m22253).isEmpty()) {
                                AccessibilityHelperService.m19968("checkUninstall10");
                                this.f12777 = String.valueOf(accessibilityEventImpl.m15020());
                                return true;
                            }
                            if (!this.f12777.isEmpty() && "com.miui.appmanager.ApplicationsDetailsActivity".equals(this.f12777) && !rootInActiveWindow.findAccessibilityNodeInfosByText("停止").isEmpty()) {
                                AccessibilityHelperService.m19968("checkUninstall11");
                                this.f12777 = String.valueOf(accessibilityEventImpl.m15020());
                                return true;
                            }
                            this.f12777 = "";
                        } else {
                            if (!this.f12777.isEmpty() && "com.miui.appmanager.ApplicationsDetailsActivity".equals(this.f12777)) {
                                if (!rootInActiveWindow.findAccessibilityNodeInfosByText(m22253).isEmpty()) {
                                    AccessibilityHelperService.m19968("checkUninstall12");
                                }
                                return true;
                            }
                            if (ObjectUtils.equals("com.miui.permcenter.settings.PrivacyMonitorOpenActivity", accessibilityEventImpl.m15020())) {
                                if (!this.f12779.m13528(m22253).isEmpty()) {
                                    AccessibilityHelperService.m19968("checkUninstall13");
                                    return true;
                                }
                            } else if (ObjectUtils.equals("com.miui.permcenter.privacymanager.behaviorrecord.PrivacyDetailActivity", accessibilityEventImpl.m15020())) {
                                AccessibilityHelperService.m19968("checkUninstall14");
                                return true;
                            }
                        }
                        for (CharSequence charSequence2 : accessibilityEventImpl.m15021()) {
                            if (charSequence2.equals(m22253) || charSequence2.equals("超级省电模式") || charSequence2.equals("开启省电模式")) {
                                AccessibilityHelperService.m19968("checkUninstall15");
                                return true;
                            }
                        }
                    } else {
                        AccessibilityHelperService.m19968("checkUninstall9");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override // p250.OsMonitor, p383.InterfaceC6489
    /* renamed from: 刻槒唱镧詴 */
    public boolean mo22210(@NonNull AccessibilityEventImpl accessibilityEventImpl) {
        if (ObjectUtils.equals("android", accessibilityEventImpl.m15019()) && accessibilityEventImpl.m24459() == 2048 && m22245(this.f12779.getRootInActiveWindow())) {
            if (System.currentTimeMillis() - C6891.m25866("boot_complete_time", 0L) <= 30000) {
                AccessibilityHelperService.m19973();
                AccessibilityHelperService.m19967();
                return false;
            }
        }
        if (super.mo22210(accessibilityEventImpl) || this.f12779.getRootInActiveWindow() == null) {
            return false;
        }
        return m22237(accessibilityEventImpl) || m14191(accessibilityEventImpl) || m22244(accessibilityEventImpl) || m22236(accessibilityEventImpl) || m22243(accessibilityEventImpl) || m22239(accessibilityEventImpl) || m22238(accessibilityEventImpl) || m22240(accessibilityEventImpl);
    }

    /* renamed from: 卝閄侸靤溆鲁扅 */
    public final boolean m22237(AccessibilityEventImpl accessibilityEventImpl) {
        boolean z;
        if (accessibilityEventImpl.m24459() == 32) {
            String charSequence = accessibilityEventImpl.m15020().toString();
            if (ObjectUtils.equals(charSequence, "com.android.settings.SubSettings")) {
                Iterator<CharSequence> it = accessibilityEventImpl.m15021().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    if ("应用设置".contentEquals(it.next())) {
                        z = true;
                        break;
                    }
                }
                if (z) {
                    AccessibilityHelperService.m19968("小米应用设置返回");
                    return true;
                }
            }
            if (ObjectUtils.equals(charSequence, "com.miui.permcenter.MainAcitivty")) {
                AccessibilityHelperService.m19968("小米应用设置返回1");
                AccessibilityHelperService.m19968("小米应用设置返回2");
                return true;
            }
        }
        return false;
    }

    /* renamed from: 壋劘跆貭澴綄秽攝煾訲 */
    public final boolean m22238(AccessibilityEventImpl accessibilityEventImpl) {
        if (accessibilityEventImpl.m24459() != 32 || !"com.android.settings.SubSettings".contentEquals(accessibilityEventImpl.m15020())) {
            return false;
        }
        Iterator<CharSequence> it = accessibilityEventImpl.m15021().iterator();
        while (it.hasNext()) {
            if (it.next().equals("使用情况访问权限")) {
                AccessibilityHelperService.m19973();
                return true;
            }
        }
        return false;
    }

    /* renamed from: 斃燸卺驼暲各撟嫺眧樬硱 */
    public final boolean m22239(AccessibilityEventImpl accessibilityEventImpl) {
        if (accessibilityEventImpl.m24459() != 32) {
            if (m22242(accessibilityEventImpl) && AppFilter.m15534(this.f12779).m25906(accessibilityEventImpl.m15019())) {
                Iterator<CharSequence> it = accessibilityEventImpl.m15021().iterator();
                while (it.hasNext()) {
                    if (it.next().equals(m22253())) {
                        AccessibilityHelperService.m19968("点击了无障碍的App名称");
                        return true;
                    }
                }
            }
            return false;
        }
        if (!"android.app.AlertDialog".contentEquals(accessibilityEventImpl.m15020())) {
            return false;
        }
        Iterator<CharSequence> it2 = accessibilityEventImpl.m15021().iterator();
        while (it2.hasNext()) {
            if (it2.next().equals("要停用“" + m22253() + "”吗？")) {
                Iterator<AccessibilityNodeInfo> it3 = this.f12779.getRootInActiveWindow().findAccessibilityNodeInfosByText("取消").iterator();
                if (it3.hasNext()) {
                    this.f12779.m13526(it3.next());
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: 杹藗瀶姙笻件稚嵅蔂 */
    public final boolean m22240(AccessibilityEventImpl accessibilityEventImpl) {
        if (accessibilityEventImpl.m24459() != 32 || !"com.miui.home.recents.settings.NavigationBarTypeActivity".contentEquals(accessibilityEventImpl.m15020())) {
            return false;
        }
        AccessibilityHelperService.m19973();
        return true;
    }

    /* renamed from: 癎躑選熁 */
    public final boolean m22241(AccessibilityEventImpl accessibilityEventImpl) {
        boolean z;
        List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId;
        AccessibilityNodeInfo m19976 = this.f12779.m19976();
        if (m19976 != null && (findAccessibilityNodeInfosByViewId = m19976.findAccessibilityNodeInfosByViewId("com.android.systemui:id/title")) != null) {
            Iterator<AccessibilityNodeInfo> it = findAccessibilityNodeInfosByViewId.iterator();
            while (it.hasNext()) {
                if (ObjectUtils.equals(it.next().getText(), "已添加开关")) {
                    z = true;
                    break;
                }
            }
        }
        z = false;
        if (!z && accessibilityEventImpl.m24459() == 1 && ObjectUtils.equals("快捷设置编辑器。", accessibilityEventImpl.m24462())) {
            z = true;
        }
        if (!z) {
            return false;
        }
        AccessibilityHelperService.m19968("checkStatusBarEdit");
        AccessibilityHelperService.m19968("checkStatusBarEdit");
        AccessibilityHelperService.m19968("checkStatusBarEdit");
        return true;
    }

    /* renamed from: 礱咄頑 */
    public final boolean m22242(AccessibilityEventImpl accessibilityEventImpl) {
        return accessibilityEventImpl.m24459() == 1;
    }

    /* renamed from: 纩慐 */
    public final boolean m14191(AccessibilityEventImpl accessibilityEventImpl) {
        SafeScreenLockView safeScreenLockView;
        SafeScreenLockView safeScreenLockView2 = this.f20709;
        if (safeScreenLockView2 != null && safeScreenLockView2.m25772()) {
            return false;
        }
        if (accessibilityEventImpl.m24459() == 32) {
            if (ObjectUtils.equals("com.miui.packageinstaller", accessibilityEventImpl.m15019())) {
                return true;
            }
            if (ObjectUtils.equals("com.android.systemui", accessibilityEventImpl.m15019())) {
                if (!"com.android.systemui.recents.RecentsActivity".contentEquals(accessibilityEventImpl.m15020())) {
                    return false;
                }
                SafeScreenLockView safeScreenLockView3 = this.f20709;
                if (safeScreenLockView3 != null) {
                    safeScreenLockView3.mo23566();
                }
                return true;
            }
            if (ObjectUtils.equals("com.miui.personalassistant", accessibilityEventImpl.m15019())) {
                return true;
            }
            if (!ObjectUtils.equals("com.miui.securitycenter", accessibilityEventImpl.m15019()) || accessibilityEventImpl.m15020() == null || !accessibilityEventImpl.m15020().toString().endsWith("Activity") || ObjectUtils.equals(accessibilityEventImpl.m15020(), "com.miui.wakepath.ui.ConfirmStartActivity") || ObjectUtils.equals(accessibilityEventImpl.m15020(), "com.miui.permcenter.install.AdbInstallActivity") || ObjectUtils.equals(accessibilityEventImpl.m15020(), "com.miui.appmanager.ApplicationsDetailsActivity")) {
                return false;
            }
            SafeScreenLockView safeScreenLockView4 = this.f20709;
            if (safeScreenLockView4 == null) {
                return true;
            }
            safeScreenLockView4.mo23566();
            return true;
        }
        if (accessibilityEventImpl.m24459() != 2048 || !ObjectUtils.equals("com.android.systemui", accessibilityEventImpl.m15019()) || this.f12779.getRootInActiveWindow().findAccessibilityNodeInfosByViewId("com.android.systemui:id/btnDock").isEmpty() || (safeScreenLockView = this.f20709) == null) {
            return false;
        }
        safeScreenLockView.mo23566();
        return true;
    }

    @Override // p250.OsMonitor, p383.InterfaceC6489
    /* renamed from: 肌緭 */
    public boolean mo14189(@NonNull AccessibilityEventImpl accessibilityEventImpl) {
        return m22241(accessibilityEventImpl) || m22246(accessibilityEventImpl);
    }

    /* renamed from: 辒迳圄袡皪郞箟 */
    public final boolean m22243(AccessibilityEventImpl accessibilityEventImpl) {
        if (accessibilityEventImpl.m24459() != 32 || !"com.miui.securitycenter".contentEquals(accessibilityEventImpl.m15019()) || !"com.miui.permcenter.autostart.AutoStartManagementActivity".contentEquals(accessibilityEventImpl.m15020())) {
            return false;
        }
        AccessibilityHelperService.m19968("自启动拦截");
        return true;
    }

    /* renamed from: 销薞醣戔攖餗 */
    public final boolean m22244(AccessibilityEventImpl accessibilityEventImpl) {
        AccessibilityNodeInfo rootInActiveWindow = this.f12779.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return false;
        }
        String m22253 = m22253();
        if (m22242(accessibilityEventImpl)) {
            int i = 0;
            for (CharSequence charSequence : accessibilityEventImpl.m15021()) {
                if ("提供于管理被监护人的设备的自动化工作".contentEquals(charSequence)) {
                    i++;
                } else if (m22253.contentEquals(charSequence)) {
                    i += 2;
                }
            }
            if (i != 3) {
                return false;
            }
            AccessibilityHelperService.m19968("设备管理权限拦截");
            return true;
        }
        if (accessibilityEventImpl.m24459() == 2048 && "android.widget.CheckBox".contentEquals(accessibilityEventImpl.m15020()) && AppFilter.m15534(this.f12779).m25906(accessibilityEventImpl.m15019()) && !rootInActiveWindow.findAccessibilityNodeInfosByText(m22253).isEmpty() && !rootInActiveWindow.findAccessibilityNodeInfosByText("停用此设备管理应用").isEmpty()) {
            AccessibilityHelperService.m19968("设备管理权限拦截1");
            return true;
        }
        if (accessibilityEventImpl.m24459() == 32 && "com.android.settings.applications.specialaccess.deviceadmin.DeviceAdminAdd".contentEquals(accessibilityEventImpl.m15020())) {
            AccessibilityHelperService.m19968("设备管理权限拦截2");
            return true;
        }
        if (rootInActiveWindow.findAccessibilityNodeInfosByText(m22253).isEmpty() || rootInActiveWindow.findAccessibilityNodeInfosByText("停用此设备管理应用").isEmpty()) {
            return false;
        }
        AccessibilityHelperService.m19968("设备管理权限拦截3");
        return true;
    }

    /* renamed from: 韐爮幀悖罤噩钼遑杯盇 */
    public final boolean m22245(AccessibilityNodeInfo accessibilityNodeInfo) {
        int childCount;
        if (accessibilityNodeInfo != null && (childCount = accessibilityNodeInfo.getChildCount()) > 0) {
            boolean z = false;
            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
                if (child != null) {
                    CharSequence contentDescription = child.getContentDescription();
                    if (ObjectUtils.equals("重新启动", contentDescription) || ObjectUtils.equals("重启", contentDescription)) {
                        if (z) {
                            return true;
                        }
                    } else if (!ObjectUtils.equals("关机", contentDescription)) {
                        continue;
                    } else if (z) {
                        return true;
                    }
                    z = true;
                }
            }
        }
        return false;
    }

    /* renamed from: 駭鑈趘薑衈講堍趃軏 */
    public final boolean m22246(AccessibilityEventImpl accessibilityEventImpl) {
        boolean z;
        List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId;
        ChildConfig m14194 = m14194();
        if (m14194 == null || m14194.getIsStatusBarOpen() == 0) {
            return false;
        }
        AccessibilityNodeInfo m19976 = this.f12779.m19976();
        if (m19976 != null) {
            List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId2 = m19976.findAccessibilityNodeInfosByViewId("com.android.systemui:id/control_center_shortcut");
            if (findAccessibilityNodeInfosByViewId2 != null) {
                Iterator<AccessibilityNodeInfo> it = findAccessibilityNodeInfosByViewId2.iterator();
                while (it.hasNext()) {
                    if (ObjectUtils.equals(it.next().getContentDescription(), "设置")) {
                        z = true;
                        break;
                    }
                }
            }
            z = false;
            if (!z && (findAccessibilityNodeInfosByViewId = m19976.findAccessibilityNodeInfosByViewId("com.android.systemui:id/tiles_edit")) != null) {
                Iterator<AccessibilityNodeInfo> it2 = findAccessibilityNodeInfosByViewId.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    if (ObjectUtils.equals(it2.next().getContentDescription(), "快捷设置编辑器。")) {
                        z = true;
                        break;
                    }
                }
            }
        } else {
            z = false;
        }
        if (!z) {
            return false;
        }
        AccessibilityHelperService.m19968("checkStatusBarEdit");
        AccessibilityHelperService.m19968("checkStatusBarEdit");
        AccessibilityHelperService.m19968("checkStatusBarEdit");
        return true;
    }
}
