package com.niuniu.babyprotect.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.niuniu.babyprotect.manager.UserInfoManager;
import com.niuniu.babyprotect.model.UserInfo;
import com.niuniu.babyprotect.model.WeekModel;
import com.niuniu.babyprotect.network.NetTools;
import com.niuniu.babyprotect.network.ResultCallBackListener;
import com.niuniu.babyprotect.network.StudentBaseUrl;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.Tools;
import com.niuniu.babyprotect.ui.base.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import im.niu.protect.R;
public class TeacherActivity extends BaseActivity {
    String dayTheWeek;
    WeekModel weekModel;
    List<WeekModel> timelist = new ArrayList();
    int initWeek = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        changeTitle("教师模式");
        showBack();
        this.timelist.addAll(Tools.getTeacherModel(this));
        getTeacherModel();
        changeWeek(nowWeek());
    }

    public void initTimeView() {
        LinearLayout timeview = (LinearLayout) findViewById(R.id.timeview);
        for (int i = 0; i < 5; i++) {
            int tag = i + 200;
            LinearLayout linearLayout = (LinearLayout) timeview.findViewWithTag(tag + "");
            linearLayout.setVisibility(View.GONE);
            TextView timetxttag = (TextView) linearLayout.findViewWithTag("221");
            timetxttag.setText("时间段0" + (i + 1));
            WeekModel weekModel = this.weekModel;
            if (weekModel != null && i < weekModel.getPatternTimeScopes().length()) {
                linearLayout.setVisibility(View.VISIBLE);
                try {
                    JSONObject object = (JSONObject) this.weekModel.getPatternTimeScopes().get(i);
                    TextView timetxt1 = (TextView) linearLayout.findViewWithTag((((i + 1) * 1000) + 1) + "");
                    TextView timetxt2 = (TextView) linearLayout.findViewWithTag((((i + 1) * 1000) + 2) + "");
                    timetxt1.setText(object.getString("startTime"));
                    timetxt2.setText(object.getString("endTime"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                linearLayout.setVisibility(View.GONE);
            }
        }
    }

    public void getTeacherModel() {
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(this);
        if (!userInfo.isBindTeacher()) {
            return;
        }
        Map<String, String> parameters = new HashMap<>();
        NetTools.getInstance().getAsynHttp(this, StudentBaseUrl.patternTeachers_querybyteacherid, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    ILog.log(msg);
                    try {
                        JSONArray data = msg.getJSONObject("data").getJSONArray("patternTeacherSlots");
                        Tools.saveTeacher(_context, data.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void changeWeek(int index) {
        this.dayTheWeek = index + "";
        selectWeekDay();
        initTimeView();
    }

    public void selectWeekDay() {
        if (this.dayTheWeek == null) {
            return;
        }
        this.weekModel = null;
        for (WeekModel weekModel : this.timelist) {
            if (weekModel.getDayTheWeek().equals(this.dayTheWeek)) {
                this.weekModel = weekModel;
            }
        }
    }

    public int nowWeek() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int mWay = c.get(7);
        if (mWay == 1) {
            return 7;
        }
        return mWay - 1;
    }
}
