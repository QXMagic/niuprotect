package com.niuniu.babyprotect.download;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;
public class DownloadUtils {
    private long downloadId;
    private DownloadManager downloadManager;
    private Context mContext;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkStatus();
        }
    };

    public DownloadUtils(Context context) {
        this.mContext = context;
    }

    public void downloadAPK(String url, String name) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedOverRoaming(false);
        request.setNotificationVisibility(0);
        request.setTitle("新版本Apk");
        request.setDescription("Apk Downloading");
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(Environment.getExternalStorageDirectory().getAbsolutePath(), name);
        DownloadManager downloadManager = (DownloadManager) this.mContext.getSystemService("download");
        this.downloadManager = downloadManager;
        this.downloadId = downloadManager.enqueue(request);
        this.mContext.registerReceiver(this.receiver, new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
    }

    public void checkStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(this.downloadId);
        Cursor c = this.downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex("status"));
            if (status == 8) {
                installAPK();
            } else if (status == 16) {
                Toast.makeText(this.mContext, "下载失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void installAPK() {
        Uri downloadFileUri = this.downloadManager.getUriForDownloadedFile(this.downloadId);
        if (downloadFileUri != null) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.mContext.startActivity(intent);
        }
    }
}
