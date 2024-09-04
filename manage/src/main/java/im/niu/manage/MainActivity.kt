package im.niu.manage

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Surface
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.preference.PreferenceFragmentCompat
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import im.niu.corelib.accessibility.AccessibilitySettingHelper
import im.niu.corelib.service.KeepLiveJobService
import im.niu.corelib.service.MainIntentService
import im.niu.corelib.utils.ILog
import org.w3c.dom.Text

class MainActivity : ComponentActivity() {
//    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private var permissionList = arrayOf(
        "android.permission.ACCESS_COARSE_LOCATION",
        "android.permission.ACCESS_FINE_LOCATION",
        "android.permission.FOREGROUND_SERVICE_LOCATION")
    private val Tag = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme { // 注意：这里会根据你创建的项目名而不同
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android") // ①
                }
            }
        }
        XXPermissions.with(this)
            // 申请单个权限
            .permission(Permission.RECORD_AUDIO)
//            .permission(Permission.GET_INSTALLED_APPS)
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
                    for (permission in permissions){
                        var rs = XXPermissions.isGranted(this@MainActivity,permission)
                        ILog.d(Tag,"permission $permission is $rs")
                    }
                    if (!allGranted) {
                        ILog.d(Tag,"部分权限被拒绝")
                        return
                    }
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
        XXPermissions.with(this).permission(permissionList).request(object : OnPermissionCallback {
            override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                if (!allGranted) {
                    ILog.d(Tag,"部分权限被拒绝")
                    Toast.makeText(this@MainActivity, "部分权限被拒绝", Toast.LENGTH_LONG).show()
                    return
                }
            }
            override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                if (doNotAskAgain) {
                    ILog.d(Tag,"被永久拒绝授权，请手动授予位置权限")
                    // 如果是被永久拒绝就跳转到应用权限系统设置页面
                    XXPermissions.startPermissionActivity(applicationContext, permissions)
                } else {
                    ILog.d(Tag,"获取录音和日历权限失败")
                }
            }
        })
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

    @Composable
    fun MessageCard(name: String) {
        Text(text = "Hello $name")
    }
}