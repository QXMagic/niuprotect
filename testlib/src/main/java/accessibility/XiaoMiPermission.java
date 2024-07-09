package accessibility;


import android.os.Build;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.niu.protect.core.Constants;

import java.util.List;

import accessibility.lib.SecureUtils;

public class XiaoMiPermission extends CommonPermission {

    private String f24269;


    public class OnDebugOpen implements OprateManager.IOpChecker {
        public OnDebugOpen() {
        }

        @Override
        public boolean mo27621(@NonNull String str, @NonNull String str2, @NonNull AccessibilityNodeInfo accessibilityNodeInfo) {
            return XiaoMiPermission.this.sleepNodeTime(str2, accessibilityNodeInfo);
        }

        @Override
        public void postResultEvent(boolean z) {
            XiaoMiPermission.this.postResultEvent(Tools.adbEnable(App.mContext), 38);
        }

        @Override // p564.OprateManager.IOpChecker
        public void mo27622(String str, String str2, boolean z) {
//            C4472.m16173(this, str, str2, z);
        }

        @Override // p564.OprateManager.IOpChecker
        public boolean checkNext(@NonNull String str, @NonNull String str2) {
            return ObjectUtils.equals("下一步", str2) || ObjectUtils.equals("允许", str2);
        }
    }


    public class C7239 implements OprateManager.IOpChecker {
        public C7239() {
        }

        @Override // p564.OprateManager.IOpChecker
        public boolean mo27621(String str, String str2, AccessibilityNodeInfo accessibilityNodeInfo) {
            return false;
        }

        @Override // p564.OprateManager.IOpChecker
        public void postResultEvent(boolean z) {
            XiaoMiPermission.this.m27629(true);
        }

        @Override // p564.OprateManager.IOpChecker
        public /* synthetic */ void mo27622(String str, String str2, boolean z) {
//            C4472.m16173(this, str, str2, z);
        }

        @Override // p564.OprateManager.IOpChecker
        public boolean checkNext(String str, String str2) {
            return false;
        }
    }


    public class C4490 implements OprateManager.IOpChecker {
        public C4490() {
        }

        @Override // p564.OprateManager.IOpChecker
        public boolean mo27621(@NonNull String str, @NonNull String str2, @NonNull AccessibilityNodeInfo accessibilityNodeInfo) {
            return false;
        }

        @Override // p564.OprateManager.IOpChecker
        public void postResultEvent(boolean z) {
            XiaoMiPermission.this.m27629(z);
        }

        @Override // p564.OprateManager.IOpChecker
        public /* synthetic */ void mo27622(String str, String str2, boolean z) {
//            C4472.m16173(this, str, str2, z);
        }

        @Override // p564.OprateManager.IOpChecker
        public boolean checkNext(@NonNull String str, @NonNull String str2) {
            return false;
        }
    }


    public class C7240 implements OprateManager.IOpChecker {

        public final /* synthetic */ AccessibilityHelperService helpService;

        public C7240(AccessibilityHelperService accessibilityHelperService) {
            this.helpService = accessibilityHelperService;
        }

        @Override // p564.OprateManager.IOpChecker
        public boolean mo27621(String str, String str2, AccessibilityNodeInfo accessibilityNodeInfo) {
            return XiaoMiPermission.this.sleepNodeTime(str2, accessibilityNodeInfo);
        }

        @Override // p564.OprateManager.IOpChecker
        public void postResultEvent(boolean z) {
            XiaoMiPermission.this.m27629(Tools.m22470(App.mContext));
        }

        @Override // p564.OprateManager.IOpChecker
        public /* synthetic */ void mo27622(String str, String str2, boolean z) {
//            C4472.m16173(this, str, str2, z);
        }

