package accessibility;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import accessibility.lib.AccessibilityUtils;
import accessibility.lib.AppHelper;
import accessibility.lib.ConfigKey;
import accessibility.lib.SafeNotificationCheck;
import accessibility.lib.WorkService;
import accessibility.lib.ChildConfig;
import accessibility.lib.SafeScreenCheck;


public class AccessibilityHelperService extends AccessibilityService implements Handler.Callback {

    /* renamed from: 睳堋弗粥辊惶, reason: contains not printable characters */
    public static AccessibilityHelperService instance;

    public Handler handler;

    public AppFilter appFilter;

    public SafeNotificationCheck f19278;

    public SafeScreenCheck safeScreenCheck;

    /* renamed from: 肌緭 */
    public final Handler f11921;

    public AccessibilityNodeInfo nodeInfo;

    public final CopyOnWriteArrayList<InterfaceC6489> handlerList = new CopyOnWriteArrayList<>();

    public final AtomicBoolean atomicBoolean = new AtomicBoolean();

    public final C6492.InterfaceC6494 f19279 = new C3244();

    public class HandlerC5785 extends Handler {
        public HandlerC5785(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message message) {
            try {
                AccessibilityHelperService.this.executeMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
                LogHelper.instance().info(e.getMessage());
            }
        }
    }



    public class C3244 implements C6492.InterfaceC6494 {
        public C3244() {
        }

        @Override // p384.C6492.InterfaceC6494
        public /* synthetic */ void mo19899() {
            C6495.m15026(this);
        }

        @Override // p384.C6492.InterfaceC6494
        public void mo19903() {
            AccessibilityHelperService.this.atomicBoolean.set(AppHelper.isLocked(Mgr.getInstance().context()));
        }

        @Override // p384.C6492.InterfaceC6494
        /* renamed from: 纩慐 */
        public void mo13500() {
            AccessibilityHelperService.this.atomicBoolean.set(AppHelper.isLocked(Mgr.getInstance().context()));
            GuardIntercept.atomTime.set(System.currentTimeMillis());
        }

        @Override // p384.C6492.InterfaceC6494
        /* renamed from: 镐藻 */
        public void mo13501() {
            AccessibilityHelperService.this.atomicBoolean.set(AppHelper.isLocked(Mgr.getInstance().context()));
            GuardIntercept.atomTime.set(System.currentTimeMillis());
        }

        @Override // p384.C6492.InterfaceC6494
        /* renamed from: 駭鑈趘薑衈講堍趃軏 */
        public /* synthetic */ void mo19904() {
            C6495.m24471(this);
        }
    }


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
                LogHelper.instance().info(e.getMessage());
            }
        }
    }

