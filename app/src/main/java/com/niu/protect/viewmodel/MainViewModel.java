package com.niu.protect.viewmodel;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.niu.protect.BabyApplication;
import com.niu.protect.network.ResultCallBackListener;
import com.niu.protect.repository.MainRepository;
import com.niu.protect.tools.ILog;
import org.json.JSONObject;
public class MainViewModel extends ViewModel {
    private MainRepository mainRepository;
    Context mContext = BabyApplication.getInstance();
    private MutableLiveData<JSONObject> updateAppJson = new MutableLiveData<>();
    private MutableLiveData<JSONObject> bindNetInfo = new MutableLiveData<>();
    private MutableLiveData<JSONObject> loadgyInfo = new MutableLiveData<>();
    private MutableLiveData<JSONObject> systemConfig = new MutableLiveData<>();

    public MainViewModel(MainRepository mainRepository) {
        this.mainRepository = mainRepository;
    }

    public MutableLiveData<JSONObject> getBindNetInfo() {
        return this.bindNetInfo;
    }

    public MutableLiveData<JSONObject> getUpdateAppJson() {
        return this.updateAppJson;
    }

    public MutableLiveData<JSONObject> getLoadgyInfo() {
        return this.loadgyInfo;
    }

    public MutableLiveData<JSONObject> getSystemConfig() {
        return this.systemConfig;
    }

    public void checkUpdateApp() {
        this.mainRepository.updataApp(this.mContext, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    updateAppJson.postValue(msg);
                }
            }
        });
    }

    public void requestBindNet(String msg) {
        this.mainRepository.bindNet(this.mContext, msg, msg2 -> {
            if (msg2 != null) {
                bindNetInfo.postValue(msg2);
            } else {
                ILog.d("--requestBindNet----", "requestBindNet null");
            }
        });
    }

    public void requestLoadgy() {
        this.mainRepository.loadgy(this.mContext, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    loadgyInfo.postValue(msg);
                }
            }
        });
    }

    public void requestSystemConfig() {
        this.mainRepository.getSystemConfig(this.mContext, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    systemConfig.postValue(msg);
                }
            }
        });
    }
}
