package com.niu.protect.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.niu.protect.BabyApplication;
import com.niu.protect.model.ParentLimiteTimeModel;
import com.niu.protect.model.ParentLimteTimeByAppModel;
import com.niu.protect.model.TeacherLimiteTimeModel;
import com.niu.protect.tools.SharedPreUtil;
public class StudentControlTimeLimitManager {
    public static StudentControlTimeLimitManager instance;
    ParentLimiteTimeModel mParentLimiteHoliday;
    ParentLimiteTimeModel mParentLimiteSchool;
    ParentLimteTimeByAppModel mParentLimteTimeByAppModel;
    TeacherLimiteTimeModel mTeacherLimiteTimeModel;

    public static final class BindType {
        public static final int BOTH_SCHOOL = 5;
        public static final int PARENT_HOLIDAY = 2;
        public static final int PARENT_SCHOOL = 1;
        public static final int TEACHER_HOLIDAY = 4;
        public static final int TEACHER_SCHOOL = 3;
    }

    public static final class ControllerWay {
        public static final int HOLIDAY = 2;
        public static final int SCHOOL = 1;
    }

    public void clearData() {
        this.mParentLimiteHoliday = null;
        this.mParentLimiteSchool = null;
        this.mParentLimteTimeByAppModel = null;
        this.mTeacherLimiteTimeModel = null;
    }

    private StudentControlTimeLimitManager() {
    }

    public static StudentControlTimeLimitManager getInstance() {
        if (instance == null) {
            synchronized (StudentControlTimeLimitManager.class) {
                if (instance == null) {
                    instance = new StudentControlTimeLimitManager();
                }
            }
        }
        return instance;
    }

    private int getValue(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SharedPreManager.SP_NAME, 0);
        return sp.getInt(key, 0);
    }

    private void saveValue(Context context, String key, int value) {
        SharedPreferences sp = context.getSharedPreferences(SharedPreManager.SP_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public ParentLimiteTimeModel getParentLimiteHoliday() {
        if (this.mParentLimiteHoliday == null) {
            String parentLimteMsg = (String) SharedPreUtil.getParam(BabyApplication.getInstance(), SharedPreManager.KEY_PARENT_HOLIDAY, "");
            if (!TextUtils.isEmpty(parentLimteMsg)) {
                this.mParentLimiteHoliday = (ParentLimiteTimeModel) new Gson().fromJson(parentLimteMsg, ParentLimiteTimeModel.class);
            }
        }
        return this.mParentLimiteHoliday;
    }

    public ParentLimiteTimeModel getmParentLimiteSchool() {
        if (this.mParentLimiteSchool == null) {
            String parentLimteMsg = (String) SharedPreUtil.getParam(BabyApplication.getInstance(), SharedPreManager.KEY_PARENT_SCHOOL, "");
            if (!TextUtils.isEmpty(parentLimteMsg)) {
                this.mParentLimiteSchool = (ParentLimiteTimeModel) new Gson().fromJson(parentLimteMsg, ParentLimiteTimeModel.class);
            }
        }
        return this.mParentLimiteSchool;
    }

    public ParentLimteTimeByAppModel getmParentLimteTimeByAppModel() {
        if (this.mParentLimteTimeByAppModel == null) {
            String parentLimteMsg = (String) SharedPreUtil.getParam(BabyApplication.getInstance(), SharedPreManager.KEY_OTHER_LIMIT_TIME, "");
            if (!TextUtils.isEmpty(parentLimteMsg)) {
                this.mParentLimteTimeByAppModel = (ParentLimteTimeByAppModel) new Gson().fromJson(parentLimteMsg,ParentLimteTimeByAppModel.class);
            }
        }
        return this.mParentLimteTimeByAppModel;
    }

    public TeacherLimiteTimeModel getmTeacherLimiteTimeModel() {
        if (this.mTeacherLimiteTimeModel == null) {
            String parentLimteMsg = (String) SharedPreUtil.getParam(BabyApplication.getInstance(), SharedPreManager.KEY_CONTROL_TEACHER, "");
            if (!TextUtils.isEmpty(parentLimteMsg)) {
                this.mTeacherLimiteTimeModel = (TeacherLimiteTimeModel) new Gson().fromJson(parentLimteMsg, TeacherLimiteTimeModel.class);
            }
        }
        return this.mTeacherLimiteTimeModel;
    }
}
