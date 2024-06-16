package com.niuniu.babyprotect.ui.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.niuniu.babyprotect.action.MyOnClickListener;
import com.niuniu.babyprotect.ui.NavigationBar;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import im.niu.protect.R;
public class BaseActivity extends AppCompatActivity {
    public Context _context;
    public NavigationBar bar;
    private Dialog mLoadDialog;
    int REQUEST_CAMERA_CODE = 200;
    int REQUEST_CODE = 201;
    public String imagePath = "";
    private AtomicInteger progressDialogCount = new AtomicInteger(0);
    ZLoadingDialog dialog = new ZLoadingDialog(this, R.style.loading_dialog);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this._context = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        getBar();
    }

    public void showlog(String msg) {
        Log.i("duke=====", msg);
    }

    public void getBar() {
        if (this.bar == null) {
            this.bar = (NavigationBar) findViewById(R.id.navigationbar);
        }
    }

    public void changeTitle(String title) {
        getBar();
        this.bar.setTitle(title);
    }

    public void showBack() {
        getBar();
        this.bar.showLeftbtn(0);
        this.bar.setLeftOnClickListener(new MyOnClickListener() {
            @Override
            public void onClick(View btn) {
                backAction();
            }
        });
    }

    public void showBack2() {
        getBar();
        this.bar.showLeftbtn(2);
        this.bar.setLeft3OnClickListener(new MyOnClickListener() {
            @Override
            public void onClick(View btn) {
                backAction();
            }
        });
    }

    public void showCancel(MyOnClickListener click) {
        getBar();
        this.bar.showLeftbtn(1);
        this.bar.setLeft2OnClickListener(click);
    }

    public void showMine(MyOnClickListener click) {
        getBar();
        this.bar.showLeftbtn(4);
        this.bar.setLeft4OnClickListener(click);
    }

    public void showMsg(MyOnClickListener click) {
        getBar();
        this.bar.showRightbtn(5);
        this.bar.setRight4OnClickListener(click);
    }

    public void showShare(MyOnClickListener click) {
        getBar();
        this.bar.showRightbtn(1);
        this.bar.setShareIcon(true);
        this.bar.setRight2OnClickListener(click);
    }

    public void showBack(MyOnClickListener click) {
        getBar();
        this.bar.showLeftbtn(0);
        this.bar.setLeftOnClickListener(click);
    }

    public void showRightText(String msg, MyOnClickListener click) {
        getBar();
        this.bar.setRightText(msg);
        this.bar.setRight1OnClickListener(click);
    }

    public void backAction() {
        finish();
    }

    public void selecttImg(String path) {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.REQUEST_CAMERA_CODE && resultCode == -1 && data != null) {
            String path = data.getStringExtra("result");
            showlog(path);
            selecttImg(path);
        } else if (requestCode == this.REQUEST_CODE && resultCode == -1 && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            for (String path2 : pathList) {
                showlog(path2);
                selecttImg(path2);
            }
        }
    }

    public void showSuccessBack(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this._context);
        builder.setTitle("提示");
        builder.setMessage(msg);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    public void showLoadDialog(String text) {
        if (this.dialog == null) {
            this.dialog = new ZLoadingDialog(this, R.style.loading_dialog);
        }
        String hintText = TextUtils.isEmpty(text) ? "加载中...." : text;
        this.dialog.setLoadingBuilder(Z_TYPE.ROTATE_CIRCLE).setLoadingColor(-12303292).setHintText(hintText).setCanceledOnTouchOutside(true).setHintTextSize(14.0f).setHintTextColor(-12303292).setDurationTime(0.5d).show();
    }

    public void showLoad() {
        showLoadDialog("");
    }

    public void showLoadText(String loadText) {
        showLoadDialog(loadText);
    }

    public void dissLoad() {
        ZLoadingDialog zLoadingDialog = this.dialog;
        if (zLoadingDialog != null) {
            zLoadingDialog.dismiss();
            this.dialog = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        dissLoad();
    }

    public void showToast(String msg) {
        Toast.makeText(this._context, msg, Toast.LENGTH_SHORT).show();
    }
}
