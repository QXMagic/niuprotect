package com.niu.protect.accessibility.auto.device.info.oppo;

import android.content.Context;

import com.google.gson.Gson;
import com.niu.protect.Constant;
import com.niu.protect.accessibility.auto.bean.MineSettingInfoModel;
import com.niu.protect.accessibility.auto.bean.PageInfoModel;
import com.niu.protect.accessibility.auto.device.BaseCreatePageInfo;
import com.niu.protect.tools.ILog;

import java.util.ArrayList;
import java.util.List;
public class OppoColorsInfo_All6 extends BaseCreatePageInfo {
    @Override
    protected BaseCreatePageInfo createInfo() {
        return new OppoColorsInfo_All6();
    }

    @Override
    public List<PageInfoModel> createPage(Context context) {
        List<PageInfoModel> childinfoModels = new ArrayList<>();
        PageInfoModel pInfoMainAutoStart = createMineSettingPage("自动授权中心", "耗电管理", 1, 1);
        childinfoModels.add(pInfoMainAutoStart);
        PageInfoModel pInfoPowerSetting = createCenterPage("应用信息", "耗电", 1, 2);
        childinfoModels.add(pInfoPowerSetting);
        PageInfoModel pInfoPowerDetail = createLastPage(Constant.APP_NAME, 1, 3, 1, "耗电管理", 2);
        pInfoPowerDetail.addCheckBox(createCheckBoxStep("开启后会导致功耗", 1, true));
        childinfoModels.add(pInfoPowerDetail);
        PageInfoModel pInfoAppSetting = createParentPage("应用信息", "通知管理", 2, 4);
        childinfoModels.add(pInfoAppSetting);
        PageInfoModel pInfoPremCenter = createLastPage(Constant.APP_NAME, 2, 5, 1, "允许通知", 4);
        pInfoPremCenter.addCheckBox(createCheckBoxStep("允许通知", 1, true));
        childinfoModels.add(pInfoPremCenter);
        PageInfoModel pInfoAppCheckBoxSetting = createLastPage("应用信息", 3, 6, 1, "允许显示悬浮窗", 5);
        pInfoAppCheckBoxSetting.addCheckBox(createCheckBoxStep("允许显示悬浮窗", 1, false));
        childinfoModels.add(pInfoAppCheckBoxSetting);
        PageInfoModel pInfoDeviceUse = createMineSettingPage("自动授权中心", "应用使用记录", 4, 7);
        childinfoModels.add(pInfoDeviceUse);
        PageInfoModel pInfoCenterAPPUse = createCenterPage("使用", Constant.APP_NAME, 4, 8);
        childinfoModels.add(pInfoCenterAPPUse);
        PageInfoModel pInfoCenterAPPUseDetail = createLastPage("使用情况", 4, 9, 2, "应用使用记录", 3);
        pInfoCenterAPPUseDetail.addCheckBox(createCheckBoxStep("允许查看使用情况", 1, true));
        childinfoModels.add(pInfoCenterAPPUseDetail);
        PageInfoModel pInfoManager = createMineSettingPage("自动授权中心", "自启动管理", 5, 10);
        childinfoModels.add(pInfoManager);
        PageInfoModel pInfoManagerMain1 = createCenterPage("设置", "应用管理", 5, 11);
        childinfoModels.add(pInfoManagerMain1);
        PageInfoModel pInfoManagerMain = createCenterPage("应用管理", "自启动管理", 5, 12);
        childinfoModels.add(pInfoManagerMain);
        PageInfoModel pInfoAutoDetail = createLastPage("自启动管理", 5, 13, 1, "自启动管理", 1);
        pInfoAutoDetail.addCheckBox(createCheckBoxStep(Constant.APP_NAME, 1, true));
        childinfoModels.add(pInfoAutoDetail);
        PageInfoModel pInfoRelatStart = createParentPage("应用管理", "关联启动管理", 5, 14);
        childinfoModels.add(pInfoRelatStart);
        PageInfoModel pInfoRelatStartLast = createLastPage("关联启动管理", 5, 15, 3, "关联启动", 1);
        pInfoRelatStartLast.addCheckBox(createCheckBoxStep(Constant.APP_NAME, 1, true));
        childinfoModels.add(pInfoRelatStartLast);
        String infos = new Gson().toJson(childinfoModels);
        ILog.d("OppoColorsInfo_All6", infos);
        return childinfoModels;
    }

    public List<MineSettingInfoModel> getSettingInfo() {
        return this.settingInfoModels;
    }

    public int getStep() {
        return this.totalSteps;
    }
}
