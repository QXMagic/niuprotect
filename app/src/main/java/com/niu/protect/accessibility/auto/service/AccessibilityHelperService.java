package com.niu.protect.accessibility.auto.service;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ObjectUtils;
import com.niu.protect.accessibility.auto.service.tmp.AccessibilityUtils;
import com.niu.protect.accessibility.auto.service.tmp.AppHelper;
import com.niu.protect.accessibility.auto.service.tmp.ChildConfig;
import com.niu.protect.accessibility.auto.service.tmp.ConfigKey;
import com.niu.protect.accessibility.auto.service.tmp.SafeNotificationCheck;
import com.niu.protect.accessibility.auto.service.tmp.SafeScreenCheck;
import com.niu.protect.accessibility.auto.service.tmp.WorkService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;



public class AccessibilityHelperService extends AccessibilityService implements Handler.Callback {

    /* renamed from: 睳堋弗粥辊惶, reason: contains not printable characters */
    public static AccessibilityHelperService f19276;

    /* renamed from: 刻槒唱镧詴, reason: contains not printable characters */
    public Handler f19277;

    /* renamed from: 垡玖 */
    public AppFilter f11920;

    /* renamed from: 旞莍癡, reason: contains not printable characters */
    public SafeNotificationCheck f19278;

    /* renamed from: 祴嚚橺谋肬鬧舘, reason: contains not printable characters */
    public SafeScreenCheck f19280;

    /* renamed from: 肌緭 */
    public final Handler f11921;

    /* renamed from: 镐藻 */
    public AccessibilityNodeInfo f11922;

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭, reason: contains not printable characters */
    public final CopyOnWriteArrayList<InterfaceC6489> f19281 = new CopyOnWriteArrayList<>();

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐, reason: contains not printable characters */
    public final AtomicBoolean f19282 = new AtomicBoolean();

    /* renamed from: 灞酞輀攼嵞漁綬迹, reason: contains not printable characters */
    public final C6492.InterfaceC6494 f19279 = new C3244();

