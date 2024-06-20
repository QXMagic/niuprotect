package com.niu.protect.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.niu.protect.R;
import com.niu.protect.ui.fragment.OneFragment;
import com.niu.protect.ui.fragment.ThrFragment;
import com.niu.protect.ui.fragment.TwoFragmen;
public class MainTabActivity extends FragmentActivity implements View.OnClickListener {
    FragmentManager fManager;
    private OneFragment fg1;
    private TwoFragmen fg2;
    private ThrFragment fg3;
    private FrameLayout flayout;
    private RelativeLayout tab1;
    private ImageView tab1_image;
    private TextView tab1_text;
    private RelativeLayout tab2;
    private ImageView tab2_image;
    private TextView tab2_text;
    private RelativeLayout tab3;
    private ImageView tab3_image;
    private TextView tab3_text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.fManager = getSupportFragmentManager();
        initViews();
    }

    public void initViews() {
        this.tab1_image = (ImageView) findViewById(R.id.tab1_image);
        this.tab2_image = (ImageView) findViewById(R.id.tab2_image);
        this.tab3_image = (ImageView) findViewById(R.id.tab3_image);
        this.tab1_text = (TextView) findViewById(R.id.tab1_text);
        this.tab2_text = (TextView) findViewById(R.id.tab2_text);
        this.tab3_text = (TextView) findViewById(R.id.tab3_text);
        this.tab1 = (RelativeLayout) findViewById(R.id.tab1);
        this.tab2 = (RelativeLayout) findViewById(R.id.tab2);
        this.tab3 = (RelativeLayout) findViewById(R.id.tab3);
        this.tab1.setOnClickListener(this);
        this.tab2.setOnClickListener(this);
        this.tab3.setOnClickListener(this);
        setChioceItem(0);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tab1) {
            setChioceItem(0);
        } else if (id == R.id.tab2) {
            setChioceItem(1);
        } else if (id == R.id.tab3) {
            setChioceItem(2);
        }
    }

    public void setChioceItem(int index) {
        FragmentTransaction transaction = this.fManager.beginTransaction();
        clearChioce();
        hideFragments(transaction);
        if (index == 0) {
            this.tab1_image.setImageResource(R.mipmap.tabicon1s);
            this.tab1_text.setTextColor(Color.parseColor("#0e74f2"));
            Fragment fragment = this.fg1;
            if (fragment == null) {
                OneFragment oneFragment = new OneFragment();
                this.fg1 = oneFragment;
                oneFragment.context = this;
                transaction.add(R.id.content, this.fg1);
            } else {
                transaction.show(fragment);
            }
        } else if (index == 1) {
            this.tab2_image.setImageResource(R.mipmap.tabicon2s);
            this.tab2_text.setTextColor(Color.parseColor("#0e74f2"));
            Fragment fragment2 = this.fg2;
            if (fragment2 == null) {
                TwoFragmen twoFragmen = new TwoFragmen();
                this.fg2 = twoFragmen;
                transaction.add(R.id.content, twoFragmen);
            } else {
                transaction.show(fragment2);
            }
        } else if (index == 2) {
            this.tab3_image.setImageResource(R.mipmap.tabicon3s);
            this.tab3_text.setTextColor(Color.parseColor("#0e74f2"));
            Fragment fragment3 = this.fg3;
            if (fragment3 == null) {
                ThrFragment thrFragment = new ThrFragment();
                this.fg3 = thrFragment;
                thrFragment.context = this;
                transaction.add(R.id.content, this.fg3);
            } else {
                transaction.show(fragment3);
            }
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        OneFragment oneFragment = this.fg1;
        if (oneFragment != null) {
            transaction.hide(oneFragment);
        }
        TwoFragmen twoFragmen = this.fg2;
        if (twoFragmen != null) {
            transaction.hide(twoFragmen);
        }
        ThrFragment thrFragment = this.fg3;
        if (thrFragment != null) {
            transaction.hide(thrFragment);
        }
    }

    public void clearChioce() {
        this.tab1_image.setImageResource(R.mipmap.tabicon1);
        this.tab1_text.setTextColor(-3355444);
        this.tab2_image.setImageResource(R.mipmap.tabicon2);
        this.tab2_text.setTextColor(-3355444);
        this.tab3_image.setImageResource(R.mipmap.tabicon3);
        this.tab3_text.setTextColor(-3355444);
    }
}
