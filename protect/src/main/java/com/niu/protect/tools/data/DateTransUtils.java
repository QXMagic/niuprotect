package com.niu.protect.tools.data;

import android.util.Log;

import com.niu.protect.core.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class DateTransUtils {
    public static final long DAY_IN_MILLIS = 86400000;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("M-d-yyyy");

    public static String stampToDate(String stamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(stamp).longValue();
        Date date = new Date(lt);
        String res = simpleDateFormat.format(date);
        return res;
    }

    public static String stampToDate(long stamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(stamp);
        String res = simpleDateFormat.format(date);
        return res;
    }

    public static long getTodayStartStamp(int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(11, hour);
        cal.set(12, minute);
        cal.set(13, second);
        long todayStamp = cal.getTimeInMillis();
        Log.i("Wingbu", " DateTransUtils-getTodayStartStamp()  获取当日" + hour + Constants.COLON_SEPARATOR + minute + Constants.COLON_SEPARATOR + second + "的时间戳 :" + todayStamp);
        return todayStamp;
    }

    public static long getZeroClockTimestamp(long time) {
        long currentStamp = time - (time % 86400000);
        Log.e("Wingbu", " DateTransUtils-getZeroClockTimestamp()  获取当日00:00:00的时间戳,东八区则为早上八点 :" + currentStamp);
        return currentStamp;
    }

    public static ArrayList<String> getSearchDays() {
        ArrayList<String> dayList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            dayList.add(getDateString(i));
        }
        return dayList;
    }

    public static String getDateString(int dayNumber) {
        long time = System.currentTimeMillis() - (dayNumber * 86400000);
        Log.i("Wingbu", " DateTransUtils-getDateString()  获取查询的日期 :" + dateFormat.format(Long.valueOf(time)));
        return dateFormat.format(Long.valueOf(time));
    }
}
