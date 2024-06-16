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
public class OppoColorsInfo_PDKM00 extends BaseCreatePageInfo {
    @Override
    protected BaseCreatePageInfo createInfo() {
        return new OppoColorsInfo_PDKM00();
    }

    @Override
    public List<PageInfoModel> createPage(Context context) {
        List<PageInfoModel> childinfoModels = new ArrayList<>();
        PageInfoModel pInfoMainAutoStart = createMineSettingPage("自动授权中心", "耗电管理", 1, 1);
        childinfoModels.add(pInfoMainAutoStart);
        PageInfoModel pInfoPowerSetting = createCenterPage("应用", "耗电管理", 1, 2);
        childinfoModels.add(pInfoPowerSetting);
        PageInfoModel pInfoPowerDetail = createLastPage(AppInfo.getAppName(context), 1, 3, 1, "耗电管理", 2);
        pInfoPowerDetail.addCheckBox(createCheckBoxStep("允许应用自启动", 1, true));
        pInfoPowerDetail.addCheckBox(createCheckBoxStep("允许应用关联启动", 1, true));
        pInfoPowerDetail.addCheckBox(createCheckBoxStep("允许完全后台行为", 1, true));
        childinfoModels.add(pInfoPowerDetail);
        PageInfoModel pInfoAppSetting = createParentPage("应用", "通知管理", 1, 4);
        childinfoModels.add(pInfoAppSetting);
        PageInfoModel pInfoPremCenter = createLastPage(AppInfo.getAppName(context), 1, 5, 1, "允许通知", 2);
        pInfoPremCenter.addCheckBox(createCheckBoxStep("允许通知", 1, true));
        childinfoModels.add(pInfoPremCenter);
        PageInfoModel pInfoAppCheckBoxSetting = createLastPage("应用", 1, 6, 1, "允许显示悬浮窗", 2);
        pInfoAppCheckBoxSetting.addCheckBox(createCheckBoxStep("允许显示悬浮窗", 1, false));
        childinfoModels.add(pInfoAppCheckBoxSetting);
        PageInfoModel pInfoDeviceUse = createMineSettingPage("自动授权中心", "应用使用记录", 2, 7);
        childinfoModels.add(pInfoDeviceUse);
        PageInfoModel pInfoCenterAPPUse = createCenterPage("访问应用使用情况", AppInfo.getAppName(context), 2, 8);
        childinfoModels.add(pInfoCenterAPPUse);
        PageInfoModel pInfoCenterAPPUseDetail = createLastPage("允许查看使用情况", 2, 9, 2, "应用使用记录", 3);
        pInfoCenterAPPUseDetail.addCheckBox(createCheckBoxStep("允许查看使用情况", 1, true));
        childinfoModels.add(pInfoCenterAPPUseDetail);
        String infos = new Gson().toJson(childinfoModels);
        ILog.d("OppoColorsInfo_PDKM00", infos);
        return childinfoModels;
    }

    public List<MineSettingInfoModel> getSettingInfo() {
        return this.settingInfoModels;
    }

    public int getStep() {
        return this.totalSteps;
    }
}