    /* JADX WARN: Classes with same name are omitted:
  classes5.dex
 */
    /* renamed from: com.zlfc.child.mvp.service.accessibility.AccessibilityHelperService$刻槒唱镧詴, reason: contains not printable characters */
    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
    public class HandlerC5785 extends Handler {
        public HandlerC5785(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            try {
                AccessibilityHelperService.this.m19975(message);
            } catch (Exception e) {
                e.printStackTrace();
                LogHelper.m27986().m27996(e.getMessage());
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted:
  classes5.dex
 */
    /* renamed from: com.zlfc.child.mvp.service.accessibility.AccessibilityHelperService$肌緭 */
    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
    public class C3244 implements C6492.InterfaceC6494 {
        public C3244() {
        }

        @Override // p384.C6492.InterfaceC6494
        /* renamed from: 偣炱嘵蟴峗舟轛 */
        public /* synthetic */ void mo19899() {
            C6495.m15026(this);
        }

        @Override // p384.C6492.InterfaceC6494
        /* renamed from: 睳堋弗粥辊惶 */
        public void mo19903() {
            AccessibilityHelperService.this.f19282.set(AppHelper.m24161(Mgr.m24656().m24660()));
        }

        @Override // p384.C6492.InterfaceC6494
        /* renamed from: 纩慐 */
        public void mo13500() {
            AccessibilityHelperService.this.f19282.set(AppHelper.m24161(Mgr.m24656().m24660()));
            GuardIntercept.f12774.set(System.currentTimeMillis());
        }

        @Override // p384.C6492.InterfaceC6494
        /* renamed from: 镐藻 */
        public void mo13501() {
            AccessibilityHelperService.this.f19282.set(AppHelper.m24161(Mgr.m24656().m24660()));
            GuardIntercept.f12774.set(System.currentTimeMillis());
        }

        @Override // p384.C6492.InterfaceC6494
        /* renamed from: 駭鑈趘薑衈講堍趃軏 */
        public /* synthetic */ void mo19904() {
            C6495.m24471(this);
        }
    }

    /* JADX WARN: Classes with same name are omitted:
  classes5.dex
 */
    /* renamed from: com.zlfc.child.mvp.service.accessibility.AccessibilityHelperService$葋申湋骶映鍮秄憁鎓羭, reason: contains not printable characters */
    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
    public class HandlerC5786 extends Handler {
        public HandlerC5786(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(@NonNull Message message) {
            try {
                AccessibilityHelperService.this.m19980((AccessibilityEvent) message.obj);
            } catch (Exception e) {
                e.printStackTrace();
                LogHelper.m27986().m27996(e.getMessage());
            }
        }
    }

    static {
//        StubApp.interface11(11960);
    }

    public AccessibilityHelperService() {
        HandlerThread handlerThread = new HandlerThread("AccessibilityHelperService_loop:");
        handlerThread.start();
        this.f19277 = new HandlerC5785(handlerThread.getLooper());
        HandlerThread handlerThread2 = new HandlerThread("AccessibilityHelperService_event:");
        handlerThread2.start();
        this.f11921 = new HandlerC5786(handlerThread2.getLooper());
        AccessibilityUtils.f22222.add(this);
    }

    /* renamed from: 卝閄侸靤溆鲁扅, reason: contains not printable characters */
    public static void m19965(boolean z) {
        AccessibilityHelperService accessibilityHelperService = f19276;
        if (accessibilityHelperService != null) {
            accessibilityHelperService.m19975(AccessibilityUtils.m24452().obtainMessage(2, Boolean.valueOf(z)));
        }
    }

    /* renamed from: 彻薯铏螙憣欖愡鼭, reason: contains not printable characters */
    public static void m19966() {
        AccessibilityHelperService accessibilityHelperService = f19276;
        if (accessibilityHelperService != null) {
            accessibilityHelperService.performGlobalAction(3);
        }
    }

    /* renamed from: 朽劔蚁灋嵿齩鶴琓麃沼瀙缹, reason: contains not printable characters */
    public static void m19967() {
        m19969("home");
    }

    /* renamed from: 灞酞輀攼嵞漁綬迹, reason: contains not printable characters */
    public static void m19968(String str) {
        if (f19276 != null) {
            LogUtil.m13850("AccessibilityHelperService->back->" + str);
            f19276.performGlobalAction(1);
        }
    }

    /* renamed from: 瞙餃莴埲, reason: contains not printable characters */
    public static void m19969(String str) {
        if (f19276 != null) {
            LogHelper.m27987("AccessibilityHelperService->" + str);
            f19276.performGlobalAction(2);
        }
    }

    /* renamed from: 鑭撇糁綖浓緗轟鱼萟磿焈, reason: contains not printable characters */
    public static /* synthetic */ void m19971() {
        try {
            Thread.sleep(500L);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        Mgr.m24658().mo22193();
    }

    /* renamed from: 陟瓠魒踱褢植螉嚜, reason: contains not printable characters */
    public static boolean m19972() {
        return f19276 != null;
    }

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐, reason: contains not printable characters */
    public static void m19973() {
        m19968("普通调用");
    }

    @Override // android.accessibilityservice.AccessibilityService
    public AccessibilityNodeInfo getRootInActiveWindow() {
        AccessibilityNodeInfo rootInActiveWindow = null;
        try {
            rootInActiveWindow = super.getRootInActiveWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (rootInActiveWindow == null) {
            return this.f11922;
        }
        this.f11922 = rootInActiveWindow;
        return this.f11922;
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(@NonNull Message message) {
        m19975(message);
        return false;
    }

    @Override // android.accessibilityservice.AccessibilityService
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        GuardIntercept.f12774.set(System.currentTimeMillis());
        if (SafeScreenCheck.m14522()) {
            AccessibilityUtils.m24452().sendEmptyMessage(7);
        }
        if (this.f19281.isEmpty()) {
            AccessibilityUtils.m24452().sendEmptyMessage(3);
            return;
        }
        if (!ConfigKey.m27247() || UserUtils.m24165()) {
            return;
        }
        if (Tools.m22490() || Tools.m22475()) {
            boolean z = false;
            List<AccessibilityNodeInfo> m19974 = m19974("com.android.systemui:id/title");
            if (ObjectUtils.isNotEmpty((Collection) m19974)) {
                Iterator<AccessibilityNodeInfo> it = m19974.iterator();
                while (it.hasNext()) {
                    CharSequence text = it.next().getText();
                    if (ObjectUtils.isNotEmpty(text) && "守护多任务".contentEquals(text)) {
                        z = true;
                    }
                }
            }
            if (z) {
                LogUtil.m13850("守护多任务执行Home");
                m19967();
            }
        }
//        if (!ModelManager.m22767()) {
//            PlugHelper.m19875().m19888(accessibilityEvent);
//        }
        if (this.f11921 == null || accessibilityEvent.getPackageName() == null || this.f19282.get()) {
            return;
        }
        Handler handler = this.f11921;
        handler.sendMessage(handler.obtainMessage(1, AccessibilityEvent.obtain(accessibilityEvent)));
    }


    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        C6492.m24467().m24468(this.f19279);
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        if (ConfigKey.m27247()) {
            new Thread(new Runnable() { // from class: 硭怯夵蟭飮沯.鞈鵚主瀭孩濣痠閕讠陲檓敐
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityHelperService.m19971();
                }
            }).start();
        }
        C6492.m24467().m24469(this.f19279);
    }

    @Override // android.accessibilityservice.AccessibilityService
    public void onInterrupt() {
    }

    @Override // android.accessibilityservice.AccessibilityService
    public void onServiceConnected() {
        super.onServiceConnected();
        f19276 = this;
        this.f19282.set(false);
        if (ConfigKey.f14714) {
            PermissionEvent permissionEvent = new PermissionEvent();
            permissionEvent.putData("id", 12);
            permissionEvent.setCode(100);
            permissionEvent.post();
        }
        this.f11920 = AppFilter.m15534(this);
        if (ConfigKey.m27247() && !ConfigKey.m27235()) {
            ContextCompat.startForegroundService(f19276, new Intent(f19276, (Class<?>) WorkService.class));
        } else {
            if (!ConfigKey.m16029() || ConfigKey.m27256()) {
                return;
            }
//            ContextCompat.startForegroundService(f19276, new Intent(f19276, (Class<?>) UnBindService.class));
        }
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        f19276 = null;
        Iterator<InterfaceC6489> it = this.f19281.iterator();
        while (it.hasNext()) {
            InterfaceC6489 next = it.next();
            if (next instanceof InterfaceC3712) {
                ((InterfaceC3712) next).onDestroy();
            }
        }
        OprateManager.instance().m27690();
        return super.onUnbind(intent);
    }

    /* renamed from: 偣炱嘵蟴峗舟轛, reason: contains not printable characters */
    public List<AccessibilityNodeInfo> m19974(String str) {
        AccessibilityNodeInfo rootInActiveWindow = getRootInActiveWindow();
        if (rootInActiveWindow != null) {
            return rootInActiveWindow.findAccessibilityNodeInfosByViewId(str);
        }
        return null;
    }

    /* renamed from: 垡玖 */
    public boolean m13526(AccessibilityNodeInfo accessibilityNodeInfo) {
        boolean z = false;
        while (accessibilityNodeInfo != null) {
            boolean performAction = accessibilityNodeInfo.performAction(16);
            if (performAction) {
                return performAction;
            }
            if (accessibilityNodeInfo.performAction(16)) {
                return true;
            }
            accessibilityNodeInfo = accessibilityNodeInfo.getParent();
            z = performAction;
        }
        return z;
    }

    /* renamed from: 旞莍癡, reason: contains not printable characters */
    public final void m19975(@NonNull Message message) {
        switch (message.what) {
            case 1:
                AccessibilityEventImpl accessibilityEventImpl = (AccessibilityEventImpl) message.obj;
                try {
                    Iterator<InterfaceC6489> it = this.f19281.iterator();
                    while (it.hasNext()) {
                        InterfaceC6489 next = it.next();
                        if (next != null) {
                            next.mo22210(accessibilityEventImpl);
                        }
                    }
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    LogHelper.m27986().m27996(e.getMessage());
                    return;
                }
            case 2:
                boolean booleanValue = ((Boolean) message.obj).booleanValue();
                Iterator<InterfaceC6489> it2 = this.f19281.iterator();
                while (it2.hasNext()) {
                    InterfaceC6489 next2 = it2.next();
                    if (next2 instanceof InterfaceC3712) {
                        ((InterfaceC3712) next2).mo23164(booleanValue);
                    }
                }
                return;
            case 3:
                m19979();
                return;
            case 4:
                Iterator<InterfaceC6489> it3 = this.f19281.iterator();
                while (it3.hasNext()) {
                    InterfaceC6489 next3 = it3.next();
                    if (next3 instanceof InterfaceC3712) {
                        ((InterfaceC3712) next3).mo23163();
                    }
                }
                return;
            case 5:
                SafeNotificationCheck safeNotificationCheck = this.f19278;
                if (safeNotificationCheck == null) {
                    return;
                }
                Map map = (Map) message.obj;
                safeNotificationCheck.m14526((AccessibilityEventImpl) map.get("event"), (Notification) map.get("notification"));
                return;
            case 6:
                ChildConfig childConfig = (ChildConfig) message.obj;
                Iterator<InterfaceC6489> it4 = this.f19281.iterator();
                while (it4.hasNext()) {
                    InterfaceC6489 next4 = it4.next();
                    if (next4 instanceof InterfaceC3712) {
                        ((InterfaceC3712) next4).mo23165(childConfig);
                    }
                }
                return;
            case 7:
                SafeScreenCheck safeScreenCheck = this.f19280;
                if (safeScreenCheck != null) {
                    safeScreenCheck.m23160();
                    return;
                }
                return;
            default:
                return;
        }
    }

    /* renamed from: 櫓昛刓叡賜, reason: contains not printable characters */
    public AccessibilityNodeInfo m19976() {
        try {
            return getRootInActiveWindow();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<AccessibilityNodeInfo> m19977(String... strArr) {
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            AccessibilityNodeInfo rootInActiveWindow = getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                break;
            }
            try {
                List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str);
                if (findAccessibilityNodeInfosByText != null && findAccessibilityNodeInfosByText.size() > 0) {
                    arrayList.addAll(findAccessibilityNodeInfosByText);
                    return arrayList;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return arrayList;
    }

    /* renamed from: 祴嚚橺谋肬鬧舘, reason: contains not printable characters */
    public AccessibilityNodeInfo m19978(String str) {
        List<AccessibilityNodeInfo> m13528 = m13528(str);
        if (m13528.isEmpty()) {
            return null;
        }
        for (AccessibilityNodeInfo accessibilityNodeInfo : m13528) {
            if (ObjectUtils.equals(accessibilityNodeInfo.getText(), str)) {
                return accessibilityNodeInfo;
            }
        }
        return null;
    }

    public boolean m13527(String... strArr) {
        return !m13528(strArr).isEmpty();
    }

    public final void m19979() {
        SafeGuardMonitor safeGuardMonitor = new SafeGuardMonitor(this);
        try {
            safeGuardMonitor.m23167();
            this.f19281.clear();
            this.f19281.add(safeGuardMonitor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.f19278 = new SafeNotificationCheck(this);
        SafeScreenCheck safeScreenCheck = new SafeScreenCheck();
        this.f19280 = safeScreenCheck;
        safeScreenCheck.m23162(f19276);
        OprateManager.instance().m27681(this);
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:76:0x0135 -> B:54:0x0148). Please report as a decompilation issue!!! */
    /* renamed from: 蝸餺閃喍, reason: contains not printable characters */
    public void m19980(AccessibilityEvent accessibilityEvent) {
        CharSequence packageName = accessibilityEvent.getPackageName();
        String packageName2 = getPackageName();
        if (packageName == null || this.f19282.get()) {
            return;
        }
        if (!this.f19281.isEmpty() && this.f19281.get(0) != null) {
            if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
                if (packageName.equals(packageName2)) {
                    AccessibilityUtils.m24452().sendEmptyMessage(4);
                    return;
                }
                if (!ObjectUtils.equals("com.android.systemui", packageName) || this.f19278 == null || this.f19277 == null) {
                    return;
                }
                Parcelable parcelableData = accessibilityEvent.getParcelableData();
                if (parcelableData instanceof Notification) {
                    AccessibilityEventImpl m19981 = m19981(accessibilityEvent);
                    HashMap hashMap = new HashMap();
                    hashMap.put("event", m19981);
                    hashMap.put("notification", parcelableData);
                    this.f19277.sendMessage(AccessibilityUtils.m24452().obtainMessage(5, hashMap));
                    return;
                }
                return;
            }
            if (ObjectUtils.equals(packageName, packageName2)) {
                return;
            }
            if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED || this.f11920.m25932(packageName.toString())) {
                if (ObjectUtils.equals("com.android.systemui", packageName) && (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED || accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED
                        || accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_VIEW_LONG_CLICKED
                        || accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED)) {
                    AccessibilityUtils.m24450(AccessibilityUtils.m24452().obtainMessage(1, m19981(accessibilityEvent)));
                    return;
                }
                try {
                    AccessibilityEventImpl m199812 = m19981(accessibilityEvent);
                    if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
                        Handler handler = this.f19277;
                        if (handler == null) {
                            return;
                        } else {
                            handler.sendMessage(AccessibilityUtils.m24452().obtainMessage(1, m199812));
                        }
                    } else {
                        if (accessibilityEvent.getEventType() != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
                                && accessibilityEvent.getEventType() != AccessibilityEvent.TYPE_VIEW_CONTEXT_CLICKED
                                && accessibilityEvent.getEventType() != AccessibilityEvent.TYPE_VIEW_LONG_CLICKED
                                && accessibilityEvent.getEventType() != AccessibilityEvent.TYPE_VIEW_CLICKED) {
                            if (this.f19278 == null || this.f19277 == null) {
                                return;
                            }
                            Parcelable parcelableData2 = accessibilityEvent.getParcelableData();
                            if (parcelableData2 instanceof Notification) {
                                HashMap hashMap2 = new HashMap();
                                hashMap2.put("event", m199812);
                                hashMap2.put("notification", parcelableData2);
                                this.f19277.sendMessage(AccessibilityUtils.m24452().obtainMessage(5, hashMap2));
                            }
                        }
                        AccessibilityUtils.m24450(AccessibilityUtils.m24452().obtainMessage(1, m199812));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogHelper.m27987(e.getMessage());
                }
                return;
            }
            return;
        }
        AccessibilityUtils.m24452().sendEmptyMessage(3);
    }

    /* renamed from: 酸恚辰橔纋黺, reason: contains not printable characters */
    public final AccessibilityEventImpl m19981(AccessibilityEvent accessibilityEvent) {
        AccessibilityNodeInfo accessibilityNodeInfo;
        try {
            accessibilityNodeInfo = accessibilityEvent.getSource();
        } catch (Exception e) {
            e.printStackTrace();
            accessibilityNodeInfo = null;
        }
        return new AccessibilityEventImpl(accessibilityEvent.getPackageName(), accessibilityEvent.getEventType(), accessibilityEvent.getParcelableData(), accessibilityEvent.getEventTime(), accessibilityEvent.getAction(), accessibilityEvent.getClassName(), accessibilityEvent.getContentDescription(), accessibilityEvent.getText(), accessibilityNodeInfo, accessibilityEvent.getContentChangeTypes());
    }

    /* renamed from: 镐藻 */
    public List<AccessibilityNodeInfo> m13528(String... strArr) {
        ArrayList arrayList = new ArrayList();
        AccessibilityNodeInfo m19976 = m19976();
        if (m19976 == null) {
            return arrayList;
        }
        try {
            for (String str : strArr) {
                List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText = m19976.findAccessibilityNodeInfosByText(str);
                if (findAccessibilityNodeInfosByText != null && findAccessibilityNodeInfosByText.size() > 0) {
                    arrayList.addAll(findAccessibilityNodeInfosByText);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}

