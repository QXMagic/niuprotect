package com.niu.protect.accessibility.auto.device.info.oppo;

import android.content.Context;
import com.google.gson.Gson;
import com.niu.protect.accessibility.auto.app.appinfo.AppInfo;
import com.niu.protect.accessibility.auto.bean.MineSettingInfoModel;
import com.niu.protect.accessibility.auto.bean.PageInfoModel;
import com.niu.protect.accessibility.auto.device.BaseCreatePageInfo;
import com.niu.protect.accessibility.auto.device.OppoDeviceInfo;
import com.niu.protect.tools.ILog;
import java.util.ArrayList;
import java.util.List;
public class OppoColorsInfo_All extends BaseCreatePageInfo {
    @Override
    protected BaseCreatePageInfo createInfo() {
        return new OppoColorsInfo_All();
    }

    @Override
    public List<PageInfoModel> createPage(Context context) {
        List<PageInfoModel> childinfoModels = new ArrayList<>();
        PageInfoModel pInfoScreen = createMineSettingPage("自动授权中心", "负一屏", 1, 1);
        childinfoModels.add(pInfoScreen);
        PageInfoModel pInfoBreeno = createParentPage("设置", OppoDeviceInfo.StepOneBreeno.BREENO, 1, 2);
        childinfoModels.add(pInfoBreeno);
        PageInfoModel pInfoBreenoDetail = createLastPage(OppoDeviceInfo.StepOneBreeno.BREENO_Click_Text, 1, 3, 2, "负一屏", 1);
        pInfoBreenoDetail.addCheckBox(createCheckBoxStep(OppoDeviceInfo.StepOneBreeno.BREENO_Click_Text, 0, true));
        childinfoModels.add(pInfoBreenoDetail);
        PageInfoModel pInfoNotification = createMineSettingPage("自动授权中心", "通知权限设置", 2, 4);
        childinfoModels.add(pInfoNotification);
        PageInfoModel pInfoNotificationSet = createCenterPage("应用信息", "通知管理", 2, 5);
        childinfoModels.add(pInfoNotificationSet);
        PageInfoModel pInfoNotificationDetail = createLastPage(AppInfo.getAppName(context), 2, 6, 1, "通知权限设置", 2);
        pInfoNotificationDetail.addCheckBox(createCheckBoxStep("允许通知", 1, true));
        childinfoModels.add(pInfoNotificationDetail);
        PageInfoModel pInfoPowerSetting = createCenterPage("应用信息", "耗电", 2, 7);
        childinfoModels.add(pInfoPowerSetting);
        PageInfoModel pInfoPowerDetail = createLastPage(Constant.APP_NAME, 2, 8, 1, "耗电管理", 2);
        pInfoPowerDetail.addCheckBox(createCheckBoxStep("允许后台运行", 1, true));
        childinfoModels.add(pInfoPowerDetail);
        PageInfoModel pInfoPremFloat = createLastPage("应用信息", 2, 9, 1, "允许悬浮窗", 2);
        pInfoPremFloat.addCheckBox(createCheckBoxStep("允许显示悬浮窗", 1, true));
        childinfoModels.add(pInfoPremFloat);
        PageInfoModel pInfoAutoStart = createMineSettingPage("自动授权中心", "允许自启动", 3, 10);
        childinfoModels.add(pInfoAutoStart);
        PageInfoModel pInfoAutoStartManager = createCenterPage("设置", "应用管理", 3, 11);
        childinfoModels.add(pInfoAutoStartManager);
        PageInfoModel pInfoAutoStartManagerLast = createCenterPage("应用管理", "自启动", 3, 12);
        childinfoModels.add(pInfoAutoStartManagerLast);
        PageInfoModel pInfoAutoStartDetail = createLastPage("自启动管理", 3, 13, 3, "允许自启动", 1);
        pInfoAutoStartDetail.addCheckBox(createCheckBoxStep(Constant.APP_NAME, 1, true));
        childinfoModels.add(pInfoAutoStartDetail);
        PageInfoModel pInfoDeviceUse = createMineSettingPage("自动授权中心", "应用使用记录", 4, 14);
        childinfoModels.add(pInfoDeviceUse);
        PageInfoModel pInfoCenterAPPUse = createCenterPage("使用", AppInfo.getAppName(context), 4, 15);
        childinfoModels.add(pInfoCenterAPPUse);
        PageInfoModel pInfoCenterAPPUseDetail = createLastPage("允许查看使用情况", 4, 16, 2, "应用使用记录", 3);
        pInfoCenterAPPUseDetail.addCheckBox(createCheckBoxStep("允许查看使用情况", 1, true));
        childinfoModels.add(pInfoCenterAPPUseDetail);
        String infos = new Gson().toJson(childinfoModels);
        ILog.d("VivoBrandDeviceInfo", infos);
        return childinfoModels;
    }

    public List<MineSettingInfoModel> getSettingInfo() {
        return this.settingInfoModels;
    }

    public int getStep() {
        return this.totalSteps;
    }
}
