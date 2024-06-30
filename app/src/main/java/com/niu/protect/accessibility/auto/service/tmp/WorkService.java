package com.niu.protect.accessibility.auto.service.tmp;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.niu.protect.accessibility.auto.service.C6492;

import java.util.Calendar;

public class WorkService extends Service{

    /* renamed from: 礱咄頑 */
    public static final Calendar f19247 = null;

    /* renamed from: 綏牽躵糽稰烳俠垳襨捈桏鷋 */
    public static long f19248;

    /* renamed from: 鞲冇 */
    public static WorkService f11899;

    /* renamed from: 偣炱嘵蟴峗舟轛 */
    public Notification.Builder f19249;

    /* renamed from: 刻槒唱镧詴 */
    public String f19250;

    /* renamed from: 卝閄侸靤溆鲁扅 */
    public C6492 f19251;

    /* renamed from: 唌橅咟 */
    public long f19252;
//
//    /* renamed from: 垡玖 */
//    public HttpClient f11900;
//
//    /* renamed from: 壋劘跆貭澴綄秽攝煾訲 */
//    public long f19253;
//
//    /* renamed from: 斃燸卺驼暲各撟嫺眧樬硱 */
//    public AppUsageTimeHelper f19255;
//
//    /* renamed from: 朽劔蚁灋嵿齩鶴琓麃沼瀙缹 */
//    public LockHelper f19257;
//
//    /* renamed from: 杹藗瀶姙笻件稚嵅蔂 */
//    public BootBroadCastReceiver f19258;
//
//    /* renamed from: 灞酞輀攼嵞漁綬迹 */
//    public AppUploadManager f19260;
//
//    /* renamed from: 睳堋弗粥辊惶 */
//    public Context f19262;
//
//    /* renamed from: 瞙餃莴埲 */
//    public long f19263;
//
//    /* renamed from: 綩私 */
//    public AppInterceptManager f11901;
//
//    /* renamed from: 纩慐 */
//    public HandlerLooper f11902;
//
//    /* renamed from: 耣怳匮色紝参凵蛴纆勚躄 */
//    public LimitHelper f19265;
//
//    /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
//    public AppFilter f19266;
//
//    /* renamed from: 蝸餺閃喍 */
//    public AppAddOrUninstallReceiver f19267;
//
//    /* renamed from: 辒迳圄袡皪郞箟 */
//    public LauncherSubSystem f19268;
//
//    /* renamed from: 酸恚辰橔纋黺 */
//    public NotificationManager f19269;
//
//    /* renamed from: 销薞醣戔攖餗 */
//    public int f19271;
//
//    /* renamed from: 镐藻 */
//    public LocationTimer f11904;
//
//    /* renamed from: 陟瓠魒踱褢植螉嚜 */
//    public LearningHelper f19272;
//
//    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
//    public HttpUploadManager f19273;
//
//    /* renamed from: 駭鑈趘薑衈講堍趃軏 */
//    public long f19275;
//
//    /* renamed from: 肌緭 */
//    public int f11903 = 0;
//
//    /* renamed from: 旞莍癡 */
//    public final Calendar f19256 = Calendar.getInstance(Locale.CHINA);
//
//    /* renamed from: 祴嚚橺谋肬鬧舘 */
//    public boolean f19264 = false;
//
//    /* renamed from: 櫓昛刓叡賜 */
//    public final ExecutorService f19259 = Executors.newCachedThreadPool();
//
//    /* renamed from: 鑭撇糁綖浓緗轟鱼萟磿焈 */
//    public final SignalClient f19270 = SignalClient.m21756();
//
//    /* renamed from: 彻薯铏螙憣欖愡鼭 */
//    public final WeakHandler f19254 = new WeakHandler();
//
//    /* renamed from: 韐爮幀悖罤噩钼遑杯盇 */
//    public boolean f19274 = false;
//
//    /* renamed from: 癎躑選熁 */
//    public final Handler f19261 = new Handler();
//
//    /* renamed from: com.zlfc.child.mvp.service.WorkService$偣炱嘵蟴峗舟轛 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class C5765 implements InterfaceC4081<String> {
//        public C5765(WorkService workService) {
//        }
//
//        @Override // p433.InterfaceC4081
//        /* renamed from: 刻槒唱镧詴 */
//        public void mo16741(int i, String str) {
//            LogUtils.m5798e("上传失败：" + str);
//        }
//
//        @Override // p433.InterfaceC4081
//        /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
//        public void mo5631(String str) {
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvp.service.WorkService$刻槒唱镧詴 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class C5766 implements InterfaceC6005<LearningBean> {
//        public C5766() {
//        }
//
//        @Override // p220.InterfaceC6005
//        /* renamed from: 肌緭 */
//        public void mo5628(String str) {
//            LogHelper.m27986().m27999("学习模式接口访问失败:" + str);
//        }
//
//        @Override // p220.InterfaceC6005
//        /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
//        public /* synthetic */ void mo16739(LearningBean learningBean) {
//            C6003.m21618(this, learningBean);
//        }
//
//        @Override // p220.InterfaceC3530
//        /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
//        public void mo16738(LearningBean learningBean) {
//            if (ObjectUtils.isNotEmpty(learningBean)) {
//                try {
//                    if (WorkService.this.f19272.m15562(learningBean)) {
//                        WorkService.this.f19272.m25975(GsonUtils.toJson(learningBean));
//                        WorkService.this.m19947("学习模式已开启");
//                        LogHelper.m27986().m27999("学习模式开关开启");
//                        WorkService.this.f19272.m25978();
//                    } else {
//                        WorkService.this.f19272.m25975("");
//                        ConfigKey.m16032();
//                        LogHelper.m27986().m27999("学习模式开关关闭");
//                        WorkService.this.f19272.m15563();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvp.service.WorkService$垡玖 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class C3240 extends TypeToken<Map<String, Integer>> {
//        public C3240(WorkService workService) {
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvp.service.WorkService$彻薯铏螙憣欖愡鼭 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class CallableC5767 implements Callable<Boolean> {
//        public CallableC5767() {
//        }
//
//        @Override // java.util.concurrent.Callable
//        /* renamed from: 肌緭 */
//        public Boolean call() {
//            boolean z;
//            boolean z2;
//            WorkService workService = WorkService.this;
//            int i = Build.VERSION.SDK_INT;
//            boolean m22488 = Tools.m22488(workService);
//            boolean m14279 = Tools.m14279(workService);
//            boolean m22506 = Tools.m22506();
//            boolean z3 = i < 23 || Tools.m22472(workService);
//            boolean m20883 = LocationHelper.m20883(workService);
//            if (CommonUtils.m21239()) {
//                m20883 = true;
//            }
//            if (!RomUtils.isVivo() || i < 24) {
//                z = true;
//                z2 = true;
//            } else {
//                z2 = Tools.m22462(workService);
//                z = Tools.m22503(workService);
//            }
//            if (m22488 && m14279 && m22506 && z3 && z2 && z && m20883) {
//                WorkService.this.f19273.m25996(1, "");
//                if (WorkService.this.f19252 == 0) {
//                    WorkService.this.f19252 = System.currentTimeMillis();
//                    WorkService.this.f11904.m20881();
//                } else if (System.currentTimeMillis() - WorkService.this.f19252 >= 120000) {
//                    WorkService.this.f11904.m20881();
//                    WorkService.this.f19252 = System.currentTimeMillis();
//                }
//            } else {
//                StringBuilder sb = new StringBuilder();
//                if (!m22488) {
//                    sb.append("位置权限");
//                }
//                if (!m14279) {
//                    if (ObjectUtils.isNotEmpty((CharSequence) sb.toString())) {
//                        sb.append(",使用情况访问权限");
//                    } else {
//                        sb.append("使用情况访问权限");
//                    }
//                }
//                if (!m22506) {
//                    if (ObjectUtils.isNotEmpty((CharSequence) sb.toString())) {
//                        sb.append(",无障碍权限");
//                    } else {
//                        sb.append("无障碍权限");
//                    }
//                }
//                if (!z3) {
//                    if (ObjectUtils.isNotEmpty((CharSequence) sb.toString())) {
//                        sb.append(",悬浮窗权限");
//                    } else {
//                        sb.append("悬浮窗权限");
//                    }
//                }
//                if (!z2) {
//                    if (ObjectUtils.isNotEmpty((CharSequence) sb.toString())) {
//                        sb.append(",自启动权限");
//                    } else {
//                        sb.append("自启动权限");
//                    }
//                }
//                if (!z) {
//                    if (ObjectUtils.isNotEmpty((CharSequence) sb.toString())) {
//                        sb.append(",后台弹出界面权限");
//                    } else {
//                        sb.append("后台弹出界面权限");
//                    }
//                }
//                if (!m20883) {
//                    if (ObjectUtils.isNotEmpty((CharSequence) sb.toString())) {
//                        sb.append(",定位服务权限");
//                    } else {
//                        sb.append("定位服务权限");
//                    }
//                }
//                WorkService.this.f19273.m25996(2, sb.toString());
//                if (m22488) {
//                    if (WorkService.this.f19252 == 0) {
//                        WorkService.this.f19252 = System.currentTimeMillis();
//                        WorkService.this.f11904.m20881();
//                    } else if (System.currentTimeMillis() - WorkService.this.f19252 >= 120000) {
//                        WorkService.this.f11904.m20881();
//                        WorkService.this.f19252 = System.currentTimeMillis();
//                    }
//                }
//            }
//            return Boolean.TRUE;
//        }
//
//        public /* synthetic */ CallableC5767(WorkService workService, RunnableC5778 runnableC5778) {
//            this();
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvp.service.WorkService$旞莍癡 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class RunnableC5768 implements Runnable {
//        public RunnableC5768(WorkService workService) {
//        }
//
//        @Override // java.lang.Runnable
//        public void run() {
//            if (!ChildStackManager.m24105().m14884(PermissionCheckActivity.class)) {
//                ToastUtils.showLong("后台弹出界面权限未开启,界面跳转失败,请手动前往设置界面开启权限");
//            } else {
//                App.showToast("请按照提示开启对应的权限");
//            }
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvp.service.WorkService$朽劔蚁灋嵿齩鶴琓麃沼瀙缹 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class C5769 implements LearningHelper.InterfaceC6901 {
//        public C5769() {
//        }
//
//        @Override // p472.LearningHelper.InterfaceC6901
//        /* renamed from: 刻槒唱镧詴 */
//        public void mo19953() {
//            WorkService.this.m19947(AppUtils.getAppName() + "运行中..");
//        }
//
//        @Override // p472.LearningHelper.InterfaceC6901
//        /* renamed from: 肌緭 */
//        public void mo13518(LearningBean.Info info) {
//            WorkService.this.m19947("学习模式已开启");
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvp.service.WorkService$櫓昛刓叡賜 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class C5770 implements ProgressBus.InterfaceC5948<Boolean> {
//        public C5770(WorkService workService) {
//        }
//
//        @Override // p197.ProgressBus.InterfaceC5948
//        @NonNull
//        /* renamed from: 肌緭 */
//        public Boolean getValue(String str) {
//            List<VoiceDbBean> m24175 = ChildDBManager.m24175(5);
//            if (ObjectUtils.isNotEmpty((Collection) m24175)) {
//                boolean z = false;
//                Iterator<VoiceDbBean> it = m24175.iterator();
//                while (it.hasNext()) {
//                    if (ObjectUtils.equals(it.next().getPackageName(), str)) {
//                        z = true;
//                    }
//                }
//                if (!z) {
//                    return Boolean.valueOf(ChildDBManager.m24179(str));
//                }
//                return Boolean.FALSE;
//            }
//            return Boolean.valueOf(ChildDBManager.m24179(str));
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvp.service.WorkService$灞酞輀攼嵞漁綬迹 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class RunnableC5771 implements Runnable {
//        public RunnableC5771(WorkService workService) {
//        }
//
//        @Override // java.lang.Runnable
//        public void run() {
//            if (ChildStackManager.m24105().m14884(ChildUpdateActivity.class)) {
//                return;
//            }
//            ToastUtils.showLong("后台弹出界面权限未开启,界面跳转失败,请手动前往设置界面开启权限");
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvp.service.WorkService$睳堋弗粥辊惶 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class RunnableC5772 implements Runnable {
//        public RunnableC5772() {
//        }
//
//        @Override // java.lang.Runnable
//        public void run() {
//            WorkService.this.m13516();
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvp.service.WorkService$瞙餃莴埲 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class C5773 implements C6492.InterfaceC6494 {
//        public C5773() {
//        }
//
//        @Override // p384.C6492.InterfaceC6494
//        /* renamed from: 偣炱嘵蟴峗舟轛 */
//        public void mo19899() {
//            WorkService.this.m19943();
//            if (System.currentTimeMillis() - WorkService.this.f19263 > (C6891.m25854("location_cycle", 10) * 60 * 1000) + DateUtils.TEN_SECOND) {
//                WorkService.this.f11904.m20881();
//            }
//        }
//
//        @Override // p384.C6492.InterfaceC6494
//        /* renamed from: 睳堋弗粥辊惶 */
//        public void mo19903() {
//        }
//
//        @Override // p384.C6492.InterfaceC6494
//        /* renamed from: 纩慐 */
//        public void mo13500() {
//            LogHelper.m16301("屏幕解锁");
//            SafeUtils.m25629();
//            if (System.currentTimeMillis() - WorkService.this.f19263 > (C6891.m25854("location_cycle", 10) * 60 * 1000) + DateUtils.TEN_SECOND) {
//                WorkService.this.f11904.m20881();
//            }
//            GuardFloatUtils.m15496();
//            C6891.m25856("current_time", System.currentTimeMillis());
//            LogHelper.m27986().m16303("手机状态", "屏幕解锁,拦截线程继续运行");
//            SafeGuardManager.m25638(App.mContext).m25651(C5773.class.getName() + "=>屏幕亮起或解锁");
//            WorkService.this.f19255.m26069();
//        }
//
//        @Override // p384.C6492.InterfaceC6494
//        /* renamed from: 镐藻 */
//        public void mo13501() {
//            LogHelper.m16301("屏幕亮起");
//            WorkService.this.m19943();
//            GuardFloatUtils.m15496();
//            C6891.m25856("current_time", System.currentTimeMillis());
//            SafeGuardManager.m25638(App.mContext).m25651(C5773.class.getName() + "=>屏幕亮起或解锁");
//        }
//
//        @Override // p384.C6492.InterfaceC6494
//        /* renamed from: 駭鑈趘薑衈講堍趃軏 */
//        public /* synthetic */ void mo19904() {
//            C6495.m24471(this);
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvp.service.WorkService$祴嚚橺谋肬鬧舘 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class C5774 implements InterfaceC6005<List<LauncherBean>> {
//        public C5774() {
//        }
//
//        @Override // p220.InterfaceC6005
//        /* renamed from: 肌緭 */
//        public /* synthetic */ void mo5628(String str) {
//            C6003.m13968(this, str);
//        }
//
//        @Override // p220.InterfaceC6005
//        /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
//        public /* synthetic */ void mo16739(List<LauncherBean> list) {
//            C6003.m21618(this, list);
//        }
//
//        @Override // p220.InterfaceC3530
//        /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
//        public void mo16738(List<LauncherBean> list) {
//            LogHelper.m27986().m16303("APP管理", "134推送重新上传=>获取到了手机App数量:" + list.size() + "个");
//            WorkService.this.f19260.m26040(list);
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvp.service.WorkService$綩私 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class C3241 implements ProgressBus.InterfaceC5948<Boolean> {
//        public C3241(WorkService workService) {
//        }
//
//        @Override // p197.ProgressBus.InterfaceC5948
//        @NonNull
//        /* renamed from: 肌緭 */
//        public Boolean getValue(String str) {
//            boolean m27245 = ConfigKey.m27245();
//            LockSetting m24173 = ChildDBManager.m24173();
//            return Boolean.valueOf(m27245 && m24173 != null && m24173.getNotification() == 1);
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvp.service.WorkService$耣怳匮色紝参凵蛴纆勚躄 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class C5775 implements InterfaceC6005<List<LauncherBean>> {
//        public C5775() {
//        }
//
//        @Override // p220.InterfaceC6005
//        /* renamed from: 肌緭 */
//        public /* synthetic */ void mo5628(String str) {
//            C6003.m13968(this, str);
//        }
//
//        @Override // p220.InterfaceC6005
//        /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
//        public /* synthetic */ void mo16739(List<LauncherBean> list) {
//            C6003.m21618(this, list);
//        }
//
//        @Override // p220.InterfaceC3530
//        /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
//        public void mo16738(List<LauncherBean> list) {
//            LogHelper.m27986().m16303("APP管理", "initLauncherConfig success=>获取手机App成功:" + list.size() + "个");
//            WorkService.this.f19260.m26034(list);
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvp.service.WorkService$肌緭 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class RunnableC3242 implements Runnable {
//        public RunnableC3242() {
//        }
//
//        @Override // java.lang.Runnable
//        public void run() {
//            WorkService.this.f19255.m26073();
//            WorkService.this.f19255.m26071();
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvp.service.WorkService$葋申湋骶映鍮秄憁鎓羭 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class C5776 implements InterfaceC6005<List<AppState>> {
//        public C5776() {
//        }
//
//        @Override // p220.InterfaceC6005
//        /* renamed from: 肌緭 */
//        public /* synthetic */ void mo5628(String str) {
//            C6003.m13968(this, str);
//        }
//
//        @Override // p220.InterfaceC6005
//        /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
//        public /* synthetic */ void mo16739(List<AppState> list) {
//            C6003.m21618(this, list);
//        }
//
//        @Override // p220.InterfaceC3530
//        /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
//        public void mo16738(List<AppState> list) {
//            ConfigKey.m27239(0);
//            WorkService.this.f11901.m25982();
//            if (!list.isEmpty()) {
//                Iterator<AppState> it = list.iterator();
//                while (it.hasNext()) {
//                    WorkService.this.f11901.m25981(it.next());
//                }
//                WorkService.this.f19265.m22959();
//            }
//            WorkService.this.f19273.m25989();
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvp.service.WorkService$蝸餺閃喍 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class C5777 implements ProgressBus.InterfaceC5948<Boolean> {
//        public C5777(WorkService workService) {
//        }
//
//        @Override // p197.ProgressBus.InterfaceC5948
//        @NonNull
//        /* renamed from: 肌緭 */
//        public Boolean getValue(String str) {
//            List find = LitePal.where("type != 0 AND type != 5").find(VoiceDbBean.class);
//            if (find == null) {
//                return Boolean.FALSE;
//            }
//            Iterator it = find.iterator();
//            while (it.hasNext()) {
//                if (ObjectUtils.equals(((VoiceDbBean) it.next()).getPackageName(), str)) {
//                    return Boolean.TRUE;
//                }
//            }
//            return Boolean.FALSE;
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvp.service.WorkService$酸恚辰橔纋黺 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class RunnableC5778 implements Runnable {
//        public RunnableC5778(WorkService workService) {
//        }
//
//        @Override // java.lang.Runnable
//        public void run() {
//            Mgr.m24658().mo14182();
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvp.service.WorkService$鑭撇糁綖浓緗轟鱼萟磿焈 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class C5779 implements SignalClient.InterfaceC6017 {
//        public C5779(WorkService workService) {
//        }
//
//        @Override // p227.SignalClient.InterfaceC6017
//        public void onDisconnected() {
//            LogHelper.m27988("Socket 断开连接");
//        }
//
//        @Override // p227.SignalClient.InterfaceC6017
//        /* renamed from: 刻槒唱镧詴 */
//        public void mo19957(String str, String str2) {
//            LogHelper.m27988("Socket 用户断开");
//        }
//
//        @Override // p227.SignalClient.InterfaceC6017
//        /* renamed from: 垡玖 */
//        public void mo13522(String str) {
//        }
//
//        @Override // p227.SignalClient.InterfaceC6017
//        /* renamed from: 旞莍癡 */
//        public void mo19958(String str, String str2) {
//            LogHelper.m27988("Socket 用户加入成功");
//        }
//
//        @Override // p227.SignalClient.InterfaceC6017
//        /* renamed from: 灞酞輀攼嵞漁綬迹 */
//        public void mo19959(String str) {
//            try {
//                LogHelper.m27988("Socket message:" + str);
//                Event event = (Event) new Gson().m26639(str, Event.class);
//                if (ObjectUtils.isNotEmpty(event)) {
//                    if (!LitePal.isExist(PushMessageData.class, "messageId = ?", String.valueOf(event.getMessageId()))) {
//                        PushMessageData pushMessageData = new PushMessageData();
//                        pushMessageData.setMessageId(event.getMessageId());
//                        pushMessageData.save();
//                        EventBus.m20451().m20455(event);
//                    } else {
//                        LogHelper.m27988("Socket:" + event.getMessageId() + "消息已存在");
//                    }
//                } else {
//                    LogHelper.m27988("Event 数据解析失败");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override // p227.SignalClient.InterfaceC6017
//        /* renamed from: 肌緭 */
//        public void mo13523() {
//            LogHelper.m27988("Socket 连接成功");
//        }
//
//        @Override // p227.SignalClient.InterfaceC6017
//        /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
//        public void mo19960(String str, String str2) {
//        }
//
//        @Override // p227.SignalClient.InterfaceC6017
//        /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
//        public void mo19961(String str, String str2) {
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvp.service.WorkService$镐藻 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class RunnableC3243 implements Runnable {
//
//        /* renamed from: com.zlfc.child.mvp.service.WorkService$镐藻$肌緭 */
//        /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//        public class C5780 implements InterfaceC4081<List<PushMessageData>> {
//
//            /* renamed from: com.zlfc.child.mvp.service.WorkService$镐藻$肌緭$刻槒唱镧詴 */
//            /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//            public class RunnableC5781 implements Runnable {
//                public RunnableC5781() {
//                }
//
//                @Override // java.lang.Runnable
//                public void run() {
//                    WorkService.this.m19943();
//                }
//            }
//
//            /* renamed from: com.zlfc.child.mvp.service.WorkService$镐藻$肌緭$肌緭 */
//            /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//            public class C5782 extends ThreadUtils.SimpleTask<Object> {
//
//                /* renamed from: 肌緭 */
//                public final /* synthetic */ List f11917;
//
//                public C5782(C5780 c5780, List list) {
//                    this.f11917 = list;
//                }
//
//                @Override // com.blankj.utilcode.util.ThreadUtils.Task
//                public Object doInBackground() {
//                    for (PushMessageData pushMessageData : this.f11917) {
//                        if (!LitePal.isExist(PushMessageData.class, "messageId = ?", String.valueOf(pushMessageData.getMessageId()))) {
//                            PushMessageData pushMessageData2 = new PushMessageData();
//                            pushMessageData2.setMessageId(pushMessageData.getMessageId());
//                            pushMessageData2.save();
//                            if (101 == pushMessageData.getCode()) {
//                                try {
//                                    long time = (DateUtil.m22577(pushMessageData.getCreateTime(), "yyyy-MM-dd HH:mm:ss").getTime() + ((new JSONObject(pushMessageData.getExtra()).optInt("lock_time") * 60) * 1000)) - System.currentTimeMillis();
//                                    if (time > 0) {
//                                        int m22590 = (int) DateUtil.m22590(time);
//                                        JSONObject jSONObject = new JSONObject();
//                                        jSONObject.put("lock_time", m22590);
//                                        String jSONObject2 = jSONObject.toString();
//                                        Event event = new Event();
//                                        event.setExtra(jSONObject2);
//                                        event.setCode(pushMessageData.getCode());
//                                        event.setGuard_alias(pushMessageData.getAlias());
//                                        event.setDisplay(pushMessageData.getDisplay());
//                                        event.setMessageId(pushMessageData.getMessageId());
//                                        event.setUid(pushMessageData.getUid());
//                                        event.post();
//                                    }
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            } else if (117 == pushMessageData.getCode()) {
//                                try {
//                                    int optInt = new JSONObject(pushMessageData.getExtra()).optInt("intervalTime");
//                                    LogUtil.m13850("intervalTime:" + optInt);
//                                    if (optInt > 0) {
//                                        m13524(pushMessageData);
//                                    }
//                                } catch (Exception e2) {
//                                    e2.printStackTrace();
//                                }
//                            } else if (143 != pushMessageData.getCode()) {
//                                m13524(pushMessageData);
//                            } else if (System.currentTimeMillis() - DateUtil.m22577(pushMessageData.getCreateTime(), "yyyy-MM-dd HH:mm:ss").getTime() < 300000) {
//                                m13524(pushMessageData);
//                            }
//                        } else {
//                            LogHelper.m27988("离线消息:" + pushMessageData.getMessageId() + "已存在");
//                            LitePal.deleteAll((Class<?>) PushMessageData.class, "messageId = ?", String.valueOf(pushMessageData.getMessageId()));
//                        }
//                    }
//                    return null;
//                }
//
//                @Override // com.blankj.utilcode.util.ThreadUtils.Task
//                public void onSuccess(Object obj) {
//                }
//
//                /* renamed from: 肌緭 */
//                public void m13524(PushMessageData pushMessageData) {
//                    Event event = new Event();
//                    event.setExtra(pushMessageData.getExtra());
//                    event.setCode(pushMessageData.getCode());
//                    event.setGuard_alias(pushMessageData.getAlias());
//                    event.setDisplay(pushMessageData.getDisplay());
//                    event.setOffLine(true);
//                    event.post();
//                }
//            }
//
//            public C5780() {
//            }
//
//            @Override // p433.InterfaceC4081
//            /* renamed from: 刻槒唱镧詴 */
//            public void mo16741(int i, String str) {
//                if (AppHelper.m24161(WorkService.this.f19262)) {
//                    return;
//                }
//                WorkService.this.f19254.removeCallbacksAndMessages(null);
//                WorkService.this.f19254.postDelayed(new RunnableC5781(), 60000L);
//            }
//
//            @Override // p433.InterfaceC4081
//            public void mo5631(List<PushMessageData> list) {
//                WorkService.this.f19254.removeCallbacksAndMessages(null);
//                if (ObjectUtils.isNotEmpty((Collection) list)) {
//                    ThreadUtils.executeBySingle(new C5782(this, list));
//                } else {
//                    LogHelper.m27988("无离线消息");
//                    LitePal.deleteAll((Class<?>) PushMessageData.class, new String[0]);
//                }
//            }
//        }
//
//        public RunnableC3243() {
//        }
//
//        @Override // java.lang.Runnable
//        public void run() {
//            String m21223 = CommonUtils.m21223();
//            Map<String, Object> m23986 = ProduceReqArg.m23986(m21223);
//            m23986.put("device_id", m21223);
//            m23986.put(JThirdPlatFormInterface.KEY_TOKEN, CommonUtils.m13846());
//            m23986.put("d_id", CommonUtils.m21234());
//            HttpClient httpClient = WorkService.this.f11900;
//            httpClient.m25016(httpClient.m25018().m25029(m23986), new C5780());
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvp.service.WorkService$陟瓠魒踱褢植螉嚜 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class RunnableC5783 implements Runnable {
//        public RunnableC5783() {
//        }
//
//        @Override // java.lang.Runnable
//        public void run() {
//            if (ConfigKey.m16029() && !ConfigKey.m27256()) {
//                AppHelper.m24159(App.mContext);
//                LogHelper.m27986().m16303("APP管理", "孩子端关闭,服务停止");
//                LooperUtils.m23746();
//                LooperUtils.m23745();
//                WorkService.this.f19269.cancelAll();
//                WorkService.this.stopSelf();
//                return;
//            }
//            if (!ConfigKey.m27247()) {
//                WorkService.this.m19947("孩子端未运行");
//                WorkService.this.f11902.m23741(1000L);
//                return;
//            }
//            Mgr.m24658().mo22193();
//            if (AppHelper.m24161(WorkService.this.f19262)) {
//                WorkService.this.m19936();
//                WorkService.this.f11902.m23741(1000L);
//                return;
//            }
//            Mgr.m24658().mo22201();
//            Mgr.m24658().mo22206();
//            AppAddOrUnInstall.m26056();
//            WorkService.this.f19260.m15578();
//            WorkService.this.m19936();
//            if (System.currentTimeMillis() - WorkService.this.f19263 > (C6891.m25854("location_cycle", 10) * 60 * 1000) + 600000) {
//                WorkService.this.f19263 = System.currentTimeMillis();
//                WorkService.this.f11904.m20881();
//            }
//            WorkService.this.f11902.m23741(1000L);
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvp.service.WorkService$鞈鵚主瀭孩濣痠閕讠陲檓敐 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class C5784 implements InterfaceC6005<List<LockInfoBean>> {
//        public C5784() {
//        }
//
//        @Override // p220.InterfaceC6005
//        /* renamed from: 肌緭 */
//        public /* synthetic */ void mo5628(String str) {
//            C6003.m13968(this, str);
//        }
//
//        @Override // p220.InterfaceC6005
//        /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
//        public /* synthetic */ void mo16739(List<LockInfoBean> list) {
//            C6003.m21618(this, list);
//        }
//
//        @Override // p220.InterfaceC3530
//        /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
//        public void mo16738(List<LockInfoBean> list) {
//            LogHelper.m27986().m27999("获取到的定时锁定数据:" + GsonUtils.toJson(list));
//            WorkService.this.f19257.m21576(list, false);
//        }
//    }
//
//    static {
//        StubApp.interface11(11956);
//        f19247 = Calendar.getInstance(Locale.CHINA);
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void getEvent(Event event) {
//        int code = event.getCode();
//        String extra = event.getExtra();
//        String guard_alias = event.getGuard_alias();
//        if (event.getUid() != 0) {
//            C6891.m15531("parent_uid", event.getUid());
//        }
//        if (ObjectUtils.isNotEmpty((CharSequence) event.getMessageId())) {
//            LogHelper.m27986().m16303("推送", "推送:" + event);
//        }
//        boolean z = true;
//        switch (code) {
//            case 101:
//                if (ObjectUtils.isNotEmpty((CharSequence) extra)) {
//                    try {
//                        if (new JSONObject(extra).optInt("lock_time") > 0) {
//                            if (this.f19257.m21578()) {
//                                this.f19257.m21571();
//                            }
//                            long j = r0 * 60 * 1000;
//                            ConfigKey.m27254();
//                            LockTemInfoBean lockTemInfoBean = new LockTemInfoBean();
//                            lockTemInfoBean.setStartTime(System.currentTimeMillis());
//                            lockTemInfoBean.setTime(j);
//                            lockTemInfoBean.setEndTime(lockTemInfoBean.getStartTime() + j);
//                            C6891.m25859("lock_tem_data", GsonUtils.toJson(lockTemInfoBean));
//                            this.f19257.m21563();
//                            return;
//                        }
//                        return;
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        return;
//                    }
//                }
//                return;
//            case 102:
//                Mgr.m24658().mo22190(this);
//                return;
//            case 103:
//            case 104:
//            case 106:
//            case 109:
//            case 113:
//            case 114:
//            case 116:
//            case 118:
//            case 119:
//            case 120:
//            case 123:
//            case 124:
//            case 125:
//            case Event.APP_COMPARE /* 137 */:
//            case 139:
//            default:
//                return;
//            case 105:
//                m19948(guard_alias);
//                return;
//            case 107:
//                this.f19257.m21569();
//                return;
//            case 108:
//                this.f19273.m25992(new C5766());
//                return;
//            case 110:
//                if (ObjectUtils.isNotEmpty((CharSequence) extra)) {
//                    Mgr.m15110().mo23041();
//                    AppState appState = (AppState) JsonUtil.m13849(extra, AppState.class);
//                    if (appState != null) {
//                        if (Mgr.m24656().m24659()) {
//                            if (appState.getStatus() == 0) {
//                                Mgr.m24657().mo23046(appState.getPackageName(), 1);
//                            } else {
//                                Mgr.m24657().mo14474(appState.getPackageName());
//                                ChildDBManager.m14911(appState.getPackageName());
//                            }
//                        }
//                        int status = appState.getStatus();
//                        if (status == 0) {
//                            if (appState.getAppType() == 1) {
//                                SafeUtils.m25630();
//                            }
//                            this.f11901.m25983(appState);
//                        } else if (status == 1) {
//                            this.f11901.m25985(appState);
//                        } else if (status == 2) {
//                            this.f11901.m15568(appState);
//                            this.f19265.m22961();
//                        } else if (status == 4) {
//                            this.f11901.m25981(appState);
//                            this.f19265.m22959();
//                        } else if (status == 6) {
//                            this.f11901.m25980(appState);
//                            this.f19265.m22962();
//                        } else if (status == 7) {
//                            this.f11901.m25984(appState);
//                        }
//                        this.f19273.m25989();
//                        return;
//                    }
//                    return;
//                }
//                return;
//            case 111:
//                if (ObjectUtils.isNotEmpty((CharSequence) extra)) {
//                    try {
//                        PlugBean plugBean = (PlugBean) JsonUtil.m13849(new JSONObject(extra).getJSONObject("pluginInfo").toString(), PlugBean.class);
//                        if (plugBean != null) {
//                            PlugHelper.m19875().m19884(plugBean);
//                            return;
//                        }
//                        return;
//                    } catch (Exception e2) {
//                        e2.printStackTrace();
//                        return;
//                    }
//                }
//                return;
//            case 112:
//                if (ObjectUtils.isNotEmpty((CharSequence) extra)) {
//                    try {
//                        PlugBean plugBean2 = (PlugBean) JsonUtil.m13849(new JSONObject(extra).getJSONObject("pluginInfo").toString(), PlugBean.class);
//                        if (plugBean2 != null) {
//                            PlugHelper.m19875().m13496(plugBean2);
//                            return;
//                        }
//                        return;
//                    } catch (Exception e3) {
//                        e3.printStackTrace();
//                        return;
//                    }
//                }
//                return;
//            case 115:
//                Mgr.m24658().mo22193();
//                Mgr.m24658().mo22206();
//                long m25860 = C6891.m25860("update_app_time", 0L);
//                long currentTimeMillis = System.currentTimeMillis();
//                if (currentTimeMillis - m25860 > 300000) {
//                    C6891.m25855("update_app_time", currentTimeMillis);
//                    this.f19255.m15588();
//                    this.f19255.m26073();
//                }
//                try {
//                    this.f19259.submit(new FutureTask(new CallableC5767(this, null)));
//                } catch (Exception e4) {
//                    e4.printStackTrace();
//                }
//                LooperUtils.m23744();
//                SafeGuardManager.m25638(this.f19262).m25649();
//                if (ObjectUtils.isNotEmpty((CharSequence) extra)) {
//                    try {
//                        ConnectBean connectBean = (ConnectBean) JsonUtil.m13849(extra, ConnectBean.class);
//                        if (connectBean != null) {
//                            if (ObjectUtils.isEmpty((CharSequence) CommonUtils.m21234())) {
//                                C6891.m25859("child_c_d_id", connectBean.getD_id());
//                                ChildBind childBind = new ChildBind();
//                                childBind.setD_id(Integer.parseInt(connectBean.getD_id()));
//                                childBind.save();
//                            }
//                            ConfigKey.m27257(connectBean.isOverdue());
//                            SafeGuardManager.m25638(App.mContext).m15463(connectBean.isOverdue());
//                            DexHelper.m16144(connectBean.getPlugVersion());
//                            if (connectBean.getIsSuper() == 0 && Mgr.m24658().mo22203()) {
//                                Mgr.m24656().m15111();
//                                this.f19273.m25993(null);
//                            }
//                            if (connectBean.isInstallApp()) {
//                                DownLoadManager.m23844(this);
//                                return;
//                            }
//                            return;
//                        }
//                        return;
//                    } catch (Exception e5) {
//                        e5.printStackTrace();
//                        return;
//                    }
//                }
//                return;
//            case 117:
//                if (ObjectUtils.isNotEmpty((CharSequence) extra)) {
//                    try {
//                        C6891.m15531("location_cycle", new JSONObject(extra).optInt("intervalTime"));
//                        this.f11904.m20879();
//                        return;
//                    } catch (Exception e6) {
//                        e6.printStackTrace();
//                        return;
//                    }
//                }
//                return;
//            case 121:
//                if (ObjectUtils.isNotEmpty((CharSequence) extra)) {
//                    this.f19257.m21576(JsonUtil.m21243(extra, LockInfoBean.class), true);
//                    return;
//                }
//                return;
//            case 122:
//                if (ObjectUtils.isNotEmpty((CharSequence) extra)) {
//                    try {
//                        LockInfoBean lockInfoBean = (LockInfoBean) JsonUtil.m13849(extra, LockInfoBean.class);
//                        if (lockInfoBean != null) {
//                            this.f19257.m21579(lockInfoBean.getLock_id());
//                            return;
//                        }
//                        return;
//                    } catch (Exception e7) {
//                        e7.printStackTrace();
//                        return;
//                    }
//                }
//                return;
//            case 126:
//                if (ObjectUtils.isNotEmpty((CharSequence) extra)) {
//                    try {
//                        Mgr.m15110().mo23041();
//                        int i = new JSONObject(extra).getInt("controls_status");
//                        App.showToast(i == 0 ? "暂停管控中" : "管控成功");
//                        m19947(i == 0 ? "已暂停管控" : AppUtils.getAppName() + "运行中..");
//                        Mgr.m15110().mo23038(i);
//                        BaseGuardAppMgr m24657 = Mgr.m24657();
//                        if (i != 0) {
//                            z = false;
//                        }
//                        m24657.mo23044(z);
//                        this.f19273.m26000(i);
//                        if (i != 0) {
//                            SafeGuardManager.m25638(App.mContext).m25651("管控开启");
//                            return;
//                        } else {
//                            if (ConfigKey.m27245()) {
//                                this.f19257.m21565(UnLockFrom.PAUSE_MANAGER);
//                                return;
//                            }
//                            return;
//                        }
//                    } catch (Exception e8) {
//                        e8.printStackTrace();
//                        return;
//                    }
//                }
//                return;
//            case 127:
//                try {
//                    long optLong = new JSONObject(extra).optLong("limit_time") * 60 * 1000;
//                    LogHelper.m27978("LimitHelper", "获取到组合限制时间:" + optLong);
//                    if (C6891.m25854("groupTime", TimeConstants.HOUR) != optLong) {
//                        C6891.m25855("groupTime", optLong);
//                        this.f19266.m25888();
//                        this.f19265.m22959();
//                        return;
//                    }
//                    return;
//                } catch (JSONException e9) {
//                    e9.printStackTrace();
//                    return;
//                }
//            case 128:
//                if (ObjectUtils.isNotEmpty((CharSequence) extra)) {
//                    try {
//                        LevelMessageWindow m14720 = LevelMessageWindow.m14720(this);
//                        JSONArray optJSONArray = new JSONObject(extra).optJSONArray("info");
//                        if (optJSONArray != null) {
//                            m14720.m23739(JsonUtil.m21243(optJSONArray.toString(), LevelMessageBean.class));
//                            return;
//                        }
//                        return;
//                    } catch (JSONException e10) {
//                        e10.printStackTrace();
//                        return;
//                    }
//                }
//                return;
//            case Event.CHILD_UPDATE /* 129 */:
//                if (ObjectUtils.isNotEmpty((CharSequence) extra)) {
//                    try {
//                        String optString = new JSONObject(extra).optString(LitePalParser.NODE_VERSION);
//                        if (ObjectUtils.isNotEmpty((CharSequence) optString)) {
//                            if (Long.parseLong(optString) > AppUtils.getAppVersionCode()) {
//                                Intent intent = new Intent(App.getSafeContext(), (Class<?>) ChildUpdateActivity.class);
//                                intent.setFlags(C2860y.f10012a);
//                                App.getSafeContext().startActivity(intent);
//                                ThreadUtils.runOnUiThreadDelayed(new RunnableC5771(this), 1000L);
//                            } else {
//                                App.showToast("当前已是最新版,无需更新");
//                            }
//                        }
//                        return;
//                    } catch (JSONException e11) {
//                        e11.printStackTrace();
//                        return;
//                    }
//                }
//                return;
//            case Event.APP_REWARD /* 130 */:
//                if (ObjectUtils.isNotEmpty((CharSequence) extra)) {
//                    try {
//                        JSONObject jSONObject = new JSONObject(extra);
//                        JSONObject optJSONObject = jSONObject.optJSONObject("reward");
//                        long j2 = jSONObject.getLong("time") * 1000;
//                        if (DateUtil.m22586(j2)) {
//                            long optLong2 = jSONObject.optLong("limit_time");
//                            if (optLong2 > 0) {
//                                RewardProvide.m13982().m21649(optLong2 * 60 * 1000, j2);
//                                this.f19265.m22959();
//                            }
//                            if (optJSONObject != null) {
//                                Map map = (Map) JsonUtil.m21242(optJSONObject.toString(), new C3240(this).m14116());
//                                if (map != null) {
//                                    for (Map.Entry entry : map.entrySet()) {
//                                        String str = (String) entry.getKey();
//                                        if (((Integer) entry.getValue()) != null) {
//                                            RewardProvide.m13982().m21654(str, r2.intValue() * 60 * 1000, j2);
//                                        }
//                                    }
//                                }
//                                this.f19265.m22961();
//                                return;
//                            }
//                            return;
//                        }
//                        return;
//                    } catch (JSONException e12) {
//                        e12.printStackTrace();
//                        return;
//                    }
//                }
//                return;
//            case Event.APP_LIMIT_GROUP_SET /* 131 */:
//                this.f19273.m25994(4, new C5776());
//                return;
//            case Event.LOCK_SETTING /* 132 */:
//                if (ObjectUtils.isNotEmpty((CharSequence) extra)) {
//                    try {
//                        LockSetting lockSetting = (LockSetting) JsonUtil.m13849(extra, LockSetting.class);
//                        LockSetting m24173 = ChildDBManager.m24173();
//                        if (m24173 == null) {
//                            lockSetting.save();
//                        } else {
//                            m24173.copy(lockSetting);
//                            m24173.update();
//                            lockSetting = m24173;
//                        }
//                        this.f19257.m21582(lockSetting);
//                        return;
//                    } catch (Exception e13) {
//                        e13.printStackTrace();
//                        return;
//                    }
//                }
//                return;
//            case Event.PERMISSION_AUTO /* 133 */:
//                Intent intent2 = new Intent(App.mContext, (Class<?>) PermissionCheckActivity.class);
//                intent2.putExtra(TypedValues.TransitionType.S_FROM, JThirdPlatFormInterface.KEY_CODE);
//                intent2.setFlags(C2860y.f10012a);
//                startActivity(intent2);
//                ThreadUtils.runOnUiThreadDelayed(new RunnableC5768(this), 1000L);
//                return;
//            case Event.UPLOAD_APP_LIST /* 134 */:
//                new LauncherAppGetHelper(this).m15584(new C5774());
//                return;
//            case Event.UPLOAD_LOG /* 135 */:
//                LogHelper.m27986().m16304();
//                return;
//            case Event.APP_USAGE_INFO /* 136 */:
//                if (ObjectUtils.isNotEmpty((CharSequence) extra)) {
//                    try {
//                        JSONObject jSONObject2 = new JSONObject(extra);
//                        this.f19273.m25999(UsageHelper.m24127(jSONObject2.optLong("starTime"), jSONObject2.optLong("endTime")));
//                        return;
//                    } catch (Exception e14) {
//                        e14.printStackTrace();
//                        return;
//                    }
//                }
//                return;
//            case Event.TEMP_UNLOCK /* 138 */:
//                if (ObjectUtils.isNotEmpty((CharSequence) extra)) {
//                    try {
//                        long optLong3 = new JSONObject(extra).optLong("unlock_time");
//                        if (optLong3 > 0) {
//                            this.f19257.m21584(optLong3 * 60 * 1000);
//                            return;
//                        }
//                        return;
//                    } catch (Exception e15) {
//                        e15.printStackTrace();
//                        return;
//                    }
//                }
//                return;
//            case Event.LOCK_TIME_V2_EVENT /* 140 */:
//                this.f19273.m15573(new C5784());
//                return;
//            case Event.CHILD_CONFIG /* 141 */:
//                if (ObjectUtils.isNotEmpty((CharSequence) extra)) {
//                    ChildDBManager.m24198((ChildConfig) JsonUtil.m13849(extra, ChildConfig.class));
//                    return;
//                }
//                return;
//            case Event.WEBSITE_EVENT /* 142 */:
//                this.f19273.m25986();
//                return;
//            case Event.RESTART_CHILD_PHONE /* 143 */:
//                Mgr.m24658().mo14181();
//                return;
//            case Event.VPN_SWITCH /* 144 */:
//                if (ObjectUtils.isNotEmpty((CharSequence) extra)) {
//                    try {
//                        int optInt = new JSONObject(extra).optInt("isWebOpen", 1);
//                        ConfigKey.m27242(optInt == 1);
//                        if (optInt == 1) {
//                            Mgr.m24658().mo22196();
//                        } else {
//                            Mgr.m24658().mo22197();
//                        }
//                        return;
//                    } catch (Exception e16) {
//                        e16.printStackTrace();
//                        return;
//                    }
//                }
//                return;
//            case Event.APP_MANAGER_SWITCH /* 145 */:
//                if (ObjectUtils.isNotEmpty((CharSequence) extra)) {
//                    try {
//                        int optInt2 = new JSONObject(extra).optInt("isAppOpen", 1);
//                        Mgr.m15110().mo23039(optInt2 == 1);
//                        BaseGuardAppMgr m246572 = Mgr.m24657();
//                        if (optInt2 != 1) {
//                            z = false;
//                        }
//                        m246572.mo14475(z);
//                        return;
//                    } catch (Exception e17) {
//                        e17.printStackTrace();
//                        return;
//                    }
//                }
//                return;
//        }
//    }
//
    @Override // android.app.Service
    @Nullable
    public IBinder onBind(Intent intent) {
        return new Binder();
    }
//
//    @Override // android.app.Service, android.content.ComponentCallbacks
//    public void onConfigurationChanged(Configuration configuration) {
//        super.onConfigurationChanged(configuration);
//        LockHelper lockHelper = this.f19257;
//        if (lockHelper != null) {
//            lockHelper.m21572();
//        }
//    }
//
//    @Override // android.app.Service
//    public void onCreate() {
//        super.onCreate();
//        f11899 = this;
//        this.f19250 = AppUtils.getAppName() + "运行中..";
//        this.f19262 = this;
//        m13515();
//        LogHelper.m27986().m16303("保活", "WorkService onCreate");
//        if (!ConfigKey.m27256()) {
//            LogHelper.m27986().m16303("保活", "WorkService 服务停止");
//            f11899 = null;
//            stopSelf();
//            return;
//        }
//        m19949();
//    }
//
//    @Override // android.app.Service
//    public void onDestroy() {
//        super.onDestroy();
//        try {
//            if (this.f19274) {
//                return;
//            }
//            HandlerLooper handlerLooper = this.f11902;
//            if (handlerLooper != null) {
//                handlerLooper.destroy();
//                this.f11904.m13728();
//                EventBus.m20451().m20464(this);
//            }
//            AppAddOrUninstallReceiver appAddOrUninstallReceiver = this.f19267;
//            if (appAddOrUninstallReceiver != null) {
//                appAddOrUninstallReceiver.m15565();
//            }
//            HttpUploadManager httpUploadManager = this.f19273;
//            if (httpUploadManager != null) {
//                httpUploadManager.m25988();
//            }
//            C6492 c6492 = this.f19251;
//            if (c6492 != null) {
//                c6492.m15025();
//            }
//            BootBroadCastReceiver bootBroadCastReceiver = this.f19258;
//            if (bootBroadCastReceiver != null) {
//                unregisterReceiver(bootBroadCastReceiver);
//            }
//            ProgressBusManager.m13824();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Override // android.app.Service
//    public int onStartCommand(Intent intent, int i, int i2) {
//        m13515();
//        return 1;
//    }
//
//    /* renamed from: 卝閄侸靤溆鲁扅 */
//    public final void m19936() {
//        this.f19256.setTime(new Date());
//        int i = this.f19256.get(12);
//        if (i != this.f11903) {
//            this.f11903 = i;
//            this.f19272.m25978();
//            this.f19257.m21563();
//            this.f19265.m14451();
//        }
//        if (this.f19275 == RecyclerView.FOREVER_NS) {
//            this.f19275 = 0L;
//        }
//        int i2 = this.f19256.get(5);
//        Calendar calendar = f19247;
//        if (i2 == calendar.get(5) && this.f19256.get(2) == calendar.get(2)) {
//            float timeInMillis = ((float) (this.f19256.getTimeInMillis() - calendar.getTimeInMillis())) / 1000.0f;
//            if (timeInMillis >= 300.0f) {
//                StringBuilder sb = new StringBuilder();
//                sb.append("今日保存->diff:");
//                sb.append(timeInMillis);
//                sb.append("---min:");
//                sb.append(Math.round(timeInMillis / 60.0f));
//                LogHelper.m27982(sb.toString());
//                ChildDBManager.m14913(Math.round(r1));
//                calendar.setTime(new Date());
//            }
//            f19248 = System.currentTimeMillis();
//            long j = this.f19275;
//            if (j != 0 && j % (this.f19271 + 1800) == 0) {
//                LogHelper.m27986().m16303("App使用时长", "开始上传应用使用情况和孩子端使用时长");
//                this.f19255.m15588();
//                this.f19255.m26073();
//            }
//            this.f19264 = false;
//        } else {
//            LogHelper.m27986().m16303("App使用时长", "进入了新的一天");
//            m19941();
//            if (!this.f19264) {
//                LogHelper.m27982("lastSystemTime=>" + f19248);
//                calendar.setTimeInMillis(f19248);
//                String m22587 = DateUtil.m22587(1);
//                ChildWeek childWeek = (ChildWeek) LitePal.where("date = ?", m22587).findFirst(ChildWeek.class);
//                float m22591 = ((float) (DateUtil.m22591(calendar.get(1), calendar.get(2), calendar.get(5), 23, 59, 59) - calendar.getTimeInMillis())) / 1000.0f;
//                if (childWeek == null) {
//                    childWeek = new ChildWeek();
//                    childWeek.setDate(m22587);
//                    childWeek.setMin(Math.round(m22591 / 60.0f));
//                    childWeek.save();
//                } else {
//                    childWeek.setMin(Math.round(((float) childWeek.getMin()) + (m22591 / 60.0f)));
//                    childWeek.update(childWeek.getBaseObjId());
//                }
//                float timeInMillis2 = ((float) (this.f19256.getTimeInMillis() - DateUtil.m22591(this.f19256.get(1), this.f19256.get(2), this.f19256.get(5), 0, 0, 0))) / 1000.0f;
//                ChildWeek childWeek2 = (ChildWeek) LitePal.where("date = ?", DateUtil.m22599()).findFirst(ChildWeek.class);
//                if (childWeek2 == null) {
//                    childWeek2 = new ChildWeek();
//                    childWeek2.setDate(DateUtil.m22599());
//                    childWeek2.setMin(Math.round(timeInMillis2 / 60.0f));
//                    childWeek2.save();
//                } else {
//                    childWeek2.setMin(Math.round(((float) childWeek2.getMin()) + (timeInMillis2 / 60.0f)));
//                    childWeek2.update(childWeek2.getBaseObjId());
//                }
//                LogHelper.m27986().m16303("App使用时长", "时间修复：上一次休眠的时间-" + f19248 + "====上一天丢失的-" + childWeek.getMin() + "----今天的：" + childWeek2.getMin());
//                this.f19264 = true;
//                calendar.setTime(new Date());
//                new Handler().postDelayed(new RunnableC3242(), (long) new Random().nextInt(300000));
//            } else {
//                calendar.setTime(new Date());
//            }
//        }
//        this.f19275++;
//    }
//
//    /* renamed from: 哠畳鲜郣新剙鳰活茙郺嵝 */
//    public void m19937(Map<String, Object> map) {
//        HttpClient httpClient = this.f11900;
//        httpClient.m25016(httpClient.m25018().m25025(map), new C5765(this));
//    }
//
//    /* renamed from: 唌橅咟 */
//    public final void m19938() {
//        C6492 m24467 = C6492.m24467();
//        this.f19251 = m24467;
//        m24467.m24468(new C5773());
//    }
//
//    /* renamed from: 壋劘跆貭澴綄秽攝煾訲 */
//    public final void m19939() {
//        ProgressBusManager.f20087.m21158(new C5770(this));
//        ProgressBusManager.f12467.m21158(new C5777(this));
//        ProgressBusManager.f20088.m21158(new C3241(this));
//    }
//
//    /* renamed from: 攏瑹迀虚熂熋卿悍铒誦爵 */
//    public void m19940() {
//        try {
//            this.f19268 = LauncherSubSystem.m13531(this);
//            this.f19258 = new BootBroadCastReceiver();
//            IntentFilter intentFilter = new IntentFilter("android.intent.action.ACTION_SHUTDOWN");
//            intentFilter.addAction("android.intent.action.SCREEN_OFF");
//            intentFilter.addAction("android.intent.action.SCREEN_ON");
//            intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
//            intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
//            intentFilter.addAction("android.intent.action.DREAMING_STARTED");
//            intentFilter.addAction("android.intent.action.DREAMING_STOPPED");
//            intentFilter.addAction("android.intent.action.MY_PACKAGE_REPLACED");
//            intentFilter.addAction("android.intent.action.VOICE_COMMAND");
//            if (Build.VERSION.SDK_INT >= 24) {
//                intentFilter.addAction("android.intent.action.USER_UNLOCKED");
//            }
//            intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
//            intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
//            intentFilter.addAction("android.intent.action.REBOOT");
//            registerReceiver(this.f19258, intentFilter);
//        } catch (Throwable th) {
//            th.printStackTrace();
//        }
//    }
//
//    /* renamed from: 斃燸卺驼暲各撟嫺眧樬硱 */
//    public final void m19941() {
//        LogHelper.m27986().m16302();
//        RewardProvide.m13982().m21653();
//    }
//
//    /* renamed from: 杹藗瀶姙笻件稚嵅蔂 */
//    public final void m19942() {
//        this.f19273.m25990();
//    }
//
//    /* renamed from: 枩棥钰蕎睨領喀镎遣跄 */
//    public final void m19943() {
//        if (ConfigKey.m27247()) {
//            this.f19261.removeCallbacksAndMessages(null);
//            this.f19261.post(new RunnableC3243());
//        }
//    }
//
//    /* renamed from: 癎躑選熁 */
//    public final void m19944() {
//        LearningHelper learningHelper = new LearningHelper();
//        this.f19272 = learningHelper;
//        learningHelper.setListener(new C5769());
//    }
//
//    /* renamed from: 礱咄頑 */
//    public final void m19945() {
//        this.f19270.m21758("http://message.zlfc.vip:7080", CommonUtils.m21223());
//        this.f19270.setSignalEventListener(new C5779(this));
//    }
//
//    /* renamed from: 綏牽躵糽稰烳俠垳襨捈桏鷋 */
//    public final void m19946() {
//        HandlerLooper handlerLooper = new HandlerLooper("limit_thread");
//        this.f11902 = handlerLooper;
//        handlerLooper.m23743(new RunnableC5783());
//        LooperUtils.m14723(this.f11902);
//        this.f11904.m20879();
//    }
//
//    /* renamed from: 纩慐 */
//    public final void m13514() {
//        ThreadUtils.runOnUiThreadDelayed(new RunnableC5778(this), 20000L);
//    }
//
//    @Override // p181.LocationHelper.InterfaceC5917
//    /* renamed from: 耣怳匮色紝参凵蛴纆勚躄 */
//    public void mo18258(String str) {
//        LogUtils.m5797d(str);
//    }
//
//    /* renamed from: 蘫聫穯搞哪曁雥贀忬琖嶹 */
//    public final synchronized void m19947(String str) {
//        try {
//            Intent m22785 = ModelManager.m22785("notification");
//            m22785.setFlags(270532608);
//            if (!ObjectUtils.equals("已解除绑定", str)) {
//                this.f19249.setContentIntent(PendingIntent.getActivity(this, 0, m22785, 0));
//            }
//            this.f19249.setContentTitle("运行提示").setSmallIcon(ModelManager.m22765().mo18078()).setContentText(str);
//            this.f19269.notify(1, this.f19249.build());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /* renamed from: 躑漕 */
//    public final void m13515() {
//        try {
////            this.f19249 = new Notification.Builder(this);
////            this.f19269 = (NotificationManager) getSystemService("notification");
////            if (Build.VERSION.SDK_INT >= 26) {
////                NotificationChannel notificationChannel = new NotificationChannel(getPackageName(), "运行提示", 2);
////                notificationChannel.setLockscreenVisibility(-1);
////                notificationChannel.setSound(null, null);
////                this.f19269.createNotificationChannel(notificationChannel);
////                this.f19249.setChannelId(getPackageName());
////            }
////            if (ConfigKey.m16029()) {
////                this.f19250 = "已解绑..";
////            } else {
////                Intent m22785 = ModelManager.m22785("notification");
////                m22785.setFlags(270532608);
////                this.f19249.setContentIntent(PendingIntent.getActivity(this, 0, m22785, 0));
////            }
////            Notification build = this.f19249.setSmallIcon(ModelManager.m22765().mo18078()).setContentTitle("运行提示").setContentText(this.f19250).build();
////            build.flags = 32;
////            startForeground(1, build);
//        } catch (Exception e) {
//            e.printStackTrace();
//            LogHelper.m27986().m16303("手机状态", "通知栏显示异常:" + e.getMessage());
//        }
//    }
//
//    /* renamed from: 辒迳圄袡皪郞箟 */
//    public final void m19948(String str) {
//        this.f19274 = true;
//        if (ObjectUtils.isNotEmpty((CharSequence) str)) {
//            this.f19273.m15572(str);
//        }
//        this.f19257.m21589();
//        this.f19257 = null;
//        Mgr.m24658().mo22191();
//        m19947("已解除绑定");
//        App.showToast("解除绑定成功");
//        this.f11904.m13729();
//        this.f19269.cancelAll();
//        EventBus.m20451().m20464(this);
//        try {
//            BootBroadCastReceiver bootBroadCastReceiver = this.f19258;
//            if (bootBroadCastReceiver != null) {
//                unregisterReceiver(bootBroadCastReceiver);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        AppAddOrUninstallReceiver appAddOrUninstallReceiver = this.f19267;
//        if (appAddOrUninstallReceiver != null) {
//            appAddOrUninstallReceiver.m15565();
//        }
//        LauncherSubSystem launcherSubSystem = this.f19268;
//        if (launcherSubSystem != null) {
//            launcherSubSystem.m20009();
//        }
//        HttpUploadManager httpUploadManager = this.f19273;
//        if (httpUploadManager != null) {
//            httpUploadManager.m25988();
//        }
//        C6492 c6492 = this.f19251;
//        if (c6492 != null) {
//            c6492.m15025();
//        }
//        HandlerLooper handlerLooper = this.f11902;
//        if (handlerLooper != null) {
//            handlerLooper.destroy();
//        }
//        new Handler().postDelayed(new RunnableC5772(), 2000L);
//        stopSelf();
//        f11899 = null;
//    }
//
//    /* renamed from: 销薞醣戔攖餗 */
//    public final void m19949() {
//        EventBus.m20451().m20460(this);
//        this.f11900 = new HttpClient();
//        ConfigKey.m27255();
//        this.f11901 = new AppInterceptManager();
//        f19247.setTime(new Date());
//        new BatteryHelper();
//        this.f11904 = new LocationTimer();
//        this.f19265 = new LimitHelper();
//        HttpUploadManager httpUploadManager = new HttpUploadManager();
//        this.f19273 = httpUploadManager;
//        this.f19255 = new AppUsageTimeHelper(httpUploadManager);
//        this.f19260 = new AppUploadManager(this.f19255);
//        this.f19257 = LockHelper.m21561();
//        this.f19271 = new Random().nextInt(100);
//        this.f11904.setOnLocationListener(this);
//        this.f19266 = AppFilter.m25867();
//        this.f19267 = new AppAddOrUninstallReceiver();
//        m19941();
//        m19944();
//        m19940();
//        m19938();
//        m19950();
//        m19942();
//        m19945();
//        m19946();
//        m19939();
//        m13514();
//    }
//
//    /* renamed from: 鞲冇 */
//    public final void m13516() {
//        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) getSystemService(ActivityChooserModel.ATTRIBUTE_ACTIVITY)).getRunningAppProcesses()) {
//            if (runningAppProcessInfo.pid != Process.myPid()) {
//                Process.killProcess(runningAppProcessInfo.pid);
//            }
//        }
//        Process.killProcess(Process.myPid());
//        System.exit(1);
//    }
//
//    @Override // p181.LocationHelper.InterfaceC5917
//    public void mo18262(TencentLocation tencentLocation) {
//        this.f19263 = System.currentTimeMillis();
//        if (System.currentTimeMillis() - this.f19253 < DateUtils.TEN_SECOND) {
//            return;
//        }
//        this.f19253 = System.currentTimeMillis();
//        if (!NetworkUtils.isConnected()) {
//            if (ObjectUtils.isNotEmpty(Double.valueOf(tencentLocation.getLatitude())) && ObjectUtils.isNotEmpty(Double.valueOf(tencentLocation.getLongitude()))) {
//                ChildCurrLocation childCurrLocation = new ChildCurrLocation();
//                childCurrLocation.setLatitude(String.valueOf(tencentLocation.getLatitude()));
//                childCurrLocation.setLongitude(String.valueOf(tencentLocation.getLongitude()));
//                childCurrLocation.setAddress(ObjectUtils.isNotEmpty((CharSequence) tencentLocation.getAddress()) ? tencentLocation.getAddress() : "");
//                childCurrLocation.setCreateTime(DateUtil.m22596());
//                childCurrLocation.setState(0);
//                childCurrLocation.save();
//                return;
//            }
//            return;
//        }
//        String m21223 = CommonUtils.m21223();
//        Map<String, Object> m23986 = ProduceReqArg.m23986(m21223);
//        Gson gson = new Gson();
//        ArrayList arrayList = new ArrayList();
//        ChildCurrLocation childCurrLocation2 = new ChildCurrLocation();
//        childCurrLocation2.setLongitude(String.valueOf(tencentLocation.getLongitude()));
//        childCurrLocation2.setLatitude(String.valueOf(tencentLocation.getLatitude()));
//        childCurrLocation2.setAddress(ObjectUtils.isNotEmpty((CharSequence) tencentLocation.getAddress()) ? tencentLocation.getAddress() : "");
//        childCurrLocation2.setCreateTime(DateUtil.m22596());
//        arrayList.add(childCurrLocation2);
//        m23986.put("device_id", m21223);
//        m23986.put(LitePalParser.NODE_LIST, gson.m26633(arrayList));
//        m23986.put("d_id", CommonUtils.m21234());
//        m19937(m23986);
//    }
//
//    /* renamed from: 駭鑈趘薑衈講堍趃軏 */
//    public final void m19950() {
//        LogHelper.m27986().m16303("APP管理", "initLauncherConfig Star=>开始检测手机App并上传");
//        new LauncherAppGetHelper(this).m15584(new C5775());
//    }
}