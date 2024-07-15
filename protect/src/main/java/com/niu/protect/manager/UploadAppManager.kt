package com.niu.protect.manager

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.niu.protect.core.Constants
import com.niu.protect.model.AppInfo
import com.niu.protect.network.NetTools
import com.niu.protect.network.StudentBaseUrl
import com.niu.protect.repository.ApkInfoRepository
import com.niu.protect.tools.ILog
import com.niu.protect.tools.RomUtil.isOppo
import com.niu.protect.tools.RomUtil.isVivo
import com.niu.protect.tools.SystemUtil
import com.niu.protect.tools.Tools
import com.niu.protect.tools.apk.ApkTools
import com.niu.protect.tools.secret.Base64Utils
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class UploadAppManager private constructor(private val context: Context) {
    private var remenberPackageName: String? = null
    fun GetInstallAppList(): Boolean {
        val appInfos: MutableList<AppInfo> = ArrayList()
        val whiteAppListManager = UserInstallWhiteAppListManager.getInstance().initWhiteApps()
        val pm = context.packageManager
        val mainIntent = Intent("android.intent.action.MAIN", null as Uri?)
        mainIntent.addCategory("android.intent.category.LAUNCHER")
        val activities = pm.queryIntentActivities(mainIntent, 0)
        ILog.d("app list", "actives size is " + activities.size)
        for (info in activities) {
            val packName = info.activityInfo.packageName
            if (packName != context.packageName && !packName.contains("launcher") && !packName.contains(
                    "com.google.android"
                ) && !packName.contains("com.google.server") && !packName.contains(
                    Constants.APPLICATION_ID
                )
            ) {
                val actInfos = info.activityInfo
                var label = actInfos.loadLabel(pm) as String
                if (TextUtils.isEmpty(label)) {
                    label = info.loadLabel(pm) as String
                }
                ILog.d("-AppName--", packName)
                if (!whiteAppListManager.contains(label)) {
                    whiteAppListManager.addInstallApps(label)
                    val mInfo = AppInfo()
                    mInfo.isDefault = info.isDefault
                    mInfo.ico = getFullResIcon(info)
                    mInfo.appName = label
                    mInfo.packageName = packName
                    mInfo.type = 2
                    appInfos.add(mInfo)
                }
            }
        }
        whiteAppListManager.saveOldApps()
        if (appInfos.size == 0) {
            ILog.d("app manager", "app list size is zero!!")
            return false
        }
        upAppList(appInfos, true)
        ILog.d("upNewInstallAPP", "upload icons")
        Tools.saveAppPageList(context, Gson().toJson(appInfos))
        ApkInfoRepository.getInstance().uploadAppIcons(appInfos)
        return true
    }

    fun GetInstallAppList2(): Boolean {
        val appInfos: MutableList<AppInfo> = ArrayList()
        val whiteAppListManager = UserInstallWhiteAppListManager.getInstance().initWhiteApps()
        val pm = context.packageManager
        val packages = pm.getInstalledPackages(0)
        for (packageInfo in packages) {
            if (1 and packageInfo.applicationInfo.flags <= 0) {
                ILog.d("--packageInfo-", packageInfo.packageName)
                try {
                    getAPPinfo(appInfos, whiteAppListManager, pm, packageInfo)
                } catch (e: Exception) {
                    ILog.d("getAPP Exception", e.message)
                }
            }
        }
        whiteAppListManager.saveOldApps()
        if (appInfos.size <= 0) {
            return false
        }
        upAppList(appInfos, true)
        Tools.saveAppPageList(context, Gson().toJson(appInfos))
        ApkInfoRepository.getInstance().uploadAppIconsCurrentTask(appInfos)
        return true
    }

    private fun getAPPinfo(
        appInfos: MutableList<AppInfo>,
        whiteAppListManager: UserInstallWhiteAppListManager,
        pm: PackageManager,
        info: PackageInfo
    ) {
        val packName = info.packageName
        if (packName == context.packageName || packName.contains("com.android")) {
            return
        }
        var appName = ""
        val icon = info.applicationInfo.loadIcon(pm)
        val appNameChar = info.applicationInfo.loadLabel(pm)
        if (appNameChar != null) {
            appName = appNameChar.toString()
        }
        if (appName === "") {
            return
        }
        ILog.d("-AppName--", appName + "")
        if (whiteAppListManager.contains(appName)) {
            return
        }
        whiteAppListManager.addInstallApps(appName)
        val mInfo = AppInfo()
        mInfo.ico = icon
        mInfo.appName = appName
        mInfo.packageName = packName
        mInfo.type = 2
        appInfos.add(mInfo)
    }

    fun getFullResIcon(info: ResolveInfo): Drawable {
        return getFullResIcon(info.activityInfo)
    }

    fun getFullResIcon(info: ActivityInfo): Drawable {
        val resources: Resources?
        var iconId: Int=0
        resources = try {
            context.packageManager.getResourcesForApplication(info.applicationInfo)
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
        return if (resources != null && info.iconResource.also { iconId = it } != 0) {
            getFullResIcon(resources, iconId)
        } else fullResDefaultActivityIcon
    }

    val fullResDefaultActivityIcon: Drawable
        get() = getFullResIcon(Resources.getSystem(), 17629184)

    fun getFullResIcon(resources: Resources, iconId: Int): Drawable {
        val d: Drawable?
        d = try {
            val activityManager =
                context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val iconDpi = activityManager.launcherLargeIconDensity
            resources.getDrawableForDensity(iconId, iconDpi)
        } catch (e: Resources.NotFoundException) {
            null
        }
        return d ?: fullResDefaultActivityIcon
    }

    fun checkDel(nlist: List<AppInfo>) {
        val delarray = JSONArray()
        val olist = Tools.getAppPageList(context)
        for (pagename in olist) {
            var isok = false
            val it = nlist.iterator()
            while (true) {
                if (!it.hasNext()) {
                    break
                }
                val appInfo = it.next()
                if (pagename == appInfo.packageName) {
                    isok = true
                    break
                }
            }
            if (!isok) {
                delarray.put(pagename)
            }
        }
        if (delarray.length() > 0) {
            try {
                val `object` = JSONObject()
                `object`.put(Constants.KEY_PACKAGE_NAMES, delarray)
                NetTools.getInstance()
                    .postAsynJSONHttp(context, StudentBaseUrl.apps_batchdelete, `object`) { msg ->
                        if (msg != null) {
                            Log.i("baby", msg.toString())
                        }
                    }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    val allApp: List<PackageInfo>
        get() {
            val pm = context.packageManager
            val packages = pm.getInstalledPackages(0)
            val backpackages: MutableList<PackageInfo> = ArrayList()
            for (packageInfo in packages) {
                if (packageInfo.applicationInfo.flags and 1 == 0) {
                    backpackages.add(packageInfo)
                } else if (Tools.checkOkApp(packageInfo, context)) {
                    Log.i("xxx", "add===" + packageInfo.packageName)
                    backpackages.add(packageInfo)
                }
            }
            return backpackages
        }

    fun upAppList(alist: List<AppInfo>, isFirstTime: Boolean) {
        val url: String
        val token = Tools.getToken(context) ?: return
        val jlist = JSONArray()
        for (packageInfo in alist) {
            val obj = JSONObject()
            try {
                obj.put("image", "")
                obj.put("name", packageInfo.appName)
                obj.put(Constants.KEY_PACKAGE_NAME, packageInfo.packageName)
                obj.put("type", packageInfo.type)
                if (SystemUtil.checkPhone() == "xiaomi") {
                    obj.put("brand", 1)
                } else if (SystemUtil.checkPhone() == "huawei") {
                    obj.put("brand", 2)
                } else if (isOppo) {
                    obj.put("brand", 3)
                } else if (isVivo) {
                    obj.put("brand", 4)
                } else {
                    obj.put("brand", 5)
                }
                jlist.put(obj)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        val listData = Base64Utils.encodeToString(jlist.toString()).replace("\n", "")
        Log.i("baby", listData)
        val parameters: MutableMap<String, String> = HashMap()
        Log.i("baby", Base64Utils.decodeToString(listData))
        parameters["listData"] = listData
        url = if (isFirstTime) {
            StudentBaseUrl.apps_batchAddApp_first
        } else {
            StudentBaseUrl.apps_batchAddApp
        }
        NetTools.getInstance().postAsynHttp(context, url, parameters) { msg ->
            if (msg != null) {
                Log.i("baby", msg.toString())
            }
        }
    }

    fun upNewInstallAPP(packageName: String): Boolean {
        var info: AppInfo
        if (TextUtils.isEmpty(packageName) || packageName == remenberPackageName) {
            return false
        }
        remenberPackageName = packageName
        if (packageName.startsWith("com.android") || packageName.startsWith("com.oppo") || packageName.contains(
                "coloros"
            ) || packageName.startsWith("com.bbk") || packageName.startsWith("com.vivo") || packageName.startsWith(
                "com.huawei"
            ) || packageName.startsWith("com.hihonor") || packageName.contains("launcher") || packageName.contains(
                "com.oplus"
            )
        ) {
            return false
        }
        UserInstallWhiteAppListManager.getInstance().initWhiteApps()
        info = ApkTools.getAPPInfoByPackageName(context, packageName)
        if (UserInstallWhiteAppListManager.getInstance()
                .oldAppsUplaod() && info!= null) {
            val isUpLoad = UserInstallWhiteAppListManager.getInstance().containsUpload(info.appName)
            ILog.d("upNewInstallAPP", "isUpLoad:" + isUpLoad + "--getAppName：--" + info.appName)
            if (!isUpLoad) {
                val appInfos: MutableList<AppInfo> = ArrayList()
                appInfos.add(info)
                upAppList(appInfos, false)
                ILog.d("upNewInstallAPP", "上传图标")
                ApkInfoRepository.getInstance().uploadAppIcons(appInfos)
                UserInstallWhiteAppListManager.getInstance().addInstallApps(info.appName)
                UserInstallWhiteAppListManager.getInstance().saveOldApps()
            }
            return !isUpLoad
        }
        return false
    }

    companion object {
        private var instance: UploadAppManager? = null
        @JvmStatic
        fun getInstance(context: Context): UploadAppManager? {
            if (instance == null) {
                instance = UploadAppManager(context)
            }
            return instance
        }
    }
}