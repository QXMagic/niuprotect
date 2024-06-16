package com.niuniu.babyprotect.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.niuniu.babyprotect.model.SystemBlackAppModel;
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
public class SystemBlackAppListManager {
    private static final String TAG = "SystemBlackAppListManager";
    private static SystemBlackAppListManager instance;
    private int remenberDayOfWeek = -1;
    private List<SystemBlackAppModel.DataDTO> systemBlackApps;

    private SystemBlackAppListManager() {
    }

    public static SystemBlackAppListManager getInstance() {
        if (instance == null) {
            synchronized (SystemBlackAppListManager.class) {
                if (instance == null) {
                    instance = new SystemBlackAppListManager();
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

    private List<SystemBlackAppModel.DataDTO> getSystemBlackApps(Context context) {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(7) - 1;
        if (this.systemBlackApps == null) {
            this.systemBlackApps = getBlackSysApp(context);
        }
        if (dayOfWeek != this.remenberDayOfWeek) {
            ILog.d(TAG, "request whiteAPP");
            requestSystemBlacklist(context);
            this.remenberDayOfWeek = dayOfWeek;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("系統黑名单------------");
        List<SystemBlackAppModel.DataDTO> list = this.systemBlackApps;
        sb.append(list != null ? list.size() : 0);
        ILog.d(TAG, sb.toString());
        return this.systemBlackApps;
    }

    public boolean systemBlackApp(Context context, String packName) {
        List<SystemBlackAppModel.DataDTO> systemBlackApps = getSystemBlackApps(context);
        if (systemBlackApps != null) {
            int size = systemBlackApps.size();
            for (int i = 0; i < size; i++) {
                SystemBlackAppModel.DataDTO dataDTO = systemBlackApps.get(i);
                if (dataDTO.getPackageName().equals(packName)) {
                    ILog.d("black packageName", packName);
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public void requestSystemBlacklist(final Context context) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("isBlacklist", "1");
        NetTools.getInstance().getAsynHttp(context, StudentBaseUrl.appBlacklists_systemBlacklist, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    ILog.log(msg);
                    SharedPreUtil.setParam(context, SharedPreManager.KEY_SYSTEM_BLACK_APP, msg.toString());
                    systemBlackApps = null;
                }
            }
        });
    }

    private List<SystemBlackAppModel.DataDTO> getBlackSysApp(Context context) {
        SystemBlackAppModel mSystemBlackAppModel;
        SharedPreferences sp = context.getSharedPreferences(SharedPreManager.SP_NAME, 0);
        String userMsg = sp.getString(SharedPreManager.KEY_SYSTEM_BLACK_APP, "");
        ILog.d("SystemBlackAppModel", userMsg);
        if (!TextUtils.isEmpty(userMsg) && (mSystemBlackAppModel = (SystemBlackAppModel) new Gson().fromJson(userMsg, (Class<Object>) SystemBlackAppModel.class)) != null) {
            return mSystemBlackAppModel.getData();
        }
        return null;
    }
}
