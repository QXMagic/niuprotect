package com.niu.protect.tools.image;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.niu.protect.BabyApplication;
import com.niu.protect.tools.ILog;
import com.niu.protect.tools.file.ZipUtils;
import java.io.File;
import java.io.FileOutputStream;
public class ImageSave {
    private static String cacheImageDir;

    public static void saveBitmap(Context context, Bitmap bitmap, String fileName) {
        try {
            String dir = context.getCacheDir().getAbsolutePath() + "/babyprotect/Pic/";
            cacheImageDir = dir;
            File fileDir = new File(dir);
            fileDir.mkdirs();
            File file = new File((cacheImageDir + fileName) + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, out);
            out.flush();
            out.close();
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveIconBitmap(Context context, Bitmap bitmap, String fileName) {
        try {
            String dir = context.getCacheDir().getAbsolutePath() + "/babyprotect/Pic/";
            cacheImageDir = dir;
            File fileDir = new File(dir);
            fileDir.mkdirs();
            File file = new File((cacheImageDir + fileName) + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delFile() {
        String dir = BabyApplication.getInstance().getCacheDir().getAbsolutePath() + "/babyprotect/Pic/";
        File fileDir = new File(dir);
        deleteDirWihtFile(fileDir);
    }

    public static void deleteDirWihtFile(File dir) {
        File[] listFiles;
        if (dir == null || !dir.exists() || !dir.isDirectory() || dir.listFiles() == null) {
            return;
        }
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                deleteDirWihtFile(file);
            }
        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    public static String makeZip() {
        String dir = BabyApplication.getInstance().getCacheDir().getAbsolutePath();
        String zipdir = dir + "/babyprotect/pic.zip";
        File file = new File(zipdir);
        if (file.exists()) {
            boolean deleteSuccess = file.delete();
            ILog.d("zip deleteSuccess", "deleteSuccess:" + deleteSuccess);
        }
        try {
            ZipUtils.ZipFolder(dir, zipdir);
        } catch (Exception e) {
            e.printStackTrace();
            ILog.d("zip", "error:" + e.getMessage());
        }
        return zipdir;
    }
}
