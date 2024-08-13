package accessibility.lib;

import android.content.ContentValues;
import android.os.Message;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ObjectUtils;

import org.litepal.LitePal;

import java.util.Collection;
import java.util.List;

import accessibility.LogHelper;
import accessibility.Mgr;

public class ChildDBManager {
    public static List<AppSuperMgrBean> m24183() {
        return LitePal.findAll(AppSuperMgrBean.class, new long[0]);
    }

    public static List<AppSuperMgrBean> m24187(int i) {
        return LitePal.where("type= ?", String.valueOf(i)).find(AppSuperMgrBean.class);
    }

    public static void m14914(String str, int i) {
        if (str == null) {
            return;
        }
        AppSuperMgrBean appSuperMgrBean = (AppSuperMgrBean) LitePal.where("packageName = ?", str).findFirst(AppSuperMgrBean.class);
        if (appSuperMgrBean == null) {
            AppSuperMgrBean appSuperMgrBean2 = new AppSuperMgrBean();
            appSuperMgrBean2.setPackageName(str);
            appSuperMgrBean2.setType(i);
            appSuperMgrBean2.save();
            return;
        }
        if (appSuperMgrBean.getType() != i) {
            appSuperMgrBean.setType(i);
            appSuperMgrBean.update();
        }
    }

    public static List<VoiceDbBean> m24175(int i) {
        return LitePal.where("type = ?", String.valueOf(i)).find(VoiceDbBean.class);
    }

    /* renamed from: 唌橅咟, reason: contains not printable characters */
    public static void m24176(String str, int i) {
        VoiceDbBean voiceDbBean = (VoiceDbBean) LitePal.where("packageName = ?", str).findFirst(VoiceDbBean.class);
        if (voiceDbBean != null) {
            voiceDbBean.setType(i);
            voiceDbBean.update();
        }
    }

    /* renamed from: 垡玖 */
    public static void m14911(String str) {
        LitePal.deleteAll((Class<?>) AppSuperMgrBean.class, "packageName = ?", str);
    }

    /* renamed from: 壋劘跆貭澴綄秽攝煾訲, reason: contains not printable characters */
    public static void m24177(String str) {
//        if (str == null) {
//            return;
//        }
//        if (System.currentTimeMillis() - f13467 >= 5000 || !ObjectUtils.equals(str, f21950)) {
//            WebSiteBean webSiteBean = new WebSiteBean();
//            webSiteBean.setHostName(str);
//            webSiteBean.setAccessingDate(DateUtil.m14311(new Date(), "yyyy-MM-dd HH:mm:ss"));
//            webSiteBean.save();
//            f21950 = str;
//            f13467 = System.currentTimeMillis();
//        }
    }

    /* renamed from: 彻薯铏螙憣欖愡鼭, reason: contains not printable characters */
    public static List<TimeDbBean> m24178(long j) {
        return LitePal.where("appState_id = ?", String.valueOf(j)).find(TimeDbBean.class);
    }

    /* renamed from: 斃燸卺驼暲各撟嫺眧樬硱, reason: contains not printable characters */
    public static boolean m24179(String str) {
        if (ObjectUtils.isEmpty((CharSequence) str)) {
            return false;
        }
        return LitePal.isExist(VoiceDbBean.class, "packageName = ?", str);
    }

    /* renamed from: 旞莍癡, reason: contains not printable characters */
    public static void m24180(int i) {
        LitePal.deleteAll((Class<?>) AppSuperMgrBean.class, "type= ?", String.valueOf(i));
    }

    @Nullable
    /* renamed from: 朽劔蚁灋嵿齩鶴琓麃沼瀙缹, reason: contains not printable characters */
    public static AppSuperMgrBean m24181(String str) {
        return (AppSuperMgrBean) LitePal.where("packageName = ?", str).findFirst(AppSuperMgrBean.class);
    }

