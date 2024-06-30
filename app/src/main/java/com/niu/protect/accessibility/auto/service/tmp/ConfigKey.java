package com.niu.protect.accessibility.auto.service.tmp;

import androidx.annotation.IntRange;

import com.niu.protect.accessibility.auto.service.AccessibilityHelperService;

public final class ConfigKey {

    /* renamed from: 肌緭 */
    public static boolean f14714;

    public static boolean m27235() {
        return WorkService.f11899 != null;
    }

    public static int m27236() {
        return C6891.m25862("lockscreeen", 0);
    }

    public static void m27237(int i) {
        C6891.m25852("lockscreeen", i);
    }

    public static boolean m16029() {
        return C6891.m25862("is_star_first", 0) == 2;
    }

    public static void m27238(@IntRange(from = 1, to = 2) int i) {
        C6891.m25852("is_star_first", i);
    }

    public static void m27239(@IntRange(from = 0, to = 1) int i) {
        C6891.m25852("permission_status", i);
        if (i == 0) {
            AccessibilityHelperService.m19965(true);
        }
    }

    /* renamed from: 旞莍癡, reason: contains not printable characters */
    public static boolean m27240() {
        return m27244() && m27247();
    }

    /* renamed from: 朽劔蚁灋嵿齩鶴琓麃沼瀙缹, reason: contains not printable characters */
    public static boolean m27241() {
        return C6891.m25858("vpn_web_open", false);
    }

    /* renamed from: 杹藗瀶姙笻件稚嵅蔂, reason: contains not printable characters */
    public static void m27242(boolean z) {
        C6891.m25861("vpn_web_open", z);
    }

    /* renamed from: 櫓昛刓叡賜, reason: contains not printable characters */
    public static boolean m27243() {
        return m27249() != 0;
    }

    /* renamed from: 灞酞輀攼嵞漁綬迹, reason: contains not printable characters */
    public static boolean m27244() {
        return C6891.instance().getInt("model_choose", 0) == 1;
    }

    /* renamed from: 睳堋弗粥辊惶, reason: contains not printable characters */
    public static boolean m27245() {
        return m27236() != 0;
    }

    /* renamed from: 瞙餃莴埲, reason: contains not printable characters */
    public static void m27246() {
        m27251(2);
    }

    /* renamed from: 祴嚚橺谋肬鬧舘, reason: contains not printable characters */
    public static boolean m27247() {
        return C6891.m25862("is_star_first", 0) == 1;
    }

    /* renamed from: 綩私 */
    public static boolean m16030() {
        return C6891.m25857("vip_overdue", false);
    }

    /* renamed from: 纩慐 */
    public static void m16031(boolean z) {
        C6891.m25863("tem_unlock_status", z);
    }

    /* renamed from: 耣怳匮色紝参凵蛴纆勚躄, reason: contains not printable characters */
    public static void m27248() {
        m27251(1);
    }

    /* renamed from: 肌緭 */
    public static void m16032() {
        m27251(0);
    }

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭, reason: contains not printable characters */
    public static int m27249() {
        return C6891.m25862("work_statue", 0);
    }

    /* renamed from: 蝸餺閃喍, reason: contains not printable characters */
    public static boolean m27250() {
        return C6891.m25857("tem_unlock_status", false);
    }

    /* renamed from: 辒迳圄袡皪郞箟, reason: contains not printable characters */
    public static void m27251(int i) {
        C6891.m25852("work_statue", i);
    }

    /* renamed from: 酸恚辰橔纋黺, reason: contains not printable characters */
    public static boolean m27252() {
        return C6891.m25862("permission_status", 0) == 1;
    }

    /* renamed from: 鑭撇糁綖浓緗轟鱼萟磿焈, reason: contains not printable characters */
    public static void m27253() {
        C6891.m25863("bindingStatus", true);
    }

    /* renamed from: 销薞醣戔攖餗, reason: contains not printable characters */
    public static void m27254() {
        C6891.m25852("lockscreeen", 1);
    }

//    public static boolean m16033(String str) {
//        return OffLineUtils.m21250(str);
//    }

    /* renamed from: 陟瓠魒踱褢植螉嚜, reason: contains not printable characters */
    public static void m27255() {
        C6891.m25852("lockscreeen", 0);
    }

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐, reason: contains not printable characters */
    public static boolean m27256() {
        return C6891.m25857("bindingStatus", false);
    }

    /* renamed from: 韐爮幀悖罤噩钼遑杯盇, reason: contains not printable characters */
    public static void m27257(boolean z) {
        C6891.m25863("vip_overdue", z);
    }
}

class DateUtil{

    public static long m14313(long parseLong) {
        return parseLong;
    }
}


