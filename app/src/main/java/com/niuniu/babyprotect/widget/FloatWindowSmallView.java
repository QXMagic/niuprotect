package com.niuniu.babyprotect.widget;

import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.lifecycle.CoroutineLiveDataKt;
import im.niu.protect.R;
import com.niuniu.babyprotect.adapter.DeskTopGridViewBaseAdapter;
import com.niuniu.babyprotect.model.AppInfo;
import com.niuniu.babyprotect.tools.Tools;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
public class FloatWindowSmallView extends LinearLayout {
    Context _context;
    List<AppInfo> appInfos;
    JSONArray blackApp;
    private Intent intent;
    GridView mGridView;
    LinearLayout mySelf;

    public FloatWindowSmallView(Context context) {
        super(context);
        this.blackApp = new JSONArray();
        this.appInfos = new ArrayList();
        this.intent = new Intent("com.yiou.floatingService.RECEIVER");
        this.mySelf = this;
        this._context = context;
        LayoutInflater.from(context).inflate(R.layout.activity_desktop, this);
        LinearLayout bgview = (LinearLayout) findViewById(R.id.bgview);
        WallpaperManager manager = WallpaperManager.getInstance(context);
        Drawable drawable = manager.getDrawable();
        bgview.setBackground(drawable);
        initAppList();
        Tools.saveDeskTop(context, 1);
        TextView outbtn = (TextView) findViewById(R.id.outbtn);
        outbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tools.saveDeskTop(_context, 0);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    checkBlackApp();
                    try {
                        Thread.sleep(CoroutineLiveDataKt.DEFAULT_TIMEOUT);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void checkBlackApp() {
        this.blackApp = Tools.getBlackApp(this._context);
    }

    private void initAppList() {
        this.appInfos.addAll(GetAppList1(this._context));
        DeskTopGridViewBaseAdapter deskTopGridViewBaseAdapter = new DeskTopGridViewBaseAdapter(this.appInfos, this._context);
        this.mGridView.setAdapter((ListAdapter) deskTopGridViewBaseAdapter);
        this.mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pagename;
                AppInfo appInfo = appInfos.get(position);
                JSONArray array = Tools.getBlackApp(_context);
                for (int i = 0; i < array.length(); i++) {
                    try {
                        pagename = array.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (!appInfo.getPackageName().equals(pagename)) {
                        continue;
                    } else {
                        AlertDialog.Builder title = new AlertDialog.Builder(_context).setTitle("提示");
                        title.setMessage(appInfo.getName() + " 已经被禁用").setPositiveButton("确定", (DialogInterface.OnClickListener) null).show();
                        return;
                    }
                }
                Intent intent = _context.getPackageManager().getLaunchIntentForPackage(appInfo.getPackageName());
                if (intent != null) {
                    intent.putExtra("type", "110");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    _context.startActivity(intent);
                    mySelf.setVisibility(View.GONE);
                }
            }
        });
    }

    public static List<AppInfo> GetAppList1(Context context) {
        List<AppInfo> list = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        Intent mainIntent = new Intent("android.intent.action.MAIN", (Uri) null);
        mainIntent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> activities = pm.queryIntentActivities(mainIntent, 0);
        for (ResolveInfo info : activities) {
            String packName = info.activityInfo.packageName;
            if (!packName.equals(context.getPackageName())) {
                AppInfo mInfo = new AppInfo();
                mInfo.setIco(info.activityInfo.applicationInfo.loadIcon(pm));
                mInfo.setName(info.activityInfo.applicationInfo.loadLabel(pm).toString());
                mInfo.setPackageName(packName);
                Intent launchIntent = new Intent();
                launchIntent.setComponent(new ComponentName(packName, info.activityInfo.name));
                mInfo.setIntent(launchIntent);
                list.add(mInfo);
            }
        }
        return list;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4 && event.getAction() == 0) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
