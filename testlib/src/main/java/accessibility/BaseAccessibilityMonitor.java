package accessibility;

import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ObjectUtils;

import java.util.List;
import java.util.Set;

import accessibility.lib.ChildConfig;

public abstract class BaseAccessibilityMonitor {

    public ChildConfig config;

    public AccessibilityHelperService accessHelperService;

    public String appName;

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
    public String packageName;

    /* renamed from: 偣炱嘵蟴峗舟轛 */
    public boolean m22252(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText;
        return (accessibilityNodeInfo == null || (findAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(str)) == null || findAccessibilityNodeInfosByText.isEmpty()) ? false : true;
    }

    @Nullable
    /* renamed from: 垡玖 */
    public AccessibilityNodeInfo m14193(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        AccessibilityNodeInfo accessibilityNodeInfo2 = null;
        if (accessibilityNodeInfo == null || accessibilityNodeInfo.getChildCount() <= 0) {
            return null;
        }
        for (int i = 0; i < accessibilityNodeInfo.getChildCount(); i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                if (ObjectUtils.equals(str, child.getViewIdResourceName())) {
                    return child;
                }
                if (accessibilityNodeInfo2 == null) {
                    accessibilityNodeInfo2 = m14193(child, str);
                }
            }
        }
        return accessibilityNodeInfo2;
    }

    /* renamed from: 旞莍癡 */
    public final String appName() {
        if (ObjectUtils.isNotEmpty((CharSequence) this.appName)) {
            return this.appName;
        }
        String appName = AppUtils.getAppName();
        this.appName = appName;
        return appName;
    }

    public void setConfig(ChildConfig childConfig) {
        this.config = childConfig;
    }

    public Set<String> m22255() {
        return AppFilter.instance(this.accessHelperService).settingActivityPkgs();
    }

    public final String getPackageName() {
        if (ObjectUtils.isNotEmpty((CharSequence) this.packageName)) {
            return this.packageName;
        }
        String appPackageName = AppUtils.getAppPackageName();
        this.packageName = appPackageName;
        return appPackageName;
    }

    public void setHelperService(AccessibilityHelperService accessibilityHelperService) {
        this.accessHelperService = accessibilityHelperService;
    }

    public boolean m22258(AccessibilityEventImpl accessibilityEventImpl) {
        return m22255().contains(accessibilityEventImpl.getPackageName().toString());
    }

    public ChildConfig getConfig() {
        return this.config;
    }
}
