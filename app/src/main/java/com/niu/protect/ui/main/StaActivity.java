package com.niu.protect.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.gzuliyujiang.wheelpicker.DatePicker;
import com.github.gzuliyujiang.wheelpicker.contract.OnDatePickedListener;
import com.github.gzuliyujiang.wheelpicker.entity.DateEntity;
import com.github.gzuliyujiang.wheelpicker.widget.DateWheelLayout;
import com.google.gson.Gson;
import com.niu.protect.adapter.StaAppAdapter;
import com.niu.protect.model.AppRecInfo;
import com.niu.protect.model.UserInfo;
import com.niu.protect.network.NetTools;
import com.niu.protect.network.ResultCallBackListener;
import com.niu.protect.network.StudentBaseUrl;
import com.niu.protect.tools.ILog;
import com.niu.protect.tools.Tools;
import com.niu.protect.ui.base.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.niu.protect.core.Constants;
import com.niu.protect.R;
public class StaActivity extends BaseActivity {
    int allTime;
    TextView alltimetxt;
    TextView dataTxt;
    String indexTime;
    int indexType;
    ListView listview;
    List<AppRecInfo> msglist = new ArrayList();
    StaAppAdapter staAppAdapter;
    UserInfo studentInfo;
    TextView weekTxt1;
    TextView weekTxt2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sta_app);
        changeTitle("统计");
        showBack();
        this.studentInfo = (UserInfo) getIntent().getSerializableExtra("studentInfo");
        this.listview = (ListView) findViewById(R.id.listview);
        StaAppAdapter staAppAdapter = new StaAppAdapter(this, this.msglist);
        this.staAppAdapter = staAppAdapter;
        this.listview.setAdapter((ListAdapter) staAppAdapter);
        TextView textView = (TextView) findViewById(R.id.dataTxt);
        this.dataTxt = textView;
        textView.setText(Tools.timeFormat(new Date(), "yyyy-MM-dd"));
        this.indexTime = this.dataTxt.getText().toString();
        this.weekTxt1 = (TextView) findViewById(R.id.weekTxt1);
        this.weekTxt2 = (TextView) findViewById(R.id.weekTxt2);
        this.alltimetxt = (TextView) findViewById(R.id.alltimetxt);
        this.dataTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (indexType == 1) {
                    showTimeDilog();
                } else {
                    loadApp(1);
                }
            }
        });
        this.weekTxt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadApp(2);
            }
        });
        this.weekTxt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadApp(3);
            }
        });
        loadApp(1);
    }

    public void loadApp(int type) {
        this.indexType = type;
        refView(type);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("studentId", this.studentInfo.getId());
        parameters.put("type", type + "");
        if (type == 1) {
            parameters.put("date", this.indexTime);
            this.dataTxt.setText(this.indexTime);
        }
        showLoad();
        NetTools.getInstance().getAsynHttp(this, StudentBaseUrl.appRecords_statistics, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                dissLoad();
                if (msg != null) {
                    ILog.log(msg);
                    msglist.clear();
                    try {
                        JSONArray data = msg.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject object = data.getJSONObject(i);
                            Gson gson = new Gson();
                            AppRecInfo appRecInfo = (AppRecInfo) gson.fromJson(object.toString(),  AppRecInfo.class);
                            msglist.add(appRecInfo);
                        }
                        makeDate();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Tools.showAlert3(_context, e.getMessage());
                    }
                }
            }
        });
    }

    public void showTimeDilog() {
        DatePicker picker = new DatePicker(this);
        picker.setOnDatePickedListener(new OnDatePickedListener() {
            @Override
            public void onDatePicked(int year, int month, int day) {
                StaActivity staActivity = StaActivity.this;
                staActivity.indexTime = year + "";
                if (month > 9) {
                    StaActivity staActivity2 = StaActivity.this;
                    staActivity2.indexTime = indexTime + Constants.ACCEPT_TIME_SEPARATOR_SERVER + month;
                } else {
                    StaActivity staActivity3 = StaActivity.this;
                    staActivity3.indexTime = indexTime + "-0" + month;
                }
                if (day > 9) {
                    StaActivity staActivity4 = StaActivity.this;
                    staActivity4.indexTime = indexTime + Constants.ACCEPT_TIME_SEPARATOR_SERVER + day;
                } else {
                    StaActivity staActivity5 = StaActivity.this;
                    staActivity5.indexTime = indexTime + "-0" + day;
                }
                loadApp(1);
            }
        });
        DateWheelLayout wheelLayout = picker.getWheelLayout();
        wheelLayout.setDateMode(0);
        wheelLayout.setDateLabel("年", "月", "日");
        wheelLayout.setRange(DateEntity.target(2020, 1, 1), DateEntity.today(), DateEntity.today());
        picker.show();
    }

    public void refView(int type) {
        this.dataTxt.setTextSize(14.0f);
        this.weekTxt1.setTextSize(14.0f);
        this.weekTxt2.setTextSize(14.0f);
        if (type == 1) {
            this.dataTxt.setTextSize(18.0f);
        } else if (type == 2) {
            this.weekTxt1.setTextSize(18.0f);
        } else {
            this.weekTxt2.setTextSize(18.0f);
        }
    }

    public void makeDate() {
        this.allTime = 0;
        for (AppRecInfo appRecInfo : this.msglist) {
            this.allTime += appRecInfo.getUseTime();
        }
        this.staAppAdapter.alltime = this.allTime;
        this.staAppAdapter.notifyDataSetChanged();
        int i = this.allTime;
        int hour = (i / 60) / 60;
        int min = (i / 60) % 60;
        String timeStr = hour > 0 ? hour + "时" : "";
        this.alltimetxt.setText(timeStr + min + "分");
    }

    public static void enterStaActivity(Context context, UserInfo userInfo) {
        Intent intent = new Intent(context, StaActivity.class);
        intent.putExtra("studentInfo", userInfo);
        context.startActivity(intent);
    }
}
