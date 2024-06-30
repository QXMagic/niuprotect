package com.niu.protect.ui.setting

import android.app.Activity
import android.app.AlertDialog
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.gson.Gson
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.niu.protect.R
import com.niu.protect.accessibility.OpenAccessibilitySettingHelper.Companion.isAccessibilitySettingsOn
import com.niu.protect.accessibility.OpenAccessibilitySettingHelper.Companion.isAccessibilitySettingsOnByService
import com.niu.protect.accessibility.OpenAccessibilitySettingHelper.Companion.jumpToSettingPage
import com.niu.protect.accessibility.StatusUseAccessibilityService
import com.niu.protect.adapter.QxViewPagerAdapter
import com.niu.protect.core.MessageEvent
import com.niu.protect.core.QxOnClickListener
import com.niu.protect.lib.receiver.DeviceReceiver
import com.niu.protect.manager.AutoSettingManager.autoSettingFinish
import com.niu.protect.manager.UserProtectManager
import com.niu.protect.model.PermisstionStepBean
import com.niu.protect.model.QxInfo
import com.niu.protect.repository.PermisstionSettingRepository
import com.niu.protect.tools.ILog
import com.niu.protect.tools.RomUtil.isVivo
import com.niu.protect.tools.Tools
import com.niu.protect.ui.base.BaseActivity
import com.niu.protect.widget.MyViewPager
import org.greenrobot.eventbus.EventBus

