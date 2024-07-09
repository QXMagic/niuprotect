package accessibility;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.RomUtils;
import com.blankj.utilcode.util.SpanUtils;

import accessibility.lib.ModelManager;

public class PermissionPopWindow {

    /* renamed from: 偣炱嘵蟴峗舟轛, reason: contains not printable characters */
    public TextView f19316;

    /* renamed from: 櫓昛刓叡賜, reason: contains not printable characters */
//    public QMUIButton f19317;

    /* renamed from: 睳堋弗粥辊惶, reason: contains not printable characters */
    public TextView f19318;

    /* renamed from: 蝸餺閃喍, reason: contains not printable characters */
    public InterfaceC5808 f19319;

    /* renamed from: 酸恚辰橔纋黺, reason: contains not printable characters */
    public TextView f19320;

    public final boolean m20471(View view) {
        boolean z = true;
//        if (this.f19512.m20565() == null) {
//            return true;
//        }
//        InterfaceC5858 m20565 = this.f19512.m20565();
//        View view2 = this.f19513;
//        BasePopupHelper basePopupHelper = this.f19512;
//        if (basePopupHelper.f19551 == null && basePopupHelper.f19536 == null) {
//            z = false;
//        }
//        return m20565.m13634(view2, view, z);
        return z;
    }

    public void m20473() {
//        if (m20471(null)) {
//            this.f19512.m20510(false);
//            mo20468(null, false);
//        }
    }


    /* renamed from: com.zlfc.child.mvvm.permission.PermissionPopWindow$刻槒唱镧詴, reason: contains not printable characters */
    /* loaded from: E:\apk\monitor\监控\classes5.dex */
    public interface InterfaceC5808 {
        void onClick();
    }

