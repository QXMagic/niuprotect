package com.niu.protect.accessibility.auto.service;


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

        import com.niu.protect.tools.ILog;
        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Iterator;
        import java.util.List;

/* JADX WARN: Classes with same name are omitted:
  classes5.dex
 */
/* compiled from: AutoScriptEngine.java */
/* renamed from: 髿芫祜邕測.灞酞輀攼嵞漁綬迹, reason: contains not printable characters */
/* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
public class OprateManager implements ScriptStateManager.InterfaceC7206 {

    /* renamed from: 垡玖 */
    public static long f14839 = 200;

    /* renamed from: 旞莍癡, reason: contains not printable characters */
    public static long f24278 = 5000;

    /* renamed from: 灞酞輀攼嵞漁綬迹, reason: contains not printable characters */
    @SuppressLint({"StaticFieldLeak"})
    public static OprateManager instance;

    /* renamed from: 刻槒唱镧詴, reason: contains not printable characters */
    @Nullable
    public InterfaceC7203 f24280;

    /* renamed from: 肌緭 */
    @Nullable
    public InterfaceC7201 f14840;

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭, reason: contains not printable characters */
    public boolean f24281;

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐, reason: contains not printable characters */
    @Nullable
    public AccessibilityService f24282;

    /* JADX WARN: Classes with same name are omitted:
  classes5.dex
 */
    /* compiled from: AutoScriptEngine.java */
    /* renamed from: 髿芫祜邕測.灞酞輀攼嵞漁綬迹$刻槒唱镧詴, reason: contains not printable characters */
    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
    public class C7200 implements InterfaceC4477 {

        /* renamed from: 肌緭 */
        public final /* synthetic */ AccessibilityNodeInfo f14841;

        public C7200(AccessibilityNodeInfo accessibilityNodeInfo) {
            this.f14841 = accessibilityNodeInfo;
        }

        @Override // p564.C7199.InterfaceC4477
        /* renamed from: 肌緭 */
        public boolean mo16170() {
            return OprateManager.this.m27657(this.f14841);
        }
    }


    public interface InterfaceC4477 {
        /* renamed from: 肌緭 */
        boolean mo16170();
    }


    public interface InterfaceC7201 {
        /* renamed from: 刻槒唱镧詴 */
        boolean mo27621(String str, String str2, AccessibilityNodeInfo accessibilityNodeInfo);

        /* renamed from: 肌緭 */
        void mo16169(boolean z);

        /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
        void mo27622(String str, String str2, boolean z);

        /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
        boolean mo27623(String str, String str2);
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


    public void mo27665(boolean z) {
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        InterfaceC7201 interfaceC7201 = this.f14840;
        if (interfaceC7201 != null) {
            interfaceC7201.mo16169(z);
        }
        this.f14840 = null;
        this.f24280 = null;
        this.f24281 = false;
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
        this.f24281 = z;
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
            InterfaceC7201 m27693 = m27693();
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
                InterfaceC7201 m27693 = m27693();
                if ((m27693 == null || m27693.mo27621("touchCenterClick", str, m16183.get(0))) ? false : true) {
                    return m27668(rect);
                }
            }
        }
        return false;
    }

    /* renamed from: 攏瑹迀虚熂熋卿悍铒誦爵, reason: contains not printable characters */
    public final void m27674(@Nullable InterfaceC7203 interfaceC7203) {
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
    public final void m27678(@Nullable InterfaceC7201 interfaceC7201) {
        this.f14840 = interfaceC7201;
    }

    @RequiresApi(api = 23)
    /* renamed from: 桿婤鷋鷯餒勡鈙洷薃蚺麮, reason: contains not printable characters */
    public boolean m27679(String str) {
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
                    InterfaceC7201 m27693 = m27693();
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
                                    InterfaceC7201 m276932 = m27693();
                                    if (m276932 != null) {
                                        m276932.mo27622("uncheck", m27675, false);
                                    }
                                    return true;
                                }
                                InterfaceC7201 m276933 = m27693();
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

    /* renamed from: 櫓昛刓叡賜, reason: contains not printable characters */
    public final void m27680(@NonNull String str) {
        try {
            Thread.sleep(1500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.f24281 = false;
        ScriptStateManager.m27695().m27698(str, this);
    }

    /* renamed from: 睳堋弗粥辊惶, reason: contains not printable characters */
    public final void m27681(@NonNull AccessibilityService accessibilityService) {
        this.f24282 = accessibilityService;
    }

    /* renamed from: 瞙餃莴埲, reason: contains not printable characters */
    public List<AccessibilityNodeInfo> m27682(String str) {
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

    /* renamed from: 礱咄頑, reason: contains not printable characters */
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
    /* renamed from: 祴嚚橺谋肬鬧舘, reason: contains not printable characters */
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
                    InterfaceC7201 m27693 = m27693();
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
                                InterfaceC7201 m276932 = m27693();
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
    /* renamed from: 綏牽躵糽稰烳俠垳襨捈桏鷋, reason: contains not printable characters */
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

    /* JADX WARN: Removed duplicated region for block: B:104:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00e4  */
    @androidx.annotation.RequiresApi(api = 23)
    /* renamed from: 綩私 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.List<android.view.accessibility.AccessibilityNodeInfo> m16183(java.lang.String r8) {
        /*
            Method dump skipped, instructions count: 364
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: p564.C7199.m16183(java.lang.String):java.util.List");
    }

    /* renamed from: 纩慐 */
    public final boolean m16184() {
        return this.f24281;
    }

    /* renamed from: 耣怳匮色紝参凵蛴纆勚躄, reason: contains not printable characters */
    public final void m27686() {
        this.f24281 = true;
    }

    /* renamed from: 肌緭 */
    public boolean mo16185(@NonNull ScriptState scriptState) {
        scriptState.m27634();
        scriptState.m27636();
        if (this.f24281) {
            ILog.log("isFinish return");
            return true;
        }
        InterfaceC7203 interfaceC7203 = this.f24280;
        if (interfaceC7203 != null && interfaceC7203.mo27654(scriptState.m27634(), scriptState.m27636())) {
            ILog.log("onStateBefore return");
            return true;
        }
        boolean m16186 = m16186(scriptState);
        ILog.log("swicthState：" + m16186 + "--" + scriptState.m27634() + "--" + scriptState.m27636());
        InterfaceC7203 interfaceC72032 = this.f24280;
        if (interfaceC72032 != null && interfaceC72032.mo16180(scriptState.m27634(), scriptState.m27636(), m16186)) {
            return true;
        }
        if (!this.f24281) {
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
        AccessibilityNodeInfo rootInActiveWindow = AccessibilityHelperService.f19276.getRootInActiveWindow();
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* renamed from: 躑漕 */
    public final boolean m16186(ScriptState scriptState) {
        String m27634 = scriptState.m27634();
        m27634.hashCode();
        char c2 = 65535;
        switch (m27634.hashCode()) {
            case -1997237900:
                if (m27634.equals("touchCenterClick")) {
                    c2 = 0;
                    break;
                }
                break;
            case -1317587267:
                if (m27634.equals("touchTopCenterClick")) {
                    c2 = 1;
                    break;
                }
                break;
            case -292418033:
                if (m27634.equals("uncheck")) {
                    c2 = 2;
                    break;
                }
                break;
            case 3015911:
                if (m27634.equals("back")) {
                    c2 = 3;
                    break;
                }
                break;
            case 3143097:
                if (m27634.equals("find")) {
                    c2 = 4;
                    break;
                }
                break;
            case 94627080:
                if (m27634.equals("check")) {
                    c2 = 5;
                    break;
                }
                break;
            case 94750088:
                if (m27634.equals("click")) {
                    c2 = 6;
                    break;
                }
                break;
            case 109522647:
                if (m27634.equals("sleep")) {
                    c2 = 7;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                if (Build.VERSION.SDK_INT >= 23) {
                    if (m27672(scriptState.m27636())) {
                        try {
                            Thread.sleep(600L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                    InterfaceC7201 interfaceC7201 = this.f14840;
                    if (interfaceC7201 != null && interfaceC7201.mo27623(scriptState.m27634(), scriptState.m27636())) {
                        return true;
                    }
                }
                return false;
            case 1:
                if (Build.VERSION.SDK_INT >= 23) {
                    if (m27673(scriptState.m27636())) {
                        try {
                            Thread.sleep(600L);
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
                        return true;
                    }
                    InterfaceC7201 interfaceC72012 = this.f14840;
                    if (interfaceC72012 != null && interfaceC72012.mo27623(scriptState.m27634(), scriptState.m27636())) {
                        return true;
                    }
                }
                return false;
            case 2:
                if (Build.VERSION.SDK_INT >= 23) {
                    if (m27679(scriptState.m27636())) {
                        try {
                            Thread.sleep(600L);
                        } catch (InterruptedException e3) {
                            e3.printStackTrace();
                        }
                        return true;
                    }
                    InterfaceC7201 interfaceC72013 = this.f14840;
                    if (interfaceC72013 != null && interfaceC72013.mo27623(scriptState.m27634(), scriptState.m27636())) {
                        return true;
                    }
                }
                return false;
            case 3:
                try {
                    Thread.sleep(600L);
                } catch (InterruptedException e4) {
                    e4.printStackTrace();
                }
                return m27676();
            case 4:
                if (Build.VERSION.SDK_INT >= 23) {
                    if (m16183(scriptState.m27636()) != null) {
                        try {
                            Thread.sleep(600L);
                        } catch (InterruptedException e5) {
                            e5.printStackTrace();
                        }
                        return true;
                    }
                    InterfaceC7201 interfaceC72014 = this.f14840;
                    if (interfaceC72014 != null && interfaceC72014.mo27623(scriptState.m27634(), scriptState.m27636())) {
                        return true;
                    }
                }
                return false;
            case 5:
                if (Build.VERSION.SDK_INT >= 23) {
                    if (m27684(scriptState.m27636())) {
                        try {
                            Thread.sleep(600L);
                        } catch (InterruptedException e6) {
                            e6.printStackTrace();
                        }
                        return true;
                    }
                    InterfaceC7201 interfaceC72015 = this.f14840;
                    if (interfaceC72015 != null && interfaceC72015.mo27623(scriptState.m27634(), scriptState.m27636())) {
                        return true;
                    }
                }
                return false;
            case 6:
                if (Build.VERSION.SDK_INT >= 23) {
                    if (m16187(scriptState.m27636())) {
                        try {
                            Thread.sleep(600L);
                        } catch (InterruptedException e7) {
                            e7.printStackTrace();
                        }
                        return true;
                    }
                    InterfaceC7201 interfaceC72016 = this.f14840;
                    if (interfaceC72016 != null && interfaceC72016.mo27623(scriptState.m27634(), scriptState.m27636())) {
                        return true;
                    }
                }
                return false;
            case 7:
                try {
                    Thread.sleep(Long.parseLong(scriptState.m27636()));
                } catch (InterruptedException e8) {
                    e8.printStackTrace();
                }
                return true;
            default:
                return false;
        }
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

    /* renamed from: 销薞醣戔攖餗, reason: contains not printable characters */
    public final boolean m27692(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo == null) {
            return false;
        }
        return ObjectUtils.equals(accessibilityNodeInfo.getClassName(), "android.widget.CheckBox") || ObjectUtils.equals(accessibilityNodeInfo.getClassName(), "android.widget.RadioButton") || ObjectUtils.equals(accessibilityNodeInfo.getClassName(), "android.widget.Switch");
    }

    @RequiresApi(api = 23)
    /* renamed from: 镐藻 */
    public boolean m16187(String str) {
        InterfaceC7201 m27693;
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
                    InterfaceC7201 m276932 = m27693();
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
    public final InterfaceC7201 m27693() {
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