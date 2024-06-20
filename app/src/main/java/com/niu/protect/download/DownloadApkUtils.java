package com.niu.protect.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import androidx.core.content.FileProvider;
import com.niu.protect.network.StudentBaseUrl;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class DownloadApkUtils {
    private static long downloadId = 0;
    private static DownloadApkUtils instance;
    private static File saveFile;
    public Context mContext;
    DownloadManager manager;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkStatus();
        }
    };

    private DownloadApkUtils() {
    }

    public static synchronized DownloadApkUtils getInstance() {
        DownloadApkUtils downloadApkUtils;
        synchronized (DownloadApkUtils.class) {
            if (instance == null) {
                instance = new DownloadApkUtils();
            }
            downloadApkUtils = instance;
        }
        return downloadApkUtils;
    }

    public void startDownload(Context mContext, String url, String title) {
        initFile(url);
        long j = downloadId;
        if (j != 0) {
            clearCurrentTask(mContext, j);
        }
        downloadId = downLoadApk(mContext, url, title);
    }

    private void initFile(String url) {
        Pattern pat = Pattern.compile("\\w+\\.apk");
        Matcher mc = pat.matcher(url);
        String fileName = null;
        while (mc.find()) {
            fileName = mc.group();
        }
        if (fileName == null) {
            fileName = "aaa.apk";
        }
        saveFile = new File(StudentBaseUrl.baseFile, fileName);
    }

    public long downLoadApk(Context context, String url, String title) {
        this.mContext = context;
        context.registerReceiver(this.receiver, new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
        this.manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        if (Build.VERSION.SDK_INT >= 24) {
            StrictMode.VmPolicy.Builder localBuilder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(localBuilder.build());
        }
        DownloadManager.Request requestApk = new DownloadManager.Request(uri);
        requestApk.setAllowedNetworkTypes(2);
        requestApk.setNotificationVisibility(1);
        if (saveFile.exists()) {
            saveFile.delete();
        }
        requestApk.setDestinationUri(Uri.fromFile(saveFile));
        requestApk.allowScanningByMediaScanner();
        requestApk.setTitle(title);
        if (Build.VERSION.SDK_INT >= 24) {
            requestApk.setRequiresDeviceIdle(false);
            requestApk.setRequiresCharging(false);
        }
        long downLoadId = this.manager.enqueue(requestApk);
        return downLoadId;
    }

    public void clearCurrentTask(Context mContext, long downloadId2) {
        DownloadManager dm = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        try {
            dm.remove(downloadId2);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }

    public void installApk(Context context) {
        downloadId = 0L;
        Intent intent = new Intent("android.intent.action.VIEW");
        try {
            String[] command = {"chmod", "777", saveFile.getAbsolutePath()};
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.start();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.niu.protect.fileprovider", saveFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(saveFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public void checkStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor cursor = this.manager.query(query);
        if (cursor.moveToFirst()) {
            int st = cursor.getColumnIndex("status");
            int status = cursor.getInt(st);
            if (status == 8) {
                installApk(this.mContext);
                cursor.close();
            } else if (status == 16) {
                cursor.close();
                this.mContext.unregisterReceiver(this.receiver);
            }
        }
    }
}
