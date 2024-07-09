package accessibility;

import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ObjectUtils;

import java.util.Iterator;
import java.util.List;

import accessibility.lib.ModelManager;

public abstract class OsMonitor extends BaseAccessibilityMonitor implements InterfaceC6489 {
    /* renamed from: 刻槒唱镧詴 */
    public boolean mo22210(@NonNull AccessibilityEventImpl accessibilityEventImpl) {
        SafeScreenLockView m23580 = SafeScreenLockView.m23580();
        if (m23580.m25772()) {
            LogHelper.m27987("isShowing");
            return true;
        }
        if (ObjectUtils.isEmpty((CharSequence) accessibilityEventImpl.className())) {
            return false;
        }
        String m24457 = accessibilityEventImpl.className();
        String m24458 = accessibilityEventImpl.packageName();
        AppFilter m15534 = AppFilter.instance(App.mContext);
        if (accessibilityEventImpl.getEventType() == 32) {
            if (m15534.m25927(m24458)) {
                AccessibilityHelperService.goBack("语音助手拦截");
                AccessibilityHelperService.goHome();
                App.showToast("应用不被允许运行");
                return true;
            }
            if (!m15534.m25905(m24457) && !m15534.m25929(m24458)) {
                if (m15534.m25911(m24457)) {
                    if (m22258(accessibilityEventImpl)) {
                        ModelManager.m22768(null);
                        return true;
                    }
                    AccessibilityHelperService.goBack("isBlackPage非系统拦截");
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    AccessibilityHelperService.goHome();
                    return true;
                }
                if (!m15534.m15540(accessibilityEventImpl.packageName()) && m15534.m25918(accessibilityEventImpl.packageName())) {
                    AccessibilityHelperService.goBack("禁止App拦截");
                    AccessibilityHelperService.goHome();
                    return true;
                }
            } else {
                m14190(this.accessHelperService.getRootInActiveWindow());
                return true;
            }
        }
        if (m15534.m25903(m24457)) {
            return true;
        }
        if (this.accessHelperService.getRootInActiveWindow() == null) {
            return false;
        }
        if (accessibilityEventImpl.getEventType() == 2) {
            if (getPackageName().contentEquals(m24458)) {
                return false;
            }
            if (accessibilityEventImpl.m24462() != null && accessibilityEventImpl.m24462().toString().contains(appName())) {
                AccessibilityHelperService.goBack("App名称拦截");
                AccessibilityHelperService.goHome();
                m23580.mo23566();
                m23580.m23588("禁止操作", "该行为已被禁止", "code:-1");
                return true;
            }
            List<CharSequence> m15021 = accessibilityEventImpl.m15021();
            if (m15021 != null && m15021.contains(appName())) {
                AccessibilityHelperService.goBack("App名称文字拦截");
                AccessibilityHelperService.goHome();
                m23580.mo23566();
                m23580.m23588("禁止操作", "该行为已被禁止", "code:-2");
                return true;
            }
        }
        return ((accessibilityEventImpl.getEventType() == 32 || accessibilityEventImpl.getEventType() == 2048 || accessibilityEventImpl.getEventType() == 2 || accessibilityEventImpl.getEventType() == 1) && !this.accessHelperService.findUiByText("卸载").isEmpty() && m14190(accessibilityEventImpl.m24460())) || m15534.m25903(m24457) || m22223(accessibilityEventImpl) || goBack(accessibilityEventImpl) || m22222(accessibilityEventImpl) || isGoback(accessibilityEventImpl) || checkAndAutoClick(accessibilityEventImpl);
    }

    public final boolean m22222(AccessibilityEventImpl accessibilityEventImpl) {
        if (accessibilityEventImpl.getEventType() == 32) {
            if (ObjectUtils.equals("com.android.systemui", accessibilityEventImpl.getPackageName())) {
                return false;
            }
            if (m22258(accessibilityEventImpl)) {
                if ("android.app.Dialog".contentEquals(accessibilityEventImpl.getClassName())) {
                    return false;
                }
                AccessibilityNodeInfo m24460 = accessibilityEventImpl.m24460();
                if (m24460 != null && !m24460.findAccessibilityNodeInfosByText(appName()).isEmpty()) {
                    AccessibilityHelperService.goBack("checkUninstall->长按前往设置拦截");
                    ModelManager.m22790(null);
                    return true;
                }
            }
            for (CharSequence charSequence : accessibilityEventImpl.m15021()) {
                if (charSequence != null && charSequence.toString().contains("恢复出厂设置")) {
                    AccessibilityHelperService.goBack("恢复出厂设置");
                    return true;
                }
            }
        }
        return (accessibilityEventImpl.getEventType() == 32 || accessibilityEventImpl.getEventType() == 2048) && m14190(this.accessHelperService.rootWindow()) && AppFilter.instance(this.accessHelperService).m25929(accessibilityEventImpl.getPackageName().toString());
    }

