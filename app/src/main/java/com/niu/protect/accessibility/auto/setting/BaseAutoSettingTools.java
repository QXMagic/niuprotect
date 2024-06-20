package com.niu.protect.accessibility.auto.setting;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.Context;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;
import com.google.gson.Gson;
import com.niu.protect.BabyApplication;
import com.niu.protect.accessibility.auto.device.OppoDeviceInfo;
import com.niu.protect.tools.ILog;
import java.util.ArrayList;
import java.util.List;
public abstract class BaseAutoSettingTools extends AccessibilityService {
    protected static final String TAG = BaseAutoSettingTools.class.getName();
    protected AccessibilityNodeInfo remenberUpNodeInfo;
    ArrayList<AccessibilityNodeInfo> roots = new ArrayList<>();

    protected void getRootInfo(AccessibilityNodeInfo root) {
        if (root == null) {
            return;
        }
        if (root.getChildCount() == 0) {
            if (!root.getPackageName().equals("com.android.systemui") && root != null) {
                String str = TAG;
                Log.i(str, "控件名称:" + ((Object) root.getClassName()));
                String str2 = TAG;
                Log.i(str2, "控件中的值：" + ((Object) root.getText()));
                String str3 = TAG;
                Log.i(str3, "控件的ID:" + root.getViewIdResourceName());
                String str4 = TAG;
                Log.i(str4, "点击是否出现弹窗:" + root.getViewIdResourceName());
                this.roots.add(root);
            }
        } else if (Build.VERSION.SDK_INT >= 21) {
            for (int i = 0; i < root.getChildCount(); i++) {
                if (root.getChild(i) != null) {
                    getRootInfo(root.getChild(i));
                }
            }
        }
    }

    protected void showRootInfo() {
        String info = new Gson().toJson(this.roots);
        ILog.d(TAG, info);
    }

    public static boolean checkPage(AccessibilityEvent event, String page) {
        int eventType = event.getEventType();
        return event.getClassName().equals(page) && eventType == 32;
    }

    public void normalModeSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isFindText(String text, AccessibilityNodeInfo nodeInfo) {
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(text);
        if (list.size() > 0) {
            return true;
        }
        return false;
    }

    public void findTextAndClick(String text, int i, AccessibilityNodeInfo nodeInfo) {
        AccessibilityNodeInfo node = null;
        if (0 == 0) {
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(text);
            if (list.size() > 0) {
                node = list.get(i);
            }
        }
        AccessibilityNodeInfo targetNode = node;
        if (targetNode != null) {
            performClick(targetNode);
        }
    }

