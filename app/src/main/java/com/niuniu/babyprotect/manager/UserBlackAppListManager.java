package com.niuniu.babyprotect.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.Gson;
import com.niuniu.babyprotect.model.UserBlackAppInfoModel;
import com.niuniu.babyprotect.network.NetTools;
import com.niuniu.babyprotect.network.ResultCallBackListener;
import com.niuniu.babyprotect.network.StudentBaseUrl;
import com.niuniu.babyprotect.tools.ILog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
public class UserBlackAppListManager {
    private static final String TAG = "UserBlackAppListManager";
    private static UserBlackAppListManager instance;
    private List<UserBlackAppInfoModel.DataDTO> mUserBalckApps;

    private UserBlackAppListManager() {
    }

    public static UserBlackAppListManager getInstance() {
        if (instance == null) {
            synchronized (UserBlackAppListManager.class) {
                if (instance == null) {
                    instance = new UserBlackAppListManager();
                }
            }
        }
        return instance;
    }

    public List<String> getBlackApp() {
        List<String> delStr = new ArrayList<>();
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

    public boolean userBlackApp(Context context, String packname) {
        List<UserBlackAppInfoModel.DataDTO> userBlackApps = getmUserBalckApps(context);
        if (userBlackApps != null && userBlackApps.size() > 0) {
            int size = userBlackApps.size();
            for (int i = 0; i < size; i++) {
                UserBlackAppInfoModel.DataDTO dataDTO = userBlackApps.get(i);
                if (dataDTO.getPackageName().equals(packname)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public void reqeustUserBlacklist(final Context context) {
        Map<String, String> parameters = new HashMap<>();
        NetTools.getInstance().getAsynHttp(context, StudentBaseUrl.appBlacklists_studentBlacklist, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    Log.i("getUserBlacklist", msg.toString());
                    UserBlackAppListManager.saveBlackUserApp(context, msg.toString());
                    mUserBalckApps = null;
                }
            }
        });
    }

    public static void saveBlackUserApp(Context context, String msg) {
        SharedPreferences sp = context.getSharedPreferences(SharedPreManager.SP_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        if (msg == null) {
            editor.remove(SharedPreManager.KEY_USER_BLACK_APP);
        } else {
            editor.putString(SharedPreManager.KEY_USER_BLACK_APP, msg);
        }
        editor.commit();
    }

    private List<UserBlackAppInfoModel.DataDTO> getSaveBlackUserApp(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SharedPreManager.SP_NAME, 0);
        String userMsg = sp.getString(SharedPreManager.KEY_USER_BLACK_APP, "");
        if (!TextUtils.isEmpty(userMsg)) {
            UserBlackAppInfoModel userBlackAppInfoModel = (UserBlackAppInfoModel) new Gson().fromJson(userMsg, (Class<Object>) UserBlackAppInfoModel.class);
            List<UserBlackAppInfoModel.DataDTO> appInfos = userBlackAppInfoModel.getData();
            return appInfos;
        }
        return null;
    }

    private List<UserBlackAppInfoModel.DataDTO> getmUserBalckApps(Context context) {
        if (this.mUserBalckApps == null) {
            this.mUserBalckApps = getSaveBlackUserApp(context);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("用户APP黑名单------------");
        List<UserBlackAppInfoModel.DataDTO> list = this.mUserBalckApps;
        sb.append(list != null ? list.size() : 0);
        ILog.d(TAG, sb.toString());
        return this.mUserBalckApps;
    }
}
