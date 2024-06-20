package com.niu.protect.ui.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.niu.protect.R;
import com.niu.protect.tools.Tools;
import com.niu.protect.ui.base.BaseActivity;
public class OpenAutoActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_auto);
        changeTitle("开启设置");
        showBack();
        Tools.saveIngAotuSet(this, 1);
        TextView openbtn = (TextView) findViewById(R.id.openBtn);
        openbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Tools.saveIngAotuSet(this, 0);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
