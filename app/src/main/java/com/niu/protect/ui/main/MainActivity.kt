package com.niu.protect.ui.main

import android.app.usage.UsageStatsManager
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.PowerManager
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.hjq.permissions.Permission
import com.niu.protect.BabyApplication.Companion.instance
import com.niu.protect.R
import com.niu.protect.accessibility.OpenAccessibilitySettingHelper.Companion.isAccessibilitySettingsOn
import com.niu.protect.accessibility.OpenAccessibilitySettingHelper.Companion.isAccessibilitySettingsOnByService
import com.niu.protect.accessibility.StatusUseAccessibilityService
import com.niu.protect.core.Constants
import com.niu.protect.core.MessageEvent
import com.niu.protect.databinding.ActivityMainBinding
import com.niu.protect.lib.receiver.BroadcastManager
import com.niu.protect.manager.AutoSettingManager
import com.niu.protect.manager.SharedPreManager
import com.niu.protect.manager.StudentMainController
import com.niu.protect.manager.UserInfoManager
import com.niu.protect.manager.WebSocketManager
import com.niu.protect.model.AppInfo
import com.niu.protect.model.SystemConfigModel
import com.niu.protect.model.UserInfo
import com.niu.protect.model.eventbus.EventParentChangeBindMode
import com.niu.protect.network.NetTools
import com.niu.protect.network.ResultCallBackListener
import com.niu.protect.network.StudentBaseUrl
import com.niu.protect.service.UploadAppWorker
import com.niu.protect.service.UploadAppWorker.Companion.startActionBaz
import com.niu.protect.tools.ILog
import com.niu.protect.tools.QRCodeUtil
import com.niu.protect.tools.RomUtil
import com.niu.protect.tools.SharedPreUtil
import com.niu.protect.tools.ToastUtil
import com.niu.protect.tools.Tools
import com.niu.protect.ui.base.BaseActivity
import com.niu.protect.homework.HomeworkActivity
import com.niu.protect.ui.mine.MineActivity
import com.niu.protect.ui.setting.OpenQxActivity
import com.niu.protect.viewmodel.MainViewModel
import com.niu.protect.viewmodel.MainViewModelFactory
import com.niu.protect.widget.UpdateDialog
import com.niu.protect.work.MineWorkerManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.Date


