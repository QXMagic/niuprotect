package com.niu.protect.tools;

import android.app.Activity;
import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.QrManager;
import com.niu.protect.R;
public class QRCodeUtil {
    public static void bindQRCode(Activity context, QrManager.OnScanResultCallback callback) {
        QrConfig qrConfig = new QrConfig.Builder().setDesText("(识别二维码)").setShowDes(true).setShowLight(true).setShowTitle(true).setShowAlbum(true).setCornerColor(-1).setLineColor(-1).setLineSpeed(2000).setScanType(1).setScanViewType(1).setCustombarcodeformat(25).setPlaySound(true).setNeedCrop(true).setIsOnlyCenter(true).setTitleText("扫描二维码").setTitleBackgroudColor(R.color.colorAccent).setTitleTextColor(-1).setShowZoom(false).setAutoZoom(false).setFingerZoom(false).setScreenOrientation(1).setDoubleEngine(false).setOpenAlbumText("选择要识别的图片").setLooperScan(false).setLooperWaitTime(5000).setScanLineStyle(1).setAutoLight(false).setShowVibrator(false).create();
        QrManager.getInstance().init(qrConfig).startScan(context, callback);
    }
}
