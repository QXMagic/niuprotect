package com.niuniu.babyprotect.network;

import com.google.gson.Gson;
import com.niuniu.babyprotect.BabyApplication;
import com.niuniu.babyprotect.model.BaseModel;
import com.niuniu.babyprotect.tools.ILog;
import java.util.Map;
import org.json.JSONObject;
public class BabyRequestManager {

    public interface CallBack<T extends BaseModel> {
        void onError(T t);

        void onSuceess(T t);
    }

    public static <T extends BaseModel> void requst(String url, Map<String, String> parameters, final Class<T> tClass, final CallBack callBack) {
        NetTools.getInstance().getAsynHttp(BabyApplication.getInstance(), url, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    BaseModel baseModel = (BaseModel) new Gson().fromJson(msg.toString(), (Class<Object>) tClass);
                    if (callBack != null) {
                        if (baseModel.getStatus().equals("200")) {
                            callBack.onSuceess(baseModel);
                            return;
                        }
                        baseModel.setStatus("-1");
                        callBack.onError(baseModel);
                        return;
                    }
                    ILog.d("BabyRequestManager", "callback is Null");
                }
            }
        });
    }
}
