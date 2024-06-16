package com.niuniu.babyprotect.test;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import im.niu.protect.R;
import com.niuniu.babyprotect.test.ui.main.TestFragment;
public class TestActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, TestFragment.newInstance()).commitNow();
        }
    }
}
