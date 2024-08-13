package accessibility;

import android.os.Handler;
import android.os.Looper;

/* compiled from: SafeScreenLockView.java */
/* renamed from: 槩嘣頠.睳堋弗粥辊惶 */
/* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
public class SafeScreenLockView {

    /* renamed from: 镐藻 */
    public static SafeScreenLockView f13250;

    /* renamed from: 垡玖 */
    public boolean f13251;

    /* renamed from: 旞莍癡 */
    public int f21607 = 3;

    /* renamed from: 祴嚚橺谋肬鬧舘 */
    public final Handler f21608 = new Handler(Looper.getMainLooper());

    public boolean m25772(){
        return true;
    }

//    /* compiled from: SafeScreenLockView.java */
//    /* renamed from: 槩嘣頠.睳堋弗粥辊惶$刻槒唱镧詴 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class RunnableC6334 implements Runnable {
//
//        /* renamed from: 肌緭 */
//        public final /* synthetic */ SuperTextView f13252;
//
//        public RunnableC6334(SuperTextView superTextView) {
//            this.f13252 = superTextView;
//        }
//
//        @Override // java.lang.Runnable
//        public void run() {
//            this.f13252.setVisibility(0);
//            if (SafeScreenLockView.this.f21607 <= 0) {
//                SafeScreenLockView.this.f21607 = 3;
//                this.f13252.setText("返回桌面");
//                SafeScreenLockView.this.f13251 = false;
//                this.f13252.m17795(ContextCompat.getColor(App.mContext, R$color.theme_color_primary));
//            } else {
//                this.f13252.setText("返回桌面(" + SafeScreenLockView.this.f21607 + "s)");
//                SafeScreenLockView.this.f21608.postDelayed(this, 1000L);
//            }
//            SafeScreenLockView.m23574(SafeScreenLockView.this);
//        }
//    }
//
//    /* compiled from: SafeScreenLockView.java */
//    /* renamed from: 槩嘣頠.睳堋弗粥辊惶$肌緭 */
//    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
//    public class ViewOnClickListenerC3762 implements View.OnClickListener {
//        public ViewOnClickListenerC3762() {
//        }
//
//        @Override // android.view.View.OnClickListener
//        public void onClick(View view) {
//            if (SafeScreenLockView.this.f13251) {
//                return;
//            }
//            if (!Tools.m22475() || !Tools.m22490()) {
//                SafeUtils.m15457(SafeScreenLockView.this.mo25767());
//            }
//            SafeScreenLockView.this.m25776();
//        }
//    }
//
//    /* renamed from: 壋劘跆貭澴綄秽攝煾訲 */
//    public static /* synthetic */ int m23574(SafeScreenLockView safeScreenLockView) {
//        int i = safeScreenLockView.f21607;
//        safeScreenLockView.f21607 = i - 1;
//        return i;
//    }
//
//    /* renamed from: 攏瑹迀虚熂熋卿悍铒誦爵 */
//    public /* synthetic */ void m23575() {
//        if (m15500() != null) {
//            this.f21608.removeCallbacksAndMessages(null);
//            SuperTextView superTextView = (SuperTextView) m25775(R$id.btn_exit);
//            superTextView.m17795(ContextCompat.getColor(App.mContext, R$color.gray_cc));
//            this.f21608.post(new RunnableC6334(superTextView));
//        }
//    }
//
//    /* renamed from: 斃燸卺驼暲各撟嫺眧樬硱 */
//    public static /* synthetic */ void m23576(SafeScreenLockView safeScreenLockView, String str) {
//        safeScreenLockView.m23581(str);
//    }
//
//    /* renamed from: 枩棥钰蕎睨領喀镎遣跄 */
//    public /* synthetic */ void m23578(String str, String str2, String str3) {
//        if (m15500() != null) {
//            TextView textView = (TextView) m25775(R$id.tvModeName);
//            TextView textView2 = (TextView) m25775(R$id.tvModeDisplay);
//            TextView textView3 = (TextView) m25775(R$id.tvCode);
//            textView2.setVisibility(0);
//            textView3.setVisibility(0);
//            textView.setText(str);
//            textView2.setText(str2);
//            textView3.setText(str3);
//        }
//    }
//
//    /* renamed from: 礱咄頑 */
    public static SafeScreenLockView m23580() {
        if (f13250 == null) {
            f13250 = new SafeScreenLockView();
        }
        return f13250;
    }

//
//    /* renamed from: 綏牽躵糽稰烳俠垳襨捈桏鷋 */
//    public /* synthetic */ void m23581(String str) {
//        if (m15500() != null) {
//            TextView textView = (TextView) m25775(R$id.tvModeName);
//            TextView textView2 = (TextView) m25775(R$id.tvModeDisplay);
//            TextView textView3 = (TextView) m25775(R$id.tvCode);
//            textView.setText(str);
//            textView2.setVisibility(8);
//            textView3.setVisibility(8);
//        }
//    }
//
//    /* renamed from: 鞲冇 */
//    public /* synthetic */ void m14671(String str, String str2) {
//        if (m15500() != null) {
//            TextView textView = (TextView) m25775(R$id.tvModeName);
//            TextView textView2 = (TextView) m25775(R$id.tvModeDisplay);
//            TextView textView3 = (TextView) m25775(R$id.tvCode);
//            textView2.setVisibility(0);
//            textView.setText(str);
//            textView2.setText(str2);
//            textView3.setVisibility(8);
//        }
//    }
//
//    @Override // p464.BaseWindowView
//    /* renamed from: 卝閄侸靤溆鲁扅 */
    public void mo23566() {
        synchronized (this) {
            if (m25772()) {
                if (this.f21607 == 3) {
                    m23587();
                }
            } else {
//                super.mo23566();
            }
        }
    }
