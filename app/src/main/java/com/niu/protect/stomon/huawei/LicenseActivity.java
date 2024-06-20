package com.niu.protect.stomon.huawei;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.niu.protect.R;
public class LicenseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.license_layout);
        Button acceptBtn = (Button) findViewById(R.id.cancelBtn);
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
        TextView licenseText = (TextView) findViewById(R.id.license_content);
        String content = Utils.getStringFromHtmlFile(this, "huawei_software_license.html");
        licenseText.setText(Html.fromHtml(content));
    }
}