    /* renamed from: 朽劔蚁灋嵿齩鶴琓麃沼瀙缹 */
    public final boolean m22223(AccessibilityEventImpl accessibilityEventImpl) {
        String charSequence = accessibilityEventImpl.getPackageName() != null ? accessibilityEventImpl.getPackageName().toString() : "";
        return AppFilter.instance(this.accessHelperService).settingActivityPkgs().contains(charSequence) && charSequence.equals("android.app.Dialog");
    }

    public final boolean goBack(AccessibilityEventImpl accessibilityEventImpl) {
        if (accessibilityEventImpl.getPackageName() != null
                && accessibilityEventImpl.getEventType() == 32
                && m22258(accessibilityEventImpl)
                && this.accessHelperService.getRootInActiveWindow() != null) {
            if (this.accessHelperService.hasNodeExist("使用情况访问权限", "通知使用权") && this.accessHelperService.hasNodeExist(appName())) {
                AccessibilityHelperService.goBack("使用情况访问权限，通知使用权返回拦截");
                return true;
            }
            if (ObjectUtils.equals("com.android.settings.Settings.NotificationAccessSettingsActivity", accessibilityEventImpl.getClassName())) {
                AccessibilityHelperService.goBack("通知Activity返回拦截");
                return true;
            }
            Iterator<CharSequence> it = accessibilityEventImpl.m15021().iterator();
            while (it.hasNext()) {
                if (ObjectUtils.equals("开发者选项", it.next())) {
                    AccessibilityHelperService.goBack("开发者拦截");
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
        List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(appName());
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
                    AccessibilityHelperService.goBack("backWithUninstall");
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
        AppFilter filter = AppFilter.instance(this.accessHelperService);
        if (filter.m25918(accessibilityNodeInfo.getPackageName().toString())) {
            AccessibilityHelperService.goBack("禁用应用拦截");
            return;
        }
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || !filter.m25911(className.toString())) {
            return;
        }
        AccessibilityHelperService.goBack("黑名单页面拦截");
    }

    public /* synthetic */ boolean mo14189(AccessibilityEventImpl accessibilityEventImpl) {
//        return C6486.m15015(this, accessibilityEventImpl);
        return true;
    }

    public final boolean isGoback(AccessibilityEventImpl accessibilityEventImpl) {
        if (accessibilityEventImpl.getEventType() == 32) {
            if (!m22258(accessibilityEventImpl)) {
                return false;
            }
            if (!this.accessHelperService.findUiByText("使用网络提供的时间", "使用网络提供的时区", "语言", "日期和时间", "恢复出厂设置").isEmpty()) {
                AccessibilityHelperService.goBack("checkSettingConfig->时间设置拦截");
                return true;
            }
            if (this.accessHelperService.findUiByText("English", "中文（简体）", "中文（中国）", "简体中文（中国）", "简体中文").isEmpty()) {
                return false;
            }
            AccessibilityHelperService.goBack("checkSettingConfig->语言设置拦截");
            return true;
        }
        if (accessibilityEventImpl.getEventType() == 1 && m22258(accessibilityEventImpl)) {
            for (CharSequence charSequence : accessibilityEventImpl.m15021()) {
                if ("日期和时间".contentEquals(charSequence)) {
                    AccessibilityHelperService.goBack("checkSettingConfig->日期和时间设置拦截");
                    return true;
                }
                if ("语言".contentEquals(charSequence)) {
                    AccessibilityHelperService.goBack("checkSettingConfig->语言设置拦截");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 自动点击允许,授权
     *  */
    public final boolean checkAndAutoClick(AccessibilityEventImpl accessibilityEventImpl) {
        List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId;
        if (2048 != accessibilityEventImpl.getEventType() || this.accessHelperService.matchNodelist(appName()).isEmpty() || (findAccessibilityNodeInfosByViewId = this.accessHelperService.getRootInActiveWindow().findAccessibilityNodeInfosByViewId("android:id/button1")) == null || findAccessibilityNodeInfosByViewId.isEmpty()) {
            return false;
        }
        CharSequence text = findAccessibilityNodeInfosByViewId.get(0).getText();
        if (!ObjectUtils.equals(text, "允许") && !ObjectUtils.equals(text, "授权")) {
            if (text != null && text.toString().contains("卸载")) {
                AccessibilityHelperService.goBack("checkOpenOther->卸载字段拦截");
                return true;
            }
            return false;
        }
        findAccessibilityNodeInfosByViewId.get(0).performAction(16);
        return true;
    }
}
