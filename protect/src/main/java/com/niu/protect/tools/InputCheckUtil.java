package com.niu.protect.tools;

import android.text.TextUtils;
import android.widget.EditText;
public class InputCheckUtil {
    public static boolean checkPhoneNo(EditText text) {
        return checkInput(text, 11, "请正确输入手机号码", "手机号码长度不够11位");
    }

    public static boolean checkAccount(EditText text) {
        return checkInput(text, 0, "请输入账号", "请输入账号");
    }

    public static boolean checkSMSCode(EditText text) {
        return checkInput(text, 6, "验证码不允许为空", "验证码长度不够6位");
    }

    public static boolean checkPwd(EditText text) {
        return checkInput(text, 6, "密码不允许为空", "密码输入长度不够6位");
    }

    private static boolean checkInput(EditText editText, int limitLength, String inputNullErrorMsg, String inputLimitLengthErrorMsg) {
        if (editText.getText() == null) {
            ToastUtil.show(inputNullErrorMsg);
            return false;
        }
        String text = editText.getText().toString();
        if (TextUtils.isEmpty(text)) {
            ToastUtil.show(inputNullErrorMsg);
            return false;
        } else if (limitLength != 0 && text.length() < limitLength) {
            ToastUtil.show(inputLimitLengthErrorMsg);
            return false;
        } else {
            return true;
        }
    }
}