    /* renamed from: 杹藗瀶姙笻件稚嵅蔂, reason: contains not printable characters */
    public static void m24182(AppState appState) {
        long baseObjId;
        List<TimeSelectBean> time = appState.getTime();
        if (ObjectUtils.isNotEmpty((Collection) time)) {
            AppState appState2 = (AppState) LitePal.where("packageName = ?", appState.getPackageName()).findFirst(AppState.class);
            if (appState2 != null) {
                appState2.setStatus(appState.getStatus());
                appState2.setPackageName(appState.getPackageName());
                appState2.setOpenTime(appState.getOpenTime());
                appState2.update(appState2.getBaseObjId());
                baseObjId = appState2.getBaseObjId();
            } else {
                appState.setOpenTime(appState.getOpenTime());
                appState.save();
                baseObjId = appState.getBaseObjId();
            }
            LitePal.deleteAll((Class<?>) TimeDbBean.class, "appState_id = ?", String.valueOf(baseObjId));
            for (TimeSelectBean timeSelectBean : time) {
                TimeDbBean timeDbBean = new TimeDbBean();
                timeDbBean.setAppState_id(baseObjId);
                String startTime = timeSelectBean.getStartTime();
                String endTime = timeSelectBean.getEndTime();
                if (ObjectUtils.isNotEmpty((CharSequence) startTime) && ObjectUtils.isNotEmpty((CharSequence) endTime)) {
                    timeDbBean.setStartTime(timeSelectBean.getStartTime());
                    timeDbBean.setEndTime(timeSelectBean.getEndTime());
                    timeDbBean.save();
                }
            }
            return;
        }
        LogHelper.instance().m16303("手机状态", "多时段设置保存失败，无多分段数据");
    }



    /* renamed from: 癎躑選熁, reason: contains not printable characters */
    public static void m24185(String str) {
        VoiceDbBean voiceDbBean = (VoiceDbBean) LitePal.where("packageName = ?", str).findFirst(VoiceDbBean.class);
        if (voiceDbBean == null) {
            VoiceDbBean voiceDbBean2 = new VoiceDbBean();
            voiceDbBean2.setType(0);
            voiceDbBean2.setPackageName(str);
            voiceDbBean2.save();
            return;
        }
        voiceDbBean.setType(0);
        voiceDbBean.update();
    }

    /* renamed from: 睳堋弗粥辊惶, reason: contains not printable characters */
    public static void m24186(String str) {
//        LitePal.deleteAll((Class<?>) LockInfoBean.class, "lock_id = ?", str);
    }



    /* renamed from: 祴嚚橺谋肬鬧舘, reason: contains not printable characters */
    public static void m24188(String str) {
//        LitePal.deleteAll((Class<?>) LockConfig.class, "lock_id = ?", str);
    }

    /* renamed from: 綩私 */
    public static List<AppState> m14912(int i) {
        return LitePal.where("status = ?", String.valueOf(i)).find(AppState.class);
    }

    /* renamed from: 纩慐 */
    public static void m14913(long j) {
//        ChildWeek childWeek = (ChildWeek) LitePal.where("date = ?", DateUtil.m22599()).findFirst(ChildWeek.class);
//        if (childWeek == null) {
//            ChildWeek childWeek2 = new ChildWeek();
//            childWeek2.setDate(DateUtil.m22599());
//            childWeek2.setMin(j);
//            childWeek2.save();
//        } else {
//            childWeek.setMin(childWeek.getMin() + j);
//            childWeek.update(childWeek.getBaseObjId());
//        }
//        if (LitePal.count((Class<?>) ChildWeek.class) > 7) {
//            List find = LitePal.where("date = ?", DateUtil.m22587(7)).find(ChildWeek.class);
//            if (ObjectUtils.isNotEmpty((Collection) find)) {
//                Iterator it = find.iterator();
//                while (it.hasNext()) {
//                    LitePal.delete(ChildWeek.class, ((ChildWeek) it.next()).getBaseObjId());
//                }
//            }
//        }
    }

    @Nullable
    /* renamed from: 耣怳匮色紝参凵蛴纆勚躄, reason: contains not printable characters */
    public static ChildConfig m24189() {
        return (ChildConfig) LitePal.findFirst(ChildConfig.class);
    }



    /* renamed from: 葋申湋骶映鍮秄憁鎓羭, reason: contains not printable characters */
    public static void m24190() {
        LitePal.deleteAll((Class<?>) AppState.class, "status = 4");
    }

