package accessibility.lib;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import accessibility.AccessibilityHelperService;

public class AccessibilityUtils {

    public static AtomicInteger atomInt;

    public static List<Handler> handlerList = new ArrayList<>();

    public static ExecutorService executorService;

    public static HashSet<Handler.Callback> callbacks = new HashSet<>();


    public static class FireCallBack extends Handler {
        public FireCallBack(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message message) {
            super.handleMessage(message);
            try {
                AccessibilityUtils.fireMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static {

        atomInt = new AtomicInteger(0);
        executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());

        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            HandlerThread handlerThread = new HandlerThread("AccessibilityHelperService_loop:" + i);
            handlerThread.start();
            FireCallBack hder = new FireCallBack(handlerThread.getLooper());
            handlerList.add(hder);
        }
    }

    /* renamed from: 偣炱嘵蟴峗舟轛, reason: contains not printable characters */
    public static void fireMessage(Message message) {
        Iterator<Handler.Callback> it = callbacks.iterator();
        while (it.hasNext()) {
            try {
                it.next().handleMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取cls对应的Child
     * */
    public static AccessibilityNodeInfo findChild(AccessibilityNodeInfo accessibilityNodeInfo, String clsName) {
        int childCount;
        if (accessibilityNodeInfo == null || (childCount = accessibilityNodeInfo.getChildCount()) <= 0) {
            return null;
        }
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                if (TextUtils.equals(child.getClassName(), clsName)) {
                    return child;
                }
                AccessibilityNodeInfo m15016 = findChild(child, clsName);
                if (m15016 != null) {
                    return m15016;
                }
            }
        }
        return null;
    }

    @Nullable
    public static List<AccessibilityNodeInfo> recursionFindNode(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
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
            if (child != null && (m24449 = recursionFindNode(child, str)) != null && !m24449.isEmpty()) {
                return m24449;
            }
        }
        return null;
    }

    /* renamed from: 櫓昛刓叡賜, reason: contains not printable characters */
    public static void recursionFireMessage(final Message message) {
        try {
            executorService.execute(() -> AccessibilityUtils.fireMessage(message));
        } catch (Exception unused) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
            recursionFireMessage(message);
        }
    }

    public static List<AccessibilityNodeInfo> findVisibleNodes(String str) {
        AccessibilityHelperService accessibilityHelperService = AccessibilityHelperService.instance;
        if (accessibilityHelperService == null) {
            return null;
        }
        return findVisibleNodes(accessibilityHelperService.getRootInActiveWindow(), str);
    }

    public static Handler m24452() {
        AtomicInteger atomicInteger = atomInt;
        int i = atomicInteger.get() + 1;
        List<Handler> list = handlerList;
        if (i >= list.size()) {
            atomicInteger.set(0);
        }
        return list.get(atomicInteger.getAndAdd(1));
    }

    @Nullable
    public static AccessibilityNodeInfo findNodeByText(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        List<AccessibilityNodeInfo> m24449 = recursionFindNode(accessibilityNodeInfo, str);
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

    //
//    public static AccessibilityNodeInfo m24454(AccessibilityNodeInfo accessibilityNodeInfo) {
//        AccessibilityNodeInfo m24454;
//        if (accessibilityNodeInfo.isCheckable()) {
//            return accessibilityNodeInfo;
//        }
//        int childCount = accessibilityNodeInfo.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
//            if (child.isCheckable()) {
//                return child;
//            }
//            if (child.getChildCount() > 0 && (m24454 = m24454(child)) != null) {
//                return m24454;
//            }
//        }
//        return null;
//    }

    @Nullable
    public static AccessibilityNodeInfo findNode(String str) {
        AccessibilityHelperService accessibilityHelperService = AccessibilityHelperService.instance;
        if (accessibilityHelperService == null) {
            return null;
        }
        return findNodeByText(accessibilityHelperService.getRootInActiveWindow(), str);
    }

    public static List<AccessibilityNodeInfo> findVisibleNodes(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        if(accessibilityNodeInfo == null){
            return null;
        }
        List<AccessibilityNodeInfo> nodelist = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(str);
        if (nodelist == null || nodelist.isEmpty()) {
            return null;
        }
        ArrayList<AccessibilityNodeInfo> arrayList = new ArrayList<>();
        for (AccessibilityNodeInfo node : nodelist) {
            if (node != null && node.isVisibleToUser()) {
                arrayList.add(node);
            }
        }
        return arrayList;
    }
}