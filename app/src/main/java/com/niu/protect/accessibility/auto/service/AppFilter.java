package com.niu.protect.accessibility.auto.service;

import android.content.Context;
import android.content.pm.ActivityInfo;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/* compiled from: AppFilter.java */
/* renamed from: 褣餐驫喸嶷.刻槒唱镧詴 */
/* loaded from: C:\Users\PC\Downloads\bfa4c7ece67b6eaa51d4653201f6959f.zip\classes5.dex */
public class AppFilter extends BaseFilter {

    /* renamed from: 斃燸卺驼暲各撟嫺眧樬硱 */
    public static volatile AppFilter f23349;

    /* renamed from: 卝閄侸靤溆鲁扅 */
//    public WebSiteListSet f23350;

    /* renamed from: 彻薯铏螙憣欖愡鼭 */
    public final ThreadSafeHashSet<String> f23351;

    /* renamed from: 朽劔蚁灋嵿齩鶴琓麃沼瀙缹 */
    public final AppArraySet f23352;

    /* renamed from: 櫓昛刓叡賜 */
    public final AppArraySet f23353;

    /* renamed from: 瞙餃莴埲 */
    public final AppArraySet f23354;

    /* renamed from: 綩私 */
    public final AppArraySet f14233;

    /* renamed from: 耣怳匮色紝参凵蛴纆勚躄 */
    public final ArrayList<AppArraySet> f23355;

    /* renamed from: 蝸餺閃喍 */
    public final AppArraySet f23356;

    /* renamed from: 鑭撇糁綖浓緗轟鱼萟磿焈 */
    public final ThreadSafeHashSet<String> f23357;

    /* renamed from: 陟瓠魒踱褢植螉嚜 */
    public final ThreadSafeHashSet<String> f23358;

    public AppFilter(Context context) {
        super(context);
        AppArraySet appArraySet = new AppArraySet(0);
        this.f23353 = appArraySet;
        AppArraySet appArraySet2 = new AppArraySet(6);
        this.f14233 = appArraySet2;
        AppArraySet appArraySet3 = new AppArraySet(4);
        this.f23352 = appArraySet3;
        AppArraySet appArraySet4 = new AppArraySet(3);
        this.f23354 = appArraySet4;
        AppArraySet appArraySet5 = new AppArraySet(2);
        this.f23356 = appArraySet5;
        ArrayList<AppArraySet> arrayList = new ArrayList<>();
        this.f23355 = arrayList;
        arrayList.add(appArraySet);
        arrayList.add(appArraySet2);
        arrayList.add(appArraySet3);
        arrayList.add(appArraySet4);
        arrayList.add(appArraySet5);
//        arrayList.add(this.f23367);
        this.f23358 = new ThreadSafeHashSet<>();
        ThreadSafeHashSet<String> threadSafeHashSet = new ThreadSafeHashSet<>();
        this.f23357 = threadSafeHashSet;
        this.f23351 = new ThreadSafeHashSet<>();
        ActivityInfo m25912 = m25912();
        if (m25912 != null && com.blankj.utilcode.util.ObjectUtils.isNotEmpty((CharSequence) m25912.packageName)) {
            threadSafeHashSet.m14793(m25912.packageName);
        }
        ActivityInfo m25923 = m25923();
        if (m25923 != null) {
            threadSafeHashSet.m14793(m25923.packageName);
        }
        threadSafeHashSet.m14793(AppUtils.getAppPackageName());
    }

    /* renamed from: 郗鮺苦鍫垫魍屪 */
    public static AppFilter m25867() {
        return m15534(App.mContext);
    }

    /* renamed from: 鞊臎 */
    public static AppFilter m15534(Context context) {
        if (f23349 == null) {
            synchronized (AppFilter.class) {
                if (f23349 == null) {
                    f23349 = new AppFilter(context);
                }
            }
        }
        return f23349;
    }

    /* renamed from: 义饿达 */
    public boolean m25868(String str) {
        return this.f14233.m25902(str);
    }

    /* renamed from: 利晉颚莙孕庮磬 */
    public void m25869() {
        this.f23356.clear();
    }

    /* renamed from: 厖毿褸涙艔淶嬉殟恇凛场 */
    public void m25870(String str) {
        if (com.blankj.utilcode.util.ObjectUtils.isEmpty((CharSequence) str)) {
            return;
        }
        this.f23351.m14793(str);
    }

    /* renamed from: 厧卥孩 */
    public void m25871(String str) {
        if (ObjectUtils.isNotEmpty((CharSequence) str)) {
            this.f23357.m14793(str);
        }
    }

    /* renamed from: 媛婱骼蒋袐弲卙 */
    public final void m25872(String str) {
        this.f23356.mo23319(str);
    }

    /* renamed from: 媥嗅趎 */
    public boolean m25873(String str) {
        return this.f23358.m23901(str);
    }

