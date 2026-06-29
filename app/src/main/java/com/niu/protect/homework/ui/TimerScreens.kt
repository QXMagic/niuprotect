package com.niu.protect.homework.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niu.protect.homework.model.AppSettings

@Composable
fun InitialTimerScreen(
    settings: AppSettings,
    onStartTimer: () -> Unit,
    onOpenSettings: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "作业时间管理",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        if (settings.isTestMode) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "【测试模式】",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "准备好了吗？",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "初始作业时间", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${settings.initialHomeworkMinutes} 分钟",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onStartTimer,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(64.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = "开始计时", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(onClick = onOpenSettings, modifier = Modifier.fillMaxWidth(0.7f)) {
            Text("设置")
        }
    }
}

@Composable
fun TimerRunningScreen(
    remainingSeconds: Int,
    totalSeconds: Int,
    isRunning: Boolean,
    onPause: () -> Unit,
    onResume: () -> Unit,
    stageText: String = "写作业时间",
    canPause: Boolean = true
) {
    val minutes = remainingSeconds / 60
    val seconds = remainingSeconds % 60
    val progress = if (totalSeconds > 0) remainingSeconds.toFloat() / totalSeconds else 0f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stageText,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(48.dp))

        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                progress = { progress },
                modifier = Modifier.size(280.dp),
                strokeWidth = 16.dp,
                color = MaterialTheme.colorScheme.primary
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = String.format("%02d:%02d", minutes, seconds),
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        if (canPause) {
            Button(
                onClick = { if (isRunning) onPause() else onResume() },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = if (isRunning) "暂停" else "继续",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun CheckHomeworkScreen(
    onComplete: () -> Unit,
    onNotComplete: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "时间到！",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.error
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "作业完成了吗？", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onComplete,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(64.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(text = "已完成作业", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onNotComplete,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(64.dp),
            shape = RoundedCornerShape(32.dp)
        ) {
            Text(text = "未完成作业", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun CompletionScreen(onRestart: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "🎉", fontSize = 80.sp)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "太棒了！",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "作业已完成！", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onRestart,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(56.dp),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(text = "重新开始", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}
