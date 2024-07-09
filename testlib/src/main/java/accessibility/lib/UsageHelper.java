package accessibility.lib;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import accessibility.App;
import accessibility.AppFilter;
import accessibility.LogHelper;

public class UsageHelper {
    /* renamed from: 偣炱嘵蟴峗舟轛, reason: contains not printable characters */
//    public static String m24127(long j, long j2) {
//        Map<String, Object> m23986 = ProduceReqArg.m23986(CommonUtils.m21223());
//        m23986.put("device_id", CommonUtils.m21223());
//        m23986.put("d_id", CommonUtils.m21234());
//        UsageEvents queryEvents = m14892().queryEvents(j, j2);
//        HashMap hashMap = new HashMap();
//        while (queryEvents.hasNextEvent()) {
//            UsageEvents.Event event = new UsageEvents.Event();
//            queryEvents.getNextEvent(event);
//            String packageName = event.getPackageName();
//            if (ObjectUtils.isNotEmpty((CharSequence) packageName)) {
//                String className = event.getClassName();
//                int eventType = event.getEventType();
//                long timeStamp = event.getTimeStamp();
//                List list = (List) hashMap.get(packageName);
//                if (list == null) {
//                    list = new ArrayList();
//                }
//                list.add(new OriginalUsage(packageName, className, eventType, timeStamp));
//                hashMap.put(packageName, list);
//            }
//        }
//        m23986.putAll(hashMap);
//        return GsonUtils.toJson(m23986);
//    }

    /* renamed from: 刻槒唱镧詴, reason: contains not printable characters */
    public static AppHelper.C6418 m24128() {
        List<AppHelper.C6418> m14904 = AppHelper.m14904(App.mContext);
        AppHelper.C6418 c6418 = null;
        if (!m24129(m14904)) {
            long j = Long.MAX_VALUE;
            for (AppHelper.C6418 c64182 : m14904) {
                long j2 = c64182.f21949;
                if (j2 < j) {
                    c6418 = c64182;
                    j = j2;
                }
            }
        }
        return c6418;
    }

    /* renamed from: 垡玖 */
    public static UsageStatsManager m14892() {
        return (UsageStatsManager) App.mContext.getSystemService(Context.USAGE_STATS_SERVICE);
    }

