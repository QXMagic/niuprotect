package com.niu.protect.download;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
public class DownloadService extends IntentService {
    public static final String BROADCAST_ACTION = "com.example.android.threadsample.BROADCAST";
    public static final String EXTENDED_DATA_STATUS = "com.example.android.threadsample.STATUS";
    private String TAG;
    private LocalBroadcastManager mLocalBroadcastManager;

    public DownloadService() {
        super("DownloadService");
        this.TAG = "DownloadService";
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String url = intent.getDataString();
        Log.i(this.TAG, url);
        DownloadManager downloadManager = (DownloadManager) getSystemService("download");
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "mydown.app");
        request.setAllowedNetworkTypes(2);
        request.setNotificationVisibility(0);
        request.setTitle("下载");
        request.setDescription("应用正在下载");
        request.setAllowedOverRoaming(false);
        long requestId = downloadManager.enqueue(request);
        Intent localIntent = new Intent(BROADCAST_ACTION);
        localIntent.putExtra(EXTENDED_DATA_STATUS, requestId);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(requestId);
        boolean isGoging = true;
        while (isGoging) {
            try {
                Cursor cursor = downloadManager.query(query);
                if (cursor != null && cursor.moveToFirst()) {
                    int status = cursor.getInt(cursor.getColumnIndex("status"));
                    if (status == 8) {
                        isGoging = false;
                        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
                        this.mLocalBroadcastManager = localBroadcastManager;
                        localBroadcastManager.sendBroadcast(localIntent);
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }
}
