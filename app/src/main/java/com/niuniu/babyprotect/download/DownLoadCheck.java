package com.niuniu.babyprotect.download;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.niuniu.babyprotect.network.StudentBaseUrl;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class DownLoadCheck {
    String downBreUrl;
    String basePatha = Environment.getExternalStorageDirectory().getAbsolutePath();
    String fileName = null;

    public static void downLoadWebBr(final Context context) {
        boolean isok = checkApkExist(context, StudentBaseUrl.brwPageName);
        if (!isok) {
            new AlertDialog.Builder(context).setTitle("提示").setMessage("是否下载安全浏览器").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    DownloadApkUtils.getInstance().startDownload(context, StudentBaseUrl.brwDownUrl, "安全浏览器");
                }
            }).setNegativeButton("取消", (DialogInterface.OnClickListener) null).show();
        } else {
            Toast.makeText(context, "已经安装安全浏览器.", Toast.LENGTH_SHORT).show();
        }
    }

    private static boolean checkApkExist(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.MATCH_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void downFile(String url) {
        this.downBreUrl = url;
        new Thread(new Runnable() {
            @Override
            public void run() {
                startDown();
            }
        }).start();
    }

    public void startDown() {
        Pattern pat = Pattern.compile("\\w+\\.apk");
        Matcher mc = pat.matcher(this.downBreUrl);
        while (mc.find()) {
            this.fileName = mc.group();
        }
        if (this.fileName == null) {
            this.fileName = "aaa.apk";
        }
        DownloadUtil.get().download(this.downBreUrl, this.basePatha, this.fileName, new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                Log.e("xaxaxa", "ok");
                Message msg = new Message();
                msg.what = 232;
            }

            @Override
            public void onDownloading(int progress) {
            }

            @Override
            public void onDownloadFailed(Exception e) {
            }
        });
    }
}
