package com.niu.protect.accessibility.auto.service;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.niu.protect.BabyApplication;
import com.niu.protect.Constant;
import com.niu.protect.accessibility.auto.app.OpenSettingApp;
import com.niu.protect.accessibility.auto.bean.CheckBoxModel;
import com.niu.protect.accessibility.auto.bean.PageInfoModel;
import com.niu.protect.accessibility.auto.device.OppoDeviceInfo;
import com.niu.protect.accessibility.auto.setting.BaseAutoSettingTools;
import com.niu.protect.manager.AutoSettingManager;
import com.niu.protect.manager.SharedPreManager;
import com.niu.protect.tools.ILog;

import java.util.List;
public abstract class BaseAccessibility extends BaseAutoSettingTools {
    private static final int START_ACCESS = 2;
    protected static final String TAG = BaseAccessibility.class.getName();
    private static final String allowBackgroundDialog_TAG = "完全允许";
    private static final int waitTime = 300;
    AccessibilityEvent mEvent;
    protected int step = 1;
    protected int totalStep = 0;
    boolean stop = false;
    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 2) {
                BaseAccessibility baseAccessibility = BaseAccessibility.this;
                baseAccessibility.openAccessibilityEvent(baseAccessibility.mEvent);
            }
        }
    };
    private List<PageInfoModel> mVivoInfos = BabyApplication.getInstance().autoSettingSteps;
    boolean findCheckBox = false;

    protected void initAccessibilityService() {
        AccessibilityServiceInfo info = getServiceInfo();
        info.eventTypes = -1;
        info.feedbackType = -1;
        info.notificationTimeout = 100L;
        info.flags = 91;
        setServiceInfo(info);
    }

    public void recycleCheckBoxClick(AccessibilityNodeInfo info, String text, int defaultCheckBoxValue) {
        if (info.getChildCount() == 0) {
            String str = TAG;
            ILog.d(str, "child--info.getClassName()----" + info.getClassName().toString());
            String str2 = TAG;
            ILog.d(str2, info.getClassName().toString().equals("android.widget.Switch") + "recycleCheckBoxClick check ");
            if (info.getClassName().toString().equals("android.widget.Switch")) {
                String str3 = TAG;
                ILog.d(str3, "child--info.getClassName()----" + info.getClassName().toString() + "text==" + ((Object) this.remenberUpNodeInfo.getText()));
                if (this.remenberUpNodeInfo.getText() != null && this.remenberUpNodeInfo.getText().toString().contains(text) && Build.VERSION.SDK_INT >= 18) {
                    String str4 = TAG;
                    ILog.d(str4, "Switch--" + info.isChecked() + "_--(defaultValue=)---" + defaultCheckBoxValue);
                    if ((!info.isChecked() && defaultCheckBoxValue == 1) || (info.isChecked() && defaultCheckBoxValue == 0)) {
                        if (!info.getParent().performAction(16)) {
                            boolean checkBoxClick = info.performAction(16);
                            if (!checkBoxClick) {
                                Rect rect = new Rect();
                                info.getBoundsInScreen(rect);
                                boolean clickSuccess = click(rect.centerX(), rect.centerY());
                                String str5 = TAG;
                                ILog.d(str5, "Switch--" + clickSuccess);
                            }
                        }
                        checkClickStatus(text);
                        return;
                    }
                    checkClickStatus(text);
                }
            } else if (info.getClassName().equals("android.widget.TextView")) {
                this.remenberUpNodeInfo = info;
            } else if (info.getClassName().equals("android.widget.CheckBox")) {
                if (this.remenberUpNodeInfo.getText().toString().contains(text)) {
                    if (!info.getParent().performAction(16)) {
                        boolean checkBoxClick2 = info.performAction(16);
                        if (!checkBoxClick2) {
                            Rect rect2 = new Rect();
                            info.getBoundsInScreen(rect2);
                            click(rect2.centerX(), rect2.centerY());
                        }
                    }
                    checkClickStatus(text);
                }
            } else if (info.getClassName().equals("android.widget.RadioButton") && this.remenberUpNodeInfo.getText().toString().contains(text)) {
                boolean click = info.getParent().performAction(16);
                if (!click) {
                    Rect rect3 = new Rect();
                    info.getBoundsInScreen(rect3);
                    click(rect3.centerX(), rect3.centerY());
                }
                String str6 = TAG;
                ILog.d(str6, click + text + "---点击-CheckBox--");
                checkClickStatus(text);
            }
        } else if (info.getChildCount() >= 2) {
            for (int i = 0; i < info.getChildCount(); i++) {
                if (info.getChild(i) != null) {
                    recycleCheckBoxClick(info.getChild(i), text, defaultCheckBoxValue);
                }
            }
        } else {
            recycleCheckBoxClick(info.getChild(0), text, defaultCheckBoxValue);
        }
    }

    public void findCheckBoxByParentText(String text, int i, int defaultCheckBoxValue) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(text);
        this.findCheckBox = false;
        if (list.size() > 0) {
            AccessibilityNodeInfo targetNode = list.get(i);
            for (int times = 1; !this.findCheckBox && times < 4; times++) {
                findCheckBox(targetNode, text, times, defaultCheckBoxValue);
                if (this.findCheckBox) {
                    break;
                }
            }
            if (!this.findCheckBox) {
                recycleCheckBoxClick(nodeInfo, text, defaultCheckBoxValue);
            }
        }
    }

    private void findCheckBox(AccessibilityNodeInfo targetNode, String text, int times, int defaultCheckBoxValue) {
        AccessibilityNodeInfo info;
        String str = TAG;
        ILog.d(str, times + "----times--");
        if (times == 1) {
            info = targetNode.getParent();
        } else if (times == 2) {
            info = targetNode.getParent().getParent();
        } else {
            info = targetNode.getParent().getParent().getParent();
        }
        int child = info.getChildCount();
        if (child >= 1) {
            for (int k = 0; k < child && !this.findCheckBox; k++) {
                AccessibilityNodeInfo infoChild = info.getChild(k);
                recycleFindCheckBox(infoChild, text, defaultCheckBoxValue);
            }
        }
    }

    public void recycleFindCheckBox(AccessibilityNodeInfo info, String text, int defaultCheckBoxValue) {
        if (info.getChildCount() == 0) {
            String str = TAG;
            ILog.d(str, "recycleFindCheckBox--info.getClassName()----" + info.getClassName().toString());
            if (info.getClassName().toString().equals("android.widget.Switch") || info.getClassName().toString().equals("android.widget.CheckBox") || info.getClassName().toString().equals("android.widget.RadioButton")) {
                ILog.d(TAG, "find------------------------------");
                this.findCheckBox = true;
                String str2 = TAG;
                ILog.d(str2, "child--info.getClassName()----" + info.getClassName().toString());
                if (info.getClassName().equals("android.widget.Switch")) {
                    if (Build.VERSION.SDK_INT >= 18) {
                        String str3 = TAG;
                        ILog.d(str3, "Switch--" + info.isChecked() + "_--(defaultValue=)---" + defaultCheckBoxValue);
                        if ((!info.isChecked() && defaultCheckBoxValue == 1) || (info.isChecked() && defaultCheckBoxValue == 0)) {
                            if (!info.performAction(16)) {
                                boolean click = info.getParent().performAction(16);
                                String str4 = TAG;
                                ILog.d(str4, click + text + "---Switch-Switch--");
                                if (!click) {
                                    Rect rect = new Rect();
                                    info.getBoundsInScreen(rect);
                                    boolean clickByXY = click(rect.centerX(), rect.centerY());
                                    String str5 = TAG;
                                    ILog.d(str5, "clickByXY-------" + clickByXY);
                                }
                            }
                            checkClickStatus(text);
                            return;
                        }
                        String str6 = TAG;
                        ILog.d(str6, "id -是否选中-isChecked--" + info.isChecked());
                        checkClickStatus(text);
                    }
                } else if (info.getClassName().equals("android.widget.CheckBox")) {
                    boolean click2 = info.getParent().performAction(16);
                    String str7 = TAG;
                    ILog.d(str7, click2 + text + "---点击-CheckBox--");
                    if (!click2) {
                        boolean checkBoxClick = info.performAction(16);
                        String str8 = TAG;
                        ILog.d(str8, checkBoxClick + text + "---checkBoxClick-CheckBox--");
                        if (!checkBoxClick) {
                            Rect rect2 = new Rect();
                            info.getBoundsInScreen(rect2);
                            click(rect2.centerX(), rect2.centerY());
                        }
                    }
                    checkClickStatus(text);
                } else if (info.getClassName().equals("android.widget.RadioButton")) {
                    boolean click3 = info.getParent().performAction(16);
                    if (!click3) {
                        Rect rect3 = new Rect();
                        info.getBoundsInScreen(rect3);
                        click(rect3.centerX(), rect3.centerY());
                    }
                    String str9 = TAG;
                    ILog.d(str9, click3 + text + "---点击-CheckBox--");
                    checkClickStatus(text);
                }
            }
        } else if (info.getChildCount() >= 2) {
            for (int i = 0; i < info.getChildCount(); i++) {
                String str10 = TAG;
                ILog.d(str10, "getChildCount--" + ((Object) info.getChild(i).getClassName()));
                if (info.getChild(i) != null) {
                    recycleFindCheckBox(info.getChild(i), text, defaultCheckBoxValue);
                }
            }
        } else {
            recycleFindCheckBox(info.getChild(0), text, defaultCheckBoxValue);
        }
    }

    protected PageInfoModel getStepInfo(List<PageInfoModel> pageInfos, int step) {
        if (pageInfos == null || pageInfos.size() == 0) {
            return null;
        }
        for (int i = 0; i < pageInfos.size(); i++) {
            if (pageInfos.get(i).getStep() == step) {
                return pageInfos.get(i);
            }
        }
        return null;
    }

    public void openAccessibilityEvent(AccessibilityEvent event) {
        if (event == null) {
            return;
        }
        int eventType = event.getEventType();
        List<PageInfoModel> list = this.mVivoInfos;
        if (list == null || list.size() == 0) {
            return;
        }
        List<PageInfoModel> list2 = this.mVivoInfos;
        this.totalStep = list2.get(list2.size() - 1).getStep();
        ILog.d(TAG, "eventType==" + eventType + "totalStep :--" + this.stop);
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("eventType= share=");
        sb.append(SharedPreManager.getAutoSettingFinish());
        ILog.d(str, sb.toString());
        if (eventType == 32 && !this.stop && AutoSettingManager.INSTANCE.isNeedAutoSetting()) {
            PageInfoModel pageInfoModel = getStepInfo(this.mVivoInfos, this.step);
            ILog.d(TAG, "step" + this.step);
            if (pageInfoModel != null && pageInfoModel.getPageTagInfo() != null && findText(pageInfoModel.getPageTagInfo(), 0) && event.getPackageName() != null && !event.getPackageName().toString().contains("launcher")) {
                normalModeSleep(300L);
                ILog.d(TAG, "getParentPage" + pageInfoModel.getParentPage() + pageInfoModel.getPageTagInfo());
                if (pageInfoModel.getParentPage() == 1 || pageInfoModel.getParentPage() == 2) {
                    int checkTimes = 0;
                    int reTryTimes = 20;
                    if (this.step == 2) {
                        reTryTimes = 3;
                    }
                    normalModeSleep(300L);
                    normalModeSleep(200L);
                    while (true) {
                        if (checkTimes >= reTryTimes) {
                            break;
                        }
                        boolean clickSuccess = findTextClickByParent(pageInfoModel.getNextPageClick(), 0);
                        if (clickSuccess) {
                            ILog.d(TAG, "parent click suceess " + this.step + "--" + pageInfoModel.getNextPageClick());
                            break;
                        }
                        checkTimes++;
                        normalModeSleep(500L);
                        swip();
                        normalModeSleep(500L);
                        ILog.d(TAG, "parent click fail " + this.step + "--" + pageInfoModel.getNextPageClick());
                    }
                    this.step++;
                    return;
                }
                ILog.d(TAG, "步骤" + this.step);
                int backStep = pageInfoModel.getBackTimes();
                ILog.d(TAG, "backStep" + backStep);
                if (pageInfoModel.getCheckBoxModels() != null && pageInfoModel.getCheckBoxModels().size() > 0) {
                    normalModeSleep(300L);
                    int childItemSize = pageInfoModel.getCheckBoxModels().size();
                    for (int j = 0; j < childItemSize; j++) {
                        CheckBoxModel childPinfo = pageInfoModel.getCheckBoxModels().get(j);
                        ILog.d(TAG, "childPinfo" + childPinfo.getClickCheckBoxItemText());
                        checkBoxAction(childPinfo, childItemSize);
                    }
                    if (!TextUtils.isEmpty(pageInfoModel.getNextPageClick())) {
                        findTextAndClick(pageInfoModel.getNextPageClick(), 0);
                    }
                }
                this.step++;
                normalModeSleep(300L);
                if (backStep >= 3) {
                    BackClick();
                    normalModeSleep(300L);
                    OpenSettingApp.backMineApp(this);
                    normalModeSleep(300L);
                } else {
                    for (int k = 0; k < backStep; k++) {
                        BackClick();
                        normalModeSleep(500L);
                    }
                }
                int k2 = this.step;
                if (k2 > this.totalStep) {
                    this.stop = true ^ this.stop;
                    return;
                }
                return;
            }
            ILog.d("----", "find 允许----------------" + event.getText());
            if (event.getText().toString().contains(allowBackgroundDialog_TAG)) {
                ILog.d("----", "find click----------------");
                findTextAndClick("允许", 1);
                normalModeSleep(100L);
                BackClick();
                normalModeSleep(200L);
            } else if (event.getText().toString().equals(Constant.APP_NAME)) {
                normalModeSleep(200L);
                findTextAndClick("允许", 1);
                BackClick();
                normalModeSleep(200L);
            }
        }
    }

    private void checkBoxAction(CheckBoxModel pageInfoModel, int size) {
        String checkBoxText = pageInfoModel.getClickCheckBoxItemText();
        int totalSwip = 15;
        if (checkBoxText.equals(OppoDeviceInfo.StepOneBreeno.BREENO_Click_Text)) {
            totalSwip = 3;
        }
        if (size == 1) {
            for (int times = 0; !findText(checkBoxText, 0) && times < totalSwip; times++) {
                swip();
                normalModeSleep(300L);
            }
        } else if (size == 2) {
            normalModeSleep(200L);
            if (!findText(checkBoxText, 0)) {
                swip();
                normalModeSleep(600L);
            }
        } else {
            normalModeSleep(200L);
        }
        if (findText(checkBoxText, 0)) {
            String str = TAG;
            ILog.d(str, "find --" + checkBoxText + "getCheckBoxDefaultValue==" + pageInfoModel.getCheckBoxDefaultValue());
            findCheckBoxByParentText(checkBoxText, 0, pageInfoModel.getCheckBoxDefaultValue());
            normalModeSleep(200L);
        }
    }

    private void checkClickStatus(String text) {
        if (this.mVivoInfos.get(this.step - 1).getParentPage() == 0) {
            List<CheckBoxModel> checkBoxModels = this.mVivoInfos.get(this.step - 1).getCheckBoxModels();
            int size = checkBoxModels.size();
            for (int k = 0; k < size; k++) {
                CheckBoxModel checkBoxModel = checkBoxModels.get(k);
                if (checkBoxModel.getClickCheckBoxItemText().equals(text)) {
                    checkBoxModel.setClickedStatus(1);
                    BabyApplication.getInstance().autoSettingSteps=this.mVivoInfos;
                    return;
                }
            }
        }
    }

    @Override
    public void onInterrupt() {
    }

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
    }
}
