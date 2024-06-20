package com.niu.protect.ui.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.niu.protect.R;
import com.niu.protect.network.NetTools;
import com.niu.protect.network.ResultCallBackListener;
import com.niu.protect.network.StudentBaseUrl;
import com.niu.protect.tools.ILog;
import com.niu.protect.tools.Tools;
import com.niu.protect.ui.base.BaseActivity;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
public class FeedBackActivity extends BaseActivity {
    EditText contxt;
    EditText phonetxt;
    Button subbtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        changeTitle("问题反馈");
        showBack();
        this.phonetxt = (EditText) findViewById(R.id.phonetxt);
        this.contxt = (EditText) findViewById(R.id.contxt);
        Button button = (Button) findViewById(R.id.subbtn);
        this.subbtn = button;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subAction();
            }
        });
    }

    public void subAction() {
        if (this.phonetxt.getText() == null || this.phonetxt.getText().toString().equals("")) {
            Tools.showAlert3(getApplication(), "请输入联系方式");
        } else if (this.contxt.getText() == null || this.contxt.getText().toString().equals("")) {
            Tools.showAlert3(getApplication(), "请输入内容");
        } else {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("contactInformation", this.phonetxt.getText().toString());
            parameters.put("content", this.contxt.getText().toString());
            parameters.put("type", "1");
            showLoad();
            NetTools.getInstance().postAsynHttp(this, StudentBaseUrl.feedbacks_add, parameters, new ResultCallBackListener() {
                @Override
                public void onResponse(JSONObject msg) {
                    dissLoad();
                    if (msg != null) {
                        ILog.log(msg);
                        Tools.showAlert3(FeedBackActivity.this, "提交成功");
                        finish();
                    }
                }
            });
        }
    }
}
