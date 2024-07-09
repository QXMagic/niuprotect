package accessibility;

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
public class AppFilter extends BaseFilter {

    /* renamed from: 斃燸卺驼暲各撟嫺眧樬硱 */
    public static volatile AppFilter _instance;

    /* renamed from: 卝閄侸靤溆鲁扅 */
//    public WebSiteListSet f23350;

    public final ThreadSafeHashSet<String> f23351;

    /* renamed from: 朽劔蚁灋嵿齩鶴琓麃沼瀙缹 */
    public final AppArraySet group_forbidden;

    /* renamed from: 櫓昛刓叡賜 */
    public final AppArraySet forbidden;

    /* renamed from: 瞙餃莴埲 */
    public final AppArraySet waitforAllow;

    /* renamed from: 綩私 */
    public final AppArraySet forbidden_mult_time;

    /* renamed from: 耣怳匮色紝参凵蛴纆勚躄 */
    public final ArrayList<AppArraySet> allList;

    /* renamed from: 蝸餺閃喍 */
    public final AppArraySet forbidden_time;

    /* renamed from: 鑭撇糁綖浓緗轟鱼萟磿焈 */
    public final ThreadSafeHashSet<String> systemActivityList;

    /* renamed from: 陟瓠魒踱褢植螉嚜 */
    public final ThreadSafeHashSet<String> f23358;

    public final AppArraySet f23367 = new AppArraySet(7);


    public AppFilter(Context context) {
        super(context);
        AppArraySet appArraySet = new AppArraySet(AppArraySet.Forbidden);
        this.forbidden = appArraySet;
        AppArraySet appArraySet2 = new AppArraySet(AppArraySet.Forbidden_MULT_TIME);
        this.forbidden_mult_time = appArraySet2;
        AppArraySet appArraySet3 = new AppArraySet(AppArraySet.GROUP_Forbidden);
        this.group_forbidden = appArraySet3;
        AppArraySet appArraySet4 = new AppArraySet(AppArraySet.WaitForAllow);
        this.waitforAllow = appArraySet4;
        AppArraySet appArraySet5 = new AppArraySet(AppArraySet.Forbidden_Time);
        this.forbidden_time = appArraySet5;
        ArrayList<AppArraySet> arrayList = new ArrayList<>();
        this.allList = arrayList;
        arrayList.add(appArraySet);
        arrayList.add(appArraySet2);
        arrayList.add(appArraySet3);
        arrayList.add(appArraySet4);
        arrayList.add(appArraySet5);
        arrayList.add(this.f23367);
        this.f23358 = new ThreadSafeHashSet<>();
        ThreadSafeHashSet<String> threadSafeHashSet = new ThreadSafeHashSet<>();
        this.systemActivityList = threadSafeHashSet;
        this.f23351 = new ThreadSafeHashSet<>();
        ActivityInfo callAc = getCallActivity();
        if (callAc != null && ObjectUtils.isNotEmpty(callAc.packageName)) {
            threadSafeHashSet.add(callAc.packageName);
        }
        ActivityInfo m25923 = getMessageActivity();
        if (m25923 != null) {
            threadSafeHashSet.add(m25923.packageName);
        }
        threadSafeHashSet.add(AppUtils.getAppPackageName());
    }

    /* renamed from: 郗鮺苦鍫垫魍屪 */
    public static AppFilter instance() {
        return instance(App.mContext);
    }

    /* renamed from: 鞊臎 */
    public static AppFilter instance(Context context) {
        if (_instance == null) {
            synchronized (AppFilter.class) {
                if (_instance == null) {
                    _instance = new AppFilter(context);
                }
            }
        }
        return _instance;
    }

    /* renamed from: 义饿达 */
    public boolean m25868(String str) {
        return this.forbidden_mult_time.contains(str);
    }

    /* renamed from: 利晉颚莙孕庮磬 */
    public void m25869() {
        this.forbidden_time.clear();
    }

    /* renamed from: 厖毿褸涙艔淶嬉殟恇凛场 */
    public void m25870(String str) {
        if (ObjectUtils.isEmpty((CharSequence) str)) {
            return;
        }
        this.f23351.add(str);
    }

    /* renamed from: 厧卥孩 */
    public void m25871(String str) {
        if (ObjectUtils.isNotEmpty((CharSequence) str)) {
            this.systemActivityList.add(str);
        }
    }

