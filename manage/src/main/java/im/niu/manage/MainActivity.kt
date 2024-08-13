package im.niu.manage

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import im.niu.corelib.accessibility.AccessibilitySettingHelper
import im.niu.corelib.service.KeepLiveJobService
import im.niu.corelib.service.MainIntentService
import im.niu.corelib.utils.ILog

class MainActivity : AppCompatActivity() {
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private var permissionList = arrayOf("android.permission.ACCESS_COARSE_LOCATION",
        "android.permission.ACCESS_FINE_LOCATION","android.permission.FOREGROUND_SERVICE_LOCATION")
    private val Tag = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        var settingsFragment:SettingsFragment
        if (savedInstanceState == null) {
            settingsFragment = SettingsFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, settingsFragment)
                .commit()
        }else{
            settingsFragment = supportFragmentManager.findFragmentById(R.id.settings) as SettingsFragment
        }
        settingsFragment.setMain(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        requestPermissionLauncher =
//            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
//                if (isGranted) {
//                    ILog.d(Tag,"granted")
//                    checkPermission()
//                } else {
//                }
//            }
//        checkPermission()
        XXPermissions.with(this)
            // 申请单个权限
            .permission(Permission.RECORD_AUDIO)
            // 申请多个权限
//            .permission(Permission.ACCESS_BACKGROUND_LOCATION)
//            .permission(Permission.ACCESS_FINE_LOCATION)
//            .permission(Permission.ACCESS_COARSE_LOCATION)
            //这三个必须在一起单独申请
            .permission(Permission.READ_PHONE_STATE)
            .permission(Permission.PACKAGE_USAGE_STATS)
            .permission(Permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
            .permission(Permission.SYSTEM_ALERT_WINDOW)

            // 设置权限请求拦截器（局部设置）
            //.interceptor(new PermissionInterceptor())
            // 设置不触发错误检测机制（局部设置）
            //.unchecked()
            .request(object : OnPermissionCallback {

                override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                    if (!allGranted) {
                        ILog.d(Tag,"部分权限被拒绝")
                        return
                    }
                    ILog.d(Tag,"获取录音和日历权限成功")
                    checkPermission()
                }

                override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                    if (doNotAskAgain) {
                        ILog.d(Tag,"被永久拒绝授权，请手动授予录音和日历权限")
                        // 如果是被永久拒绝就跳转到应用权限系统设置页面
                        XXPermissions.startPermissionActivity(applicationContext, permissions)
                    } else {
                        ILog.d(Tag,"获取录音和日历权限失败")
                    }
                }
            })
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            for (permission in permissionList) {
                if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    ILog.d(Tag,"{$permission} not granted")
                    requestPermissionLauncher.launch(permission)
                    return
                }
            }
        }
        ILog.d(Tag,"all permission is granted")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForegroundService(Intent(this, MainIntentService::class.java))
        } else {
            startService(Intent(this, MainIntentService::class.java))
        }
        KeepLiveJobService.startJob(this)
        if(!AccessibilitySettingHelper.isAccessibilitySettingsOnByService(this)){
            AccessibilitySettingHelper.openAccessibility(this)
        }else {
            finish()
        }
    }

    class SettingsFragment() : PreferenceFragmentCompat() {
        var mainActivity: MainActivity? = null

        fun setMain(mainActivity:MainActivity) {
            this.mainActivity = mainActivity
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            val preference = findPreference<androidx.preference.Preference>("sync")
            preference?.setOnPreferenceClickListener {
                mainActivity?.checkPermission()
                true
            }
        }
    }
}