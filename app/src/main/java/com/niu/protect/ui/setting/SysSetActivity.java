package com.niu.protect.ui.setting;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.niu.protect.R;
import com.niu.protect.core.MyOnClickListener;
import com.niu.protect.model.UserInfo;
import com.niu.protect.network.NetTools;
import com.niu.protect.network.ResultCallBackListener;
import com.niu.protect.network.StudentBaseUrl;
import com.niu.protect.tools.ILog;
import com.niu.protect.tools.Tools;
import com.niu.protect.ui.base.BaseActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.bertsir.zbar.Qr.ScanResult;
import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.QrManager;
public class SysSetActivity extends BaseActivity {
    UserInfo userInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys_set);
        changeTitle("设置");
        showCancel(new MyOnClickListener() {
            @Override
            public void onClick(View btn) {
                Intent backHome = new Intent("android.intent.action.MAIN");
                backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                backHome.addCategory("android.intent.category.HOME");
                startActivity(backHome);
                finish();
            }
        });
        LinearLayout btn1 = (LinearLayout) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.settings.WIFI_SETTINGS");
                startActivity(intent);
            }
        });
        LinearLayout btn2 = (LinearLayout) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.settings.DATA_ROAMING_SETTINGS");
                startActivity(intent);
            }
        });
        LinearLayout btn3 = (LinearLayout) findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.settings.BLUETOOTH_SETTINGS");
                startActivity(intent);
            }
        });
        LinearLayout btn4 = (LinearLayout) findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.settings.NETWORK_OPERATOR_SETTINGS");
                startActivity(intent);
            }
        });
        LinearLayout btn5 = (LinearLayout) findViewById(R.id.btn0);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qxset = Tools.getQxSet(_context);
                if (qxset == 2) {
                    Intent intent = new Intent();
                    intent.setClass(_context, OpenQxActivity.class);
                    _context.startActivity(intent);
                    return;
                }
                bindQRCode();
            }
        });
    }

    public void bindQRCode() {
        QrConfig qrConfig = new QrConfig.Builder().setDesText("(识别二维码)").setShowDes(true).setShowLight(true).setShowTitle(true).setShowAlbum(true).setCornerColor(-1).setLineColor(-1).setLineSpeed(2000).setScanType(1).setScanViewType(1).setCustombarcodeformat(25).setPlaySound(true).setNeedCrop(true).setIsOnlyCenter(true).setTitleText("扫描二维码").setTitleBackgroudColor(Color.parseColor("#7C85FF")).setTitleTextColor(-1).setShowZoom(false).setAutoZoom(false).setFingerZoom(false).setScreenOrientation(1).setDoubleEngine(false).setOpenAlbumText("选择要识别的图片").setLooperScan(false).setLooperWaitTime(5000).setScanLineStyle(1).setAutoLight(false).setShowVibrator(false).create();
        QrManager.getInstance().init(qrConfig).startScan(this, new QrManager.OnScanResultCallback() {
            @Override
            public void onScanSuccess(ScanResult result) {
                bindNet(result.getContent());
            }
        });
    }

    public void bindNet(String msg) {
        showLoad();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("memberId", msg);
        NetTools.getInstance().postAsynHttp(this, StudentBaseUrl.students_qrCodeBind, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg2) {
                dissLoad();
                if (msg2 != null) {
                    ILog.log(msg2);
                    Toast.makeText(_context, "绑定成功", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.setClass(_context, OpenQxActivity.class);
                    _context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            Intent backHome = new Intent("android.intent.action.MAIN");
            backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            backHome.addCategory("android.intent.category.HOME");
            startActivity(backHome);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
