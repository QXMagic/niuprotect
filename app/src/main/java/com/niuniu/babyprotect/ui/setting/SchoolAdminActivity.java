package com.niuniu.babyprotect.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import im.niu.protect.R;
import com.niuniu.babyprotect.tools.Tools;
import com.niuniu.babyprotect.ui.base.BaseActivity;
import com.niuniu.babyprotect.ui.webview.OneWebActivity;
public class SchoolAdminActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_admin);
        changeTitle("教务管理");
        showBack();
        LinearLayout jqbtn = (LinearLayout) findViewById(R.id.jqbtn);
        jqbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = Tools.getToken(_context);
                if (token == null) {
                    return;
                }
                String url = "http://139.9.121.96:8080/h5/#/?token=" + token;
                Intent intent = new Intent(SchoolAdminActivity.this, OneWebActivity.class);
                intent.putExtra("jumpUrl", url);
                intent.putExtra("title", "请假管理");
                startActivity(intent);
            }
        });
    }
}
