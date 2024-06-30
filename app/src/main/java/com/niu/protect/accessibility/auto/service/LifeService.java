package com.niu.protect.accessibility.auto.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.niu.protect.accessibility.auto.service.tmp.ConfigKey;

/* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
public class LifeService extends Service implements C6492.InterfaceC6494 {

    /* renamed from: 刻槒唱镧詴 */
    public static LifeService f19296;

    /* renamed from: 肌緭 */
    public C6492 f11938;

    /* renamed from: 肌緭 */
    public static boolean m13533() {
        return f19296 != null;
    }

    @Override // android.app.Service
    @Nullable
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        C7094.m16002(this);
        if (!ConfigKey.m27256()) {
            C7094.m27171(this);
            return;
        }
        f19296 = this;
        C6492 m24467 = C6492.m24467();
        this.f11938 = m24467;
        m24467.m24468(this);
        LogHelper.m27985("LifeService onCreate");
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        f19296 = null;
        C6492 c6492 = this.f11938;
        if (c6492 != null) {
            c6492.m24469(this);
        }
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        C7094.m16002(this);
        if (intent != null && ObjectUtils.equals(intent.getStringExtra("from"), NotificationCompat.CATEGORY_ALARM)) {
            if (Build.VERSION.SDK_INT >= 23) {
                LiveManager.m27172().m27173();
            }
            LogHelper.m27985("LifeService onStartCommand---alarm star");
        }
        LogHelper.m27985("LifeService onStartCommand");
        return 1;
    }

    @Override // p384.C6492.InterfaceC6494
    /* renamed from: 偣炱嘵蟴峗舟轛 */
    public /* synthetic */ void mo19899() {
        C6495.m15026(this);
    }

    @Override // p384.C6492.InterfaceC6494
    /* renamed from: 睳堋弗粥辊惶 */
    public void mo19903() {
        try {
//            Intent intent = new Intent(this, (Class<?>) DeameActivity.class);
//            intent.setFlags(C2860y.f10012a);
//            startActivity(intent);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    @Override // p384.C6492.InterfaceC6494
    /* renamed from: 纩慐 */
    public void mo13500() {
    }

    @Override // p384.C6492.InterfaceC6494
    /* renamed from: 镐藻 */
    public void mo13501() {
    }

    @Override // p384.C6492.InterfaceC6494
    /* renamed from: 駭鑈趘薑衈講堍趃軏 */
    public /* synthetic */ void mo19904() {
        C6495.m24471(this);
    }
}
