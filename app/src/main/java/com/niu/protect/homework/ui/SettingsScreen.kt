package com.niu.protect.homework.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.niu.protect.homework.HomeworkLockManager
import com.niu.protect.homework.model.ActivityItem
import com.niu.protect.homework.model.AppSettings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settings: AppSettings,
    activityList: List<ActivityItem>,
    onSettingsChange: (AppSettings) -> Unit,
    onAddActivity: (String, Float, String) -> Unit,
    onUpdateActivity: (ActivityItem) -> Unit,
    onDeleteActivity: (String) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var showAddDialog by remember { mutableStateOf(false) }
    var editingActivity by remember { mutableStateOf<ActivityItem?>(null) }
    var hardLockEnabled by remember { mutableStateOf(HomeworkLockManager.isEnabled(context)) }
    var showPinDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "返回")
            }
            Text(
                text = "设置",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ---------------- 手机管控 ----------------
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "手机管控",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("作业未完成时锁定手机")
                        Text(
                            text = "开启后，作业进行/检查阶段仅允许使用本应用、桌面、电话",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                    Switch(
                        checked = hardLockEnabled,
                        onCheckedChange = { enabled ->
                            hardLockEnabled = enabled
                            HomeworkLockManager.setEnabled(context, enabled)
                        }
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(onClick = { showPinDialog = true }) {
                    Text("修改家长密码")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ---------------- 时间设置 ----------------
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "时间设置",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("初始作业时间（分钟）")
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {
                            if (settings.initialHomeworkMinutes > 5) {
                                onSettingsChange(settings.copy(initialHomeworkMinutes = settings.initialHomeworkMinutes - 5))
                            }
                        }) {
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "减少")
                        }
                        Text(
                            text = "${settings.initialHomeworkMinutes}",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        IconButton(onClick = {
                            onSettingsChange(settings.copy(initialHomeworkMinutes = settings.initialHomeworkMinutes + 5))
                        }) {
                            Icon(Icons.Default.Add, contentDescription = "增加")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("目标赚取时间（分钟）")
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {
                            if (settings.targetEarnMinutes > 5) {
                                onSettingsChange(settings.copy(targetEarnMinutes = settings.targetEarnMinutes - 5))
                            }
                        }) {
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "减少")
                        }
                        Text(
                            text = "${settings.targetEarnMinutes}",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        IconButton(onClick = {
                            onSettingsChange(settings.copy(targetEarnMinutes = settings.targetEarnMinutes + 5))
                        }) {
                            Icon(Icons.Default.Add, contentDescription = "增加")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("测试模式")
                        Text(
                            text = "计时加速10倍，用于快速测试流程",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                    Switch(
                        checked = settings.isTestMode,
                        onCheckedChange = { enabled ->
                            onSettingsChange(settings.copy(isTestMode = enabled))
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "赚取时间活动",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "添加活动")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(activityList, key = { it.id }) { activity ->
                ActivitySettingItem(
                    activity = activity,
                    onEdit = { editingActivity = activity },
                    onDelete = { onDeleteActivity(activity.id) }
                )
            }
        }
    }

    if (showAddDialog) {
        ActivityEditDialog(
            title = "添加活动",
            initial = null,
            onDismiss = { showAddDialog = false },
            onConfirm = { name, ratio, desc ->
                onAddActivity(name, ratio, desc)
                showAddDialog = false
            }
        )
    }

    editingActivity?.let { activity ->
        ActivityEditDialog(
            title = "编辑活动",
            initial = activity,
            onDismiss = { editingActivity = null },
            onConfirm = { name, ratio, desc ->
                onUpdateActivity(activity.copy(name = name, timeRatio = ratio, description = desc))
                editingActivity = null
            }
        )
    }

    if (showPinDialog) {
        ChangePinDialog(
            onDismiss = { showPinDialog = false },
            onConfirm = { newPin ->
                HomeworkLockManager.setPin(context, newPin)
                showPinDialog = false
            }
        )
    }
}

@Composable
private fun ActivitySettingItem(
    activity: ActivityItem,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = activity.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                if (activity.description.isNotEmpty()) {
                    Text(
                        text = activity.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
            Text(
                text = "${activity.timeRatio}x",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(end = 16.dp)
            )
            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "编辑")
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "删除")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ActivityEditDialog(
    title: String,
    initial: ActivityItem?,
    onDismiss: () -> Unit,
    onConfirm: (name: String, ratio: Float, description: String) -> Unit
) {
    var name by remember { mutableStateOf(initial?.name ?: "") }
    var ratio by remember { mutableStateOf(initial?.timeRatio?.toString() ?: "1.0") }
    var description by remember { mutableStateOf(initial?.description ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("活动名称") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = ratio,
                    onValueChange = { ratio = it },
                    label = { Text("时间比例") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("描述（可选）") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val ratioValue = ratio.toFloatOrNull() ?: 1f
                    if (name.isNotBlank() && ratioValue > 0) {
                        onConfirm(name, ratioValue, description)
                    }
                },
                enabled = name.isNotBlank()
            ) { Text("保存") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("取消") }
        }
    )
}

@Composable
private fun ChangePinDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var pin by remember { mutableStateOf("") }
    var pin2 by remember { mutableStateOf("") }
    val valid = pin.length >= 4 && pin == pin2

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("修改家长密码") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = pin,
                    onValueChange = { pin = it.filter { c -> c.isDigit() } },
                    label = { Text("新密码（至少4位数字）") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = pin2,
                    onValueChange = { pin2 = it.filter { c -> c.isDigit() } },
                    label = { Text("确认密码") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { if (valid) onConfirm(pin) }, enabled = valid) {
                Text("保存")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("取消") }
        }
    )
}
