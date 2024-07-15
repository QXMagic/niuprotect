package com.niu.protect.manager.filters;

import android.content.Context;

public interface IFilter {
    boolean systemWhiteApp(Context context, String packName);
}
