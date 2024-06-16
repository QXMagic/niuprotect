package com.niuniu.babyprotect.repository;

import android.content.Context;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.niuniu.babyprotect.network.NetTools;
import com.niuniu.babyprotect.network.ResultCallBackListener;
import com.niuniu.babyprotect.network.StudentBaseUrl;
import com.niuniu.babyprotect.tools.AppInfoUtils;
import java.util.HashMap;
import java.util.Map;
import org.android.agoo.message.MessageService;
public class MainRepository extends BaseRepository {
    public static MainRepository instance;

    private MainRepository() {
    }

    public static MainRepository getInstance() {
        if (instance == null) {
            synchronized (MainRepository.class) {
                if (instance == null) {
                    instance = new MainRepository();
                }
            }
        }
        return instance;
    }

    public void bindNet(Context _context, String msg, ResultCallBackListener resultCallBackListener) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("memberId", msg);
        NetTools.getInstance().postAsynHttp(_context, StudentBaseUrl.students_qrCodeBind, parameters, resultCallBackListener);
    }

    public void loadgy(Context context, ResultCallBackListener resultCallBackListener) {
        Map<String, String> parameters = new HashMap<>();
        NetTools.getInstance().getAsynHttp(context, StudentBaseUrl.inspirationalMottos_random, parameters, resultCallBackListener);
    }

    public void getSystemConfig(Context context, ResultCallBackListener resultCallBackListener) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("appDownChannel", AppInfoUtils.getChannel(context));
        parameters.put("appType", MessageService.MSG_DB_READY_REPORT);
        parameters.put("versionCode", AppInfoUtils.getVersionCode(context) + "");
        NetTools.getInstance().getAsynHttp(context, StudentBaseUrl.SYSTEM_CONFIG, parameters, resultCallBackListener);
    }

    public void updataApp(Context context, ResultCallBackListener resultCallBackListener) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("type", "android_version");
        parameters.put(DispatchConstants.PLATFORM, "xiaomi");
        parameters.put("service", "student");
        NetTools.getInstance().getAsynHttp(context, StudentBaseUrl.appversion_info, parameters, resultCallBackListener);
    }

    public void getAutoSettingContent(Context context, ResultCallBackListener resultCallBackListener) {
        Map<String, String> parameters = new HashMap<>();
        NetTools.getInstance().getAsynHttp(context, StudentBaseUrl.auto_setting_content, parameters, resultCallBackListener);
    }
}
