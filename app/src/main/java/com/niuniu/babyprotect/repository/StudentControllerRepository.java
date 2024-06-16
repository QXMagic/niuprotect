package com.niuniu.babyprotect.repository;

import android.content.Context;
import android.util.Log;
import com.niuniu.babyprotect.manager.OnRequstResultCallBack;
import com.niuniu.babyprotect.manager.SharedPreManager;
import com.niuniu.babyprotect.manager.UserInfoManager;
import com.niuniu.babyprotect.model.UserInfo;
import com.niuniu.babyprotect.network.NetTools;
import com.niuniu.babyprotect.network.ResultCallBackListener;
import com.niuniu.babyprotect.network.StudentBaseUrl;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.SharedPreUtil;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
public class StudentControllerRepository extends BaseRepository {
    public static StudentControllerRepository instance;

    private StudentControllerRepository() {
    }

    public static StudentControllerRepository getInstance() {
        if (instance == null) {
            synchronized (StudentControllerRepository.class) {
                if (instance == null) {
                    instance = new StudentControllerRepository();
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

    public void requestParentHolidayModel(final Context context, final OnRequstResultCallBack requestCallBack) {
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(context);
        if (userInfo == null) {
            return;
        }
        Map<String, String> parameters = new HashMap<>();
        parameters.put("studentId", userInfo.getId());
        parameters.put("type", "2");
        NetTools.getInstance().getAsynHttp(context, StudentBaseUrl.patternParents_query, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    ILog.log(msg);
                    try {
                        msg.getJSONObject("data").getJSONArray("patternParentSlots");
                        boolean success = SharedPreUtil.setParam(context, SharedPreManager.KEY_PARENT_HOLIDAY, msg.toString());
                        ILog.d("setParam", success + "KEY_PARENT_HOLIDAY");
                        if (requestCallBack != null) {
                            requestCallBack.requestParentHolidayResult();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void requestParentSchoolModel(final Context context, final OnRequstResultCallBack requestCallBack) {
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(context);
        if (userInfo == null) {
            return;
        }
        Map<String, String> parameters = new HashMap<>();
        parameters.put("studentId", userInfo.getId());
        parameters.put("type", "1");
        NetTools.getInstance().getAsynHttp(context, StudentBaseUrl.patternParents_query, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    ILog.log(msg);
                    try {
                        msg.getJSONObject("data").getJSONArray("patternParentSlots");
                        boolean success = SharedPreUtil.setParam(context, SharedPreManager.KEY_PARENT_SCHOOL, msg.toString());
                        ILog.d("setParam", success + "KEY_PARENT_SCHOOL");
                        if (requestCallBack != null) {
                            requestCallBack.requestParentSchoolResult();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void requestOtherTimeModel(final Context context, final OnRequstResultCallBack requestCallBack) {
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(context);
        if (userInfo == null) {
            return;
        }
        Map<String, String> parameters = new HashMap<>();
        parameters.put("studentId", userInfo.getId());
        NetTools.getInstance().getAsynHttp(context, StudentBaseUrl.applicationPrograms_general, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    Log.e("xaaa", msg.toString());
                    ILog.log(msg);
                    try {
                        msg.getString("data");
                        boolean success = SharedPreUtil.setParam(context, SharedPreManager.KEY_OTHER_LIMIT_TIME, msg.toString());
                        ILog.d("setParam", success + "KEY_OTHER_LIMIT_TIME");
                        if (requestCallBack != null) {
                            requestCallBack.requestOtherTimeResult();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