    /* renamed from: 崜鲜瀐線钾 */
    public boolean m25874(String str) {
        return this.f23354.m25902(str);
    }

    /* renamed from: 嵷徝糁伋痏邜浫袊譃一迴袣 */
    public void m25875() {
        this.f23351.m23899();
//        ChildDBManager.m24197();
    }

    /* renamed from: 忦喐弒驤 */
    public Set<String> m25876() {
        return this.f23358.m23902();
    }

    /* renamed from: 愹蔧皆嘸嘏蓽梌菉 */
    public final void m25877(String str) {
        this.f14233.mo23319(str);
    }

    /* renamed from: 扛癒供鴼稠窤鋧嘆 */
    public void m25878(String str, int i) {
        Iterator<AppArraySet> it = this.f23355.iterator();
        while (it.hasNext()) {
            AppArraySet next = it.next();
            if (next.mo14579() == i) {
                next.mo23319(str);
            } else {
                next.remove(str);
            }
        }
    }

    /* renamed from: 拁錉鼉緫科銓諒濌矤鹂 */
    public void m25879() {
        this.f23358.m23899();
    }

    /* renamed from: 掣末騾嚺跬骧輣狾懮 */
    public void m25880() {
        m25884(this.f14238.getPackageName());
        ActivityInfo m25912 = m25912();
        if (m25912 != null) {
            m25884(m25912.packageName);
        } else {
            m25884("com.android.contacts");
        }
    }

    /* renamed from: 控鼱雹怮悿錿攳淎魂鸔蠯 */
    public AppArraySet m25881() {
        return this.f23356;
    }

    /* renamed from: 掳迠界 */
    public boolean m25882(String str) {
        return this.f23352.m25902(str);
    }

    @Override // p469.BaseFilter
    /* renamed from: 旞莍癡 */
    public final void mo25883(@NonNull String str) {
//        if (this.f23367.m25902(str) || this.f23353.m25902(str)) {
//            return;
//        }
        this.f23365.m14793(str);
    }

    /* renamed from: 桿婤鷋鷯餒勡鈙洷薃蚺麮 */
    public void m25884(String str) {
        this.f23358.m14793(str);
    }

    /* renamed from: 洣媯幵絮蠽 */
    public void m25885() {
        this.f14233.clear();
    }

    /* renamed from: 狢橞再欠 */
    public boolean m25886(String str) {
        return m25928(this.f23351.m23902(), str);
    }

    /* renamed from: 琞驜杫怬 */
    public boolean m25887(String str) {
        return this.f23353.m25902(str);
    }

    /* renamed from: 畋熷藛笠駙坈莵蓕瘦 */
    public void m25888() {
        this.f23352.clear();
    }

    /* renamed from: 祬贠潪蓺眣蠈銊凚滘 */
    public ArrayList<AppArraySet> m25889() {
        return this.f23355;
    }

    /* renamed from: 繚潯鍢骬蓀乖顑潽 */
    public boolean m25890(String str) {
        return this.f23356.m25902(str);
    }

    /* renamed from: 翡埿丘蟻鴔倞贮峾瞋弅 */
    public AppArraySet m25891() {
        return this.f23352;
    }

    /* renamed from: 翺軳鎱蔸濎鹄 */
    public Set<String> m25892() {
        return this.f23357.m23902();
    }

    /* renamed from: 蒎鮋闯剁簫制睆芸槣餀鲚偔 */
    public void m25893() {
        Iterator<AppArraySet> it = this.f23355.iterator();
        while (it.hasNext()) {
            AppArraySet next = it.next();
            if (next.mo14579() != 7) {
                next.clear();
            }
        }
        m25875();
    }

    /* renamed from: 蘫聫穯搞哪曁雥贀忬琖嶹 */
    public final void m25894(String str) {
        this.f23352.mo23319(str);
    }

    /* renamed from: 賱坔栩颢筶 */
    public final boolean m25895(String str) {
        if (str == null) {
            return false;
        }
        return m25928(this.f23357.m23902(), str) || m25909(str);
    }

    /* renamed from: 躑漕 */
    public final void m15535(String str) {
        this.f23354.mo23319(str);
    }

    /* renamed from: 驉鑣偏 */
    public void m25896(String str) {
        Iterator<AppArraySet> it = this.f23355.iterator();
        while (it.hasNext()) {
            it.next().remove(str);
        }
    }

    /* renamed from: 鯙餟偆安槟跘碠樅 */
//    public WebSiteListSet m25897() {
//        if (this.f23350 == null) {
//            this.f23350 = new WebSiteSetImp();
//        }
//        return this.f23350;
//    }

    /* renamed from: 鵖寴诮粣蘤鞎 */
    public AppArraySet m25898() {
        return this.f14233;
    }

    public boolean m25918(String m24458) {
        return false;
    }
}
