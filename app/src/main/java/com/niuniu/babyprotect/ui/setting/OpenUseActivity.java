package com.niuniu.babyprotect.ui.setting;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import im.niu.protect.R;
import com.niuniu.babyprotect.tools.Tools;
import com.niuniu.babyprotect.ui.base.BaseActivity;
import java.util.List;
public class OpenUseActivity extends BaseActivity {
    TextView openbtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_set);
        changeTitle("使用情况访问授权");
        TextView textView = (TextView) findViewById(R.id.openAotuBtn);
        this.openbtn = textView;
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent("android.settings.USAGE_ACCESS_SETTINGS");
                startActivity(intent2);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Tools.saveIngAotuSet(this, 0);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!isNoOption() || isNoSwitch()) {
            Toast.makeText(this, "打开成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private boolean isNoOption() {
        PackageManager packageManager = getApplicationContext().getPackageManager();
        Intent intent = new Intent("android.settings.USAGE_ACCESS_SETTINGS");
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, 65536);
        return list.size() > 0;
    }

    private boolean isNoSwitch() {
        if (Build.VERSION.SDK_INT >= 21) {
            long ts = System.currentTimeMillis();
            UsageStatsManager usageStatsManager = (UsageStatsManager) getApplicationContext().getSystemService("usagestats");
            List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(4, 0L, ts);
            return (queryUsageStats == null || queryUsageStats.isEmpty()) ? false : true;
        }
        return true;
    }
}
