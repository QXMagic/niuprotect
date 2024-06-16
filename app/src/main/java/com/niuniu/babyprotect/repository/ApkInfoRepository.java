package com.niuniu.babyprotect.repository;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.niuniu.babyprotect.BabyApplication;
import com.niuniu.babyprotect.manager.SharedPreManager;
import com.niuniu.babyprotect.model.AppInfo;
import com.niuniu.babyprotect.network.NetTools;
import com.niuniu.babyprotect.network.ResultCallBackListener;
import com.niuniu.babyprotect.network.StudentBaseUrl;
import com.niuniu.babyprotect.tools.ILog;
import com.niuniu.babyprotect.tools.SharedPreUtil;
import com.niuniu.babyprotect.tools.apk.ApkTools;
import com.niuniu.babyprotect.tools.file.FileHelper;
import com.niuniu.babyprotect.tools.image.ImageSave;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
public class ApkInfoRepository extends BaseRepository {
    public static final int HANDLER_IMAGE_DELETE_ZIP = 2;
    public static final int HANDLER_IMAGE_ZIP = 1;
    public static ApkInfoRepository instance;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            int i = msg.what;
            if (i == 1) {
                uploadImage((String) msg.obj);
            } else if (i == 2) {
                deleteZipImage((String) msg.obj);
                SharedPreUtil.setParam(BabyApplication.getInstance(), SharedPreManager.KEY_APP_UPLOAD_APPS, true);
            }
        }
    };

    private ApkInfoRepository() {
    }

    public static ApkInfoRepository getInstance() {
        if (instance == null) {
            synchronized (ApkInfoRepository.class) {
                if (instance == null) {
                    instance = new ApkInfoRepository();
                }
            }
        }
        return instance;
    }

    public void upAppIcon() {
        List<AppInfo> mlist = ApkTools.getAppList(BabyApplication.getInstance());
        uploadAppIcons(mlist);
    }

    public void uploadAppIcons(final List<AppInfo> mlist) {
        ILog.d("ApkTools", mlist.size() + "--");
        if (mlist.size() == 0) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (AppInfo packageInfo : mlist) {
                    Drawable drawable = packageInfo.getIco();
                    if (drawable != null) {
                        Bitmap bitmap = ImageSave.drawableToBitmap(drawable);
                        String filename = packageInfo.getPackageName();
                        ImageSave.saveIconBitmap(BabyApplication.getInstance(), bitmap, filename.replace(".", ""));
                    }
                }
                String fileStr = ImageSave.makeZip();
                ILog.d("ApkTools fileStr", fileStr);
                ImageSave.delFile();
                mHandler.obtainMessage(1, fileStr).sendToTarget();
            }
        }).start();
    }

    public void uploadAppIconsCurrentTask(List<AppInfo> mlist) {
        ILog.d("ApkTools CurrentTask", mlist.size() + "--");
        if (mlist.size() == 0) {
            return;
        }
        for (AppInfo packageInfo : mlist) {
            Drawable drawable = packageInfo.getIco();
            if (drawable != null) {
                Bitmap bitmap = ImageSave.drawableToBitmap(drawable);
                String filename = packageInfo.getPackageName();
                ImageSave.saveIconBitmap(BabyApplication.getInstance(), bitmap, filename.replace(".", ""));
            } else {
                ILog.d("uploadIcon", "drawable is null");
            }
        }
        String fileStr = ImageSave.makeZip();
        ILog.d("uploadIcon", fileStr);
        ImageSave.delFile();
        uploadImage(fileStr);
    }

    public void uploadImage(final String fileStr) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("file", fileStr);
        parameters.put("name", "123123");
        NetTools.getInstance().postImageAsynHttp(BabyApplication.getInstance(), false, StudentBaseUrl.fileInfos_uploadZip, parameters, new ResultCallBackListener() {
            @Override
            public void onResponse(JSONObject msg) {
                ILog.d("uploadImage", msg.toString());
                if (msg != null) {
                    ILog.log(msg);
                    mHandler.obtainMessage(2, fileStr).sendToTarget();
                }
            }
        });
    }

    public void deleteZipImage(String file) {
        FileHelper.deleteFile(file);
    }
}
