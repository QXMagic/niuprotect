package com.niu.protect.accessibility.auto.service.tmp;

import com.tencent.mmkv.MMKV;

public class C6891 {

    /* renamed from: 刻槒唱镧詴, reason: contains not printable characters */
    public static MMKV f23348;

    /* renamed from: 肌緭 */
    public static MMKV f14232;

    /* renamed from: 偣炱嘵蟴峗舟轛, reason: contains not printable characters */
    public static void m25852(String str, int i) {
        m25853().encode(str, i);
    }

    public static MMKV m25853() {
        if (f23348 == null) {
            f23348 = MMKV.mmkvWithID("guard", 2);
        }
        return f23348;
    }

    /* renamed from: 垡玖 */
    public static double m15530(String str, double d) {
        return instance().decodeDouble(str, d);
    }

    /* renamed from: 旞莍癡, reason: contains not printable characters */
    public static int m25854(String str, int i) {
        return instance().decodeInt(str, i);
    }

    /* renamed from: 朽劔蚁灋嵿齩鶴琓麃沼瀙缹, reason: contains not printable characters */
    public static void m25855(String str, long j) {
        instance().encode(str, j);
    }

    /* renamed from: 櫓昛刓叡賜, reason: contains not printable characters */
    public static void m25856(String str, long j) {
        m25853().encode(str, j);
    }

    /* renamed from: 灞酞輀攼嵞漁綬迹, reason: contains not printable characters */
    public static boolean m25857(String str, boolean z) {
        return m25853().decodeBool(str, z);
    }

    /* renamed from: 睳堋弗粥辊惶, reason: contains not printable characters */
    public static boolean m25858(String str, boolean z) {
        return instance().decodeBool(str, z);
    }

    /* renamed from: 瞙餃莴埲, reason: contains not printable characters */
    public static void m25859(String str, String str2) {
        instance().encode(str, str2);
    }

    /* renamed from: 祴嚚橺谋肬鬧舘, reason: contains not printable characters */
    public static long m25860(String str, long j) {
        return instance().decodeLong(str, j);
    }

    /* renamed from: 綩私 */
    public static void m15531(String str, int i) {
        instance().encode(str, i);
    }

    /* renamed from: 耣怳匮色紝参凵蛴纆勚躄, reason: contains not printable characters */
    public static void m25861(String str, boolean z) {
        instance().encode(str, z);
    }

    /* renamed from: 肌緭 */
    public static MMKV instance() {
        if (f14232 == null) {
            f14232 = MMKV.defaultMMKV();
        }
        return f14232;
    }

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭, reason: contains not printable characters */
    public static int m25862(String str, int i) {
        return m25853().decodeInt(str, i);
    }

    /* renamed from: 蝸餺閃喍, reason: contains not printable characters */
    public static void m25863(String str, boolean z) {
        m25853().decodeBool(str, z);
    }

    /* renamed from: 酸恚辰橔纋黺, reason: contains not printable characters */
    public static void m25864(String str, float f) {
        m25853().decodeFloat(str, f);
    }

    /* renamed from: 镐藻 */
    public static String m15533(String str, String str2) {
        return instance().decodeString(str, str2);
    }

    /* renamed from: 陟瓠魒踱褢植螉嚜, reason: contains not printable characters */
    public static void m25865(String str) {
        m15531(str, m25854(str, 0) + 1);
    }

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐, reason: contains not printable characters */
    public static long m25866(String str, long j) {
        return m25853().decodeLong(str, j);
    }
}