package com.niu.protect.accessibility.auto.service.tmp;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ObjectUtils;
import com.niu.protect.accessibility.auto.service.AccessibilityHelperService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AccessibilityUtils {

    /* renamed from: 刻槒唱镧詴, reason: contains not printable characters */
    public static AtomicInteger f22220 = null;

    /* renamed from: 肌緭 */
    public static List<Handler> f13574 = null;

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭, reason: contains not printable characters */
    public static ExecutorService f22221;

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐, reason: contains not printable characters */
    public static HashSet<Handler.Callback> f22222 = null;

    /* JADX WARN: Classes with same name are omitted:
  classes5.dex
 */
    /* compiled from: AccessibilityUtils.java */
    /* renamed from: 硭怯夵蟭飮沯.垡玖$肌緭, reason: contains not printable characters */
    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
    public class HandlerC6487 extends Handler {
        public HandlerC6487(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(@NonNull Message message) {
            super.handleMessage(message);
            try {
                AccessibilityUtils.m24447(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    {
        ArrayList arrayList = new ArrayList();
        f13574 = arrayList;
        f22220 = new AtomicInteger(0);
        f22221 = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        f22222 = new HashSet<>();
        arrayList.clear();
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            HandlerThread handlerThread = new HandlerThread("AccessibilityHelperService_loop:" + i);
            handlerThread.start();
            f13574.add(new HandlerC6487(handlerThread.getLooper()));
        }
    }

    /* renamed from: 偣炱嘵蟴峗舟轛, reason: contains not printable characters */
    public static void m24447(Message message) {
        Iterator<Handler.Callback> it = f22222.iterator();
        while (it.hasNext()) {
            try {
                it.next().handleMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    /* renamed from: 垡玖 */
    public static AccessibilityNodeInfo m15016(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        int childCount;
        if (accessibilityNodeInfo == null || (childCount = accessibilityNodeInfo.getChildCount()) <= 0) {
            return null;
        }
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                if (TextUtils.equals(child.getClassName(), str)) {
                    return child;
                }
                AccessibilityNodeInfo m15016 = m15016(child, str);
                if (m15016 != null) {
                    return m15016;
                }
            }
        }
        return null;
    }

    @Nullable
    /* renamed from: 旞莍癡, reason: contains not printable characters */
    public static List<AccessibilityNodeInfo> m24449(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        List<AccessibilityNodeInfo> m24449;
        if (accessibilityNodeInfo == null) {
            return null;
        }
        List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(str);
        if (findAccessibilityNodeInfosByText != null && !findAccessibilityNodeInfosByText.isEmpty()) {
            return findAccessibilityNodeInfosByText;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        if (childCount <= 0) {
            return null;
        }
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null && (m24449 = m24449(child, str)) != null && !m24449.isEmpty()) {
                return m24449;
            }
        }
        return null;
    }

    /* renamed from: 櫓昛刓叡賜, reason: contains not printable characters */
    public static void m24450(final Message message) {
        try {
            f22221.execute(new Runnable() { // from class: 硭怯夵蟭飮沯.灞酞輀攼嵞漁綬迹
                @Override // java.lang.Runnable
                public final void run() {
                    AccessibilityUtils.m24447(message);
                }
            });
        } catch (Exception unused) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            f22221 = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
            m24450(message);
        }
    }

    /* renamed from: 灞酞輀攼嵞漁綬迹, reason: contains not printable characters */
    public static List<AccessibilityNodeInfo> m24451(String str) {
        AccessibilityHelperService accessibilityHelperService = AccessibilityHelperService.f19276;
        if (accessibilityHelperService == null) {
            return null;
        }
        return m24456(accessibilityHelperService.getRootInActiveWindow(), str);
    }

    /* renamed from: 睳堋弗粥辊惶, reason: contains not printable characters */
    public static Handler m24452() {
        AtomicInteger atomicInteger = f22220;
        int i = atomicInteger.get() + 1;
        List<Handler> list = f13574;
        if (i >= list.size()) {
            atomicInteger.set(0);
        }
        return list.get(atomicInteger.getAndAdd(1));
    }

    @Nullable
    /* renamed from: 祴嚚橺谋肬鬧舘, reason: contains not printable characters */
    public static AccessibilityNodeInfo m24453(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        List<AccessibilityNodeInfo> m24449 = m24449(accessibilityNodeInfo, str);
        if (m24449 == null || m24449.isEmpty()) {
            return null;
        }
        for (AccessibilityNodeInfo accessibilityNodeInfo2 : m24449) {
            CharSequence text = accessibilityNodeInfo2.getText();
            if (ObjectUtils.isNotEmpty(text) && str.contentEquals(text)) {
                return accessibilityNodeInfo2;
            }
        }
        return null;
    }

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭, reason: contains not printable characters */
    public static AccessibilityNodeInfo m24454(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo m24454;
        if (accessibilityNodeInfo.isCheckable()) {
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child.isCheckable()) {
                return child;
            }
            if (child.getChildCount() > 0 && (m24454 = m24454(child)) != null) {
                return m24454;
            }
        }
        return null;
    }

    @Nullable
    /* renamed from: 镐藻 */
    public static AccessibilityNodeInfo m15018(String str) {
        AccessibilityHelperService accessibilityHelperService = AccessibilityHelperService.f19276;
        if (accessibilityHelperService == null) {
            return null;
        }
        return m24453(accessibilityHelperService.getRootInActiveWindow(), str);
    }

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐, reason: contains not printable characters */
    public static List<AccessibilityNodeInfo> m24456(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId;
        if (accessibilityNodeInfo == null || (findAccessibilityNodeInfosByViewId = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(str)) == null || findAccessibilityNodeInfosByViewId.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (AccessibilityNodeInfo accessibilityNodeInfo2 : findAccessibilityNodeInfosByViewId) {
            if (accessibilityNodeInfo2 != null && accessibilityNodeInfo2.isVisibleToUser()) {
                arrayList.add(accessibilityNodeInfo2);
            }
        }
        return arrayList;
    }
}