package com.niu.protect.accessibility.auto.service;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

/* compiled from: LiveManager.java */
@RequiresApi(api = 23)
/* renamed from: 靇榝杍匭髶勈愖楈效.肌緭 */
/* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
public class LiveManager {

    /* renamed from: 刻槒唱镧詴 */
    public final PendingIntent f24046;

    /* renamed from: 肌緭 */
    public final AlarmManager f14694;

    /* compiled from: LiveManager.java */
    /* renamed from: 靇榝杍匭髶勈愖楈效.肌緭$刻槒唱镧詴 */
    /* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
    public static final class C7095 {

        /* renamed from: 肌緭 */
        public static final LiveManager f14695 = new LiveManager();
    }


    /* renamed from: 刻槒唱镧詴 */
    public static LiveManager m27172() {
        return C7095.f14695;
    }

    /* renamed from: 肌緭 */
    public void m16003() {
        try {
            this.f14694.cancel(this.f24046);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint({"NewApi"})
    /* renamed from: 葋申湋骶映鍮秄憁鎓羭 */
    public void m27173() {
        LogHelper.m27985("startWorkDelay");
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                this.f14694.setExactAndAllowWhileIdle(AlarmManager.RTC, System.currentTimeMillis() + 600000, this.f24046);
            } else {
                this.f14694.setExact(AlarmManager.RTC, System.currentTimeMillis() + 600000, this.f24046);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐 */
    public void m27174() {
        try {
            LogHelper.m27985("startWorkNow");
            if (Build.VERSION.SDK_INT >= 23) {
                this.f14694.setExactAndAllowWhileIdle(AlarmManager.RTC, System.currentTimeMillis(), this.f24046);
            } else {
                this.f14694.setExact(AlarmManager.RTC, System.currentTimeMillis(), this.f24046);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LiveManager() {
        this.f14694 = (AlarmManager) App.mContext.getSystemService(AlarmManager.class);
        Intent intent = new Intent(App.mContext, (Class<?>) LifeService.class);
        intent.putExtra("from", NotificationCompat.CATEGORY_ALARM);
        this.f24046 = PendingIntent.getService(App.mContext, 0, intent, Build.VERSION.SDK_INT >= 30 ? PendingIntent.FLAG_UPDATE_CURRENT : 0);
    }
}
