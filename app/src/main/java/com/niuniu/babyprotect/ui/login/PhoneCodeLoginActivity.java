package com.niuniu.babyprotect.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import com.google.gson.Gson;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import im.niu.protect.R;
import com.niuniu.babyprotect.accessibility.auto.device.SystemDeviceInfo;
import com.niuniu.babyprotect.manager.UserInfoManager;
import com.niuniu.babyprotect.model.BaseModel;
import com.niuniu.babyprotect.model.UserInfo;
import com.niuniu.babyprotect.network.NetTools;
import com.niuniu.babyprotect.network.ResultCallBackListener;
import com.niuniu.babyprotect.network.StudentBaseUrl;
import com.niuniu.babyprotect.tools.AppInfoUtils;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.InputCheckUtil;
import com.niuniu.babyprotect.tools.JumpActivityTools;
import com.niuniu.babyprotect.tools.ToastUtil;
import com.niuniu.babyprotect.tools.Tools;
import com.niuniu.babyprotect.ui.base.BaseActivity;
import com.niuniu.babyprotect.ui.webview.OneWebActivity;
import com.taobao.accs.common.Constants;
//import com.tencent.bugly.crashreport.crash.BuglyBroadcastRecevier;
import java.util.HashMap;
import java.util.Map;
import org.android.agoo.message.MessageService;
import org.json.JSONException;
import org.json.JSONObject;
public class PhoneCodeLoginActivity extends BaseActivity {
    private Button btnGetCode;
    CheckBox ckPrivate;
    boolean isMchecked = false;
    CountDownTimer mCountDownTimer;
    EditText phonetxt;
    EditText pwdPhoneCode;
    private TextView tvNotLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone_code);
        changeTitle("3985学生端");
        CheckBox checkBox = (CheckBox) findViewById(R.id.cb_xieyi);
        this.ckPrivate = checkBox;
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isMchecked = isChecked;
            }
        });
        this.phonetxt = (EditText) findViewById(R.id.phonetxt);
        this.pwdPhoneCode = (EditText) findViewById(R.id.pwdPhoneCode);
        this.phonetxt.setText(Tools.getUsername(this._context));
        Button loginbtn = (Button) findViewById(R.id.btn_login);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginByCode(false);
            }
        });
        TextView txtbtn1 = (TextView) findViewById(R.id.tv_user);
        txtbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhoneCodeLoginActivity.this, OneWebActivity.class);
                intent.putExtra("jumpUrl", StudentBaseUrl.URL_USER);
                intent.putExtra("title", "用户协议");
                startActivity(intent);
            }
        });
        TextView txtbtn2 = (TextView) findViewById(R.id.tv_private);
        txtbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhoneCodeLoginActivity.this, OneWebActivity.class);
                intent.putExtra("jumpUrl", StudentBaseUrl.URL_YINSI);
                intent.putExtra("title", "隐私政策");
                startActivity(intent);
            }
        });
        Button button = (Button) findViewById(R.id.btn_getcode);
        this.btnGetCode = button;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCode();
            }
        });
    }

    public void sendCode() {
        if (!this.isMchecked) {
            ToastUtil.show("请先勾选阅读用户协议和隐私协议");
        } else if (!InputCheckUtil.checkPhoneNo(this.phonetxt)) {
        } else {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("phone", this.phonetxt.getText().toString());
            showLoad();
            NetTools.getInstance().postAsynHttp(this, "v1/members/sendVCode", parameters, new ResultCallBackListener() {
                @Override
                public void onResponse(JSONObject msg) {
                    dissLoad();
                    if (msg != null) {
                        ToastUtils.showLongToast(_context, "发送验证码成功");
                        BaseModel bean = (BaseModel) new Gson().fromJson(msg.toString(),  BaseModel.class);
                        if (bean.getStatus().equals("200")) {
                            codeStart();
                        }
                    }
                }
            });
        }
    }

    public void loginByCode(boolean notLogin) {
        if (!InputCheckUtil.checkPhoneNo(this.phonetxt) || !InputCheckUtil.checkSMSCode(this.pwdPhoneCode)) {
            return;
        }
        if (!this.isMchecked && !notLogin) {
            ToastUtil.show("请先勾选阅读用户协议和隐私协议");
        } else {
            login(this.phonetxt.getText().toString(), this.pwdPhoneCode.getText().toString());
        }
    }

    private void login(String phone, String code) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("username", phone);
        parameters.put(Constants.KEY_HTTP_CODE, code);
        parameters.put("appDownChannel", AppInfoUtils.getChannel(this));
        parameters.put("versionCode", AppInfoUtils.getVersionName(this));
        parameters.put("memberType", MessageService.MSG_DB_READY_REPORT);
        parameters.put("mobileBrand", SystemDeviceInfo.getBrand());
        parameters.put("mobileModel", SystemDeviceInfo.getModel());
        parameters.put("systemVersion", SystemDeviceInfo.getPhoneOs());
        showLoad();
        NetTools.getInstance().postAsynHttp(this, StudentBaseUrl.USER_CODE_LOGIN, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                dissLoad();
                if (msg != null) {
                    ILog.log(msg);
                    try {
                        String data = msg.getString("data");
                        Gson gson = new Gson();
                        UserInfo userModel = (UserInfo) gson.fromJson(data, UserInfo.class);
                        UserInfoManager.getInstance().saveUser(_context, data);
                        Tools.saveToken(_context, userModel.getToken());
                        Tools.saveUserName(_context, phonetxt.getText().toString());
                        JumpActivityTools.jumpToMainActivity(PhoneCodeLoginActivity.this, userModel);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Tools.showAlert3(PhoneCodeLoginActivity.this, e.getMessage());
                    }
                }
            }
        });
    }

    public void codeStart() {
        this.btnGetCode.setEnabled(false);
        CountDownTimer countDownTimer = this.mCountDownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        this.mCountDownTimer = new CountDownTimer(60, 1000L) {
            @Override
            public void onTick(long l) {
                btnGetCode.setEnabled(false);
                Button button = btnGetCode;
                button.setText((l / 1000) + "s后重新获取");
            }

            @Override
            public void onFinish() {
                btnGetCode.setText("重新获取验证码");
                btnGetCode.setEnabled(true);
            }
        }.start();
    }
}
