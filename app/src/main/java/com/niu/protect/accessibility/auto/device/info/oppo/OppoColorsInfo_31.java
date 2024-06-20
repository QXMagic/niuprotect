package com.niu.protect.accessibility.auto.device.info.oppo;

import android.content.Context;
import com.google.gson.Gson;
import com.niu.protect.accessibility.auto.bean.MineSettingInfoModel;
import com.niu.protect.accessibility.auto.bean.PageInfoModel;
import com.niu.protect.accessibility.auto.device.BaseCreatePageInfo;
import com.niu.protect.tools.ILog;
import java.util.ArrayList;
import java.util.List;
public class OppoColorsInfo_31 extends BaseCreatePageInfo {
    @Override
    protected BaseCreatePageInfo createInfo() {
        return new OppoColorsInfo_31();
    }

    @Override
    public List<PageInfoModel> createPage(Context context) {
        List<PageInfoModel> childinfoModels = new ArrayList<>();
        PageInfoModel pInfoMainAutoStart = createMineSettingPage("自动授权中心", "耗电管理", 1, 1);
        childinfoModels.add(pInfoMainAutoStart);
        PageInfoModel pInfoPowerSetting = createCenterPage("应用详情", "耗电管理", 1, 2);
        childinfoModels.add(pInfoPowerSetting);
        PageInfoModel pInfoPowerDetail = createLastPage(Constant.APP_NAME, 1, 3, 1, "耗电管理", 2);
        pInfoPowerDetail.addCheckBox(createCheckBoxStep("允许应用自启动", 1, true));
        pInfoPowerDetail.addCheckBox(createCheckBoxStep("允许应用关联启动", 1, true));
        pInfoPowerDetail.addCheckBox(createCheckBoxStep("允许完全后台行为", 1, true));
        childinfoModels.add(pInfoPowerDetail);
        PageInfoModel pInfoAppSetting = createParentPage("应用详情", "通知管理", 1, 4);
        childinfoModels.add(pInfoAppSetting);
        PageInfoModel pInfoPremCenter = createLastPage(Constant.APP_NAME, 1, 5, 2, "通知管理", 2);
        pInfoPremCenter.addCheckBox(createCheckBoxStep("允许通知", 1, true));
        childinfoModels.add(pInfoPremCenter);
        PageInfoModel pInfoFloatView = createMineSettingPage("自动授权中心", "设置悬浮窗权限", 2, 6);
        childinfoModels.add(pInfoFloatView);
        PageInfoModel pInfoFloatViewCenter = createParentPage("悬浮窗", Constant.APP_NAME, 2, 7);
        childinfoModels.add(pInfoFloatViewCenter);
        PageInfoModel pInfoFloatViewSetting = createLastPage(Constant.APP_NAME, 2, 8, 1, "设置悬浮窗权限", 5);
        pInfoFloatViewSetting.addCheckBox(createCheckBoxStep("允许", 1, true));
        childinfoModels.add(pInfoFloatViewSetting);
        PageInfoModel pInfoDeviceUse = createMineSettingPage("自动授权中心", "应用使用记录", 3, 9);
        childinfoModels.add(pInfoDeviceUse);
        PageInfoModel pInfoCenterAPPUse = createCenterPage("使用情况", Constant.APP_NAME, 3, 10);
        childinfoModels.add(pInfoCenterAPPUse);
        PageInfoModel pInfoCenterAPPUseDetail = createLastPage("允许查看使用情况", 3, 11, 2, "应用使用记录", 3);
        pInfoCenterAPPUseDetail.addCheckBox(createCheckBoxStep("允许查看使用情况", 1, true));
        childinfoModels.add(pInfoCenterAPPUseDetail);
        String infos = new Gson().toJson(childinfoModels);
        ILog.d("OppoColorsInfo_31", infos);
        return childinfoModels;
    }

    public List<MineSettingInfoModel> getSettingInfo() {
        return this.settingInfoModels;
    }

    public int getStep() {
        return this.totalSteps;
    }
}
