package com.niuniu.babyprotect.repository;

import android.content.Context;
import im.niu.protect.R;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;
public abstract class BaseRepository {
    ZLoadingDialog dialog;

    public void showLoad(Context context) {
        ZLoadingDialog zLoadingDialog = new ZLoadingDialog(context, R.style.loading_dialog);
        this.dialog = zLoadingDialog;
        zLoadingDialog.setLoadingBuilder(Z_TYPE.SINGLE_CIRCLE).setCanceledOnTouchOutside(true).setDialogBackgroundColor(0).setHintTextSize(16.0f).setHintTextColor(-12303292).setDurationTime(0.5d).show();
    }

    public void dissLoad() {
        ZLoadingDialog zLoadingDialog = this.dialog;
        if (zLoadingDialog != null) {
            zLoadingDialog.dismiss();
            this.dialog = null;
        }
    }
}
