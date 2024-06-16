package com.niuniu.babyprotect;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.niuniu.babyprotect.databinding.ActivityMainBindingImpl;
import com.niuniu.babyprotect.databinding.ActivityPermCollectBindingImpl;
import com.niuniu.babyprotect.databinding.RcPermCollectionBindingImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import im.niu.protect.R;

public class DataBinderMapperImpl extends DataBinderMapper {
    private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP;
    private static final int LAYOUT_ACTIVITYMAIN = 1;
    private static final int LAYOUT_ACTIVITYPERMCOLLECT = 2;
    private static final int LAYOUT_RCPERMCOLLECTION = 3;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray(3);
        INTERNAL_LAYOUT_ID_LOOKUP = sparseIntArray;
        sparseIntArray.put(R.layout.activity_main, 1);
        INTERNAL_LAYOUT_ID_LOOKUP.put(R.layout.activity_perm_collect, 2);
        INTERNAL_LAYOUT_ID_LOOKUP.put(R.layout.rc_perm_collection, 3);
    }

    @Override
    public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
        int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
        if (localizedLayoutId > 0) {
            Object tag = view.getTag();
            if (tag == null) {
                throw new RuntimeException("view must have a tag");
            }
            if (localizedLayoutId == 1) {
                if ("layout/activity_main_0".equals(tag)) {
                    return new ActivityMainBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_main is invalid. Received: " + tag);
            } else if (localizedLayoutId == 2) {
                if ("layout/activity_perm_collect_0".equals(tag)) {
                    return new ActivityPermCollectBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for activity_perm_collect is invalid. Received: " + tag);
            } else if (localizedLayoutId == 3) {
                if ("layout/rc_perm_collection_0".equals(tag)) {
                    return new RcPermCollectionBindingImpl(component, view);
                }
                throw new IllegalArgumentException("The tag for rc_perm_collection is invalid. Received: " + tag);
            } else {
                return null;
            }
        }
        return null;
    }

    @Override
    public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
        if (views == null || views.length == 0) {
            return null;
        }
        int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
        if (localizedLayoutId > 0) {
            Object tag = views[0].getTag();
            if (tag == null) {
                throw new RuntimeException("view must have a tag");
            }
        }
        return null;
    }

    @Override
    public int getLayoutId(String tag) {
        Integer tmpVal;
        if (tag == null || (tmpVal = InnerLayoutIdLookup.sKeys.get(tag)) == null) {
            return 0;
        }
        return tmpVal.intValue();
    }

    @Override
    public String convertBrIdToString(int localId) {
        String tmpVal = InnerBrLookup.sKeys.get(localId);
        return tmpVal;
    }

    @Override
    public List<DataBinderMapper> collectDependencies() {
        ArrayList<DataBinderMapper> result = new ArrayList<>(1);
        result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
        return result;
    }

    private static class InnerBrLookup {
        static final SparseArray<String> sKeys;

        private InnerBrLookup() {
        }

        static {
            SparseArray<String> sparseArray = new SparseArray<>(1);
            sKeys = sparseArray;
            sparseArray.put(0, "_all");
        }
    }

    private static class InnerLayoutIdLookup {
        static final HashMap<String, Integer> sKeys;

        private InnerLayoutIdLookup() {
        }

        static {
            HashMap<String, Integer> hashMap = new HashMap<>(3);
            sKeys = hashMap;
            hashMap.put("layout/activity_main_0", Integer.valueOf((int) R.layout.activity_main));
            sKeys.put("layout/activity_perm_collect_0", Integer.valueOf((int) R.layout.activity_perm_collect));
            sKeys.put("layout/rc_perm_collection_0", Integer.valueOf((int) R.layout.rc_perm_collection));
        }
    }
}
