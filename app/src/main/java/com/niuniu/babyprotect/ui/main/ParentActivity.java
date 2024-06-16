package com.niuniu.babyprotect.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import im.niu.protect.R;
import com.niuniu.babyprotect.manager.UserInfoManager;
import com.niuniu.babyprotect.model.UserInfo;
import com.niuniu.babyprotect.model.WeekModel;
import com.niuniu.babyprotect.tools.Tools;
import com.niuniu.babyprotect.ui.base.BaseActivity;
import com.umeng.analytics.AnalyticsConfig;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
public class ParentActivity extends BaseActivity {
    List<WeekModel> timelist = new ArrayList();
    TextView userModel;
    WeekModel weekModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);
        changeTitle("家长模式");
        showBack();
        this.userModel = (TextView) findViewById(R.id.userModel);
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(this);
        if (userInfo.getParentPattern() == 2) {
            this.timelist.addAll(Tools.getParentModel(this, 2));
            this.userModel.setText("假期模式");
        } else {
            this.timelist.addAll(Tools.getParentModel(this, 1));
            this.userModel.setText("上学模式");
        }
        Iterator<WeekModel> it = this.timelist.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            WeekModel weekModel = it.next();
            if (weekModel.checkNowTime()) {
                this.weekModel = weekModel;
                break;
            }
        }
        initTimeView();
        TextView appbtn = (TextView) findViewById(R.id.appbtn);
        appbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(_context, ParentAppActivity.class);
                UserInfo userInfo2 = UserInfoManager.getInstance().getUserInfo(_context);
                if (userInfo2.getParentPattern() == 2) {
                    intent.putExtra("intoType", 3);
                    userModel.setText("假期模式");
                } else {
                    intent.putExtra("intoType", 1);
                    userModel.setText("上学模式");
                }
                _context.startActivity(intent);
            }
        });
    }

    public void initTimeView() {
        if (this.weekModel == null) {
            return;
        }
        LinearLayout timeview = (LinearLayout) findViewById(R.id.timeview);
        for (int i = 0; i < 5; i++) {
            int tag = i + 200;
            LinearLayout linearLayout = (LinearLayout) timeview.findViewWithTag(tag + "");
            TextView timetxttag = (TextView) linearLayout.findViewWithTag("221");
            timetxttag.setText("时间段0" + (i + 1));
            if (i < this.weekModel.getPatternTimeScopes().length()) {
                linearLayout.setVisibility(View.VISIBLE);
                try {
                    JSONObject object = (JSONObject) this.weekModel.getPatternTimeScopes().get(i);
                    TextView timetxt1 = (TextView) linearLayout.findViewWithTag((((i + 1) * 1000) + 1) + "");
                    TextView timetxt2 = (TextView) linearLayout.findViewWithTag((((i + 1) * 1000) + 2) + "");
                    timetxt1.setText(object.getString(AnalyticsConfig.RTD_START_TIME));
                    timetxt2.setText(object.getString("endTime"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                linearLayout.setVisibility(View.GONE);
            }
        }
    }
}
