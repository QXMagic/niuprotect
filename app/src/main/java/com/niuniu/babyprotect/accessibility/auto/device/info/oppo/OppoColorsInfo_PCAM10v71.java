package com.niuniu.babyprotect.accessibility.auto.device.info.oppo;

import android.content.Context;
import com.google.gson.Gson;
import com.niuniu.babyprotect.accessibility.auto.app.appinfo.AppInfo;
import com.niuniu.babyprotect.accessibility.auto.bean.MineSettingInfoModel;
import com.niuniu.babyprotect.accessibility.auto.bean.PageInfoModel;
import com.niuniu.babyprotect.accessibility.auto.device.BaseCreatePageInfo;
import com.niuniu.babyprotect.tools.ILog;
import java.util.ArrayList;
import java.util.List;
public class OppoColorsInfo_PCAM10v71 extends BaseCreatePageInfo {
    @Override
    protected BaseCreatePageInfo createInfo() {
        return new OppoColorsInfo_PCAM10v71();
    }

    @Override
    public List<PageInfoModel> createPage(Context context) {
        List<PageInfoModel> childinfoModels = new ArrayList<>();
        PageInfoModel pInfoMainAutoStart = createMineSettingPage("自动授权中心", "耗电管理", 1, 1);
        childinfoModels.add(pInfoMainAutoStart);
        PageInfoModel pInfoPowerSetting = createCenterPage("应用信息", "耗电管理", 1, 2);
        childinfoModels.add(pInfoPowerSetting);
        PageInfoModel pInfoPowerDetail = createLastPage(AppInfo.getAppName(context), 1, 3, 1, "耗电管理", 2);
        pInfoPowerDetail.addCheckBox(createCheckBoxStep("开启后会导致功耗", 1, true));
        childinfoModels.add(pInfoPowerDetail);
        PageInfoModel pInfoAppSetting = createParentPage("应用信息", "通知管理", 1, 4);
        childinfoModels.add(pInfoAppSetting);
        PageInfoModel pInfoPremCenter = createLastPage(AppInfo.getAppName(context), 1, 5, 1, "允许通知", 2);
        pInfoPremCenter.addCheckBox(createCheckBoxStep("允许通知", 1, true));
        childinfoModels.add(pInfoPremCenter);
        PageInfoModel pInfoAppCheckBoxSetting = createLastPage("应用信息", 1, 6, 1, "允许悬浮窗", 2);
        pInfoAppCheckBoxSetting.addCheckBox(createCheckBoxStep("允许显示悬浮窗", 1, true));
        childinfoModels.add(pInfoAppCheckBoxSetting);
        PageInfoModel pInfoDeviceUse = createMineSettingPage("自动授权中心", "应用使用记录", 2, 7);
        childinfoModels.add(pInfoDeviceUse);
        PageInfoModel pInfoCenterAPPUse = createCenterPage("读取应用使用情况", AppInfo.getAppName(context), 2, 8);
        childinfoModels.add(pInfoCenterAPPUse);
        PageInfoModel pInfoCenterAPPUseDetail = createLastPage("使用情况访问权限", 2, 9, 2, "应用使用记录", 3);
        pInfoCenterAPPUseDetail.addCheckBox(createCheckBoxStep("允许查看使用情况", 1, true));
        childinfoModels.add(pInfoCenterAPPUseDetail);
        PageInfoModel pInfoAutoStart = createMineSettingPage("自动授权中心", "自启动管理", 3, 10);
        childinfoModels.add(pInfoAutoStart);
        PageInfoModel pInfoSettingAppManager = createParentPage("设置", "应用管理", 3, 11);
        childinfoModels.add(pInfoSettingAppManager);
        PageInfoModel pInfoCenterSettingAppManager = createCenterPage("应用管理", "自启动管理", 3, 12);
        childinfoModels.add(pInfoCenterSettingAppManager);
        PageInfoModel pInfoAutoStartLast = createLastPage("自启动管理", 3, 13, 3, "自启动管理", 1);
        pInfoAutoStartLast.addCheckBox(createCheckBoxStep(AppInfo.getAppName(context), 1, true));
        childinfoModels.add(pInfoAutoStartLast);
        String infos = new Gson().toJson(childinfoModels);
        ILog.d("OppoColorsInfo_PCAM10v71", infos);
        return childinfoModels;
    }

    public List<MineSettingInfoModel> getSettingInfo() {
        return this.settingInfoModels;
    }

    public int getStep() {
        return this.totalSteps;
    }
}
