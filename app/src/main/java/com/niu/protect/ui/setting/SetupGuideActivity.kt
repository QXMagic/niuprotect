package com.niu.protect.ui.setting

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.niu.protect.R
import com.niu.protect.mm.MmSetupCheck
import com.niu.protect.tools.ILog
import com.niu.protect.ui.base.BaseActivity

/**
 * 装机引导：一屏展示管控所需的全部权限/开关的实时状态，逐项引导开启。
 * onResume 时刷新，用户开完返回即见绿色对勾。
 */
class SetupGuideActivity : BaseActivity() {

    private lateinit var container: LinearLayout
    private lateinit var summary: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup_guide)
        changeTitle("管控设置检查")
        showBack()
        container = findViewById(R.id.container)
        summary = findViewById(R.id.summaryTxt)
    }

    override fun onResume() {
        super.onResume()
        render()
    }

    private fun dp(v: Int): Int = (v * resources.displayMetrics.density).toInt()

    private fun render() {
        container.removeAllViews()
        val items = MmSetupCheck.items(this)
        val pending = items.count { it.checkable && !it.granted }
        summary.text = if (pending == 0)
            "✅ 已完成检查项全部开启。自启动如未开请手动确认，确保重启后管控自动恢复。"
        else
            "还有 $pending 项待开启。国产手机需手动授予，缺一项对应功能会失效。"

        for (item in items) {
            container.addView(buildRow(item))
        }
    }

    private fun buildRow(item: MmSetupCheck.Item): View {
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setBackgroundColor(Color.WHITE)
            setPadding(dp(16), dp(18), dp(16), dp(18))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.topMargin = dp(12)
            layoutParams = lp
        }

        val textCol = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }
        textCol.addView(TextView(this).apply {
            text = item.title
            setTextColor(Color.parseColor("#222222"))
            textSize = 16f
        })
        textCol.addView(TextView(this).apply {
            text = item.desc
            setTextColor(Color.parseColor("#999999"))
            textSize = 12f
            setPadding(0, dp(4), 0, 0)
        })
        card.addView(textCol)

        if (item.checkable && item.granted) {
            card.addView(TextView(this).apply {
                text = "已开启"
                setTextColor(Color.parseColor("#0a8a32"))
                textSize = 14f
            })
        } else {
            card.addView(Button(this).apply {
                text = "去开启"
                setTextColor(Color.WHITE)
                setBackgroundColor(Color.parseColor("#6a6cff"))
                textSize = 14f
                minWidth = dp(88)
                setOnClickListener { openSetting(item) }
            })
        }
        return card
    }

    private fun openSetting(item: MmSetupCheck.Item) {
        try {
            // 应用列表用运行时权限弹窗更顺手，其余走设置页 Intent
            if (item.key == "applist") {
                androidx.core.app.ActivityCompat.requestPermissions(
                    this, arrayOf("com.android.permission.GET_INSTALLED_APPS"), 0x51
                )
                return
            }
            startActivity(item.intent)
        } catch (e: Exception) {
            ILog.d("SetupGuide", "open setting error: ${e.message}")
            try {
                startActivity(
                    Intent(
                        android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        android.net.Uri.parse("package:$packageName")
                    )
                )
            } catch (_: Exception) {
            }
        }
    }
}
