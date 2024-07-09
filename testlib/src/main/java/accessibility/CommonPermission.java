package accessibility;

import android.os.Build;
import android.view.accessibility.AccessibilityNodeInfo;

import com.blankj.utilcode.util.ObjectUtils;
import com.niu.protect.tools.RomUtil;

import java.util.List;

public abstract class CommonPermission extends BasePermission {

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭, reason: contains not printable characters */
    public static CommonPermission f24288;

    /* JADX WARN: Classes with same name are omitted:
  classes5.dex
 */
    /* compiled from: CommonPermission.java */
    /* renamed from: 髿芫祜邕測.祴嚚橺谋肬鬧舘$刻槒唱镧詴, reason: contains not printable characters */
    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
    public static class C7207 extends CommonPermission {
    }

    /* JADX WARN: Classes with same name are omitted:
  classes5.dex
 */
    /* compiled from: CommonPermission.java */
    /* renamed from: 髿芫祜邕測.祴嚚橺谋肬鬧舘$肌緭 */
    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
    public class C4480 implements OprateManager.IOpChecker {
        public C4480() {
        }

        public boolean mo27621( String str,  String str2,  AccessibilityNodeInfo accessibilityNodeInfo) {
            return false;
        }

        public void postResultEvent(boolean z) {
            CommonPermission.this.m27629(z);
        }

        public /* synthetic */ void mo27622(String str, String str2, boolean z) {
//            C4472.m16173(this, str, str2, z);
        }

        public boolean checkNext(String str, String str2) {
            return false;
        }
    }

    /* renamed from: 櫓昛刓叡賜, reason: contains not printable characters */
    public static CommonPermission getPermission() {
        if (f24288 == null) {
            synchronized (CommonPermission.class) {
                f24288 = new XiaoMiPermission();
            }
        }
        return f24288;
    }

    /* renamed from: 卝閄侸靤溆鲁扅, reason: contains not printable characters */
    public boolean mo27700() {
        return false;
    }

    /* renamed from: 哠畳鲜郣新剙鳰活茙郺嵝 */
    public boolean mo27642() {
        return false;
    }

    /* renamed from: 唌橅咟 */
    public boolean mo27624() {
        return false;
    }

    /* renamed from: 壋劘跆貭澴綄秽攝煾訲, reason: contains not printable characters */
    public boolean mo27701() {
        return false;
    }

    /* renamed from: 彻薯铏螙憣欖愡鼭 */
    public boolean mo27643() {
        return false;
    }

    /* renamed from: 攏瑹迀虚熂熋卿悍铒誦爵, reason: contains not printable characters */
    public boolean m27702() {
        if (m16174("android:id/button1")) {
            return true;
        }
        m27627("确定");
        return true;
    }

    /* renamed from: 斃燸卺驼暲各撟嫺眧樬硱, reason: contains not printable characters */
    public boolean mo27703() {
        return false;
    }

    /* renamed from: 朽劔蚁灋嵿齩鶴琓麃沼瀙缹 */
    public boolean mo27645() {
        return false;
    }

    /* renamed from: 杹藗瀶姙笻件稚嵅蔂 */
    public boolean mo27618() {
        return false;
    }

    /* renamed from: 枩棥钰蕎睨領喀镎遣跄, reason: contains not printable characters */
    public boolean openDebuger() {
        AccessibilityNodeInfo parent;
        if (AccessibilityHelperService.instance == null) {
            postResultEvent(false, 38);
            return true;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            AccessibilityNodeInfo m27688 = this.operateManager.m27688();
            AccessibilityNodeInfo m27671 = this.operateManager.m27671(m27688, "android:id/checkbox", new String[]{"USB 调试", "USB调试"});
            if (m27671 == null) {
                m27671 = this.operateManager.m27671(m27688, "android:id/switch_widget", new String[]{"USB 调试", "USB调试"});
            }
            if (this.operateManager.m27692(m27671)) {
                if (!ObjectUtils.equals("开启", m27671.getContentDescription()) && !m27671.isChecked()) {
                    m27671.performAction(16);
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (RomUtil.isMiui()) {
                        List<AccessibilityNodeInfo> m16183 = this.operateManager.m16183("确定");
                        if (m16183 != null && !m16183.isEmpty()) {
                            AccessibilityNodeInfo accessibilityNodeInfo = m16183.get(0);
                            sleepNodeTime("确定", accessibilityNodeInfo);
                            accessibilityNodeInfo.performAction(16);
                        }
                    } else {
                        List<AccessibilityNodeInfo> m161832 = this.operateManager.m16183("确定");
                        if (m161832 != null && !m161832.isEmpty()) {
                            AccessibilityNodeInfo accessibilityNodeInfo2 = m161832.get(0);
                            if (accessibilityNodeInfo2.isClickable()) {
                                if (!accessibilityNodeInfo2.performAction(16) && (parent = accessibilityNodeInfo2.getParent()) != null) {
                                    parent.performAction(16);
                                }
                            } else {
                                AccessibilityNodeInfo parent2 = accessibilityNodeInfo2.getParent();
                                if (parent2 != null) {
                                    parent2.performAction(16);
                                }
                            }
                        }
                    }
                    postResultEvent(m27671.isChecked(), 38);
                } else {
                    postResultEvent(true, 38);
                    return true;
                }
            }
        } else {
            postResultEvent(false, 38);
        }
        return true;
    }

    /* renamed from: 癎躑選熁 */
    public boolean mo27647() {
        return false;
    }

    /* renamed from: 瞙餃莴埲 */
    public boolean mo27648() {
        return false;
    }

    /* renamed from: 礱咄頑, reason: contains not printable characters */
    public boolean mo27705() {
        return false;
    }

    /* renamed from: 綏牽躵糽稰烳俠垳襨捈桏鷋 */
    public boolean mo27649() {
        return false;
    }

    /* renamed from: 綩私 */
    public boolean mo16179() {
        return false;
    }

    /* renamed from: 纩慐 */
    public boolean mo16171() {
        return false;
    }

    /* renamed from: 耣怳匮色紝参凵蛴纆勚躄 */
    public boolean mo27650() {
        return false;
    }

    /* renamed from: 蝸餺閃喍, reason: contains not printable characters */
    public boolean m27706() {
        if (AccessibilityHelperService.instance == null) {
            m27629(false);
            return true;
        }
        StringBuilder sb = new StringBuilder();
        if (!Tools.isPad() && !Tools.versionLess11()) {
            sb.append("uncheck->USB 调试");
        } else {
            sb.append("uncheck->USB调试");
        }
        this.operateManager.setOprator(new C4480());
        runCmd(sb);
        return true;
    }

    /* renamed from: 辒迳圄袡皪郞箟 */
    public boolean clickMiUI(int i) {
        return false;
    }

    /* renamed from: 鑭撇糁綖浓緗轟鱼萟磿焈 */
    public boolean mo27652() {
        return false;
    }

    /* renamed from: 销薞醣戔攖餗 */
    public boolean setAppManager() {
        return false;
    }

    /* renamed from: 陟瓠魒踱褢植螉嚜, reason: contains not printable characters */
    public boolean setPopWindow() {
        return false;
    }

    /* renamed from: 鞲冇 */
    public boolean allowSee() {
        return false;
    }

    /* renamed from: 韐爮幀悖罤噩钼遑杯盇 */
    public boolean setAllowAll() {
        return false;
    }

    /* renamed from: 駭鑈趘薑衈講堍趃軏, reason: contains not printable characters */
    public boolean mo27708() {
        return false;
    }
}