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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
public class UserWhiteAppListManager {
    private static final String TAG = "UserWhiteAppListManager";
    private static UserWhiteAppListManager instance;
    private List<UserBlackAppInfoModel.DataDTO> mUserWhiteApps;

    private UserWhiteAppListManager() {
    }

    public static UserWhiteAppListManager getInstance() {
        if (instance == null) {
            synchronized (UserWhiteAppListManager.class) {
                if (instance == null) {
                    instance = new UserWhiteAppListManager();
                }
            }
        }
        return instance;
    }

    public boolean userWhiteApp(Context context, String packname) {
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

    public void reqeustUserWhitelist(final Context context) {
        Map<String, String> parameters = new HashMap<>();
        NetTools.getInstance().getAsynHttp(context, StudentBaseUrl.USER_WHITE_APP_LIST, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    Log.i("getUserWhiteAppList", msg.toString());
                    UserWhiteAppListManager.saveWhiteUserApp(context, msg.toString());
                    mUserWhiteApps = null;
                }
            }
        });
    }

    public static void saveWhiteUserApp(Context context, String msg) {
        SharedPreferences sp = context.getSharedPreferences(SharedPreManager.SP_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        if (msg == null) {
            editor.remove(SharedPreManager.KEY_USER_WHITE_APP);
        } else {
            editor.putString(SharedPreManager.KEY_USER_WHITE_APP, msg);
        }
        editor.commit();
    }

    private List<UserBlackAppInfoModel.DataDTO> getSaveWhiteUserApp(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SharedPreManager.SP_NAME, 0);
        String userMsg = sp.getString(SharedPreManager.KEY_USER_WHITE_APP, "");
        if (!TextUtils.isEmpty(userMsg)) {
            UserBlackAppInfoModel userBlackAppInfoModel = (UserBlackAppInfoModel) new Gson().fromJson(userMsg, UserBlackAppInfoModel.class);
            List<UserBlackAppInfoModel.DataDTO> appInfos = userBlackAppInfoModel.getData();
            return appInfos;
        }
        return null;
    }

    private List<UserBlackAppInfoModel.DataDTO> getmUserBalckApps(Context context) {
        if (this.mUserWhiteApps == null) {
            this.mUserWhiteApps = getSaveWhiteUserApp(context);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("用户APP白名单------------");
        List<UserBlackAppInfoModel.DataDTO> list = this.mUserWhiteApps;
        sb.append(list != null ? list.size() : 0);
        ILog.d(TAG, sb.toString());
        return this.mUserWhiteApps;
    }
}
