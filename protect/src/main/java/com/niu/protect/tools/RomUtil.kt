package com.niu.protect.tools

import android.os.Build
import android.text.TextUtils
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.Locale

object RomUtil {
    private const val KEY_VERSION_EMUI = "ro.build.version.emui"
    private const val KEY_VERSION_MIUI = "ro.miui.ui.version.name"
    private const val KEY_VERSION_OPPO = "ro.build.version.opporom"
    private const val KEY_VERSION_SMARTISAN = "ro.smartisan.version"
    private const val KEY_VERSION_VIVO = "ro.vivo.os.version"
    private const val ROM_EMUI = "EMUI"
    private const val ROM_FLYME = "FLYME"
    private const val ROM_MIUI = "MIUI"
    private const val ROM_OPPO = "OPPO"
    private const val ROM_QIKU = "QIKU"
    private const val ROM_SMARTISAN = "SMARTISAN"
    private const val ROM_VIVO = "VIVO"
    private const val TAG = "RomUtil"
    private var sName: String? = null
    private var sVersion: String? = null
    @JvmStatic
    val isHuawei: Boolean
        get() = check(ROM_EMUI)
    val isMiui: Boolean
        get() = check(ROM_MIUI)
    @JvmStatic
    val isVivo: Boolean
        get() = check(ROM_VIVO)
    @JvmStatic
    val isOppo: Boolean
        get() = check(ROM_OPPO)
    val isFlyme: Boolean
        get() = check(ROM_FLYME)

    fun is360(): Boolean {
        return check(ROM_QIKU) || check("360")
    }

    val isSmartisan: Boolean
        get() = check(ROM_SMARTISAN)
    val name: String?
        get() {
            if (sName == null) {
                check("")
            }
            return sName
        }
    val version: String?
        get() {
            if (sVersion == null) {
                check("")
            }
            return sVersion
        }

    private fun check(rom: String): Boolean {
        val str = sName
        do {
            if (str != null) {
                break
            }
            sVersion = getProp(KEY_VERSION_MIUI)
            if (!TextUtils.isEmpty(sVersion)) {
                sName = ROM_MIUI
                break
            }
            sVersion = getProp(KEY_VERSION_EMUI)
            if (!TextUtils.isEmpty(sVersion)) {
                sName = ROM_EMUI
                break
            }
            sVersion = getProp(KEY_VERSION_OPPO)
            if (!TextUtils.isEmpty(sVersion)) {
                sName = ROM_OPPO
                break
            }
            sVersion = getProp(KEY_VERSION_VIVO)
            if (!TextUtils.isEmpty(sVersion)) {
                sName = ROM_VIVO
                break
            }
            sVersion = getProp(KEY_VERSION_SMARTISAN)
            if (!TextUtils.isEmpty(sVersion)) {
                sName = ROM_SMARTISAN
                break
            }
            sVersion = Build.DISPLAY
            if (sVersion!!.uppercase(Locale.getDefault()).contains(ROM_FLYME)) {
                sName = ROM_FLYME
                break
            }
            sVersion = "unknown"
            sName = Build.MANUFACTURER.uppercase(Locale.getDefault())
        } while (false)
        ILog.d(TAG, "version is $sVersion , rom name is $sName")
        return sName == rom
    }

    private fun getProp(name: String): String? {
        var input: BufferedReader? = null
        return try {
            try {
                val runtime = Runtime.getRuntime()
                val p = runtime.exec("getprop $name")
                input = BufferedReader(InputStreamReader(p.inputStream), 1024)
                val line = input.readLine()
                input.close()
                try {
                    input.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
                line
            } catch (ex: IOException) {
                Log.e(TAG, "Unable to read prop $name", ex)
                if (input != null) {
                    try {
                        input.close()
                    } catch (e2: IOException) {
                        e2.printStackTrace()
                    }
                }
                null
            }
        } catch (th: Throwable) {
            if (input != null) {
                try {
                    input.close()
                } catch (e3: IOException) {
                    e3.printStackTrace()
                }
            }
            throw th
        }
    }
}