package accessibility;


import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.annotation.SuppressLint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.HorizontalScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ObjectUtils;
import com.niu.protect.tools.ILog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class OprateManager implements ScriptStateManager.IScriptExecutor {

    /* renamed from: 垡玖 */
    public static long f14839 = 200;

    public static long f24278 = 5000;

    @SuppressLint({"StaticFieldLeak"})
    public static OprateManager instance;

    @Nullable
    public InterfaceC7203 f24280;

    @Nullable
    public IOpChecker f14840;

    public boolean finished;

    @Nullable
    public AccessibilityService f24282;


    public class C7200 implements InterfaceC4477 {

        /* renamed from: 肌緭 */
        public final /* synthetic */ AccessibilityNodeInfo f14841;

        public C7200(AccessibilityNodeInfo accessibilityNodeInfo) {
            this.f14841 = accessibilityNodeInfo;
        }

        @Override // p564.C7199.InterfaceC4477
        public boolean mo16170() {
            return OprateManager.this.m27657(this.f14841);
        }
    }


    public interface InterfaceC4477 {
        /* renamed from: 肌緭 */
        boolean mo16170();
    }


    public interface IOpChecker {
        /* renamed from: 刻槒唱镧詴 */
        boolean mo27621(String str, String str2, AccessibilityNodeInfo accessibilityNodeInfo);

        /* renamed from: 肌緭 */
        void postResultEvent(boolean z);

        /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
        void mo27622(String str, String str2, boolean z);

        /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
        boolean checkNext(String str, String str2);
    }

    /* JADX WARN: Classes with same name are omitted:
  classes5.dex
 */
    /* compiled from: AutoScriptEngine.java */
    /* renamed from: 髿芫祜邕測.灞酞輀攼嵞漁綬迹$肌緭 */
    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
    public class C4478 implements InterfaceC4477 {

        /* renamed from: 肌緭 */
        public final /* synthetic */ AccessibilityNodeInfo f14842;

        public C4478(AccessibilityNodeInfo accessibilityNodeInfo) {
            this.f14842 = accessibilityNodeInfo;
        }

        @Override // p564.C7199.InterfaceC4477
        /* renamed from: 肌緭 */
        public boolean mo16170() {
            return OprateManager.this.m27662(this.f14842);
        }
    }


    public class C7202 extends AccessibilityService.GestureResultCallback {
        public C7202(OprateManager c7199) {
        }

        @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
        public void onCancelled(GestureDescription gestureDescription) {
            super.onCancelled(gestureDescription);
        }

        @Override // android.accessibilityservice.AccessibilityService.GestureResultCallback
        public void onCompleted(GestureDescription gestureDescription) {
            super.onCompleted(gestureDescription);
        }
    }

    public interface InterfaceC7203 {
        /* renamed from: 刻槒唱镧詴 */
        boolean mo27653(@NonNull String str);

        /* renamed from: 肌緭 */
        boolean mo16180(@NonNull String str, @NonNull String str2, boolean z);

        /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
        boolean mo27654(@NonNull String str, @NonNull String str2);
    }

    /* renamed from: 彻薯铏螙憣欖愡鼭, reason: contains not printable characters */
    public static OprateManager instance() {
        if (instance == null) {
            instance = new OprateManager();
        }
        return instance;
    }

    /* renamed from: 癎躑選熁, reason: contains not printable characters */
    public static /* synthetic */ boolean m27659(AccessibilityNodeInfo accessibilityNodeInfo) {
        return instance.m27657(accessibilityNodeInfo);
    }

    /* renamed from: 駭鑈趘薑衈講堍趃軏, reason: contains not printable characters */
    public static /* synthetic */ boolean m27663(AccessibilityNodeInfo accessibilityNodeInfo) {
        return instance.m27662(accessibilityNodeInfo);
    }

    @RequiresApi(api = 23)
    /* renamed from: 偣炱嘵蟴峗舟轛, reason: contains not printable characters and merged with bridge method [inline-methods] */
    public boolean m27662(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo.getActionList().contains(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN)) {
            return accessibilityNodeInfo.performAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN.getId());
        }
        return (ObjectUtils.equals(accessibilityNodeInfo.getClassName(), ViewPager.class.getName()) || ObjectUtils.equals(accessibilityNodeInfo.getClassName(), HorizontalScrollView.class.getName()) || !accessibilityNodeInfo.performAction(8192)) ? false : true;
    }


    public void setResult(boolean z) {
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        IOpChecker interfaceC7201 = this.f14840;
        if (interfaceC7201 != null) {
            interfaceC7201.postResultEvent(z);
        }
        this.f14840 = null;
        this.f24280 = null;
        this.finished = false;
    }

    /* renamed from: 卝閄侸靤溆鲁扅, reason: contains not printable characters */
    public final String m27666(String str, String str2, int i) {
        if (!str.contains(str2)) {
            return str;
        }
        String[] split = str.split(str2);
        return split.length > 1 ? split[i] : str;
    }

    @RequiresApi(api = 23)
    /* renamed from: 厖毿褸涙艔淶嬉殟恇凛场, reason: contains not printable characters and merged with bridge method [inline-methods] */
    public boolean m27657(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo.getActionList().contains(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP)) {
            return accessibilityNodeInfo.performAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP.getId());
        }
        if (ObjectUtils.equals(accessibilityNodeInfo.getClassName(), ViewPager.class.getName()) || ObjectUtils.equals(accessibilityNodeInfo.getClassName(), HorizontalScrollView.class.getName())) {
            return false;
        }
        return accessibilityNodeInfo.performAction(4096);
    }

    /* renamed from: 厧卥孩, reason: contains not printable characters */
    public final boolean m27668(Rect rect) {
        int i = rect.right;
        int i2 = rect.top;
        int i3 = Build.VERSION.SDK_INT;
        if (i3 < 24) {
            return false;
        }
        GestureDescription.Builder builder = new GestureDescription.Builder();
        Path path = new Path();
        path.moveTo(i, i2);
        path.close();
        if (i3 >= 26) {
            builder.addStroke(new GestureDescription.StrokeDescription(path, 1L, 1L, false));
        } else {
            builder.addStroke(new GestureDescription.StrokeDescription(path, 1L, 1L));
        }
        GestureDescription build = builder.build();
        AccessibilityService accessibilityService = this.f24282;
        return accessibilityService != null && accessibilityService.dispatchGesture(build, new C7202(this), null);
    }

    public final void m27669(boolean z) {
        this.finished = z;
    }

    public final List<AccessibilityNodeInfo> m27670(String str, AccessibilityNodeInfo accessibilityNodeInfo, InterfaceC4477 interfaceC4477) {
        long currentTimeMillis = System.currentTimeMillis();
        while (interfaceC4477.mo16170()) {
            try {
                Thread.sleep(f14839);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(str);
            if (findAccessibilityNodeInfosByText != null && !findAccessibilityNodeInfosByText.isEmpty() && findAccessibilityNodeInfosByText.get(0).isVisibleToUser()) {
                return findAccessibilityNodeInfosByText;
            }
            if (System.currentTimeMillis() - currentTimeMillis > f24278) {
                ILog.log("looperFind 超时停止->" + str);
                return null;
            }
        }
        return null;
    }

    /* renamed from: 壋劘跆貭澴綄秽攝煾訲, reason: contains not printable characters */
    public AccessibilityNodeInfo m27671(final AccessibilityNodeInfo accessibilityNodeInfo, String str, String[] strArr) {
        if (Build.VERSION.SDK_INT < 23) {
            return null;
        }
        AccessibilityNodeInfo m27683 = m27683(str, strArr, accessibilityNodeInfo, new InterfaceC4477() { // from class: 髿芫祜邕測.刻槒唱镧詴
            @Override // p564.C7199.InterfaceC4477
            /* renamed from: 肌緭 */
            public final boolean mo16170() {
                boolean m27663;
                m27663 = OprateManager.m27663(accessibilityNodeInfo);
                return m27663;
            }
        });
        if (m27683 != null) {
            return m27683;
        }
        AccessibilityNodeInfo m276832 = m27683(str, strArr, accessibilityNodeInfo, new InterfaceC4477() { // from class: 髿芫祜邕測.肌緭
            @Override // p564.C7199.InterfaceC4477
            /* renamed from: 肌緭 */
            public final boolean mo16170() {
                boolean m27659;
                m27659 = OprateManager.m27659(accessibilityNodeInfo);
                return m27659;
            }
        });
        if (m276832 != null) {
            return m276832;
        }
        return null;
    }

    @RequiresApi(api = 23)
    /* renamed from: 媛婱骼蒋袐弲卙, reason: contains not printable characters */
    public final boolean m27672(@NonNull String str) {
        InterfaceC7203 interfaceC7203;
        AccessibilityService accessibilityService;
        List<AccessibilityNodeInfo> m16183 = m16183(str);
        if ((m16183 == null || m16183.isEmpty()) && (interfaceC7203 = this.f24280) != null && interfaceC7203.mo27653(str)) {
            return false;
        }
        for (int i = 5; m16183 == null && i > 0; i--) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            m16183 = m16183(str);
        }
        if (m16183 == null) {
            return false;
        }
        if (m16183.isEmpty()) {
            return false;
        }
        Rect rect = new Rect();
        m16183.get(0).getBoundsInScreen(rect);
        Rect rect2 = new Rect();
        m16183.get(0).getParent().getBoundsInScreen(rect2);
        if (rect.top != 0 && rect.right != 0 && (accessibilityService = this.f24282) != null) {
            rect.right = (int) (100 - (accessibilityService.getResources().getDisplayMetrics().density * 30.0f));
            int height = rect2.height() / 2;
            if (Build.VERSION.SDK_INT >= 31) {
                rect.top = rect2.top + height;
            } else {
                rect.top = rect2.top + height + (height / 2);
            }
            IOpChecker m27693 = m27693();
            if ((m27693 == null || m27693.mo27621("touchCenterClick", str, m16183.get(0))) ? false : true) {
                return m27687(rect);
            }
        }
        return false;
    }

    @RequiresApi(api = 23)
    public final boolean m27673(String str) {
        InterfaceC7203 interfaceC7203;
        AccessibilityService accessibilityService;
        List<AccessibilityNodeInfo> m16183 = m16183(str);
        if ((m16183 == null || m16183.isEmpty()) && (interfaceC7203 = this.f24280) != null && interfaceC7203.mo27653(str)) {
            return false;
        }
        for (int i = 5; m16183 == null && i > 0; i--) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            m16183 = m16183(str);
        }
        if (m16183 != null && !m16183.isEmpty()) {
            Rect rect = new Rect();
            m16183.get(0).getBoundsInScreen(rect);
            if (rect.top != 0 && rect.right != 0 && (accessibilityService = this.f24282) != null) {
                rect.right = (int) (100 - (accessibilityService.getResources().getDisplayMetrics().density * 35.0f));
                rect.top += rect.height() / 2;
                IOpChecker m27693 = m27693();
                if ((m27693 == null || m27693.mo27621("touchCenterClick", str, m16183.get(0))) ? false : true) {
                    return m27668(rect);
                }
            }
        }
        return false;
    }

    /* renamed from: 攏瑹迀虚熂熋卿悍铒誦爵, reason: contains not printable characters */
    public final void setOp2(@Nullable InterfaceC7203 interfaceC7203) {
        this.f24280 = interfaceC7203;
    }

    /* renamed from: 斃燸卺驼暲各撟嫺眧樬硱, reason: contains not printable characters */
    public final String m27675(String str) {
        return m27689(str, "@");
    }

    /* renamed from: 旞莍癡, reason: contains not printable characters */
    public final boolean m27676() {
        boolean z;
        AccessibilityService accessibilityService;
        if (this.f14840 != null && (accessibilityService = this.f24282) != null) {
            if (!this.f14840.mo27621("back", "", accessibilityService.getRootInActiveWindow())) {
                z = true;
                return !z ? false : false;
            }
        }
        z = false;
        return !z ? false : false;
    }

    @RequiresApi(api = 23)
    /* renamed from: 朽劔蚁灋嵿齩鶴琓麃沼瀙缹, reason: contains not printable characters */
    public final List<AccessibilityNodeInfo> m27677(String str, AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo == null) {
            return null;
        }
        ILog.log("findNode2 Star->" + accessibilityNodeInfo.getViewIdResourceName() + "---" + str);
        List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(str);
        boolean z = findAccessibilityNodeInfosByText == null || findAccessibilityNodeInfosByText.isEmpty();
        if (!z) {
            AccessibilityNodeInfo accessibilityNodeInfo2 = findAccessibilityNodeInfosByText.get(0);
            z = (accessibilityNodeInfo2 == null || accessibilityNodeInfo2.isVisibleToUser()) ? false : true;
        }
        if (!z) {
            return findAccessibilityNodeInfosByText;
        }
        if (accessibilityNodeInfo.isScrollable()) {
            ILog.log("findNode2 执行向下滑动");
            List<AccessibilityNodeInfo> m27670 = m27670(str, accessibilityNodeInfo, new C4478(accessibilityNodeInfo));
            if (m27670 != null) {
                ILog.log("findNode2 执行向下滑动获取到了数据");
                return m27670;
            }
            ILog.log("findNode2 执行向上滑动");
            List<AccessibilityNodeInfo> m276702 = m27670(str, accessibilityNodeInfo, new C7200(accessibilityNodeInfo));
            if (m276702 != null) {
                ILog.log("findNode2 执行向上滑动获取到了数据");
                return m276702;
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            List<AccessibilityNodeInfo> list = null;
            while (childCount > 0) {
                childCount--;
                ILog.log("findNode2 获取子类" + childCount);
                try {
                    list = m27677(str, accessibilityNodeInfo.getChild(childCount));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (list != null) {
                    return list;
                }
            }
            return null;
        }
        int childCount2 = accessibilityNodeInfo.getChildCount();
        ILog.log("findNode2 获取子类 noScroll" + childCount2);
        List<AccessibilityNodeInfo> list2 = null;
        while (childCount2 > 0) {
            childCount2--;
            try {
                list2 = m27677(str, accessibilityNodeInfo.getChild(childCount2));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (list2 != null) {
                return list2;
            }
        }
        return null;
    }

    /* renamed from: 枩棥钰蕎睨領喀镎遣跄, reason: contains not printable characters */
    public final void setOprator(@Nullable IOpChecker interfaceC7201) {
        this.f14840 = interfaceC7201;
    }

    @RequiresApi(api = 23)
    public boolean unCheckNode(String str) {
        ILog.log("unCheckNode Star->" + str);
        List<AccessibilityNodeInfo> m16183 = m16183(str);
        InterfaceC7203 interfaceC7203 = this.f24280;
        if (interfaceC7203 != null && interfaceC7203.mo27653(str)) {
            return false;
        }
        for (int i = 5; m16183 == null && i > 0; i--) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            m16183 = m16183(str);
        }
        if (m16183 != null && !m16183.isEmpty()) {
            String m27675 = m27675(str);
            ILog.log("unCheckNode 文字Value->" + m27675);
            for (AccessibilityNodeInfo accessibilityNodeInfo : m16183) {
                if (m27692(accessibilityNodeInfo)) {
                    try {
                        Thread.sleep(200L);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                    IOpChecker m27693 = m27693();
                    if (!accessibilityNodeInfo.isChecked()) {
                        if (m27693 != null) {
                            m27693.mo27622("uncheck", m27675, false);
                        }
                        return true;
                    }
                    if (m27693 != null && !m27693.mo27621("uncheck", m27675, accessibilityNodeInfo)) {
                        accessibilityNodeInfo.performAction(16);
                        AccessibilityNodeInfo m27685 = m27685(accessibilityNodeInfo, m27675);
                        AccessibilityNodeInfo m276852 = m27685(accessibilityNodeInfo, m27675);
                        if (m276852.isChecked()) {
                            if (m276852.getParent() != null) {
                                m276852.getParent().performAction(16);
                                m27685 = m27685(m276852, m27675);
                            } else {
                                m27685 = m276852;
                            }
                        }
                        return !m27685.isChecked();
                    }
                } else {
                    AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
                    if (parent == null) {
                        continue;
                    } else {
                        int childCount = parent.getChildCount();
                        while (childCount > 0) {
                            childCount--;
                            AccessibilityNodeInfo child = parent.getChild(childCount);
                            if (m27692(child)) {
                                try {
                                    Thread.sleep(200L);
                                } catch (InterruptedException e3) {
                                    e3.printStackTrace();
                                }
                                if (!child.isChecked()) {
                                    IOpChecker m276932 = m27693();
                                    if (m276932 != null) {
                                        m276932.mo27622("uncheck", m27675, false);
                                    }
                                    return true;
                                }
                                IOpChecker m276933 = m27693();
                                if (m276933 != null && !m276933.mo27621("uncheck", m27675, child)) {
                                    child.performAction(16);
                                    AccessibilityNodeInfo m276853 = m27685(child, m27675);
                                    if (m276853.isChecked() && m276853.getParent() != null) {
                                        m276853.getParent().performAction(16);
                                        m276853 = m27685(m276853, m27675);
                                    }
                                    return !m276853.isChecked();
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public final void lateRun(@NonNull String str) {
        try {
            Thread.sleep(1500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.finished = false;
        ScriptStateManager.instance().runScript(str, this);
    }

    public final void m27681(@NonNull AccessibilityService accessibilityService) {
        this.f24282 = accessibilityService;
    }

    public List<AccessibilityNodeInfo> findNodeById(String str) {
        AccessibilityNodeInfo rootInActiveWindow;
        AccessibilityService accessibilityService = this.f24282;
        if (accessibilityService == null || (rootInActiveWindow = accessibilityService.getRootInActiveWindow()) == null) {
            return null;
        }
        ILog.log("通过id寻找:" + str);
        List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId(str);
        if (findAccessibilityNodeInfosByViewId == null || findAccessibilityNodeInfosByViewId.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (AccessibilityNodeInfo accessibilityNodeInfo : findAccessibilityNodeInfosByViewId) {
            if (accessibilityNodeInfo != null && accessibilityNodeInfo.isVisibleToUser()) {
                arrayList.add(accessibilityNodeInfo);
            }
        }
        ILog.log("通过id找到了:" + arrayList.size());
        return arrayList;
    }

    public final AccessibilityNodeInfo m27683(String str, String[] strArr, AccessibilityNodeInfo accessibilityNodeInfo, InterfaceC4477 interfaceC4477) {
        long currentTimeMillis = System.currentTimeMillis();
        while (interfaceC4477.mo16170()) {
            try {
                Thread.sleep(300L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(str);
            if (findAccessibilityNodeInfosByViewId != null && !findAccessibilityNodeInfosByViewId.isEmpty()) {
                for (AccessibilityNodeInfo accessibilityNodeInfo2 : findAccessibilityNodeInfosByViewId) {
                    CharSequence text = accessibilityNodeInfo2.getText();
                    if (text == null) {
                        text = accessibilityNodeInfo2.getContentDescription();
                    }
                    if (ObjectUtils.isNotEmpty(text) && Arrays.asList(strArr).contains(text)) {
                        return accessibilityNodeInfo2;
                    }
                }
            }
            if (System.currentTimeMillis() - currentTimeMillis > f24278) {
                return null;
            }
        }
        return null;
    }

    @RequiresApi(api = 23)
    public boolean m27684(String str) {
        List<AccessibilityNodeInfo> m16183 = m16183(str);
        InterfaceC7203 interfaceC7203 = this.f24280;
        if (interfaceC7203 != null && interfaceC7203.mo27653(str)) {
            return false;
        }
        for (int i = 5; m16183 == null && i > 0; i--) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            m16183 = m16183(str);
        }
        if (m16183 != null && !m16183.isEmpty()) {
            for (AccessibilityNodeInfo accessibilityNodeInfo : m16183) {
                if (m27692(accessibilityNodeInfo)) {
                    try {
                        Thread.sleep(200L);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                    if (accessibilityNodeInfo.isChecked()) {
                        return true;
                    }
                    IOpChecker m27693 = m27693();
                    if (m27693 != null && !m27693.mo27621("check", str, accessibilityNodeInfo)) {
                        accessibilityNodeInfo.performAction(16);
                        AccessibilityNodeInfo m27685 = m27685(accessibilityNodeInfo, str);
                        if (!m27685.isChecked() && m27685.getParent() != null) {
                            m27685.getParent().performAction(16);
                            m27685 = m27685(accessibilityNodeInfo, str);
                        }
                        return m27685.isChecked();
                    }
                } else {
                    AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
                    if (parent == null) {
                        continue;
                    } else {
                        int childCount = parent.getChildCount();
                        for (int i2 = 0; i2 < childCount; i2++) {
                            AccessibilityNodeInfo child = parent.getChild(i2);
                            if (m27692(child)) {
                                try {
                                    Thread.sleep(200L);
                                } catch (InterruptedException e3) {
                                    e3.printStackTrace();
                                }
                                if (child.isChecked()) {
                                    return true;
                                }
                                IOpChecker m276932 = m27693();
                                if (m276932 != null && !m276932.mo27621("check", str, child)) {
                                    child.performAction(16);
                                    AccessibilityNodeInfo m276852 = m27685(child, str);
                                    if (!m276852.isChecked() && m276852.getParent() != null) {
                                        m276852.getParent().performAction(16);
                                        m276852 = m27685(m276852, str);
                                    }
                                    return m276852.isChecked();
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @RequiresApi(api = 23)
    public final AccessibilityNodeInfo m27685(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        List<AccessibilityNodeInfo> m16183;
        if (accessibilityNodeInfo.refresh() || (m16183 = m16183(str)) == null || m16183.isEmpty()) {
            return accessibilityNodeInfo;
        }
        if (m27692(accessibilityNodeInfo)) {
            Iterator<AccessibilityNodeInfo> it = m16183.iterator();
            while (it.hasNext()) {
                AccessibilityNodeInfo parent = it.next().getParent();
                if (parent != null) {
                    int childCount = parent.getChildCount();
                    while (childCount > 0) {
                        childCount--;
                        AccessibilityNodeInfo child = parent.getChild(childCount);
                        if (m27692(child)) {
                            child.refresh();
                            return child;
                        }
                    }
                }
            }
        }
        m16183.get(0).refresh();
        return m16183.get(0);
    }

    public List<AccessibilityNodeInfo> m16183(String str) {
        LogHelper.m27985("findNode开始->" + str);
        AccessibilityService accessibilityService = this.f24282;
        if (accessibilityService == null) {
            return null;
        }
        AccessibilityNodeInfo rootInActiveWindow = accessibilityService.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return null;
        }
        List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str);

        if (findAccessibilityNodeInfosByText != null){
            if (findAccessibilityNodeInfosByText.isEmpty() == true) {
                return null;
            }
        }
        AccessibilityNodeInfo accessibilityNodeInfo = findAccessibilityNodeInfosByText.get(0);
        if (accessibilityNodeInfo != null){
            if (!accessibilityNodeInfo.isVisibleToUser()){
                return null;
            }
        }
        if (rootInActiveWindow.isScrollable()) {
            ILog.log("findNode=>isScroll 开始执行向下滑动");
            List<AccessibilityNodeInfo> result = m27670(str, rootInActiveWindow, new C7263(this,accessibilityNodeInfo)); // 假设这是向下滚动的逻辑
            if (result != null) {
                return result;
            }

            ILog.log("findNode=>isScroll 开始执行向上滑动");
            result = m27670(str, rootInActiveWindow, new C7225(this,accessibilityNodeInfo)); // 假设这是向上滚动的逻辑
            if (result != null) {
                return result;
            }
        }

        if (str.contains("@")) {
            String[] split = str.split("@");
            if (split.length > 1) {
                String str2 = split[0];
                String str3 = m27675(str); // 假设这是处理字符串的方法
                List<AccessibilityNodeInfo> m27682 = findNodeById(str2); // 假设这是另一种查找节点的方法
                if (m27682 != null) {
                    return m27682;
                }
            }
        }

        List<AccessibilityNodeInfo> list = null;
        if (str.contains("#")) {
            String hashProcessedString = m27691(str, "#");
            String anotherHashProcessedString = m27689(str, "#"); // 可能需要不同的处理逻辑，但这里为了示例保持相同
            List<AccessibilityNodeInfo> nodesByViewId = findNodeById(hashProcessedString);
            if (nodesByViewId != null && !nodesByViewId.isEmpty()) {
                return nodesByViewId;
            }

            list = m27677(anotherHashProcessedString, rootInActiveWindow);
            if (list != null && !list.isEmpty()) {
                return list;
            }
        }

        if (str.contains("&")) {
            String ampersandProcessedString = m27691(str, "&");
            String anotherAmpersandProcessedString = m27689(str, "&"); // 可能需要不同的处理逻辑，但这里为了示例保持相同
            List<AccessibilityNodeInfo> nodesByViewId2 = findNodeById(ampersandProcessedString);
            if (nodesByViewId2 != null && !nodesByViewId2.isEmpty()) {
                List<AccessibilityNodeInfo> filteredList = new ArrayList<>();
                for (AccessibilityNodeInfo node : nodesByViewId2) {
                    if (node.getViewIdResourceName().equals(ampersandProcessedString)) {
                        filteredList.add(node);
                    }
                }
                if (!filteredList.isEmpty()) {
                    return filteredList;
                }
            }

            List<AccessibilityNodeInfo> nodesByText = m27677(anotherAmpersandProcessedString, rootInActiveWindow);
            if (nodesByText != null) {
                List<AccessibilityNodeInfo> filteredList = new ArrayList<>();
                for (AccessibilityNodeInfo node : nodesByText) {
                    CharSequence text = node.getText();
                    if (text != null && text.toString().equals(anotherAmpersandProcessedString)) {
                        filteredList.add(node);
                    }
                }
                if (!filteredList.isEmpty()) {
                    return filteredList;
                }
            }
        }
        return null;

    }


    /* renamed from: 纩慐 */
    public final boolean isFinished() {
        return this.finished;
    }

    /* renamed from: 耣怳匮色紝参凵蛴纆勚躄, reason: contains not printable characters */
    public final void m27686() {
        this.finished = true;
    }

    public boolean executeScript(@NonNull ScriptState scriptState) {
        if (this.finished) {
            ILog.log("isFinish return");
            return true;
        }
        InterfaceC7203 interfaceC7203 = this.f24280;
        if (interfaceC7203 != null && interfaceC7203.mo27654(scriptState.getOp(), scriptState.getValue())) {
            ILog.log("onStateBefore return");
            return true;
        }
        boolean m16186 = executeCmd(scriptState);
        ILog.log("swicthState：" + m16186 + "--" + scriptState.getOp() + "--" + scriptState.getValue());
        InterfaceC7203 interfaceC72032 = this.f24280;
        if (interfaceC72032 != null && interfaceC72032.mo16180(scriptState.getOp(), scriptState.getValue(), m16186)) {
            return true;
        }
        if (!this.finished) {
            return m16186;
        }
        ILog.log("isFinish return");
        return true;
    }

    /* renamed from: 蘫聫穯搞哪曁雥贀忬琖嶹, reason: contains not printable characters */
    public boolean m27687(Rect rect) {
        int i = rect.right;
        int i2 = rect.top;
        int i3 = Build.VERSION.SDK_INT;
        if (i3 >= 24) {
            GestureDescription.Builder builder = new GestureDescription.Builder();
            Path path = new Path();
            path.moveTo(i, i2);
            path.close();
            if (i3 >= 26) {
                builder.addStroke(new GestureDescription.StrokeDescription(path, 1L, 1L, false));
            } else {
                builder.addStroke(new GestureDescription.StrokeDescription(path, 1L, 1L));
            }
            GestureDescription build = builder.build();
            AccessibilityService accessibilityService = this.f24282;
            if (accessibilityService != null) {
                return accessibilityService.dispatchGesture(build, null, null);
            }
        }
        return false;
    }

    /* renamed from: 蝸餺閃喍, reason: contains not printable characters */
    public AccessibilityNodeInfo m27688() {
        AccessibilityNodeInfo rootInActiveWindow = AccessibilityHelperService.instance.getRootInActiveWindow();
        if (rootInActiveWindow.isScrollable()) {
            return rootInActiveWindow;
        }
        int childCount = rootInActiveWindow.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = rootInActiveWindow.getChild(i);
            if (child != null && child.isScrollable()) {
                return child;
            }
        }
        return null;
    }


    public final boolean executeCmd(ScriptState scriptState) {
        String op = scriptState.getOp();
        op.hashCode();
        switch (op.hashCode()) {
            case -1997237900:
                if (op.equals("touchCenterClick")) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (m27672(scriptState.getValue())) {
                            try {
                                Thread.sleep(600L);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return true;
                        }
                        IOpChecker interfaceC7201 = this.f14840;
                        if (interfaceC7201 != null && interfaceC7201.checkNext(scriptState.getOp(), scriptState.getValue())) {
                            return true;
                        }
                    }
                    return false;
                }
                break;
            case -1317587267:
                if (op.equals("touchTopCenterClick")) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (m27673(scriptState.getValue())) {
                            try {
                                Thread.sleep(600L);
                            } catch (InterruptedException e2) {
                                e2.printStackTrace();
                            }
                            return true;
                        }
                        IOpChecker interfaceC72012 = this.f14840;
                        if (interfaceC72012 != null && interfaceC72012.checkNext(scriptState.getOp(), scriptState.getValue())) {
                            return true;
                        }
                    }
                    return false;
                }
                break;
            case -292418033:
                if (op.equals("uncheck")) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (unCheckNode(scriptState.getValue())) {
                            try {
                                Thread.sleep(600L);
                            } catch (InterruptedException e3) {
                                e3.printStackTrace();
                            }
                            return true;
                        }
                        IOpChecker interfaceC72013 = this.f14840;
                        if (interfaceC72013 != null && interfaceC72013.checkNext(scriptState.getOp(), scriptState.getValue())) {
                            return true;
                        }
                    }
                    return false;
                }
                break;
            case 3015911:
                if (op.equals("back")) {
                    try {
                        Thread.sleep(600L);
                    } catch (InterruptedException e4) {
                        e4.printStackTrace();
                    }
                    return m27676();
                }
                break;
            case 3143097:
                if (op.equals("find")) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (m16183(scriptState.getValue()) != null) {
                            try {
                                Thread.sleep(600L);
                            } catch (InterruptedException e5) {
                                e5.printStackTrace();
                            }
                            return true;
                        }
                        IOpChecker interfaceC72014 = this.f14840;
                        if (interfaceC72014 != null && interfaceC72014.checkNext(scriptState.getOp(), scriptState.getValue())) {
                            return true;
                        }
                    }
                    return false;
                }
                break;
            case 94627080:
                if (op.equals("check")) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (m27684(scriptState.getValue())) {
                            try {
                                Thread.sleep(600L);
                            } catch (InterruptedException e6) {
                                e6.printStackTrace();
                            }
                            return true;
                        }
                        IOpChecker interfaceC72015 = this.f14840;
                        if (interfaceC72015 != null && interfaceC72015.checkNext(scriptState.getOp(), scriptState.getValue())) {
                            return true;
                        }
                    }
                    return false;
                }
                break;
            case 94750088:
                if (op.equals("click")) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (m16187(scriptState.getValue())) {
                            try {
                                Thread.sleep(600L);
                            } catch (InterruptedException e7) {
                                e7.printStackTrace();
                            }
                            return true;
                        }
                        IOpChecker interfaceC72016 = this.f14840;
                        if (interfaceC72016 != null && interfaceC72016.checkNext(scriptState.getOp(), scriptState.getValue())) {
                            return true;
                        }
                    }
                    return false;
                }
                break;
            case 109522647:
                if (op.equals("sleep")) {
                    try {
                        Thread.sleep(Long.parseLong(scriptState.getValue()));
                    } catch (InterruptedException e8) {
                        e8.printStackTrace();
                    }
                    return true;
                }
                break;
        }
        return false;
    }

    /* renamed from: 辒迳圄袡皪郞箟, reason: contains not printable characters */
    public final String m27689(String str, String str2) {
        return m27666(str, str2, 1);
    }

    /* renamed from: 酸恚辰橔纋黺, reason: contains not printable characters */
    public final void m27690() {
        this.f24282 = null;
    }

    /* renamed from: 鑭撇糁綖浓緗轟鱼萟磿焈, reason: contains not printable characters */
    public final String m27691(String str, String str2) {
        return m27666(str, str2, 0);
    }

    public final boolean m27692(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo == null) {
            return false;
        }
        return ObjectUtils.equals(accessibilityNodeInfo.getClassName(), "android.widget.CheckBox") || ObjectUtils.equals(accessibilityNodeInfo.getClassName(), "android.widget.RadioButton") || ObjectUtils.equals(accessibilityNodeInfo.getClassName(), "android.widget.Switch");
    }

    @RequiresApi(api = 23)
    /* renamed from: 镐藻 */
    public boolean m16187(String str) {
        IOpChecker m27693;
        List<AccessibilityNodeInfo> m16183 = m16183(str);
        InterfaceC7203 interfaceC7203 = this.f24280;
        if (interfaceC7203 != null && interfaceC7203.mo27653(str)) {
            return false;
        }
        for (int i = 5; m16183 == null && i > 0; i--) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            m16183 = m16183(str);
        }
        if (m16183 != null && !m16183.isEmpty()) {
            for (AccessibilityNodeInfo accessibilityNodeInfo : m16183) {
                if (accessibilityNodeInfo.isClickable()) {
                    IOpChecker m276932 = m27693();
                    if (m276932 != null && !m276932.mo27621("click", str, accessibilityNodeInfo)) {
                        return accessibilityNodeInfo.performAction(16);
                    }
                } else if (accessibilityNodeInfo.getParent() != null && accessibilityNodeInfo.getParent().isClickable() && (m27693 = m27693()) != null && !m27693.mo27621("click", str, accessibilityNodeInfo) && accessibilityNodeInfo.getParent() != null) {
                    return accessibilityNodeInfo.getParent().performAction(16);
                }
            }
        }
        return false;
    }

    @Nullable
    /* renamed from: 陟瓠魒踱褢植螉嚜, reason: contains not printable characters */
    public final IOpChecker m27693() {
        return this.f14840;
    }

    @RequiresApi(api = 23)
    /* renamed from: 鞲冇 */
    public void m16188(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo.isScrollable()) {
            long currentTimeMillis = System.currentTimeMillis();
            while (instance.m27657(accessibilityNodeInfo)) {
                try {
                    Thread.sleep(f14839);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (System.currentTimeMillis() - currentTimeMillis > f24278) {
                    ILog.log("scrollPageEnd 超时停止");
                    return;
                }
            }
        }
    }
}

//public class C4478 implements OprateManager.InterfaceC4477 {
//
//    /* renamed from: 肌緭 */
//    public final /* synthetic */ AccessibilityNodeInfo f14842;
//
//    public C4478(AccessibilityNodeInfo accessibilityNodeInfo) {
//        this.f14842 = accessibilityNodeInfo;
//    }
//
//    @Override // p564.C7199.InterfaceC4477
//    /* renamed from: 肌緭 */
//    public boolean mo16170() {
////        return OprateManager.this.m27662(this.f14842);
//        return false;
//    }
//}

final /* synthetic */ class C7225 implements OprateManager.InterfaceC4477 {

    /* renamed from: 刻槒唱镧詴 */
    public final /* synthetic */ AccessibilityNodeInfo f24292;

    /* renamed from: 肌緭 */
    public final /* synthetic */ OprateManager f14868;

    public /* synthetic */ C7225(OprateManager c7199, AccessibilityNodeInfo accessibilityNodeInfo) {
        this.f14868 = c7199;
        f24292 = accessibilityNodeInfo;
    }

    @Override // p564.C7199.InterfaceC4477
    /* renamed from: 肌緭 */
    public final boolean mo16170() {
//        return OprateManager.m27660(OprateManager.this, f24292);
        return false;
    }
}

final /* synthetic */ class C7263 implements OprateManager.InterfaceC4477 {

    public final /* synthetic */ AccessibilityNodeInfo f24302;

    public final /* synthetic */ OprateManager f14912;

    public /* synthetic */ C7263(OprateManager c7199, AccessibilityNodeInfo accessibilityNodeInfo) {
        f14912 = c7199;
        f24302 = accessibilityNodeInfo;
    }

    @Override // p564.C7199.InterfaceC4477
    public final boolean mo16170() {
//        return OprateManager.m27661(OprateManager.this, f24302);
        return false;
    }
}