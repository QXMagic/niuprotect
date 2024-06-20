package com.niu.protect.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Toast;

import com.niu.protect.network.NetTools;
import com.niu.protect.network.ResultCallBackListener;
import com.niu.protect.network.StudentBaseUrl;
import com.niu.protect.tools.ILog;
import com.niu.protect.ui.base.BaseActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import com.niu.protect.R;
public class QrScanActivity extends BaseActivity {
    QRCodeView qrCodeView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan);
        changeTitle("扫码");
        showBack();
        //TODO
//        ZXingView zXingView = (ZXingView) findViewById(R.id.zbarview);
//        this.qrCodeView = zXingView;
//        zXingView.setDelegate(new QRCodeView.Delegate() {
//            @Override
//            public void onScanQRCodeSuccess(String result) {
//                vibrate();
//                Toast.makeText(_context, result, Toast.LENGTH_SHORT).show();
//                bindNet(result);
//                qrCodeView.stopSpot();
//            }
//
//            public void onCameraAmbientBrightnessChanged(boolean isDark) {
//                String tipText = qrCodeView.getScanBoxView().getTipText();
//                if (isDark) {
//                    if (!tipText.contains("\n环境过暗，请打开闪光灯")) {
//                        ScanBoxView scanBoxView = qrCodeView.getScanBoxView();
//                        scanBoxView.setTipText(tipText + "\n环境过暗，请打开闪光灯");
//                    }
//                } else if (tipText.contains("\n环境过暗，请打开闪光灯")) {
//                    qrCodeView.getScanBoxView().setTipText(tipText.substring(0, tipText.indexOf("\n环境过暗，请打开闪光灯")));
//                }
//            }
//
//            @Override
//            public void onScanQRCodeOpenCameraError() {
//                Toast.makeText(_context, "错误", Toast.LENGTH_SHORT).show();
//            }
//        });
        this.qrCodeView.startCamera();
    }

    @Override
    public void onStart() {
        super.onStart();
        this.qrCodeView.startCamera();
        this.qrCodeView.startSpotAndShowRect();
    }

    @Override
    public void onStop() {
        this.qrCodeView.stopCamera();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        this.qrCodeView.onDestroy();
        super.onDestroy();
    }

    public void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200L);
    }

    public void bindNet(String msg) {
        showLoad();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("memberId", msg);
        NetTools.getInstance().postAsynHttp(this, StudentBaseUrl.students_qrCodeBind, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg2) {
                if (msg2 != null) {
                    ILog.log(msg2);
                    Toast.makeText(_context, "绑定成功", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}
