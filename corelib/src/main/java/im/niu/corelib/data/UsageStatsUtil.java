package im.niu.corelib.data;

public class UsageStatsUtil {
//    List<AppInfo> alist;
//    List<UsePackageInfo> showList = new ArrayList();
//    List<UsePackageInfo> showappList = new ArrayList();
//
//    public static void getAppUse(Context mContext) {
//        UsageEvents events;
//        long end = System.currentTimeMillis();
//        long startTime = NiuUtil.Companion.timeToDayStart(Long.valueOf(end));
//        UsageStatsManager usageStatsManager = (UsageStatsManager) mContext.getSystemService(Context.USAGE_STATS_SERVICE);
//        if (usageStatsManager == null || (events = usageStatsManager.queryEvents(startTime, end)) == null) {
//            return;
//        }
//        UsageEvents.Event usageEvent = new UsageEvents.Event();
//        PackageManager pm = mContext.getPackageManager();
//        while (events.hasNextEvent()) {
//            events.getNextEvent(usageEvent);
//            if (usageEvent.getEventType() == UsageEvents.Event.ACTIVITY_RESUMED) {
//                UsePackageInfo myPackageInfo = new UsePackageInfo();
//                try {
//                    PackageInfo appPackageInfo = pm.getPackageInfo(usageEvent.getPackageName(), 0);
//                    myPackageInfo.setmAppName(appPackageInfo.applicationInfo.loadLabel(mContext.getPackageManager()).toString());
//                } catch (PackageManager.NameNotFoundException e) {
//                    e.printStackTrace();
//                }
//                myPackageInfo.setStaTime(usageEvent.getTimeStamp());
//                myPackageInfo.setmPackageName(usageEvent.getPackageName());
//                myPackageInfo.setAppType(usageEvent.getEventType());
//                myPackageInfo.setChangeTimes(usageEvent.getTimeStamp());
////                ILog.d("UsageEvents-onresu-", myPackageInfo.getmAppName());
//            } else if (usageEvent.getEventType() == UsageEvents.Event.ACTIVITY_PAUSED) {
//                UsePackageInfo myPackageInfo2 = new UsePackageInfo();
//                try {
//                    PackageInfo appPackageInfo2 = pm.getPackageInfo(usageEvent.getPackageName(), 0);
//                    myPackageInfo2.setmAppName(appPackageInfo2.applicationInfo.loadLabel(mContext.getPackageManager()).toString());
//                } catch (PackageManager.NameNotFoundException e2) {
//                    e2.printStackTrace();
//                }
//                myPackageInfo2.setmPackageName(usageEvent.getPackageName());
//                myPackageInfo2.setAppType(usageEvent.getEventType());
//                myPackageInfo2.setChangeTimes(usageEvent.getTimeStamp());
////                ILog.d("UsageEvents-onPaused-", myPackageInfo2.getmAppName());
//
//            }
//        }
//        new HashMap();
//    }
//
//    public static List<AppInfo> getAppList(Context context) {
//        List<AppInfo> list = new ArrayList<>();
//        PackageManager pm = context.getPackageManager();
//        Intent mainIntent = new Intent("android.intent.action.MAIN", (Uri) null);
//        mainIntent.addCategory("android.intent.category.LAUNCHER");
//        List<ResolveInfo> activities = pm.queryIntentActivities(mainIntent, 0);
//        for (ResolveInfo info : activities) {
//            String packName = info.activityInfo.packageName;
//            if (!packName.equals(context.getPackageName())) {
//                AppInfo mInfo = new AppInfo();
//                mInfo.ico = info.activityInfo.applicationInfo.loadIcon(pm);
//                mInfo.name = info.activityInfo.applicationInfo.loadLabel(pm).toString();
//                mInfo.packageName = packName;
//                Intent launchIntent = new Intent();
//                launchIntent.setComponent(new ComponentName(packName, info.activityInfo.name));
//                mInfo.intent = launchIntent;
//                list.add(mInfo);
//            }
//        }
//        return list;
//    }
}