//    static {
////        StubApp.interface11(11960);
//    }

    public AccessibilityHelperService() {
        HandlerThread handlerThread = new HandlerThread("AccessibilityHelperService_loop:");
        handlerThread.start();
        this.handler = new HandlerC5785(handlerThread.getLooper());
        HandlerThread handlerThread2 = new HandlerThread("AccessibilityHelperService_event:");
        handlerThread2.start();
        this.f11921 = new HandlerC5786(handlerThread2.getLooper());
        AccessibilityUtils.callbacks.add(this);
    }

    /* renamed from: 卝閄侸靤溆鲁扅, reason: contains not printable characters */
    public static void m19965(boolean z) {
        AccessibilityHelperService accessibilityHelperService = instance;
        if (accessibilityHelperService != null) {
            accessibilityHelperService.executeMessage(AccessibilityUtils.m24452().obtainMessage(2, z));
        }
    }

    /* renamed from: 彻薯铏螙憣欖愡鼭, reason: contains not printable characters */
    public static void m19966() {
        AccessibilityHelperService accessibilityHelperService = instance;
        if (accessibilityHelperService != null) {
            accessibilityHelperService.performGlobalAction(3);
        }
    }

    /* renamed from: 朽劔蚁灋嵿齩鶴琓麃沼瀙缹, reason: contains not printable characters */
    public static void goHome() {
        goHome("home");
    }

    public static void goBack(String str) {
        if (instance != null) {
            LogUtil.m13850("AccessibilityHelperService->back->" + str);
            instance.performGlobalAction(GLOBAL_ACTION_BACK);
        }
    }

    /* renamed from: 瞙餃莴埲, reason: contains not printable characters */
    public static void goHome(String str) {
        if (instance != null) {
            LogHelper.m27987("AccessibilityHelperService->" + str);
            instance.performGlobalAction(GLOBAL_ACTION_HOME);
        }
    }

    /* renamed from: 鑭撇糁綖浓緗轟鱼萟磿焈, reason: contains not printable characters */
    public static /* synthetic */ void m19971() {
        try {
            Thread.sleep(500L);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        Mgr.getGuardIntercept().mo22193();
    }

    public static boolean m19972() {
        return instance != null;
    }

    public static void goBack() {
        goBack("普通调用");
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
            return this.nodeInfo;
        }
        this.nodeInfo = rootInActiveWindow;
        return this.nodeInfo;
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(@NonNull Message message) {
        executeMessage(message);
        return false;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        GuardIntercept.atomTime.set(System.currentTimeMillis());
        if (SafeScreenCheck.m14522()) {
            AccessibilityUtils.m24452().sendEmptyMessage(7);
        }
        if (this.handlerList.isEmpty()) {
            AccessibilityUtils.m24452().sendEmptyMessage(3);
            return;
        }
        if (!ConfigKey.m27247() || UserUtils.m24165()) {
            return;
        }
        if (Tools.versionLess11() || Tools.isPad()) {
            boolean z = false;
            List<AccessibilityNodeInfo> m19974 = findNodeByViewId("com.android.systemui:id/title");
            if (ObjectUtils.isNotEmpty(m19974)) {
                for (AccessibilityNodeInfo accessibilityNodeInfo : m19974) {
                    CharSequence text = accessibilityNodeInfo.getText();
                    if (ObjectUtils.isNotEmpty(text) && "守护多任务".contentEquals(text)) {
                        z = true;
                    }
                }
            }
            if (z) {
                LogUtil.m13850("守护多任务执行Home");
                goHome();
            }
        }
//        if (!ModelManager.m22767()) {
//            PlugHelper.m19875().m19888(accessibilityEvent);
//        }
        if (this.f11921 == null || accessibilityEvent.getPackageName() == null || this.atomicBoolean.get()) {
            return;
        }
        Handler handler = this.f11921;
        handler.sendMessage(handler.obtainMessage(1, AccessibilityEvent.obtain(accessibilityEvent)));
    }


    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        C6492.m24467().add(this.f19279);
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
        C6492.m24467().remove(this.f19279);
    }

    @Override // android.accessibilityservice.AccessibilityService
    public void onInterrupt() {
    }

    @Override // android.accessibilityservice.AccessibilityService
    public void onServiceConnected() {
        super.onServiceConnected();
        instance = this;
        this.atomicBoolean.set(false);
        if (ConfigKey.f14714) {
            PermissionEvent permissionEvent = new PermissionEvent();
            permissionEvent.putData("id", 12);
            permissionEvent.setCode(100);
            permissionEvent.post();
        }
        this.appFilter = AppFilter.instance(this);
        if (ConfigKey.m27247() && !ConfigKey.m27235()) {
            ContextCompat.startForegroundService(instance, new Intent(instance, (Class<?>) WorkService.class));
        } else {
            if (!ConfigKey.m16029() || ConfigKey.m27256()) {
                return;
            }
//            ContextCompat.startForegroundService(f19276, new Intent(f19276, (Class<?>) UnBindService.class));
        }
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        instance = null;
        for (InterfaceC6489 next : this.handlerList) {
            if (next instanceof InterfaceC3712) {
                ((InterfaceC3712) next).onDestroy();
            }
        }
        OprateManager.instance().m27690();
        return super.onUnbind(intent);
    }

    public List<AccessibilityNodeInfo> findNodeByViewId(String str) {
        AccessibilityNodeInfo rootInActiveWindow = getRootInActiveWindow();
        if (rootInActiveWindow != null) {
            return rootInActiveWindow.findAccessibilityNodeInfosByViewId(str);
        }
        return null;
    }

    public boolean performAction(AccessibilityNodeInfo nodeInfo) {
        while (nodeInfo != null) {
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            boolean performAction = nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            if (performAction) {
                return true;
            }
            if (nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                return true;
            }
            nodeInfo = nodeInfo.getParent();
        }
        return false;
    }

    public final void executeMessage(@NonNull Message message) {
        switch (message.what) {
            case 1:
                AccessibilityEventImpl accessibilityEventImpl = (AccessibilityEventImpl) message.obj;
                try {
                    for (InterfaceC6489 next : this.handlerList) {
                        if (next != null) {
                            next.mo22210(accessibilityEventImpl);
                        }
                    }
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    LogHelper.instance().info(e.getMessage());
                    return;
                }
            case 2:
                boolean booleanValue = (Boolean) message.obj;
                for (InterfaceC6489 next2 : this.handlerList) {
                    if (next2 instanceof InterfaceC3712) {
                        ((InterfaceC3712) next2).mo23164(booleanValue);
                    }
                }
                return;
            case 3:
                m19979();
                return;
            case 4:
                for (InterfaceC6489 next3 : this.handlerList) {
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
                for (InterfaceC6489 next4 : this.handlerList) {
                    if (next4 instanceof InterfaceC3712) {
                        ((InterfaceC3712) next4).setConfig(childConfig);
                    }
                }
                return;
            case 7:
                SafeScreenCheck safeScreenCheck = this.safeScreenCheck;
                if (safeScreenCheck != null) {
                    safeScreenCheck.m23160();
                }
        }
    }

    public AccessibilityNodeInfo rootWindow() {
        try {
            return getRootInActiveWindow();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<AccessibilityNodeInfo> findUiByText(String... strArr) {
        ArrayList<AccessibilityNodeInfo> arrayList = new ArrayList<>();
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

    public AccessibilityNodeInfo findNodeInfoByText(String str) {
        List<AccessibilityNodeInfo> m13528 = matchNodelist(str);
        if (m13528.isEmpty()) {
            return null;
        }
        for (AccessibilityNodeInfo accessibilityNodeInfo : m13528) {
            if (TextUtils.equals(accessibilityNodeInfo.getText(), str)) {
                return accessibilityNodeInfo;
            }
        }
        return null;
    }

    public boolean hasNodeExist(String... strArr) {
        return !matchNodelist(strArr).isEmpty();
    }

    public final void m19979() {
        SafeGuardMonitor safeGuardMonitor = new SafeGuardMonitor(this);
        try {
            safeGuardMonitor.m23167();
            this.handlerList.clear();
            this.handlerList.add(safeGuardMonitor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.f19278 = new SafeNotificationCheck(this);
        SafeScreenCheck safeScreenCheck = new SafeScreenCheck();
        this.safeScreenCheck = safeScreenCheck;
        safeScreenCheck.m23162(instance);
        OprateManager.instance().m27681(this);
    }

    public void m19980(AccessibilityEvent accessibilityEvent) {
        CharSequence packageName = accessibilityEvent.getPackageName();
        String packageName2 = getPackageName();
        if (packageName == null || this.atomicBoolean.get()) {
            return;
        }
        if (!this.handlerList.isEmpty() && this.handlerList.get(0) != null) {
            if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
                if (packageName.equals(packageName2)) {
                    AccessibilityUtils.m24452().sendEmptyMessage(4);
                    return;
                }
                if (!TextUtils.equals("com.android.systemui", packageName) || this.f19278 == null || this.handler == null) {
                    return;
                }
                Parcelable parcelableData = accessibilityEvent.getParcelableData();
                if (parcelableData instanceof Notification) {
                    AccessibilityEventImpl m19981 = createEvent(accessibilityEvent);
                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("event", m19981);
                    hashMap.put("notification", parcelableData);
                    this.handler.sendMessage(AccessibilityUtils.m24452().obtainMessage(5, hashMap));
                    return;
                }
                return;
            }
            if (TextUtils.equals(packageName, packageName2)) {
                return;
            }
            if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED || this.appFilter.m25932(packageName.toString())) {
                if (TextUtils.equals("com.android.systemui", packageName) && (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED || accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED
                        || accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_VIEW_LONG_CLICKED
                        || accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED)) {
                    AccessibilityUtils.recursionFireMessage(AccessibilityUtils.m24452().obtainMessage(1, createEvent(accessibilityEvent)));
                    return;
                }
                try {
                    AccessibilityEventImpl m199812 = createEvent(accessibilityEvent);
                    if (accessibilityEvent.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
                        Handler handler = this.handler;
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
                            if (this.f19278 == null || this.handler == null) {
                                return;
                            }
                            Parcelable parcelableData2 = accessibilityEvent.getParcelableData();
                            if (parcelableData2 instanceof Notification) {
                                HashMap<String,Object> hashMap2 = new HashMap<>();
                                hashMap2.put("event", m199812);
                                hashMap2.put("notification", parcelableData2);
                                this.handler.sendMessage(AccessibilityUtils.m24452().obtainMessage(5, hashMap2));
                            }
                        }
                        AccessibilityUtils.recursionFireMessage(AccessibilityUtils.m24452().obtainMessage(1, m199812));
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

    public final AccessibilityEventImpl createEvent(AccessibilityEvent accessibilityEvent) {
        AccessibilityNodeInfo accessibilityNodeInfo;
        try {
            accessibilityNodeInfo = accessibilityEvent.getSource();
        } catch (Exception e) {
            e.printStackTrace();
            accessibilityNodeInfo = null;
        }
        return new AccessibilityEventImpl(accessibilityEvent.getPackageName(), accessibilityEvent.getEventType(), accessibilityEvent.getParcelableData(), accessibilityEvent.getEventTime(), accessibilityEvent.getAction(), accessibilityEvent.getClassName(), accessibilityEvent.getContentDescription(), accessibilityEvent.getText(), accessibilityNodeInfo, accessibilityEvent.getContentChangeTypes());
    }

    public List<AccessibilityNodeInfo> matchNodelist(String... strArr) {
        ArrayList<AccessibilityNodeInfo> arrayList = new ArrayList<>();
        AccessibilityNodeInfo info = rootWindow();
        if (info == null) {
            return arrayList;
        }
        try {
            for (String str : strArr) {
                List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText = info.findAccessibilityNodeInfosByText(str);
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

