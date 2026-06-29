package im.niu.corelib.ui

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import im.niu.corelib.R
import im.niu.corelib.manager.UpgradeManager
import im.niu.corelib.ui.PermissionActivity.Companion.permissionItemList
import im.niu.corelib.ui.ui.theme.NiuprotectTheme
import im.niu.corelib.utils.ILog
interface IUIClickAble{
    fun onClick(permission: PermissionItem)
}
class PermissionItem(var must:Boolean = true){
    var name: String = ""
    var id: String = ""
    var grpoup: String = ""
    var error:String=""
    var enable:Boolean = false

}
class PermissionActivity : ComponentActivity() ,IUIClickAble{
    private var tag = "PermissionActivity"

    override fun onClick(permission:PermissionItem){
        val pp = XXPermissions.with(this)
        if(TextUtils.isEmpty(permission.grpoup)){
            pp.permission(permission.id)
        }else{
            for(ppi in permissionItemList){
                if(ppi.grpoup==permission.grpoup){
                    pp.permission(ppi.id)
                }
            }
        }

       pp.request(object : OnPermissionCallback {

                override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                    for (pname in permissions) {
                        val rs = XXPermissions.isGranted(this@PermissionActivity, pname)
                        ILog.d(tag, "permission $pname is $rs")
                        if(!rs){
                            permission.error="授权失败"
                        }
                    }
                    if (!allGranted) {
                        ILog.d(tag, "部分权限被拒绝")
                    }
                    refreshPermissionList()
                }

                override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                    if (doNotAskAgain) {
                        permission.error="被永久拒绝授权，请手动授予"
                        ILog.d(tag, "被永久拒绝授权，请手动授予录音和日历权限")
                        // 如果是被永久拒绝就跳转到应用权限系统设置页面
                        XXPermissions.startPermissionActivity(applicationContext, permissions)
                    } else {
                        ILog.d(tag, "获取录音和日历权限失败")
                    }
                    refreshPermissionList()
                }
            })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        refreshPermissionList()


    }

    private fun refreshPermissionList(){
        for (permission in permissionItemList){
            val granted = XXPermissions.isGranted(this,permission.id)
            permission.enable = granted
        }
        setContent {
            NiuprotectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(this)
                }
            }
        }
    }


    companion object {
        //targetSdkVersion >= 33 should use android.permission.READ_MEDIA_IMAGES, android.permission.READ_MEDIA_VIDEO, android.permission.READ_MEDIA_AUDIO
        var permissionItemList:ArrayList<PermissionItem> = ArrayList()
        fun init(targetSdkVersion:Int){
            if(targetSdkVersion>=33){
                permissionItemList.add(PermissionItem().apply {
                    name = "图片"
                    id="android.permission.READ_MEDIA_IMAGES"
                    grpoup="external_storage"
                })
                permissionItemList.add(PermissionItem().apply {
                    name = "视频"
                    id="android.permission.READ_MEDIA_VIDEO"
                    grpoup="external_storage"
                })
                permissionItemList.add(PermissionItem().apply {
                    name = "音频"
                    id="android.permission.READ_MEDIA_AUDIO"
                    grpoup="external_storage"
                })
            }else{
                permissionItemList.add(PermissionItem().apply {
                    name = "读取外部存储"
                    id = Permission.READ_EXTERNAL_STORAGE
                })
            }
            permissionItemList.add(
                PermissionItem(false).apply {
                    name = "使用外部存储"
                    id = Permission.WRITE_EXTERNAL_STORAGE
                })
            permissionItemList.add(
                PermissionItem().apply {
                    name = "麦克风"
                    id = Permission.RECORD_AUDIO
                })
            permissionItemList.add(
                PermissionItem().apply {
                    name = "电话状态"
                    id = Permission.READ_PHONE_STATE
                    enable = true
                })
            permissionItemList.add(
                PermissionItem().apply {
                    name = "应用使用情况"
                    id = Permission.PACKAGE_USAGE_STATS
                })
            permissionItemList.add(
                PermissionItem().apply {
                    name = "忽略电池优化"
                    id = Permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                })
            permissionItemList.add(
                PermissionItem().apply {
                    name = "悬浮窗"
                    id = Permission.SYSTEM_ALERT_WINDOW
                })
            permissionItemList.add(
                PermissionItem().apply {
                    name = "获取位置"
                    id = "android.permission.ACCESS_COARSE_LOCATION"
                    grpoup="location"
                })
            permissionItemList.add(
                PermissionItem().apply {
                    name = "精准定位"
                    id = "android.permission.ACCESS_FINE_LOCATION"
                    grpoup="location"
                })
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                permissionItemList.add(
                    PermissionItem(false).apply {
                        name = "位置服务"
                        id = "android.permission.FOREGROUND_SERVICE_LOCATION"
                        grpoup = "location"
                    })
            }
            permissionItemList.add(
                PermissionItem().apply {
                    name = "后台定位"
                    id = Permission.ACCESS_BACKGROUND_LOCATION
                })
        }
    }
}

    @Composable
    fun Greeting(callback: IUIClickAble) {
        Column {
            for(permissionItem in permissionItemList){
                if(permissionItem.enable){
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween){
                        Text(permissionItem.name, lineHeight = 30.sp, color = Color.Cyan)
                        Image(painterResource(R.drawable.success),"checked")
                    }
                }else{
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(permissionItem.name, lineHeight = 46.sp)
                        Button(onClick = {
                            callback.onClick(permissionItem)
                        }) {
                            Text("设置权限")
                        }
                    }
                }
            }
        }
    }
