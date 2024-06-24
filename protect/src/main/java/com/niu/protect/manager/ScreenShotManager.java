package com.niu.protect.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.hardware.display.VirtualDisplay;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Handler;
public class ScreenShotManager {
    public static Bitmap capture(Activity activity) {
        activity.getWindow().getDecorView().setDrawingCacheEnabled(true);
        Bitmap bmp = activity.getWindow().getDecorView().getDrawingCache();
        return bmp;
    }

    private void parseData(Intent data, Context context) {
        MediaProjectionManager mMediaProjectionManager = (MediaProjectionManager) context.getSystemService("media_projection");
        MediaProjection mMediaProjection = mMediaProjectionManager.getMediaProjection(-1, data);
        final ImageReader mImageReader = ImageReader.newInstance(350, 720, 256, 1);
        VirtualDisplay mVirtualDisplay = mMediaProjection.createVirtualDisplay("screen-mirror", 398, 1000, Resources.getSystem().getDisplayMetrics().densityDpi, 16, mImageReader.getSurface(), null, null);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mImageReader.acquireLatestImage();
            }
        }, 300L);
        mVirtualDisplay.release();
    }
}
