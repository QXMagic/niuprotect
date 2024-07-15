package com.niu.protect.network;

import com.google.gson.Gson;
import com.niu.protect.core.Constants;
import com.niu.protect.model.BaseModel;
import com.niu.protect.tools.ILog;

import org.json.JSONObject;

import java.util.Map;
public class BabyRequestManager {

    public interface CallBack<T extends BaseModel> {
        void onError(T t);

        void onSuceess(T t);
    }

    public static <T extends BaseModel> void requst(String url, Map<String, String> parameters, final Class<T> tClass, final CallBack callBack) {
        NetTools.getInstance().getAsynHttp(Constants.MainInstance.getContext(), url, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    BaseModel baseModel = (BaseModel) new Gson().fromJson(msg.toString(), tClass);
                    if (callBack != null) {
                        if (baseModel.status.equals("200")) {
                            callBack.onSuceess(baseModel);
                            return;
                        }
                        baseModel.status = "-1";
                        callBack.onError(baseModel);
                        return;
                    }
                    ILog.d("BabyRequestManager", "callback is Null");
                }
            }
        });
    }
}
