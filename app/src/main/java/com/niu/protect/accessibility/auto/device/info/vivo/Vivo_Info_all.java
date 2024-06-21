package com.niu.protect.accessibility.auto.device.info.vivo;

import android.content.Context;

import com.google.gson.Gson;
import com.niu.protect.Constant;
import com.niu.protect.accessibility.auto.app.appinfo.AppInfo;
import com.niu.protect.accessibility.auto.bean.PageInfoModel;
import com.niu.protect.accessibility.auto.device.BaseCreatePageInfo;
import com.niu.protect.tools.ILog;

import java.util.ArrayList;
import java.util.List;
public class Vivo_Info_all extends BaseCreatePageInfo {
    @Override
    protected BaseCreatePageInfo createInfo() {
        return new Vivo_Info_all();
    }

    @Override
    public List<PageInfoModel> createPage(Context context) {
        List<PageInfoModel> childinfoModels = new ArrayList<>();
        PageInfoModel pageInfoModel = createMineSettingPage("自动授权中心", "通知权限设置", 1, 1);
        childinfoModels.add(pageInfoModel);
        PageInfoModel pInfoNotifiDetail = createLastPage(AppInfo.getAppName(context), 1, 2, 1, "通知权限设置", 4);
        pInfoNotifiDetail.addCheckBox(createCheckBoxStep("允许通知", 1, true));
        childinfoModels.add(pInfoNotifiDetail);
        PageInfoModel pageFloatWindows = createMineSettingPage("自动授权中心", "悬浮窗权限设置", 2, 3);
        childinfoModels.add(pageFloatWindows);
        PageInfoModel pageFloatWindowsDetail = createLastPage("悬浮窗", 2, 4, 1, "悬浮窗权限设置", 5);
        pageFloatWindowsDetail.addCheckBox(createCheckBoxStep(Constant.APP_NAME, 1, true));
        childinfoModels.add(pageFloatWindowsDetail);
        PageInfoModel pageAutoStart = createMineSettingPage("自动授权中心", "自启动权限设置", 3, 5);
        childinfoModels.add(pageAutoStart);
        PageInfoModel pageAutoStartDetail = createLastPage("自启动", 3, 6, 1, "自启动权限设置", 9);
        pageAutoStartDetail.addCheckBox(createCheckBoxStep(Constant.APP_NAME, 1, true));
        childinfoModels.add(pageAutoStartDetail);
        PageInfoModel pInfoDeviceUse = createMineSettingPage("自动授权中心", "应用使用记录", 4, 7);
        childinfoModels.add(pInfoDeviceUse);
        PageInfoModel pInfoCenterAPPUse = createCenterPage("有权查看使用情况的应用", AppInfo.getAppName(context), 4, 8);
        childinfoModels.add(pInfoCenterAPPUse);
        PageInfoModel pInfoCenterAPPUseDetail = createLastPage("使用情况访问权限", 4, 9, 2, "应用使用记录", 3);
        pInfoCenterAPPUseDetail.addCheckBox(createCheckBoxStep("允许查看使用情况", 1, true));
        childinfoModels.add(pInfoCenterAPPUseDetail);
        PageInfoModel pInfoMinePower = createMineSettingPage("自动授权中心", "高耗电量", 5, 10);
        childinfoModels.add(pInfoMinePower);
        PageInfoModel pInfoMinePowerSetting = createCenterPage("设置", "电池", 5, 11);
        childinfoModels.add(pInfoMinePowerSetting);
        PageInfoModel pInfoMinePowerCenter = createCenterPage("电池", "后台高耗电", 5, 12);
        childinfoModels.add(pInfoMinePowerCenter);
        PageInfoModel pInfoPowerDetail = createLastPage("后台高耗电", 5, 13, 3, "高耗电量", 1);
        pInfoPowerDetail.addCheckBox(createCheckBoxStep(AppInfo.getAppName(context), 1, true));
        childinfoModels.add(pInfoPowerDetail);
        String infos = new Gson().toJson(childinfoModels);
        ILog.d("Vivo_Info_all", infos);
        return childinfoModels;
    }
}
