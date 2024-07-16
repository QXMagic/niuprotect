package com.niu.protect.tools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import android.widget.Toast;

import androidx.core.view.ViewCompat;


import com.google.gson.Gson;
import com.niu.protect.core.Constants;
import com.niu.protect.model.AppInfo;
import com.niu.protect.model.OtherTimeInfo;
import com.niu.protect.model.ParentModelInfo;
import com.niu.protect.model.TeacherModelInfo;
import com.niu.protect.model.UsePackageInfo;
import com.niu.protect.model.UserInfo;
import com.niu.protect.model.WeekModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class Tools {
    public static final String BasePath = Environment.getExternalStorageDirectory().getPath() + "/smartlamp/";
    public static final String filepath = "smartlamp";
    public static final int pageSize = 20;
    public static final int timeout = 5;

    public static String timeFormat(Date date, String sformat) {
        SimpleDateFormat format = new SimpleDateFormat(sformat);
        return format.format(date);
    }

    public static long timeForLong(String sformat) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = format.parse(sformat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long timestamp = date.getTime();
        return timestamp;
    }

    /**
     * 获取指定时间的凌晨时间戳
     * */
    public static long zeroTimeForLong(Long timest) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(timest.longValue());
        String dateStr = format.format(date);
        String dateStr2 = dateStr + " 00:00:00";
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = format2.parse(dateStr2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long timestamp = date.getTime();
        return timestamp;
    }

    public static void showAlert3(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static Bitmap small(Bitmap bitmap, float value) {
        Matrix matrix = new Matrix();
        matrix.postScale(value, value);
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) ((dipValue * scale) + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) ((pxValue / scale) + 0.5f);
    }

    public static int getwindowwidth(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getwindowheight(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public static void saveToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        SharedPreferences.Editor editor = sp.edit();
        if (token == null) {
            editor.remove("token");
        } else {
            editor.putString("token", token);
        }
        editor.commit();
    }

    public static String getToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        String userMsg = sp.getString("token", null);
        if (userMsg == null) {
            return null;
        }
        return userMsg;
    }

    public static void saveQxSet(Context context, int type) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("QxSet", type);
        editor.commit();
    }

    public static int getQxSet(Context context) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        int userMsg = sp.getInt("QxSet", 0);
        return userMsg;
    }

    public static void saveLocTask(Context context, int type) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("LocTask", type);
        editor.commit();
    }

    public static int getLocTask(Context context) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        int aotuSet = sp.getInt("LocTask", 0);
        return aotuSet;
    }

    public static void saveAutoSet(Context context, int type) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("AutoSet", type);
        editor.commit();
    }

    public static int getAutoSet(Context context) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        int aotuSet = sp.getInt("AutoSet", 0);
        return aotuSet;
    }

    public static void saveStep4(Context context, int type) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("Step4", type);
        editor.commit();
    }

    public static int getStep4(Context context) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        int aotuSet = sp.getInt("Step4", 0);
        return aotuSet;
    }

    public static void saveStep5(Context context, int type) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("Step5", type);
        editor.commit();
    }

    public static int getStep5(Context context) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        int aotuSet = sp.getInt("Step5", 0);
        return aotuSet;
    }

    public static void saveStep6(Context context, int type) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("Step6", type);
        editor.commit();
    }

    public static int getStep6(Context context) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        int aotuSet = sp.getInt("Step6", 0);
        return aotuSet;
    }

    public static void saveStep7(Context context, int type) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("Step7", type);
        editor.commit();
    }

    public static int getStep7(Context context) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        int aotuSet = sp.getInt("Step7", 0);
        return aotuSet;
    }

    public static void saveStep8(Context context, int type) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("Step8", type);
        editor.commit();
    }

    public static int getStep8(Context context) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        int aotuSet = sp.getInt("Step8", 0);
        return aotuSet;
    }

    public static void saveStep9(Context context, int type) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("Step9", type);
        editor.commit();
    }

    public static int getStep9(Context context) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        int aotuSet = sp.getInt("Step9", 0);
        return aotuSet;
    }

    public static void saveStep10(Context context, int type) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("Step10", type);
        editor.commit();
    }

    public static int getStep10(Context context) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        int aotuSet = sp.getInt("Step10", 0);
        return aotuSet;
    }

    public static void saveAotuSet(Context context, int type) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("AotuSet", type);
        editor.commit();
    }

    public static int getAotuSet(Context context) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        int aotuSet = sp.getInt("AotuSet", -1);
        return aotuSet;
    }

    public static void saveIngAotuSet(Context context, int type) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("IngAotuSet", type);
        editor.commit();
    }

    public static int getIngAotuSet(Context context) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        int aotuSet = sp.getInt("IngAotuSet", 0);
        return aotuSet;
    }

    public static void saveUserName(Context context, String username) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        SharedPreferences.Editor editor = sp.edit();
        if (username == null) {
            editor.remove("username");
        } else {
            editor.putString("username", username);
        }
        editor.commit();
    }

    public static String getUsername(Context context) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        String userMsg = sp.getString("username", null);
        if (userMsg == null) {
            return null;
        }
        return userMsg;
    }

    public static void savePwd(Context context, String pwd) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        SharedPreferences.Editor editor = sp.edit();
        if (pwd == null) {
            editor.remove("pwd");
        } else {
            editor.putString("pwd", pwd);
        }
        editor.commit();
    }

    public static String getPwd(Context context) {
        SharedPreferences sp = context.getSharedPreferences("babypro", 0);
        String userMsg = sp.getString("pwd", null);
        if (userMsg == null) {
            return null;
        }
        return userMsg;
    }

    public static Bitmap GetRoundedCornerBitmap(Bitmap bitmap, int roundPx) {
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(ViewCompat.MEASURED_STATE_MASK);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            canvas.drawBitmap(bitmap, src, rect, paint);
            return output;
        } catch (Exception e) {
            return bitmap;
        }
    }

    public static String BinaryToHex(String s) {
        if (s.equals("")) {
            return Constants.MSG_DB_READY_REPORT;
        }
        return Long.toHexString(Long.parseLong(s, 2));
    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString();
        return uniqueId;
    }

    public static Bitmap decodeUriAsBitmap(Uri uri, Context context) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, String> urlToMap(String url) {
        Map<String, String> list = new HashMap<>();
        String[] sta = url.split("&");
        for (String str : sta) {
            String[] ccc = str.split("=");
            list.put(ccc[0], ccc[1]);
        }
        return list;
    }

    public static void showLogin(Context context) {
    }

    public static int getDpi(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            Class c = Class.forName("android.view.Display");
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            int height = dm.heightPixels;
            return height;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int[] getScreenWH(Context poCotext) {
        WindowManager wm = (WindowManager) poCotext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        return new int[]{width, height};
    }

    public static int getVrtualBtnHeight(Context poCotext) {
        int[] location = getScreenWH(poCotext);
        int realHeiht = getDpi((Activity) poCotext);
        int virvalHeight = realHeiht - location[1];
        return virvalHeight;
    }

    public static String logbyte(byte[] data) {
        String revmsg = "";
        for (byte b : data) {
            String aa = Integer.toHexString(b);
            revmsg = aa.length() == 1 ? revmsg + Constants.MSG_DB_READY_REPORT + aa + Constants.ACCEPT_TIME_SEPARATOR_SP : revmsg + aa.replace("ffffff", "") + Constants.ACCEPT_TIME_SEPARATOR_SP;
        }
        return revmsg;
    }

    public static int BCD_CO(int x) {
        return ((x / 10) * 16) + (x % 10);
    }

    public static int BCD_TO_TEN(int x) {
        return ((x / 16) * 10) + (x % 16);
    }

    public static void saveUpTime(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("smartam", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, System.currentTimeMillis());
        editor.commit();
    }

    public static String getUpTime(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("smartam", 0);
        long ltime = sp.getLong(key, 0L);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm");
        if (ltime == 0) {
            return format.format(new Date());
        }
        return format.format(new Date(ltime));
    }

    public static float changeHeight(int type, int value) {
        float height = value;
        if (type == 1) {
            return Math.round((height * 0.0328f) * 10.0f) / 10.0f;
        }
        return height;
    }

    public static float changeWidth(int type, int value) {
        float width = value;
        if (type == 2) {
            return Math.round((width * 2.2046225f) * 10.0f) / 10.0f;
        }
        if (type == 3) {
            return Math.round((width * 0.157473f) * 10.0f) / 10.0f;
        }
        return width;
    }

    public static float changelength(int type, int value) {
        float length = value / 1000.0f;
        if (type == 2) {
            length *= 0.621f;
        }
        return ((int) (length * 100.0f)) / 100.0f;
    }

    private boolean ExistSDCard() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            return true;
        }
        return false;
    }

    public static float LowPassFilter0(float data, float ratio, float Yn_1) {
        return (ratio * data) + ((1.0f - ratio) * Yn_1);
    }

    public static String creatFile(String fileName) {
        String path = BasePath + fileName;
        File dirFirstFolder = new File(path);
        if (!dirFirstFolder.exists()) {
            dirFirstFolder.mkdirs();
        }
        return path;
    }

    public static void writeToFile(String filePath, String msg) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(msg.getBytes());
            raf.close();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public static List<String> getFileList(String path) {
        File file = new File(path);
        List<String> mlist = new ArrayList<>();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) {
                return mlist;
            }
            for (File file2 : files) {
                String filePath = file2.getAbsolutePath();
                if (filePath.indexOf(".txt") != -1) {
                    mlist.add(filePath);
                }
            }
        }
        return mlist;
    }

    public static String readFile(String path) {
        try {
            File urlFile = new File(path);
            InputStreamReader isr = new InputStreamReader(new FileInputStream(urlFile), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String str = "";
            while (true) {
                String mimeTypeLine = br.readLine();
                if (mimeTypeLine != null) {
                    str = str + mimeTypeLine;
                } else {
                    return str;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean objIsNullStr(Object obj) {
        if (obj == null || obj.toString().isEmpty() || obj.toString().equals("")) {
            return true;
        }
        return false;
    }

    public static void saveUser(Context context, String msg) {
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        SharedPreferences.Editor editor = sp.edit();
        if (msg == null) {
            editor.remove("usermsg");
        } else {
            editor.putString("usermsg", msg);
        }
        editor.commit();
    }

    public static UserInfo getUser(Context context) {
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        String userMsg = sp.getString("usermsg", null);
        if (userMsg == null) {
            return null;
        }
        Gson gson = new Gson();
        UserInfo userModel = (UserInfo) gson.fromJson(userMsg, UserInfo.class);
        return userModel;
    }

    public static JSONArray getBlackApp(Context context) {
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        String userMsg = sp.getString("BlackApp", null);
        if (userMsg == null) {
            return new JSONArray();
        }
        try {
            return new JSONArray(userMsg);
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public static void saveBlackApp(Context context, JSONArray array) {
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("BlackApp", array.toString());
        editor.commit();
    }

    public static int getDeskTop(Context context) {
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        int userMsg = sp.getInt("DeskTop", 0);
        return userMsg;
    }

    public static void saveDeskTop(Context context, int msg) {
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("DeskTop", msg);
        editor.commit();
    }

    public static void saveAppPageList(Context context, String msg) {
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("AppPageList", msg);
        editor.commit();
    }

    public static int getPertTop(Context context) {
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        int userMsg = sp.getInt("PertTop", 0);
        return userMsg;
    }

    public static void savePertTop(Context context, int msg) {
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("PertTop", msg);
        editor.commit();
        ILog.d("savePertTop", msg + "");
    }

    public static List<String> getAppPageList(Context context) {
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        String appList = sp.getString("AppPageList", null);
        if (appList == null) {
            return new ArrayList();
        }
        List<String> stringList = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(appList);
            for (int i = 0; i < array.length(); i++) {
                stringList.add(array.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringList;
    }

    public static void saveAppList(Context context) {
        String dateStr = timeFormat(new Date(), "yyyy-MM-dd");
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("AppList", dateStr);
        editor.commit();
    }

    public static boolean getAppList(Context context) {
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        String appList = sp.getString("AppList", null);
        if (appList == null) {
            saveAppList(context);
            return false;
        }
        String dateStr = timeFormat(new Date(), "yyyy-MM-dd");
        if (dateStr.equals(appList)) {
            return true;
        }
        saveAppList(context);
        return false;
    }

    public static void saveOneUse(Context context) {
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("OneUse", true);
        editor.commit();
    }

    public static boolean firstStart(Context context) {
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        boolean isUse = sp.getBoolean("OneUse", false);
        return isUse;
    }

    public static void saveParentSchool(Context context, String msg) {
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        SharedPreferences.Editor editor = sp.edit();
        if (msg == null) {
            editor.remove("ParentModelInfo");
        } else {
            editor.putString("ParentModelInfo", msg);
        }
        editor.commit();
    }

    public static ParentModelInfo getParentSchool(Context context) {
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        String userMsg = sp.getString("ParentModelInfo", null);
        if (userMsg == null) {
            return null;
        }
        Gson gson = new Gson();
        ParentModelInfo userModel = (ParentModelInfo) gson.fromJson(userMsg, ParentModelInfo.class);
        return userModel;
    }

    public static void saveOtherTime(Context context, String msg) {
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        SharedPreferences.Editor editor = sp.edit();
        if (msg == null) {
            editor.remove("OtherTimeInfo");
        } else {
            editor.putString("OtherTimeInfo", msg);
        }
        editor.commit();
    }

    public static List<OtherTimeInfo> getOtherTime(Context context) {
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        String userMsg = sp.getString("OtherTimeInfo", null);
        List<OtherTimeInfo> mlist = new ArrayList<>();
        if (userMsg == null) {
            return mlist;
        }
        try {
            JSONArray array = new JSONArray(userMsg);
            Gson gson = new Gson();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                OtherTimeInfo userModel = (OtherTimeInfo) gson.fromJson(object.toString(), OtherTimeInfo.class);
                mlist.add(userModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mlist;
    }

    public static void savePlayTime(Context context, String pagekName, int tims) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String ddd = simpleDateFormat.format(date);
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        SharedPreferences.Editor editor = sp.edit();
        String playTimeDD = sp.getString("PlayTimeDD", null);
        if (playTimeDD == null || !playTimeDD.equals(ddd)) {
            editor.remove("PlayTime");
            editor.putString("PlayTimeDD", ddd);
            editor.commit();
        }
        String playTime = sp.getString("PlayTime", null);
        JSONObject object = null;
        if (playTime != null) {
            try {
                object = new JSONObject(playTime);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            object = new JSONObject();
        }
        try {
            object.put(pagekName, tims);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        editor.putString("PlayTime", object.toString());
        editor.commit();
    }

    public static int getPlayTime(Context context, String pagekName) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String ddd = simpleDateFormat.format(date);
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        SharedPreferences.Editor editor = sp.edit();
        String playTimeDD = sp.getString("PlayTimeDD", null);
        if (playTimeDD == null || !playTimeDD.equals(ddd)) {
            editor.remove("PlayTime");
            editor.putString("PlayTimeDD", ddd);
            editor.commit();
        }
        String playTime = sp.getString("PlayTime", null);
        JSONObject object = null;
        if (playTime != null) {
            try {
                object = new JSONObject(playTime);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            object = new JSONObject();
        }
        try {
            int pt = object.getInt(pagekName);
            return pt;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public static void saveParentHoliday(Context context, String msg) {
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        SharedPreferences.Editor editor = sp.edit();
        if (msg == null) {
            editor.remove("ParentHoliday");
        } else {
            editor.putString("ParentHoliday", msg);
        }
        editor.commit();
    }

    public static ParentModelInfo getParentHoliday(Context context) {
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        String userMsg = sp.getString("ParentHoliday", null);
        if (userMsg == null) {
            return null;
        }
        Gson gson = new Gson();
        ParentModelInfo userModel = (ParentModelInfo) gson.fromJson(userMsg, ParentModelInfo.class);
        return userModel;
    }

    public static TeacherModelInfo getTeacher(Context context) {
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        String userMsg = sp.getString("saveTeacher", null);
        if (userMsg == null) {
            return null;
        }
        Log.e("xxxxxx", userMsg);
        Gson gson = new Gson();
        TeacherModelInfo userModel = (TeacherModelInfo) gson.fromJson(userMsg, TeacherModelInfo.class);
        return userModel;
    }

    public static boolean checkOkApp(PackageInfo packageInfo, Context context) {
        String appName = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
        if (appName.toLowerCase().contains("输入法") || packageInfo.packageName.contains("com.xiaomi.cameratest") || packageInfo.packageName.contains("com.miui.contentextension") || packageInfo.packageName.contains("com.miui.qr") || packageInfo.packageName.contains("com.miui.fm") || packageInfo.packageName.contains("com.xiaomi.cameratools") || packageInfo.packageName.contains("com.miui.gallery") || packageInfo.packageName.contains("com.xiaomi.payment") || packageInfo.packageName.contains("com.miui.player") || packageInfo.packageName.contains("com.miui.video") || packageInfo.packageName.contains("com.xiaomi.market") || packageInfo.packageName.contains("com.xiaomi.misettings") || packageInfo.packageName.contains("com.miui.yellowpage")) {
            return true;
        }
        return (packageInfo.packageName.equals(Constants.ANDROID) || packageInfo.packageName.contains("android.") || packageInfo.packageName.contains(".mi") || packageInfo.packageName.contains("miui") || packageInfo.packageName.contains("fido.") || packageInfo.packageName.contains("wapi.") || packageInfo.packageName.contains("xiaomi.") || packageInfo.packageName.contains("op01") || packageInfo.packageName.contains("sdk") || packageInfo.packageName.contains("mediatek") || appName.toLowerCase().contains("service") || appName.toLowerCase().contains("sdk") || appName.toLowerCase().contains("com.") || appName.toLowerCase().contains("system") || appName.toLowerCase().contains("miui")) ? false : true;
    }

    public static boolean checkSysApp(PackageInfo packageInfo, Context context) {
        String appName = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
        return packageInfo.packageName.equals(Constants.ANDROID) || packageInfo.packageName.contains("android.") || appName.toLowerCase().contains("com.") || appName.toLowerCase().contains("system");
    }

    public static boolean checkOkApp(UsePackageInfo packageInfo, Context context) {
        String appName = packageInfo.getmAppName();
        if (appName.toLowerCase().contains("输入法") || packageInfo.getmPackageName().contains("com.xiaomi.cameratest") || packageInfo.getmPackageName().contains("com.miui.contentextension") || packageInfo.getmPackageName().contains("com.miui.qr") || packageInfo.getmPackageName().contains("com.miui.fm") || packageInfo.getmPackageName().contains("com.xiaomi.cameratools") || packageInfo.getmPackageName().contains("com.miui.gallery") || packageInfo.getmPackageName().contains("com.xiaomi.payment") || packageInfo.getmPackageName().contains("com.miui.player") || packageInfo.getmPackageName().contains("com.miui.video") || packageInfo.getmPackageName().contains("com.xiaomi.market") || packageInfo.getmPackageName().contains("com.xiaomi.misettings") || packageInfo.getmPackageName().contains("com.miui.yellowpage")) {
            return true;
        }
        return (packageInfo.getmPackageName().equals(Constants.ANDROID) || packageInfo.getmPackageName().contains("android.") || packageInfo.getmPackageName().contains(".mi") || packageInfo.getmPackageName().contains("miui") || packageInfo.getmPackageName().contains("fido.") || packageInfo.getmPackageName().contains("wapi.") || packageInfo.getmPackageName().contains("xiaomi.") || packageInfo.getmPackageName().contains("op01") || packageInfo.getmPackageName().contains("sdk") || packageInfo.getmPackageName().contains("mediatek") || appName.toLowerCase().contains("service") || appName.toLowerCase().contains("sdk") || appName.toLowerCase().contains("com.") || appName.toLowerCase().contains("system") || appName.toLowerCase().contains("miui")) ? false : true;
    }

    public static void saveBlackUserApp(Context context, String msg) {
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        SharedPreferences.Editor editor = sp.edit();
        if (msg == null) {
            editor.remove("saveBlackUserApp");
        } else {
            editor.putString("saveBlackUserApp", msg);
        }
        editor.commit();
    }

    public static List<AppInfo> getBlackUserApp(Context context) {
        List<AppInfo> list = new ArrayList<>();
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        String userMsg = sp.getString("saveBlackUserApp", null);
        if (userMsg != null) {
            try {
                JSONArray array = new JSONArray(userMsg);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    Gson gson = new Gson();
                    AppInfo userModel = (AppInfo) gson.fromJson(obj.toString(), AppInfo.class);
                    list.add(userModel);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static void saveBlackSysApp(Context context, String msg) {
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        SharedPreferences.Editor editor = sp.edit();
        if (msg == null) {
            editor.remove("saveBlackSysApp");
        } else {
            editor.putString("saveBlackSysApp", msg);
        }
        editor.commit();
    }

    public static List<AppInfo> getBlackSysApp(Context context) {
        List<AppInfo> list = new ArrayList<>();
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        String userMsg = sp.getString("saveBlackSysApp", null);
        if (userMsg != null) {
            try {
                JSONArray array = new JSONArray(userMsg);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    Gson gson = new Gson();
                    AppInfo userModel = (AppInfo) gson.fromJson(obj.toString(),  AppInfo.class);
                    list.add(userModel);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static void saveParentModel(Context context, String msg, int type) {
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        SharedPreferences.Editor editor = sp.edit();
        if (msg == null) {
            editor.remove("ParentModel" + type);
        } else {
            editor.putString("ParentModel" + type, msg);
        }
        editor.commit();
    }

    public static List<WeekModel> getParentModel(Context context, int type) {
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        String userMsg = sp.getString("ParentModel" + type, null);
        List<WeekModel> timelist = new ArrayList<>();
        if (userMsg == null) {
            return timelist;
        }
        try {
            JSONArray data = new JSONArray(userMsg);
            for (int i = 0; i < data.length(); i++) {
                JSONObject object = data.getJSONObject(i);
                WeekModel weekModel = new WeekModel();
                weekModel.setDayTheWeek(object.getString("dayTheWeek"));
                JSONArray timedata = object.getJSONArray("patternTimeScopes");
                for (int j = 0; j < timedata.length(); j++) {
                    JSONObject jobject = timedata.getJSONObject(j);
                    weekModel.getPatternTimeScopes().put(jobject);
                }
                timelist.add(weekModel);
            }
            int i2 = timelist.size();
            if (i2 == 0) {
                for (int i3 = 0; i3 < 7; i3++) {
                    WeekModel weekModel2 = new WeekModel();
                    weekModel2.setDayTheWeek((i3 + 1) + "");
                    timelist.add(weekModel2);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return timelist;
    }

    public static void saveTeacher(Context context, String msg) {
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        SharedPreferences.Editor editor = sp.edit();
        if (msg == null) {
            editor.remove("saveTeacher");
        } else {
            editor.putString("saveTeacher", msg);
        }
        editor.commit();
    }

    public static List<WeekModel> getTeacherModel(Context context) {
        SharedPreferences sp = context.getSharedPreferences("wanplus", 0);
        String userMsg = sp.getString("saveTeacher", null);
        List<WeekModel> timelist = new ArrayList<>();
        if (userMsg == null) {
            return timelist;
        }
        try {
            JSONArray data = new JSONArray(userMsg);
            for (int i = 0; i < data.length(); i++) {
                JSONObject object = data.getJSONObject(i);
                WeekModel weekModel = new WeekModel();
                weekModel.setDayTheWeek(object.getString("dayTheWeek"));
                JSONArray timedata = object.getJSONArray("patternTimeScopes");
                for (int j = 0; j < timedata.length(); j++) {
                    JSONObject jobject = timedata.getJSONObject(j);
                    weekModel.getPatternTimeScopes().put(jobject);
                }
                timelist.add(weekModel);
            }
            int i2 = timelist.size();
            if (i2 == 0) {
                for (int i3 = 0; i3 < 7; i3++) {
                    WeekModel weekModel2 = new WeekModel();
                    weekModel2.setDayTheWeek((i3 + 1) + "");
                    timelist.add(weekModel2);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return timelist;
    }
}