//
//    /* renamed from: 哠畳鲜郣新剙鳰活茙郺嵝 */
//    public void m23586(String str) {
//        m25774(new Runnable
//        /*  JADX ERROR: Method code generation error
//            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0005: INVOKE
//              (r1v0 'this' ﾘﾢ￠ￔ￮Q.ﾲNￜﾡﾸﾥￖ￠ﾹ￵ﾻￌ A[IMMUTABLE_TYPE, THIS])
//              (wrap:ﾘﾢ￠ￔ￮Q.ﾔ￸ￇiﾰV:0x0002: CONSTRUCTOR (r1v0 'this' ﾘﾢ￠ￔ￮Q.ﾲNￜﾡﾸﾥￖ￠ﾹ￵ﾻￌ A[IMMUTABLE_TYPE, THIS]), (r2v0 'str' java.lang.String) A[MD:(ﾘﾢ￠ￔ￮Q.ﾲNￜﾡﾸﾥￖ￠ﾹ￵ﾻￌ, java.lang.String):void (m), WRAPPED] (LINE:1) call: ﾘﾢ￠ￔ￮Q.ﾔ￸ￇiﾰV.<init>(ﾘﾢ￠ￔ￮Q.ﾲNￜﾡﾸﾥￖ￠ﾹ￵ﾻￌ, java.lang.String):void type: CONSTRUCTOR)
//             VIRTUAL call: ￏﾠ￡ﾡ￢vﾅￍￊￒﾍﾪﾐￇﾻ￾ﾠﾫ.￬ﾑ￹Wￖ￷ﾞﾂﾺﾢﾝ￼ﾯi￩iￚﾥￚ￯ﾙmﾔﾝ.ﾲh￯ﾜ￝ﾫﾈ￈(java.lang.Runnable):void A[MD:(java.lang.Runnable):void (m)] (LINE:1) in method: ﾘﾢ￠ￔ￮Q.ﾲNￜﾡﾸﾥￖ￠ﾹ￵ﾻￌ.ﾆSﾮﾒￏￊ￠R￐ￂﾄﾀ￸Mﾻ￮ￆﾎ￠a￡￐(java.lang.String):void, file: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex
//            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
//            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
//            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
//            	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
//            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
//            	at jadx.core.dex.regions.Region.generate(Region.java:35)
//            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
//            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:297)
//            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:276)
//            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:406)
//            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
//            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
//            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
//            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
//            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
//            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
//            Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Method arg registers not loaded: ﾘﾢ￠ￔ￮Q.ﾔ￸ￇiﾰV.<init>(ﾘﾢ￠ￔ￮Q.ﾲNￜﾡﾸﾥￖ￠ﾹ￵ﾻￌ, java.lang.String):void, class status: PROCESS_COMPLETE
//            	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:289)
//            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:803)
//            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
//            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
//            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
//            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
//            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
//            	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1117)
//            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:884)
//            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:422)
//            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
//            	... 15 more
//            */
//        /*
//            this = this;
//            槩嘣頠.旞莍癡 r0 = new 槩嘣頠.旞莍癡
//            r0.<init>(r1, r2)
//            r1.m25774(r0)
//            return
//        */
//        throw new UnsupportedOperationException("Method not decompiled: p322.SafeScreenLockView.m23586(java.lang.String):void");
//    }
//
//    /* renamed from: 媛婱骼蒋袐弲卙 */
    public void m23587() {
        this.f13251 = true;
//        m25774(new Runnable() { // from class: 槩嘣頠.垡玖
//            public /* synthetic */ RunnableC3760() {
//            }
//
//            @Override // java.lang.Runnable
//            public final void run() {
//                SafeScreenLockView.this.m23575();
//            }
//        });
    }


//
//    @Override // p464.BaseWindowView
//    /* renamed from: 彻薯铏螙憣欖愡鼭 */
//    public void mo23567() {
//        m23587();
//    }
//
//    @Override // p464.BaseWindowView
//    /* renamed from: 灞酞輀攼嵞漁綬迹 */
//    public void mo23568() {
//        SafeGuardStateBinding.m19856(m15500());
//        m25775(R$id.btn_exit).setOnClickListener(new ViewOnClickListenerC3762());
//    }
//
//    @Override // p464.BaseWindowView
//    /* renamed from: 睳堋弗粥辊惶 */
//    public int mo23569() {
//        return R$layout.safe_guard_state;
//    }
//
//    /* renamed from: 蘫聫穯搞哪曁雥贀忬琖嶹 */
    public void m23588(final String str, final String str2, final String str3) {
//        m25774(new Runnable() { // from class: 槩嘣頠.镐藻
//            @Override // java.lang.Runnable
//            public final void run() {
//                SafeScreenLockView.this.m23578(str, str2, str3);
//            }
//        });
    }
//
//    /* renamed from: 躑漕 */
//    public void m14672(final String str, final String str2) {
//        m25774(new Runnable() { // from class: 槩嘣頠.祴嚚橺谋肬鬧舘
//            @Override // java.lang.Runnable
//            public final void run() {
//                SafeScreenLockView.this.m14671(str, str2);
//            }
//        });
//    }
}
