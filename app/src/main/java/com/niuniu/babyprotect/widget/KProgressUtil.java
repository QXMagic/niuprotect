package com.niuniu.babyprotect.widget;

import android.content.Context;
import io.github.rupinderjeet.kprogresshud.KProgressHUD;
public class KProgressUtil {
    static KProgressHUD kProgress;

    public static void showLoading(Context context) {
        kProgress = KProgressHUD.create(context).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCornerRadius(6.0f).setSize(75, 75).setCancellable(true).setAnimationSpeed(2).setDimAmount(0.5f).show();
    }

    public static void dismissLoading() {
        KProgressHUD kProgressHUD = kProgress;
        if (kProgressHUD != null && kProgressHUD.isShowing()) {
            kProgress.dismiss();
            kProgress = null;
        }
    }
}
