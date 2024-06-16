package com.niuniu.babyprotect.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class SharedPreUtil {
    private static final String FILE_NAME = "sp_protect";
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;

    public SharedPreUtil(Context mContext, String preferenceName) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(preferenceName, 0);
        this.preferences = sharedPreferences;
        this.editor = sharedPreferences.edit();
    }

    public <T> void setDataList(String tag, List<T> datalist) {
        if (datalist == null) {
            return;
        }
        Gson gson = new Gson();
        String strJson = gson.toJson(datalist);
        this.editor.clear();
        this.editor.putString(tag, strJson);
        this.editor.commit();
    }

    public <T> List<T> getDataList(String tag, Class<T> cls) {
        ArrayList arrayList = new ArrayList();
        String strJson = this.preferences.getString(tag, null);
        if (strJson == null) {
            return arrayList;
        }
        try {
            Gson gson = new Gson();
            JsonArray array = JsonParser.parseString(strJson).getAsJsonArray();
            Iterator<JsonElement> it = array.iterator();
            while (it.hasNext()) {
                JsonElement jsonElement = it.next();
                arrayList.add(gson.fromJson(jsonElement, (Class<Object>) cls));
            }
        } catch (Exception e) {
            Log.e("--sp保存data--", "Exception : " + e.getMessage());
        }
        return arrayList;
    }

    public <T> void setData(String tag, T data) {
        if (data == null) {
            return;
        }
        Gson gson = new Gson();
        String strJson = gson.toJson(data);
        this.editor.clear();
        this.editor.putString(tag, strJson);
        this.editor.commit();
    }

    public <T> T getData(String tag, Class<T> cls) {
        String strJson = this.preferences.getString(tag, null);
        if (strJson == null) {
            return null;
        }
        try {
            Gson gson = new Gson();
            JsonElement jsonElement = new JsonParser().parse(strJson);
            T data = (T) gson.fromJson(jsonElement, (Class<Object>) cls);
            return data;
        } catch (Exception e) {
            Log.e("--sp获取data--", "Exception : " + e.getMessage());
            return null;
        }
    }

    public static boolean setParam(Context context, String key, Object object) {
        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences("sp_protect", 0);
        SharedPreferences.Editor editor = sp.edit();
        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, ((Integer) object).intValue());
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, ((Boolean) object).booleanValue());
        } else if ("Float".equals(type)) {
            editor.putFloat(key, ((Float) object).floatValue());
        } else if ("Long".equals(type)) {
            editor.putLong(key, ((Long) object).longValue());
        }
        return editor.commit();
    }

    public static Object getParam(Context context, String key, Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences("sp_protect", 0);
        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        }
        if ("Integer".equals(type)) {
            return Integer.valueOf(sp.getInt(key, ((Integer) defaultObject).intValue()));
        }
        if ("Boolean".equals(type)) {
            return Boolean.valueOf(sp.getBoolean(key, ((Boolean) defaultObject).booleanValue()));
        }
        if ("Float".equals(type)) {
            return Float.valueOf(sp.getFloat(key, ((Float) defaultObject).floatValue()));
        }
        if ("Long".equals(type)) {
            return Long.valueOf(sp.getLong(key, ((Long) defaultObject).longValue()));
        }
        return null;
    }

    public static void clearSaveApp(Context context, String fileName) {
        SharedPreferences sp = context.getSharedPreferences(fileName, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().commit();
    }

    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences("sp_protect", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().commit();
    }

    public static void clearAll(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("sp_protect", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    public static void setSharedPreference(String key, String[] values, Context context) {
        String str = "";
        SharedPreferences sp = context.getSharedPreferences("data", 0);
        if (values != null && values.length > 0) {
            for (String value : values) {
                str = (str + value) + "#";
            }
            SharedPreferences.Editor et = sp.edit();
            et.putString(key, str);
            et.commit();
        }
    }

    public static String[] getSharedPreference(String key, Context context) {
        SharedPreferences sp = context.getSharedPreferences("data", 0);
        String values = sp.getString(key, "");
        String[] str = values.split("#");
        return str;
    }
}
