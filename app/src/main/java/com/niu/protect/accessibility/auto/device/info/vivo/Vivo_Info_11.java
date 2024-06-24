package com.niu.protect.accessibility.auto.device.info.vivo;

import android.content.Context;
import com.google.gson.Gson;
import com.niu.protect.lib.appinfo.AppInfo;
import com.niu.protect.accessibility.auto.bean.PageInfoModel;
import com.niu.protect.accessibility.auto.device.BaseCreatePageInfo;
import com.niu.protect.tools.ILog;
import java.util.ArrayList;
import java.util.List;
public class Vivo_Info_11 extends BaseCreatePageInfo {
    @Override
    protected BaseCreatePageInfo createInfo() {
        return new Vivo_Info_11();
    }

    @Override
    public List<PageInfoModel> createPage(Context context) {
        List<PageInfoModel> childinfoModels = new ArrayList<>();
        PageInfoModel pageInfoModel = createMineSettingPage("自动授权中心", "应用权限设置", 1, 1);
        childinfoModels.add(pageInfoModel);
        PageInfoModel pInfoSetting = createParentPage("信息", "通知", 2, 2);
        childinfoModels.add(pInfoSetting);
        PageInfoModel pInfoNotifiDetail = createLastPage(AppInfo.getAppName(context), 1, 3, 1, "应用权限设置", 2);
        pInfoNotifiDetail.addCheckBox(createCheckBoxStep("允许通知", 1, true));
        childinfoModels.add(pInfoNotifiDetail);
        PageInfoModel pInfoSettingSecond = createParentPage("信息", "权限", 1, 4);
        childinfoModels.add(pInfoSettingSecond);
        PageInfoModel pInfoPremCenter = createCenterPage("应用权限", "单项权限设置", 1, 5);
        childinfoModels.add(pInfoPremCenter);
        PageInfoModel pInfoPremDetail = createLastPage(AppInfo.getAppName(context), 1, 6, 3, "应用权限设置", 2);
        pInfoPremDetail.addCheckBox(createCheckBoxStep("后台启动", 1, false));
        pInfoPremDetail.addCheckBox(createCheckBoxStep("悬浮窗", 1, false));
        pInfoPremDetail.addCheckBox(createCheckBoxStep("后台弹出界面", 1, true));
        childinfoModels.add(pInfoPremDetail);
        PageInfoModel pInfoDeviceUse = createMineSettingPage("自动授权中心", "应用使用记录", 3, 7);
        childinfoModels.add(pInfoDeviceUse);
        PageInfoModel pInfoCenterAPPUse = createCenterPage("有权查看使用情况的应用", AppInfo.getAppName(context), 3, 8);
        childinfoModels.add(pInfoCenterAPPUse);
        PageInfoModel pInfoCenterAPPUseDetail = createLastPage("使用情况访问权限", 3, 9, 2, "应用使用记录", 3);
        pInfoCenterAPPUseDetail.addCheckBox(createCheckBoxStep("允许查看使用情况", 1, true));
        childinfoModels.add(pInfoCenterAPPUseDetail);
        PageInfoModel pInfoMinePower = createMineSettingPage("自动授权中心", "高耗电量", 4, 10);
        childinfoModels.add(pInfoMinePower);
        PageInfoModel pInfoMinePowerSetting = createCenterPage("设置", "电池", 4, 11);
        childinfoModels.add(pInfoMinePowerSetting);
        PageInfoModel pInfoMinePowerCenter = createCenterPage("电池", "后台高耗电", 4, 12);
        childinfoModels.add(pInfoMinePowerCenter);
        PageInfoModel pInfoPowerDetail = createLastPage("后台高耗电", 4, 13, 3, "高耗电量", 1);
        pInfoPowerDetail.addCheckBox(createCheckBoxStep(AppInfo.getAppName(context), 1, true));
        childinfoModels.add(pInfoPowerDetail);
        String infos = new Gson().toJson(childinfoModels);
        ILog.d("Vivo_Info_11", infos);
        return childinfoModels;
    }
}
