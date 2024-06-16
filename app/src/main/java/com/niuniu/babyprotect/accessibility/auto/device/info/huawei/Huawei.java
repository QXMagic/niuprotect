package com.niuniu.babyprotect.accessibility.auto.device.info.huawei;

import android.content.Context;
import com.google.gson.Gson;
import com.niuniu.babyprotect.accessibility.auto.bean.MineSettingInfoModel;
import com.niuniu.babyprotect.accessibility.auto.bean.PageInfoModel;
import com.niuniu.babyprotect.accessibility.auto.device.BaseCreatePageInfo;
import com.niuniu.babyprotect.tools.ILog;
import java.util.ArrayList;
import java.util.List;
public class Huawei extends BaseCreatePageInfo {
    @Override
    protected BaseCreatePageInfo createInfo() {
        return new Huawei();
    }

    @Override
    public List<PageInfoModel> createPage(Context context) {
        List<PageInfoModel> childinfoModels = new ArrayList<>();
        PageInfoModel pInfoMainAutoStart = createMineSettingPage("自动授权中心", "应用自启动管理", 1, 1);
        childinfoModels.add(pInfoMainAutoStart);
        PageInfoModel pInfoPowerSetting = createCenterPage("管家", "应用启动管理", 1, 2);
        childinfoModels.add(pInfoPowerSetting);
        PageInfoModel pInfoAutoStartCenter = createLastPage("应用启动管理", 1, 3, 0, "应用自启动管理", 7);
        pInfoAutoStartCenter.addCheckBox(createCheckBoxStep("3985学生端", 0, true));
        childinfoModels.add(pInfoAutoStartCenter);
        PageInfoModel pInfoPowerDetail = createLastAlterWindowsPage("手动管理", 1, 4, "确定", 3, "应用自启动管理", 7);
        pInfoPowerDetail.addCheckBox(createCheckBoxStep("允许自启动", 1, true));
        pInfoPowerDetail.addCheckBox(createCheckBoxStep("允许关联启动", 1, true));
        pInfoPowerDetail.addCheckBox(createCheckBoxStep("允许后台活动", 1, true));
        childinfoModels.add(pInfoPowerDetail);
        PageInfoModel pInfoNotifaction = createMineSettingPage("自动授权中心", "通知管理", 2, 5);
        childinfoModels.add(pInfoNotifaction);
        PageInfoModel pInfoAppSetting = createParentPage("应用信息", "通知管理", 1, 6);
        childinfoModels.add(pInfoAppSetting);
        PageInfoModel pInfoPremCenter = createLastPage("3985学生端", 1, 7, 2, "通知管理", 2);
        pInfoPremCenter.addCheckBox(createCheckBoxStep("允许通知", 1, true));
        childinfoModels.add(pInfoPremCenter);
        PageInfoModel pInfoFloatView = createMineSettingPage("自动授权中心", "设置悬浮窗权限", 3, 8);
        childinfoModels.add(pInfoFloatView);
        PageInfoModel pInfoFloatViewCenter = createParentPage("显示在其他应用的上层", "3985学生端", 3, 9);
        childinfoModels.add(pInfoFloatViewCenter);
        PageInfoModel pInfoFloatViewSetting = createLastPage("在其他应用上层显示", 3, 10, 2, "设置悬浮窗权限", 5);
        pInfoFloatViewSetting.addCheckBox(createCheckBoxStep("在其他应用上层显示", 1, true));
        childinfoModels.add(pInfoFloatViewSetting);
        PageInfoModel pInfoDeviceUse = createMineSettingPage("自动授权中心", "应用使用记录", 4, 11);
        childinfoModels.add(pInfoDeviceUse);
        PageInfoModel pInfoCenterAPPUse = createCenterPage("使用情况", "3985学生端", 4, 12);
        childinfoModels.add(pInfoCenterAPPUse);
        PageInfoModel pInfoCenterAPPUseDetail = createLastPage("允许访问使用记录", 4, 13, 2, "应用使用记录", 3);
        pInfoCenterAPPUseDetail.addCheckBox(createCheckBoxStep("允许访问使用记录", 1, true));
        childinfoModels.add(pInfoCenterAPPUseDetail);
        String infos = new Gson().toJson(childinfoModels);
        ILog.d("huawei--", infos);
        return childinfoModels;
    }

    public List<MineSettingInfoModel> getSettingInfo() {
        return this.settingInfoModels;
    }

    public int getStep() {
        return this.totalSteps;
    }
}
