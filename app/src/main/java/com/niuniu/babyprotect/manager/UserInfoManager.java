package com.niuniu.babyprotect.manager;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.niuniu.babyprotect.model.UserInfo;
import com.niuniu.babyprotect.network.NetTools;
import com.niuniu.babyprotect.network.ResultCallBackListener;
import com.niuniu.babyprotect.network.StudentBaseUrl;
import com.niuniu.babyprotect.tools.ILog;
import com.tencent.mmkv.MMKV;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
public class UserInfoManager {
    private static volatile UserInfoManager instance = null;
    private static volatile UserInfo userInfo;
    MMKV mmkv = MMKV.mmkvWithID("user_info", 2);

    public interface OnSuccessCallBack {
        void onSuccess();
    }

    private UserInfoManager() {
    }

    public static UserInfoManager getInstance() {
        if (instance == null) {
            synchronized (UserInfoManager.class) {
                if (instance == null) {
                    instance = new UserInfoManager();
                }
            }
        }
        return instance;
    }

    public UserInfo getUserInfo(Context context) {
        if (userInfo == null) {
            userInfo = getUser(context);
        }
        return userInfo;
    }

    public void refreshUserInfo(final Context context, final OnSuccessCallBack onSuccessCallBack) {
        UserInfo userInfo2 = getUserInfo(context);
        if (userInfo2 == null) {
            return;
        }
        Map<String, String> parameters = new HashMap<>();
        parameters.put("studentId", userInfo2.getId());
        NetTools.getInstance().getAsynHttp(context, StudentBaseUrl.members_getMemberInfo, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    ILog.log(msg);
                    try {
                        String data = msg.getString("data");
                        saveUser(context, data);
                        if (onSuccessCallBack != null) {
                            onSuccessCallBack.onSuccess();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void saveUser(Context context, String msg) {
        if (msg == null) {
            userInfo = null;
            this.mmkv.removeValueForKey(SharedPreManager.KEY_USER_INFO);
            return;
        }
        userInfo = (UserInfo) new Gson().fromJson(msg, UserInfo.class);
        boolean saveSuccess = this.mmkv.encode(SharedPreManager.KEY_USER_INFO, msg);
        ILog.d("---", saveSuccess + "-userInfo mode--" + userInfo.getParentPattern());
    }

    private UserInfo getUser(Context context) {
        String userMsg = this.mmkv.decodeString(SharedPreManager.KEY_USER_INFO, "");
        if (!TextUtils.isEmpty(userMsg)) {
            ILog.d("userMsg", userMsg + "--");
            Gson gson = new Gson();
            UserInfo userModel = (UserInfo) gson.fromJson(userMsg, UserInfo.class);
            return userModel;
        }
        return null;
    }
}
