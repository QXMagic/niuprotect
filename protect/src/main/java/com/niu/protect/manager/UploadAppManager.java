package com.niu.protect.manager;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.niu.protect.core.Constants;
import com.niu.protect.model.AppInfo;
import com.niu.protect.network.NetTools;
import com.niu.protect.network.ResultCallBackListener;
import com.niu.protect.network.StudentBaseUrl;
import com.niu.protect.repository.ApkInfoRepository;
import com.niu.protect.tools.ILog;
import com.niu.protect.tools.RomUtil;
import com.niu.protect.tools.SystemUtil;
import com.niu.protect.tools.Tools;
import com.niu.protect.tools.apk.ApkTools;
import com.niu.protect.tools.secret.Base64Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class UploadAppManager {
    private static UploadAppManager instance = null;
    private Context context;
    private String remenberPackageName;

    private UploadAppManager(Context context) {
        this.context = context;
    }

    public static UploadAppManager getInstance(Context context) {
        if (instance == null) {
            instance = new UploadAppManager(context);
        }
        return instance;
    }

    public boolean GetInstallAppList() {
        List<AppInfo> appInfos = new ArrayList<>();
        UserInstallWhiteAppListManager whiteAppListManager = UserInstallWhiteAppListManager.getInstance().initWhiteApps();
        PackageManager pm = this.context.getPackageManager();
        Intent mainIntent = new Intent("android.intent.action.MAIN", (Uri) null);
        mainIntent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> activities = pm.queryIntentActivities(mainIntent, 0);
        ILog.d("app list","actives size is "+activities.size());
        for (ResolveInfo info : activities) {
            String packName = info.activityInfo.packageName;
            if (!packName.equals(this.context.getPackageName()) && !packName.contains("launcher") && !packName.contains("com.google.android") && !packName.contains("com.google.server") && !packName.contains(Constants.APPLICATION_ID)) {
                ActivityInfo actInfos = info.activityInfo;
                String label = (String) actInfos.loadLabel(pm);
                if (TextUtils.isEmpty(label)) {
                    label = (String) info.loadLabel(pm);
                }
                ILog.d("-AppName--", packName);
                if (!whiteAppListManager.contains(label)) {
                    whiteAppListManager.addInstallApps(label);
                    AppInfo mInfo = new AppInfo();
                    mInfo.setDefault(info.isDefault);
                    mInfo.setIco(getFullResIcon(info));
                    mInfo.setAppName(label);
                    mInfo.setPackageName(packName);
                    mInfo.setType(2);
                    appInfos.add(mInfo);
                }
            }
        }
        whiteAppListManager.saveOldApps();
        if (appInfos.size() == 0) {
            ILog.d("app manager","app list size is zero!!");
            return false;
        }
        upAppList(appInfos, true);
        ILog.d("upNewInstallAPP", "上传图标");
        Tools.saveAppPageList(this.context, new Gson().toJson(appInfos));
        ApkInfoRepository.getInstance().uploadAppIcons(appInfos);
        return true;
    }

    public boolean GetInstallAppList2() {
        List<AppInfo> appInfos = new ArrayList<>();
        UserInstallWhiteAppListManager whiteAppListManager = UserInstallWhiteAppListManager.getInstance().initWhiteApps();
        PackageManager pm = this.context.getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (PackageInfo packageInfo : packages) {
            if ((1 & packageInfo.applicationInfo.flags) <= 0) {
                ILog.d("--packageInfo-", packageInfo.packageName);
                try {
                    getAPPinfo(appInfos, whiteAppListManager, pm, packageInfo);
                } catch (Exception e) {
                    ILog.d("getAPP Exception", "获取应用报错");
                }
            }
        }
        whiteAppListManager.saveOldApps();
        if (appInfos.size() <= 0) {
            return false;
        }
        upAppList(appInfos, true);
        Tools.saveAppPageList(this.context, new Gson().toJson(appInfos));
        ApkInfoRepository.getInstance().uploadAppIconsCurrentTask(appInfos);
        return true;
    }

    private void getAPPinfo(List<AppInfo> appInfos, UserInstallWhiteAppListManager whiteAppListManager, PackageManager pm, PackageInfo info) {
        String packName = info.packageName;
        if (packName.equals(this.context.getPackageName()) || packName.contains("com.android")) {
            return;
        }
        String appName = "";
        Drawable icon = info.applicationInfo.loadIcon(pm);
        CharSequence appNameChar = info.applicationInfo.loadLabel(pm);
        if (appNameChar != null) {
            appName = appNameChar.toString();
        }
        if (appName == "") {
            return;
        }
        ILog.d("-AppName--", appName + "");
        if (whiteAppListManager.contains(appName)) {
            return;
        }
        whiteAppListManager.addInstallApps(appName);
        AppInfo mInfo = new AppInfo();
        mInfo.setIco(icon);
        mInfo.setAppName(appName);
        mInfo.setPackageName(packName);
        mInfo.setType(2);
        appInfos.add(mInfo);
    }

    public Drawable getFullResIcon(ResolveInfo info) {
        return getFullResIcon(info.activityInfo);
    }

    public Drawable getFullResIcon(ActivityInfo info) {
        Resources resources;
        int iconId;
        try {
            resources = this.context.getPackageManager().getResourcesForApplication(info.applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            resources = null;
        }
        if (resources != null && (iconId = info.getIconResource()) != 0) {
            return getFullResIcon(resources, iconId);
        }
        return getFullResDefaultActivityIcon();
    }

    public Drawable getFullResDefaultActivityIcon() {
        return getFullResIcon(Resources.getSystem(), 17629184);
    }

    public Drawable getFullResIcon(Resources resources, int iconId) {
        Drawable d;
        try {
            ActivityManager activityManager = (ActivityManager) this.context.getSystemService(Context.ACTIVITY_SERVICE);
            int iconDpi = activityManager.getLauncherLargeIconDensity();
            d = resources.getDrawableForDensity(iconId, iconDpi);
        } catch (Resources.NotFoundException e) {
            d = null;
        }
        return d != null ? d : getFullResDefaultActivityIcon();
    }

    public void checkDel(List<AppInfo> nlist) {
        JSONArray delarray = new JSONArray();
        List<String> olist = Tools.getAppPageList(this.context);
        for (String pagename : olist) {
            boolean isok = false;
            Iterator<AppInfo> it = nlist.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                AppInfo appInfo = it.next();
                if (pagename.equals(appInfo.getPackageName())) {
                    isok = true;
                    break;
                }
            }
            if (!isok) {
                delarray.put(pagename);
            }
        }
        if (delarray.length() > 0) {
            try {
                JSONObject object = new JSONObject();
                object.put(Constants.KEY_PACKAGE_NAMES, delarray);
                NetTools.getInstance().postAsynJSONHttp(this.context, StudentBaseUrl.apps_batchdelete, object, new ResultCallBackListener() {
                    @Override
                    public void onResponse(JSONObject msg) {
                        if (msg != null) {
                            Log.i("baby", msg.toString());
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public List<PackageInfo> getAllApp() {
        PackageManager pm = this.context.getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        List<PackageInfo> backpackages = new ArrayList<>();
        for (PackageInfo packageInfo : packages) {
            if ((packageInfo.applicationInfo.flags & 1) == 0) {
                backpackages.add(packageInfo);
            } else if (Tools.checkOkApp(packageInfo, this.context)) {
                Log.i("xxx", "add===" + packageInfo.packageName);
                backpackages.add(packageInfo);
            }
        }
        return backpackages;
    }

    public void upAppList(List<AppInfo> alist, boolean isFirstTime) {
        String url;
        String token = Tools.getToken(this.context);
        if (token == null) {
            return;
        }
        JSONArray jlist = new JSONArray();
        for (AppInfo packageInfo : alist) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("image", "");
                obj.put("name", packageInfo.getAppName());
                obj.put(Constants.KEY_PACKAGE_NAME, packageInfo.getPackageName());
                obj.put("type", packageInfo.getType());
                if (SystemUtil.checkPhone().equals("xiaomi")) {
                    obj.put("brand", 1);
                } else if (SystemUtil.checkPhone().equals("huawei")) {
                    obj.put("brand", 2);
                } else if (RomUtil.isOppo()) {
                    obj.put("brand", 3);
                } else if (RomUtil.isVivo()) {
                    obj.put("brand", 4);
                } else {
                    obj.put("brand", 5);
                }
                jlist.put(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String listData = Base64Utils.encodeToString(jlist.toString()).replace("\n", "");
        Log.i("baby", listData);
        Map<String, String> parameters = new HashMap<>();
        Log.i("baby", Base64Utils.decodeToString(listData));
        parameters.put("listData", listData);
        if (isFirstTime) {
            url = StudentBaseUrl.apps_batchAddApp_first;
        } else {
            url = StudentBaseUrl.apps_batchAddApp;
        }
        NetTools.getInstance().postAsynHttp(this.context, url, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    Log.i("baby", msg.toString());
                }
            }
        });
    }

    public boolean upNewInstallAPP(String packageName) {
        AppInfo info;
        if (TextUtils.isEmpty(packageName) || packageName.equals(this.remenberPackageName)) {
            return false;
        }
        this.remenberPackageName = packageName;
        if (packageName.startsWith("com.android") || packageName.startsWith("com.oppo") || packageName.contains("coloros") || packageName.startsWith("com.bbk") || packageName.startsWith("com.vivo") || packageName.startsWith("com.huawei") || packageName.startsWith("com.hihonor") || packageName.contains("launcher") || packageName.contains("com.oplus")) {
            return false;
        }
        UserInstallWhiteAppListManager.getInstance().initWhiteApps();
        if (UserInstallWhiteAppListManager.getInstance().oldAppsUplaod() && (info = ApkTools.getAPPInfoByPackageName(this.context, packageName)) != null) {
            boolean isUpLoad = UserInstallWhiteAppListManager.getInstance().containsUpload(info.getAppName());
            ILog.d("upNewInstallAPP", "isUpLoad:" + isUpLoad + "--getAppName：--" + info.getAppName());
            if (!isUpLoad) {
                List<AppInfo> appInfos = new ArrayList<>();
                appInfos.add(info);
                upAppList(appInfos, false);
                ILog.d("upNewInstallAPP", "上传图标");
                ApkInfoRepository.getInstance().uploadAppIcons(appInfos);
                UserInstallWhiteAppListManager.getInstance().addInstallApps(info.getAppName());
                UserInstallWhiteAppListManager.getInstance().saveOldApps();
            }
            return !isUpLoad;
        }
        return false;
    }
}
