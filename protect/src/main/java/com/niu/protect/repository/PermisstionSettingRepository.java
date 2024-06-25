package com.niu.protect.repository;

import android.content.Context;
import com.google.gson.Gson;
import com.niu.protect.manager.OnRequstResultCallBack;
import com.niu.protect.manager.SharedPreManager;
import com.niu.protect.manager.UserInfoManager;
import com.niu.protect.model.PermisstionStepBean;
import com.niu.protect.model.UserInfo;
import com.niu.protect.network.NetTools;
import com.niu.protect.network.ResultCallBackListener;
import com.niu.protect.network.StudentBaseUrl;
import com.niu.protect.tools.ILog;
import com.niu.protect.tools.SharedPreUtil;
import com.niu.protect.tools.secret.Base64Utils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
public class PermisstionSettingRepository extends BaseRepository {
    public static PermisstionSettingRepository instance;

    private PermisstionSettingRepository() {
    }

    public static PermisstionSettingRepository getInstance() {
        if (instance == null) {
            synchronized (PermisstionSettingRepository.class) {
                if (instance == null) {
                    instance = new PermisstionSettingRepository();
                }
            }
        }
        return instance;
    }

    public void requestTeacherModel(final Context context, final OnRequstResultCallBack requestCallBack) {
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(context);
        if (!userInfo.isBindTeacher() || userInfo == null) {
            return;
        }
        Map<String, String> parameters = new HashMap<>();
        NetTools.getInstance().getAsynHttp(context, StudentBaseUrl.patternTeachers_querybyteacherid, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    ILog.log(msg);
                    try {
                        msg.getJSONObject("data").getJSONArray("patternTeacherSlots");
                        SharedPreUtil.setParam(context, SharedPreManager.KEY_CONTROL_TEACHER, msg.toString());
                        if (requestCallBack != null) {
                            requestCallBack.requestTeacherResult();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void setPermissionStep(Context context, List<PermisstionStepBean> permisstionStepBeanList, OnRequstResultCallBack requestCallBack) {
        Map<String, String> parameters = new HashMap<>();
        String jsonData = new Gson().toJson(permisstionStepBeanList);
        ILog.d("jsonData", jsonData);
        String requestData = Base64Utils.encodeToString(jsonData).replace("\n", "");
        parameters.put("permissionData", requestData);
        NetTools.getInstance().postAsynHttp(context, StudentBaseUrl.PERMISSION_SETTING, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
            }
        });
    }

    public void setPermissionResult(Context context, OnRequstResultCallBack requestCallBack) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("permissionResult", "1");
        NetTools.getInstance().postAsynHttp(context, StudentBaseUrl.PERMISSION_RESULT, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
            }
        });
    }
}
