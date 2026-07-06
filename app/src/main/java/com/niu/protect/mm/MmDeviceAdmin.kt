package com.niu.protect.mm

import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.provider.Settings
import com.niu.protect.lib.receiver.DeviceReceiver

/**
 * 防卸载：设备管理器(Device Admin)。
 *
 * 只要本应用是「已激活」的设备管理器，系统就禁止直接卸载——用户必须先到
 * 「设置 > 安全 > 设备管理器」取消激活才能卸载，多一道门槛即可拦住孩子随手卸载。
 *
 * 注意：这里只声明空策略(见 res/xml/device_admin.xml)，不申请锁屏/清数据等强权限，
 * 激活对话框对用户更友好、也更容易通过厂商审核；防卸载不依赖任何具体策略。
 */
object MmDeviceAdmin {

    /** 设备管理器接收器组件(protect 模块的 DeviceReceiver) */
    private fun component(context: Context): ComponentName =
        ComponentName(context.applicationContext, DeviceReceiver::class.java)

    private fun dpm(context: Context): DevicePolicyManager =
        context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager

    /** 是否已激活为设备管理器(=已开启防卸载) */
    fun isActive(context: Context): Boolean = try {
        dpm(context).isAdminActive(component(context))
    } catch (e: Exception) {
        false
    }

    /** 弹出系统激活对话框(从 Activity 调起) */
    fun requestActivate(activity: Activity) {
        val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
            putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, component(activity))
            putExtra(
                DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "开启后可防止孩子私自卸载本应用，保证家长管控持续生效。"
            )
        }
        activity.startActivity(intent)
    }

    /** 激活用 Intent(供引导页统一 startActivity 使用) */
    fun activateIntent(context: Context): Intent =
        Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
            putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, component(context))
            putExtra(
                DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "开启后可防止孩子私自卸载本应用，保证家长管控持续生效。"
            )
        }

    /** 取消激活(解绑时调用，取消后才允许卸载) */
    fun deactivate(context: Context) {
        try {
            val comp = component(context)
            if (dpm(context).isAdminActive(comp)) {
                dpm(context).removeActiveAdmin(comp)
            }
        } catch (e: Exception) {
            // ignore
        }
    }

    /** 打开设备管理器设置列表(用户手动取消激活的入口，找不到则回退安全设置) */
    fun openSettings(context: Context) {
        val intent = try {
            Intent(Settings.ACTION_SECURITY_SETTINGS)
        } catch (e: Exception) {
            Intent(Settings.ACTION_SETTINGS)
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}
