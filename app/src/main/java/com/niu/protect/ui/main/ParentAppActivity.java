package com.niu.protect.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.niu.protect.R;
import com.niu.protect.adapter.ParentAppAdapter;
import com.niu.protect.manager.UserInfoManager;
import com.niu.protect.model.OtherTimeInfo;
import com.niu.protect.model.UserInfo;
import com.niu.protect.network.NetTools;
import com.niu.protect.network.ResultCallBackListener;
import com.niu.protect.network.StudentBaseUrl;
import com.niu.protect.tools.ILog;
import com.niu.protect.tools.Tools;
import com.niu.protect.ui.base.BaseActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
public class ParentAppActivity extends BaseActivity {
    ListView listview;
    ParentAppAdapter parentAppAdapter;
    List<OtherTimeInfo> packagelist = new ArrayList();
    int intoType = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_app);
        changeTitle("app限制列表");
        showBack();
        this.intoType = getIntent().getIntExtra("intoType", 0);
        this.listview = (ListView) findViewById(R.id.listview);
        ParentAppAdapter parentAppAdapter = new ParentAppAdapter(this, this.packagelist, this.intoType);
        this.parentAppAdapter = parentAppAdapter;
        this.listview.setAdapter((ListAdapter) parentAppAdapter);
        getOrderTimeModel();
    }

    public void getOrderTimeModel() {
        UserInfo userInfo = UserInfoManager.getInstance().getUserInfo(this);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("studentId", userInfo.getId());
        NetTools.getInstance().getAsynHttp(this, StudentBaseUrl.applicationPrograms_general, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    ILog.log(msg);
                    try {
                        String data = msg.getString("data");
                        Log.i("xa==a=dd=", data);
                        Tools.saveOtherTime(ParentAppActivity.this, data);
                        packagelist.clear();
                        packagelist.addAll(Tools.getOtherTime(ParentAppActivity.this));
                        parentAppAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
