package com.niu.protect.repository;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.niu.protect.core.Constants;
import com.niu.protect.manager.SharedPreManager;
import com.niu.protect.model.AppInfo;
import com.niu.protect.network.NetTools;
import com.niu.protect.network.ResultCallBackListener;
import com.niu.protect.network.StudentBaseUrl;
import com.niu.protect.tools.ILog;
import com.niu.protect.tools.SharedPreUtil;
import com.niu.protect.tools.apk.ApkTools;
import com.niu.protect.tools.file.FileHelper;
import com.niu.protect.tools.image.ImageSave;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
                SharedPreUtil.setParam(Constants.MainInstance.getContext(), SharedPreManager.KEY_APP_UPLOAD_APPS, true);
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
        List<AppInfo> mlist = ApkTools.getAppList(Constants.MainInstance.getContext());
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
                    Drawable drawable = packageInfo.ico;
                    if (drawable != null) {
                        Bitmap bitmap = ImageSave.drawableToBitmap(drawable);
                        String filename = packageInfo.packageName;
                        ImageSave.saveIconBitmap(Constants.MainInstance.getContext(),  bitmap, filename.replace(".", ""));
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
            Drawable drawable = packageInfo.ico;
            if (drawable != null) {
                Bitmap bitmap = ImageSave.drawableToBitmap(drawable);
                String filename = packageInfo.packageName;
                ImageSave.saveIconBitmap(Constants.MainInstance.getContext(),  bitmap, filename.replace(".", ""));
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
        NetTools.getInstance().postImageAsynHttp(Constants.MainInstance.getContext(),  false, StudentBaseUrl.fileInfos_uploadZip, parameters, new ResultCallBackListener() {
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
