package com.niu.protect.map.maputil;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Process;
import com.baidu.mapapi.model.LatLng;
import com.niu.protect.BabyApplication;
import com.niu.protect.map.MapConstants;
import com.niu.protect.model.CurrentLocation;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class CommonUtil {
    public static final double DISTANCE = 1.0E-4d;
    public static final String ENTITY_NAME = "myTrace";
    private static DecimalFormat df = new DecimalFormat("######0.00");

    public static String getCurProcessName(Context context) {
        int pid = Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return "";
    }

    public static long getCurrentTime() {
        return System.currentTimeMillis() / 1000;
    }

    public static boolean isEqualToZero(double value) {
        return Math.abs(value - 0.0d) < 0.01d;
    }

    public static boolean isZeroPoint(double latitude, double longitude) {
        return isEqualToZero(latitude) && isEqualToZero(longitude);
    }

    public static long toTimeStamp(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        try {
            Date date = sdf.parse(time);
            return date.getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public static String getHMS(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            return sdf.format((Date) new Timestamp(timestamp));
        } catch (Exception e) {
            e.printStackTrace();
            return String.valueOf(timestamp);
        }
    }

    public static String formatTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.format((Date) new Timestamp(timestamp));
        } catch (Exception e) {
            e.printStackTrace();
            return String.valueOf(timestamp);
        }
    }

    public static String formatSecond(int second) {
        Integer hours = Integer.valueOf(second / 3600);
        Integer minutes = Integer.valueOf((second / 60) - (hours.intValue() * 60));
        Integer seconds = Integer.valueOf((second - (minutes.intValue() * 60)) - ((hours.intValue() * 60) * 60));
        Object[] array = {hours, minutes, seconds};
        return String.format("%1$,02d:%2$,02d:%3$,02d", array);
    }

    public static final String formatDouble(double doubleValue) {
        return df.format(doubleValue);
    }

    public static double getXMoveDistance(double slope) {
        if (slope == Double.MAX_VALUE) {
            return 1.0E-4d;
        }
        return Math.abs((1.0E-4d * slope) / Math.sqrt((slope * slope) + 1.0d));
    }

    public static double getInterception(double slope, LatLng point) {
        return point.latitude - (point.longitude * slope);
    }

    public static double getSlope(LatLng fromPoint, LatLng toPoint) {
        if (toPoint.longitude == fromPoint.longitude) {
            return Double.MAX_VALUE;
        }
        return (toPoint.latitude - fromPoint.latitude) / (toPoint.longitude - fromPoint.longitude);
    }

    public static double getAngle(LatLng fromPoint, LatLng toPoint) {
        double slope = getSlope(fromPoint, toPoint);
        if (slope == Double.MAX_VALUE) {
            return toPoint.latitude > fromPoint.latitude ? 0.0d : 180.0d;
        }
        float deltAngle = 0.0f;
        if ((toPoint.latitude - fromPoint.latitude) * slope < 0.0d) {
            deltAngle = 180.0f;
        }
        double radio = Math.atan(slope);
        return (((radio / 3.141592653589793d) * 180.0d) + deltAngle) - 90.0d;
    }

    public static void saveCurrentLocation(BabyApplication trackApp) {
        SharedPreferences.Editor editor = trackApp.trackConf.edit();
        StringBuffer locationInfo = new StringBuffer();
        locationInfo.append(CurrentLocation.locTime);
        locationInfo.append(";");
        locationInfo.append(CurrentLocation.latitude);
        locationInfo.append(";");
        locationInfo.append(CurrentLocation.longitude);
        editor.putString(MapConstants.LAST_LOCATION, locationInfo.toString());
        editor.apply();
    }

    public static String getEntityName() {
        return ENTITY_NAME;
    }
}
