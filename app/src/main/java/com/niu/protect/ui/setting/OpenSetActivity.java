package com.niu.protect.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.niu.protect.R;
import com.niu.protect.stomon.StoToolManager;
import com.niu.protect.tools.Tools;
import com.niu.protect.ui.base.BaseActivity;
public class OpenSetActivity extends BaseActivity {
    TextView openbtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_set);
        changeTitle("自启动授权");
        Tools.saveAotuSet(this, 0);
        Tools.saveIngAotuSet(this, 1);
        StoToolManager.getInstance(this).makeSetView(0);
        TextView textView = (TextView) findViewById(R.id.openAotuBtn);
        this.openbtn = textView;
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("android.settings.SETTINGS"));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Tools.saveIngAotuSet(this, 0);
        StoToolManager.getInstance(this).makeSetView(1);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        int aotus = Tools.getAotuSet(this);
        if (aotus != 1) {
            Tools.saveAotuSet(this, -1);
            showBack();
            return;
        }
        showBack();
    }
}
