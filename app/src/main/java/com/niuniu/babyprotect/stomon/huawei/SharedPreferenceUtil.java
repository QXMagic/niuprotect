package com.niuniu.babyprotect.stomon.huawei;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
public class SharedPreferenceUtil {
    private static final String FILLNAME = "share_data";
    private Context mContext;
    private String mEulaKey;
    private int mVersionCode;
    private static String EULA_PREFIX = "eula_useraccepted_";
    private static SharedPreferences mSharedPreferences = null;

    public SharedPreferenceUtil(Context context) {
        this.mEulaKey = null;
        this.mContext = null;
        this.mContext = context;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.mVersionCode = getVersionCodeInner();
        this.mEulaKey = EULA_PREFIX + this.mVersionCode;
    }

    public boolean hasUserAccepted() {
        return mSharedPreferences.getBoolean(this.mEulaKey, false);
    }

    public void saveUserChoice(boolean accepted) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(this.mEulaKey, accepted);
        editor.commit();
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    private int getVersionCodeInner() {
        PackageInfo pi = null;
        try {
            pi = this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pi.versionCode;
    }

    private static synchronized SharedPreferences getInstance(Context context) {
        SharedPreferences sharedPreferences;
        synchronized (SharedPreferenceUtil.class) {
            if (mSharedPreferences == null) {
                mSharedPreferences = context.getApplicationContext().getSharedPreferences(FILLNAME, 0);
            }
            sharedPreferences = mSharedPreferences;
        }
        return sharedPreferences;
    }

    public static void putString(Context context, String key, String value) {
        getInstance(context).edit().putString(key, value).apply();
    }

    public static String getString(Context context, String key, String defValue) {
        return getInstance(context).getString(key, defValue);
    }

    public static void putBoolean(Context context, String key, Boolean value) {
        getInstance(context).edit().putBoolean(key, value.booleanValue()).apply();
    }

    public static boolean getBoolean(Context context, String key, Boolean defValue) {
        return getInstance(context).getBoolean(key, defValue.booleanValue());
    }

    public static boolean contains(Context context, String key) {
        return getInstance(context).contains(key);
    }
}
