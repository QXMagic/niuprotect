package com.niuniu.babyprotect.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import im.niu.protect.R;
import com.niuniu.babyprotect.adapter.ParentAppAdapter;
import com.niuniu.babyprotect.manager.UserInfoManager;
import com.niuniu.babyprotect.model.OtherTimeInfo;
import com.niuniu.babyprotect.model.UserInfo;
import com.niuniu.babyprotect.network.NetTools;
import com.niuniu.babyprotect.network.ResultCallBackListener;
import com.niuniu.babyprotect.network.StudentBaseUrl;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.Tools;
import com.niuniu.babyprotect.ui.base.BaseActivity;
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
