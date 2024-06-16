package com.niuniu.babyprotect.tools.data;

import java.text.SimpleDateFormat;
import java.util.Date;
public class RelativeDateFormat {
    private static final long ONE_DAY = 86400000;
    private static final String ONE_DAY_AGO = "天前";
    private static final long ONE_HOUR = 3600000;
    private static final String ONE_HOUR_AGO = "小时前";
    private static final long ONE_MINUTE = 60000;
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_SECOND_AGO = "秒前";
    private static final long ONE_WEEK = 604800000;
    private static final String ONE_YEAR_AGO = "年前";

    public static String format(long times) {
        Date date = new Date(times);
        long delta = new Date().getTime() - date.getTime();
        if (delta < 60000) {
            long seconds = toSeconds(delta);
            StringBuilder sb = new StringBuilder();
            sb.append(seconds > 0 ? seconds : 1L);
            sb.append(ONE_SECOND_AGO);
            return sb.toString();
        } else if (delta < 2700000) {
            long minutes = toMinutes(delta);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(minutes > 0 ? minutes : 1L);
            sb2.append(ONE_MINUTE_AGO);
            return sb2.toString();
        } else if (delta < 86400000) {
            long hours = toHours(delta);
            StringBuilder sb3 = new StringBuilder();
            sb3.append(hours > 0 ? hours : 1L);
            sb3.append(ONE_HOUR_AGO);
            return sb3.toString();
        } else if (delta < 172800000) {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            String time = format.format(date);
            return "昨天 " + time;
        } else if (delta < 2592000000L) {
            SimpleDateFormat format2 = new SimpleDateFormat("MM-dd HH:mm");
            return format2.format(date);
        } else if (delta < 29030400000L) {
            SimpleDateFormat format3 = new SimpleDateFormat("MM-dd HH:mm");
            return format3.format(date);
        } else {
            SimpleDateFormat format4 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return format4.format(date);
        }
    }

    private static long toSeconds(long date) {
        return date / 1000;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60;
    }

    private static long toDays(long date) {
        return toHours(date) / 24;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365;
    }
}
