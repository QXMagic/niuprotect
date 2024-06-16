package com.niuniu.babyprotect.repository;

import android.util.Log;
import com.niuniu.babyprotect.BabyApplication;
import com.niuniu.babyprotect.model.UploadLocationInfo;
import com.niuniu.babyprotect.network.NetTools;
import com.niuniu.babyprotect.network.ResultCallBackListener;
import com.niuniu.babyprotect.network.StudentBaseUrl;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.Tools;
import java.util.List;
import org.android.agoo.common.AgooConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class LocationInfoRepository extends BaseRepository {
    public static LocationInfoRepository instance;

    public interface UploadCallBack {
        void uploadFinish();
    }

    private LocationInfoRepository() {
    }

    public static LocationInfoRepository getInstance() {
        if (instance == null) {
            synchronized (LocationInfoRepository.class) {
                if (instance == null) {
                    instance = new LocationInfoRepository();
                }
            }
        }
        return instance;
    }

    public void uploadLoacationInfo(List<UploadLocationInfo> infos, final UploadCallBack callBack) {
        String token = Tools.getToken(BabyApplication.getInstance());
        if (token == null) {
            return;
        }
        JSONArray alist = new JSONArray();
        for (UploadLocationInfo latLngInfo : infos) {
            JSONObject object = new JSONObject();
            try {
                object.put("latitude", latLngInfo.getLatitude());
                object.put("longitude", latLngInfo.getLongitude());
                object.put("timeStamp", latLngInfo.getTimeStamp());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            alist.put(object);
        }
        JSONObject object2 = new JSONObject();
        try {
            object2.put("content", alist);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        Log.i(AgooConstants.MESSAGE_LOCAL, alist.toString());
        NetTools.getInstance().postAsynJSONHttp(BabyApplication.getInstance(), StudentBaseUrl.traceGatherRecords_saveGatherRecords, object2, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    ILog.d("location", "upload success");
                }
                UploadCallBack uploadCallBack = callBack;
                if (uploadCallBack != null) {
                    uploadCallBack.uploadFinish();
                }
            }
        });
    }
}
