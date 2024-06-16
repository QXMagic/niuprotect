package com.niuniu.babyprotect.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.niuniu.babyprotect.model.TempOutControlModel;
import com.niuniu.babyprotect.network.NetTools;
import com.niuniu.babyprotect.network.ResultCallBackListener;
import com.niuniu.babyprotect.network.StudentBaseUrl;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.SharedPreUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
public class TempOutControlManager {
    private static final String TAG = "TempOutControlManager";
    private static TempOutControlManager instance;
    private TempOutControlModel.DataDTO mTempOut;

    private TempOutControlManager() {
    }

    public static TempOutControlManager getInstance() {
        if (instance == null) {
            synchronized (TempOutControlManager.class) {
                if (instance == null) {
                    instance = new TempOutControlManager();
                }
            }
        }
        return instance;
    }

    public boolean getTempOutTime(Context context) {
        if (this.mTempOut == null) {
            this.mTempOut = getTempOutControlData(context);
        }
        if (this.mTempOut == null) {
            return false;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String strStarttime = this.mTempOut.getStartTime();
        String strEndTime = this.mTempOut.getEndTime();
        if (TextUtils.isEmpty(strStarttime) || TextUtils.isEmpty(strEndTime)) {
            return false;
        }
        try {
            long startTime = simpleDateFormat.parse(strStarttime).getTime();
            long endTime = simpleDateFormat.parse(strEndTime).getTime();
            long currentTime = System.currentTimeMillis();
            ILog.d(TAG, "startTime:" + startTime);
            ILog.d(TAG, "endTime:" + endTime);
            ILog.d(TAG, "currentTime:" + currentTime);
            return currentTime >= startTime && currentTime <= endTime;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void requestOutControl(final Context context) {
        Map<String, String> parameters = new HashMap<>();
        NetTools.getInstance().getAsynHttp(context, StudentBaseUrl.GET_TEMPORARY_UNBINDINFO, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    ILog.d("white app", msg.toString());
                    SharedPreUtil.setParam(context, SharedPreManager.KEY_TEMP_OUT_CONTROL, msg.toString());
                    mTempOut = null;
                }
            }
        });
    }

    private TempOutControlModel.DataDTO getTempOutControlData(Context context) {
        TempOutControlModel mSystemWhiteAppModel;
        SharedPreferences sp = context.getSharedPreferences(SharedPreManager.SP_NAME, 0);
        String userMsg = sp.getString(SharedPreManager.KEY_TEMP_OUT_CONTROL, "");
        ILog.d(TAG, userMsg);
        if (!TextUtils.isEmpty(userMsg) && (mSystemWhiteAppModel = (TempOutControlModel) new Gson().fromJson(userMsg, TempOutControlModel.class)) != null) {
            return mSystemWhiteAppModel.getData();
        }
        return null;
    }
}
