package com.niuniu.babyprotect.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.niuniu.babyprotect.model.XcxControlModel;
import com.niuniu.babyprotect.network.NetTools;
import com.niuniu.babyprotect.network.ResultCallBackListener;
import com.niuniu.babyprotect.network.StudentBaseUrl;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.SharedPreUtil;
//import com.xiaomi.mipush.sdk.Constants;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
public class XcxControlManager {
    private static final String TAG = "TempOutControlManager";
    private static final String WX_GAME = "com.tencent.mm.plugin.game.ui.chat_tab.GameWebTabUI";
    private static final String WX_GAME2 = "com.tencent.mm.plugin.game.luggage.LuggageGameWebViewUI";
    private static final String WX_PROGRAM = "com.tencent.mm.plugin.appbrand.launching.AppBrandLaunchProxyUI";
    private static XcxControlManager instance;
    private XcxControlModel mXcxControlModel;

    private XcxControlManager() {
    }

    public static XcxControlManager getInstance() {
        if (instance == null) {
            synchronized (XcxControlManager.class) {
                if (instance == null) {
                    instance = new XcxControlManager();
                }
            }
        }
        return instance;
    }

    public boolean checkXcxInControl(Context context, String clasName) {
        if (clasName.equals(WX_PROGRAM) || clasName.equals(WX_GAME) || clasName.equals(WX_GAME2)) {
            if (this.mXcxControlModel == null) {
                this.mXcxControlModel = getXcxControlModel(context);
            }
            XcxControlModel xcxControlModel = this.mXcxControlModel;
            if (xcxControlModel != null && xcxControlModel.getData() != null && this.mXcxControlModel.getData().size() > 0) {
                return checkPatternTimeCanNotUse(this.mXcxControlModel.getData(), clasName);
            }
        }
        return false;
    }

    public void requestSmallProgramlist(final Context context) {
        Map<String, String> parameters = new HashMap<>();
        NetTools.getInstance().getAsynHttp(context, StudentBaseUrl.GET_SMALLPROGRAMLIST, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    ILog.d("xcx app", msg.toString());
                    SharedPreUtil.setParam(context, SharedPreManager.KEY_XCX_CONTROL, msg.toString());
                    mXcxControlModel = null;
                }
            }
        });
    }

    private XcxControlModel getXcxControlModel(Context context) {
        XcxControlModel mXcxControlModel;
        SharedPreferences sp = context.getSharedPreferences(SharedPreManager.SP_NAME, 0);
        String userMsg = sp.getString(SharedPreManager.KEY_XCX_CONTROL, "");
        ILog.d(TAG, userMsg);
        if (!TextUtils.isEmpty(userMsg) && (mXcxControlModel = (XcxControlModel) new Gson().fromJson(userMsg, XcxControlModel.class)) != null) {
            return mXcxControlModel;
        }
        return null;
    }

    private boolean checkPatternTimeCanNotUse(List<XcxControlModel.DataDTO> patternTimeScops, String claName) {
        if (patternTimeScops != null && patternTimeScops.size() > 0) {
            int size = patternTimeScops.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                XcxControlModel.DataDTO xcx = patternTimeScops.get(i);
                if (!xcx.getClassName().equals(claName)) {
                    i++;
                } else if (xcx.getUseStatus() == 1) {
                    return true;
                } else {
                    if (xcx.getUseStatus() != 2 && xcx.getUseStatus() == 3) {
                        Calendar calendar = Calendar.getInstance();
                        int currentHour = calendar.get(11);
                        int currentMinute = calendar.get(12);
                        String startTime = xcx.getStartTime();
                        String endTime = xcx.getEndTime();
                        ILog.d("controltime", "startTime:" + startTime + "-----------endtime:" + endTime);
                        if (checkControlTime(currentHour, currentMinute, startTime, endTime)) {
                            ILog.d("control time", "-------true---");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean checkControlTime(int currentHour, int currentMinute, String startTime, String endTime) {
        if (!TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)) {
            String[] startHours = startTime.split(":");
            int startHour = Integer.valueOf(startHours[0]).intValue();
            int startMinute = Integer.valueOf(startHours[1]).intValue();
            String[] endHours = endTime.split(":");
            int endHour = Integer.valueOf(endHours[0]).intValue();
            int endMinute = Integer.valueOf(endHours[1]).intValue();
            int currentTotalTime = (currentHour * 60) + currentMinute;
            int limitTotalTimeStart = (startHour * 60) + startMinute;
            int limitEndTimes = (endHour * 60) + endMinute;
            ILog.d("limitTime", "currentTime=" + currentTotalTime + "--limitTotalTimeStart:" + limitTotalTimeStart + "---limitEndTimes:" + limitEndTimes);
            return currentTotalTime <= limitEndTimes && currentTotalTime >= limitTotalTimeStart;
        }
        return false;
    }
}