    /* renamed from: 旞莍癡, reason: contains not printable characters */
    public static boolean m24129(List<AppHelper.C6418> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        for (AppHelper.C6418 c6418 : list) {
            if (!TextUtils.isEmpty(c6418.f13463.getPackageName())) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: 櫓昛刓叡賜, reason: contains not printable characters */
    public static String m24130() {
        return m24135(DateUtil.m14316(), DateUtil.m22594(), false);
    }

    /* renamed from: 灞酞輀攼嵞漁綬迹, reason: contains not printable characters */
    public static long m24131(List<UsageEvents.Event> list, AppHelper.C6418 c6418) {
        ComponentName componentName;
        int i;
        String str;
        ArrayList arrayList = new ArrayList();
        if (list == null || list.isEmpty()) {
            return 0L;
        }
        int size = list.size();
        long j = Long.MAX_VALUE;
        String str2 = null;
        UsageEvents.Event event = null;
        int i2 = 0;
        while (true) {
            boolean z = true;
            if (i2 >= size) {
                break;
            }
            UsageEvents.Event event2 = list.get(i2);
            String packageName = event2.getPackageName();
            if (event2.getEventType() == 1 || event2.getEventType() == 1) {
                String className = event2.getClassName();
                int i3 = i2;
                while (true) {
                    if (i3 >= size) {
                        break;
                    }
                    UsageEvents.Event event3 = list.get(i3);
                    if (event3.getEventType() == 2 || event3.getEventType() == 2) {
                        String className2 = event3.getClassName();
                        if (ObjectUtils.equals(className, className2)) {
                            while (i3 >= 0) {
                                UsageEvents.Event event4 = list.get(i3);
                                str = packageName;
                                if ((event4.getEventType() != 1 && event4.getEventType() != 1) || !ObjectUtils.equals(className2, event4.getClassName())) {
                                    i3--;
                                    packageName = str;
                                } else if (event2.getTimeStamp() == event4.getTimeStamp()) {
                                    arrayList.add(event2);
                                    arrayList.add(event3);
                                }
                            }
                        }
                    }
                    i3++;
                    packageName = packageName;
                }
                str = packageName;
                z = false;
                if (!z) {
                    int i4 = i2;
                    while (true) {
                        if (i4 >= size) {
                            break;
                        }
                        UsageEvents.Event event5 = list.get(i4);
                        if (event5.getEventType() == 23 && ObjectUtils.equals(className, event5.getClassName())) {
                            arrayList.add(event2);
                            arrayList.add(event5);
                            break;
                        }
                        i4++;
                    }
                }
            } else {
                str = packageName;
            }
            if (event2.getTimeStamp() < j) {
                j = event2.getTimeStamp();
                event = event2;
            }
            i2++;
            str2 = str;
        }
        long j2 = 0;
        for (int i5 = 0; i5 < arrayList.size() && (i = i5 + 1) < arrayList.size(); i5 += 2) {
            UsageEvents.Event event6 = (UsageEvents.Event) arrayList.get(i5);
            UsageEvents.Event event7 = (UsageEvents.Event) arrayList.get(i);
            if ((event6.getEventType() == 1 || event6.getEventType() == 1) && (event7.getEventType() == 2 || event7.getEventType() == 2 || event7.getEventType() == 23)) {
                j2 += event7.getTimeStamp() - event6.getTimeStamp();
            }
        }
        if (c6418 != null && (componentName = c6418.f13463) != null && ObjectUtils.equals(componentName.getPackageName(), str2)) {
            j2 += System.currentTimeMillis() - c6418.f21949;
        }
        if (event == null) {
            return j2;
        }
        if (event.getEventType() != 2 && event.getEventType() != 2 && event.getEventType() != 23) {
            return j2;
        }
        if (event.getTimeStamp() > DateUtil.m22604()) {
            return j2;
        }
        long m22585 = DateUtil.m22585();
        return event.getTimeStamp() > m22585 ? j2 + (event.getTimeStamp() - m22585) : j2;
    }

    /* renamed from: 睳堋弗粥辊惶, reason: contains not printable characters */
    public static String m24132() {
        return m24135(DateUtil.m22585(), System.currentTimeMillis(), true);
    }

    /* renamed from: 祴嚚橺谋肬鬧舘, reason: contains not printable characters */
    public static long m24133() {
        Map<String, List<UsageEvents.Event>> m24134 = m24134();
        AppHelper.C6418 m24128 = m24128();
        Iterator<Map.Entry<String, List<UsageEvents.Event>>> it = m24134.entrySet().iterator();
        long j = 0;
        while (it.hasNext()) {
            j += m24131(it.next().getValue(), m24128);
        }
        return j;
    }

    /* renamed from: 肌緭 */
    public static boolean m14893(String str) {
        return (ObjectUtils.equals(AppFilter.instance().m15539(), str) || ObjectUtils.equals(str, AppUtils.getAppPackageName()) || ObjectUtils.equals("android", str) || ObjectUtils.equals("com.android.settings", str)) ? false : true;
    }

    /* renamed from: 葋申湋骶映鍮秄憁鎓羭, reason: contains not printable characters */
    public static Map<String, List<UsageEvents.Event>> m24134() {
        return m24136(new ArrayList(), DateUtil.m22585(), System.currentTimeMillis());
    }

    /* renamed from: 酸恚辰橔纋黺, reason: contains not printable characters */
    public static String m24135(long j, long j2, boolean z) {
        int i;
        int i2;
        HashMap hashMap;
        Iterator it;
        ComponentName componentName;
        HashMap hashMap2;
        Iterator it2;
        boolean z2;
        UsageEvents queryEvents = m14892().queryEvents(j, j2);
        HashMap hashMap3 = new HashMap();
        while (true) {
            i = 2;
            i2 = 1;
            if (!queryEvents.hasNextEvent()) {
                break;
            }
            UsageEvents.Event event = new UsageEvents.Event();
            queryEvents.getNextEvent(event);
            String packageName = event.getPackageName();
            String className = event.getClassName();
            if (!TextUtils.isEmpty(packageName) && !TextUtils.isEmpty(className) && m14893(packageName) && (event.getEventType() == 1 || event.getEventType() == 1 || event.getEventType() == 2 || event.getEventType() == 2 || event.getEventType() == 23)) {
                List list = (List) hashMap3.get(packageName);
                if (list == null) {
                    list = new ArrayList();
                }
                list.add(new UsageJson(className, event.getEventType(), event.getTimeStamp()));
                hashMap3.put(packageName, list);
            }
        }
        if (hashMap3.size() == 0) {
            LogHelper.m27994("今日没有运行任何App");
            return "";
        }
        ArrayList arrayList = new ArrayList();
        Iterator it3 = hashMap3.entrySet().iterator();
        while (it3.hasNext()) {
            Map.Entry entry = (Map.Entry) it3.next();
            String str = (String) entry.getKey();
            List list2 = (List) entry.getValue();
            if (list2.isEmpty()) {
                hashMap = hashMap3;
                it = it3;
            } else {
                arrayList.clear();
                int size = list2.size();
                long j3 = Long.MAX_VALUE;
                UsageJson usageJson = null;
                UsageJson usageJson2 = null;
                int i3 = 0;
                while (i3 < size) {
                    UsageJson usageJson3 = (UsageJson) list2.get(i3);
                    if (usageJson3.getEventType() == i2 || usageJson3.getEventType() == i2) {
                        String className2 = usageJson3.getClassName();
                        int i4 = i3;
                        while (true) {
                            if (i4 >= size) {
                                hashMap2 = hashMap3;
                                it2 = it3;
                                break;
                            }
                            UsageJson usageJson4 = (UsageJson) list2.get(i4);
                            it2 = it3;
                            if (usageJson4.getEventType() == i || usageJson4.getEventType() == i) {
                                String className3 = usageJson4.getClassName();
                                if (ObjectUtils.equals(className2, className3)) {
                                    while (true) {
                                        if (i4 < 0) {
                                            hashMap2 = hashMap3;
                                            break;
                                        }
                                        UsageJson usageJson5 = (UsageJson) list2.get(i4);
                                        hashMap2 = hashMap3;
                                        if ((usageJson5.getEventType() != 1 && usageJson5.getEventType() != 1) || !ObjectUtils.equals(className3, usageJson5.getClassName())) {
                                            i4--;
                                            hashMap3 = hashMap2;
                                        } else if (usageJson3.getT() == usageJson5.getT()) {
                                            arrayList.add(usageJson3);
                                            arrayList.add(usageJson4);
                                            z2 = true;
                                        }
                                    }
                                }
                            }
                            i4++;
                            it3 = it2;
                            hashMap3 = hashMap3;
                            i = 2;
                        }
                        z2 = false;
                        if (!z2) {
                            int i5 = i3;
                            while (true) {
                                if (i5 >= size) {
                                    break;
                                }
                                UsageJson usageJson6 = (UsageJson) list2.get(i5);
                                if (usageJson6.getEventType() == 23 && ObjectUtils.equals(className2, usageJson6.getClassName())) {
                                    arrayList.add(usageJson3);
                                    arrayList.add(usageJson6);
                                    z2 = true;
                                    break;
                                }
                                i5++;
                            }
                        }
                        if (!z2 && (usageJson == null || usageJson.getT() < usageJson3.getT())) {
                            usageJson = usageJson3;
                        }
                    } else {
                        hashMap2 = hashMap3;
                        it2 = it3;
                    }
                    if (usageJson3.getT() < j3) {
                        j3 = usageJson3.getT();
                        usageJson2 = usageJson3;
                    }
                    i3++;
                    it3 = it2;
                    hashMap3 = hashMap2;
                    i = 2;
                    i2 = 1;
                }
                HashMap hashMap4 = hashMap3;
                it = it3;
                if (!z && usageJson != null) {
                    arrayList.add(usageJson);
                    arrayList.add(new UsageJson(usageJson.getC(), 2, DateUtil.m22594(), 1));
                    LogHelper.m27985("昨夜未关闭事件:" + str + "，手动添加一个结束事件");
                } else {
                    AppHelper.C6418 m24128 = m24128();
                    if (m24128 != null && (componentName = m24128.f13463) != null && usageJson != null && ObjectUtils.equals(componentName.getPackageName(), str)) {
                        arrayList.add(usageJson);
                        arrayList.add(new UsageJson(usageJson.getC(), 2, System.currentTimeMillis(), 1));
                        LogHelper.m27994("当前正在运行:" + str + "，手动添加一个结束事件");
                    }
                }
                if (usageJson2 != null) {
                    if (usageJson2.getEventType() != 2 && usageJson2.getEventType() != 2) {
                        if (usageJson2.getEventType() != 23) {
                        }
                    }
                    if (z) {
                        if (usageJson2.getT() <= DateUtil.m22604()) {
                            arrayList.add(0, new UsageJson(usageJson2.getClassName(), 1, DateUtil.m22585(), 1));
                            arrayList.add(1, usageJson2);
                        }
                    } else if (usageJson2.getT() <= DateUtil.m22592()) {
                        arrayList.add(0, new UsageJson(usageJson2.getClassName(), 1, DateUtil.m14316(), 1));
                        arrayList.add(1, usageJson2);
                        LogHelper.m27994("跨越凌晨未结束，手动添加一个开始事件");
                    }
                    LogHelper.m27994("跨越凌晨未结束，手动添加一个开始事件");
                }
                list2.clear();
                list2.addAll(arrayList);
                hashMap = hashMap4;
                hashMap.put(str, list2);
            }
            it3 = it;
            hashMap3 = hashMap;
            i = 2;
            i2 = 1;
        }
        return GsonUtils.toJson(hashMap3);
    }

    /* renamed from: 镐藻 */
    public static Map<String, Long> m14894(List<String> list) {
        Map<String, List<UsageEvents.Event>> m24136 = m24136(list, DateUtil.m22585(), System.currentTimeMillis());
        HashMap hashMap = new HashMap();
        AppHelper.C6418 m24128 = m24128();
        for (Map.Entry<String, List<UsageEvents.Event>> entry : m24136.entrySet()) {
            hashMap.put(entry.getKey(), Long.valueOf(m24131(entry.getValue(), m24128)));
        }
        return hashMap;
    }

    /* renamed from: 鞈鵚主瀭孩濣痠閕讠陲檓敐, reason: contains not printable characters */
    public static Map<String, List<UsageEvents.Event>> m24136(List<String> list, long j, long j2) {
        boolean isEmpty = list.isEmpty();
        HashMap hashMap = new HashMap();
        UsageEvents queryEvents = m14892().queryEvents(j, j2);
        while (queryEvents.hasNextEvent()) {
            UsageEvents.Event event = new UsageEvents.Event();
            queryEvents.getNextEvent(event);
            String packageName = event.getPackageName();
            if (isEmpty || list.contains(packageName)) {
                String className = event.getClassName();
                if (!TextUtils.isEmpty(packageName) && !TextUtils.isEmpty(className) && m14893(packageName)) {
                    if (event.getEventType() != 1 && event.getEventType() != 1) {
                        if (event.getEventType() == 2 || event.getEventType() == 2 || event.getEventType() == 23) {
                            List list2 = (List) hashMap.get(packageName);
                            if (list2 == null) {
                                list2 = new ArrayList();
                            }
                            list2.add(event);
                            hashMap.put(packageName, list2);
                        }
                    } else {
                        List list3 = (List) hashMap.get(packageName);
                        if (list3 == null) {
                            list3 = new ArrayList();
                        }
                        list3.add(event);
                        hashMap.put(packageName, list3);
                    }
                }
            }
        }
        return hashMap;
    }
}
