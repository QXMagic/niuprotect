package com.niuniu.babyprotect.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.niuniu.babyprotect.manager.UserInfoManager;
import com.niuniu.babyprotect.model.UserInfo;
import com.niuniu.babyprotect.network.NetTools;
import com.niuniu.babyprotect.network.ResultCallBackListener;
import com.niuniu.babyprotect.network.StudentBaseUrl;
import com.niuniu.babyprotect.tools.AppInfoUtils;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.InputCheckUtil;
import com.niuniu.babyprotect.tools.Tools;
import com.niuniu.babyprotect.ui.base.BaseActivity;
import com.niuniu.babyprotect.ui.webview.OneWebActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import atmp.consts.Constants;
import im.niu.protect.R;
public class RegActivity extends BaseActivity {
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
        setContentView(R.layout.activity_reg);
        changeTitle("注册");
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
        Button regbtn = (Button) findViewById(R.id.regbtn);
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regAction();
            }
        });
        TextView txtbtn1 = (TextView) findViewById(R.id.txtbtn1);
        txtbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegActivity.this, OneWebActivity.class);
                intent.putExtra("jumpUrl", "http://114.55.1.93:9666/stu-mob-html/regUserAgree.html");
                intent.putExtra("title", "用户协议");
                startActivity(intent);
            }
        });
        TextView txtbtn2 = (TextView) findViewById(R.id.txtbtn2);
        txtbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegActivity.this, OneWebActivity.class);
                intent.putExtra("jumpUrl", "http://114.55.1.93:9666/stu-mob-html/privacyPolicy.html");
                intent.putExtra("title", "隐私政策");
                startActivity(intent);
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

    public void regAction() {
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
            parameters.put("appDownChannel", AppInfoUtils.getChannel(this));
            parameters.put("versionCode", AppInfoUtils.getVersionName(this));
            parameters.put("memberType", Constants.MSG_DB_READY_REPORT);
            showLoadText("注册中...");
            NetTools.getInstance().postAsynHttp(this, StudentBaseUrl.user_register, parameters, new ResultCallBackListener() {
                @Override
                public void onResponse(JSONObject msg) {
                    dissLoad();
                    if (msg != null) {
                        ILog.log(msg);
                        try {
                            String data = msg.getString("data");
                            Gson gson = new Gson();
                            UserInfo userModel = (UserInfo) gson.fromJson(data, UserInfo.class);
                            UserInfoManager.getInstance().saveUser(RegActivity.this, data);
                            Tools.saveToken(RegActivity.this, userModel.getToken());
                            Intent intent = new Intent(RegActivity.this, RegMsgActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Tools.showAlert3(RegActivity.this, e.getMessage());
                        }
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
