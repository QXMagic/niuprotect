package accessibility.lib;

public class ChildJumpUtils {

//    /* compiled from: ChildJumpUtils.java */
//    /* renamed from: 璙冝耝.灞酞輀攼嵞漁綬迹$刻槒唱镧詴, reason: contains not printable characters */
//    /* loaded from: E:\apk\monitor\监控\classes5.dex */
//    public class RunnableC6417 implements Runnable {
//        @Override // java.lang.Runnable
//        public void run() {
//            if (DeskLauncherActivity.f19286 == null) {
//                SafeGuardManager.m25638(App.mContext).m25651("Jump start");
//            }
//        }
//    }
//
//    /* compiled from: ChildJumpUtils.java */
//    /* renamed from: 璙冝耝.灞酞輀攼嵞漁綬迹$肌緭 */
//    /* loaded from: E:\apk\monitor\监控\classes5.dex */
//    public class RunnableC3862 implements Runnable {
//
//        /* renamed from: 肌緭 */
//        public final /* synthetic */ DeskLauncherActivity f13458;
//
//        public RunnableC3862(DeskLauncherActivity deskLauncherActivity) {
//            this.f13458 = deskLauncherActivity;
//        }
//
//        @Override // java.lang.Runnable
//        public void run() {
//            this.f13458.onResume();
//        }
//    }
//
//    /* renamed from: 刻槒唱镧詴, reason: contains not printable characters */
//    public static void m24137(Context context, String str) {
//        if (CommonUtils.m21239()) {
//            m24140();
//            return;
//        }
//        C7045 m22561 = C3629.m22561("/permission/SuperActiveChooseActivity");
//        if (ObjectUtils.isNotEmpty((CharSequence) str)) {
//            m22561.m26754(TypedValues.TransitionType.S_FROM, str);
//        }
//        m22561.m15843(context);
//    }
//
//    /* renamed from: 垡玖 */
//    public static void m14895() {
//        C3629.m22561("/child/PermissionCompleteActivity").m26763();
//    }
//
//    /* renamed from: 旞莍癡, reason: contains not printable characters */
//    public static void m24138(Context context, String str) {
//        C7045 m22561 = C3629.m22561("/permission/PhoneChildActiveActivity");
//        if (ObjectUtils.isNotEmpty((CharSequence) str)) {
//            m22561.m26754(TypedValues.TransitionType.S_FROM, str);
//        }
//        m22561.m26756(C2860y.f10012a);
//        m22561.m15843(context);
//    }
//
//    /* renamed from: 灞酞輀攼嵞漁綬迹, reason: contains not printable characters */
//    public static void m24139() {
//        if (!Mgr.m24658().mo22192() && !CommonUtils.m21239()) {
//            m14897();
//        } else {
//            m14895();
//        }
//    }
//
//    /* renamed from: 睳堋弗粥辊惶, reason: contains not printable characters */
//    public static void m24140() {
//        C3629.m22561("/guard/zltpermission/active").m26763();
//    }
//
//    /* renamed from: 祴嚚橺谋肬鬧舘, reason: contains not printable characters */
//    public static void m24141(Context context, String str) {
//        C7045 m22561 = C3629.m22561("/permission/SuperActiveActivity");
//        if (ObjectUtils.isNotEmpty((CharSequence) str)) {
//            m22561.m26754(TypedValues.TransitionType.S_FROM, str);
//        }
//        m22561.m26756(C2860y.f10012a);
//        m22561.m15843(context);
//    }
//
//    /* renamed from: 肌緭 */
//    public static boolean m14896(Context context, String str) {
//        LogUtil.m13850("Star from=>" + str);
//        DeskLauncherActivity deskLauncherActivity = DeskLauncherActivity.f19286;
//        if (deskLauncherActivity != null) {
//            Handler handler = DeskLauncherActivity.f11929;
//            handler.removeCallbacksAndMessages(null);
//            handler.post(new RunnableC3862(deskLauncherActivity));
//            return true;
//        }
//        LogUtil.m13850("DeskLauncherActivity star");
//        Handler handler2 = DeskLauncherActivity.f11929;
//        handler2.removeCallbacksAndMessages(null);
//        handler2.postDelayed(new RunnableC6417(), 3000L);
//        if (Build.VERSION.SDK_INT < 30) {
//            DeskLauncherActivity.m19996(context);
//        }
//        return DeskLauncherActivity.m19998(context, null);
//    }
//
//    /* renamed from: 葋申湋骶映鍮秄憁鎓羭, reason: contains not printable characters */
//    public static void m24142(Context context, String str) {
//        C7045 m22561 = C3629.m22561("/permission/ComputeAdbActivity");
//        if (ObjectUtils.isNotEmpty((CharSequence) str)) {
//            m22561.m26754(TypedValues.TransitionType.S_FROM, str);
//        }
//        m22561.m26756(C2860y.f10012a);
//        m22561.m15843(context);
//    }
//
//    /* renamed from: 镐藻 */
//    public static void m14897() {
//        C3629.m22561("/permission/SuperChooseActivity").m26763();
//    }
//
//    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐, reason: contains not printable characters */
//    public static void m24143(Context context) {
//        if (CommonUtils.m21239()) {
//            m24140();
//            return;
//        }
//        C7045 m22561 = C3629.m22561("/permission/PermissionChooseActivity");
//        if (context instanceof Activity) {
//            m22561.m15843(context);
//        } else {
//            m22561.m26756(C2860y.f10012a).m26763();
//        }
}
