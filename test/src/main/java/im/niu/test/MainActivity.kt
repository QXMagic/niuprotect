package im.niu.test

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import im.niu.corelib.App
import im.niu.corelib.net.MessageWraper
import im.niu.corelib.net.WebSocketManager
import im.niu.corelib.service.KeepLiveJobService
import im.niu.corelib.service.MainIntentService
import im.niu.corelib.utils.ILog
import im.niu.data.Userinfo
import im.niu.test.ui.theme.NiuprotectTheme
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {
    private val Tag = "MainActivity"
    private lateinit var webSocketManager: WebSocketManager
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private var permissionList = arrayOf("android.permission.ACCESS_COARSE_LOCATION",
        "android.permission.ACCESS_FINE_LOCATION",
        "android.permission.ACCESS_BACKGROUND_LOCATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NiuprotectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }

        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    ILog.d(Tag,"granted")
                    checkPermission()
                } else {

                }
            }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            requestPermissionLauncher.launch("android.permission.ACCESS_FINE_LOCATION");
            requestPermissionLauncher.launch("android.permission.ACCESS_BACKGROUND_LOCATION");
        }

        webSocketManager = WebSocketManager("192.168.31.31", 9090);
        //test()
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            for (permission in permissionList) {
                if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissionLauncher.launch(permission)
                    return
                }
            }
        }
        ILog.d(Tag,"all permission is granted")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            startForegroundService(Intent(this, KeepLiveJobService::class.java))
//        } else {
//            startService(Intent(this, MainIntentService::class.java))
//        }
    }

    override fun onResume() {
        super.onResume()
        webSocketManager.reconnect()
        //test()
    }

//    ActivityResultLauncher<String> requestPermissionLauncher
//    = registerForActivityResult(
//    new ActivityResultContracts.RequestPermission(),
//    result -> {
//        if (result.equals(true)) {
//            //权限获取到之后的动作
//        } else {
//            //权限没有获取到的动作
//        }
//    });


    private fun test() {


        while (!webSocketManager.isEnable()) {
            Thread.sleep(100)
        }
        var message = MessageWraper()
        message.data = Userinfo.UserInfo.newBuilder()
            .setId("uu0101")
            .setDevice("xm")
            .setOs("os")
            .setVersion(15)
            .build()
        webSocketManager.sendMessage(message)
        message.data = Userinfo.AppInfo.newBuilder()
            .setName("testaaaaaaaaaaaa").setPackage(BuildConfig.APPLICATION_ID)
            .build()
        webSocketManager.sendMessage(message)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello! $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NiuprotectTheme {
        Greeting("Android")
    }
}