package com.niu.protect.accessibility.auto.service;

import android.os.Build;
import android.view.accessibility.AccessibilityNodeInfo;

import com.niu.protect.core.Constants;

import java.util.List;

public class BasePermission {

    /* renamed from: 肌緭 */
    public OprateManager f14817 = OprateManager.instance();

    public String appName = Constants.APP_NAME;

    public void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /* renamed from: 刻槒唱镧詴, reason: contains not printable characters */
    public void m27626() {
        AccessibilityNodeInfo parent;
        try {
            Thread.sleep(200L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<AccessibilityNodeInfo> m27682 = this.f14817.m27682("android:id/button1");
        if (m27682 == null || m27682.isEmpty()) {
            return;
        }
        AccessibilityNodeInfo accessibilityNodeInfo = m27682.get(0);
        if (accessibilityNodeInfo.isClickable()) {
            if (accessibilityNodeInfo.performAction(16) || (parent = accessibilityNodeInfo.getParent()) == null) {
                return;
            }
            parent.performAction(16);
            return;
        }
        AccessibilityNodeInfo parent2 = accessibilityNodeInfo.getParent();
        if (parent2 != null) {
            parent2.performAction(16);
        }
    }

    /* renamed from: 垡玖 */
    public boolean m16174(String str) {
        List<AccessibilityNodeInfo> m27682 = this.f14817.m27682(str);
        if (m27682 != null && !m27682.isEmpty()) {
            AccessibilityNodeInfo accessibilityNodeInfo = m27682.get(0);
            if (accessibilityNodeInfo.isClickable()) {
                if (accessibilityNodeInfo.performAction(16)) {
                    return true;
                }
                AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
                if (parent != null) {
                    return parent.performAction(16);
                }
            } else {
                AccessibilityNodeInfo parent2 = accessibilityNodeInfo.getParent();
                if (parent2 != null) {
                    return parent2.performAction(16);
                }
            }
        }
        return false;
    }

    /* renamed from: 旞莍癡, reason: contains not printable characters */
    public boolean m27627(String str) {
        List<AccessibilityNodeInfo> m16183 = Build.VERSION.SDK_INT >= 23 ? this.f14817.m16183(str) : null;
        if (m16183 == null || m16183.isEmpty()) {
            return false;
        }
        return m16183.get(0).performAction(16);
    }

    /* renamed from: 灞酞輀攼嵞漁綬迹, reason: contains not printable characters */
    public boolean m27628(String str) {
        List<AccessibilityNodeInfo> m27682 = this.f14817.m27682(str);
        if (m27682 != null && !m27682.isEmpty()) {
            AccessibilityNodeInfo accessibilityNodeInfo = m27682.get(0);
            if (this.f14817.m27692(accessibilityNodeInfo)) {
                if (accessibilityNodeInfo.isChecked() || accessibilityNodeInfo.performAction(16)) {
                    return true;
                }
                AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
                if (parent != null) {
                    return parent.performAction(16);
                }
            }
        }
        return false;
    }

    /* renamed from: 睳堋弗粥辊惶, reason: contains not printable characters */
    public void m27629(boolean z) {
//        PermissionEvent permissionEvent = new PermissionEvent();
//        permissionEvent.setCode(100);
//        permissionEvent.putData("auto_result", z);
//        permissionEvent.post();
        m27630();
    }

    /* renamed from: 祴嚚橺谋肬鬧舘, reason: contains not printable characters */
    public void m27630() {
//        Context safeContext = App.getSafeContext();
//        Intent intent = new Intent(safeContext, (Class<?>) PermissionCheckActivity.class);
//        intent.setFlags(C2860y.f10012a);
//        safeContext.startActivity(intent);
    }

    /* renamed from: 肌緭 */
    public boolean m16175(String str, AccessibilityNodeInfo accessibilityNodeInfo) {
        CharSequence text = accessibilityNodeInfo.getText();
        if (text != null && !text.equals(str)) {
            if (text.toString().contains("(") || text.toString().contains("（")) {
                try {
                    boolean z = !text.toString().contains("(");
                    Thread.sleep((Long.parseLong(text.subSequence(text.toString().indexOf(z ? "（" : "(") + 1, text.toString().indexOf(z ? "）" : ")")).toString()) * 1000) + 500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭, reason: contains not printable characters */
    public void m27631(StringBuilder sb) {
        OprateManager.instance().m27680(sb.toString());
    }

    /* renamed from: 酸恚辰橔纋黺, reason: contains not printable characters */
    public void m27632(boolean z, int i) {
//        PermissionEvent permissionEvent = new PermissionEvent();
//        permissionEvent.setCode(100);
//        permissionEvent.putData("auto_result", z);
//        permissionEvent.putData("id", i);
//        permissionEvent.post();
        m27630();
    }

    public void postResultEvent(boolean z, int i) {
//        PermissionEvent permissionEvent = new PermissionEvent();
//        permissionEvent.setCode(100);
//        permissionEvent.putData("auto_result", z);
//        permissionEvent.putData("id", i);
//        permissionEvent.post();
    }

    
    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐, reason: contains not printable characters */
    public AccessibilityNodeInfo m27633(AccessibilityNodeInfo accessibilityNodeInfo, String str, String str2) {
        if (accessibilityNodeInfo.getClassName().equals(str) && str2.equals(accessibilityNodeInfo.getContentDescription())) {
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        while (childCount > 0) {
            childCount--;
            if (OprateManager.instance().m16184()) {
                return null;
            }
            AccessibilityNodeInfo m27633 = m27633(accessibilityNodeInfo.getChild(childCount), str, str2);
            if (m27633 != null) {
                return m27633;
            }
        }
        return null;
    }
}