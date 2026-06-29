package com.niu.protect.homework.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.niu.protect.homework.model.ActivityItem

@Composable
fun EarnTimeScreen(
    activityList: List<ActivityItem>,
    earnedSeconds: Int,
    targetSeconds: Int,
    selectedActivity: ActivityItem?,
    isRunning: Boolean,
    elapsedSeconds: Int,
    onSelectActivity: (ActivityItem) -> Unit,
    onStartEarning: (ActivityItem) -> Unit,
    onStopEarning: () -> Unit,
    onComplete: () -> Unit,
    canComplete: Boolean
) {
    val earnedMinutesDisplay = earnedSeconds / 60
    val earnedSecondsDisplay = earnedSeconds % 60
    val targetMinutes = targetSeconds / 60

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "赚取作业时间",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = if (earnedSeconds >= targetSeconds)
                    MaterialTheme.colorScheme.primaryContainer
                else
                    MaterialTheme.colorScheme.surfaceVariant
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "已赚取时间", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = String.format("%02d:%02d", earnedMinutesDisplay, earnedSecondsDisplay),
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (earnedSeconds >= targetSeconds)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "目标: $targetMinutes 分钟",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (earnedSeconds >= targetSeconds)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.outline
                )
                if (earnedSeconds >= targetSeconds) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "✓ 时间已足够！",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedActivity != null && isRunning) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "正在: ${selectedActivity.name}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "比例: ${selectedActivity.timeRatio}x",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = String.format("已进行 %02d:%02d", elapsedSeconds / 60, elapsedSeconds % 60),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onStopEarning,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("停止赚取", fontSize = 16.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(
            text = "选择活动:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(activityList, key = { it.id }) { activity ->
                ActivityItemCard(
                    activity = activity,
                    isSelected = selectedActivity?.id == activity.id,
                    isRunning = isRunning,
                    onClick = {
                        if (!isRunning) {
                            onSelectActivity(activity)
                            onStartEarning(activity)
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onComplete,
            enabled = canComplete,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(
                text = if (canComplete) "开始写作业" else "赚取更多时间",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ActivityItemCard(
    activity: ActivityItem,
    isSelected: Boolean,
    isRunning: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp),
        onClick = { if (!isRunning) onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = activity.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                if (activity.description.isNotEmpty()) {
                    Text(
                        text = activity.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${activity.timeRatio}x",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "时间比例",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}