    public boolean performClick(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return false;
        }
        if (nodeInfo.isClickable()) {
            return nodeInfo.performAction(16);
        }
        return performClick(nodeInfo.getParent());
    }

    public void findAndLongClick(String id, int i) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        AccessibilityNodeInfo targetNode = null;
        if (0 == 0) {
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId(id);
            if (list.size() > 0) {
                targetNode = list.get(i);
            }
        }
        if (targetNode != null) {
            AccessibilityNodeInfo n = targetNode;
            performLongClick(n);
        }
    }

    public void performLongClick(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return;
        }
        if (nodeInfo.isClickable()) {
            nodeInfo.performAction(32);
        } else {
            performLongClick(nodeInfo.getParent());
        }
    }

    public void openNext(Context context, String str) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo == null) {
            Toast.makeText(context, "rootWindow为空", Toast.LENGTH_SHORT).show();
            return;
        }
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(str);
        if (list != null && list.size() > 0) {
            list.get(list.size() - 1).performAction(16);
            list.get(list.size() - 1).getParent().performAction(16);
        }
    }

    public Rect isFindWXBouns(String id, int i, String parentid) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId(parentid);
        if (list.size() > 0) {
            AccessibilityNodeInfo targetNode = list.get(0);
            List<AccessibilityNodeInfo> list1 = targetNode.findAccessibilityNodeInfosByViewId(id);
            if (list1.size() > 0) {
                Rect nodeRect = new Rect();
                list1.get(i).getBoundsInScreen(nodeRect);
                return nodeRect;
            }
            return null;
        }
        return null;
    }

    public void performBackClick() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        performGlobalAction(2);
    }

    public boolean BackClick() {
        try {
            Thread.sleep(200L);
            ILog.d("i", "--------BackClick-------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return performGlobalAction(1);
    }

    public void SWIPE_LEFT_AND_RIGHTClick() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        performGlobalAction(4);
    }

    public void SWIPE_DOWN_AND_UPClick() {
        try {
            Thread.sleep(300L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        performGlobalAction(1);
    }

    public boolean findText(String text, int i) {
        int findTimes = 0;
        while (findTimes < 3) {
            AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
            if (nodeInfo != null) {
                List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(text);
                if (list.size() > 0) {
                    return true;
                }
            }
            findTimes++;
            normalModeSleep(200L);
        }
        return false;
    }

    public void findTextAndClick(String text, int i) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        AccessibilityNodeInfo targetNode = null;
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(text);
        if (list.size() > 0) {
            AccessibilityNodeInfo targetNode2 = list.get(i);
            targetNode = targetNode2;
        }
        ILog.d("findTextAndClick", "---" + text + "----------" + i);
        if (targetNode != null) {
            AccessibilityNodeInfo n = targetNode;
            performClick(n);
        }
    }

    public boolean findTextAndClickParent(String text, int i) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(text);
            if (list.size() > 0) {
                AccessibilityNodeInfo node = list.get(i);
                return performClick(node.getParent());
            }
            return false;
        }
        return false;
    }

    public boolean findTextClickByParent(String text, int i) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(text);
            ILog.d("findTextClickByParent size", list.size() + "--");
            if (list.size() > 0) {
                AccessibilityNodeInfo node = list.get(i);
                if (text.toLowerCase().contains("breeno")) {
                    if (!node.getClassName().equals("android.widget.TextView") || text.equals("breeno_voice")) {
                        return false;
                    }
                    return performClick(node);
                }
                boolean isClick = performClick(node);
                if (!isClick) {
                    Rect rect = new Rect();
                    list.get(i).getBoundsInScreen(rect);
                    return click(rect.centerX(), rect.centerY());
                }
                return isClick;
            }
        }
        return false;
    }

    public boolean findClickById(String id, int i) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId(id);
            if (list.size() > 0) {
                AccessibilityNodeInfo node = list.get(i);
                return node.performAction(16);
            }
            return false;
        }
        return false;
    }

    public boolean findById(String id, int i) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId(id);
            if (list.size() > 0) {
                list.get(i);
                return true;
            }
            return false;
        }
        return false;
    }

    public void swip() {
        Path path = new Path();
        int start = BabyApplication.height - 100;
        path.moveTo(250.0f, start);
        path.lineTo(250.0f, 100);
        if (Build.VERSION.SDK_INT >= 24) {
            GestureDescription.Builder builder = new GestureDescription.Builder();
            GestureDescription gestureDescription = builder.addStroke(new GestureDescription.StrokeDescription(path, 100L, 500L)).build();
            dispatchGesture(gestureDescription, new AccessibilityService.GestureResultCallback() {
                @Override
                public void onCompleted(GestureDescription gestureDescription2) {
                    super.onCompleted(gestureDescription2);
                    ILog.d(BaseAutoSettingTools.TAG, "滑动结束");
                }

                @Override
                public void onCancelled(GestureDescription gestureDescription2) {
                    super.onCancelled(gestureDescription2);
                }
            }, new Handler(Looper.getMainLooper()));
        }
    }

    public boolean click(float x, float y) {
        Path path = new Path();
        path.moveTo(x, y);
        if (Build.VERSION.SDK_INT >= 24) {
            GestureDescription.Builder builder = new GestureDescription.Builder();
            GestureDescription gestureDescription = builder.addStroke(new GestureDescription.StrokeDescription(path, 0L, 100L)).build();
            boolean dispatch = dispatchGesture(gestureDescription, new AccessibilityService.GestureResultCallback() {
                @Override
                public void onCompleted(GestureDescription gestureDescription2) {
                    super.onCompleted(gestureDescription2);
                    ILog.d(BaseAutoSettingTools.TAG, "滑动结束");
                }

                @Override
                public void onCancelled(GestureDescription gestureDescription2) {
                    super.onCancelled(gestureDescription2);
                }
            }, new Handler(Looper.getMainLooper()));
            return dispatch;
        }
        return false;
    }

    public static boolean scroll2PositionClick(AccessibilityService service, String text, String listId, int num, int stepFlagIndex) {
        AccessibilityNodeInfo rootInActiveWindow;
        if (Build.VERSION.SDK_INT >= 18 && (rootInActiveWindow = service.getRootInActiveWindow()) != null) {
            List<AccessibilityNodeInfo> item = rootInActiveWindow.findAccessibilityNodeInfosByText(text);
            List<AccessibilityNodeInfo> list = rootInActiveWindow.findAccessibilityNodeInfosByViewId(listId);
            if (item == null || item.size() == 0) {
                String str = TAG;
                Log.d(str, "不存在 " + text);
                String str2 = TAG;
                Log.d(str2, "不存在 " + list.size());
                if (list != null && list.size() > 0) {
                    try {
                        Thread.sleep(200L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    list.get(0).performAction(4096);
                    String str3 = TAG;
                    Log.d(str3, "---- [ " + text + " ] 滚动查找中 ----");
                }
            } else {
                String str4 = TAG;
                Log.d(str4, "存在 " + text);
                AccessibilityNodeInfo clickableItem = item.get(0);
                if (clickableItem.isEnabled() && clickableItem.isClickable()) {
                    for (int i = 0; i < num; i++) {
                        boolean success = clickableItem.performAction(16);
                        String str5 = TAG;
                        Log.d(str5, "点击: " + text);
                        if (success) {
                            return true;
                        }
                    }
                } else {
                    AccessibilityNodeInfo parent = clickableItem.getParent();
                    if (parent.isEnabled() && parent.isClickable() && 0 < num) {
                        parent.performAction(16);
                        String str6 = TAG;
                        Log.d(str6, "点击parent: " + text);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean findRecycleViewScrollClick(AccessibilityService service, String text, String listId, int num, int stepFlagIndex) {
        AccessibilityNodeInfo rootInActiveWindow;
        AccessibilityNodeInfo info;
        if (Build.VERSION.SDK_INT >= 18 && (rootInActiveWindow = service.getRootInActiveWindow()) != null) {
            List<AccessibilityNodeInfo> item = rootInActiveWindow.findAccessibilityNodeInfosByText(text);
            List<AccessibilityNodeInfo> list = rootInActiveWindow.findAccessibilityNodeInfosByViewId(listId);
            if (item == null || item.size() == 0) {
                Log.d(TAG, "不存在 " + text);
                Log.d(TAG, "不存在 " + list.size());
                if (list != null && list.size() > 0) {
                    try {
                        Thread.sleep(200L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    list.get(0).performAction(4096);
                    Log.d(TAG, "---- [ " + text + " ] 滚动查找中 ----");
                } else {
                    int count = rootInActiveWindow.getChildCount();
                    int i = 0;
                    while (true) {
                        if (i >= count) {
                            info = null;
                            break;
                        }
                        AccessibilityNodeInfo child = rootInActiveWindow.getChild(i);
                        if (!child.getClassName().equals("androidx.recyclerview.widget.RecyclerView")) {
                            i++;
                        } else {
                            info = child;
                            break;
                        }
                    }
                    if (info != null) {
                        try {
                            Thread.sleep(200L);
                        } catch (InterruptedException e2) {
                            e2.printStackTrace();
                        }
                        info.performAction(4096);
                    }
                }
            } else {
                Log.d(TAG, "存在 " + text);
                AccessibilityNodeInfo clickableItem = item.get(0);
                if (clickableItem.isEnabled() && clickableItem.isClickable()) {
                    for (int i2 = 0; i2 < num; i2++) {
                        boolean success = clickableItem.performAction(16);
                        Log.d(TAG, "点击: " + text);
                        if (success) {
                            return true;
                        }
                    }
                } else {
                    AccessibilityNodeInfo parent = clickableItem.getParent();
                    if (parent.isEnabled() && parent.isClickable() && 0 < num) {
                        parent.performAction(16);
                        Log.d(TAG, "点击parent: " + text);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void recycleCheckBox(AccessibilityNodeInfo info, String text, int step) {
        if (info.getChildCount() == 0) {
            if (info.getClassName().equals("android.widget.Switch")) {
                if (this.remenberUpNodeInfo.getText().equals(text)) {
                    String str = TAG;
                    ILog.d(str, "checkBox " + text + "----" + info.getText().toString());
                    if (step == 1) {
                        boolean isOpen = info.getText().toString().equals("开启");
                        OppoDeviceInfo.StepOneBreeno.STATUS_CLICKED = isOpen;
                    } else if (step == 2) {
                        boolean isOpen2 = info.getText().toString().equals("开启");
                        OppoDeviceInfo.StepTwoAutoStart.STATUS_CLICKED = isOpen2;
                    } else if (step == 3) {
                        boolean isOpen3 = info.getText().toString().equals("开启");
                        OppoDeviceInfo.StepThreeBackRunning.STATUS_CLICKED = isOpen3;
                    } else if (step == 4) {
                        boolean isOpen4 = info.getText().toString().equals("开启");
                        OppoDeviceInfo.StepFourAppUse.STATUS_CLICKED = isOpen4;
                    } else if (step == 5) {
                        boolean isOpen5 = info.getText().toString().equals("开启");
                        OppoDeviceInfo.StepFiveNOtification.STATUS_CLICKED = isOpen5;
                    } else if (step == 6) {
                        boolean isOpen6 = info.getText().toString().equals("开启");
                        OppoDeviceInfo.StepSixOverlayWindows.STATUS_CLICKED = isOpen6;
                    }
                    String str2 = TAG;
                    ILog.d(str2, "自动启动" + info.getText().toString().equals("开启"));
                    return;
                }
                return;
            } else if (info.getClassName().equals("android.widget.TextView")) {
                this.remenberUpNodeInfo = info;
                return;
            } else {
                return;
            }
        }
        String str3 = TAG;
        ILog.d(str3, "checkBox " + text + "----" + info.getChildCount());
        if (info.getChildCount() >= 2) {
            for (int i = 0; i < info.getChildCount(); i++) {
                if (info.getChild(i) != null) {
                    recycleCheckBox(info.getChild(i), text, step);
                }
            }
            return;
        }
        recycleCheckBox(info.getChild(0), text, step);
    }

    public boolean clickButton(String id) {
        for (int times = 0; times < 3; times++) {
            boolean clickSuccess = findClickById(id, 0);
            if (clickSuccess) {
                return true;
            }
            normalModeSleep(500L);
        }
        return false;
    }

    public void recycleCheckBoxClick(AccessibilityNodeInfo info, String text) {
        if (info.getChildCount() == 0) {
            String str = TAG;
            ILog.d(str, "---控件---" + ((Object) info.getText()));
        } else if (info.getChildCount() > 0) {
            for (int i = 0; i < info.getChildCount(); i++) {
                if (info.getChild(i) != null) {
                    recycleCheckBoxClick(info.getChild(i), text);
                }
            }
        } else {
            recycleCheckBoxClick(info.getChild(0), text);
        }
    }

    protected boolean checkBackTxt(String key, AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return false;
        }
        List<AccessibilityNodeInfo> list2 = nodeInfo.findAccessibilityNodeInfosByText(key);
        if (list2.size() <= 0) {
            return false;
        }
        return true;
    }

    protected boolean checkBackId(String key, AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return false;
        }
        List<AccessibilityNodeInfo> list2 = nodeInfo.findAccessibilityNodeInfosByViewId(key);
        if (list2.size() <= 0) {
            return false;
        }
        return true;
    }
}
