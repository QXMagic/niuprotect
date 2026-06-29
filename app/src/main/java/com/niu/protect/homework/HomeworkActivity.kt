package com.niu.protect.homework

import android.Manifest
import android.app.AlarmManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.niu.protect.homework.service.TimerService
import com.niu.protect.homework.ui.MainScreen
import com.niu.protect.homework.ui.theme.HomeworkTheme
import com.niu.protect.homework.viewmodel.MainViewModel

/**
 * 作业时间控制入口。承载 Compose 作业流程界面，并绑定计时前台服务。
 */
class HomeworkActivity : ComponentActivity() {

    private var isServiceBound = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            isServiceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isServiceBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        bindTimerService()

        setContent {
            HomeworkTheme {
                val vm: MainViewModel = viewModel()

                val permissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission()
                ) { isGranted ->
                    if (!isGranted) {
                        Toast.makeText(
                            this,
                            "通知权限被拒绝，后台计时可能无法正常显示",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                LaunchedEffect(Unit) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        val granted = ContextCompat.checkSelfPermission(
                            this@HomeworkActivity, Manifest.permission.POST_NOTIFICATIONS
                        ) == PackageManager.PERMISSION_GRANTED
                        if (!granted) {
                            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    }
                }

                var showExactAlarmDialog by remember { mutableStateOf(false) }
                LaunchedEffect(Unit) {
                    kotlinx.coroutines.delay(1500)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                        if (!am.canScheduleExactAlarms()) showExactAlarmDialog = true
                    }
                }

                if (showExactAlarmDialog) {
                    androidx.compose.material3.AlertDialog(
                        onDismissRequest = { showExactAlarmDialog = false },
                        title = { androidx.compose.material3.Text("需要精确闹钟权限") },
                        text = {
                            androidx.compose.material3.Text(
                                "Android 12+ 需授予精确闹钟权限，才能确保计时结束后准时提醒。"
                            )
                        },
                        confirmButton = {
                            androidx.compose.material3.TextButton(onClick = {
                                showExactAlarmDialog = false
                                try {
                                    startActivity(
                                        Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                                            data = Uri.parse("package:$packageName")
                                        }
                                    )
                                } catch (e: Exception) {
                                    startActivity(Intent(Settings.ACTION_SETTINGS))
                                }
                            }) { androidx.compose.material3.Text("去设置") }
                        },
                        dismissButton = {
                            androidx.compose.material3.TextButton(
                                onClick = { showExactAlarmDialog = false }
                            ) { androidx.compose.material3.Text("取消") }
                        }
                    )
                }

                val isRestoring by vm.isRestoring.collectAsState()
                if (isRestoring) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    MainScreen(vm = vm)
                }
            }
        }
    }

    private fun bindTimerService() {
        bindService(Intent(this, TimerService::class.java), serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onResume() {
        super.onResume()
        if (!isServiceBound) bindTimerService()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isServiceBound) {
            try {
                unbindService(serviceConnection)
                isServiceBound = false
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
