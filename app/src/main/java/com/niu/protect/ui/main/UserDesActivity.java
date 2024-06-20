package com.niu.protect.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.google.gson.Gson;
import com.niu.protect.R;
import com.niu.protect.adapter.UserDesAdapter;
import com.niu.protect.model.AppInfo;
import com.niu.protect.model.UserInfo;
import com.niu.protect.network.NetTools;
import com.niu.protect.network.ResultCallBackListener;
import com.niu.protect.network.StudentBaseUrl;
import com.niu.protect.tools.ILog;
import com.niu.protect.tools.Tools;
import com.niu.protect.ui.base.BaseActivity;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class UserDesActivity extends BaseActivity {
    ListView listView;
    JSONArray msglist = new JSONArray();
    UserInfo studentInfo;
    UserDesAdapter userDesAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_des);
        changeTitle("使用详情");
        showBack();
        this.studentInfo = (UserInfo) getIntent().getSerializableExtra("studentInfo");
        this.listView = (ListView) findViewById(R.id.listview);
        UserDesAdapter userDesAdapter = new UserDesAdapter(this, this.msglist);
        this.userDesAdapter = userDesAdapter;
        this.listView.setAdapter((ListAdapter) userDesAdapter);
        loadmsg();
    }

    public void loadmsg() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("studentId", this.studentInfo.getId());
        parameters.put("recordDate", Tools.timeFormat(new Date(), "yyyy-MM-dd"));
        showLoad();
        NetTools.getInstance().getAsynHttp(this, StudentBaseUrl.appUseRecords_appuserecord, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                dissLoad();
                if (msg != null) {
                    ILog.log(msg);
                    try {
                        JSONArray data = msg.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject object = data.getJSONObject(i);
                            String userTime = object.getString("useTime");
                            JSONArray applist = object.getJSONArray("appList");
                            JSONArray alist = new JSONArray();
                            for (int j = 0; j < applist.length(); j++) {
                                Gson gson = new Gson();
                                AppInfo appInfo = (AppInfo) gson.fromJson(applist.get(j).toString(), AppInfo.class);
                                alist.put(appInfo);
                            }
                            JSONObject mlist = new JSONObject();
                            mlist.put("useTime", userTime);
                            mlist.put("appList", alist);
                            msglist.put(mlist);
                        }
                        userDesAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Tools.showAlert3(UserDesActivity.this, e.getMessage());
                    }
                }
            }
        });
    }

    public static void enterUserDesActivity(Context context, UserInfo userInfo) {
        Intent intent = new Intent(context, UserDesActivity.class);
        intent.putExtra("studentInfo", userInfo);
        context.startActivity(intent);
    }
}
