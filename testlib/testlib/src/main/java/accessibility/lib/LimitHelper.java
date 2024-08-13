package accessibility.lib;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import accessibility.AppFilter;
import accessibility.LogHelper;
import accessibility.UserUtils;

public class LimitHelper {

    public final AppFilter appFilter = AppFilter.instance();

    public void m22959() {
//        long m21650 = RewardProvide.m13982().m21650();
//        List<AppState> m14912 = ChildDBManager.m14912(4);
//        if (ObjectUtils.isEmpty((Collection) m14912)) {
//            this.appFilter.m25888();
//            ChildDBManager.m24192(2);
//            return;
//        }
//        ArrayList arrayList = new ArrayList();
//        for (AppState state : m14912) {
//            arrayList.add(state.getPackageName());
//        }
//        Map<String, Long> m14894 = UsageHelper.m14894(arrayList);
//        long j = 0;
//        for (AppState appState : m14912) {
//            Long l = m14894.get(appState.getPackageName());
//            if (l == null) {
//                l = 0L;
//            }
//            m14450(appState.getPackageName() + ":运行时长:" + l + "--允许时长:" + m21650);
//            appState.setLeftTime(l.longValue());
//            appState.update(appState.getBaseObjId());
//            j += l.longValue();
//        }
//        m14450("今日组合App总运行时长:" + j + "--限制时长:" + m21650);
//        if (j >= m21650) {
//            m14450("今日组合App运行时长已到");
//            if (this.appFilter.m25891().notContains(m14912)) {
//                this.appFilter.m25888();
//                ChildDBManager.m24192(2);
//                for (AppState appState2 : m14912) {
//                    this.appFilter.m25894(appState2.getPackageName());
//                    if (appState2.getAppType() == 1) {
//                        ChildDBManager.m24176(appState2.getPackageName(), 2);
//                    }
//                    this.appFilter.m25917(appState2.getPackageName());
//                }
//            }
//            LogHelper.instance().m27998("checkGroupLimit_Result", "组合限制时间到:" + GsonUtils.toJson(m14912) + "--今日组合App总运行时长:" + j);
//            return;
//        }
//        this.appFilter.m25888();
//        ChildDBManager.m24192(2);
//        m14450("今日组合App还没到");
    }

    /* renamed from: 垡玖 */
    public final void m14450(String str) {
    }

    /* renamed from: 灞酞輀攼嵞漁綬迹, reason: contains not printable characters */
    public final boolean m22960(List<TimeDbBean> list) {
        long time = new Date().getTime();
        for (TimeDbBean timeDbBean : list) {
            String startTime = timeDbBean.getStartTime();
            String endTime = timeDbBean.getEndTime();
            long m22601 = DateUtil.m22601(startTime);
            long m226012 = DateUtil.m22601(endTime);
            m14450("时间段：" + timeDbBean.getStartTime() + "~~~" + timeDbBean.getEndTime());
            if (time >= m22601 && time <= m226012) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: 肌緭 */
    public void m14451() {
        if (UserUtils.m24165()) {
            return;
        }
        m22961();
        m22959();
        m22962();
    }

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭, reason: contains not printable characters */
    public void m22961() {
        List<AppState> m14912 = ChildDBManager.m14912(2);
        if (ObjectUtils.isEmpty((Collection) m14912)) {
            this.appFilter.m25869();
            ChildDBManager.m24192(1);
            return;
        }
        ArrayList<AppState> arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator<AppState> it = m14912.iterator();
        while (it.hasNext()) {
            arrayList2.add(it.next().getPackageName());
        }
        Map<String, Long> m14894 = UsageHelper.m14894(arrayList2);
        for (AppState appState : m14912) {
            Long l = m14894.get(appState.getPackageName());
            if (l == null) {
                l = 0L;
            }
            m14450(appState.getPackageName() + ":运行时长:" + l + "--允许时长:" + appState.getAllowTime());
            appState.setLeftTime(l.longValue());
            appState.update(appState.getBaseObjId());
            if (l.longValue() > 0 && l.longValue() >= appState.getAllowTime() + appState.getRewardTime()) {
                arrayList.add(appState);
            }
        }
        if (this.appFilter.m25881().notContains(arrayList)) {
            this.appFilter.m25869();
            ChildDBManager.m24192(1);
            for (AppState appState2 : arrayList) {
                String packageName = appState2.getPackageName();
                this.appFilter.m25872(packageName);
                this.appFilter.m25917(packageName);
                if (appState2.getAppType() == 1) {
                    ChildDBManager.m24176(packageName, 1);
                }
            }
        }
        LogHelper.instance().m27998("checkLimitEvent_Result", "限制应用包名:" + GsonUtils.toJson(arrayList));
    }

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐, reason: contains not printable characters */
    public void m22962() {
        List<AppState> m14912 = ChildDBManager.m14912(6);
        if (ObjectUtils.isEmpty((Collection) m14912)) {
            m14450("无数据");
            this.appFilter.m25885();
            ChildDBManager.m24192(3);
            return;
        }
        ArrayList<AppState> arrayList = new ArrayList();
        for (AppState appState : m14912) {
            boolean m22960 = m22960(ChildDBManager.m24178(appState.getBaseObjId()));
            m14450("是否添加：" + m22960 + "----" + appState.getPackageName());
            if (!m22960) {
                arrayList.add(appState);
            }
        }
        if (this.appFilter.m25898().notContains(arrayList)) {
            this.appFilter.m25885();
            ChildDBManager.m24192(3);
            for (AppState appState2 : arrayList) {
                String packageName = appState2.getPackageName();
                this.appFilter.m25877(packageName);
                this.appFilter.m25917(packageName);
                if (appState2.getAppType() == 1) {
                    ChildDBManager.m24176(packageName, 3);
                }
            }
        }
    }
}
