package accessibility;

import android.content.Context;
import android.view.View;

public class ConfirmDialog extends BaseDialog {

    /* renamed from: 刻槒唱镧詴, reason: contains not printable characters */
    public String f19206;

    /* renamed from: 灞酞輀攼嵞漁綬迹, reason: contains not printable characters */
    public String f19207;

    /* renamed from: 肌緭 */
//    public DialogConfrimLayoutBinding f11859;

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭, reason: contains not printable characters */
    public String f19208;

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐, reason: contains not printable characters */
    public String f19209;

    /* renamed from: com.zlfc.child.dialog.ConfirmDialog$刻槒唱镧詴, reason: contains not printable characters */
    /* loaded from: E:\apk\monitor\监控\classes5.dex */
    public class ViewOnClickListenerC5744 implements View.OnClickListener {
        public ViewOnClickListenerC5744() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            ConfirmDialog.this.dismiss();
            InterfaceC3288 interfaceC3288 = ConfirmDialog.this.mDialogClickListener;
            if (interfaceC3288 != null) {
                interfaceC3288.mo16830();
            }
        }
    }

    /* renamed from: com.zlfc.child.dialog.ConfirmDialog$肌緭 */
    /* loaded from: E:\apk\monitor\监控\classes5.dex */
    public class ViewOnClickListenerC3227 implements View.OnClickListener {
        public ViewOnClickListenerC3227() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            ConfirmDialog.this.dismiss();
            InterfaceC3288 interfaceC3288 = ConfirmDialog.this.mDialogClickListener;
            if (interfaceC3288 != null) {
                interfaceC3288.mo5649();
            }
        }
    }

    public ConfirmDialog(Context context, String str, String str2) {
        super(context);
        this.f19206 = str;
        this.f19208 = str2;
//        setRootResID(R$drawable.rounded_corners);
    }

    @Override // com.zlfc.common.base.BaseDialog
    public int getLayoutID() {
//        return R$layout.dialog_confrim_layout;
        return 0;
    }

    @Override // com.zlfc.common.base.BaseDialog
    public void initEvent() {
    }

    @Override // com.zlfc.common.base.BaseDialog
    public void initView() {
//        DialogConfrimLayoutBinding m19852 = DialogConfrimLayoutBinding.m19852(this.view);
//        this.f11859 = m19852;
//        m19852.f19114.setOnClickListener(new ViewOnClickListenerC3227());
//        this.f11859.f19115.setOnClickListener(new ViewOnClickListenerC5744());
//        this.f11859.f19116.setText(this.f19206);
//        this.f11859.f11830.setText(this.f19208);
//        if (ObjectUtils.isNotEmpty((CharSequence) this.f19209)) {
//            this.f11859.f19114.setText(this.f19209);
//        }
//        if (ObjectUtils.isNotEmpty((CharSequence) this.f19207)) {
//            this.f11859.f19115.setText(this.f19207);
//        }
    }

    public ConfirmDialog(Context context, String str, String str2, String str3, String str4) {
        super(context);
        this.f19206 = str;
        this.f19208 = str2;
        this.f19209 = str3;
        this.f19207 = str4;
//        setRootResID(R$drawable.rounded_corners);
    }
}
