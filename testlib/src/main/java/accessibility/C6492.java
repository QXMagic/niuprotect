package accessibility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.blankj.utilcode.util.ObjectUtils;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class C6492 {

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
    public static C6492 f22230;

    /* renamed from: 肌緭 */
    public Context f13581;

    /* renamed from: 刻槒唱镧詴 */
    public C6493 f22231 = new C6493();

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
    public CopyOnWriteArrayList<InterfaceC6494> f22232 = new CopyOnWriteArrayList<>();

    /* compiled from: PhoneStatusListener.java */
    /* renamed from: 硿珃釽踐忋悕緇鮚槰.葋申湋骶映鍮秄憁鎓羭$葋申湋骶映鍮秄憁鎓羭 */
    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
    public interface InterfaceC6494 {
        /* renamed from: 偣炱嘵蟴峗舟轛 */
        void mo19899();

        /* renamed from: 睳堋弗粥辊惶 */
        void mo19903();

        /* renamed from: 纩慐 */
        void mo13500();

        /* renamed from: 镐藻 */
        void mo13501();

        /* renamed from: 駭鑈趘薑衈講堍趃軏 */
        void mo19904();
    }

    public C6492(Context context) {
        this.f13581 = context;
        m24470();
    }

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
    public static C6492 m24467() {
        if (f22230 == null) {
            f22230 = new C6492(App.mContext);
        }
        return f22230;
    }

    /* renamed from: 刻槒唱镧詴 */
    public void add(InterfaceC6494 interfaceC6494) {
        this.f22232.add(interfaceC6494);
    }

    /* renamed from: 垡玖 */
    public void m15025() {
        try {
            this.f13581.unregisterReceiver(this.f22231);
            this.f22232.clear();
            f22230 = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: 灞酞輀攼嵞漁綬迹 */
    public void remove(InterfaceC6494 interfaceC6494) {
        if (interfaceC6494 != null) {
            this.f22232.remove(interfaceC6494);
        }
    }

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
    public final void m24470() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        this.f13581.registerReceiver(this.f22231, intentFilter);
    }

    /* compiled from: PhoneStatusListener.java */
    /* renamed from: 硿珃釽踐忋悕緇鮚槰.葋申湋骶映鍮秄憁鎓羭$刻槒唱镧詴 */
    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
    public class C6493 extends BroadcastReceiver {

        /* renamed from: 肌緭 */
        public String f13582;

        public C6493() {
            this.f13582 = null;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            this.f13582 = action;
            if (ObjectUtils.isEmpty((CharSequence) action)) {
                return;
            }
            if (ObjectUtils.equals(this.f13582, "android.intent.action.SCREEN_ON")) {
                Iterator it = C6492.this.f22232.iterator();
                while (it.hasNext()) {
                    ((InterfaceC6494) it.next()).mo13501();
                }
                return;
            }
            if (ObjectUtils.equals(this.f13582, "android.intent.action.SCREEN_OFF")) {
                Iterator it2 = C6492.this.f22232.iterator();
                while (it2.hasNext()) {
                    ((InterfaceC6494) it2.next()).mo19903();
                }
                return;
            }
            if (ObjectUtils.equals(this.f13582, "android.intent.action.USER_PRESENT")) {
                Iterator it3 = C6492.this.f22232.iterator();
                while (it3.hasNext()) {
                    ((InterfaceC6494) it3.next()).mo13500();
                }
            } else if (ObjectUtils.equals(this.f13582, "android.net.conn.CONNECTIVITY_CHANGE")) {
                if (NetWorkUtils.m14317()) {
                    LogHelper.instance().m16303("手机状态", "网络已连接");
                    Iterator it4 = C6492.this.f22232.iterator();
                    while (it4.hasNext()) {
                        ((InterfaceC6494) it4.next()).mo19899();
                    }
                    return;
                }
                LogHelper.instance().m16303("手机状态", "网络未连接");
                Iterator it5 = C6492.this.f22232.iterator();
                while (it5.hasNext()) {
                    ((InterfaceC6494) it5.next()).mo19904();
                }
            }
        }
    }
}
