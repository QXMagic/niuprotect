package accessibility;

import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ObjectUtils;

import java.util.Iterator;
import java.util.List;

import accessibility.lib.DataKV;
import accessibility.lib.ChildConfig;

public class XiaoMiMonitor extends OsMonitor {

    /* renamed from: 旞莍癡 */
    public static XiaoMiMonitor instance;

    /* renamed from: 垡玖 */
    public String f12777 = "";

    /* renamed from: 灞酞輀攼嵞漁綬迹 */
    public SafeScreenLockView lockView;

    public XiaoMiMonitor(SafeScreenLockView safeScreenLockView) {
        this.lockView = safeScreenLockView;
    }

    /* renamed from: 唌橅咟 */
    public static XiaoMiMonitor m22235(SafeScreenLockView safeScreenLockView) {
        if (instance == null) {
            instance = new XiaoMiMonitor(safeScreenLockView);
        }
        return instance;
    }

    private boolean intercept(AccessibilityEventImpl accessibilityEventImpl) {
        CharSequence text;
        CharSequence text2;
        CharSequence packageName = accessibilityEventImpl.getPackageName();
        AccessibilityNodeInfo rootInActiveWindow = this.accessHelperService.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return false;
        }
        if (TextUtils.isEmpty(packageName.toString())) {
            packageName = rootInActiveWindow.getPackageName();
        }
        String appName = appName();
        boolean m22242 = eventType1(accessibilityEventImpl);
        if (m22242 && (ObjectUtils.equals("com.miui.securitycenter", packageName) || ObjectUtils.equals("com.xiaomi.market", packageName))) {
            for (CharSequence charSequence : accessibilityEventImpl.m15021()) {
                if (ObjectUtils.equals(charSequence, appName)) {
                    AccessibilityHelperService.goBack("checkUninstall1");
                    SafeScreenLockView safeScreenLockView = this.lockView;
                    if (safeScreenLockView != null) {
                        safeScreenLockView.mo23566();
                    }
                    return true;
                }
                if (ObjectUtils.equals("安全中心", charSequence)) {
                    SafeScreenLockView safeScreenLockView2 = this.lockView;
                    if (safeScreenLockView2 != null) {
                        safeScreenLockView2.mo23566();
                    }
                    AccessibilityHelperService.goBack("checkUninstall2");
                    return true;
                }
                if (charSequence.toString().contains("一键省电")) {
                    AccessibilityHelperService.goBack("checkUninstall3");
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Iterator<AccessibilityNodeInfo> it = rootInActiveWindow.findAccessibilityNodeInfosByText("确定").iterator();
                    while (it.hasNext()) {
                        this.accessHelperService.performAction(it.next());
                    }
                    return true;
                }
            }
        } else if (accessibilityEventImpl.getEventType() == 2048 && ObjectUtils.equals("com.miui.home", packageName)) {
            if (ObjectUtils.equals("miui.app.AlertDialog", accessibilityEventImpl.getClassName())) {
                List<CharSequence> m15021 = accessibilityEventImpl.m15021();
                if (m15021.contains("卸载") && m15021.contains(appName)) {
                    AccessibilityHelperService.goBack("检测到桌面卸载弹窗");
                }
                return true;
            }
            AccessibilityNodeInfo m24460 = accessibilityEventImpl.m24460();
            if (m24460 != null) {
                List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText = m24460.findAccessibilityNodeInfosByText("卸载");
                if (findAccessibilityNodeInfosByText != null) {
                    for (AccessibilityNodeInfo accessibilityNodeInfo : findAccessibilityNodeInfosByText) {
                        if (accessibilityNodeInfo.isVisibleToUser() && (text2 = accessibilityNodeInfo.getText()) != null && text2.toString().contains(appName)) {
                            AccessibilityHelperService.goBack("检测到桌面卸载文字");
                            return true;
                        }
                    }
                }
                List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText2 = m24460.findAccessibilityNodeInfosByText("移除");
                if (findAccessibilityNodeInfosByText2 != null) {
                    for (AccessibilityNodeInfo accessibilityNodeInfo2 : findAccessibilityNodeInfosByText2) {
                        if (accessibilityNodeInfo2.isVisibleToUser() && (text = accessibilityNodeInfo2.getText()) != null && text.toString().contains(appName)) {
                            AccessibilityHelperService.goBack("检测到桌面移除文字");
                            return true;
                        }
                    }
                }
            }
        }
        if (m22242 && "com.android.permissioncontroller".contentEquals(packageName)) {
            Iterator<CharSequence> it2 = accessibilityEventImpl.m15021().iterator();
            while (it2.hasNext()) {
                if (it2.next().equals(appName)) {
                    AccessibilityHelperService.goBack("checkUninstall4");
                    return true;
                }
            }
        }
        if (accessibilityEventImpl.getEventType() == 32) {
            if (ObjectUtils.equals("com.miui.home.launcher.uninstall.DeleteDialog", accessibilityEventImpl.getClassName())) {
                Iterator<CharSequence> it3 = accessibilityEventImpl.m15021().iterator();
                while (it3.hasNext()) {
                    if (it3.next().toString().contains(appName)) {
                        AccessibilityHelperService.goBack("checkUninstall5");
                        return true;
                    }
                }
            } else if (ObjectUtils.equals("com.xiaomi.market.ui.LocalAppsActivity", accessibilityEventImpl.getClassName())) {
                Iterator<CharSequence> it4 = accessibilityEventImpl.m15021().iterator();
                while (it4.hasNext()) {
                    if (it4.next().toString().contains("应用卸载")) {
                        AccessibilityHelperService.goBack("应用市场防卸载");
                        return true;
                    }
                }
            } else if (ObjectUtils.equals("com.miui.optimizecenter.deepclean.installedapp.InstalledAppsActivity", accessibilityEventImpl.getClassName())) {
                Iterator<CharSequence> it5 = accessibilityEventImpl.m15021().iterator();
                while (it5.hasNext()) {
                    if (it5.next().toString().contains("应用卸载")) {
                        AccessibilityHelperService.goBack("应用市场防卸载");
                        return true;
                    }
                }
            }
            if (ObjectUtils.equals("com.xiaomi.misettings", packageName)) {
                if (!this.accessHelperService.matchNodelist(appName).isEmpty()) {
                    AccessibilityHelperService.goBack("checkUninstall6");
                    return true;
                }
            } else {
                if (ObjectUtils.equals("com.miui.cleanmaster", packageName) && ObjectUtils.equals("com.miui.optimizecenter.deepclean.installedapp.InstalledAppsActivity", accessibilityEventImpl.getClassName())) {
                    AccessibilityHelperService.goBack("checkUninstall7");
                    return true;
                }
                if ("com.miui.securitycenter".contentEquals(packageName)) {
                    if (!this.accessHelperService.findUiByText(appName).isEmpty()) {
                        AccessibilityHelperService.goBack("checkUninstall8");
                        return true;
                    }
                    if (!ObjectUtils.equals("com.miui.powercenter.PowerMainActivity", accessibilityEventImpl.getClassName()) && !ObjectUtils.equals("com.miui.permcenter.settings.PrivacySettingsActivity", accessibilityEventImpl.getClassName()) && !ObjectUtils.equals("com.miui.appmanager.AppManagerMainActivity", accessibilityEventImpl.getClassName())) {
                        if ("com.miui.appmanager.ApplicationsDetailsActivity".contentEquals(accessibilityEventImpl.getClassName())) {
                            if (!rootInActiveWindow.findAccessibilityNodeInfosByText(appName).isEmpty()) {
                                AccessibilityHelperService.goBack("checkUninstall10");
                                this.f12777 = String.valueOf(accessibilityEventImpl.getClassName());
                                return true;
                            }
                            if (!this.f12777.isEmpty() && "com.miui.appmanager.ApplicationsDetailsActivity".equals(this.f12777) && !rootInActiveWindow.findAccessibilityNodeInfosByText("停止").isEmpty()) {
                                AccessibilityHelperService.goBack("checkUninstall11");
                                this.f12777 = String.valueOf(accessibilityEventImpl.getClassName());
                                return true;
                            }
                            this.f12777 = "";
                        } else {
                            if (!this.f12777.isEmpty() && "com.miui.appmanager.ApplicationsDetailsActivity".equals(this.f12777)) {
                                if (!rootInActiveWindow.findAccessibilityNodeInfosByText(appName).isEmpty()) {
                                    AccessibilityHelperService.goBack("checkUninstall12");
                                }
                                return true;
                            }
                            if (ObjectUtils.equals("com.miui.permcenter.settings.PrivacyMonitorOpenActivity", accessibilityEventImpl.getClassName())) {
                                if (!this.accessHelperService.matchNodelist(appName).isEmpty()) {
                                    AccessibilityHelperService.goBack("checkUninstall13");
                                    return true;
                                }
                            } else if (ObjectUtils.equals("com.miui.permcenter.privacymanager.behaviorrecord.PrivacyDetailActivity", accessibilityEventImpl.getClassName())) {
                                AccessibilityHelperService.goBack("checkUninstall14");
                                return true;
                            }
                        }
                        for (CharSequence charSequence2 : accessibilityEventImpl.m15021()) {
                            if (charSequence2.equals(appName) || charSequence2.equals("超级省电模式") || charSequence2.equals("开启省电模式")) {
                                AccessibilityHelperService.goBack("checkUninstall15");
                                return true;
                            }
                        }
                    } else {
                        AccessibilityHelperService.goBack("checkUninstall9");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override // p250.OsMonitor, p383.InterfaceC6489
    public boolean mo22210(@NonNull AccessibilityEventImpl accessibilityEventImpl) {
        if (ObjectUtils.equals("android", accessibilityEventImpl.getPackageName())
                && accessibilityEventImpl.getEventType() == 2048
                && m22245(this.accessHelperService.getRootInActiveWindow())) {
            if (System.currentTimeMillis() - DataKV.getLong("boot_complete_time", 0L) <= 30000) {
                AccessibilityHelperService.goBack();
                AccessibilityHelperService.goHome();
                return false;
            }
        }
        if (super.mo22210(accessibilityEventImpl) || this.accessHelperService.getRootInActiveWindow() == null) {
            return false;
        }
        return skipSetting(accessibilityEventImpl)
                || m14191(accessibilityEventImpl)
                || m22244(accessibilityEventImpl)
                || intercept(accessibilityEventImpl)
                || m22243(accessibilityEventImpl)
                || skipAccessibilityApp(accessibilityEventImpl)
                || skipUsingPriviliges(accessibilityEventImpl)
                || skipRecent(accessibilityEventImpl);
    }

    /* renamed from: 卝閄侸靤溆鲁扅 */
    public final boolean skipSetting(AccessibilityEventImpl accessibilityEventImpl) {
        boolean z;
        if (accessibilityEventImpl.getEventType() == 32) {
            String charSequence = accessibilityEventImpl.getClassName().toString();
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
                    AccessibilityHelperService.goBack("小米应用设置返回");
                    return true;
                }
            }
            if (ObjectUtils.equals(charSequence, "com.miui.permcenter.MainAcitivty")) {
                AccessibilityHelperService.goBack("小米应用设置返回1");
                AccessibilityHelperService.goBack("小米应用设置返回2");
                return true;
            }
        }
        return false;
    }

    public final boolean skipUsingPriviliges(AccessibilityEventImpl accessibilityEventImpl) {
        if (accessibilityEventImpl.getEventType() != 32 || !"com.android.settings.SubSettings".contentEquals(accessibilityEventImpl.getClassName())) {
            return false;
        }
        Iterator<CharSequence> it = accessibilityEventImpl.m15021().iterator();
        while (it.hasNext()) {
            if (it.next().equals("使用情况访问权限")) {
                AccessibilityHelperService.goBack();
                return true;
            }
        }
        return false;
    }

    /* renamed from: 斃燸卺驼暲各撟嫺眧樬硱 */
    public final boolean skipAccessibilityApp(AccessibilityEventImpl accessibilityEventImpl) {
        if (accessibilityEventImpl.getEventType() != 32) {
            if (eventType1(accessibilityEventImpl) && AppFilter.instance(this.accessHelperService).m25906(accessibilityEventImpl.getPackageName())) {
                Iterator<CharSequence> it = accessibilityEventImpl.m15021().iterator();
                while (it.hasNext()) {
                    if (it.next().equals(appName())) {
                        AccessibilityHelperService.goBack("点击了无障碍的App名称");
                        return true;
                    }
                }
            }
            return false;
        }
        if (!"android.app.AlertDialog".contentEquals(accessibilityEventImpl.getClassName())) {
            return false;
        }
        Iterator<CharSequence> it2 = accessibilityEventImpl.m15021().iterator();
        while (it2.hasNext()) {
            if (it2.next().equals("要停用“" + appName() + "”吗？")) {
                Iterator<AccessibilityNodeInfo> it3 = this.accessHelperService.getRootInActiveWindow().findAccessibilityNodeInfosByText("取消").iterator();
                if (it3.hasNext()) {
                    this.accessHelperService.performAction(it3.next());
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean skipRecent(AccessibilityEventImpl accessibilityEventImpl) {
        if (accessibilityEventImpl.getEventType() != 32
                || !"com.miui.home.recents.settings.NavigationBarTypeActivity".contentEquals(accessibilityEventImpl.getClassName())) {
            return false;
        }
        AccessibilityHelperService.goBack();
        return true;
    }

    public final boolean skipQuickSetting(AccessibilityEventImpl accessibilityEventImpl) {
        boolean z;
        List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId;
        AccessibilityNodeInfo m19976 = this.accessHelperService.rootWindow();
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
        if (!z && accessibilityEventImpl.getEventType() == 1 && ObjectUtils.equals("快捷设置编辑器。", accessibilityEventImpl.m24462())) {
            z = true;
        }
        if (!z) {
            return false;
        }
        AccessibilityHelperService.goBack("checkStatusBarEdit");
        AccessibilityHelperService.goBack("checkStatusBarEdit");
        AccessibilityHelperService.goBack("checkStatusBarEdit");
        return true;
    }

    /* renamed from: 礱咄頑 */
    public final boolean eventType1(AccessibilityEventImpl accessibilityEventImpl) {
        return accessibilityEventImpl.getEventType() == 1;
    }

    /* renamed from: 纩慐 */
    public final boolean m14191(AccessibilityEventImpl accessibilityEventImpl) {
        SafeScreenLockView safeScreenLockView;
        SafeScreenLockView safeScreenLockView2 = this.lockView;
        if (safeScreenLockView2 != null && safeScreenLockView2.m25772()) {
            return false;
        }
        if (accessibilityEventImpl.getEventType() == 32) {
            if (ObjectUtils.equals("com.miui.packageinstaller", accessibilityEventImpl.getPackageName())) {
                return true;
            }
            if (ObjectUtils.equals("com.android.systemui", accessibilityEventImpl.getPackageName())) {
                if (!"com.android.systemui.recents.RecentsActivity".contentEquals(accessibilityEventImpl.getClassName())) {
                    return false;
                }
                SafeScreenLockView safeScreenLockView3 = this.lockView;
                if (safeScreenLockView3 != null) {
                    safeScreenLockView3.mo23566();
                }
                return true;
            }
            if (ObjectUtils.equals("com.miui.personalassistant", accessibilityEventImpl.getPackageName())) {
                return true;
            }
            if (!ObjectUtils.equals("com.miui.securitycenter", accessibilityEventImpl.getPackageName()) || accessibilityEventImpl.getClassName() == null || !accessibilityEventImpl.getClassName().toString().endsWith("Activity") || ObjectUtils.equals(accessibilityEventImpl.getClassName(), "com.miui.wakepath.ui.ConfirmStartActivity") || ObjectUtils.equals(accessibilityEventImpl.getClassName(), "com.miui.permcenter.install.AdbInstallActivity") || ObjectUtils.equals(accessibilityEventImpl.getClassName(), "com.miui.appmanager.ApplicationsDetailsActivity")) {
                return false;
            }
            SafeScreenLockView safeScreenLockView4 = this.lockView;
            if (safeScreenLockView4 == null) {
                return true;
            }
            safeScreenLockView4.mo23566();
            return true;
        }
        if (accessibilityEventImpl.getEventType() != 2048 || !ObjectUtils.equals("com.android.systemui", accessibilityEventImpl.getPackageName()) || this.accessHelperService.getRootInActiveWindow().findAccessibilityNodeInfosByViewId("com.android.systemui:id/btnDock").isEmpty() || (safeScreenLockView = this.lockView) == null) {
            return false;
        }
        safeScreenLockView.mo23566();
        return true;
    }

    @Override // p250.OsMonitor, p383.InterfaceC6489
    /* renamed from: 肌緭 */
    public boolean mo14189(@NonNull AccessibilityEventImpl accessibilityEventImpl) {
        return skipQuickSetting(accessibilityEventImpl) || m22246(accessibilityEventImpl);
    }

    /* renamed from: 辒迳圄袡皪郞箟 */
    public final boolean m22243(AccessibilityEventImpl accessibilityEventImpl) {
        if (accessibilityEventImpl.getEventType() != 32 || !"com.miui.securitycenter".contentEquals(accessibilityEventImpl.getPackageName()) || !"com.miui.permcenter.autostart.AutoStartManagementActivity".contentEquals(accessibilityEventImpl.getClassName())) {
            return false;
        }
        AccessibilityHelperService.goBack("自启动拦截");
        return true;
    }

    /* renamed from: 销薞醣戔攖餗 */
    public final boolean m22244(AccessibilityEventImpl accessibilityEventImpl) {
        AccessibilityNodeInfo rootInActiveWindow = this.accessHelperService.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return false;
        }
        String m22253 = appName();
        if (eventType1(accessibilityEventImpl)) {
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
            AccessibilityHelperService.goBack("设备管理权限拦截");
            return true;
        }
        if (accessibilityEventImpl.getEventType() == 2048 && "android.widget.CheckBox".contentEquals(accessibilityEventImpl.getClassName()) && AppFilter.instance(this.accessHelperService).m25906(accessibilityEventImpl.getPackageName()) && !rootInActiveWindow.findAccessibilityNodeInfosByText(m22253).isEmpty() && !rootInActiveWindow.findAccessibilityNodeInfosByText("停用此设备管理应用").isEmpty()) {
            AccessibilityHelperService.goBack("设备管理权限拦截1");
            return true;
        }
        if (accessibilityEventImpl.getEventType() == 32 && "com.android.settings.applications.specialaccess.deviceadmin.DeviceAdminAdd".contentEquals(accessibilityEventImpl.getClassName())) {
            AccessibilityHelperService.goBack("设备管理权限拦截2");
            return true;
        }
        if (rootInActiveWindow.findAccessibilityNodeInfosByText(m22253).isEmpty() || rootInActiveWindow.findAccessibilityNodeInfosByText("停用此设备管理应用").isEmpty()) {
            return false;
        }
        AccessibilityHelperService.goBack("设备管理权限拦截3");
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

    public final boolean m22246(AccessibilityEventImpl accessibilityEventImpl) {
        boolean z;
        List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId;
        ChildConfig m14194 = getConfig();
        if (m14194 == null || m14194.getIsStatusBarOpen() == 0) {
            return false;
        }
        AccessibilityNodeInfo m19976 = this.accessHelperService.rootWindow();
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
        AccessibilityHelperService.goBack("checkStatusBarEdit");
        AccessibilityHelperService.goBack("checkStatusBarEdit");
        AccessibilityHelperService.goBack("checkStatusBarEdit");
        return true;
    }
}
