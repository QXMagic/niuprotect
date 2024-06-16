package com.niuniu.babyprotect.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.niuniu.babyprotect.model.SystemWhiteAppModel;
import com.niuniu.babyprotect.network.NetTools;
import com.niuniu.babyprotect.network.ResultCallBackListener;
import com.niuniu.babyprotect.network.StudentBaseUrl;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.SharedPreUtil;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

import atmp.consts.Constants;

public class SystemWhiteAppListManager {
    private static final String TAG = "SystemWhiteAppListManager";
    private static SystemWhiteAppListManager instance;
    private int remenberDayOfWeek = -1;
    private List<SystemWhiteAppModel.DataDTO> systemWhiteApps;

    private SystemWhiteAppListManager() {
    }

    public static SystemWhiteAppListManager getInstance() {
        if (instance == null) {
            synchronized (SystemWhiteAppListManager.class) {
                if (instance == null) {
                    instance = new SystemWhiteAppListManager();
                }
            }
        }
        return instance;
    }

    public List<String> getBlackApp() {
        List<String> delStr = new ArrayList<>();
        delStr.add("卸载");
        delStr.add("应用卸载");
        delStr.add("软件卸载");
        delStr.add("卸载应用");
        delStr.add("卸载软件");
        delStr.add("应用删除");
        delStr.add("软件删除");
        delStr.add("删除应用");
        delStr.add("删除软件");
        delStr.add("软件管理");
        delStr.add("手机卫士");
        delStr.add("手机管家");
        delStr.add("内存清理");
        delStr.add("手机清理");
        delStr.add("鲁大师");
        return delStr;
    }

    public List<String> getBlackIds() {
        List<String> idStr = new ArrayList<>();
        idStr.add("com.bbk.launcher2:id/overview_panel");
        idStr.add("com.android.systemui:id/clear_all_notification");
        idStr.add("com.android.systemui:id/btn_clear_all");
        idStr.add("com.android.systemui:id/vivo_dissmiss_all");
        idStr.add("com.oppo.launcher:id/overview_panel");
        idStr.add("com.oppo.launcher:id/deep_shortcuts_container");
        idStr.add("com.oppo.launcher:id/btn_remove");
        return idStr;
    }

    private List<SystemWhiteAppModel.DataDTO> getSystemWhiteApps(Context context, boolean isReflash) {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(7) - 1;
        if (this.systemWhiteApps == null) {
            this.systemWhiteApps = getWhiteSysApp(context);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("系統白名单------------");
        sb.append(dayOfWeek != this.remenberDayOfWeek);
        ILog.d(TAG, sb.toString());
        if (dayOfWeek != this.remenberDayOfWeek || isReflash) {
            ILog.d(TAG, "request whiteAPP");
            this.remenberDayOfWeek = dayOfWeek;
            requestSystemWhitelist(context);
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("系統白名单------------");
        List<SystemWhiteAppModel.DataDTO> list = this.systemWhiteApps;
        sb2.append(list != null ? list.size() : 0);
        ILog.d(TAG, sb2.toString());
        return this.systemWhiteApps;
    }

    public boolean systemWhiteApp(Context context, String packName) {
        List<SystemWhiteAppModel.DataDTO> systemWhiteApps = getSystemWhiteApps(context, false);
        if (systemWhiteApps != null) {
            int size = systemWhiteApps.size();
            for (int i = 0; i < size; i++) {
                SystemWhiteAppModel.DataDTO dataDTO = systemWhiteApps.get(i);
                ILog.d("-getPackageName--", dataDTO.getPackageName());
                if (dataDTO.getPackageName().trim().equals(packName)) {
                    ILog.d("White packageName", packName);
                    return true;
                }
            }
        }
        return false;
    }

    public void requestSystemWhitelist(final Context context) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("isBlacklist", Constants.MSG_DB_READY_REPORT);
        NetTools.getInstance().getAsynHttp(context, StudentBaseUrl.appBlacklists_systemWhiteList, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    ILog.d("white app", msg.toString());
                    SharedPreUtil.setParam(context, SharedPreManager.KEY_SYSTEM_WHITE_APP, msg.toString());
                    systemWhiteApps = null;
                }
            }
        });
    }

    private List<SystemWhiteAppModel.DataDTO> getWhiteSysApp(Context context) {
        SystemWhiteAppModel mSystemWhiteAppModel;
        SharedPreferences sp = context.getSharedPreferences(SharedPreManager.SP_NAME, 0);
        String userMsg = sp.getString(SharedPreManager.KEY_SYSTEM_WHITE_APP, "");
        ILog.d("SystemWhiteAppModel", userMsg);
        if (!TextUtils.isEmpty(userMsg) && (mSystemWhiteAppModel = (SystemWhiteAppModel) new Gson().fromJson(userMsg, SystemWhiteAppModel.class)) != null) {
            return mSystemWhiteAppModel.getData();
        }
        return null;
    }
}
