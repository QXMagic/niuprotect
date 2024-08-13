package accessibility.lib;

import android.app.AppOpsManager;
import android.app.KeyguardManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.PowerManager;
import android.os.Process;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.RomUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import accessibility.Mgr;
import accessibility.Tools;

/* compiled from: AppHelper.java */
/* renamed from: 璙冝耝.肌緭 */
/* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
public class AppHelper {

    /* renamed from: 肌緭 */
    public static final List<C6418> f13462 = new ArrayList();

    /* renamed from: 刻槒唱镧詴 */
    public static long f21945 = 0;

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
    public static List<C6418> f21947 = null;

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
    public static final Map<String, C6418> f21948 = new HashMap();

    /* renamed from: 灞酞輀攼嵞漁綬迹 */
    public static long f21946 = 0;

    /* compiled from: AppHelper.java */
    /* renamed from: 璙冝耝.肌緭$刻槒唱镧詴 */
    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
    public class C6418 {

        /* renamed from: 刻槒唱镧詴 */
        public long f21949;

        /* renamed from: 肌緭 */
        public ComponentName f13463;

        public C6418(long j, ComponentName componentName) {
            this.f21949 = j;
            this.f13463 = componentName;
        }
    }

    public class C6419 extends ArrayList<C6418> {

        /* renamed from: 肌緭 */
        public final /* synthetic */ C6418 f13464;

        public C6419(C6418 c6418) {
            this.f13464 = c6418;
            add(c6418);
        }
    }

    /* renamed from: 偣炱嘵蟴峗舟轛 */
    public static void m24150(Context context, boolean z) {
        if (ExamineUtils.m24111() || Mgr.getGuardIntercept().mo22192()) {
            return;
        }
        if (!RomUtils.isVivo() || Build.VERSION.SDK_INT < 29) {
            if (RomUtils.isOppo()) {
                int i = Build.VERSION.SDK_INT;
                if (i < 28 && !z) {
                    return;
                }
                if (i >= 30) {
                    m24157(context);
                    m24159(context);
                    return;
                }
            }
            if ((!RomUtils.isHuawei() || Build.VERSION.SDK_INT < 29) && !RomUtils.isGoogle()) {
                m24157(context);
            }
        }
    }

    @Nullable
    /* renamed from: 刻槒唱镧詴 */
    public static Intent m24151(Context context, String str) {
        try {
            return context.getPackageManager().getLaunchIntentForPackage(str);
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: 垡玖 */
    public static List<C6418> m14901() {
        List<C6418> list;
        synchronized (AppHelper.class) {
            list = f13462;
            if (list.isEmpty()) {
//                list.add(new C6418(0L, new ComponentName("", "")));
            }
        }
        return list;
    }

    /* renamed from: 旞莍癡 */
    public static int m24152(int i) {
        return Build.VERSION.SDK_INT >= 23 ? i | 67108864 : i;
    }

    /* renamed from: 朽劔蚁灋嵿齩鶴琓麃沼瀙缹 */
    public static List<C6418> m24153(UsageStatsManager usageStatsManager, long j) {
        HashMap<String, C6418> m24163 = m24163(usageStatsManager.queryEvents(j, System.currentTimeMillis()));
        if (m24163 != null) {
            return new ArrayList(m24163.values());
        }
        return null;
    }

    /* renamed from: 櫓昛刓叡賜 */
    public static boolean m24154(Context context) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.addCategory("android.intent.category.DEFAULT");
        ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(intent, 65536);
        return resolveActivity != null && ObjectUtils.equals(context.getPackageName(), resolveActivity.activityInfo.packageName);
    }

    @NonNull
    /* renamed from: 灞酞輀攼嵞漁綬迹 */
    public static String m24155() {
        return "com.zlfc.child.mvvm.activity.DeskLauncherActivity";
    }

    @Nullable
    /* renamed from: 睳堋弗粥辊惶 */
    public static List<C6418> m24156(Context context) {
        UsageEvents queryEvents;
        synchronized (AppHelper.class) {
            if (isLocked(context)) {
                return null;
            }
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            if (appOpsManager == null) {
                return null;
            }
            if (appOpsManager.checkOpNoThrow("android:get_usage_stats", Process.myUid(), context.getPackageName()) != 0) {
                return null;
            }
            UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            if (f21946 > System.currentTimeMillis()) {
                f21948.clear();
            }
            if (f21948.isEmpty()) {
                long currentTimeMillis = System.currentTimeMillis() - 1440000;
                f21946 = currentTimeMillis;
                queryEvents = usageStatsManager.queryEvents(currentTimeMillis, System.currentTimeMillis());
                if (!queryEvents.hasNextEvent()) {
                    long currentTimeMillis2 = System.currentTimeMillis() - 3600000;
                    f21946 = currentTimeMillis2;
                    queryEvents = usageStatsManager.queryEvents(currentTimeMillis2, System.currentTimeMillis());
                }
                if (!queryEvents.hasNextEvent()) {
                    long currentTimeMillis3 = System.currentTimeMillis() - 10800000;
                    f21946 = currentTimeMillis3;
                    queryEvents = usageStatsManager.queryEvents(currentTimeMillis3, System.currentTimeMillis());
                }
                if (!queryEvents.hasNextEvent()) {
                    long currentTimeMillis4 = System.currentTimeMillis() - 86400000;
                    f21946 = currentTimeMillis4;
                    queryEvents = usageStatsManager.queryEvents(currentTimeMillis4, System.currentTimeMillis());
                }
            } else {
                queryEvents = usageStatsManager.queryEvents(f21946, System.currentTimeMillis());
            }
            long j = 0;
            String str = null;
            String str2 = null;
            while (queryEvents.hasNextEvent()) {
                UsageEvents.Event event = new UsageEvents.Event();
                queryEvents.getNextEvent(event);
                String packageName = event.getPackageName();
                String className = event.getClassName();
                if (className != null && !packageName.equals("android")) {
                    if (packageName.equals("com.android.systemui") && className.equals("com.android.systemui.recents.RecentsActivity") && !RomUtils.isVivo() && !RomUtils.isOneplus() && !RomUtils.isSamsung() && !RomUtils.isGoogle()) {
                        packageName = "com.android.systemui.custom.zlfcapp";
                    }
                    if (event.getEventType() != 1) {
                        if (event.getEventType() == 2) {
                            f21948.remove(packageName);
                        }
                    } else if (event.getTimeStamp() >= j) {
                        j = event.getTimeStamp();
                        str2 = packageName;
                        str = className;
                    }
                }
            }
//            if (str != null && str2 != null) {
//                C6418 c6418 = new C6418(j, new ComponentName(str2, str));
//                f21946 = j;
//                f21948.put(str2, c6418);
//                return new C6419(c6418);
//            }
            Map<String, C6418> map = f21948;
            if (map.isEmpty()) {
                return null;
            }
            return new ArrayList(map.values());
        }
    }

    /* renamed from: 瞙餃莴埲 */
    public static void m24157(Context context) {
        try {
            if (context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()) == null) {
                return;
            }
            String m24160 = m24160();
            PackageManager packageManager = context.getPackageManager();
            ComponentName componentName = new ComponentName(context.getPackageName(), m24160);
            if (packageManager.getComponentEnabledSetting(componentName) == PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {
                return;
            }
            packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: 祴嚚橺谋肬鬧舘 */
    public static List<C6418> m24158(Context context) {
        if (System.currentTimeMillis() - f21945 > 100) {
            synchronized (AppHelper.class) {
                f21945 = System.currentTimeMillis();
                try {
                    AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                    if (appOpsManager == null) {
                        return m14901();
                    }
                    if (appOpsManager.checkOpNoThrow("android:get_usage_stats", Process.myUid(), context.getPackageName()) != 0) {
                        return m14901();
                    }
                    List<C6418> m14904 = m14904(context);
                    if (m14904 == null) {
                        return m14901();
                    }
                    f21947 = m14904;
                    return m14904;
                } catch (Exception e) {
                    e.printStackTrace();
                    return m14901();
                }
            }
        }
        List<C6418> list = f21947;
        return list == null ? m14901() : list;
    }

    /* renamed from: 綩私 */
    public static boolean m14902(Context context) {
        return !(RomUtils.isVivo() && ObjectUtils.equals(Tools.m22505("persist.home.restr.enable"), "true")) && m24154(context);
    }

    /* renamed from: 耣怳匮色紝参凵蛴纆勚躄 */
    public static void m24159(Context context) {
        try {
            if (context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()) != null) {
                return;
            }
            PackageManager packageManager = context.getPackageManager();
            ComponentName componentName = new ComponentName(context.getPackageName(), m24160());
            if (packageManager.getComponentEnabledSetting(componentName) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
                return;
            }
            packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: 肌緭 */
    public static void m14903(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ComponentName componentName = new ComponentName(context.getPackageName(), m24155());
        if (packageManager.getComponentEnabledSetting(componentName) != PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        }
    }

    @NonNull
    /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
    public static String m24160() {
        return ModelManager.m22781();
    }

    /* renamed from: 蝸餺閃喍 */
    public static boolean isLocked(Context context) {
        if (context == null) {
            return false;
        }
        try {
            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            if (Build.VERSION.SDK_INT >= 22 && keyguardManager != null) {
                if (keyguardManager.isKeyguardSecure()) {
                    return keyguardManager.isKeyguardLocked();
                }
                if (keyguardManager.inKeyguardRestrictedInputMode()) {
                    return true;
                }
            }
            if (((PowerManager) context.getSystemService(Context.POWER_SERVICE)) == null) {
                return false;
            }
            return true;
//            return !r3.isInteractive();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                return !((PowerManager) context.getSystemService(Context.POWER_SERVICE)).isScreenOn();
            } catch (Exception e2) {
                e2.printStackTrace();
                return false;
            }
        }
    }

    /* renamed from: 酸恚辰橔纋黺 */
    public static void m24162(Context context) {
        m24150(context, false);
    }

    @Nullable
    /* renamed from: 镐藻 */
    public static List<C6418> m14904(Context context) {
        synchronized (AppHelper.class) {
            if (Build.VERSION.SDK_INT <= 25) {
                return m24156(context);
            }
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            if (appOpsManager == null) {
                return null;
            }
            if (appOpsManager.checkOpNoThrow("android:get_usage_stats", Process.myUid(), context.getPackageName()) != 0) {
                return null;
            }
            UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            List<C6418> m24153 = m24153(usageStatsManager, System.currentTimeMillis() - 600000);
            if (m24153 != null) {
                return m24153;
            }
            List<C6418> m241532 = m24153(usageStatsManager, System.currentTimeMillis() - 3600000);
            if (m241532 != null) {
                return m241532;
            }
            List<C6418> m241533 = m24153(usageStatsManager, System.currentTimeMillis() - 10800000);
            if (m241533 != null) {
                return m241533;
            }
            List<C6418> m241534 = m24153(usageStatsManager, System.currentTimeMillis() - 86400000);
            if (m241534 != null) {
                return m241534;
            }
            return null;
        }
    }

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
    public static HashMap<String, C6418> m24163(UsageEvents usageEvents) {
        HashMap<String, C6418> hashMap = new HashMap<>();
        while (usageEvents.hasNextEvent()) {
            UsageEvents.Event event = new UsageEvents.Event();
            usageEvents.getNextEvent(event);
            String packageName = event.getPackageName();
            String className = event.getClassName();
            if (className != null && !ObjectUtils.equals("android", packageName) && !ObjectUtils.isEmpty((CharSequence) packageName)) {
                if (packageName.equals("com.android.systemui") && className.equals("com.android.systemui.recents.RecentsActivity") && !RomUtils.isVivo() && !RomUtils.isOneplus() && !RomUtils.isSamsung() && !RomUtils.isGoogle()) {
                    packageName = "com.android.systemui.custom.zlfcapp";
                }
                if (event.getEventType() != 2 && event.getEventType() != 2 && event.getEventType() != 23 && event.getEventType() != 24) {
                    if (event.getEventType() == 1 || event.getEventType() == 1) {
                        long timeStamp = event.getTimeStamp();
                        long j = f21946;
                        if (timeStamp > j && j <= System.currentTimeMillis()) {
                            f21946 = event.getTimeStamp();
                        }
//                        hashMap.put(packageName + className, new C6418(event.getTimeStamp(), new ComponentName(packageName, className)));
                    }
                } else {
                    hashMap.remove(packageName + className);
                }
            }
        }
        if (hashMap.isEmpty()) {
            return null;
        }
        return hashMap;
    }
}