        @Override // p564.OprateManager.IOpChecker
        public boolean checkNext(String str, String str2) {
            if (ObjectUtils.equals("授予通知使用权", str2)) {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!this.helpService.findUiByText("确定", "允许").isEmpty()) {
                    return true;
                }
            }
            if (ObjectUtils.equals("允许", str2)) {
                return true;
            }
            if (!ObjectUtils.equals(XiaoMiPermission.this.f24269, str2)) {
                return false;
            }
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            return !this.helpService.findUiByText("允许").isEmpty();
        }
    }


    public class IpPortChecker implements OprateManager.IOpChecker {
        public IpPortChecker() {
        }

        @Override // p564.OprateManager.IOpChecker
        public boolean mo27621(@NonNull String str, @NonNull String str2, @NonNull AccessibilityNodeInfo accessibilityNodeInfo) {
            return XiaoMiPermission.this.sleepNodeTime(str2, accessibilityNodeInfo);
        }

        @Override // p564.OprateManager.IOpChecker
        public void postResultEvent(boolean z) {
            String str = "-1";
            String code = "";
            if (z) {
                List<AccessibilityNodeInfo> m27682 = XiaoMiPermission.this.operateManager.findNodeById("com.android.settings:id/pairing_code");
                if (m27682 != null && !m27682.isEmpty()) {
                    CharSequence text = m27682.get(0).getText();
                    if (ObjectUtils.isNotEmpty(text)) {
                        code = text.toString();
                    }
                }
                List<AccessibilityNodeInfo> m276822 = XiaoMiPermission.this.operateManager.findNodeById("com.android.settings:id/ip_addr");
                if (m276822 != null && !m276822.isEmpty()) {
                    CharSequence text2 = m276822.get(0).getText();
                    if (ObjectUtils.isNotEmpty(text2)) {
                        str = text2.toString();
                        String[] split = str.split(":");
                        if (split.length == 2) {
                            str = split[1];
                        }
                    }
                }
            }
            PermissionEvent permissionEvent = new PermissionEvent();
            permissionEvent.setCode(102);
            permissionEvent.putData("code", code);
            permissionEvent.putData("port", str);
            permissionEvent.post();
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
        public /* synthetic */ void mo27622(String str, String str2, boolean z) {
//            C4472.m16173(this, str, str2, z);
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
        public boolean checkNext(@NonNull String str, @NonNull String str2) {
            return ObjectUtils.equals("下一步", str2) || ObjectUtils.equals("允许", str2);
        }
    }


    public class C7242 implements OprateManager.IOpChecker {
        public C7242() {
        }

        @Override // p564.OprateManager.IOpChecker
        public boolean mo27621(String str, String str2, AccessibilityNodeInfo accessibilityNodeInfo) {
            return false;
        }

        @Override // p564.OprateManager.IOpChecker
        public void postResultEvent(boolean z) {
            XiaoMiPermission.this.m27629(z);
        }

        @Override // p564.OprateManager.IOpChecker
        public /* synthetic */ void mo27622(String str, String str2, boolean z) {
//            C4472.m16173(this, str, str2, z);
        }

        @Override // p564.OprateManager.IOpChecker
        public boolean checkNext(String str, String str2) {
            if (ObjectUtils.equals("始终允许", str2)) {
                return true;
            }
            return ObjectUtils.equals(XiaoMiPermission.this.f24269, str2);
        }
    }


    public class C7243 implements OprateManager.IOpChecker {
        public C7243() {
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 刻槒唱镧詴 */
        public boolean mo27621(String str, String str2, AccessibilityNodeInfo accessibilityNodeInfo) {
            return XiaoMiPermission.this.sleepNodeTime(str2, accessibilityNodeInfo);
        }

        @Override // p564.OprateManager.IOpChecker
        public void postResultEvent(boolean z) {
            XiaoMiPermission.this.m27629(PermissionAutoManager.checkIndex(App.mContext, 40));
        }

        @Override // p564.OprateManager.IOpChecker
        public /* synthetic */ void mo27622(String str, String str2, boolean z) {
//            C4472.m16173(this, str, str2, z);
        }

        @Override // p564.OprateManager.IOpChecker
        public boolean checkNext(String str, String str2) {
            return false;
        }
    }


    public class C7244 implements OprateManager.InterfaceC7203 {
        public C7244() {
        }

        @Override // p564.OprateManager.InterfaceC7203
        public boolean mo27653(@NonNull String str) {
            return false;
        }

        @Override // p564.OprateManager.InterfaceC7203
        public boolean mo16180(@NonNull String str, @NonNull String str2, boolean z) {
            if (!z && ObjectUtils.equals(str2, XiaoMiPermission.this.f24269)) {
                return true;
            }
            if (!ObjectUtils.equals(str2, "允许") || !z || !AccessibilityHelperService.instance.findUiByText("确定").isEmpty()) {
                return false;
            }
            XiaoMiPermission.this.operateManager.m27669(true);
            return true;
        }

        @Override // p564.OprateManager.InterfaceC7203
        public boolean mo27654(@NonNull String str, @NonNull String str2) {
            return false;
        }
    }


    public class OnCheckDev implements OprateManager.IOpChecker {

        /* renamed from: 肌緭 */
        public final /* synthetic */ int f14891;

        public OnCheckDev(int i) {
            this.f14891 = i;
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 刻槒唱镧詴 */
        public boolean mo27621(String str, String str2, AccessibilityNodeInfo accessibilityNodeInfo) {
            return false;
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 肌緭 */
        public void postResultEvent(boolean z) {
            if (Tools.isDeveloperOpen()) {
                if (this.f14891 == 39) {
                    XiaoMiPermission.this.openDebuger();
                    return;
                } else {
                    XiaoMiPermission.this.mo27642();
                    return;
                }
            }
            XiaoMiPermission.this.postResultEvent(false, this.f14891);
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
        public /* synthetic */ void mo27622(String str, String str2, boolean z) {
//            C4472.m16173(this, str, str2, z);
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
        public boolean checkNext(String str, String str2) {
            return false;
        }
    }

    /* JADX WARN: Classes with same name are omitted:
  classes5.dex
 */
    /* compiled from: XiaoMiPermission.java */
    /* renamed from: 髿芫祜邕測.鑭撇糁綖浓緗轟鱼萟磿焈$瞙餃莴埲, reason: contains not printable characters */
    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
    public class C7246 implements OprateManager.IOpChecker {
        public C7246() {
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 刻槒唱镧詴 */
        public boolean mo27621(String str, String str2, AccessibilityNodeInfo accessibilityNodeInfo) {
            return false;
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 肌緭 */
        public void postResultEvent(boolean z) {
            XiaoMiPermission.this.m27629(z);
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
        public /* synthetic */ void mo27622(String str, String str2, boolean z) {
//            C4472.m16173(this, str, str2, z);
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
        public boolean checkNext(String str, String str2) {
            return false;
        }
    }

    /* JADX WARN: Classes with same name are omitted:
  classes5.dex
 */
    /* compiled from: XiaoMiPermission.java */
    /* renamed from: 髿芫祜邕測.鑭撇糁綖浓緗轟鱼萟磿焈$祴嚚橺谋肬鬧舘, reason: contains not printable characters */
    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
    public class CheckerDebuger implements OprateManager.InterfaceC7203 {
        public CheckerDebuger() {
        }

        @Override // p564.OprateManager.InterfaceC7203
        /* renamed from: 刻槒唱镧詴 */
        public boolean mo27653(@NonNull String str) {
            return false;
        }

        @Override // p564.OprateManager.InterfaceC7203
        /* renamed from: 肌緭 */
        public boolean mo16180(@NonNull String str, @NonNull String str2, boolean z) {
            if (!str.equals("check")) {
                return false;
            }
            if (str2.equals("无线调试")) {
                XiaoMiPermission.this.sleep(200L);
                XiaoMiPermission.this.m27627("始终允许");
                XiaoMiPermission.this.sleep(200L);
                XiaoMiPermission.this.m16174("android:id/button1");
                try {
                    Thread.sleep(500L);
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
            if (!str2.equals("android:id/switch_widget#无线调试")) {
                return false;
            }
            XiaoMiPermission.this.sleep(200L);
            XiaoMiPermission.this.m16174("android:id/button1");
            XiaoMiPermission.this.m27627("确定");
            XiaoMiPermission.this.sleep(500L);
            XiaoMiPermission.this.m27628("android:id/switch_widget");
            try {
                Thread.sleep(500L);
                return false;
            } catch (Exception e2) {
                e2.printStackTrace();
                return false;
            }
        }

        @Override // p564.OprateManager.InterfaceC7203
        /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
        public boolean mo27654(@NonNull String str, @NonNull String str2) {
            return false;
        }
    }

    /* JADX WARN: Classes with same name are omitted:
  classes5.dex
 */
    /* compiled from: XiaoMiPermission.java */
    /* renamed from: 髿芫祜邕測.鑭撇糁綖浓緗轟鱼萟磿焈$綩私 */
    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
    public class C4491 implements OprateManager.IOpChecker {
        public C4491() {
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 刻槒唱镧詴 */
        public boolean mo27621(String str, String str2, AccessibilityNodeInfo accessibilityNodeInfo) {
            return false;
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 肌緭 */
        public void postResultEvent(boolean z) {
            XiaoMiPermission.this.m27629(z);
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
        public /* synthetic */ void mo27622(String str, String str2, boolean z) {
//            C4472.m16173(this, str, str2, z);
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
        public boolean checkNext(String str, String str2) {
            return false;
        }
    }

    /* JADX WARN: Classes with same name are omitted:
  classes5.dex
 */
    /* compiled from: XiaoMiPermission.java */
    /* renamed from: 髿芫祜邕測.鑭撇糁綖浓緗轟鱼萟磿焈$耣怳匮色紝参凵蛴纆勚躄, reason: contains not printable characters */
    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
    public class C7248 implements OprateManager.IOpChecker {
        public C7248() {
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 刻槒唱镧詴 */
        public boolean mo27621(@NonNull String str, @NonNull String str2, @NonNull AccessibilityNodeInfo accessibilityNodeInfo) {
            return false;
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 肌緭 */
        public void postResultEvent(boolean z) {
            XiaoMiPermission.this.m27629(!Tools.m22473(App.mContext));
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
        public /* synthetic */ void mo27622(String str, String str2, boolean z) {
//            C4472.m16173(this, str, str2, z);
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
        public boolean checkNext(@NonNull String str, @NonNull String str2) {
            return false;
        }
    }

    /* JADX WARN: Classes with same name are omitted:
  classes5.dex
 */
    /* compiled from: XiaoMiPermission.java */
    /* renamed from: 髿芫祜邕測.鑭撇糁綖浓緗轟鱼萟磿焈$肌緭 */
    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
    public class C4492 implements OprateManager.InterfaceC7203 {

        /* renamed from: 肌緭 */
        public final /* synthetic */ AccessibilityHelperService f14896;

        public C4492(AccessibilityHelperService accessibilityHelperService) {
            this.f14896 = accessibilityHelperService;
        }

        @Override // p564.OprateManager.InterfaceC7203
        /* renamed from: 刻槒唱镧詴 */
        public boolean mo27653(@NonNull String str) {
            return false;
        }

        @Override // p564.OprateManager.InterfaceC7203
        /* renamed from: 肌緭 */
        public boolean mo16180(@NonNull String str, @NonNull String str2, boolean z) {
            if (!ObjectUtils.equals(str2, "允许") || !z || !this.f14896.findUiByText("确定").isEmpty()) {
                return false;
            }
            XiaoMiPermission.this.operateManager.m27669(true);
            return true;
        }

        @Override // p564.OprateManager.InterfaceC7203
        /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
        public boolean mo27654(@NonNull String str, @NonNull String str2) {
            return false;
        }
    }

    /* JADX WARN: Classes with same name are omitted:
  classes5.dex
 */
    /* compiled from: XiaoMiPermission.java */
    /* renamed from: 髿芫祜邕測.鑭撇糁綖浓緗轟鱼萟磿焈$葋申湋骶映鍮秄憁鎓羭, reason: contains not printable characters */
    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
    public class C7249 implements OprateManager.IOpChecker {
        public C7249() {
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 刻槒唱镧詴 */
        public boolean mo27621(String str, String str2, AccessibilityNodeInfo accessibilityNodeInfo) {
            return false;
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 肌緭 */
        public void postResultEvent(boolean z) {
            XiaoMiPermission.this.m27629(z);
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
        public /* synthetic */ void mo27622(String str, String str2, boolean z) {
//            C4472.m16173(this, str, str2, z);
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
        public boolean checkNext(String str, String str2) {
            return false;
        }
    }


    public class PopWindow implements OprateManager.IOpChecker {
        public PopWindow() {
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 刻槒唱镧詴 */
        public boolean mo27621(String str, String str2, AccessibilityNodeInfo accessibilityNodeInfo) {
            return false;
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 肌緭 */
        public void postResultEvent(boolean z) {
            XiaoMiPermission.this.m27629(z);
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
        public /* synthetic */ void mo27622(String str, String str2, boolean z) {
//            C4472.m16173(this, str, str2, z);
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
        public boolean checkNext(String str, String str2) {
            return false;
        }
    }


    public class C7251 implements OprateManager.IOpChecker {
        public C7251() {
        }

        @Override // p564.OprateManager.IOpChecker
        public boolean mo27621(@NonNull String str, @NonNull String str2, @NonNull AccessibilityNodeInfo accessibilityNodeInfo) {
            return false;
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 肌緭 */
        public void postResultEvent(boolean z) {
            if (!z) {
                XiaoMiPermission.this.m27629(SecureUtils.isOwnerApp() && Build.VERSION.SDK_INT > 28);
            } else {
                XiaoMiPermission.this.m27629(true);
            }
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
        public /* synthetic */ void mo27622(String str, String str2, boolean z) {
//            C4472.m16173(this, str, str2, z);
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
        public boolean checkNext(@NonNull String str, @NonNull String str2) {
            return false;
        }
    }

    /* JADX WARN: Classes with same name are omitted:
  classes5.dex
 */
    /* compiled from: XiaoMiPermission.java */
    /* renamed from: 髿芫祜邕測.鑭撇糁綖浓緗轟鱼萟磿焈$鑭撇糁綖浓緗轟鱼萟磿焈, reason: contains not printable characters */
    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
    public class C7252 implements OprateManager.InterfaceC7203 {
        public C7252() {
        }

        @Override // p564.OprateManager.InterfaceC7203
        /* renamed from: 刻槒唱镧詴 */
        public boolean mo27653(@NonNull String str) {
            return false;
        }

        @Override // p564.OprateManager.InterfaceC7203
        /* renamed from: 肌緭 */
        public boolean mo16180(@NonNull String str, @NonNull String str2, boolean z) {
            return false;
        }

        @Override // p564.OprateManager.InterfaceC7203
        /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
        public boolean mo27654(@NonNull String str, @NonNull String str2) {
            List<AccessibilityNodeInfo> m16183;
            if (Build.VERSION.SDK_INT >= 23 && (m16183 = XiaoMiPermission.this.operateManager.m16183(str2)) != null && !m16183.isEmpty()) {
                for (AccessibilityNodeInfo accessibilityNodeInfo : m16183) {
                    if ("激活".contentEquals(accessibilityNodeInfo.getText())) {
                        return accessibilityNodeInfo.performAction(16);
                    }
                }
            }
            return false;
        }
    }


    public class OpenMiUI implements OprateManager.IOpChecker {

        /* renamed from: 肌緭 */
        public final /* synthetic */ int f14901;

        public OpenMiUI(int i) {
            this.f14901 = i;
        }

        public /* synthetic */ void m16195(int i) {
            XiaoMiPermission.this.openDev(i);
        }

        @Override // p564.OprateManager.IOpChecker
        public boolean mo27621(String str, String str2, AccessibilityNodeInfo accessibilityNodeInfo) {
            return false;
        }

        @Override // p564.OprateManager.IOpChecker
        public void postResultEvent(boolean z) {
            if (z) {
                Tools.openDevelopment();
                final int i = this.f14901;
                ThreadUtils.runOnUiThreadDelayed(new Runnable() {
                    @Override // java.lang.Runnable
                    public final void run() {
                        OpenMiUI.this.m16195(i);
                    }
                }, 1000L);
                return;
            }
            XiaoMiPermission.this.postResultEvent(false, this.f14901);
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
        public /* synthetic */ void mo27622(String str, String str2, boolean z) {
//            C4472.m16173(this, str, str2, z);
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
        public boolean checkNext(String str, String str2) {
            return false;
        }
    }

    /* JADX WARN: Classes with same name are omitted:
  classes5.dex
 */
    /* compiled from: XiaoMiPermission.java */
    /* renamed from: 髿芫祜邕測.鑭撇糁綖浓緗轟鱼萟磿焈$陟瓠魒踱褢植螉嚜, reason: contains not printable characters */
    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
    public class C7253 implements OprateManager.IOpChecker {
        public C7253() {
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 刻槒唱镧詴 */
        public boolean mo27621(@NonNull String str, @NonNull String str2, @NonNull AccessibilityNodeInfo accessibilityNodeInfo) {
            return XiaoMiPermission.this.sleepNodeTime(str2, accessibilityNodeInfo);
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 肌緭 */
        public void postResultEvent(boolean z) {
            XiaoMiPermission.this.m27629(Tools.isOwner(App.mContext));
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
        public /* synthetic */ void mo27622(String str, String str2, boolean z) {
//            C4472.m16173(this, str, str2, z);
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
        public boolean checkNext(@NonNull String str, @NonNull String str2) {
            if (ObjectUtils.equals("授予", str2)) {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!AccessibilityHelperService.instance.findUiByText("确定", "允许").isEmpty()) {
                    return true;
                }
            } else if (ObjectUtils.equals("允许", str2) && Build.VERSION.SDK_INT >= 23) {
                List<AccessibilityNodeInfo> m16183 = XiaoMiPermission.this.operateManager.m16183("确定");
                if (m16183 == null || m16183.isEmpty()) {
                    return false;
                }
                return m16183.get(0).performAction(16);
            }
            if (!ObjectUtils.equals(XiaoMiPermission.this.f24269, str2)) {
                return false;
            }
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            return !AccessibilityHelperService.instance.findUiByText("允许").isEmpty();
        }
    }

    /* JADX WARN: Classes with same name are omitted:
  classes5.dex
 */
    /* compiled from: XiaoMiPermission.java */
    /* renamed from: 髿芫祜邕測.鑭撇糁綖浓緗轟鱼萟磿焈$鞈鵚主瀭孩濣痠閕讠陲檓敐, reason: contains not printable characters */
    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
    public class C7254 implements OprateManager.IOpChecker {
        public C7254() {
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 刻槒唱镧詴 */
        public boolean mo27621(String str, String str2, AccessibilityNodeInfo accessibilityNodeInfo) {
            return XiaoMiPermission.this.sleepNodeTime(str2, accessibilityNodeInfo);
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 肌緭 */
        public void postResultEvent(boolean z) {
            XiaoMiPermission.this.m27629(Tools.m14279(App.mContext));
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
        public /* synthetic */ void mo27622(String str, String str2, boolean z) {
//            C4472.m16173(this, str, str2, z);
        }

        @Override // p564.OprateManager.IOpChecker
        /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
        public boolean checkNext(String str, String str2) {
            if (!ObjectUtils.equals("允许查看", str2)) {
                return ObjectUtils.equals("允许", str2);
            }
            try {
                Thread.sleep(1500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return !AccessibilityHelperService.instance.findUiByText("确定", "允许").isEmpty();
        }
    }

    @Override // p564.CommonPermission
    /* renamed from: 卝閄侸靤溆鲁扅 */
    public final boolean mo27700() {
        AccessibilityHelperService accessibilityHelperService = AccessibilityHelperService.instance;
        boolean z = false;
        if (accessibilityHelperService == null) {
            m27629(false);
            return true;
        }
        float m22494 = Tools.version();
        StringBuilder sb = new StringBuilder();
        if (m22494 < 12.0f) {
            sb.append("uncheck->长按电源键0.5秒\n");
        } else {
            AccessibilityNodeInfo m19978 = accessibilityHelperService.findNodeInfoByText("语音唤醒");
            if (m19978 != null && m19978.getParent() != null && PermissionAutoManager.m24454(m19978.getParent()) != null) {
                z = true;
            }
            if (z) {
                sb.append("uncheck->语音唤醒\n");
            } else {
                sb.append("click->语音唤醒\n");
                sb.append("uncheck->语音唤醒\n");
                sb.append("back->\n");
            }
            if (Tools.version() >= 14.0f) {
                sb.append("uncheck->电源键唤醒\n");
                sb.append("click->其他方式唤醒\n");
                sb.append("uncheck->线控耳机唤醒\n");
                sb.append("uncheck->蓝牙耳机唤醒\n");
                sb.append("sleep->200\n");
                sb.append("click->无");
            } else {
                sb.append("click->按键唤醒\n");
                if (Tools.version() >= 13.0f) {
                    sb.append("uncheck->长按电源键\n");
                } else {
                    sb.append("click->无\n");
                }
                sb.append("back->\n");
                sb.append("click->耳机唤醒\n");
                sb.append("uncheck->线控耳机唤醒\n");
                sb.append("uncheck->蓝牙耳机唤醒\n");
                sb.append("back->\n");
            }
        }
        this.operateManager.setOprator(new C4491());
        runCmd(sb);
        return true;
    }

    @Override // p564.CommonPermission
    /* renamed from: 哠畳鲜郣新剙鳰活茙郺嵝 */
    public final boolean mo27642() {
        if (AccessibilityHelperService.instance == null) {
            postResultEvent(false, 36);
            return true;
        }
        StringBuilder sb = new StringBuilder();
        float m22494 = Tools.version();
        sb.append("uncheck->安全设置\n");
        sb.append("check->安全设置\n");
        sb.append("sleep->200\n");
        sb.append("click->下一步\n");
        sb.append("sleep->200\n");
        sb.append("click->下一步\n");
        sb.append("sleep->200\n");
        sb.append("click->允许\n");
        sb.append("sleep->200\n");
        if (m22494 >= 13.0f) {
            sb.append("click->无线调试\n");
            sb.append("sleep->200\n");
            sb.append("check->android:id/switch_widget#无线调试\n");
            sb.append("sleep->200\n");
            sb.append("click->使用配对码配对设备\n");
        } else {
            sb.append("check->无线调试\n");
            sb.append("sleep->200\n");
            sb.append("click->无线调试\n");
            sb.append("sleep->200\n");
            sb.append("click->使用配对码配对设备\n");
        }
        this.operateManager.setOprator(new IpPortChecker());
        this.operateManager.setOp2(new CheckerDebuger());
        runCmd(sb);
        return true;
    }

    @Override // p564.CommonPermission
    /* renamed from: 唌橅咟 */
    public final boolean mo27624() {
        AccessibilityHelperService accessibilityHelperService = AccessibilityHelperService.instance;
        if (accessibilityHelperService == null) {
            m27629(false);
            return true;
        }
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        if (accessibilityHelperService.hasNodeExist("授予通知使用权")) {
            sb.append("click->");
            sb.append(this.appName);
            sb.append("\nsleep->1000\n");
            sb.append("check->授予通知使用权\nclick->我已知晓\nclick->确定");
        } else if (Tools.isXiaomi()) {
            sb.append("click->");
            sb.append(this.appName);
            sb.append("\nsleep->1000\n");
            sb.append("click->授予通知使用权\nclick->我已知晓\nclick->确定");
        } else {
            float m22494 = Tools.version();
            if (m22494 >= 13.0f || Tools.isXiaomi()) {
                sb.append("click->");
                sb.append(this.appName);
                sb.append("\nsleep->1000\n");
                sb.append("check->授予通知使用权\nclick->我已知晓\nclick->确定");
            } else if (m22494 >= 12.5f) {
                sb.append("click->");
                sb.append(this.appName);
                sb.append("\nsleep->1000\n");
                sb.append("click->我已知晓\nclick->确定");
            } else {
                sb.append("check->");
                sb.append(this.appName);
                sb.append("\nsleep->1000\n");
                sb.append("click->允许");
                sb.append("\nclick->我已知晓\nclick->确定");
            }
        }
        this.operateManager.setOprator(new C7240(accessibilityHelperService));
        this.operateManager.setOp2(new C4492(accessibilityHelperService));
        runCmd(sb);
        return true;
    }

    @Override // p564.CommonPermission
    /* renamed from: 壋劘跆貭澴綄秽攝煾訲 */
    public boolean mo27701() {
        if (AccessibilityHelperService.instance == null) {
            m27629(false);
            return true;
        }
        StringBuilder sb = new StringBuilder();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        float m22494 = Tools.version();
        if (m22494 <= 10.0f) {
            sb.append("sleep->100\n");
            sb.append("click->全面屏\n");
            sb.append("sleep->100\n");
            sb.append("click->经典导航键\n");
            sb.append("back->\n");
        } else if (m22494 <= 12.0f) {
            sb.append("sleep->100\n");
            sb.append("click->更多设置\n");
            sb.append("sleep->100\n");
            sb.append("click->全面屏\n");
            sb.append("sleep->100\n");
            sb.append("click->经典导航键\n");
            sb.append("back->\n");
            sb.append("back->\n");
        } else {
            sb.append("sleep->100\n");
            sb.append("click->桌面\n");
            sb.append("sleep->100\n");
            sb.append("click->系统导航方式\n");
            sb.append("sleep->100\n");
            sb.append("click->经典导航键\n");
            sb.append("back->\n");
            sb.append("back->\n");
        }
        OprateManager.instance().setOprator(new C7248());
        runCmd(sb);
        return true;
    }

    @Override // p564.CommonPermission
    /* renamed from: 彻薯铏螙憣欖愡鼭 */
    public final boolean mo27643() {
        if (AccessibilityHelperService.instance == null) {
            m27629(false);
            return true;
        }
        StringBuilder sb = new StringBuilder("click->无限制");
        this.operateManager.setOprator(new C7249());
        runCmd(sb);
        return true;
    }

    @Override // p564.CommonPermission
    /* renamed from: 朽劔蚁灋嵿齩鶴琓麃沼瀙缹 */
    public boolean mo27645() {
        if (Tools.version() >= 12.5d) {
            sleep(1000L);
            if (!m16174("android:id/button3")) {
                m27627("继续");
            }
            sleep(5000L);
            if (!m27628("com.miui.packageinstaller:id/install_checked")) {
                m27627("已了解此安装包未经安全检测");
            }
            sleep(200L);
            if (m16174("com.miui.packageinstaller:id/second_button")) {
                return true;
            }
            m27627("继续更新");
            return true;
        }
        sleep(3000L);
        if (m16174("com.miui.packageinstaller:id/text")) {
            return true;
        }
        m27627("继续");
        return true;
    }

    @Override // p564.CommonPermission
    /* renamed from: 杹藗瀶姙笻件稚嵅蔂 */
    public final boolean mo27618() {
        if (AccessibilityHelperService.instance == null) {
            m27629(false);
            return true;
        }
        String appName = Constants.APP_NAME;
        StringBuilder sb = new StringBuilder();
        sb.append("sleep->200\n");
        sb.append("click->优化加速\n");
        sb.append("sleep->200\n");
        sb.append("click->锁定任务\n");
        sb.append("sleep->200\n");
        sb.append("check->");
        sb.append(appName);
        OprateManager.instance().setOprator(new C7246());
        runCmd(sb);
        return true;
    }

    @Override // p564.CommonPermission
    /* renamed from: 枩棥钰蕎睨領喀镎遣跄 */
    public boolean openDebuger() {
        if (AccessibilityHelperService.instance == null) {
            postResultEvent(false, 36);
            return true;
        }
        float version = Tools.version();
        StringBuilder sb = new StringBuilder();
        sb.append("uncheck->安全设置\n");
        sb.append("check->安全设置\n");
        sb.append("sleep->200\n");
        sb.append("click->下一步\n");
        sb.append("sleep->200\n");
        sb.append("click->下一步\n");
        sb.append("sleep->200\n");
        sb.append("click->允许\n");
        sb.append("sleep->200\n");
        sb.append("uncheck->USB 调试\n");
        sb.append("check->USB 调试\n");
        sb.append("sleep->200\n");
        if (version >= 12.5d) {
            sb.append("click->我已知晓\n");
            sb.append("sleep->200\n");
            sb.append("click->确定\n");
        } else {
            sb.append("click->允许");
        }
        this.operateManager.setOprator(new OnDebugOpen());
        runCmd(sb);
        return true;
    }

    @Override // p564.CommonPermission
    /* renamed from: 瞙餃莴埲 */
    public boolean mo27648() {
        m16174(Constants.APPLICATION_ID + ":id/btn_ok");
        sleep(1000L);
        boolean m16174 = m16174("com.lbe.security.miui:id/permission_allow_button");
        boolean m27627 = m27627("始终允许");
        if (!m16174 && !m27627) {
            return true;
        }
        m16174(Constants.APPLICATION_ID + ":id/btn_ok");
        return true;
    }

    @Override // p564.CommonPermission
    /* renamed from: 礱咄頑 */
    public final boolean mo27705() {
        AccessibilityHelperService accessibilityHelperService = AccessibilityHelperService.instance;
        if (accessibilityHelperService == null) {
            m27629(false);
            return true;
        }
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<AccessibilityNodeInfo> m19977 = accessibilityHelperService.findUiByText("照明弹");
        StringBuilder sb = new StringBuilder();
        sb.append("sleep->200\n");
        if (m19977.isEmpty()) {
            sb.append("uncheck->权限使用提醒");
        } else {
            sb.append("click->照明弹\n");
            sb.append("sleep->200\n");
            sb.append("uncheck->应用敏感行为提醒");
        }
        this.operateManager.setOprator(new C7239());
        runCmd(sb);
        return true;
    }

    @Override // p564.CommonPermission
    /* renamed from: 綏牽躵糽稰烳俠垳襨捈桏鷋 */
    public boolean mo27649() {
        return false;
    }

    @Override // p564.CommonPermission
    /* renamed from: 綩私 */
    public boolean mo16179() {
        if (AccessibilityHelperService.instance == null) {
            m27629(false);
            return true;
        }
        StringBuilder sb = new StringBuilder();
        if (Tools.isXiaomi()) {
            sb.append("click->" + this.appName + "\n");
            sb.append("sleep->200\n");
            sb.append("click->允许来自此来源的应用\n");
            sb.append("sleep->200\n");
            sb.append("click->我已知晓\n");
            sb.append("sleep->200\n");
            sb.append("click->确定\n");
        } else {
            float m22494 = Tools.version();
            sb.append("click->");
            sb.append(this.appName);
            sb.append("\n");
            sb.append("sleep->200\n");
            sb.append("check->android:id/switch_widget#允许\n");
            sb.append("sleep->200\n");
            if (m22494 >= 12.5d || Tools.isXiaomi()) {
                sb.append("click->我已知晓\n");
                sb.append("sleep->200\n");
                sb.append("click->确定\n");
            } else if (m22494 > 10.0f) {
                sb.append("click->允许");
            }
        }
        this.operateManager.setOprator(new C7243());
        runCmd(sb);
        return true;
    }

    @Override // p564.CommonPermission
    /* renamed from: 纩慐 */
    public final boolean mo16171() {
        String str;
        AccessibilityHelperService accessibilityHelperService = AccessibilityHelperService.instance;
        if (accessibilityHelperService == null) {
            m27629(false);
            return true;
        }
        if (Tools.isXiaomi()) {
            str = "click->权限管理\nsleep->100\nclick->其他权限\nsleep->100\nclick->显示悬浮窗\nsleep->100\nclick->始终允许";
        } else if (Tools.version() >= 14.0f) {
            str = "click->权限管理\nclick->显示悬浮窗\nsleep->100\nclick->始终允许";
        } else {
            int i = Build.VERSION.SDK_INT;
            if (i >= 29) {
                str = "click->" + this.appName + "\ncheck->允许显示";
            } else if (i >= 23) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (accessibilityHelperService.hasNodeExist("允许出现")) {
                    str = "check->允许出现";
                } else {
                    str = accessibilityHelperService.hasNodeExist("允许显示") ? "check->允许显示" : "click->显示悬浮窗\nclick->允许";
                }
            } else {
                str = "click->悬浮窗\nclick->始终允许";
            }
        }
        this.operateManager.setOprator(new C7242());
        this.operateManager.lateRun(str);
        return true;
    }

    @Override // p564.CommonPermission
    /* renamed from: 耣怳匮色紝参凵蛴纆勚躄 */
    public final boolean mo27650() {
        String str;
        if (AccessibilityHelperService.instance == null) {
            m27629(false);
            return true;
        }
        if (Tools.isPad()) {
            str = "click->授权管理\nclick->自启动管理\ncheck->" + Constants.APP_NAME;
        } else {
            str = "check->" + Constants.APP_NAME;
        }
        this.operateManager.setOprator(new C7251());
        this.operateManager.lateRun(str);
        return true;
    }

    /* renamed from: 蘫聫穯搞哪曁雥贀忬琖嶹, reason: contains not printable characters */
    public final void openDev(int i) {
        if (AccessibilityHelperService.instance == null) {
            postResultEvent(false, i);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("check->开启开发者选项\n");
        sb.append("sleep->500\n");
        sb.append("click->确定\n");
        sb.append("sleep->100\n");
        this.operateManager.setOprator(new OnCheckDev(i));
        runCmd(sb);
    }

    @Override // p564.CommonPermission
    /* renamed from: 辒迳圄袡皪郞箟 */
    public boolean clickMiUI(int i) {
        if (AccessibilityHelperService.instance == null) {
            postResultEvent(false, i);
            return true;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("click->我的设备\n");
        sb.append("sleep->100\n");
        sb.append("click->全部参数\n");
        sb.append("sleep->100\n");
        for (int i2 = 0; i2 < 10; i2++) {
            sb.append("click->MIUI 版本\n");
        }
        this.operateManager.setOprator(new OpenMiUI(i));
        runCmd(sb);
        return true;
    }

    @Override // p564.CommonPermission
    /* renamed from: 销薞醣戔攖餗 */
    public boolean setAppManager() {
        AccessibilityHelperService accessibilityHelperService = AccessibilityHelperService.instance;
        if (accessibilityHelperService == null) {
            m27629(false);
            return true;
        }
        StringBuilder sb = new StringBuilder();
        if (Tools.isXiaomi()) {
            sb.append("click->启用此设备管理应用\n");
            sb.append("sleep->100\n");
            sb.append("click->我已知晓\n");
            sb.append("sleep->200\n");
            sb.append("click->确定\n");
        } else {
            float m22494 = Tools.version();
            AccessibilityNodeInfo m19978 = accessibilityHelperService.findNodeInfoByText((Tools.isPad() || m22494 >= 10.0f) ? "启用此设备管理应用" : "激活此设备管理员");
            if (m19978 == null) {
                m27629(false);
                return true;
            }
            if (accessibilityHelperService.performAction(m19978)) {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (accessibilityHelperService.hasNodeExist("我已知晓")) {
                    sb.append("click->我已知晓\nclick->确定");
                } else if (accessibilityHelperService.hasNodeExist("授予")) {
                    sb.append("check->授予\nclick->确定");
                } else if (!Tools.isPad()) {
                    if (m22494 >= 10.0f) {
                        sb.append("click->下一步\nclick->下一步\nclick->允许");
                    } else {
                        sb.append("click->允许");
                    }
                }
            } else {
                m27629(false);
            }
        }
        OprateManager.instance().setOprator(new C7253());
        this.operateManager.setOp2(new C7252());
        runCmd(sb);
        return true;
    }

    @Override // p564.CommonPermission
    /* renamed from: 陟瓠魒踱褢植螉嚜 */
    public final boolean setPopWindow() {
        if (AccessibilityHelperService.instance == null) {
            m27629(false);
            return true;
        }
        StringBuilder sb = new StringBuilder();
        if (Tools.version() != 12.0f && !Tools.isXiaomi()) {
            sb.append("click->后台弹出界面");
            sb.append("\nsleep->1000\n");
            sb.append("click->允许");
        } else {
            sb.append("click->其他权限\n");
            sb.append("sleep->200\n");
            sb.append("click->后台弹出界面\n");
            sb.append("sleep->1000\n");
            sb.append("click->始终允许");
        }
        this.operateManager.setOprator(new PopWindow());
        runCmd(sb);
        return true;
    }

    @Override // p564.CommonPermission
    /* renamed from: 鞲冇 */
    public final boolean allowSee() {
        if (AccessibilityHelperService.instance == null) {
            m27629(false);
            return true;
        }
        StringBuilder sb = new StringBuilder();
        if (Tools.isXiaomi()) {
            sb.append("click->允许查看\nclick->我已知晓\nclick->确定");
        } else {
            float m22494 = Tools.version();
            if (m22494 >= 12.5f) {
                sb.append("sleep->1500\n");
                sb.append("click->");
                sb.append(this.appName);
                sb.append("\n");
                sb.append("check->允许查看\nclick->我已知晓\nclick->确定");
            } else if (m22494 >= 12.0f) {
                sb.append("sleep->1500\n");
                sb.append("click->");
                sb.append(this.appName);
                sb.append("\n");
                sb.append("check->允许查看\nclick->允许\nclick->我已知晓\nclick->确定");
            } else {
                sb.append("sleep->1500\n");
                sb.append("click->");
                sb.append(this.appName);
                sb.append("\n");
                sb.append("check->允许");
            }
        }
        this.operateManager.setOprator(new C7254());
        this.operateManager.setOp2(new C7244());
        runCmd(sb);
        return true;
    }

    @Override // p564.CommonPermission
    public final boolean setAllowAll() {
        if (AccessibilityHelperService.instance == null) {
            m27629(false);
            return true;
        }
        StringBuilder sb = new StringBuilder();
        if (Build.VERSION.SDK_INT >= 30) {
            sb.append("click->仅在使用中允许\n");
            sb.append("sleep->500\n");
            sb.append("click->始终允许");
        } else {
            sb.append("click->允许");
        }
        this.operateManager.setOprator(new C4490());
        runCmd(sb);
        return true;
    }
}

