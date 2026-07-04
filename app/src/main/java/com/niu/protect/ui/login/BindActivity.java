package com.niu.protect.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.niu.protect.R;
import com.niu.protect.accessibility.auto.device.SystemDeviceInfo;
import com.niu.protect.manager.DeviceIdManager;
import com.niu.protect.manager.UserInfoManager;
import com.niu.protect.model.UserInfo;
import com.niu.protect.network.NetTools;
import com.niu.protect.network.ResultCallBackListener;
import com.niu.protect.network.StudentBaseUrl;
import com.niu.protect.tools.ILog;
import com.niu.protect.tools.JumpActivityTools;
import com.niu.protect.tools.ToastUtil;
import com.niu.protect.tools.Tools;
import com.niu.protect.ui.base.BaseActivity;
import com.niu.protect.ui.main.MainActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 设备绑定页（替代登录页作为未绑定时的入口）。
 *
 * 家长在管理面板生成 6 位绑定码 → 孩子端在此输码 → 服务端下发 device_token。
 * 孩子端从此不持有账号密码，只有设备级凭证。
 */
public class BindActivity extends BaseActivity {

    private EditText codetxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind);
        changeTitle("绑定设备");
        this.codetxt = (EditText) findViewById(R.id.codetxt);
        Button bindbtn = (Button) findViewById(R.id.bindbtn);
        bindbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bindAction();
            }
        });
    }

    private void bindAction() {
        String code = this.codetxt.getText().toString().trim();
        if (TextUtils.isEmpty(code) || code.length() != 6) {
            ToastUtil.show("请输入 6 位绑定码");
            return;
        }
        String deviceId = DeviceIdManager.getInstance().getDeviceId();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("code", code);
        parameters.put("device_id", deviceId);
        parameters.put("brand", SystemDeviceInfo.getBrand());
        parameters.put("model", SystemDeviceInfo.getModel());
        parameters.put("os", SystemDeviceInfo.getPhoneOs());
        showLoadText("绑定中..");
        NetTools.getInstance().postAsynHttp(this, StudentBaseUrl.DEVICE_BIND, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                dissLoad();
                if (msg == null) {
                    return;
                }
                ILog.log(msg);
                JSONObject data = msg.optJSONObject("data");
                if (data == null) {
                    ToastUtil.show("绑定失败");
                    return;
                }
                String deviceToken = data.optString("device_token");
                if (TextUtils.isEmpty(deviceToken)) {
                    ToastUtil.show("绑定失败：凭证为空");
                    return;
                }
                DeviceIdManager.getInstance().saveDeviceToken(deviceToken);

                // 下游大量代码读取 UserInfo.getId() 作为设备标识，这里用 device_id 填充，
                // 使 WebSocket uid 与后续接口调用保持一致。
                UserInfo userModel = new UserInfo();
                userModel.setId(data.optString("device_id", deviceId));
                userModel.setToken(deviceToken);
                UserInfoManager.getInstance().saveUser(_context, new Gson().toJson(userModel));
                Tools.saveToken(_context, deviceToken);

                ToastUtil.show("绑定成功");
                JumpActivityTools.jumpToMainActivity(BindActivity.this, MainActivity.class, userModel);
                finish();
            }
        });
    }
}
