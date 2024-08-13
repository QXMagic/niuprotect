//package im.niu.testapp.accessibility;
//
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.View;
//
//import androidx.annotation.NonNull;
//import androidx.core.internal.view.SupportMenu;
//import androidx.recyclerview.widget.LinearLayoutManager;
//
//import com.blankj.utilcode.util.AppUtils;
//import com.blankj.utilcode.util.ObjectUtils;
//import com.blankj.utilcode.util.RomUtils;
//import com.blankj.utilcode.util.SPUtils;
//import com.blankj.utilcode.util.SpanUtils;
//import com.blankj.utilcode.util.ToastUtils;
//import com.chad.library.adapter.base.BaseQuickAdapter;
//
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;
//
//import java.util.Iterator;
//
//import im.niu.testapp.accessibility.lib.CommonUtils;
//import im.niu.testapp.accessibility.lib.ConfigKey;
//import im.niu.testapp.accessibility.lib.PermissionAdapter;
//import im.niu.testapp.accessibility.lib.PermissionNextState;
//import im.niu.testapp.accessibility.lib.PermissionValues;
//import im.niu.testapp.accessibility.lib.PermissionViewModel;
//import im.niu.testapp.accessibility.lib.SafeScreenCheck;
//
//
//public class PermissionCheckActivity extends Activity {
//
//    /* renamed from: 垡玖 */
//    public PermissionViewModel f11946;
//
//    /* renamed from: 灞酞輀攼嵞漁綬迹 */
//    public TtsHelper f19307;
//
//    /* renamed from: 睳堋弗粥辊惶 */
//    public TipsDialog f19308;
//
//    /* renamed from: 祴嚚橺谋肬鬧舘 */
//    public BackCheckUtils f19309;
//
//    /* renamed from: 酸恚辰橔纋黺 */
//    public boolean f19311;
//
//    /* renamed from: 镐藻 */
//    public ConfirmDialog f11947;
//
//    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
//    public PermissionAdapter f19312;
//
//    /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
//    public final Handler f19310 = new Handler();
//
//    /* renamed from: 旞莍癡 */
//    public final Handler f19306 = new Handler();
//
//    /* renamed from: 偣炱嘵蟴峗舟轛 */
//    public long f19305 = 0;
//
//    /* renamed from: com.zlfc.child.mvvm.permission.PermissionCheckActivity$偣炱嘵蟴峗舟轛 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class C5792 implements BaseDialog.InterfaceC3288 {
//        public C5792() {
//        }
//
//        @Override // com.zlfc.common.base.BaseDialog.InterfaceC3288
//        /* renamed from: 刻槒唱镧詴 */
//        public void mo16830() {
//            PermissionCheckActivity.this.f19308.dismiss();
//            PermissionCheckActivity.this.m20053("开始");
//            PermissionCheckActivity.this.f11946.f19329 = true;
//        }
//
//        @Override // com.zlfc.common.base.BaseDialog.InterfaceC3288
//        /* renamed from: 肌緭 */
//        public void mo5649() {
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvvm.permission.PermissionCheckActivity$刻槒唱镧詴 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class C5793 implements PermissionNextState.StateCall {
//
//        /* renamed from: com.zlfc.child.mvvm.permission.PermissionCheckActivity$刻槒唱镧詴$肌緭 */
//        /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//        public class RunnableC3253 implements Runnable {
//
//            /* renamed from: com.zlfc.child.mvvm.permission.PermissionCheckActivity$刻槒唱镧詴$肌緭$刻槒唱镧詴 */
//            /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//            public class RunnableC5794 implements Runnable {
//                public RunnableC5794() {
//                }
//
//                @Override // java.lang.Runnable
//                public void run() {
//                    if (LocalVpnService.m13569()) {
//                        return;
//                    }
//                    PermissionCheckActivity.this.m20058("Vpn授权失败");
//                    PermissionEvent permissionEvent = new PermissionEvent();
//                    permissionEvent.setCode(100);
//                    permissionEvent.putData("auto_result", false);
//                    permissionEvent.putData("id", 41);
//                    permissionEvent.post();
//                    MessageEvent messageEvent = new MessageEvent();
//                    messageEvent.setMessage("finish_activity");
//                    EventBus.m20451().m20455(messageEvent);
//                }
//            }
//
//            /* renamed from: com.zlfc.child.mvvm.permission.PermissionCheckActivity$刻槒唱镧詴$肌緭$肌緭 */
//            /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//            public class RunnableC5795 implements Runnable {
//                public RunnableC5795() {
//                }
//
//                @Override // java.lang.Runnable
//                public void run() {
//                    if (PermissionAutoManager.checkIndex(PermissionCheckActivity.this.f12051, 23)) {
//                        return;
//                    }
//                    LogUtil.m13850("截屏授权失败");
//                    SafeScreenCheck.m23159();
//                    PermissionEvent permissionEvent = new PermissionEvent();
//                    permissionEvent.setCode(101);
//                    permissionEvent.post();
//                    MessageEvent messageEvent = new MessageEvent();
//                    messageEvent.setMessage("finish_activity");
//                    EventBus.m20451().m20455(messageEvent);
//                }
//            }
//
//
//            public class RunnableC5796 implements Runnable {
//                public RunnableC5796() {
//                }
//
//                @Override // java.lang.Runnable
//                public void run() {
//                    PermissionCheckActivity.this.f11946.f19327 = false;
//                    if (PermissionCheckActivity.this.f11946.f19328 != null) {
//                        PermissionCheckActivity.this.m20058(PermissionCheckActivity.this.f11946.f19328.getPermissionName() + "开始自动授权");
//                        if (PermissionAutoManager.setItem(PermissionCheckActivity.this.f11946.f19328.getId())) {
//                            return;
//                        }
//                        if (PermissionCheckActivity.this.f11946.f19328.getId() != 23 && PermissionCheckActivity.this.f11946.f19328.getId() != 4) {
//                            LogUtil.m13850("自动授权失败添加权限:" + PermissionCheckActivity.this.f11946.f19328.getPermissionName());
//                            PermissionCheckActivity.this.f11946.f19328.setManual(true);
//                            PermissionCheckActivity.this.f11946.f19324.add(PermissionCheckActivity.this.f11946.f19328);
//                        }
//                        PermissionCheckActivity.this.m20053("无自动实现类，手动开启");
//                    }
//                }
//            }
//
//            public RunnableC3253() {
//            }
//
//            @Override // java.lang.Runnable
//            public void run() {
//                if (PermissionCheckActivity.this.f11946.f19328 == null) {
//                    return;
//                }
//                PermissionCheckActivity permissionCheckActivity = PermissionCheckActivity.this;
//                PermissionAutoManager.m22513(permissionCheckActivity, permissionCheckActivity.f11946.f19328, true);
//                PermissionCheckActivity.this.m20058(PermissionCheckActivity.this.f11946.f19328.getPermissionName() + "未授权");
//                PermissionCheckActivity.this.f11946.f19327 = true;
//                if (PermissionCheckActivity.this.f11946.f19328.getId() == 23) {
//                    PermissionCheckActivity.this.f11946.f19327 = false;
//                    new Handler().postDelayed(new RunnableC5795(), 5000L);
//                } else if (PermissionCheckActivity.this.f11946.f19328.getId() == 41) {
//                    PermissionCheckActivity.this.f11946.f19327 = false;
//                    new Handler().postDelayed(new RunnableC5794(), 5000L);
//                } else {
//                    PermissionAutoManager.m14282(new RunnableC5796(), 500L);
//                }
//            }
//        }
//
//        public C5793() {
//        }
//
//        @Override // com.zlfc.child.bean.PermissionNextState.StateCall
//        public void call(@NonNull PermissionNextState permissionNextState) {
//            PermissionCheckActivity.this.f11946.f19321 = permissionNextState.getNextState();
//            if (PermissionCheckActivity.this.f11946.f19321 == null) {
//                PermissionCheckActivity.this.m20052();
//                return;
//            }
//            PermissionCheckActivity.this.f11946.f19328 = PermissionCheckActivity.this.f11946.f19321.getCurr();
//            if (PermissionCheckActivity.this.f11946.f19328 == null) {
//                PermissionCheckActivity.this.m20053("viewModel.mCurrentBean为空");
//                return;
//            }
//            PermissionCheckActivity permissionCheckActivity = PermissionCheckActivity.this;
//            permissionCheckActivity.m20058(permissionCheckActivity.f11946.f19328.toString());
//            if (!PermissionAutoManager.checkIndex(PermissionCheckActivity.this.f12051, PermissionCheckActivity.this.f11946.f19328.getId())) {
//                PermissionCheckActivity.this.f19306.postDelayed(new RunnableC3253(), 1000L);
//            } else {
//                PermissionCheckActivity.this.m20053("权限已开启");
//            }
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvvm.permission.PermissionCheckActivity$垡玖 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class RunnableC3254 implements Runnable {
//        public RunnableC3254() {
//        }
//
//        @Override // java.lang.Runnable
//        public void run() {
//            if (PermissionCheckActivity.this.f11946.f11973) {
//                ConfigKey.m27239(0);
//            }
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvvm.permission.PermissionCheckActivity$旞莍癡 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class ViewOnClickListenerC5797 implements View.OnClickListener {
//        public ViewOnClickListenerC5797() {
//        }
//
//        @Override // android.view.View.OnClickListener
//        public void onClick(View view) {
//            new PermissionSettingDialog(PermissionCheckActivity.this.f12051).show();
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvvm.permission.PermissionCheckActivity$櫓昛刓叡賜 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class C5798 implements TtsHelper.InterfaceC7044 {
//
//        /* renamed from: 肌緭 */
//        public final /* synthetic */ String f11956;
//
//        public C5798(String str) {
//            this.f11956 = str;
//        }
//
//        @Override // p524.TtsHelper.InterfaceC7044
//        /* renamed from: 肌緭 */
//        public void mo13540() {
//            PermissionCheckActivity.this.f19307.m26747(this.f11956);
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvvm.permission.PermissionCheckActivity$灞酞輀攼嵞漁綬迹 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class C5799 implements BaseDialog.InterfaceC3288 {
//        public C5799() {
//        }
//
//        @Override // com.zlfc.common.base.BaseDialog.InterfaceC3288
//        /* renamed from: 刻槒唱镧詴 */
//        public void mo16830() {
//            ConfigKey.m27239(0);
//            OprateManager.instance().m27686();
//            if (!PermissionCheckActivity.this.f11946.m20081("code") && !PermissionCheckActivity.this.f11946.m20081("setting")) {
//                PermissionCheckActivity.this.m20063();
//                PermissionCheckActivity.super.onBackPressed();
//            } else {
//                PermissionCheckActivity.this.finish();
//                ChildStackManager.m24105().m24106();
//                SafeUtils.m15457(PermissionCheckActivity.this.f12051);
//            }
//        }
//
//        @Override // com.zlfc.common.base.BaseDialog.InterfaceC3288
//        /* renamed from: 肌緭 */
//        public void mo5649() {
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvvm.permission.PermissionCheckActivity$睳堋弗粥辊惶 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class C5800 implements PermissionFailDialog.InterfaceC5812 {
//        public C5800() {
//        }
//
//        @Override // com.zlfc.child.mvvm.permission.dialog.PermissionFailDialog.InterfaceC5812
//        /* renamed from: 肌緭 */
//        public void mo5715() {
//            PermissionCheckActivity permissionCheckActivity = PermissionCheckActivity.this;
//            permissionCheckActivity.m20050(permissionCheckActivity.f11946.f19328);
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvvm.permission.PermissionCheckActivity$祴嚚橺谋肬鬧舘 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class ViewOnClickListenerC5801 implements View.OnClickListener {
//        public ViewOnClickListenerC5801() {
//        }
//
//        @Override // android.view.View.OnClickListener
//        public void onClick(View view) {
//            Mgr.getInstance().mo22191();
//            App.showToast("已解绑");
//            PermissionCheckActivity.this.finish();
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvvm.permission.PermissionCheckActivity$肌緭 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class C3255 implements BaseQuickAdapter.InterfaceC4891 {
//
//        /* renamed from: com.zlfc.child.mvvm.permission.PermissionCheckActivity$肌緭$刻槒唱镧詴 */
//        /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//        public class C5802 implements PermissionPopWindow.InterfaceC5808 {
//
//            /* renamed from: 肌緭 */
//            public final /* synthetic */ PermissionBean f11961;
//
//            public C5802(PermissionBean permissionBean) {
//                this.f11961 = permissionBean;
//            }
//
//            @Override // com.zlfc.child.mvvm.permission.PermissionPopWindow.InterfaceC5808
//            public void onClick() {
//                PermissionCheckActivity.this.m20065(this.f11961);
//            }
//        }
//
//        /* renamed from: com.zlfc.child.mvvm.permission.PermissionCheckActivity$肌緭$肌緭 */
//        /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//        public class C5803 implements PermissionPopWindow.InterfaceC5808 {
//
//            /* renamed from: 肌緭 */
//            public final /* synthetic */ PermissionBean f11962;
//
//            public C5803(PermissionBean permissionBean) {
//                this.f11962 = permissionBean;
//            }
//
//            @Override // com.zlfc.child.mvvm.permission.PermissionPopWindow.InterfaceC5808
//            public void onClick() {
//                PermissionCheckActivity.this.m20050(this.f11962);
//            }
//        }
//
//        public C3255() {
//        }
//
//        @Override
//        public void mo5648(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//            int id = view.getId();
//            PermissionBean permissionBean = PermissionCheckActivity.this.f11946.f11972.get(i);
//            if (id == R$id.llRoot) {
//                PermissionCheckActivity.this.f11946.f11974 = i;
//                if (permissionBean.getId() == 12) {
//                    PermissionPopWindow permissionPopWindow = new PermissionPopWindow(PermissionCheckActivity.this.f12051);
//                    permissionPopWindow.m20080("无障碍开启步骤:");
//                    PermissionCheckActivity.this.f11946.f19330.m22446(permissionPopWindow.m20075());
//                    permissionPopWindow.m20078(new C5803(permissionBean));
//                    permissionPopWindow.m20473();
//                    return;
//                }
//                if (permissionBean.getId() != 14 || permissionBean.isManual()) {
//                    PermissionCheckActivity.this.m20065(permissionBean);
//                    return;
//                }
//                PermissionPopWindow permissionPopWindow2 = new PermissionPopWindow(PermissionCheckActivity.this.f12051);
//                permissionPopWindow2.m20080("“后台高耗电开关”权限开启步骤:");
//                SpanUtils append = SpanUtils.with(permissionPopWindow2.m20075()).append(permissionBean.getDescriptor() + "\n");
//                int m22787 = ModelManager.m22787("permission_battery_image");
//                if (m22787 != -1) {
//                    append.appendImage(m22787);
//                }
//                append.create();
//                permissionPopWindow2.m20078(new C5802(permissionBean));
//                permissionPopWindow2.m20473();
//                return;
//            }
//            if (id == R$id.ivHelper) {
//                PermissionPopWindow permissionPopWindow3 = new PermissionPopWindow(PermissionCheckActivity.this.f12051);
//                permissionPopWindow3.m20076();
//                if (permissionBean.getId() == 12) {
//                    permissionPopWindow3.m20080("无障碍开启步骤:");
//                    PermissionCheckActivity.this.f11946.f19330.m22446(permissionPopWindow3.m20075());
//                } else {
//                    permissionPopWindow3.m20080(permissionBean.getPermissionName());
//                    permissionPopWindow3.m20075().setText(permissionBean.getDescriptor());
//                }
//                permissionPopWindow3.m20473();
//            }
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvvm.permission.PermissionCheckActivity$葋申湋骶映鍮秄憁鎓羭 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class C5804 implements BaseDialog.InterfaceC3288 {
//        public C5804() {
//        }
//
//        @Override // com.zlfc.common.base.BaseDialog.InterfaceC3288
//        /* renamed from: 刻槒唱镧詴 */
//        public void mo16830() {
//            ChildJumpUtils.m24139();
//            PermissionCheckActivity.this.finish();
//        }
//
//        @Override // com.zlfc.common.base.BaseDialog.InterfaceC3288
//        /* renamed from: 肌緭 */
//        public void mo5649() {
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvvm.permission.PermissionCheckActivity$酸恚辰橔纋黺 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class C5805 implements BaseDialog.InterfaceC3288 {
//
//        /* renamed from: 肌緭 */
//        public final /* synthetic */ InterfaceC6004 f11964;
//
//        public C5805(PermissionCheckActivity permissionCheckActivity, InterfaceC6004 interfaceC6004) {
//            this.f11964 = interfaceC6004;
//        }
//
//        @Override // com.zlfc.common.base.BaseDialog.InterfaceC3288
//        /* renamed from: 刻槒唱镧詴 */
//        public void mo16830() {
//            this.f11964.mo16966();
//        }
//
//        @Override // com.zlfc.common.base.BaseDialog.InterfaceC3288
//        /* renamed from: 肌緭 */
//        public void mo5649() {
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvvm.permission.PermissionCheckActivity$镐藻 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class C3256 implements TtsHelper.InterfaceC7044 {
//        public C3256() {
//        }
//
//        @Override // p524.TtsHelper.InterfaceC7044
//        /* renamed from: 肌緭 */
//        public void mo13540() {
//            if (PermissionCheckActivity.this.m20061()) {
//                if (RomUtils.isVivo()) {
//                    PermissionCheckActivity.this.f19307.m26747("请先按照提示开启后台高耗电权限,随后再开启无障碍权限");
//                } else {
//                    PermissionCheckActivity.this.f19307.m26747("请点击无障碍开关按照提示开启无障碍权限");
//                }
//            }
//        }
//    }
//
//    /* renamed from: com.zlfc.child.mvvm.permission.PermissionCheckActivity$鞈鵚主瀭孩濣痠閕讠陲檓敐 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class C5806 implements BaseDialog.InterfaceC3288 {
//        public C5806() {
//        }
//
//        @Override // com.zlfc.common.base.BaseDialog.InterfaceC3288
//        /* renamed from: 刻槒唱镧詴 */
//        public void mo16830() {
//            ConfigKey.m27239(0);
//            OprateManager.instance().m27686();
//            PermissionCheckActivity.this.m20063();
//            PermissionCheckActivity.super.onBackPressed();
//        }
//
//        @Override // com.zlfc.common.base.BaseDialog.InterfaceC3288
//        /* renamed from: 肌緭 */
//        public void mo5649() {
//        }
//    }
//
//    /* renamed from: 冇絿龞芚薝恾濙邩竺鉼趙滖 */
//    public /* synthetic */ void m20022() {
//        m20064(this.f11946.f19328);
//    }
//
//    /* renamed from: 噜犖丽雚佁 */
//    public static boolean m20025() {
//        return ConfigKey.m27252();
//    }
//
//    /* renamed from: 廰乆毖弾渌恵墄轢 */
//    public static void m20028(Context context, String str) {
//        Intent intent = new Intent(context, (Class<?>) PermissionCheckActivity.class);
//        intent.putExtra(TypedValues.TransitionType.S_FROM, str);
//        context.startActivity(intent);
//    }
//
//    /* renamed from: 掓峠滔譶吓碥嚸樱 */
//    public /* synthetic */ void m20032() {
//        LogUtil.m13850("showTipsDialog");
//        m20064(this.f11946.f19328);
//    }
//
//    /* renamed from: 縵襜黳锱丟鄢涫棉 */
//    public /* synthetic */ void m20041() {
//        m20064(this.f11946.f19328);
//    }
//
//    /* renamed from: 蝑盞藄嫏崱潜未雛銘帏槬湼 */
//    public /* synthetic */ void m20045() {
//        this.f11946.m20082();
//        this.f19312.notifyDataSetChanged();
//        m20055();
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void getEvent(PermissionEvent permissionEvent) {
//        PermissionBean permissionBean;
//        int code = permissionEvent.getCode();
//        if (code != 100) {
//            if (code == 101 && (permissionBean = this.f11946.f19328) != null) {
//                if (18 == permissionBean.getId()) {
//                    FloatCreateViewManager.getInstance().destroy(2);
//                    this.f11946.f19328.setOpen(true);
//                    this.f11946.m20082();
//                    m20055();
//                    return;
//                }
//                if (23 == this.f11946.f19328.getId()) {
//                    if (PermissionAutoManager.checkIndex(this.f12051, 23)) {
//                        this.f11946.f19328.setOpen(true);
//                        this.f11946.m20082();
//                    }
//                    m20053(this.f11946.f19328.getPermissionName());
//                    return;
//                }
//                return;
//            }
//            return;
//        }
//        Bundle bundle = permissionEvent.getBundle();
//        boolean z = bundle.getBoolean("auto_result", false);
//        int i = bundle.getInt("id", 0);
//        if (i == 12) {
//            if (RomUtils.isMeizu()) {
//                if (this.f11946.f11972.isEmpty()) {
//                    return;
//                }
//                this.f11946.f11972.get(0).setOpen(true);
//                m20055();
//                return;
//            }
//            Intent intent = new Intent(App.mContext, (Class<?>) PermissionCheckActivity.class);
//            intent.setFlags(C2860y.f10012a);
//            App.mContext.startActivity(intent);
//            this.f19309.m13537();
//            return;
//        }
//        LogUtil.m13850("自动授权结果:" + z);
//        if (!z) {
//            PermissionBean permissionBean2 = this.f11946.f19328;
//            if (permissionBean2 != null) {
//                if (permissionBean2.getId() != 23 && this.f11946.f19328.getId() != 4) {
//                    LogUtil.m13850("自动授权失败添加权限:" + this.f11946.f19328.getPermissionName());
//                    this.f11946.f19328.setManual(true);
//                    PermissionViewModel permissionViewModel = this.f11946;
//                    permissionViewModel.f19324.add(permissionViewModel.f19328);
//                }
//            } else {
//                LogUtil.m13850("自动授权失败 添加权限为空");
//            }
//        }
//        m20053("自动授权之后" + i);
//    }
//
//
//    @Override // androidx.activity.ComponentActivity, android.app.Activity
//    public void onBackPressed() {
//        int i;
//        if (!m20061()) {
//            super.onBackPressed();
//            return;
//        }
//        PermissionViewModel permissionViewModel = this.f11946;
//        if (permissionViewModel.f19322) {
//            return;
//        }
//        if (permissionViewModel.f19329) {
//            if (System.currentTimeMillis() - this.f19305 > 2000) {
//                this.f19305 = System.currentTimeMillis();
//                return;
//            } else {
//                DialogUtils.m21258(this.f12051, m20061() ? "还差一步即可绑定成功,真的要退出吗?" : "权限即将设置完成,是否要退出?", new C5799());
//                return;
//            }
//        }
//        boolean z = false;
//        if (permissionViewModel.f11972.size() > 0) {
//            boolean z2 = true;
//            i = 0;
//            for (PermissionBean permissionBean : this.f11946.f11972) {
//                if (!permissionBean.isOpen()) {
//                    if (permissionBean.isNeedOpen()) {
//                        z2 = false;
//                    }
//                    i++;
//                }
//            }
//            z = z2;
//        } else {
//            i = 0;
//        }
//        if (z) {
//            DialogUtils.m21258(this.f12051, "您还有" + i + "项权限未开启,是否忽略此权限?", new C5804());
//            return;
//        }
//        DialogUtils.m21258(this.f12051, m20061() ? "还差一步即可绑定成功,真的要退出吗?" : "权限即将设置完成,是否要退出?", new C5806());
//    }
//
//    @Override // com.zlfc.common.base.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
//    public void onDestroy() {
//        super.onDestroy();
//        this.f11946.m20086(true);
//        this.f11946.f11973 = false;
//        ChildStackManager.m24105().m24107(this);
//        ConfigKey.m27239(0);
//        FloatCreateViewManager.getInstance().destroy(3);
//        FloatCreateViewManager.getInstance().destroy(2);
//        this.f19306.removeCallbacksAndMessages(null);
//        TtsHelper ttsHelper = this.f19307;
//        if (ttsHelper != null) {
//            ttsHelper.m15840();
//        }
//    }
//
//    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
//    public void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        Mgr.m15110().mo23042();
//        m20055();
//    }
//
//    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
//    public void onPause() {
//        super.onPause();
//        PermissionViewModel permissionViewModel = this.f11946;
//        permissionViewModel.f11973 = true;
//        PermissionBean permissionBean = permissionViewModel.f19328;
//        if (permissionBean != null && permissionBean.getId() == 18) {
//            m20064(this.f11946.f19328);
//        }
//        PermissionViewModel permissionViewModel2 = this.f11946;
//        if (permissionViewModel2.f19325 == 0 && !permissionViewModel2.f19331) {
//            ConfigKey.m27239(0);
//        } else {
//            this.f19310.removeCallbacksAndMessages(null);
//            this.f19310.postDelayed(new RunnableC3254(), 60000L);
//        }
//    }
//
//    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
//    public void onResume() {
//        super.onResume();
//        ConfigKey.m27239(1);
//        this.f11946.f11973 = false;
//        this.f19310.removeCallbacksAndMessages(null);
//        ConfigKey.f14714 = false;
//        m20055();
//    }
//
//    /* renamed from: 丆劣蜑篞瞴 */
//    public final void m20050(PermissionBean permissionBean) {
//        ConfigKey.f14714 = true;
//        Mgr.m15110().mo23042();
//        PermissionViewModel permissionViewModel = this.f11946;
//        permissionViewModel.f19331 = true;
//        permissionViewModel.f19328 = permissionBean;
//        AccessibilityHelperService.m19965(false);
//        if (RomUtils.isXiaomi() && Build.VERSION.SDK_INT < 33) {
//            try {
//                Tools.m22459(this.f12051);
//                return;
//            } catch (Exception e) {
//                e.printStackTrace();
//                this.f12051.startActivity(new Intent("android.settings.ACCESSIBILITY_SETTINGS"));
//                new Handler().postDelayed(new Runnable() { // from class: 川硸遽捀餞橔.刻槒唱镧詴
//                    @Override // java.lang.Runnable
//                    public final void run() {
//                        PermissionCheckActivity.this.m20022();
//                    }
//                }, 100L);
//                return;
//            }
//        }
//        try {
//            this.f12051.startActivity(new Intent("android.settings.ACCESSIBILITY_SETTINGS"));
//            new Handler().postDelayed(new Runnable() { // from class: 川硸遽捀餞橔.葋申湋骶映鍮秄憁鎓羭
//                @Override // java.lang.Runnable
//                public final void run() {
//                    PermissionCheckActivity.this.m20041();
//                }
//            }, 100L);
//        } catch (Exception e2) {
//            e2.printStackTrace();
//        }
//    }
//
//    @Override // com.zlfc.common.base.BaseActivity
//    /* renamed from: 唌橅咟 */
//    public int mo16776() {
//        return R$layout.permission_layout_activity;
//    }
//
//    /* renamed from: 喁蛯咂趘洐漛摓峿鳭蜋幟 */
//    public final void m20051() {
//        TipsDialog tipsDialog = new TipsDialog(this.f12051, "自动授权完成,剩余权限需要您手动开启,如果确认权限已经开启,请返回当前页面,在弹窗中点击“已成功开启”即可");
//        tipsDialog.setCancelable(false);
//        tipsDialog.setCanceledOnTouchOutside(false);
//        tipsDialog.show();
//        m20056("请手动开启剩余权限 权限全部开启完成后即可绑定成功");
//    }
//
//    @SuppressLint({"NotifyDataSetChanged"})
//    /* renamed from: 媥嗅趎 */
//    public final void m20052() {
//        this.f11946.f19329 = false;
//        FloatCreateViewManager.getInstance().destroy(3);
//        ((PermissionLayoutActivityBinding) this.f19419).f19161.setText("还有以下权限需要您手动开启");
//        PermissionViewModel permissionViewModel = this.f11946;
//        permissionViewModel.f19325 = 1;
//        permissionViewModel.m20083();
//        PermissionViewModel permissionViewModel2 = this.f11946;
//        permissionViewModel2.f19328 = null;
//        permissionViewModel2.f11972.clear();
//        for (PermissionBean permissionBean : this.f11946.f19324) {
//            if (!PermissionAutoManager.checkIndex(this.f12051, permissionBean.getId())) {
//                this.f11946.f11972.add(permissionBean);
//            }
//        }
//        PermissionViewModel permissionViewModel3 = this.f11946;
//        permissionViewModel3.f19331 = false;
//        if (permissionViewModel3.f11972.size() == 0) {
//            if (m20061()) {
//                ChildJumpUtils.m24139();
//                finish();
//                return;
//            } else {
//                ToastUtils.showLong("权限已全部设置成功");
//                finish();
//                ChildStackManager.m24105().m24106();
//                SafeUtils.m15457(this.f12051);
//                return;
//            }
//        }
//        m20051();
//        if (ObjectUtils.isNotEmpty(this.f11946.f19330.m14270())) {
//            PermissionViewModel permissionViewModel4 = this.f11946;
//            permissionViewModel4.f11972.add(permissionViewModel4.f19330.m14270());
//        }
//        this.f19312.notifyDataSetChanged();
//    }
//
//    @Override // com.zlfc.common.base.BaseActivity
//    public void mo16779() {
//        this.f11946 = (PermissionViewModel) m20206(PermissionViewModel.class);
//        String stringExtra = getIntent().getStringExtra(TypedValues.TransitionType.S_FROM);
//        if (ObjectUtils.isEmpty((CharSequence) stringExtra)) {
//            stringExtra = "default";
//        }
//        this.f11946.m13541(stringExtra);
//        this.f19309 = new BackCheckUtils(this);
//        boolean z = true;
//        if (!this.f11946.m20087()) {
//            this.f11946.f19330 = new PermissionValues(m20061());
//            PermissionViewModel permissionViewModel = this.f11946;
//            permissionViewModel.f19321 = permissionViewModel.f19330.m22447();
//            PermissionViewModel permissionViewModel2 = this.f11946;
//            permissionViewModel2.f19324 = permissionViewModel2.f19330.m22441();
//            if (this.f11946.m20081("default")) {
//                PermissionViewModel permissionViewModel3 = this.f11946;
//                permissionViewModel3.f11972.addAll(permissionViewModel3.f19330.m22436());
//                PermissionViewModel permissionViewModel4 = this.f11946;
//                permissionViewModel4.f11972.add(permissionViewModel4.f19330.m22434());
//            } else if (this.f11946.m20081("super")) {
//                PermissionViewModel permissionViewModel5 = this.f11946;
//                permissionViewModel5.f11972.add(permissionViewModel5.f19330.m22434());
//            } else {
//                PermissionViewModel permissionViewModel6 = this.f11946;
//                permissionViewModel6.f11972.add(permissionViewModel6.f19330.m22434());
//            }
//        }
//        Mgr.getInstance().mo23042();
//        LogHelper.m27986();
//        SPUtils.getInstance().put("app_task_id", getTaskId());
//        this.f19312 = new PermissionAdapter(this.f11946.f11972);
//        ((PermissionLayoutActivityBinding) this.f19419).f19160.setLayoutManager(new LinearLayoutManager(this.f12051));
//        this.f19312.bindToRecyclerView(((PermissionLayoutActivityBinding) this.f19419).f19160);
//        this.f19312.setOnItemChildClickListener(new C3255());
//        ((PermissionLayoutActivityBinding) this.f19419).f19162.setOnRightClickListener(new ViewOnClickListenerC5797());
//        if (m20061()) {
//            ((PermissionLayoutActivityBinding) this.f19419).f19163.setVisibility(0);
//            ((PermissionLayoutActivityBinding) this.f19419).f19163.setOnClickListener(new ViewOnClickListenerC5801());
//        }
//        if (this.f11946.m20087()) {
//            return;
//        }
//        if (PermissionAutoManager.checkIndex(this.f12051, 12)) {
//            Iterator<PermissionBean> it = this.f11946.f11972.iterator();
//            while (true) {
//                if (!it.hasNext()) {
//                    break;
//                }
//                PermissionBean next = it.next();
//                if (next.getId() == 12) {
//                    next.setOpen(true);
//                    break;
//                }
//            }
//            if (!this.f11946.m20081(JThirdPlatFormInterface.KEY_CODE) && !CommonUtils.m21239()) {
//                z = false;
//            }
//            m20054(z);
//            return;
//        }
//        this.f19307 = new TtsHelper(new C3256());
//    }
//
//    /* renamed from: 狢橞再欠 */
//    public final void m20053(String str) {
//        m20058(str);
//        PermissionViewModel permissionViewModel = this.f11946;
//        if (permissionViewModel.f19327) {
//            m20058("doAutoGrant拦截了");
//            return;
//        }
//        PermissionNextState permissionNextState = permissionViewModel.f19321;
//        if (permissionNextState == null) {
//            m20052();
//            return;
//        }
//        permissionViewModel.f19331 = true;
//        m20059();
//        PermissionNextState nextState = permissionNextState.getNextState();
//        this.f11946.m13542();
//        if (nextState != null) {
//            ((PermissionLayoutActivityBinding) this.f19419).f19161.setText("正在自动授权,请勿操作界面(" + (this.f11946.m20084() - 1) + "/" + this.f11946.f19330.m22443() + ")");
//        }
//        permissionNextState.onState(new C5793());
//    }
//
//    /* renamed from: 簐抳誑瞔 */
//    public final void m20054(boolean z) {
//        boolean z2;
//        Iterator<PermissionBean> it = this.f11946.f19330.m22437().iterator();
//        while (true) {
//            if (!it.hasNext()) {
//                z2 = true;
//                break;
//            }
//            if (!PermissionAutoManager.checkIndex(this.f12051, it.next().getId())) {
//                z2 = false;
//                break;
//            }
//        }
//        if (z2) {
//            if (m20061()) {
//                ChildJumpUtils.m24139();
//            } else {
//                ToastUtils.showLong("权限已全部设置成功");
//            }
//            finish();
//            return;
//        }
//        if (z) {
//            m20053("开始自动开权限");
//            this.f11946.f19329 = true;
//        } else {
//            m20057();
//        }
//    }
//
//    /* renamed from: 蒎鮋闯剁簫制睆芸槣餀鲚偔 */
//    public final void m20055() {
//        FloatCreateViewManager.getInstance().destroy(1);
//        FloatCreateViewManager.getInstance().destroy(2);
//        PermissionViewModel permissionViewModel = this.f11946;
//        permissionViewModel.f19322 = false;
//        PermissionBean permissionBean = permissionViewModel.f19328;
//        if (permissionBean != null) {
//            int id = permissionBean.getId();
//            PermissionViewModel permissionViewModel2 = this.f11946;
//            int i = permissionViewModel2.f19325;
//            if (i == 0) {
//                if (id == 12) {
//                    if (PermissionAutoManager.checkIndex(this.f12051, permissionViewModel2.f19328.getId())) {
//                        m20054(false);
//                        this.f11946.m20082();
//                    } else {
//                        m20062();
//                    }
//                } else if (id == 14 && !permissionViewModel2.f19328.isManual() && this.f11946.m20084() == 0) {
//                    m20056("接下来请按照提示开启无障碍权限");
//                    this.f11946.m20082();
//                    this.f19312.notifyDataSetChanged();
//                } else if (this.f11946.m20087()) {
//                    m20053("activity销毁后重建继续授权");
//                }
//            } else if (i == 1) {
//                if (permissionViewModel2.f19328.isNeedCheck() && !this.f11946.f19328.isOpen()) {
//                    m20060(new InterfaceC6004() { // from class: 川硸遽捀餞橔.灞酞輀攼嵞漁綬迹
//                        @Override // p220.InterfaceC6004
//                        /* renamed from: 刻槒唱镧詴 */
//                        public final void mo16966() {
//                            PermissionCheckActivity.this.m20045();
//                        }
//
//                        @Override // p220.InterfaceC6004
//                        /* renamed from: 肌緭 */
//                        public /* synthetic */ void mo5670(String str) {
//                            C6006.m13969(this, str);
//                        }
//
//                        @Override // p220.InterfaceC6004
//                        /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
//                        public /* synthetic */ void mo16967() {
//                            C6006.m21619(this);
//                        }
//                    });
//                } else if (!this.f11946.f19328.isOpen()) {
//                    this.f11946.m20085(PermissionAutoManager.checkIndex(this.f12051, this.f11946.f19328.getId()));
//                }
//            }
//        }
//        this.f19312.notifyDataSetChanged();
//        PermissionViewModel permissionViewModel3 = this.f11946;
//        if (permissionViewModel3.f19325 == 1) {
//            permissionViewModel3.f19322 = true;
//            Iterator<PermissionBean> it = permissionViewModel3.f11972.iterator();
//            while (true) {
//                if (!it.hasNext()) {
//                    break;
//                } else if (!it.next().isOpen()) {
//                    this.f11946.f19322 = false;
//                    break;
//                }
//            }
//            if (this.f11946.f19322) {
//                if (m20061()) {
//                    ChildJumpUtils.m24139();
//                    finish();
//                    return;
//                }
//                this.f11946.f11973 = false;
//                ToastUtils.showLong("权限已全部设置成功");
//                finish();
//                ChildStackManager.m24105().m24106();
//                SafeUtils.m15457(this.f12051);
//            }
//        }
//    }
//
//    /* renamed from: 藉祠睮亘滨醃堒捕浗綨恘骛 */
//    public final void m20056(String str) {
//        try {
//            if (m20061()) {
//                TtsHelper ttsHelper = this.f19307;
//                if (ttsHelper == null) {
//                    this.f19307 = new TtsHelper(new C5798(str));
//                } else {
//                    ttsHelper.m26747(str);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /* renamed from: 跏褭憿鸫厶鳅撮 */
//    public final void m20057() {
//        if (this.f19311 || isFinishing()) {
//            return;
//        }
//        TipsDialog tipsDialog = this.f19308;
//        if (tipsDialog == null) {
//            TipsDialog tipsDialog2 = new TipsDialog(this.f12051);
//            this.f19308 = tipsDialog2;
//            tipsDialog2.m19870("开始自动授权");
//            this.f19308.setCanceledOnTouchOutside(false);
//            this.f19308.setCancelable(false);
//            this.f19308.mDialogClickListener = new C5792();
//            this.f19308.show();
//            SpanUtils.with(this.f19308.m13492()).append(AppUtils.getAppName() + "将自动授权对应的权限，授权期间切记").append("不要操作手机,等待权限自动授权完成,").setForegroundColor(SupportMenu.CATEGORY_MASK).setFontSize(18, true).append("避免自动授权失败").create();
//            this.f19311 = true;
//            m20056("请点击谈窗下方按钮开始自动授权 自动授权期间不要操作手机");
//            return;
//        }
//        if (tipsDialog.isShowing()) {
//            return;
//        }
//        this.f19308.show();
//        this.f19311 = true;
//        m20056("请点击谈窗下方按钮开始自动授权 自动授权期间不要操作手机");
//    }
//
//    /* renamed from: 銬闆蛎姉銗撽淵猿瑫擳拋 */
//    public final void m20058(String str) {
//    }
//
//    /* renamed from: 鎂敚粒奐諺蛬猁峭千疮绪斾 */
//    public final void m20059() {
//        if (AccessibilityHelperService.f19276 != null) {
//            View inflate = LayoutInflater.from(this.f12051).inflate(R$layout.view_toast, (ViewGroup) null, false);
//            ((TextView) inflate.findViewById(R.id.message)).setText("自动授权中,请不要操作界面");
//            FloatCreateViewManager.getInstance().create(AccessibilityHelperService.f19276, 3, inflate, 0, 20, 49, null);
//        } else if (Tools.canDrawOverlays(this.f12051)) {
//            View inflate2 = LayoutInflater.from(this.f12051).inflate(R$layout.view_toast, (ViewGroup) null, false);
//            ((TextView) inflate2.findViewById(R.id.message)).setText("自动授权中,请不要操作界面");
//            FloatCreateViewManager.getInstance().create(App.mContext, 3, inflate2, 0, 20, 49, null);
//        }
//    }
//
//    /* renamed from: 鎡濝鞄髄陾糢硬 */
//    public final void m20060(InterfaceC6004 interfaceC6004) {
//        String str;
//        if (isFinishing()) {
//            return;
//        }
//        ConfirmDialog confirmDialog = this.f11947;
//        if (confirmDialog == null) {
//            if (this.f11946.f19328 != null) {
//                str = "您是否按照提示成功操作了" + this.f11946.f19328.getPermissionName() + "权限?";
//            } else {
//                str = "您是否按照提示正确操作了权限?";
//            }
//            ConfirmDialog confirmDialog2 = new ConfirmDialog(this.f12051, "提示", str, "我再看看", "已成功操作");
//            this.f11947 = confirmDialog2;
//            confirmDialog2.mDialogClickListener = new C5805(this, interfaceC6004);
//            this.f11947.show();
//            return;
//        }
//        confirmDialog.show();
//    }
//
//    /* renamed from: 飳伡哼 */
//    public final boolean m20061() {
//        return this.f11946.m20081("default") || this.f11946.m20081("super");
//    }
//
//    /* renamed from: 首滕颩 */
//    public final void m20062() {
//        PermissionFailDialog permissionFailDialog = new PermissionFailDialog(this.f12051, new C5800());
//        permissionFailDialog.show();
//        SpanUtils.with(permissionFailDialog.m20091()).append("【无障碍】未成功开启!,如果出现连续多次失败,可以通过").append("重启手机").setForegroundColor(SupportMenu.CATEGORY_MASK).setFontSize(20, true).append("解决问题!!\n").append("如果有什么问题或建议可以联系下方客服解决!!").create();
//    }
//
//    /* renamed from: 驉鑣偏 */
//    public final void m20063() {
//        int size = this.f11946.f11972.size();
//        if (size > 0) {
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < size; i++) {
//                PermissionBean permissionBean = this.f11946.f11972.get(i);
//                if (!permissionBean.isOpen() && !permissionBean.isVideo() && permissionBean.getId() != -1) {
//                    sb.append(permissionBean.getPermissionName());
//                    sb.append(",");
//                }
//            }
//            AnalyticsUtils.m16299("权限未开完退出,未开启权限:" + sb.toString());
//        }
//    }
//
//    /* renamed from: 麵則療压溩惚軂瑧糉颐衰 */
//    public final void m20064(PermissionBean permissionBean) {
//        if (C6891.m25858("permission_tips", false) || Build.VERSION.SDK_INT < 23 || permissionBean == null || ObjectUtils.isEmpty((CharSequence) permissionBean.getDescriptorName())) {
//            return;
//        }
//        int id = permissionBean.getId();
//        if (id != 12 && id != 14) {
//            OnePxActivity.m19909(this.f12051, permissionBean.getDescriptorName(), permissionBean.getDescriptor());
//            return;
//        }
//        Intent intent = new Intent();
//        intent.putExtra("permissionId", permissionBean.getId());
//        intent.setClass(this.f12051, OperateAnimActivity.class);
//        startActivity(intent);
//    }
//
//    /* renamed from: 鼹碹棲扽熓鏄 */
//    public final void m20065(PermissionBean permissionBean) {
//        int id = permissionBean.getId();
//        if (RomUtils.isVivo()) {
//            if (id != 14) {
//                if (permissionBean.isOpen()) {
//                    return;
//                }
//                if (id != 12 && !PermissionAutoManager.checkIndex(this.f12051, 12)) {
//                    App.showToast("请先开启辅助设置权限");
//                    return;
//                }
//            } else if (permissionBean.isManual() && permissionBean.isOpen()) {
//                return;
//            }
//        } else {
//            if (permissionBean.isOpen()) {
//                return;
//            }
//            if (id != 12 && !PermissionAutoManager.checkIndex(this.f12051, 12)) {
//                App.showToast("请先开启辅助设置权限");
//                return;
//            }
//        }
//        ConfigKey.f14714 = false;
//        ConfigKey.m27239(1);
//        PermissionViewModel permissionViewModel = this.f11946;
//        permissionViewModel.f19331 = true;
//        permissionViewModel.f19328 = permissionBean;
//        AccessibilityHelperService.m19965(false);
//        if (!PermissionAutoManager.m22513(this, permissionBean, false)) {
//            this.f11946.f19328.setOpen(true);
//            this.f19312.notifyDataSetChanged();
//            m20055();
//        } else {
//            if (id == 4 || id == 18 || id == 1 || id == 41) {
//                return;
//            }
//            if (((RomUtils.isOppo() || Tools.m22497()) && Build.VERSION.SDK_INT < 23 && permissionBean.getId() == 3) || Build.VERSION.SDK_INT >= 32) {
//                return;
//            }
//            new Handler().postDelayed(new Runnable() {
//                @Override // java.lang.Runnable
//                public void run() {
//                    PermissionCheckActivity.this.m20032();
//                }
//            }, 100L);
//        }
//    }
//}