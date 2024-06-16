package com.niuniu.babyprotect.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.niuniu.babyprotect.BabyApplication;
import com.niuniu.babyprotect.manager.UploadAppManager;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.image.ImageSave;
public class UploadAppIntentService extends IntentService {
    private static final String ACTION_BAZ = "com.niuniu.babyprotect.service.action.BAZ";
    private static final String ACTION_FOO = "com.niuniu.babyprotect.service.action.FOO";
    private static final String EXTRA_PARAM1 = "com.niuniu.babyprotect.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.niuniu.babyprotect.service.extra.PARAM2";

    public UploadAppIntentService() {
        super("InitStartIntentService");
    }

    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, UploadAppIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        init();
    }

    public void init() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ImageSave.delFile();
        UploadAppManager.getInstance(BabyApplication.getInstance()).GetInstallAppList();
    }

    private void handleActionFoo(String param1, String param2) {
    }

    private void handleActionBaz(String param1, String param2) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ILog.d("InitStartIntentService", "ondestroy");
    }
}
