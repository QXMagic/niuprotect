package com.niuniu.babyprotect.ui.main;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.niuniu.babyprotect.adapter.UseAppAdapter;
import com.niuniu.babyprotect.model.AppInfo;
import com.niuniu.babyprotect.model.UsePackageInfo;
import com.niuniu.babyprotect.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import im.niu.protect.R;
public class UseAppActivity extends BaseActivity {
    private static final int MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS = 1101;
    List<AppInfo> alist;
    ListView listview;
    UseAppAdapter useAppAdapter;
    List<UsePackageInfo> appList = new ArrayList();
    List<UsePackageInfo> showList = new ArrayList();
    List<UsePackageInfo> desList = new ArrayList();
    List<UsePackageInfo> writeList = new ArrayList();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_app);
        changeTitle("app使用时间");
        showBack();
        this.alist = getAppList(this);
        getHigherPackageName();
        this.listview = (ListView) findViewById(R.id.listview);
        UseAppAdapter useAppAdapter = new UseAppAdapter(this, this.desList);
        this.useAppAdapter = useAppAdapter;
        this.listview.setAdapter((ListAdapter) useAppAdapter);
        TextView gjTime = (TextView) findViewById(R.id.gjTime);
        gjTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("android.settings.USAGE_ACCESS_SETTINGS"));
            }
        });
    }

    private synchronized void getHigherPackageName() {
        //TODO decode
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
//Method not decompiled: com.niuniu.babyprotect.ui.main.UseAppActivity.getHigherPackageName():void");
    }

    public boolean checkApp(java.lang.String r4) {
        //TODO decode
//本方法所在的代码反编译失败，请在反编译界面按照提示打开jeb编译器，找到当前对应的类的相应方法，替换到这里，然后进行适当的代码修复工作

return true;//这行代码是为了保证方法体完整性额外添加的，请按照上面的方法补充完善代码

//throw new UnsupportedOperationException(
//Method not decompiled: com.niuniu.babyprotect.ui.main.UseAppActivity.checkApp(java.lang.String):boolean");
    }

    public static List<AppInfo> getAppList(Context context) {
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

    private String getLowerVersionPackageName() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName topActivity = activityManager.getRunningTasks(1).get(0).topActivity;
        String topPackageName = topActivity.getPackageName();
        return topPackageName;
    }

    private boolean hasPermission() {
        AppOpsManager appOpsM = (AppOpsManager) getSystemService("appops");
        int mode = 0;
        if (Build.VERSION.SDK_INT > 19) {
            mode = appOpsM.checkOpNoThrow("android:get_usage_stats", Process.myUid(), getPackageName());
        }
        return mode == 0;
    }
}
