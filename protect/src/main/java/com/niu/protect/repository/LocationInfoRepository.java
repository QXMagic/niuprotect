package com.niu.protect.repository;

import android.util.Log;

import com.niu.protect.core.Constants;
import com.niu.protect.model.UploadLocationInfo;
import com.niu.protect.network.NetTools;
import com.niu.protect.network.ResultCallBackListener;
import com.niu.protect.network.StudentBaseUrl;
import com.niu.protect.tools.ILog;
import com.niu.protect.tools.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


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
        String token = Tools.getToken(Constants.MainInstance.getContext());
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
        Log.i(Constants.MESSAGE_LOCAL, alist.toString());
        NetTools.getInstance().postAsynJSONHttp(Constants.MainInstance.getContext(), StudentBaseUrl.traceGatherRecords_saveGatherRecords, object2, new ResultCallBackListener() {
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