    /* renamed from: 媛婱骼蒋袐弲卙 */
    public final void m25872(String str) {
        this.forbidden_time.addPackageName(str);
    }

    /* renamed from: 媥嗅趎 */
    public boolean m25873(String str) {
        return this.f23358.contains(str);
    }

    /* renamed from: 崜鲜瀐線钾 */
    public boolean m25874(String str) {
        return this.waitforAllow.contains(str);
    }

    /* renamed from: 嵷徝糁伋痏邜浫袊譃一迴袣 */
    public void m25875() {
        this.f23351.m23899();
//        ChildDBManager.m24197();
    }

    /* renamed from: 忦喐弒驤 */
    public Set<String> m25876() {
        return this.f23358.toSet();
    }

    /* renamed from: 愹蔧皆嘸嘏蓽梌菉 */
    public final void m25877(String str) {
        this.forbidden_mult_time.addPackageName(str);
    }

    public void m25878(String str, int i) {
        Iterator<AppArraySet> it = this.allList.iterator();
        while (it.hasNext()) {
            AppArraySet next = it.next();
            if (next.getType() == i) {
                next.addPackageName(str);
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
        m25884(this.context.getPackageName());
        ActivityInfo m25912 = getCallActivity();
        if (m25912 != null) {
            m25884(m25912.packageName);
        } else {
            m25884("com.android.contacts");
        }
    }

    /* renamed from: 控鼱雹怮悿錿攳淎魂鸔蠯 */
    public AppArraySet m25881() {
        return this.forbidden_time;
    }

    /* renamed from: 掳迠界 */
    public boolean m25882(String str) {
        return this.group_forbidden.contains(str);
    }

    @Override // p469.BaseFilter
    /* renamed from: 旞莍癡 */
    public final void mo25883(@NonNull String str) {
//        if (this.f23367.m25902(str) || this.f23353.m25902(str)) {
//            return;
//        }
        this.f23365.add(str);
    }

    /* renamed from: 桿婤鷋鷯餒勡鈙洷薃蚺麮 */
    public void m25884(String str) {
        this.f23358.add(str);
    }

    /* renamed from: 洣媯幵絮蠽 */
    public void m25885() {
        this.forbidden_mult_time.clear();
    }

    /* renamed from: 狢橞再欠 */
    public boolean m25886(String str) {
        return m25928(this.f23351.toSet(), str);
    }

    /* renamed from: 琞驜杫怬 */
    public boolean m25887(String str) {
        return this.forbidden.contains(str);
    }

    /* renamed from: 畋熷藛笠駙坈莵蓕瘦 */
    public void m25888() {
        this.group_forbidden.clear();
    }

    /* renamed from: 祬贠潪蓺眣蠈銊凚滘 */
    public ArrayList<AppArraySet> m25889() {
        return this.allList;
    }

    /* renamed from: 繚潯鍢骬蓀乖顑潽 */
    public boolean m25890(String str) {
        return this.forbidden_time.contains(str);
    }

    /* renamed from: 翡埿丘蟻鴔倞贮峾瞋弅 */
    public AppArraySet m25891() {
        return this.group_forbidden;
    }

    /* renamed from: 翺軳鎱蔸濎鹄 */
    public Set<String> m25892() {
        return this.systemActivityList.toSet();
    }

    /* renamed from: 蒎鮋闯剁簫制睆芸槣餀鲚偔 */
    public void m25893() {
        Iterator<AppArraySet> it = this.allList.iterator();
        while (it.hasNext()) {
            AppArraySet next = it.next();
            if (next.getType() != 7) {
                next.clear();
            }
        }
        m25875();
    }

    /* renamed from: 蘫聫穯搞哪曁雥贀忬琖嶹 */
    public final void m25894(String str) {
        this.group_forbidden.addPackageName(str);
    }

    /* renamed from: 賱坔栩颢筶 */
    public final boolean m25895(String str) {
        if (str == null) {
            return false;
        }
        return m25928(this.systemActivityList.toSet(), str) || m25909(str);
    }

    /* renamed from: 躑漕 */
    public final void m15535(String str) {
        this.waitforAllow.addPackageName(str);
    }

    /* renamed from: 驉鑣偏 */
    public void m25896(String str) {
        Iterator<AppArraySet> it = this.allList.iterator();
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
        return this.forbidden_mult_time;
    }

    //m25918
    public boolean m25918(String m24458) {
        return false;
    }
}
