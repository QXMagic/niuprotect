package im.niu.manage

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.hjq.permissions.XXPermissions
import im.niu.corelib.accessibility.AccessibilitySettingHelper
import im.niu.corelib.manager.UpgradeManager
import im.niu.corelib.service.KeepLiveJobService
import im.niu.corelib.service.MainIntentService
import im.niu.corelib.ui.AccessSettingActivity
import im.niu.corelib.ui.AlertActivity
import im.niu.corelib.ui.PermissionActivity
import im.niu.corelib.utils.ILog
import im.niu.manage.ui.theme.NiuprotectTheme

class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NiuprotectTheme { // 注意：这里会根据你创建的项目名而不同
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {

                }
            }
        }
        ILog.d(TAG, "onCreate")
        val intent = Intent(this, AlertActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        ILog.d(TAG, "onResume")
        toStart()
    }

    private fun toStart(){
        if (!AccessibilitySettingHelper.isAccessibilitySettingsOnByService(this)) {
            val intent = Intent(this, AccessSettingActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            return
        }
        var granted = true
        var interaptive = false
        PermissionActivity.init(applicationInfo.targetSdkVersion)
        val list = PermissionActivity.permissionItemList
        for (permission in list){
            val ok = XXPermissions.isGranted(this,permission.id)
            interaptive = interaptive||(!ok && permission.must)
            granted = granted && ok
        }
        //没有完成权限
        if(!interaptive){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                startForegroundService(Intent(this, MainIntentService::class.java))
            } else {
                startService(Intent(this, MainIntentService::class.java))
            }
            KeepLiveJobService.startJob(this)
        }
        if(!granted){
            val intent = Intent(this, PermissionActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            return
        }
        finish()
    }

    @Composable
    fun MessageCard(name: String) {
        Text(text = "Hello $name")
    }
}