package com.niuniu.babyprotect.download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import anetwork.channel.util.RequestConstant;
import java.io.File;
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra(DownloadService.EXTENDED_DATA_STATUS);
        Log.i(RequestConstant.ENV_TEST, data);
        intent.getLongExtra("extra_download_id", -1L);
        Intent intent2 = new Intent("android.intent.action.VIEW");
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent2.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/myApp.apk")), "application/vnd.android.package-archive");
        context.startActivity(intent2);
    }
}
