package com.niu.protect.accessibility.auto.device.info.vivo;

import android.content.Context;
import com.google.gson.Gson;
import com.niu.protect.accessibility.auto.app.appinfo.AppInfo;
import com.niu.protect.accessibility.auto.bean.PageInfoModel;
import com.niu.protect.accessibility.auto.device.BaseCreatePageInfo;
import com.niu.protect.tools.ILog;
import java.util.ArrayList;
import java.util.List;
public class Vivo_originOs10_2 extends BaseCreatePageInfo {
    @Override
    protected BaseCreatePageInfo createInfo() {
        return new Vivo_originOs10_2();
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
        PageInfoModel pInfoMinePowerCenter = createCenterPage("电池", "耗电管理", 4, 12);
        childinfoModels.add(pInfoMinePowerCenter);
        PageInfoModel pInfoPowerAppList = createCenterPage("后台高耗电", AppInfo.getAppName(context), 4, 13);
        childinfoModels.add(pInfoPowerAppList);
        PageInfoModel pInfoPowerDetail = createLastPage("后台高耗电", 4, 14, 4, "高耗电量", 1);
        pInfoPowerDetail.addCheckBox(createCheckBoxStep("允许应用在后台消耗更多的电量", 1, true));
        childinfoModels.add(pInfoPowerDetail);
        PageInfoModel pageAutoStart = createMineSettingPage("自动授权中心", "自启动权限设置", 5, 15);
        childinfoModels.add(pageAutoStart);
        PageInfoModel pageAutoStartDetail = createLastPage("自启动", 5, 16, 1, "自启动权限设置", 9);
        pageAutoStartDetail.addCheckBox(createCheckBoxStep(Constant.APP_NAME, 1, true));
        childinfoModels.add(pageAutoStartDetail);
        String infos = new Gson().toJson(childinfoModels);
        ILog.d("Vivo_originOs10", infos);
        return childinfoModels;
    }
}
