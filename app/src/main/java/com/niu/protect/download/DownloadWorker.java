package com.niu.protect.download;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class DownloadWorker extends Worker {
    private String TAG;

    public DownloadWorker(Context context, WorkerParameters workerParams) {
        super(context,workerParams);
        this.TAG = DownloadWorker.class.getName();
    }


    @NonNull
    @Override
    public Result doWork() {
        String url = this.getInputData().getString("url");
        Log.i(this.TAG, url);
        DownloadManager downloadManager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDestinationInExternalFilesDir(getApplicationContext(), Environment.DIRECTORY_DOWNLOADS, "mydown.app");
        request.setAllowedNetworkTypes(2);
        request.setNotificationVisibility(0);
        request.setTitle("下载");
        request.setDescription("应用正在下载");
        request.setAllowedOverRoaming(false);
        long requestId = downloadManager.enqueue(request);
//        Intent localIntent = new Intent(BROADCAST_ACTION);
//        localIntent.putExtra(EXTENDED_DATA_STATUS, requestId);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(requestId);
        boolean isGoging = true;
        while (isGoging) {
            try {
                Cursor cursor = downloadManager.query(query);
                if (cursor != null && cursor.moveToFirst()) {
                    int idx = cursor.getColumnIndex("status");
                    int status = cursor.getInt(idx);
                    if (status == 8) {
                        isGoging = false;
//                        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
//                        localBroadcastManager.sendBroadcast(localIntent);
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Result.success();
    }
}
