package com.niu.protect.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.niu.protect.R;
import com.niu.protect.accessibility.auto.device.SystemDeviceInfo;
import com.niu.protect.core.Constants;
import com.niu.protect.manager.UserInfoManager;
import com.niu.protect.model.UserInfo;
import com.niu.protect.network.NetTools;
import com.niu.protect.network.ResultCallBackListener;
import com.niu.protect.network.StudentBaseUrl;
import com.niu.protect.tools.AppInfoUtils;
import com.niu.protect.tools.ILog;
import com.niu.protect.tools.InputCheckUtil;
import com.niu.protect.tools.JumpActivityTools;
import com.niu.protect.tools.Tools;
import com.niu.protect.ui.base.BaseActivity;
import com.niu.protect.ui.main.MainActivity;
import com.niu.protect.ui.webview.OneWebActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class LoginActivity extends BaseActivity {
    private static final int READ_WRITE_SDCARD_PERMISSION_REQUEST_CODE = 0;
    String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.READ_PHONE_STATE", "android.permission.ACCESS_FINE_LOCATION"};
    EditText phonetxt;
    EditText pwdtxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        changeTitle("家长装机登录");
        if (Build.VERSION.SDK_INT >= 30) {
            requestPermissions2();
        } else {
            requestPermissions3();
        }
        this.phonetxt = (EditText) findViewById(R.id.phonetxt);
        this.pwdtxt = (EditText) findViewById(R.id.pwdtxt);
        this.phonetxt.setText(Tools.getUsername(this._context));
        this.pwdtxt.setText(Tools.getPwd(this._context));
        // 注册/忘记密码入口下线：账号由家长在管理后台/面板维护，孩子端不提供
        TextView fortegbtn = (TextView) findViewById(R.id.fortegbtn);
        fortegbtn.setVisibility(View.GONE);
        Button loginbtn = (Button) findViewById(R.id.loginbtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginAction();
            }
        });
        TextView txtbtn1 = (TextView) findViewById(R.id.txtbtn1);
        txtbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, OneWebActivity.class);
                intent.putExtra("jumpUrl", StudentBaseUrl.URL_USER);
                intent.putExtra("title", "用户协议");
                startActivity(intent);
            }
        });
        TextView txtbtn2 = (TextView) findViewById(R.id.txtbtn2);
        txtbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, OneWebActivity.class);
                intent.putExtra("jumpUrl", StudentBaseUrl.URL_YINSI);
                intent.putExtra("title", "隐私政策");
                startActivity(intent);
            }
        });
    }

    public void loginAction() {
        Tools.saveToken(this, null);
        if (!InputCheckUtil.checkAccount(this.phonetxt) || !InputCheckUtil.checkPwd(this.pwdtxt)) {
            return;
        }
        Map<String, String> parameters = new HashMap<>();
        parameters.put("account", this.phonetxt.getText().toString());
        parameters.put("password", this.pwdtxt.getText().toString());
        parameters.put("terminal", "6"); // 安卓 App（likeadmin UserTerminalEnum.ANDROID）
        parameters.put("scene", "1");    // 账号密码登录（likeadmin LoginEnum.ACCOUNT_PASSWORD）
        showLoadText("登录中..");
        NetTools.getInstance().postAsynHttp(this, StudentBaseUrl.user_login, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                dissLoad();
                if (msg != null) {
                    ILog.log(msg);
                    try {
                        JSONObject data = msg.getJSONObject("data");
                        UserInfo userModel = new UserInfo();
                        userModel.setId(data.optString("id"));
                        userModel.setNickName(data.optString("nickname"));
                        userModel.setMobile(data.optString("mobile"));
                        userModel.setImageUrl(data.optString("avatar"));
                        userModel.setToken(data.optString("token"));
                        userModel.setUsername(phonetxt.getText().toString());
                        UserInfoManager.getInstance().saveUser(_context, new Gson().toJson(userModel));
                        Tools.saveToken(_context, userModel.getToken());
                        Tools.saveUserName(_context, phonetxt.getText().toString());
                        Tools.savePwd(_context, pwdtxt.getText().toString());
                        JumpActivityTools.jumpToMainActivity(LoginActivity.this, MainActivity.class, userModel);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Tools.showAlert3(LoginActivity.this, e.getMessage());
                    }
                }
            }
        });
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0 || ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length <= 0 || grantResults[0] != 0 || grantResults[1] != 0) {
                Toast.makeText(this, "读写内存卡内容权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void requestPermissions2() {
        XXPermissions.with(this).permission(Permission.READ_PHONE_STATE).permission("android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION").request(new OnPermissionCallback() {
            @Override
            public void onGranted(List<String> permissions, boolean all) {
            }

            @Override
            public void onDenied(List<String> permissions, boolean never) {
                if (never) {
                    Toast.makeText(LoginActivity.this, "被永久拒绝授权，请手动授予权限", Toast.LENGTH_SHORT).show();
                    XXPermissions.startPermissionActivity((Activity) LoginActivity.this, permissions);
                    return;
                }
                Toast.makeText(LoginActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestPermissions3() {
        XXPermissions.with(this).permission("android.permission.ACCESS_COARSE_LOCATION", "android.permission.READ_PHONE_STATE", "android.permission.ACCESS_FINE_LOCATION").request(new OnPermissionCallback() {
            @Override
            public void onGranted(List<String> permissions, boolean all) {
            }

            @Override
            public void onDenied(List<String> permissions, boolean never) {
                if (never) {
                    Toast.makeText(LoginActivity.this, "被永久拒绝授权，请手动授予权限", Toast.LENGTH_SHORT).show();
                    XXPermissions.startPermissionActivity((Activity) LoginActivity.this, permissions);
                    return;
                }
                Toast.makeText(LoginActivity.this, "获取权限失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
