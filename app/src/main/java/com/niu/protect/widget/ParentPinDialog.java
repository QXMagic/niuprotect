package com.niu.protect.widget;

import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.niu.protect.homework.HomeworkLockManager;

/**
 * 家长 PIN 校验对话框。
 * 用于保护"退出登录 / 解绑设备"等孩子不应触碰的操作——复用作业管控的家长 PIN。
 */
public final class ParentPinDialog {

    public interface OnVerified {
        void onVerified();
    }

    private ParentPinDialog() {
    }

    /**
     * 弹出 PIN 输入框，校验通过后回调 onVerified。
     *
     * @param title 弹窗标题，说明这是什么操作（如"退出登录需家长验证"）
     */
    public static void verify(Context context, String title, OnVerified callback) {
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        input.setHint("请输入家长密码");

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(input)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", (dialog, which) -> {
                    String pin = input.getText().toString();
                    if (TextUtils.isEmpty(pin)) {
                        Toast.makeText(context, "请输入家长密码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (HomeworkLockManager.INSTANCE.checkPin(context, pin)) {
                        callback.onVerified();
                    } else {
                        Toast.makeText(context, "家长密码错误", Toast.LENGTH_SHORT).show();
                    }
                })
                .setCancelable(false)
                .show();
    }
}
