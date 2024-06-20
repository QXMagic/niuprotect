package com.niu.protect.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.niu.protect.R;
import com.niu.protect.ui.NavigationBar;
public class TwoFragmen extends Fragment {
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_two, container, false);
        this.view = inflate;
        NavigationBar bar = (NavigationBar) inflate.findViewById(R.id.navigationbar);
        bar.setTitle("我2");
        return this.view;
    }
}