class OpenQxActivity : BaseActivity() {
    var btn_enter_auto: Button? = null
    private var dpm: DevicePolicyManager? = null
    private var mAdapter: QxViewPagerAdapter? = null
    private var mDeviceAdminSample: ComponentName? = null
    private var permisstionStepBeanList: ArrayList<PermisstionStepBean?>? = ArrayList()
    private var view_pager: MyViewPager? = null
    var qxInfoList: MutableList<QxInfo?> = ArrayList()
    var step = 0
    var setPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_qx)
        changeTitle(getString(R.string.permission_title))
        val button = findViewById<View>(R.id.btn_enter_auto) as Button
        btn_enter_auto = button
        button.setOnClickListener {
            val permisstionSettingRepository = PermisstionSettingRepository.getInstance()
            val openQxActivity = this@OpenQxActivity
            permisstionSettingRepository.setPermissionStep(
                openQxActivity,
                openQxActivity.permisstionStepBeanList,
                null
            )
            val intent = Intent(this@OpenQxActivity, PermissionCollectActivity::class.java)
            intent.putExtra("data", Gson().toJson(permisstionStepBeanList))
            startActivity(intent)
        }
        UserProtectManager.getInstance().setProtect(-2)
        autoSettingFinish = 0
        initViews()
        layoutInflater
        val lf = LayoutInflater.from(this)
        for (i in 0..3) {
            val qxInfo = QxInfo()
            qxInfo.isok = 0
            qxInfo.view = lf.inflate(R.layout.item_qxview, null as ViewGroup?)
            val permisstionStepBean = PermisstionStepBean()
            if (i != 0) {
                if (i != 1) {
                    if (i == 2) {
                        qxInfo.title = "忽略电池优化"
                        qxInfo.des = "选择Constant.APP_NAME ➜ 允许"
                        permisstionStepBean.stepNo = 3
                        permisstionStepBean.permissionName = "忽略电池优化"
                        permisstionStepBean.status = -1
                    } else if (i == 3) {
                        qxInfo.title = "辅助功能"
                        qxInfo.des = "选择Constant.APP_NAME ➜ 打开开关"
                        permisstionStepBean.stepNo = 4
                        permisstionStepBean.permissionName = "辅助功能"
                        permisstionStepBean.status = -1
                    }
                } else {
                    qxInfo.title = "激活设备管理器"
                    qxInfo.des = "设备管理器 ➜ 激活"
                    permisstionStepBean.stepNo = 2
                    permisstionStepBean.permissionName = "激活设备管理器"
                    permisstionStepBean.status = -1
                }
            } else {
                qxInfo.title = "获取后台定位权限"
                qxInfo.des = "请选择始终允许"
                permisstionStepBean.stepNo = 1
                permisstionStepBean.permissionName = "获取后台定位权限"
                permisstionStepBean.status = -1
            }
            qxInfoList.add(qxInfo)
            permisstionStepBeanList!!.add(permisstionStepBean)
        }
        mAdapter!!.add(qxInfoList, this)
        mAdapter!!.notifyDataSetChanged()
        refDot()
        UserProtectManager.getInstance().setProtect(-2)
        Tools.saveLocTask(this, -1)
        Tools.saveStep4(this, -1)
        Tools.saveStep5(this, -1)
        Tools.saveStep6(this, -1)
        Tools.saveStep7(this, -1)
        Tools.saveStep8(this, -1)
        Tools.saveStep9(this, -1)
        Tools.saveStep10(this, -1)
    }

    public override fun onResume() {
        super.onResume()
        ILog.d(
            TAG,
            "----------isAccessibilitySettingsOn--------------" + isAccessibilitySettingsOn(
                this,
                StatusUseAccessibilityService::class.java.name
            )
        )
        if (isAccessibilitySettingsOn(
                this,
                StatusUseAccessibilityService::class.java.name
            ) || isAccessibilitySettingsOnByService(this)
        ) {
            permisstionStepBeanList!![setPosition]!!.status = 1
            btn_enter_auto!!.visibility = View.VISIBLE
            mAdapter!!.setShowButton(false)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == 4) {
            false
        } else super.onKeyDown(keyCode, event)
    }

    fun openLock() {
        dpm = getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val componentName = ComponentName(applicationContext, DeviceReceiver::class.java)
        mDeviceAdminSample = componentName
        if (dpm!!.isAdminActive(componentName)) {
            nextAction()
        }
        val intent = Intent("android.app.action.ADD_DEVICE_ADMIN")
        intent.putExtra("android.app.extra.DEVICE_ADMIN", mDeviceAdminSample)
        intent.putExtra("android.app.extra.ADD_EXPLANATION", "开启后就可以使用锁屏功能了...")
        startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE)
    }

    private fun initViews() {
        view_pager = findViewById<View>(R.id.view_pager) as MyViewPager
        val qxViewPagerAdapter = QxViewPagerAdapter()
        mAdapter = qxViewPagerAdapter
        view_pager!!.adapter = qxViewPagerAdapter
        view_pager!!.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                refDot()
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        mAdapter!!.setQxOnClickListener(object : QxOnClickListener {
            override fun onClick(pos: Int) {
                setPosition = pos
                if (pos == 0) {
                    localAction()
                } else if (pos == 1) {
                    openLock()
                } else if (pos != 2) {
                    if (pos == 3) {
                        Tools.saveAutoSet(this@OpenQxActivity, 1)
                        xfkAction()
                    } else if (pos == 4) {
                        closeTasklock()
                    }
                } else if (!isIgnoringBatteryOptimizations) {
                    requestIgnoreBatteryOptimizations()
                } else {
                    nextAction()
                }
                if (pos == 5) {
                    Tools.saveStep10(this@OpenQxActivity, -3)
                    val batterySaver = Intent()
                    batterySaver.component = ComponentName(
                        "com.iqoo.powersaving",
                        "com.iqoo.powersaving.PowerSavingManagerActivity"
                    )
                    startActivity(batterySaver)
                } else if (pos == 6) {
                    askForPermission()
                } else if (pos == 7) {
                    if (Tools.getStep8(this@OpenQxActivity) == -1) {
                        AlertDialog.Builder(this@OpenQxActivity).setTitle("提示")
                            .setMessage("自动设置，请勿操作")
                            .setPositiveButton("确定") { dialogInterface, i ->
                                Tools.saveStep8(this@OpenQxActivity, 0)
                                val intent = Intent("android.settings.USAGE_ACCESS_SETTINGS")
                                _context.startActivity(intent)
                            }.show()
                    }
                    _context.startActivity(Intent("android.settings.USAGE_ACCESS_SETTINGS"))
                } else if (pos == 8) {
                    if (Tools.getStep9(this@OpenQxActivity) == -1) {
                        AlertDialog.Builder(this@OpenQxActivity).setTitle("提示")
                            .setMessage("自动设置，请勿操作")
                            .setPositiveButton("确定") { dialogInterface, i ->
                                Tools.saveStep9(this@OpenQxActivity, 0)
                                val intent = Intent()
                                intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
                                intent.addCategory("android.intent.category.DEFAULT")
                                intent.data = Uri.parse("package:$packageName")
                                startActivity(intent)
                            }.show()
                    }
                    val intent = Intent()
                    intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
                    intent.addCategory("android.intent.category.DEFAULT")
                    intent.data = Uri.parse("package:$packageName")
                    startActivity(intent)
                } else if (pos == 9) {
                    UserProtectManager.getInstance().setProtect(1)
                    Tools.saveAutoSet(this@OpenQxActivity, 0)
                    Tools.saveQxSet(_context, 1)
                    val backHome = Intent("android.intent.action.MAIN")
                    backHome.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    backHome.addCategory("android.intent.category.HOME")
                    startActivity(backHome)
                    finish()
                }
            }
        } as QxOnClickListener?)
    }

    fun closeTasklock() {
        val version = Build.VERSION.SDK_INT
        if (version == 28) {
            if (Tools.getStep4(this) == -1) {
                AlertDialog.Builder(this).setTitle("提示").setMessage("自动设置，请勿 手动操作")
                    .setPositiveButton("确定") { dialogInterface, i ->
                        Tools.saveStep4(this@OpenQxActivity, 0)
                        val intent = getpManager()
                        if (intent != null) {
                            _context.startActivity(intent)
                        }
                    }.show()
                return
            }
            val intent = getpManager()
            if (intent != null) {
                _context.startActivity(intent)
            }
        } else if (Tools.getStep6(this) == -1) {
            EventBus.getDefault().post(MessageEvent())
            step = 4
        } else {
            startAppSettingDetail()
            step = 4
        }
    }

    private fun showGotoAutoSettingDialog() {
        AlertDialog.Builder(this).setTitle("提示")
            .setMessage("自动设置，请勿手动操作，自动设置失败后可以手动操作")
            .setPositiveButton("确定") { dialogInterface, i ->
                Tools.saveStep6(this@OpenQxActivity, 0)
                startAppSettingDetail()
            }.show()
    }

    private fun taskCloseSetting() {
        AlertDialog.Builder(this).setTitle("提示").setMessage("是否按照操作锁定任务栏？")
            .setPositiveButton("确定") { dialogInterface, i ->
                Tools.saveStep6(this@OpenQxActivity, 0)
                btn_enter_auto!!.visibility = View.VISIBLE
                mAdapter!!.setShowButton(false)
            }
            .setNegativeButton("没有") { dialog, which -> dialog.dismiss() }.show()
    }

    fun getpManager(): Intent? {
        val pm = packageManager
        val mainIntent = Intent("android.intent.action.MAIN", null as Uri?)
        mainIntent.addCategory("android.intent.category.LAUNCHER")
        val activities = pm.queryIntentActivities(mainIntent, 0)
        for (info in activities) {
            val packName = info.activityInfo.packageName
            if (packName == "com.iqoo.secure") {
                val launchIntent = Intent()
                launchIntent.component = ComponentName(packName, info.activityInfo.name)
                return launchIntent
            }
        }
        return null
    }

    fun refDot() {
        val dotview = findViewById<View>(R.id.dotview) as LinearLayout
        for (i in qxInfoList.indices) {
            val qxInfo = qxInfoList[i]
            val tag = i + 1000
            val dview = dotview.findViewWithTag<View>(tag.toString() + "") as LinearLayout
            if (dview != null) {
                if (qxInfo!!.isok == 0) {
                    dview.setBackgroundResource(R.drawable.view_round9)
                } else if (qxInfo.isok == 1) {
                    dview.setBackgroundResource(R.drawable.view_round8)
                } else if (qxInfo.isok == -1) {
                    dview.setBackgroundResource(R.drawable.view_round10)
                }
                if (i == view_pager!!.currentItem) {
                    dview.setBackgroundResource(R.drawable.view_round11)
                }
            }
        }
    }

    fun nextAction() {
        permisstionStepBeanList!![setPosition]!!.status = 1
        val qxInfo = qxInfoList[view_pager!!.currentItem]
        qxInfo!!.isok = 1
        mAdapter!!.notifyDataSetChanged()
        val myViewPager = view_pager
        myViewPager!!.currentItem = myViewPager.currentItem + 1
        ILog.d("permissionSet", "list:" + Gson().toJson(permisstionStepBeanList))
    }

    override fun onRestart() {
        super.onRestart()
    }

    private fun resetPermission() {
        if (view_pager!!.currentItem == qxInfoList.size - 1) {
            return
        }
        if (isVivo) {
            if (Tools.getStep4(this) == 1) {
                nextAction()
                Tools.saveStep4(this, -1)
            } else if (Tools.getStep4(this) == -2) {
                Tools.showAlert3(this, "自动设置失败，请手动设置")
                Tools.saveStep4(this, -3)
                val qxInfo = qxInfoList[view_pager!!.currentItem]
                qxInfo!!.isok = -1
            } else if (Tools.getStep4(this) == -3) {
                AlertDialog.Builder(this).setTitle("提示").setMessage("是否按要求设定")
                    .setPositiveButton("确定") { dialogInterface, i ->
                        Tools.saveStep4(this@OpenQxActivity, 1)
                        nextAction()
                    }
                    .setNegativeButton("取消") { dialogInterface, i ->
                        Tools.saveStep4(this@OpenQxActivity, -1)
                        val qxInfo2 = qxInfoList[view_pager!!.currentItem]
                        qxInfo2!!.isok = -1
                        refDot()
                    }.show()
            }
            if (Tools.getStep5(this) == 1) {
                nextAction()
                Tools.saveStep5(this, -1)
            } else if (Tools.getStep5(this) == -2) {
                Tools.showAlert3(this, "自动设置失败，请手动设置")
                Tools.saveStep5(this, -3)
                val qxInfo2 = qxInfoList[view_pager!!.currentItem]
                qxInfo2!!.isok = -1
            } else if (Tools.getStep5(this) == -3) {
                AlertDialog.Builder(this).setTitle("提示").setMessage("是否按要求设定")
                    .setPositiveButton("确定") { dialogInterface, i ->
                        Tools.saveStep5(this@OpenQxActivity, 1)
                        nextAction()
                    }
                    .setNegativeButton("取消") { dialogInterface, i ->
                        Tools.saveStep5(this@OpenQxActivity, -1)
                        val qxInfo3 = qxInfoList[view_pager!!.currentItem]
                        qxInfo3!!.isok = -1
                        refDot()
                    }.show()
            }
            if (Tools.getStep6(this) == 1) {
                nextAction()
                Tools.saveStep6(this, -1)
            } else if (Tools.getStep6(this) == -2) {
                Tools.showAlert3(this, "自动设置失败，请手动设置")
                Tools.saveStep6(this, -3)
                val qxInfo3 = qxInfoList[view_pager!!.currentItem]
                qxInfo3!!.isok = -1
            } else if (Tools.getStep6(this) == -3) {
                AlertDialog.Builder(this).setTitle("提示").setMessage("是否按要求设定")
                    .setPositiveButton("确定") { dialogInterface, i ->
                        Tools.saveStep6(this@OpenQxActivity, 1)
                        nextAction()
                    }
                    .setNegativeButton("取消") { dialogInterface, i ->
                        Tools.saveStep6(this@OpenQxActivity, -1)
                        val qxInfo4 = qxInfoList[view_pager!!.currentItem]
                        qxInfo4!!.isok = -1
                        refDot()
                    }.show()
            }
            if (Tools.getStep7(this) == 1) {
                nextAction()
                Tools.saveStep7(this, -1)
            } else if (Tools.getStep7(this) == -2) {
                Tools.showAlert3(this, "自动设置失败，请手动设置")
                Tools.saveStep7(this, -3)
                val qxInfo4 = qxInfoList[view_pager!!.currentItem]
                qxInfo4!!.isok = -1
            } else if (Tools.getStep7(this) == -3) {
                AlertDialog.Builder(this).setTitle("提示").setMessage("是否按要求设定")
                    .setPositiveButton("确定") { dialogInterface, i ->
                        Tools.saveStep7(this@OpenQxActivity, 1)
                        nextAction()
                    }
                    .setNegativeButton("取消") { dialogInterface, i ->
                        Tools.saveStep7(this@OpenQxActivity, -1)
                        val qxInfo5 = qxInfoList[view_pager!!.currentItem]
                        qxInfo5!!.isok = -1
                        refDot()
                    }.show()
            }
            if (Tools.getStep8(this) == 1) {
                nextAction()
                Tools.saveStep8(this, -1)
            } else if (Tools.getStep8(this) == -2) {
                Tools.showAlert3(this, "自动设置失败，请手动设置")
                Tools.saveStep8(this, -3)
                val qxInfo5 = qxInfoList[view_pager!!.currentItem]
                qxInfo5!!.isok = -1
            } else if (Tools.getStep8(this) == -3) {
                AlertDialog.Builder(this).setTitle("提示").setMessage("是否按要求设定")
                    .setPositiveButton("确定") { dialogInterface, i ->
                        Tools.saveStep8(this@OpenQxActivity, 1)
                        nextAction()
                    }
                    .setNegativeButton("取消") { dialogInterface, i ->
                        Tools.saveStep8(this@OpenQxActivity, -1)
                        val qxInfo6 = qxInfoList[view_pager!!.currentItem]
                        qxInfo6!!.isok = -1
                        refDot()
                    }.show()
            }
            if (Tools.getStep9(this) == 1) {
                nextAction()
                Tools.saveStep9(this, -1)
            } else if (Tools.getStep9(this) == -2) {
                Tools.showAlert3(this, "自动设置失败，请手动设置")
                Tools.saveStep9(this, -3)
                val qxInfo6 = qxInfoList[view_pager!!.currentItem]
                qxInfo6!!.isok = -1
            } else if (Tools.getStep9(this) == -3) {
                AlertDialog.Builder(this).setTitle("提示").setMessage("是否按要求设定")
                    .setPositiveButton("确定") { dialogInterface, i ->
                        Tools.saveStep9(this@OpenQxActivity, 1)
                        nextAction()
                    }
                    .setNegativeButton("取消") { dialogInterface, i ->
                        Tools.saveStep9(this@OpenQxActivity, -1)
                        val qxInfo7 = qxInfoList[view_pager!!.currentItem]
                        qxInfo7!!.isok = -1
                        refDot()
                    }.show()
            }
            if (Tools.getStep10(this) == 1) {
                nextAction()
                Tools.saveStep10(this, -1)
            } else if (Tools.getStep10(this) == -2) {
                Tools.showAlert3(this, "自动设置失败，请手动设置")
                Tools.saveStep10(this, -3)
                val qxInfo7 = qxInfoList[view_pager!!.currentItem]
                qxInfo7!!.isok = -1
            } else if (Tools.getStep10(this) == -3) {
                AlertDialog.Builder(this).setTitle("提示").setMessage("是否按要求设定")
                    .setPositiveButton("确定") { dialogInterface, i ->
                        Tools.saveStep10(this@OpenQxActivity, -1)
                        nextAction()
                    }
                    .setNegativeButton("取消") { dialogInterface, i ->
                        Tools.saveStep10(this@OpenQxActivity, -1)
                        val qxInfo8 = qxInfoList[view_pager!!.currentItem]
                        qxInfo8!!.isok = -1
                        refDot()
                    }.show()
            }
        }
        val lct = Tools.getLocTask(this)
        if (lct == 2) {
            Tools.saveLocTask(this, -1)
            AlertDialog.Builder(this).setTitle("提示").setMessage("是否按要求设定")
                .setPositiveButton("确定") { dialogInterface, i -> nextAction() }
                .setNegativeButton("取消", null as DialogInterface.OnClickListener?).show()
        }
        refDot()
    }

    fun startAppSettingDetail() {
        if (Build.MODEL.contains("Y85") && !Build.MODEL.contains("Y85A") || Build.MODEL.contains("vivo Y53L")) {
            val localIntent = Intent()
            localIntent.setClassName(
                "com.vivo.permissionmanager",
                "com.vivo.permissionmanager.activity.PurviewTabActivity"
            )
            localIntent.putExtra("packagename", packageName)
            localIntent.putExtra("tabId", "1")
            startActivity(localIntent)
            return
        }
        val localIntent2 = Intent()
        localIntent2.setClassName(
            "com.vivo.permissionmanager",
            "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity"
        )
        localIntent2.action = "secure.intent.action.softPermissionDetail"
        localIntent2.putExtra("packagename", packageName)
        startActivity(localIntent2)
    }

    fun OPPO() {
        val localIntent = Intent()
        localIntent.setClassName(
            "com.color.safecenter",
            "com.color.safecenter.permission.PermissionManagerActivity"
        )
        localIntent.putExtra("packagename", packageName)
        startActivity(localIntent)
    }

    fun xfkAction() {
        if (!isAccessibilitySettingsOn(this, StatusUseAccessibilityService::class.java.name)) {
            jumpToSettingPage(this)
            return
        }
        Toast.makeText(this, "权限已开启", Toast.LENGTH_SHORT).show()
        nextAction()
    }

    fun localAction() {
        XXPermissions.with(this).permission(
            Permission.ACCESS_BACKGROUND_LOCATION,
            "android.permission.ACCESS_FINE_LOCATION"
        ).request(object : OnPermissionCallback {
            override fun onGranted(permissions: List<String>, all: Boolean) {
                nextAction()
            }

            override fun onDenied(permissions: List<String>, never: Boolean) {
                if (never) {
                    Toast.makeText(
                        this@OpenQxActivity,
                        "被永久拒绝授权，请手动授予权限",
                        Toast.LENGTH_SHORT
                    ).show()
                    XXPermissions.startPermissionActivity(
                        (this@OpenQxActivity as Activity),
                        permissions
                    )
                    return
                }
                Toast.makeText(this@OpenQxActivity, "获取权限失败", Toast.LENGTH_SHORT).show()
            }
        })
    }

    val isIgnoringBatteryOptimizations: Boolean
        get() {
            val powerManager =
                getSystemService(POWER_SERVICE) as PowerManager
            return if (powerManager == null || Build.VERSION.SDK_INT < 23) {
                false
            } else powerManager.isIgnoringBatteryOptimizations(packageName)
        }

    fun requestIgnoreBatteryOptimizations() {
        try {
            val intent = Intent("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS")
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun askForPermission() {
        if (!Settings.canDrawOverlays(this)) {
            if (Tools.getStep7(this) == -1) {
                AlertDialog.Builder(this).setTitle("提示").setMessage("自动设置，请勿操作")
                    .setPositiveButton("确定") { dialogInterface, i ->
                        Tools.saveStep7(this@OpenQxActivity, 0)
                        startAppSettingDetail()
                    }.setNegativeButton("取消", null as DialogInterface.OnClickListener?).show()
                return
            } else {
                startAppSettingDetail()
                return
            }
        }
        Toast.makeText(this, "权限已开启", Toast.LENGTH_SHORT).show()
        nextAction()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "权限授予失败，无法开启悬浮窗", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "权限授予成功！", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        var OVERLAY_PERMISSION_REQ_CODE = 1
        private const val TAG = "OpenQxActivity"
    }
}