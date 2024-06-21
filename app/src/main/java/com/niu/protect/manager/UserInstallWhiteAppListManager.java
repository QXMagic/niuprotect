package com.niu.protect.manager;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.niu.protect.BabyApplication;
import com.niu.protect.Constant;
import com.niu.protect.network.NetTools;
import com.niu.protect.network.ResultCallBackListener;
import com.niu.protect.network.StudentBaseUrl;
import com.niu.protect.tools.ILog;
import com.niu.protect.tools.SharedPreUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class UserInstallWhiteAppListManager {
    public static final String PRE_NAME = "pre_name_upload";
    private static UserInstallWhiteAppListManager instance;
    private List<String> whiteApps = new ArrayList();
    private List<String> oldInstallUpdateApps = new ArrayList();

    private UserInstallWhiteAppListManager() {
    }

    public static UserInstallWhiteAppListManager getInstance() {
        if (instance == null) {
            synchronized (UserInstallWhiteAppListManager.class) {
                if (instance == null) {
                    instance = new UserInstallWhiteAppListManager();
                }
            }
        }
        return instance;
    }

    public UserInstallWhiteAppListManager initWhiteApps() {
        this.whiteApps.add(Constant.APP_NAME);
        this.whiteApps.add("Breeno 指令");
        this.whiteApps.add("天气");
        this.whiteApps.add("日历");
        this.whiteApps.add("相册");
        this.whiteApps.add("相机");
        this.whiteApps.add("我的 OPPO");
        this.whiteApps.add("家庭空间");
        this.whiteApps.add("录音");
        this.whiteApps.add("即录剪辑");
        this.whiteApps.add("钱包");
        this.whiteApps.add("OPPO 社区");
        this.whiteApps.add("OPPO 商城");
        this.whiteApps.add("时钟");
        this.whiteApps.add("手机搬家");
        this.whiteApps.add("云服务");
        this.whiteApps.add("小布助手");
        this.whiteApps.add("主题商店");
        this.whiteApps.add("音乐");
        this.whiteApps.add("计算器");
        this.whiteApps.add("指南针");
        this.whiteApps.add("便签");
        this.whiteApps.add("一键锁屏");
        this.whiteApps.add("使用技巧");
        this.whiteApps.add("健康");
        this.whiteApps.add("智能家居");
        this.whiteApps.add("一键录屏");
        this.whiteApps.add("录屏精灵");
        this.whiteApps.add("全能录屏大师");
        this.whiteApps.add("国际上网");
        this.whiteApps.add("华为商城");
        this.whiteApps.add("link now");
        this.whiteApps.add("智慧语音");
        this.whiteApps.add("手机管家");
        this.whiteApps.add("录音机");
        this.whiteApps.add("我的华为");
        this.whiteApps.add("小艺输入法");
        this.whiteApps.add("备忘录");
        this.whiteApps.add("会员中心");
        this.whiteApps.add("畅连");
        this.whiteApps.add("生活服务");
        this.whiteApps.add("云空间");
        this.whiteApps.add("运动健康");
        this.whiteApps.add("电子邮件");
        this.whiteApps.add("华为教育中心");
        this.whiteApps.add("手电筒");
        this.whiteApps.add("备份恢复");
        this.whiteApps.add("短信");
        this.whiteApps.add("信息");
        this.whiteApps.add("电话");
        this.whiteApps.add("百度地图");
        this.whiteApps.add("高德地图");
        initOldInstallApps();
        return instance;
    }

    public boolean contains(String name) {
        if (!TextUtils.isEmpty(name)) {
            if (this.whiteApps.contains(name) || containsUpload(name)) {
                return true;
            }
            return false;
        }
        return false;
    }

    private void initOldInstallApps() {
        List<String> list = this.oldInstallUpdateApps;
        if (list == null || list.size() == 0) {
            SharedPreUtil sharedPreferenUtil = new SharedPreUtil(BabyApplication.getInstance(), PRE_NAME);
            this.oldInstallUpdateApps = sharedPreferenUtil.getDataList(SharedPreManager.KEY_APP_UPLOAD_APPS, String.class);
        }
        ILog.d("oldInstallUpdateApps", new Gson().toJson(this.oldInstallUpdateApps) + "--");
    }

    public void addInstallApps(String appName) {
        if (!TextUtils.isEmpty(appName) && !containsUpload(appName)) {
            this.oldInstallUpdateApps.add(appName);
        }
    }

    public void saveOldApps() {
        List<String> list = this.oldInstallUpdateApps;
        if (list != null && list.size() > 0) {
            SharedPreUtil sharedPreferenUtil = new SharedPreUtil(BabyApplication.getInstance(), PRE_NAME);
            sharedPreferenUtil.setDataList(SharedPreManager.KEY_APP_UPLOAD_APPS, this.oldInstallUpdateApps);
        }
    }

    public void clearOldApps() {
        List<String> list = this.oldInstallUpdateApps;
        if (list != null && list.size() > 0) {
            SharedPreUtil.clearSaveApp(BabyApplication.getInstance(), PRE_NAME);
        }
    }

    public boolean containsUpload(String appName) {
        if (TextUtils.isEmpty(appName) || this.oldInstallUpdateApps.size() <= 0) {
            return false;
        }
        return this.oldInstallUpdateApps.contains(appName) || this.whiteApps.contains(appName);
    }

    public boolean oldAppsUplaod() {
        List<String> list = this.oldInstallUpdateApps;
        return (list == null || list.size() == 0) ? false : true;
    }

    public void reqeustSystemWhitelist(Context context) {
        Map<String, String> parameters = new HashMap<>();
        NetTools.getInstance().getAsynHttp(context, StudentBaseUrl.appBlacklists_systemWhiteList, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    Log.i("getUserBlacklist", msg.toString());
                }
            }
        });
    }
}
