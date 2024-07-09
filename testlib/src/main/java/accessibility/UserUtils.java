package accessibility;

import com.blankj.utilcode.util.AppUtils;

import accessibility.lib.DataKV;

public class UserUtils {
    /* renamed from: 刻槒唱镧詴, reason: contains not printable characters */
    public static boolean m24165() {
        return DataKV.m25857("isPauseManager", false) || m24167();
    }

    /* renamed from: 垡玖 */
    public static void m14906(boolean z) {
        DataKV.m25863("isUpdate", z);
        if (z) {
            DataKV.m25852("updateVersion", AppUtils.getAppVersionCode());
        }
    }

    /* renamed from: 灞酞輀攼嵞漁綬迹, reason: contains not printable characters */
    public static void m24166(boolean z) {
        LogHelper.instance().m16303("手机状态", "设置管控状态:" + z);
        DataKV.m25863("isPauseManager", z);
        if (z) {
            return;
        }
        DataKV.m25863("isUpdate", false);
    }

    /* renamed from: 肌緭 */
    public static boolean m14907() {
        return DataKV.m25857("has_call_phone", false);
    }

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭, reason: contains not printable characters */
    public static boolean m24167() {
        return DataKV.m25857("isUpdate", false);
    }

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐, reason: contains not printable characters */
    public static void m24168(boolean z) {
        DataKV.m25863("has_call_phone", z);
    }
}
