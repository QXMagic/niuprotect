package com.niu.protect.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class DownloadUtil {
    private static DownloadUtil downloadUtil;
    private final OkHttpClient okHttpClient = new OkHttpClient();

    public interface OnDownloadListener {
        void onDownloadFailed(Exception exc);

        void onDownloadSuccess(File file);

        void onDownloading(int i);
    }

    public static DownloadUtil get() {
        if (downloadUtil == null) {
            downloadUtil = new DownloadUtil();
        }
        return downloadUtil;
    }

    public void download(String url, final String destFileDir, final String destFileName, final OnDownloadListener listener) {
        Request request = new Request.Builder().url(url).build();
        OkHttpClient client = new OkHttpClient();
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e2) {
                listener.onDownloadFailed(e2);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                FileOutputStream fos = null;
                File dir = new File(destFileDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(dir, destFileName);
                try {
                    try {
                        try {
                            is = response.body().byteStream();
                            long total = response.body().contentLength();
                            fos = new FileOutputStream(file);
                            long sum = 0;
                            while (true) {
                                int len = is.read(buf);
                                if (len == -1) {
                                    break;
                                }
                                fos.write(buf, 0, len);
                                sum += len;
                                int progress = (int) (((((float) sum) * 1.0f) / ((float) total)) * 100.0f);
                                listener.onDownloading(progress);
                            }
                            fos.flush();
                            listener.onDownloadSuccess(file);
                            if (is != null) {
                                is.close();
                            }
                            fos.close();
                        } catch (Exception e2) {
                            listener.onDownloadFailed(e2);
                            if (is != null) {
                                is.close();
                            }
                            if (fos != null) {
                                fos.close();
                            }
                        }
                    } catch (IOException e3) {
                    }
                } catch (Throwable th) {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e4) {
                            throw th;
                        }
                    }
                    if (fos != null) {
                        fos.close();
                    }
                    throw th;
                }
            }
        });
    }
}
