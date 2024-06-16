package com.niuniu.babyprotect.tools;

import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class EventUtils {
    public static final long DAY_IN_MILLIS = 86400000;
    public static final String TAG = "EventUtils";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("M-d-yyyy HH:mm:ss");

    public static ArrayList<UsageEvents.Event> getEventList(Context context, long startTime, long endTime) {
        ArrayList<UsageEvents.Event> mEventList = new ArrayList<>();
        Log.i(TAG, " EventUtils-getEventList()   Range start:" + startTime);
        Log.i(TAG, " EventUtils-getEventList()   Range end:" + endTime);
        Log.i(TAG, " EventUtils-getEventList()   Range start:" + dateFormat.format(Long.valueOf(startTime)));
        Log.i(TAG, " EventUtils-getEventList()   Range end:" + dateFormat.format(Long.valueOf(endTime)));
        UsageStatsManager mUsmManager = (UsageStatsManager) context.getSystemService("usagestats");
        UsageEvents events = mUsmManager.queryEvents(startTime, endTime);
        while (events.hasNextEvent()) {
            UsageEvents.Event e = new UsageEvents.Event();
            events.getNextEvent(e);
            if (e.getEventType() == 1 || e.getEventType() == 2) {
                Log.i(TAG, " EventUtils-getEventList()  " + e.getTimeStamp() + "   event:" + e.getClassName() + "   type = " + e.getEventType());
                mEventList.add(e);
            }
        }
        return mEventList;
    }

    public static ArrayList<UsageStats> getUsageList(Context context, long startTime, long endTime) {
        UsageStatsManager mUsmManager;
        List<UsageStats> ulist;
        Log.i(TAG, " EventUtils-getUsageList()   Range start:" + startTime);
        Log.i(TAG, " EventUtils-getUsageList()   Range end:" + endTime);
        Log.i(TAG, " EventUtils-getUsageList()   Range start:" + dateFormat.format(Long.valueOf(startTime)));
        Log.i(TAG, " EventUtils-getUsageList()   Range end:" + dateFormat.format(Long.valueOf(endTime)));
        ArrayList<UsageStats> list = new ArrayList<>();
        UsageStatsManager mUsmManager2 = (UsageStatsManager) context.getSystemService("usagestats");
        List<UsageStats> ulist2 = mUsmManager2.queryUsageStats(0, startTime, endTime);
        for (UsageStats stats : ulist2) {
            if (stats.getTotalTimeInForeground() <= 0) {
                mUsmManager = mUsmManager2;
                ulist = ulist2;
            } else {
                long currentStamp = Tools.zeroTimeForLong(Long.valueOf(new Date().getTime()));
                String timeStr = Tools.timeFormat(new Date(stats.getFirstTimeStamp()), "yyyy-MM-dd HH:mm");
                StringBuilder sb = new StringBuilder();
                mUsmManager = mUsmManager2;
                sb.append(" EventUtils-getUsageList()   llltime=");
                sb.append(timeStr);
                Log.e(TAG, sb.toString());
                String timeStr2 = Tools.timeFormat(new Date(currentStamp), "yyyy-MM-dd HH:mm");
                StringBuilder sb2 = new StringBuilder();
                ulist = ulist2;
                sb2.append(" EventUtils-getUsageList()   la=time=");
                sb2.append(timeStr2);
                Log.e(TAG, sb2.toString());
                if (stats.getFirstTimeStamp() >= currentStamp) {
                    list.add(stats);
                    String timeStr3 = Tools.timeFormat(new Date(stats.getFirstTimeStamp()), "yyyy-MM-dd HH:mm");
                    Log.e(TAG, " EventUtils-getUsageList()   time=" + timeStr3 + "  stats:" + stats.getPackageName() + "   TotalTimeInForeground = " + stats.getTotalTimeInForeground());
                    String timeStr4 = Tools.timeFormat(new Date(stats.getLastTimeUsed()), "yyyy-MM-dd HH:mm");
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(" EventUtils-getUsageList()   llltime=");
                    sb3.append(timeStr4);
                    Log.e(TAG, sb3.toString());
                    String timeStr5 = Tools.timeFormat(new Date(stats.getLastTimeStamp()), "yyyy-MM-dd HH:mm");
                    Log.e(TAG, " EventUtils-getUsageList()   la=time=" + timeStr5);
                }
            }
            mUsmManager2 = mUsmManager;
            ulist2 = ulist;
        }
        return list;
    }
}