    @Nullable
    /* renamed from: 蝸餺閃喍, reason: contains not printable characters */
    public static String m24191() {
//        AppCache appCache = (AppCache) LitePal.where("type = 1").findFirst(AppCache.class);
//        if (appCache != null) {
//            return appCache.getJson();
//        }
        return null;
    }

    /* renamed from: 辒迳圄袡皪郞箟, reason: contains not printable characters */
    public static void m24192(int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", (Integer) 0);
        LitePal.updateAll((Class<?>) VoiceDbBean.class, contentValues, "type = ?", String.valueOf(i));
    }

    /* renamed from: 酸恚辰橔纋黺, reason: contains not printable characters */
    public static void m24193() {
//        LitePal.deleteAll((Class<?>) LockSetting.class, new String[0]);
//        LitePal.deleteAll((Class<?>) LockConfig.class, new String[0]);
    }

    @Nullable
    /* renamed from: 鑭撇糁綖浓緗轟鱼萟磿焈, reason: contains not printable characters */
    public static List<TimeSelectBean> m24194(String str) {
        return LitePal.where("parentId = ?", str).find(TimeSelectBean.class);
    }

    /* renamed from: 销薞醣戔攖餗, reason: contains not printable characters */
    public static void m24195(String str) {
//        if (ObjectUtils.isEmpty((CharSequence) str)) {
//            LogHelper.m27986().m27999("缓存的AppList是空的");
//            return;
//        }
//        AppCache appCache = (AppCache) LitePal.where("type = 1").findFirst(AppCache.class);
//        if (appCache != null) {
//            appCache.setJson(str);
//            appCache.update();
//        } else {
//            AppCache appCache2 = new AppCache();
//            appCache2.setJson(str);
//            appCache2.setType(1);
//            appCache2.save();
//        }
    }

    /* renamed from: 镐藻 */
    public static void m14915() {
//        LitePal.deleteAll((Class<?>) LockInfoBean.class, new String[0]);
//        LitePal.deleteAll((Class<?>) TimeSelectBean.class, new String[0]);
    }

//    @Nullable
//    public static LockConfig m24196(String str) {
//        return (LockConfig) LitePal.where("lock_id = ?", str).findFirst(LockConfig.class);
//    }

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐, reason: contains not printable characters */
    public static void m24197() {
        LitePal.deleteAll((Class<?>) VoiceDbBean.class, new String[0]);
    }

    /* renamed from: 韐爮幀悖罤噩钼遑杯盇, reason: contains not printable characters */
    public static void m24198(ChildConfig childConfig) {
        if (childConfig == null) {
            return;
        }
        ChildConfig childConfig2 = (ChildConfig) LitePal.findFirst(ChildConfig.class);
        if (childConfig2 == null) {
            childConfig.save();
        } else {
            childConfig2.setCloseRecent(childConfig.getCloseRecent());
            childConfig2.setDisallowInstallApp(childConfig.getDisallowInstallApp());
            childConfig2.setDisallowUninstallApp(childConfig.getDisallowUninstallApp());
            childConfig2.setIsStatusBarOpen(childConfig.getIsStatusBarOpen());
            childConfig2.update();
        }
        Mgr.getGuardIntercept().mo14180(childConfig.getDisallowInstallApp() == 1);
        Mgr.getGuardIntercept().mo22200(childConfig.getDisallowUninstallApp() == 1);
        Mgr.getGuardIntercept().mo22195(childConfig.getIsStatusBarOpen() == 1);
        Message obtain = Message.obtain();
        obtain.obj = childConfig;
        obtain.what = 6;
        AccessibilityUtils.m24452().sendMessage(obtain);
    }

    /* renamed from: 駭鑈趘薑衈講堍趃軏, reason: contains not printable characters */
    public static void m24199(AppState appState) {
        AppState appState2 = (AppState) LitePal.where("packageName = ?", appState.getPackageName()).findFirst(AppState.class);
        if (appState2 != null) {
            appState2.setOpenTime(appState.getOpenTime());
            appState2.setAllowTime(appState.getAllowTimeNoCover());
            appState2.setStatus(appState.getStatus());
            appState2.update(appState2.getBaseObjId());
            return;
        }
        appState.setOpenTime(appState.getOpenTime());
        appState.save();
    }

}
