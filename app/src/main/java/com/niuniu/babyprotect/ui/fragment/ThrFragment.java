package com.niuniu.babyprotect.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import im.niu.protect.R;
import com.niuniu.babyprotect.ui.NavigationBar;
public class ThrFragment extends Fragment {
    public Context context;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_thr, container, false);
        this.view = inflate;
        NavigationBar bar = (NavigationBar) inflate.findViewById(R.id.navigationbar);
        bar.setTitle("我3");
        return this.view;
    }

    public void refView() {
    }
}