    /* renamed from: com.zlfc.child.mvvm.permission.PermissionPopWindow$肌緭 */
    /* loaded from: E:\apk\monitor\监控\classes5.dex */
    public class ViewOnClickListenerC3258 implements View.OnClickListener {
        public ViewOnClickListenerC3258() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
//            PermissionPopWindow.this.m20489();
//            if (PermissionPopWindow.this.f19319 != null) {
//                PermissionPopWindow.this.f19319.onClick();
//            }
        }
    }

    public PermissionPopWindow(Context context) {
//        super(context, -1, -1);
//        m20496(80);
    }

    /* renamed from: 嵷徝糁伋痏邜浫袊譃一迴袣, reason: contains not printable characters */
    public void m20074() {
        TextView m20075 = m20075();
        String appName = AppUtils.getAppName();
        int i = Build.VERSION.SDK_INT;
        if (RomUtils.isVivo()) {
            SpanUtils.with(m20075).append("①滑动到最下方,在【下载服务】或【已安装的服务】中找到" + appName + "\n\n").appendImage(ModelManager.m22787("vivo_access")).append("\n\n②点击并开启开关").create();
            return;
        }
        if (!RomUtils.isHuawei() && !Tools.m22457()) {
            if (RomUtils.isXiaomi()) {
                SpanUtils.with(m20075).append("①滑动找到【更多已下载服务】模块\n\n").appendImage(ModelManager.m22787("XIAOMI_ACCESS_1")).append("\n\n②找到" + appName + "并开启开关\n\n").appendImage(ModelManager.m22787("XIAOMI_ACCESS_2")).create();
                return;
            }
            if (!RomUtils.isOppo() && !Tools.m22497() && !RomUtils.isOneplus()) {
                SpanUtils.with(m20075).append("①滑动到最下方,找到【已下载服务】模块\n").append("②找到" + appName + "并开启开关").create();
                return;
            }
            if (i >= 31) {
                SpanUtils.with(m20075).append("①滑动找到【更多】模块\n\n").appendImage(ModelManager.m22787("OPPO_ACCESS_HIGH")).append("\n\n②找到" + appName + "并开启开关").create();
                return;
            }
            SpanUtils.with(m20075).append("①滑动到最下方,找到【已下载服务】模块\n").append("②找到" + appName + "并开启开关\n\n").appendImage(ModelManager.m22787("OPPO_ACCESS_LOW")).create();
            return;
        }
        SpanUtils.with(m20075).append("①滑动到最下方,找到【下载服务】或【已安装的服务】模块\n\n").appendImage(ModelManager.m22787("HUAWEI_ACCESS_1")).append("\n\n②找到" + appName + "并开启开关\n\n").appendImage(ModelManager.m22787("HUAWEI_ACCESS_2")).create();
    }

    /* renamed from: 拁錉鼉緫科銓諒濌矤鹂, reason: contains not printable characters */
    public TextView m20075() {
        return this.f19318;
    }

    /* renamed from: 掣末騾嚺跬骧輣狾懮, reason: contains not printable characters */
    public void m20076() {
//        QMUIButton qMUIButton = this.f19317;
//        if (qMUIButton != null) {
//            qMUIButton.setText("我知道了");
//        }
    }

    /* renamed from: 洣媯幵絮蠽, reason: contains not printable characters */
    public int m20077(Context context) {
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    /* renamed from: 琞驜杫怬, reason: contains not printable characters */
    public void m20078(InterfaceC5808 interfaceC5808) {
        this.f19319 = interfaceC5808;
    }

//    @Override // razerdp.basepopup.BasePopupWindow
//    /* renamed from: 礱咄頑 */
//    public void mo17098(View view) {
//        m20474(ObjectAnimator.ofFloat(m20499(), "translationY", ScreenUtils.getAppScreenHeight(), 0.0f).setDuration(200L));
//        m20482(ObjectAnimator.ofFloat(m20499(), "translationY", 0.0f, ScreenUtils.getAppScreenHeight()).setDuration(200L));
//        this.f19318 = (TextView) m20493(R$id.tvStep);
//        this.f19320 = (TextView) m20493(R$id.tvTitle);
//        this.f19317 = (QMUIButton) m20493(R$id.tvKnow);
//        this.f19316 = (TextView) m20493(R$id.tvImportant);
//        TextView textView = (TextView) m20493(R$id.tvPopTitle);
//        textView.setBackgroundColor(ContextCompat.getColor(m13632(), R$color.theme_color_primary));
//        textView.setPadding(0, m20077(m13632()) + ((int) m20503(10.0f)), 0, (int) m20503(10.0f));
//        this.f19317.setOnClickListener(new ViewOnClickListenerC3258());
//    }

    public void mo20468(View view, boolean z) {
//        View m20491 = m20491();
//        if (m20491 == null) {
//            m20477(new NullPointerException("PopupWindow需要宿主DecorView的WindowToken，请检查是否存在DecorView"));
//            return;
//        }
//        if (m20491.getWindowToken() == null) {
//            m20477(new IllegalStateException("宿主窗口尚未准备好，等待准备就绪后弹出"));
//            m20494(m20491, view, z);
//            return;
//        }
//        m20505("宿主窗口已经准备好，执行弹出");
//        this.f19512.m13641(view, z);
//        try {
//            if (m20497()) {
//                m20477(new IllegalStateException("BasePopup已经显示了"));
//                return;
//            }
//            this.f19512.m20529();
//            if (view != null) {
//                this.f12198.showAtLocation(view, m20470(), 0, 0);
//            } else {
//                this.f12198.showAtLocation(m20491, 0, 0, 0);
//            }
//            m20505("弹窗执行成功");
//        } catch (Exception e) {
//            e.printStackTrace();
//            m20477(e);
//        }
    }



    /* renamed from: 祬贠潪蓺眣蠈銊凚滘, reason: contains not printable characters */
    public void m20079(String str) {
        TextView textView = this.f19316;
        if (textView != null) {
            textView.setVisibility(View.VISIBLE);
            this.f19316.setText(str);
        }
    }

    /* renamed from: 翡埿丘蟻鴔倞贮峾瞋弅, reason: contains not printable characters */
    public void m20080(String str) {
        TextView textView = this.f19320;
        if (textView != null) {
            textView.setText(str);
        }
    }

//    @Override // p267.InterfaceC3640
//    /* renamed from: 肌緭 */
//    public View mo5698() {
//        return m20495(R$layout.permission_check_layout);
//    }
}
