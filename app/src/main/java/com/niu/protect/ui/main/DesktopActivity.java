package com.niu.protect.ui.main;

import android.app.ActivityManager;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.viewpager.widget.ViewPager;

import com.niu.protect.R;
import com.niu.protect.adapter.DeskTopGridViewBaseAdapter;
import com.niu.protect.adapter.GridViewAdapter;
import com.niu.protect.adapter.ViewPagerAdapter;
import com.niu.protect.manager.UserProtectManager;
import com.niu.protect.model.AppInfo;
import com.niu.protect.service.FloatingService;
import com.niu.protect.tools.Tools;
import com.niu.protect.ui.base.BaseActivity;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class DesktopActivity extends BaseActivity {
    public static int item_grid_num = 24;
    public static int number_columns = 4;
    Intent floatWinIntent;
    private ViewPagerAdapter mAdapter;
    private ViewPager view_pager;
    private List<GridView> gridList = new ArrayList();
    JSONArray blackApp = new JSONArray();
    List<AppInfo> appInfos = new ArrayList();
    private Intent intent = new Intent("com.niu.protect.floatingService.RECEIVER");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desktop);
        LinearLayout bgview = (LinearLayout) findViewById(R.id.bgview);
        WallpaperManager manager = WallpaperManager.getInstance(this);
        Drawable drawable = manager.getDrawable();
        bgview.setBackground(drawable);
        initViews();
        initAppList();
        Tools.saveDeskTop(this, 1);
        UserProtectManager.getInstance().setProtect(1);
        Intent intent = new Intent(this, FloatingService.class);
        this.floatWinIntent = intent;
        startService(intent);
        TextView outbtn = (TextView) findViewById(R.id.outbtn);
        outbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tools.saveDeskTop(_context, 0);
                UserProtectManager.getInstance().setProtect(-2);
                intent.putExtra(NotificationCompat.CATEGORY_PROGRESS, 1);
                DesktopActivity desktopActivity = DesktopActivity.this;
                desktopActivity.sendBroadcast(desktopActivity.intent);
                Intent iii = new Intent(_context, MainActivity.class);
                startActivity(iii);
                finish();
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    checkBlackApp();
                    try {
                        Thread.sleep(5000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void initViews() {
        this.view_pager = (ViewPager) findViewById(R.id.view_pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter();
        this.mAdapter = viewPagerAdapter;
        this.view_pager.setAdapter(viewPagerAdapter);
    }

    public void checkBlackApp() {
        this.blackApp = Tools.getBlackApp(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(this.floatWinIntent);
    }

    @Override
    public void onPause() {
        super.onPause();
        Tools.saveDeskTop(this, 2);
    }

    @Override
    public void onStart() {
        super.onStart();
        Tools.saveDeskTop(this, 1);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Tools.saveDeskTop(this, 1);
    }

    private void initAppList() {
        int pageSize;
        this.appInfos.addAll(GetAppList1(this));
        new DeskTopGridViewBaseAdapter(this.appInfos, this);
        if (this.appInfos.size() % item_grid_num == 0) {
            pageSize = this.appInfos.size() / item_grid_num;
        } else {
            pageSize = (this.appInfos.size() / item_grid_num) + 1;
        }
        for (int i = 0; i < pageSize; i++) {
            GridView gridView = new GridView(this);
            GridViewAdapter adapter = new GridViewAdapter(this.appInfos, i, this);
            gridView.setNumColumns(number_columns);
            gridView.setAdapter((ListAdapter) adapter);
            gridView.setOnItemClickListener(adapter);
            this.gridList.add(gridView);
        }
        this.mAdapter.add(this.gridList);
    }

    public List<AppInfo> GetAppList1(Context context) {
        List<AppInfo> list = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        Intent mainIntent = new Intent("android.intent.action.MAIN", (Uri) null);
        mainIntent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> activities = pm.queryIntentActivities(mainIntent, 0);
        for (ResolveInfo info : activities) {
            String packName = info.activityInfo.packageName;
            if (!packName.equals(context.getPackageName())) {
                AppInfo mInfo = new AppInfo();
                mInfo.setDefault(info.isDefault);
                mInfo.setIco(getFullResIcon(info));
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

    public Drawable getFullResDefaultActivityIcon() {
        return getFullResIcon(Resources.getSystem(), 17629184);
    }

    public Drawable getFullResIcon(Resources resources, int iconId) {
        Drawable d;
        try {
            ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            int iconDpi = activityManager.getLauncherLargeIconDensity();
            d = resources.getDrawableForDensity(iconId, iconDpi);
        } catch (Resources.NotFoundException e) {
            d = null;
        }
        return d != null ? d : getFullResDefaultActivityIcon();
    }

    public Drawable getFullResIcon(String packageName, int iconId) {
        Resources resources;
        try {
            resources = getPackageManager().getResourcesForApplication(packageName);
        } catch (PackageManager.NameNotFoundException e) {
            resources = null;
        }
        if (resources != null && iconId != 0) {
            return getFullResIcon(resources, iconId);
        }
        return getFullResDefaultActivityIcon();
    }

    public Drawable getFullResIcon(ResolveInfo info) {
        return getFullResIcon(info.activityInfo);
    }

    public Drawable getFullResIcon(ActivityInfo info) {
        Resources resources;
        int iconId;
        try {
            resources = getPackageManager().getResourcesForApplication(info.applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            resources = null;
        }
        if (resources != null && (iconId = info.getIconResource()) != 0) {
            return getFullResIcon(resources, iconId);
        }
        return getFullResDefaultActivityIcon();
    }

    private Drawable getAppIcon(ResolveInfo info) {
        return getFullResIcon(info.activityInfo);
    }
}
