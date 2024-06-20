package com.niu.protect.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import androidx.viewpager.widget.PagerAdapter;
import java.util.ArrayList;
import java.util.List;
public class ViewPagerAdapter extends PagerAdapter {
    private List<GridView> gridList = new ArrayList();

    public void add(List<GridView> datas) {
        if (this.gridList.size() > 0) {
            this.gridList.clear();
        }
        this.gridList.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.gridList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return -2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(this.gridList.get(position));
        return this.gridList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
