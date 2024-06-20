package com.niu.protect.stomon.huawei;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.niu.protect.R;
public class SampleEula {
    private static final int REQUEST_ENABLE = 1;
    private Activity mActivity;
    private ComponentName mAdminName;
    private DevicePolicyManager mDevicePolicyManager;
    boolean notShowAgain = false;

    public SampleEula(Activity context, DevicePolicyManager devicePolicyManager, ComponentName adminName) {
        this.mActivity = null;
        this.mDevicePolicyManager = null;
        this.mAdminName = null;
        this.mActivity = context;
        this.mDevicePolicyManager = devicePolicyManager;
        this.mAdminName = adminName;
    }

    public void show() {
        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(this.mActivity);
        boolean hasUserAccepted = sharedPreferenceUtil.hasUserAccepted();
        this.notShowAgain = hasUserAccepted;
        if (!hasUserAccepted) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mActivity).setPositiveButton(this.mActivity.getString(R.string.accept_btn), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SharedPreferenceUtil sharedPreferenceUtil2 = new SharedPreferenceUtil(mActivity);
                    sharedPreferenceUtil2.saveUserChoice(notShowAgain);
                    dialogInterface.dismiss();
                    activeProcess();
                }
            }).setNegativeButton(this.mActivity.getString(R.string.exit_btn), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mActivity.finish();
                }
            });
            AlertDialog eulaDialog = builder.create();
            eulaDialog.setCancelable(false);
            LayoutInflater inflater = LayoutInflater.from(this.mActivity);
            View layout = inflater.inflate(R.layout.eula_layout, (ViewGroup) null);
            TextView permissionText = (TextView) layout.findViewById(R.id.content_permissions);
            String content = Utils.getStringFromHtmlFile(this.mActivity, "huawei_permission_statement.html");
            permissionText.setText(Html.fromHtml(content));
            TextView statementText = (TextView) layout.findViewById(R.id.read_statement);
            statementText.setMovementMethod(LinkMovementMethod.getInstance());
            CharSequence text = statementText.getText();
            if (text instanceof Spannable) {
                int end = text.length();
                Spannable sp = (Spannable) text;
                URLSpan[] urls = (URLSpan[]) sp.getSpans(0, end, URLSpan.class);
                SpannableStringBuilder style = new SpannableStringBuilder(text);
                style.clearSpans();
                int length = urls.length;
                int i = 0;
                while (i < length) {
                    AlertDialog.Builder builder2 = builder;
                    URLSpan url = urls[i];
                    LayoutInflater inflater2 = inflater;
                    MyURLSpan myURLSpan = new MyURLSpan();
                    style.setSpan(myURLSpan, sp.getSpanStart(url), sp.getSpanEnd(url), 34);
                    i++;
                    builder = builder2;
                    inflater = inflater2;
                    length = length;
                    permissionText = permissionText;
                }
                statementText.setText(style);
            }
            CheckBox checkbox = (CheckBox) layout.findViewById(R.id.not_show_check);
            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        notShowAgain = true;
                    } else {
                        notShowAgain = false;
                    }
                }
            });
            eulaDialog.setView(layout);
            eulaDialog.show();
            return;
        }
        activeProcess();
    }

    private class MyURLSpan extends ClickableSpan {
        private MyURLSpan() {
        }

        @Override
        public void onClick(View widget) {
            widget.setBackgroundColor(Color.parseColor("#00000000"));
            Intent intent = new Intent(mActivity, LicenseActivity.class);
            mActivity.startActivity(intent);
        }
    }

    protected void activeProcess() {
        DevicePolicyManager devicePolicyManager = this.mDevicePolicyManager;
        if (devicePolicyManager != null && !devicePolicyManager.isAdminActive(this.mAdminName)) {
            Intent intent = new Intent("android.app.action.ADD_DEVICE_ADMIN");
            intent.putExtra("android.app.extra.DEVICE_ADMIN", this.mAdminName);
            this.mActivity.startActivityForResult(intent, 1);
            Log.d("SAMPLE", "activeProcess");
        }
    }
}
