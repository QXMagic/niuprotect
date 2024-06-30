package com.niu.protect.accessibility.auto.service;

import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.AppUtils;
import com.niu.protect.accessibility.auto.service.tmp.ChildConfig;

import java.util.List;
import java.util.Set;

public abstract class BaseAccessibilityMonitor {

    public ChildConfig config;

    public AccessibilityHelperService f12779;

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
    public String f20711;

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
    public String f20712;

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
    public final String m22253() {
        if (ObjectUtils.isNotEmpty((CharSequence) this.f20711)) {
            return this.f20711;
        }
        String appName = AppUtils.getAppName();
        this.f20711 = appName;
        return appName;
    }

    /* renamed from: 櫓昛刓叡賜 */
    public void m22254(ChildConfig childConfig) {
        this.config = childConfig;
    }

    /* renamed from: 睳堋弗粥辊惶 */
    public Set<String> m22255() {
        return AppFilter.m15534(this.f12779).m25930();
    }

    /* renamed from: 祴嚚橺谋肬鬧舘 */
    public final String m22256() {
        if (ObjectUtils.isNotEmpty((CharSequence) this.f20712)) {
            return this.f20712;
        }
        String appPackageName = AppUtils.getAppPackageName();
        this.f20712 = appPackageName;
        return appPackageName;
    }

    /* renamed from: 蝸餺閃喍 */
    public void m22257(AccessibilityHelperService accessibilityHelperService) {
        this.f12779 = accessibilityHelperService;
    }

    /* renamed from: 酸恚辰橔纋黺 */
    public boolean m22258(AccessibilityEventImpl accessibilityEventImpl) {
        return m22255().contains(accessibilityEventImpl.m15019().toString());
    }

    /* renamed from: 镐藻 */
    public ChildConfig m14194() {
        return this.config;
    }
}
