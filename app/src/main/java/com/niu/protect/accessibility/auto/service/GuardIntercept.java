package com.niu.protect.accessibility.auto.service;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.view.accessibility.AccessibilityWindowInfo;

import com.blankj.utilcode.util.AppUtils;
import com.niu.protect.accessibility.auto.service.tmp.AppHelper;
import com.niu.protect.accessibility.auto.service.tmp.AppSuperMgrBean;
import com.niu.protect.accessibility.auto.service.tmp.C6891;
import com.niu.protect.accessibility.auto.service.tmp.ChildDBManager;
import com.niu.protect.accessibility.auto.service.tmp.ConfigKey;
import com.niu.protect.accessibility.auto.service.tmp.SecureUtils;

import org.litepal.LitePal;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class GuardIntercept extends BaseGuardIntercept {

        /* renamed from: 肌緭 */
        public static final AtomicLong f12774 = new AtomicLong(System.currentTimeMillis());


//        public class RunnableC6076 implements Runnable {
//            public RunnableC6076(GuardIntercept guardIntercept) {
//            }
//
//            @Override // java.lang.Runnable
//            public void run() {
////                App.mContext.stopService(new Intent(App.mContext, (Class<?>) LocalVpnService.class));
//            }
//        }


        @Override // p407.BaseGuardIntercept
        public boolean mo22192() {
        int r0 = (SecureUtils.m24093() && SecureUtils.m24091(App.mContext)) ? 1 : 0;
            C6891.m15531("super_statue", r0);
            return r0>0;
        }

        @Override // p407.BaseGuardIntercept
        /* renamed from: 刻槒唱镧詴, reason: contains not printable characters */
        public void mo22193() {
            if (AccessibilityHelperService.f19276 == null && !Tools.m22495(App.mContext) && SecureUtils.m24091(App.mContext)) {
                SecureUtils.m14879(1);
                LogHelper.m27986().m27999("无障碍开关开启");
            }
        }

        @Override // p407.BaseGuardIntercept
        /* renamed from: 垡玖 */
        public void mo14180(boolean z) {
            try {
                if (Mgr.m24656().m24659()) {
                    if (z) {
                        SecureUtils.m24078("no_install_apps");
                    } else {
                        SecureUtils.m14876("no_install_apps");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /* renamed from: 彻薯铏螙憣欖愡鼭, reason: contains not printable characters */
        public void m22194() {
            C6891.m25861("upload_app_statue", false);
            C6891.m25859("lock_data", "");
            C6891.m15531("super_statue", 0);
            ConfigKey.m27255();
            C6891.m25863("bindingStatus", false);
            C6891.m25863("show_loading", false);
            C6891.m25864("realmeUiVersion", 0.0f);
            ConfigKey.m27238(2);
            ConfigKey.m27239(0);
            Mgr.m15110().mo23039(true);
        }

        @Override // p407.BaseGuardIntercept
        /* renamed from: 旞莍癡, reason: contains not printable characters */
        public void mo22195(boolean z) {
            try {
                if (Mgr.m24656().m24659()) {
                    SecureUtils.m14878(z);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override // p407.BaseGuardIntercept
        public void mo22190(Context context) {
//            HttpUploadManager httpUploadManager = new HttpUploadManager();
//            if (ScreenUtils.isScreenLock()) {
//                httpUploadManager.m25987(2, "孩子端手机处于锁屏状态不可进行截图");
//                return;
//            }
//            httpUploadManager.m25987(3, !AccessibilityHelperService.m19972() ? "无障碍未开启" : "");
//            SafeScreenCheck.m23158();
//            Intent intent = new Intent(context, (Class<?>) NoneActivity.class);
//            intent.putExtra("openPermission", false);
//            intent.setFlags(C2860y.f10012a);
//            context.startActivity(intent);
        }

        @Override // p407.BaseGuardIntercept
        public void mo22196() {
//            if (LocalVpnService.f19400 || !ConfigKey.m27247()) {
//                return;
//            }
//            if (Mgr.m24656().m24659()) {
//                SecureUtils.m24096(null);
//            }
//            if (VpnService.prepare(App.mContext) == null) {
//                Intent intent = new Intent(App.mContext, (Class<?>) LocalVpnService.class);
//                intent.putExtra("type", 1);
//                App.mContext.startService(intent);
//            }
        }

        @Override // p407.BaseGuardIntercept
        /* renamed from: 灞酞輀攼嵞漁綬迹, reason: contains not printable characters */
        public void mo22197() {
//            if (LocalVpnService.f19399 == null || !LocalVpnService.f19400) {
//                return;
//            }
//            SecureUtils.m24083();
//            Intent intent = new Intent(App.mContext, (Class<?>) LocalVpnService.class);
//            intent.putExtra("type", 2);
//            App.mContext.startService(intent);
//            ThreadUtils.runOnUiThreadDelayed(new RunnableC6076(this), 1000L);
        }

        @Override // p407.BaseGuardIntercept
        /* renamed from: 睳堋弗粥辊惶, reason: contains not printable characters */
        public boolean mo22198() {
            AccessibilityHelperService accessibilityHelperService = AccessibilityHelperService.f19276;
            boolean z = false;
            if (accessibilityHelperService == null) {
                return false;
            }
            List<AccessibilityWindowInfo> windows = accessibilityHelperService.getWindows();
            if (windows != null && !windows.isEmpty()) {
                z = true;
            }
            if (accessibilityHelperService.m19976() != null) {
                return true;
            }
            return z;
        }

        @Override // p407.BaseGuardIntercept
        /* renamed from: 瞙餃莴埲, reason: contains not printable characters */
        public void mo22199() {
            if (Mgr.m24656().m24659()) {
                SecureUtils.m24095(true);
                SecureUtils.m24094(true);
                m22204(true);
                String appPackageName = AppUtils.getAppPackageName();
                SecureUtils.m24092(appPackageName, "android.permission.READ_EXTERNAL_STORAGE", 1);
                SecureUtils.m24092(appPackageName, "android.permission.WRITE_EXTERNAL_STORAGE", 1);
                SecureUtils.m24092(appPackageName, "android.permission.ACCESS_COARSE_LOCATION", 1);
                SecureUtils.m24092(appPackageName, "android.permission.ACCESS_FINE_LOCATION", 1);
                int i = Build.VERSION.SDK_INT;
                if (i >= 29) {
                    SecureUtils.m24092(appPackageName, "android.permission.ACCESS_BACKGROUND_LOCATION", 1);
                }
                if (i >= 23) {
                    SecureUtils.m24092(appPackageName, "android.permission.ACCESS_NOTIFICATION_POLICY", 1);
                }
                SecureUtils.m24092(appPackageName, "android.permission.READ_PHONE_STATE", 1);
                SecureUtils.m24098(AppUtils.getAppPackageName(), true);
            }
        }

        @Override // p407.BaseGuardIntercept
        /* renamed from: 祴嚚橺谋肬鬧舘, reason: contains not printable characters */
        public void mo22200(boolean z) {
            try {
                if (Mgr.m24656().m24659()) {
                    if (z) {
                        SecureUtils.m24078("no_uninstall_apps");
                    } else {
                        SecureUtils.m14876("no_uninstall_apps");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override // p407.BaseGuardIntercept
        /* renamed from: 綩私 */
        public void mo14181() {
            if (Build.VERSION.SDK_INT < 24 || !Mgr.m24656().m24659()) {
                return;
            }
            try {
                SecureUtils.m24088().reboot(SecureUtils.m24082());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override // p407.BaseGuardIntercept
        public void mo22191() {
            m22205();
            ConfigKey.m27238(2);
            m22194();
            LitePal.deleteDatabase(ModelManager.m14371());
//            FloatCreateViewManager.getInstance().destroyAll();
            mo22197();
            ProgressBusManager.m13824();
            ProgressBusManager.m21159();
            AppHelper.m24159(App.mContext);
//            ChildStackManager.m24105().m24106();
            LooperUtils.m23746();
            LooperUtils.m23745();
//            SignalClient.m21756().m21760();
            if (Build.VERSION.SDK_INT >= 23) {
                LiveManager.m27172().m16003();
            }
        }

        @Override // p407.BaseGuardIntercept
        /* renamed from: 肌緭 */
        public void mo14182() {
            LogHelper.m27986().m27999("allStatusSetEnds");
            if (Mgr.m15110().mo14471()) {
                Mgr.m24657().mo23044(true);
            } else if (Mgr.m15110().mo14472()) {
                Mgr.m24657().mo23044(false);
            } else {
                Mgr.m24657().mo14475(false);
            }
        }

        @Override // p407.BaseGuardIntercept
        /* renamed from: 葋申湋骶映鍮秄憁鎓羭, reason: contains not printable characters */
        public void mo22201() {
            try {
                AccessibilityHelperService accessibilityHelperService = AccessibilityHelperService.f19276;
                AtomicLong atomicLong = f12774;
                if (System.currentTimeMillis() - atomicLong.get() > 600000) {
                    if (accessibilityHelperService != null) {
                        List<AccessibilityWindowInfo> windows = accessibilityHelperService.getWindows();
                        boolean z = true;
                        boolean z2 = (windows == null || windows.isEmpty()) ? false : true;
                        if (accessibilityHelperService.m19976() == null) {
                            z = z2;
                        }
                        if (z || !SecureUtils.m24091(App.mContext)) {
                            if (z) {
                                atomicLong.set(System.currentTimeMillis());
                                LogHelper.m27986().m27999("无障碍检测，isAccessibilityEnable is Right");
                                return;
                            }
                            return;
                        }
                        LogHelper.m27986().m27999("无障碍失效，service!=null,关闭无障碍");
                        SecureUtils.m14879(0);
                        atomicLong.set(System.currentTimeMillis());
                        AccessibilityHelperService.f19276 = null;
                        return;
                    }
                    if (Tools.m22495(App.mContext) && SecureUtils.m24091(App.mContext)) {
                        LogHelper.m27986().m27999("无障碍失效，service==null,关闭无障碍");
                        SecureUtils.m14879(0);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override // p407.BaseGuardIntercept
        /* renamed from: 蝸餺閃喍, reason: contains not printable characters */
        public void mo22202() {
            if (SecureUtils.m24093()) {
                m22205();
                SecureUtils.m24098(AppUtils.getAppPackageName(), false);
                SecureUtils.m24087();
                C6891.m15531("super_statue", 0);
                return;
            }
            Tools.m22499(App.mContext);
        }

        @Override // p407.BaseGuardIntercept
        /* renamed from: 酸恚辰橔纋黺, reason: contains not printable characters */
        public boolean mo22203() {
            if (C6891.m25854("super_statue", 0) == 1) {
                return true;
            }
            return mo22192();
        }

        /* renamed from: 鑭撇糁綖浓緗轟鱼萟磿焈, reason: contains not printable characters */
        public void m22204(boolean z) {
            if (SecureUtils.m14880("no_add_user")) {
                SecureUtils.m24089(z);
            }
        }

        @Override // p407.BaseGuardIntercept
        /* renamed from: 镐藻 */
        public void mo14183(Activity activity) {
            List<ActivityManager.AppTask> appTasks = ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE)).getAppTasks();
            if (appTasks == null || appTasks.isEmpty()) {
                return;
            }
            appTasks.get(0).setExcludeFromRecents(true);
        }

        /* renamed from: 陟瓠魒踱褢植螉嚜, reason: contains not printable characters */
        public final void m22205() {
            if (SecureUtils.m24093()) {
                SecureUtils.m24095(false);
                SecureUtils.m24094(false);
                m22204(false);
                SecureUtils.m24083();
                mo14180(false);
                mo22200(false);
                mo22195(false);
                Iterator<AppSuperMgrBean> it = ChildDBManager.m24183().iterator();
                while (it.hasNext()) {
                    Mgr.m24657().mo14474(it.next().getPackageName());
                }
            }
        }

        @Override // p407.BaseGuardIntercept
        /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐, reason: contains not printable characters */
        public void mo22206() {
            if (ConfigKey.m27241()) {
//                if (LocalVpnService.f19400 || !ConfigKey.m27247()) {
//                    return;
//                }
//                if (Mgr.m24656().m24659()) {
//                    SecureUtils.m24096(null);
//                }
//                if (VpnService.prepare(App.mContext) == null) {
//                    Intent intent = new Intent(App.mContext, (Class<?>) LocalVpnService.class);
//                    intent.putExtra("type", 1);
//                    App.mContext.startService(intent);
//                    return;
//                }
                return;
            }
            mo22197();
        }
    }