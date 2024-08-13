package accessibility;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import java.util.concurrent.atomic.AtomicBoolean;

public class SafeGuardManager {

    public static SafeGuardManager f14122;
//
//    /* renamed from: 偣炱嘵蟴峗舟轛, reason: contains not printable characters */
//    public final FunctionFilter f23167;
//
//    /* renamed from: 刻槒唱镧詴, reason: contains not printable characters */
    public Context f23168;
//
//    /* renamed from: 櫓昛刓叡賜, reason: contains not printable characters */
    public final Handler f23170;
//
//    /* renamed from: 灞酞輀攼嵞漁綬迹, reason: contains not printable characters */
//    public final String f23171;
//
//    /* renamed from: 睳堋弗粥辊惶, reason: contains not printable characters */
//    public final AtomicBoolean f23172;
//
//    /* renamed from: 肌緭 */
    public Handler f14124;
//
//    /* renamed from: 葋申湋骶映鍮秄憁鎓羭, reason: contains not printable characters */
//    public AppFilter f23174;
//
//    /* renamed from: 酸恚辰橔纋黺, reason: contains not printable characters */
    public final AtomicBoolean f23176;
//
//    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐, reason: contains not printable characters */
//    public String f23177;
//
//    /* renamed from: 垡玖 */
//    public long f14123 = 1000;
//
//    /* renamed from: 旞莍癡, reason: contains not printable characters */
//    public long f23169 = 0;
//
//    /* renamed from: 祴嚚橺谋肬鬧舘, reason: contains not printable characters */
//    public boolean f23173 = false;
//
//    /* renamed from: 镐藻 */
//    public final List<String> f14125 = new ArrayList();
//
//    /* renamed from: 蝸餺閃喍, reason: contains not printable characters */
//    public Runnable f23175 = new RunnableC6836();
//
//    /* JADX WARN: Classes with same name are omitted:
//  classes5.dex
// */
//    /* compiled from: SafeGuardManager.java */
//    /* renamed from: 蛁淒圏糢.肌緭$刻槒唱镧詴, reason: contains not printable characters */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class RunnableC6835 implements Runnable {
//        public RunnableC6835() {
//        }
//
//        @Override // java.lang.Runnable
//        public final void run() {
//            SafeGuardManager.this.m25648();
//        }
//    }
//
//    /* JADX WARN: Classes with same name are omitted:
//  classes5.dex
// */
//    /* compiled from: SafeGuardManager.java */
//    /* renamed from: 蛁淒圏糢.肌緭$肌緭, reason: contains not printable characters */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class RunnableC6836 implements Runnable {
//        public RunnableC6836() {
//        }
//
//        @Override // java.lang.Runnable
//        public void run() {
//            if (AppHelper.m24161(SafeGuardManager.this.f23168)) {
//                LogHelper.m27986().m16303("拦截", "检测到锁屏，拦截线程1分钟后启动");
//                SafeGuardManager.this.f23177 = null;
//                SafeGuardManager.this.m25656();
//                SafeGuardManager.this.m25662(this, 60000L);
//                return;
//            }
//            if (ConfigKey.m16029()) {
//                SafeGuardManager.this.f23177 = null;
//                SafeGuardManager.this.m25646();
//                Event event = new Event();
//                event.setCode(105);
//                EventBus.m20451().m20455(event);
//                LogHelper.m27986().m16303("拦截", "线程关闭,孩子端处于关闭状态");
//                return;
//            }
//            if (PermissionCheckActivity.m20025() || UserUtils.m24165()) {
//                SafeGuardManager.this.f23177 = null;
//                SafeGuardManager.this.m25662(this, DateUtils.TEN_SECOND);
//                LogHelper.m27986().m16303("拦截", "线程暂停，原因：1.开权限:" + PermissionCheckActivity.m20025() + "2.暂停管控:" + UserUtils.m24165());
//                return;
//            }
//            List<AppHelper.C6418> m25667 = SafeGuardManager.this.m25667();
//            if (SafeGuardManager.this.m25643(m25667)) {
//                SafeGuardManager.this.f23177 = null;
//                SafeGuardManager.this.m25647(this);
//                LogHelper.m27986().m16303("拦截", "未获取到顶层App运行信息,无法进行拦截操作");
//                return;
//            }
//            SafeGuardManager.this.f14125.clear();
//            Iterator<AppHelper.C6418> it = m25667.iterator();
//            while (it.hasNext()) {
//                ComponentName componentName = it.next().f13463;
//                SafeGuardManager.this.f14125.add(componentName.getPackageName());
//                if (SafeGuardManager.this.m15461(componentName)) {
//                    break;
//                }
//            }
//            SafeGuardManager.this.m25647(this);
//        }
//    }
//
//    /* JADX WARN: Classes with same name are omitted:
//  classes5.dex
// */
//    /* compiled from: SafeGuardManager.java */
//    /* renamed from: 蛁淒圏糢.肌緭$葋申湋骶映鍮秄憁鎓羭, reason: contains not printable characters */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class C6837 implements InterfaceC3802 {
//
//        /* JADX WARN: Classes with same name are omitted:
//  classes5.dex
// */
//        /* compiled from: SafeGuardManager.java */
//        /* renamed from: 蛁淒圏糢.肌緭$葋申湋骶映鍮秄憁鎓羭$肌緭, reason: contains not printable characters */
//        /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//        public class C6838 implements InterfaceC3802 {
//            public C6838() {
//            }
//
//            @Override // p337.InterfaceC3802
//            /* renamed from: 刻槒唱镧詴 */
//            public void mo23827() {
//                LogHelper.m16301("设置返回");
//                AccessibilityHelperService.m19973();
//            }
//
//            @Override // p337.InterfaceC3802
//            /* renamed from: 肌緭 */
//            public void mo14756() {
//                SafeUtils.m15457(App.mContext);
//                SafeSettingView m23557 = SafeSettingView.m23557();
//                m23557.mo23566();
//                m23557.m23570("禁止频繁操作");
//                DataKV.m25861("setting_intercept", false);
//                SafeGuardManager.this.f23167.m23829();
//            }
//        }
//
//        public C6837() {
//        }
//
//        @Override // p337.InterfaceC3802
//        /* renamed from: 刻槒唱镧詴 */
//        public void mo23827() {
//            if (AccessibilityHelperService.f19276 == null) {
//                SafeUtils.m15457(App.mContext);
//                SafeSettingView m23557 = SafeSettingView.m23557();
//                m23557.mo23566();
//                m23557.m23570("禁止频繁操作");
//                DataKV.m25861("setting_intercept", false);
//                SafeGuardManager.this.f23167.m23829();
//                return;
//            }
//            FunctionInterval.m23830(5, 200L, 0L, new C6838());
//        }
//
//        @Override // p337.InterfaceC3802
//        /* renamed from: 肌緭 */
//        public /* synthetic */ void mo14756() {
//            C6374.m14757(this);
//        }
//    }
//
//    /* JADX WARN: Classes with same name are omitted:
//  classes5.dex
// */
//    /* compiled from: SafeGuardManager.java */
//    /* renamed from: 蛁淒圏糢.肌緭$鞈鵚主瀭孩濣痠閕讠陲檓敐, reason: contains not printable characters */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class C6839 implements InterfaceC3802 {
//
//        /* JADX WARN: Classes with same name are omitted:
//  classes5.dex
// */
//        /* compiled from: SafeGuardManager.java */
//        /* renamed from: 蛁淒圏糢.肌緭$鞈鵚主瀭孩濣痠閕讠陲檓敐$刻槒唱镧詴, reason: contains not printable characters */
//        /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//        public class C6840 implements InterfaceC3802 {
//
//            /* JADX WARN: Classes with same name are omitted:
//  classes5.dex
// */
//            /* compiled from: SafeGuardManager.java */
//            /* renamed from: 蛁淒圏糢.肌緭$鞈鵚主瀭孩濣痠閕讠陲檓敐$刻槒唱镧詴$肌緭, reason: contains not printable characters */
//            /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//            public class RunnableC6841 implements Runnable {
//
//                /* JADX WARN: Classes with same name are omitted:
//  classes5.dex
// */
//                /* compiled from: SafeGuardManager.java */
//                /* renamed from: 蛁淒圏糢.肌緭$鞈鵚主瀭孩濣痠閕讠陲檓敐$刻槒唱镧詴$肌緭$肌緭, reason: contains not printable characters */
//                /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//                public class RunnableC6842 implements Runnable {
//                    public RunnableC6842() {
//                    }
//
//                    @Override // java.lang.Runnable
//                    public void run() {
//                        SafeGuardManager.this.f23167.m23829();
//                    }
//                }
//
//                public RunnableC6841() {
//                }
//
//                @Override // java.lang.Runnable
//                public void run() {
//                    ModelManager.m22790(null);
//                    SafeGuardManager.this.f23170.postDelayed(new RunnableC6842(), 200L);
//                }
//            }
//
//            public C6840() {
//            }
//
//            @Override // p337.InterfaceC3802
//            /* renamed from: 刻槒唱镧詴 */
//            public void mo23827() {
//                LogHelper.m16301("设置返回");
//                AccessibilityHelperService.m19973();
//            }
//
//            @Override // p337.InterfaceC3802
//            /* renamed from: 肌緭 */
//            public void mo14756() {
//                AccessibilityHelperService.m19967();
//                SafeGuardManager.this.f23170.postDelayed(new RunnableC6841(), 500L);
//            }
//        }
//
//        /* JADX WARN: Classes with same name are omitted:
//  classes5.dex
// */
//        /* compiled from: SafeGuardManager.java */
//        /* renamed from: 蛁淒圏糢.肌緭$鞈鵚主瀭孩濣痠閕讠陲檓敐$肌緭, reason: contains not printable characters */
//        /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//        public class RunnableC6843 implements Runnable {
//
//            /* JADX WARN: Classes with same name are omitted:
//  classes5.dex
// */
//            /* compiled from: SafeGuardManager.java */
//            /* renamed from: 蛁淒圏糢.肌緭$鞈鵚主瀭孩濣痠閕讠陲檓敐$肌緭$肌緭, reason: contains not printable characters */
//            /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//            public class RunnableC6844 implements Runnable {
//                public RunnableC6844() {
//                }
//
//                @Override // java.lang.Runnable
//                public void run() {
//                    SafeGuardManager.this.f23167.m23829();
//                }
//            }
//
//            public RunnableC6843() {
//            }
//
//            @Override // java.lang.Runnable
//            public void run() {
//                ModelManager.m22790(null);
//                SafeGuardManager.this.f23170.postDelayed(new RunnableC6844(), 200L);
//            }
//        }
//
//        public C6839() {
//        }
//
//        @Override // p337.InterfaceC3802
//        /* renamed from: 刻槒唱镧詴 */
//        public void mo23827() {
//            if (AccessibilityHelperService.f19276 == null) {
//                SafeUtils.m15457(App.mContext);
//                SafeGuardManager.this.f23170.postDelayed(new RunnableC6843(), 1000L);
//            } else {
//                FunctionInterval.m23830(2, 200L, 0L, new C6840());
//            }
//        }
//
//        @Override // p337.InterfaceC3802
//        /* renamed from: 肌緭 */
//        public /* synthetic */ void mo14756() {
//            C6374.m14757(this);
//        }
//    }
//
    public SafeGuardManager(Context context) {
        this.f23168 = context;
//        AppFilter m15534 = AppFilter.m15534(context);
//        this.f23174 = m15534;
//        this.f23171 = m15534.m25925();
//        this.f23172 = new AtomicBoolean(ConfigKey.m16030());
        this.f23176 = new AtomicBoolean(Mgr.getStatusMgr().mo14472());
//        this.f23167 = new FunctionFilter();
        this.f23170 = new Handler();
        m25641();
    }
//
//    /* renamed from: 销薞醣戔攖餗, reason: contains not printable characters */
    public static SafeGuardManager m25638(Context context) {
        synchronized (SafeGuardManager.class) {
            if (f14122 == null) {
                f14122 = new SafeGuardManager(context);
            }
        }
        return f14122;
    }
//
//    /* renamed from: 偣炱嘵蟴峗舟轛, reason: contains not printable characters */
//    public final boolean m25640(ComponentName componentName) {
//        if (AccessibilityHelperService.f19276 != null) {
//            return false;
//        }
//        String packageName = componentName.getPackageName();
//        if (!"com.android.systemui.custom.zlfcapp".equals(packageName)) {
//            return false;
//        }
//        SafeScreenLockView m23580 = SafeScreenLockView.m23580();
//        m23580.mo23566();
//        m23580.m23586("设备守护中");
//        m25653(packageName);
//        return true;
//    }
//
//    /* renamed from: 卝閄侸靤溆鲁扅, reason: contains not printable characters */
    public final void m25641() {
        if (this.f14124 != null) {
            return;
        }
        HandlerThread handlerThread = new HandlerThread("topAppCheck", -1);
        handlerThread.start();
        this.f14124 = new Handler(handlerThread.getLooper());
    }
//
    public void m25642(boolean z) {
        this.f23176.set(z);
    }
//
//    /* renamed from: 唌橅咟, reason: contains not printable characters */
//    public boolean m25643(List<AppHelper.C6418> list) {
//        Iterator<AppHelper.C6418> it = list.iterator();
//        while (it.hasNext()) {
//            if (TextUtils.isEmpty(it.next().f13463.getPackageName())) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /* renamed from: 壋劘跆貭澴綄秽攝煾訲, reason: contains not printable characters */
//    public final boolean m25644(String str, String str2) {
//        return !TextUtils.isEmpty(str) && str2.equals(str);
//    }
//
//    /* renamed from: 彻薯铏螙憣欖愡鼭, reason: contains not printable characters */
//    public final boolean m25645(ComponentName componentName) {
//        return this.f23174.m25913(componentName.getPackageName()) || this.f23174.m25903(componentName.getClassName());
//    }
//
//    /* renamed from: 攏瑹迀虚熂熋卿悍铒誦爵, reason: contains not printable characters */
//    public void m25646() {
//        Handler handler = this.f14124;
//        if (handler != null) {
//            handler.removeCallbacksAndMessages(null);
//        }
//        this.f23173 = true;
//    }
//
//    /* renamed from: 斃燸卺驼暲各撟嫺眧樬硱, reason: contains not printable characters */
//    public final void m25647(Runnable runnable) {
//        m25662(runnable, this.f14123);
//    }
//
//    /* renamed from: 旞莍癡, reason: contains not printable characters */
//    public void m25648() {
//        if (AppHelper.m24161(this.f23168)) {
//            GuardFloatUtils.m15496();
//            m25661();
//            return;
//        }
//        if (this.f14124 == null) {
//            m25641();
//        }
//        List<AppHelper.C6418> m25667 = m25667();
//        if (m25643(m25667)) {
//            this.f23177 = null;
//            LogHelper.m16301("checkBackApp=>查询到顶层App的数量为0");
//            m25653(null);
//            m25661();
//            return;
//        }
//        LogHelper.m16301("checkBackApp=>查询到顶层App的数量:" + m25667.size());
//        Iterator<AppHelper.C6418> it = m25667.iterator();
//        while (it.hasNext() && !m15461(it.next().f13463)) {
//        }
//        m25661();
//    }
//
//    /* renamed from: 朽劔蚁灋嵿齩鶴琓麃沼瀙缹, reason: contains not printable characters */
//    public void m25649() {
//        if (this.f23169 <= 0 || System.currentTimeMillis() - this.f23169 <= DateUtils.TEN_SECOND || this.f23173) {
//            return;
//        }
//        LogHelper.m27986().m27999("重启Safe线程成功");
//        m25661();
//    }
//
//    /* renamed from: 杹藗瀶姙笻件稚嵅蔂, reason: contains not printable characters */
//    public final HashMap<String, ComponentName> m25650() {
//        HashMap<String, ComponentName> hashMap = new HashMap<>();
//        List<AccessibilityWindowInfo> windows = AccessibilityHelperService.f19276.getWindows();
//        if (windows != null && windows.size() > 0) {
//            for (AccessibilityWindowInfo accessibilityWindowInfo : windows) {
//                try {
//                    AccessibilityNodeInfo root = accessibilityWindowInfo.getRoot();
//                    if (root != null && ObjectUtils.isNotEmpty(root.getPackageName())) {
//                        CharSequence packageName = root.getPackageName();
//                        if (accessibilityWindowInfo.getType() == 1 || this.f23174.m25927(packageName)) {
//                            ComponentName componentName = new ComponentName(packageName.toString(), root.getClassName().toString());
//                            hashMap.put(componentName.getPackageName(), componentName);
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return hashMap;
//    }
//
//    /* renamed from: 枩棥钰蕎睨領喀镎遣跄, reason: contains not printable characters */
//    public void m25651(String str) {
//        LogHelper.m16301("onBackOpen:" + str);
//        m15465();
//    }
//
//    /* renamed from: 櫓昛刓叡賜, reason: contains not printable characters */
//    public final boolean m25652(ComponentName componentName) {
//        if ("com.vivo.upslide.recents.RecentsActivity".equals(componentName.getClassName())) {
//            if (AppHelper.m14902(this.f23168) || VivoUtils.m14900()) {
//                return true;
//            }
//            Intent intent = new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS_VIVO");
//            intent.setPackage(this.f23168.getPackageName());
//            intent.putExtra("reason", "recentapps");
//            this.f23168.sendBroadcast(intent);
//            return true;
//        }
//        if (!"com.bbk.launche.Launcher".equals(componentName.getClassName())) {
//            return false;
//        }
//        if (VivoUtils.m14900()) {
//            return true;
//        }
//        VivoUtils.m24149(true);
//        Intent intent2 = new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS_VIVO");
//        intent2.setPackage(this.f23168.getPackageName());
//        intent2.putExtra("reason", "homekey");
//        this.f23168.sendBroadcast(intent2);
//        return true;
//    }
//
//    /* renamed from: 癎躑選熁, reason: contains not printable characters */
//    public void m25653(String str) {
//        SafeUtils.m15457(this.f23168);
//        if (ObjectUtils.isNotEmpty((CharSequence) str) && this.f23174.m25886(str)) {
//            SafeUtils.m25630();
//        }
//    }
//
//    /* renamed from: 睳堋弗粥辊惶, reason: contains not printable characters */
//    public final boolean m25654() {
//        String appName = AppUtils.getAppName();
//        AccessibilityHelperService accessibilityHelperService = AccessibilityHelperService.f19276;
//        if (accessibilityHelperService != null && !accessibilityHelperService.m19977("Launcher", "桌面").isEmpty()) {
//            AccessibilityHelperService.m19968("checkInstall 安装");
//            AccessibilityHelperService.m19967();
//            return true;
//        }
//        if (accessibilityHelperService == null || accessibilityHelperService.m19977(appName).isEmpty() || accessibilityHelperService.m19977("Uninstall", "卸载").isEmpty()) {
//            return false;
//        }
//        AccessibilityHelperService.m19968("checkInstall 卸载");
//        AccessibilityHelperService.m19967();
//        return true;
//    }
//
//    /* renamed from: 瞙餃莴埲, reason: contains not printable characters */
//    public final boolean m25655(ComponentName componentName) {
//        if (!"com.android.settings".equals(componentName.getPackageName())) {
//            return false;
//        }
//        LogHelper.m16301("跳转到设置——checkSetting");
//        m25660();
//        return true;
//    }
//
//    /* renamed from: 礱咄頑, reason: contains not printable characters */
//    public void m25656() {
//        GuardFloatUtils.m15496();
//    }
//
//    /* renamed from: 祴嚚橺谋肬鬧舘, reason: contains not printable characters */
//    public final boolean m25657(ComponentName componentName) {
//        if (!m25644(SystemSettingActivity.f19351, componentName.getClassName()) && !this.f23174.m25903(componentName.getClassName()) && !m25665(componentName)) {
//            if (AccessibilityHelperService.f19276 != null && !this.f23174.m25905(componentName.getClassName())) {
//                return false;
//            }
//            LogHelper.m16301("跳转到设置--checkClassNameWhite");
//            m25660();
//        }
//        return true;
//    }
//
//    /* renamed from: 綏牽躵糽稰烳俠垳襨捈桏鷋, reason: contains not printable characters */
//    public final void m25658(String str) {
//        ((ActivityManager) App.mContext.getSystemService(ActivityChooserModel.ATTRIBUTE_ACTIVITY)).killBackgroundProcesses(str);
//    }
//
//    /* renamed from: 綩私 */
//    public boolean m15461(ComponentName componentName) {
//        this.f23177 = componentName.getPackageName();
//        if (this.f23174.m25906(componentName.getPackageName()) && m25657(componentName)) {
//            return false;
//        }
//        if (m25655(componentName)) {
//            return true;
//        }
//        if (!ConfigKey.m27250() && ConfigKey.m27245() && !this.f23172.get()) {
//            if (UserUtils.m14907()) {
//                if (!this.f23174.m25932(this.f23177)) {
//                    if (!LockHelper.m21561().m21568() && !ObjectUtils.equals(this.f23177, this.f23171)) {
//                        App.showToast("设备已锁定,该App禁止运行");
//                        m25653(this.f23177);
//                    }
//                    LockEvent.m21520(4);
//                }
//                return true;
//            }
//            if (!this.f23174.m25895(this.f23177)) {
//                if (!LockHelper.m21561().m21568() && !ObjectUtils.equals(this.f23177, this.f23171)) {
//                    App.showToast("设备已锁定,该App禁止运行");
//                    m25653(this.f23177);
//                }
//                LockEvent.m21520(4);
//            }
//            return false;
//        }
//        if (this.f23174.m25911(componentName.getClassName())) {
//            m25660();
//            LogHelper.m16301("跳转到设置——isBlackPage");
//            return true;
//        }
//        if (this.f23174.m25926(componentName.getPackageName()) && !this.f23172.get()) {
//            if (!this.f23174.m25932(componentName.getPackageName()) && this.f23174.m25918(componentName.getPackageName())) {
//                m25653(this.f23177);
//                SafeScreenLockView m23580 = SafeScreenLockView.m23580();
//                m23580.mo23566();
//                String appName = AppUtils.getAppName(componentName.getPackageName());
//                if (ObjectUtils.isEmpty((CharSequence) appName)) {
//                    appName = componentName.getPackageName();
//                }
//                m23580.m14672(appName + "已被禁止", "应用不被允许运行");
//                App.showToast("应用不被允许运行");
//                m25658(componentName.getPackageName());
//                return true;
//            }
//            SafeScreenLockView.m23580().m25779();
//            return false;
//        }
//        if (m25664(componentName)) {
//            return true;
//        }
//        if (m25645(componentName)) {
//            m25656();
//            return false;
//        }
//        if (this.f23174.m25929(this.f23177)) {
//            m25656();
//            return false;
//        }
//        if (componentName.getClassName().equals(SystemSettingActivity.f19351)) {
//            m25656();
//            return false;
//        }
//        if ((componentName.getPackageName().equals(SystemSettingActivity.f11989) || componentName.getPackageName().equals("com.coloros.simsettings")) && RomUtils.isOppo() && this.f23174.m25903(componentName.getClassName())) {
//            m25656();
//            return false;
//        }
//        if (m25644(this.f23177, "com.android.systemui")) {
//            m25656();
//            return false;
//        }
//        if (RomUtils.isVivo() && m25652(componentName)) {
//            m25656();
//            return false;
//        }
//        if (m25640(componentName)) {
//            m25656();
//            return false;
//        }
//        if (m25659(componentName)) {
//            return true;
//        }
//        if (!m15464(componentName)) {
//            return false;
//        }
//        m25656();
//        return true;
//    }
//
//    /* renamed from: 纩慐 */
//    public String m15462() {
//        return this.f23177;
//    }
//
//    /* renamed from: 耣怳匮色紝参凵蛴纆勚躄, reason: contains not printable characters */
//    public boolean m25659(ComponentName componentName) {
//        if (this.f23172.get()) {
//            return false;
//        }
//        String packageName = componentName.getPackageName();
//        int m27249 = ConfigKey.m27249();
//        if (m27249 != 0) {
//            if (m27249 == 1 && !this.f23174.m25873(packageName)) {
//                m25653(packageName);
//                SafeScreenLockView m23580 = SafeScreenLockView.m23580();
//                m23580.mo23566();
//                m23580.m23588("好好学习,天天向上", "学习模式APP禁止", "pkg:" + packageName);
//                return true;
//            }
//            if (m27249 == 2 && !this.f23174.m25895(packageName)) {
//                m25653(packageName);
//                SafeScreenLockView m235802 = SafeScreenLockView.m23580();
//                m235802.mo23566();
//                m235802.m23588("好好学习,天天向上", "学习模式-其余时间禁用模式", "pkg:" + packageName);
//                return true;
//            }
//        }
//        return m25663(componentName);
//    }
//
//    /* renamed from: 蘫聫穯搞哪曁雥贀忬琖嶹, reason: contains not printable characters */
//    public final void m25660() {
//        if (DataKV.m25858("setting_intercept", false)) {
//            this.f23167.m23828(new C6837());
//        } else {
//            this.f23167.m23828(new C6839());
//        }
//    }
//
//    /* renamed from: 蝸餺閃喍, reason: contains not printable characters */
//    public void m25661() {
//        m25646();
//        if (this.f14124 != null) {
//            long currentTimeMillis = System.currentTimeMillis();
//            DataKV.m25856("current_time", currentTimeMillis);
//            this.f23169 = currentTimeMillis;
//            this.f23173 = false;
//            this.f14124.postDelayed(this.f23175, this.f14123);
//        }
//    }
//
//    /* renamed from: 躑漕 */
//    public void m15463(boolean z) {
//        this.f23172.set(z);
//    }
//
//    /* renamed from: 辒迳圄袡皪郞箟, reason: contains not printable characters */
//    public final void m25662(Runnable runnable, long j) {
//        if (this.f14124 != null) {
//            long currentTimeMillis = System.currentTimeMillis();
//            DataKV.m25856("current_time", currentTimeMillis);
//            this.f23173 = false;
//            this.f23169 = currentTimeMillis;
//            this.f14124.postDelayed(runnable, j);
//        }
//    }
//
//    /* renamed from: 酸恚辰橔纋黺, reason: contains not printable characters */
//    public final boolean m25663(ComponentName componentName) {
//        char c2;
//        if (!this.f23176.get()) {
//            return false;
//        }
//        String packageName = componentName.getPackageName();
//        if (this.f23174.m25890(packageName)) {
//            c2 = 1;
//        } else if (this.f23174.m25868(packageName)) {
//            c2 = 2;
//        } else {
//            c2 = this.f23174.m25882(packageName) ? (char) 3 : (char) 0;
//        }
//        if (c2 <= 0) {
//            return false;
//        }
//        m25653(packageName);
//        SafeScreenLockView m23580 = SafeScreenLockView.m23580();
//        m23580.mo23566();
//        if (c2 == 1) {
//            m23580.m23588("此APP今日使用时长已到", "App使用时长限制", "pkg:" + packageName);
//        } else if (c2 == 2) {
//            m23580.m23588("该时段禁止运行此APP", "App多时段限制", "pkg:" + packageName);
//        } else {
//            m23580.m23588("组合限制时间已到", "App禁止运行", "pkg:" + packageName);
//        }
//        return true;
//    }
//
//    /* renamed from: 鑭撇糁綖浓緗轟鱼萟磿焈, reason: contains not printable characters */
//    public boolean m25664(ComponentName componentName) {
//        if (!"com.android.packageinstaller.UninstallerActivity".equalsIgnoreCase(componentName.getClassName())) {
//            return false;
//        }
//        if (AccessibilityHelperService.f19276 != null) {
//            GuardFloatUtils.m15496();
//            return m25654();
//        }
//        m25653(componentName.getPackageName());
//        GuardFloatUtils.m25766(5);
//        return true;
//    }
//
//    /* renamed from: 镐藻 */
//    public final boolean m15464(ComponentName componentName) {
//        char c2;
//        if (ConfigKey.m27243()) {
//            m25656();
//            return false;
//        }
//        String packageName = componentName.getPackageName();
//        if (this.f23174.m25918(packageName)) {
//            c2 = 1;
//        } else if (this.f23174.m25887(packageName)) {
//            c2 = 2;
//        } else {
//            c2 = this.f23174.m25874(packageName) ? (char) 3 : (char) 0;
//        }
//        if (c2 > 0 && c2 != 1 && this.f23172.get()) {
//            return true;
//        }
//        if (c2 > 1 && !this.f23176.get()) {
//            return true;
//        }
//        if (c2 > 0) {
//            if (AccessibilityHelperService.f19276 != null && this.f23174.m25905(componentName.getClassName()) && AccessibilityHelperService.f19276.m19977(AppUtils.getAppName()).isEmpty()) {
//                return false;
//            }
//            m25653(packageName);
//            SafeScreenLockView m23580 = SafeScreenLockView.m23580();
//            m23580.mo23566();
//            String appName = AppUtils.getAppName(componentName.getPackageName());
//            if (ObjectUtils.isEmpty((CharSequence) appName)) {
//                appName = componentName.getPackageName();
//            }
//            if (c2 == 1) {
//                m23580.m14672(appName + "已被禁止", "应用不被允许运行");
//            } else if (c2 == 2) {
//                m23580.m14672(appName + "已被设置为禁止", "应用禁止运行");
//            } else {
//                m23580.m14672(appName + "需要家长端批准才能运行", "应用禁止运行");
//            }
//            App.showToast("应用不被允许运行");
//            m25658(componentName.getPackageName());
//            return true;
//        }
//        m25656();
//        return false;
//    }
//
//    /* renamed from: 陟瓠魒踱褢植螉嚜, reason: contains not printable characters */
//    public final boolean m25665(ComponentName componentName) {
//        String className = componentName.getClassName();
//        return "com.android.settings.SubSettings".equals(className) || "com.vivo.settings.VivoSubSettings".equals(className) || "android.app.Dialog".equals(className) || "android.widget.FrameLayout".equals(className);
//    }
//
//    /* renamed from: 鞲冇 */
//    public void m15465() {
//        if (ProcessUtil.m24146()) {
//            if (UserUtils.m24165()) {
//                GuardFloatUtils.m15496();
//                m25661();
//                return;
//            }
//            if (AppHelper.m24161(this.f23168)) {
//                this.f23177 = ModelManager.m22772();
//                m25661();
//                GuardFloatUtils.m15496();
//            } else {
//                if (PermissionCheckActivity.m20025()) {
//                    this.f23177 = AppUtils.getAppPackageName();
//                    GuardFloatUtils.m15496();
//                    return;
//                }
//                GuardFloatUtils.m15496();
//                LogUtil.m21245("checkBackApp");
//                Handler handler = this.f14124;
//                if (handler != null) {
//                    handler.removeCallbacksAndMessages(null);
//                    this.f14124.post(new RunnableC6835());
//                }
//            }
//        }
//    }
//
//    /* renamed from: 韐爮幀悖罤噩钼遑杯盇, reason: contains not printable characters */
//    public List<String> m25666() {
//        return this.f14125;
//    }
//
//    /* renamed from: 駭鑈趘薑衈講堍趃軏, reason: contains not printable characters */
//    public List<AppHelper.C6418> m25667() {
//        ArrayList arrayList = new ArrayList();
//        List<AppHelper.C6418> m24158 = AppHelper.m24158(this.f23168);
//        if (AccessibilityHelperService.f19276 != null) {
//            ResultWrapper resultWrapper = new ResultWrapper(Boolean.FALSE);
//            if (UsageHelper.m24129(m24158)) {
//                m24158.clear();
//                resultWrapper.m24144(Boolean.TRUE);
//            } else {
//                arrayList.addAll(m24158);
//            }
//            HashMap<String, ComponentName> m25650 = m25650();
//            if (ObjectUtils.isNotEmpty((Map) m25650)) {
//                if (((Boolean) resultWrapper.m14898()).booleanValue()) {
//                    Iterator<Map.Entry<String, ComponentName>> it = m25650.entrySet().iterator();
//                    if (it.hasNext()) {
//                        arrayList.add(new AppHelper.C6418(0L, it.next().getValue()));
//                    }
//                } else {
//                    for (Map.Entry<String, ComponentName> entry : m25650.entrySet()) {
//                        Iterator<AppHelper.C6418> it2 = m24158.iterator();
//                        while (true) {
//                            if (!it2.hasNext()) {
//                                break;
//                            }
//                            if (!ObjectUtils.equals(it2.next().f13463.getPackageName(), entry.getValue().getPackageName())) {
//                                arrayList.add(new AppHelper.C6418(0L, entry.getValue()));
//                                break;
//                            }
//                        }
//                    }
//                }
//            }
//        } else {
//            arrayList.addAll(m24158);
//        }
//        return arrayList.isEmpty() ? AppHelper.m14901() : arrayList;
//    }
}
