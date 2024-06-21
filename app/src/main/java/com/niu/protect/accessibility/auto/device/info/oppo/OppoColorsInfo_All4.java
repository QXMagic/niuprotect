package com.niu.protect.accessibility.auto.device.info.oppo;

import android.content.Context;

import com.google.gson.Gson;
import com.niu.protect.Constant;
import com.niu.protect.accessibility.auto.bean.MineSettingInfoModel;
import com.niu.protect.accessibility.auto.bean.PageInfoModel;
import com.niu.protect.accessibility.auto.device.BaseCreatePageInfo;
import com.niu.protect.accessibility.auto.device.OppoDeviceInfo;
import com.niu.protect.tools.ILog;

import java.util.ArrayList;
import java.util.List;
public class OppoColorsInfo_All4 extends BaseCreatePageInfo {
    @Override
    protected BaseCreatePageInfo createInfo() {
        return new OppoColorsInfo_All4();
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
        PageInfoModel pInfoMainAutoStart = createMineSettingPage("自动授权中心", "耗电管理", 2, 4);
        childinfoModels.add(pInfoMainAutoStart);
        PageInfoModel pInfoPowerSetting = createCenterPage("应用信息", "耗电", 2, 5);
        childinfoModels.add(pInfoPowerSetting);
        PageInfoModel pInfoPowerDetail = createLastPage(Constant.APP_NAME, 2, 6, 1, "耗电管理", 2);
        pInfoPowerDetail.addCheckBox(createCheckBoxStep("允许应用自启动", 1, true));
        pInfoPowerDetail.addCheckBox(createCheckBoxStep("允许应用关联启动", 1, true));
        pInfoPowerDetail.addCheckBox(createCheckBoxStep("允许完全后台行为", 1, true));
        pInfoPowerDetail.addCheckBox(createCheckBoxStep("开启后会导致功耗", 1, true));
        childinfoModels.add(pInfoPowerDetail);
        PageInfoModel pInfoAppSetting = createParentPage("应用信息", "通知管理", 2, 7);
        childinfoModels.add(pInfoAppSetting);
        PageInfoModel pInfoPremCenter = createLastPage(Constant.APP_NAME, 2, 8, 1, "允许通知", 2);
        pInfoPremCenter.addCheckBox(createCheckBoxStep("允许通知", 1, true));
        childinfoModels.add(pInfoPremCenter);
        PageInfoModel pInfoAppCheckBoxSetting = createLastPage("应用信息", 2, 9, 1, "允许显示悬浮窗", 2);
        pInfoAppCheckBoxSetting.addCheckBox(createCheckBoxStep("允许显示悬浮窗", 1, false));
        pInfoAppCheckBoxSetting.addCheckBox(createCheckBoxStep("允许自动启动", 1, false));
        childinfoModels.add(pInfoAppCheckBoxSetting);
        PageInfoModel pInfoDeviceUse = createMineSettingPage("自动授权中心", "应用使用记录", 3, 10);
        childinfoModels.add(pInfoDeviceUse);
        PageInfoModel pInfoCenterAPPUse = createCenterPage("使用", Constant.APP_NAME, 3, 11);
        childinfoModels.add(pInfoCenterAPPUse);
        PageInfoModel pInfoCenterAPPUseDetail = createLastPage("允许查看使用情况", 3, 12, 2, "应用使用记录", 3);
        pInfoCenterAPPUseDetail.addCheckBox(createCheckBoxStep("允许查看使用情况", 1, true));
        childinfoModels.add(pInfoCenterAPPUseDetail);
        String infos = new Gson().toJson(childinfoModels);
        ILog.d("OppoColorsInfo_All4", infos);
        return childinfoModels;
    }

    public List<MineSettingInfoModel> getSettingInfo() {
        return this.settingInfoModels;
    }

    public int getStep() {
        return this.totalSteps;
    }
}