class MainActivity : BaseActivity() {
    private var binding: ActivityMainBinding? = null
    private var mUserInfo: UserInfo? = null
    private var mainViewModel: MainViewModel? = null
    private var systemConfigModel: SystemConfigModel? = null
    private val wmParams: WindowManager.LayoutParams? = null
    private val inflater: LayoutInflater? = null
    private var PermissionString = arrayOf(Permission.ACCESS_BACKGROUND_LOCATION)
    private var isLoaded = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate = ActivityMainBinding.inflate(
            layoutInflater
        )
        binding = inflate
        setContentView(inflate.root)
        mainViewModel = ViewModelProvider(this, MainViewModelFactory()).get(
            MainViewModel::class.java
        )
        //bind events
        initUi()
        //net work 回调
        initObserve()
        mUserInfo = UserInfoManager.getInstance().getUserInfo(this)
        WebSocketManager.instance.start()
        mainRunning = true
        uploadAllAPP()
        requestAppListPermission()
    }

    /** 管控自检：缺关键权限/开关时用常驻通知引导开启（切走也可见，比启动期弹窗可靠） */
    private fun checkMmSetup() {
        val missing = com.niu.protect.mm.MmSetupCheck.missing(this)
        ILog.d(TAG, "checkMmSetup missing=${missing.size}: ${missing.joinToString { it.key }}")
        val nm = getSystemService(android.content.Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
        if (missing.isEmpty()) {
            nm.cancel(MM_SETUP_NOTI_ID)
            return
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val ch = android.app.NotificationChannel(
                MM_SETUP_CHANNEL, "管控状态", android.app.NotificationManager.IMPORTANCE_HIGH
            )
            nm.createNotificationChannel(ch)
        }
        val top = missing.first()
        val titles = missing.joinToString("、") { it.title }
        val pi = android.app.PendingIntent.getActivity(
            this, 0, top.intent,
            android.app.PendingIntent.FLAG_IMMUTABLE or android.app.PendingIntent.FLAG_UPDATE_CURRENT
        )
        val noti = androidx.core.app.NotificationCompat.Builder(this, MM_SETUP_CHANNEL)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("管控未完全生效")
            .setContentText("点击开启：$titles")
            .setStyle(androidx.core.app.NotificationCompat.BigTextStyle().bigText("【${top.title}】${top.desc}\n仍需开启：$titles"))
            .setPriority(androidx.core.app.NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pi)
            .build()
        try { nm.notify(MM_SETUP_NOTI_ID, noti) } catch (e: Exception) { ILog.d(TAG, "mm setup noti error: ${e.message}") }
    }

    /** 请求"读取应用列表"权限：国产 ROM(ColorOS/MIUI 等)靠此权限才能枚举已装应用做管控 */
    private fun requestAppListPermission() {
        val perm = "com.android.permission.GET_INSTALLED_APPS"
        try {
            if (androidx.core.content.ContextCompat.checkSelfPermission(this, perm)
                != android.content.pm.PackageManager.PERMISSION_GRANTED
            ) {
                androidx.core.app.ActivityCompat.requestPermissions(this, arrayOf(perm), 0x51)
            }
        } catch (e: Exception) {
            ILog.d(TAG, "request app-list permission error: ${e.message}")
        }
    }

    fun uploadAllAPP() {
        val isUploadAPP =
            SharedPreUtil.getParam(instance, SharedPreManager.KEY_APP_UPLOAD_APPS, false) as Boolean
        if (!isUploadAPP) {
            startActionBaz(this)
        }
    }

    private fun checkPermissionOfAutoSetting() {
        val permission =
            isAccessibilitySettingsOn(this, StatusUseAccessibilityService::class.java.name)

        val autoFinished = AutoSettingManager.isSettingFinish
        val accessSuccess = permission || isAccessibilitySettingsOnByService(this)
        ILog.d(TAG, "is access ability on ?$permission,running? $accessSuccess,is auto setting finished $autoFinished")
        if (accessSuccess && !autoFinished) {
            ILog.d(TAG, "go to setting")
            enterPermissionSetting()
        } else {
            checkPermission()
        }
    }

    private fun initObserve() {
        mainViewModel!!.updateAppJson.observe(this) { jsonObject: JSONObject? ->
            if (jsonObject != null) {
                UpdateDialog.checkUpdate(jsonObject, _context)
            }
        }
        mainViewModel!!.bindNetInfo.observe(this) { jsonObject: JSONObject? ->
            if (jsonObject != null) {
                Toast.makeText(_context, "绑定成功", Toast.LENGTH_LONG).show()
                uploadAllAPP()
            }
        }
        mainViewModel!!.loadgyInfo.observe(this) { jsonObject: JSONObject? ->
            if (jsonObject != null) {
                try {
                    val data = jsonObject.getJSONObject("data").getString("content")
                    //励志格言
                    val gytxt = findViewById<View>(R.id.gytxt) as TextView
                    gytxt.text = data
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
        mainViewModel!!.systemConfig.observe(this) { jsonObject: JSONObject? ->
            if (jsonObject != null) {
                systemConfigModel = Gson().fromJson(
                    jsonObject.toString(),
                    SystemConfigModel::class.java
                ) as SystemConfigModel
            }
        }
        mainViewModel!!.requestLoadgy()
        mainViewModel!!.requestSystemConfig()
    }

    private fun initUi() {
        binding!!.scanbtn.setOnClickListener { bindQRCode() }
        binding!!.clockbtn.setOnClickListener {
            try {
                val intent = Intent("android.intent.action.SHOW_ALARMS")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        binding!!.userHead.setOnClickListener {
            val intent = Intent()
            intent.setClass(_context, MineActivity::class.java)
            _context.startActivity(intent)
        }
        binding!!.mybtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(_context, MineActivity::class.java)
            _context.startActivity(intent)
        }
        binding!!.homeworkBtn.setOnClickListener {
            val intent = Intent()
            intent.setClass(_context, HomeworkActivity::class.java)
            _context.startActivity(intent)
        }
        binding!!.goTeacherBtn.setOnClickListener {
            val userInfo = UserInfoManager.getInstance().getUserInfo(_context)
            if (!userInfo.isBindTeacher) {
                bindQRCode()
                return@setOnClickListener
            }
            ToastUtil.show("已绑定")
            StudentMainController.getInstance().requestMainControl()
        }
        binding!!.goParentBtn.setOnClickListener {
            val userInfo = UserInfoManager.getInstance().getUserInfo(_context)
            if (!userInfo.isBindParent) {
                bindQRCode()
                return@setOnClickListener
            }
            ToastUtil.show("已绑定")
            StudentMainController.getInstance().requestMainControl()
        }
        binding!!.userDesBtn.setOnClickListener {
            if (mUserInfo == null) {
                showToast("数据加载失败,请稍后")
                return@setOnClickListener
            }
            val mainActivity = this@MainActivity
            UserDesActivity.enterUserDesActivity(mainActivity, mainActivity.mUserInfo)
        }
        binding!!.staAppBtn.setOnClickListener {
            if (mUserInfo == null) {
                showToast("数据加载失败,请稍后")
                return@setOnClickListener
            }
            val mainActivity = this@MainActivity
            StaActivity.enterStaActivity(mainActivity, mainActivity.mUserInfo)
        }
    }

    /**
     * 进入权限设置
     * */
    private fun enterPermissionSetting() {
        val userInfo = UserInfoManager.getInstance().getUserInfo(this)
        if (userInfo.isBindTeacher || userInfo.isBindParent) {
            val isHw = RomUtil.isHuawei
            if (RomUtil.isOppo || RomUtil.isVivo || isHw || RomUtil.isMiui) {
                ILog.d(TAG, "enter permission setting $isHw")
                val intent = Intent()
                intent.setClass(_context, OpenQxActivity::class.java)
                _context.startActivity(intent)
            } else {
                ILog.d(TAG, "not support device ,can't enter permission setting")
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
            moveTaskToBack(true)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun bindQRCode() {
        QRCodeUtil.bindQRCode(this) { result ->
            val resultContent = result.getContent()
            if (!TextUtils.isEmpty(resultContent)) {
                mainViewModel!!.requestBindNet(result.getContent())
            } else {
                ToastUtil.show("扫码失败")
            }
        }
    }

    /**
     * 获取完用户信息之后
     **/
    private fun refUi(uInfo: UserInfo?) {
        var userInfo = uInfo
        if (userInfo == null) {
            userInfo = UserInfoManager.getInstance().getUserInfo(this)
        }
        if (userInfo == null) {
            ILog.d(Constants.KEY_USER_ID, "userInfo is null")
            return
        }
        var userName = userInfo.name
        if (TextUtils.isEmpty(userName)) {
            userName = userInfo.mobile
        }
        binding!!.username.text = userName
        if (!userInfo.isBindTeacher) {
            binding!!.bingttxt.text = "未绑定老师"
        } else {
            binding!!.bingttxt.text = "已绑定老师"
        }
        if (!userInfo.isBindParent) {
            binding!!.bingptxt.text = "未绑定家长"
        } else {
            binding!!.bingptxt.text = "已绑定家长"
        }

    }



    private fun checkPermission() {
        ILog.d(TAG, "check permission")
        var targetSdkVersion = 0
        try {
            val info = packageManager.getPackageInfo(packageName, 0)
            targetSdkVersion = info.applicationInfo?.targetSdkVersion ?: 0
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            ILog.d(TAG, "check permission error")
        }
        if (targetSdkVersion >= 23) {
            val isAllGranted = checkPermissionAllGranted(PermissionString)
            if (isAllGranted) {
                Log.e(NotificationCompat.CATEGORY_ERROR, "所有权限已经授权！")
            } else {
                ActivityCompat.requestPermissions(this, PermissionString, 1)
            }
        }
    }

    private fun checkPermissionAllGranted(permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != 0) {
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            var isAllGranted = true
            for (grant in grantResults){
                if(grant!=0){
                    isAllGranted = false
                    break;
                }
            }
            if (isAllGranted) {
                Log.e(TAG, "权限都授权了")
            }else{

            }
        }
    }

    /**
     * 每次切换屏幕都会走一次
     *  */
    private fun getUserInfo() {
        isLoaded = true
        val userInfo = UserInfoManager.getInstance().getUserInfo(this) ?: return
        val parameters: MutableMap<String, String> = HashMap()
        parameters["studentId"] = userInfo.id
        NetTools.instance
            .getAsynHttp(this, StudentBaseUrl.members_getMemberInfo, parameters) { msg ->
                if (msg != null) {
                    ILog.log("getUserInfo --$msg")
                    try {
                        val data = msg.getString("data")
                        mUserInfo = Gson().fromJson(data, UserInfo::class.java) as UserInfo
                        UserInfoManager.getInstance().saveUser(_context, data)
                        refUi(mUserInfo)
                        if (userInfo.isBindTeacher || userInfo.isBindParent) {
                            checkPermissionOfAutoSetting()
                            requestControlInfo()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }
        binding!!.username.text = userInfo.name
    }

    private fun requestControlInfo() {
        val isSettingOn =
            isAccessibilitySettingsOn(this, StatusUseAccessibilityService::class.java.name)
        val isFirstLoad = AutoSettingManager.isSettingFinish
        ILog.d(TAG, "is setting on? $isSettingOn,is setting finished? $isFirstLoad")
        if (!isSettingOn || !isFirstLoad) {
            ILog.d(TAG, "Controller is not On, Over")
            return
        }
        binding!!.username.postDelayed({
            ILog.d(TAG, "start requestControlInfo---------")
            StudentMainController.getInstance().requstWeekControlTime(this@MainActivity)
            StudentMainController.getInstance().getUserWhiteApp()
            StudentMainController.getInstance().getXcxControl()
        }, 10000L)
        MineWorkerManager.getInstance().periodicWorkRequest(instance)
    }

    public override fun onResume() {
        super.onResume()
        ILog.d(TAG, "---onResume-----")
        checkMmSetup()
        if(isLoaded){
            return
        }
        getUserInfo()
    }

    override fun onPause() {
        super.onPause()
        ILog.d(TAG,"---on Pause---")
    }

    private val isNoOption: Boolean
        get() {
            val packageManager = applicationContext.packageManager
            val intent = Intent("android.settings.USAGE_ACCESS_SETTINGS")
            val list =
                packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
            return list.size > 0
        }
    private val isNoSwitch: Boolean
        get() {
            val ts = System.currentTimeMillis()
            val usageStatsManager =
                applicationContext.getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager
            val queryUsageStats = usageStatsManager.queryUsageStats(4, 0L, ts)
            return !(queryUsageStats == null || queryUsageStats.isEmpty())
        }

    fun download(url: String?) {
//        val myserviceIntent = Intent(this, DownloadWorker::class.java)
//        myserviceIntent.data = Uri.parse(url)
//        startService(myserviceIntent)
        val data = Data.Builder().putString("url", url).build()
        val workRequest =
            OneTimeWorkRequest.Builder(UploadAppWorker::class.java).setInputData(data).build()
        WorkManager.getInstance(_context).enqueue(workRequest)
    }

    private val isIgnoringBatteryOptimizations: Boolean
        get() {
            val powerManager =
                getSystemService(POWER_SERVICE) as PowerManager
            return powerManager.isIgnoringBatteryOptimizations(packageName)
        }

    fun requestIgnoreBatteryOptimizations() {
        try {
            val intent = Intent("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS")
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun loadApp() {
        val userInfo = UserInfoManager.getInstance().getUserInfo(this)
        val parameters: MutableMap<String, String> = HashMap()
        parameters["studentId"] = userInfo.id
        showLoad()
        NetTools.instance
            .getAsynHttp(this, StudentBaseUrl.parents_statistic, parameters){ msg ->
                dissLoad()
                if (msg != null) {
                    ILog.log(msg)
                    try {
                        val data = msg.getJSONObject("data")
                        val duration = data.getInt("duration")
                        val stepNumber = data.getInt("stepNumber")
                        var appInfo = AppInfo()
                        if (Tools.objIsNullStr(data["useMostApp"])) {
                            val gson = Gson()
                            appInfo = gson.fromJson(
                                data.getJSONObject("useMostApp").toString(),
                                AppInfo::class.java
                            ) as AppInfo
                        }
                        refAppView(duration, stepNumber, appInfo)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Tools.showAlert3(this@MainActivity, e.message)
                    }
                }
            }
    }

    private fun refAppView(duration: Int, stepNumber: Int, appInfo: AppInfo) {
        val textView = binding!!.appTimeTxt
        textView.text = "今日已经使用了" + duration / 60 + "分钟"
        if (Tools.objIsNullStr(appInfo.appName)) {
            binding!!.appMoreTxt.text = "暂无使用APP"
        } else {
            val textView2 = binding!!.appMoreTxt
            textView2.text = "使用最多的APP " + appInfo.appName
        }
        val textView3 = binding!!.appStepTxt
        textView3.text = "步数$stepNumber"
    }

    fun loadappmsg() {
        val userInfo = UserInfoManager.getInstance().getUserInfo(this)
        val parameters: MutableMap<String, String> = HashMap()
        parameters["studentId"] = userInfo.id
        parameters["recordDate"] = Tools.timeFormat(Date(), "yyyy-MM-dd")
        showLoad()
        NetTools.instance.getAsynHttp(
            this,
            StudentBaseUrl.appUseRecords_appuserecord,
            parameters,
            ResultCallBackListener { msg ->
                dissLoad()
                if (msg != null) {
                    ILog.log(msg)
                    try {
                        val data = msg.getJSONArray("data")
                        if (data.length() == 0) {
                            val mlist = JSONObject()
                            mlist.put("useTime", "今天暂无使用APP")
                            mlist.put("appList", JSONArray())
                            refAppview(mlist)
                        }
                        for (i in 0 until data.length()) {
                            val `object` = data.getJSONObject(i)
                            val userTime = `object`.getString("useTime")
                            val applist = `object`.getJSONArray("appList")
                            val alist = JSONArray()
                            for (j in 0 until applist.length()) {
                                val gson = Gson()
                                val appInfo = gson.fromJson(
                                    applist[j].toString(),
                                    AppInfo::class.java
                                ) as AppInfo
                                alist.put(appInfo)
                            }
                            val mlist2 = JSONObject()
                            mlist2.put("useTime", userTime)
                            mlist2.put("appList", alist)
                            if (i == 0) {
                                refAppview(mlist2)
                                return@ResultCallBackListener
                            }
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            })
    }

    private fun refAppview(mlist: JSONObject) {
        val convertView = findViewById<View>(R.id.userDesBtn) as LinearLayout
        try {
            var userTime = mlist.getString("useTime")
            val alist = mlist.getJSONArray("appList")
            val timelal = convertView.findViewById<View>(R.id.timelal) as TextView
            timelal.text = userTime
            for (i in 0..2) {
                val appview =
                    convertView.findViewWithTag<View>(((i + 1) * 1000).toString() + "") as LinearLayout
                if (i < alist.length()) {
                    appview.visibility = View.VISIBLE
                } else {
                    appview.visibility = View.GONE
                }
            }
            var j = 0
            while (j < alist.length() && j <= 2) {
                val tag = (j + 1) * 1000
                convertView.findViewWithTag<View>(tag.toString() + "") as LinearLayout
                val appInfo = alist[j] as AppInfo
                val img1 = convertView.findViewWithTag<View>((tag + 1).toString() + "") as ImageView
                val txt1 = convertView.findViewWithTag<View>((tag + 2).toString() + "") as TextView
                val txt2 = convertView.findViewWithTag<View>((tag + 3).toString() + "") as TextView
                val probar =
                    convertView.findViewWithTag<View>((tag + 4).toString() + "") as ProgressBar
                val userTime2 = userTime
                Glide.with((this as FragmentActivity)).asBitmap()
                    .load(StudentBaseUrl.BASE_URL + appInfo.appImage).into(img1)
                probar.max = 1440
                probar.progress = appInfo.useTime
                var timeStr = ""
                val hour = appInfo.useTime / 60
                val min = appInfo.useTime % 60
                if (hour > 0) {
                    timeStr = hour.toString() + "时"
                }
                txt1.text = appInfo.appName
                txt2.text = timeStr + min + "分"
                j++
                userTime = userTime2
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: EventParentChangeBindMode?) {
        ILog.d(TAG, "---EventParentChangeBindMode-----")
        refUi(null)
        Toast.makeText(instance, "收到新的指令", Toast.LENGTH_LONG).show()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent?) {
        ILog.d(TAG, "---MessageEvent-----")
        download("")
    }

    public override fun onDestroy() {
        ILog.d(TAG, "---onDestroy-----")
        super.onDestroy()
        BroadcastManager.sendAccessibilityStop(this)
        WebSocketManager.instance.onDestroy()
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val MM_SETUP_CHANNEL = "mm_setup"
        private const val MM_SETUP_NOTI_ID = 0x515E
        var mainRunning = false
    }
}