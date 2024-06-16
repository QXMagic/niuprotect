package com.niuniu.babyprotect.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import org.json.JSONException;
import org.json.JSONObject;
public class UpdateDialog {
    public static void checkUpdate(JSONObject msg, final Context context) {
        try {
            JSONObject data = msg.getJSONObject("data");
            int newversion = data.getInt("version");
            final String durl = data.getString("updataurl");
            String remark = data.getString("remark");
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int version = info.versionCode;
            boolean isNew = false;
            if (version < newversion) {
                isNew = true;
            }
            if (isNew) {
                new AlertDialog.Builder(context).setTitle("有新版本更新").setMessage(remark).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        UpdateDialog.openUrl(context, durl);
                    }
                }).setNegativeButton("取消", (DialogInterface.OnClickListener) null).show();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public static void openUrl(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);
    }
}
