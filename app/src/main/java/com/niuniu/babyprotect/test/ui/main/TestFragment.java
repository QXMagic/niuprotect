package com.niuniu.babyprotect.test.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import im.niu.protect.R;
public class TestFragment extends Fragment {
    private TestViewModel mViewModel;

    public static TestFragment newInstance() {
        return new TestFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mViewModel = (TestViewModel) new ViewModelProvider(this).get(TestViewModel.class);
    }
}
