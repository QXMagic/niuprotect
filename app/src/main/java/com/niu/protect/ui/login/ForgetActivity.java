package com.niu.protect.ui.login;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.niu.protect.R;
import com.niu.protect.core.Constants;
import com.niu.protect.network.NetTools;
import com.niu.protect.network.ResultCallBackListener;
import com.niu.protect.network.StudentBaseUrl;
import com.niu.protect.tools.ILog;
import com.niu.protect.tools.InputCheckUtil;
import com.niu.protect.tools.Tools;
import com.niu.protect.ui.base.BaseActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
public class ForgetActivity extends BaseActivity {
    TextView codebtn;
    EditText codetxt;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 100) {
                codebtn.setTextColor(-3355444);
                codebtn.setEnabled(false);
                codebtn.setText(msg.obj.toString());
            } else if (msg.what == 101) {
                codebtn.setTextColor(Color.parseColor("#436edb"));
                codebtn.setEnabled(true);
                codebtn.setText("获取验证码");
            }
            super.handleMessage(msg);
        }
    };
    EditText phonetxt;
    EditText pwdtxt;
    EditText pwdtxt1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        changeTitle("忘记密码");
        showBack();
        this.phonetxt = (EditText) findViewById(R.id.phonetxt);
        this.pwdtxt = (EditText) findViewById(R.id.pwdtxt);
        this.pwdtxt1 = (EditText) findViewById(R.id.pwdtxt1);
        this.codetxt = (EditText) findViewById(R.id.codetxt);
        TextView textView = (TextView) findViewById(R.id.codebtn);
        this.codebtn = textView;
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeAction();
            }
        });
        Button pwdbtn = (Button) findViewById(R.id.pwdbtn);
        pwdbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwdAction();
            }
        });
    }

    public void codeAction() {
        if (this.phonetxt.getText() == null || this.phonetxt.getText().toString().equals("")) {
            Tools.showAlert3(getApplication(), "手机号不能为空");
            return;
        }
        getCode();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("phone", this.phonetxt.getText().toString());
        NetTools.getInstance().postAsynHttp(this, "v1/members/sendVCode", parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                if (msg != null) {
                    ILog.log(msg);
                }
            }
        });
    }

    public void pwdAction() {
        if (!InputCheckUtil.checkPhoneNo(this.phonetxt) || !InputCheckUtil.checkPwd(this.pwdtxt)) {
            return;
        }
        if (this.pwdtxt1.getText() == null || this.pwdtxt1.getText().toString().equals("")) {
            Tools.showAlert3(getApplication(), "密码不能为空");
        } else if (!this.pwdtxt1.getText().toString().equals(this.pwdtxt.getText().toString())) {
            Tools.showAlert3(getApplication(), "密码不一致");
        } else if (!InputCheckUtil.checkSMSCode(this.codetxt)) {
        } else {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("username", this.phonetxt.getText().toString());
            parameters.put("password", this.pwdtxt.getText().toString());
            parameters.put(Constants.KEY_HTTP_CODE, this.codetxt.getText().toString());
            NetTools.getInstance().postAsynHttp(this, StudentBaseUrl.user_changePwd, parameters, new ResultCallBackListener() {
                @Override
                public void onResponse(JSONObject msg) {
                    if (msg != null) {
                        Tools.showAlert3(ForgetActivity.this, "密码修改成功，请重新登录");
                        finish();
                    }
                }
            });
        }
    }

    public void getCode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int time = 60;
                while (time >= 0) {
                    Message msg = new Message();
                    msg.what = 100;
                    msg.obj = "等待" + time + "秒";
                    handler.sendMessage(msg);
                    time += -1;
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.sendEmptyMessage(101);
            }
        }).start();
    }
}
