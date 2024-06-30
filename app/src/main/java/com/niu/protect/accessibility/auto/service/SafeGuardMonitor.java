package com.niu.protect.accessibility.auto.service;

import android.content.ContextWrapper;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.NonNull;

import com.niu.protect.accessibility.auto.service.tmp.AccessibilityUtils;
import com.niu.protect.accessibility.auto.service.tmp.ChildConfig;

public class SafeGuardMonitor extends ContextWrapper implements InterfaceC3712 {

    /* renamed from: 刻槒唱镧詴 */
    public final AccessibilityHelperService f21266;

    /* renamed from: 灞酞輀攼嵞漁綬迹 */
    public final String f21267;

    /* renamed from: 肌緭 */
    public final AppFilter f13097;

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
    public boolean f21268;

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
    public OsMonitor f21269;

    public SafeGuardMonitor(AccessibilityHelperService accessibilityHelperService) {
        super(accessibilityHelperService);
        this.f21268 = true;
        this.f13097 = AppFilter.m15534(accessibilityHelperService);
        this.f21266 = accessibilityHelperService;
        this.f21267 = getPackageName();
    }

    /* renamed from: 镐藻 */
    public /* synthetic */ void m14525() {
        String packageName = m23168().getPackageName();
        AccessibilityNodeInfo m19976 = m23168().m19976();
        if (m19976 == null || !ObjectUtils.equals(packageName, m19976.getPackageName())) {
            return;
        }
//        FloatCreateViewManager.getInstance().destroy(1);
    }

    @Override // p299.InterfaceC3712
    public void onDestroy() {
//        EventBus.m20451().m20464(this);
    }

    @Override // p383.InterfaceC6489
    /* renamed from: 刻槒唱镧詴 */
    public boolean mo22210(@NonNull AccessibilityEventImpl accessibilityEventImpl) {
        if (accessibilityEventImpl.m15019() == null || !this.f21268) {
            return false;
        }
        if (accessibilityEventImpl.m15019().equals(this.f21267)) {
            return true;
        }
//        if (ObjectUtils.isNotEmpty((CharSequence) SystemSettingActivity.f19351) && ObjectUtils.equals(accessibilityEventImpl.m15020(), SystemSettingActivity.f19351)) {
//            return false;
//        }
        return m23166(accessibilityEventImpl);
    }

    /* renamed from: 旞莍癡 */
    public final boolean m23166(AccessibilityEventImpl accessibilityEventImpl) {
        if (accessibilityEventImpl.m15019() == null || this.f21269 == null || !this.f13097.m25932(accessibilityEventImpl.m24458())) {
            return false;
        }
        if (ObjectUtils.equals("com.android.systemui", accessibilityEventImpl.m15019())) {
            return this.f21269.mo14189(accessibilityEventImpl);
        }
        return this.f21269.mo22210(accessibilityEventImpl);
    }

    @Override // p299.InterfaceC3712
    /* renamed from: 灞酞輀攼嵞漁綬迹 */
    public void mo23163() {
        if (this.f21268) {
            AccessibilityUtils.m24452().postDelayed(new Runnable() { // from class: 攳艪抣.刻槒唱镧詴
                @Override // java.lang.Runnable
                public final void run() {
                    SafeGuardMonitor.this.m14525();
                }
            }, 2000L);
        }
    }

    /* renamed from: 睳堋弗粥辊惶 */
    public void m23167() {
        SafeScreenLockView m23580 = SafeScreenLockView.m23580();
//        if (!RomUtils.isHuawei() && !Tools.m22457()) {
//            if (RomUtils.isXiaomi()) {
        this.f21269 = XiaoMiMonitor.m22235(m23580);
//            } else if (RomUtils.isVivo()) {
//                this.f21269 = VivoMonitor.m22271();
//            } else if (!RomUtils.isOppo() && !Tools.m22497()) {
//                if (RomUtils.isSamsung()) {
//                    this.f21269 = SamsumMonitor.m22248();
//                } else if (RomUtils.isMeizu()) {
//                    this.f21269 = MeizuMonitor.m22285();
//                } else if (RomUtils.isOneplus()) {
//                    this.f21269 = OneplusMonitor.m22231();
//                } else {
//                    this.f21269 = GoogleMonitor.m22208();
//                }
//            } else {
//                this.f21269 = OppoMonitor.m22211();
//            }
//        } else {
//            this.f21269 = HuaweiMonitor.m22259();
//        }
        OsMonitor osMonitor = this.f21269;
        if (osMonitor == null) {
            LogUtil.m13850("没有找到合适的Monitor");
            return;
        }
        osMonitor.m22257(this.f21266);
        AccessibilityNodeInfo m19976 = this.f21266.m19976();
        if (m19976 != null && m19976.getPackageName() != null) {
            if (AppFilter.m25867().m25932(m19976.getPackageName().toString())) {
                this.f21269.m22225(m19976);
            }
        }
//        this.f21269.m22254(ChildDBManager.m24189());
//        NotificationUtils.m20190(this, NotificationListenerServiceImpl.class.getName());
    }

    /* renamed from: 祴嚚橺谋肬鬧舘 */
    public final AccessibilityHelperService m23168() {
        return this.f21266;
    }

    @Override // p383.InterfaceC6489
    /* renamed from: 肌緭 */
    public /* synthetic */ boolean mo14189(AccessibilityEventImpl accessibilityEventImpl) {
//        return C6486.m15015(this, accessibilityEventImpl);
        return false;
    }

    @Override // p299.InterfaceC3712
    /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
    public void mo23164(boolean z) {
        this.f21268 = z;
    }

    @Override // p299.InterfaceC3712
    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
    public void mo23165(ChildConfig childConfig) {
        OsMonitor osMonitor = this.f21269;
        if (osMonitor != null) {
            osMonitor.m22254(childConfig);
        }
    }
}
