package com.niuniu.babyprotect.stomon.huawei;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
public class Utils {
    public static String TAG = "SampleUtils";

    public static String getStringFromHtmlFile(Context context, String filePath) {
        String result = "";
        if (context == null || filePath == null) {
            return "";
        }
        InputStream stream = null;
        BufferedReader reader = null;
        InputStreamReader streamReader = null;
        try {
            try {
                try {
                    stream = context.getAssets().open(filePath);
                    streamReader = new InputStreamReader(stream, "utf-8");
                    reader = new BufferedReader(streamReader);
                    StringBuilder builder = new StringBuilder();
                    boolean readCurrentLine = true;
                    while (true) {
                        String line = reader.readLine();
                        if (line == null) {
                            break;
                        }
                        if (line.contains("<style")) {
                            readCurrentLine = false;
                        } else if (line.contains("</style")) {
                            readCurrentLine = true;
                        }
                        if (readCurrentLine) {
                            builder.append(line);
                            builder.append("\n");
                        }
                    }
                    result = builder.toString();
                    try {
                        reader.close();
                    } catch (IOException ex) {
                        Log.e(TAG, ex.getMessage());
                    }
                    try {
                        streamReader.close();
                    } catch (IOException ex2) {
                        Log.e(TAG, ex2.getMessage());
                    }
                    if (stream != null) {
                        stream.close();
                    }
                } catch (IOException ex3) {
                    Log.e(TAG, ex3.getMessage());
                }
            } catch (Exception ex7) {
                Log.e(TAG, ex7.getMessage());
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException ex8) {
                        Log.e(TAG, ex8.getMessage());
                    }
                }
                if (streamReader != null) {
                    try {
                        streamReader.close();
                    } catch (IOException ex9) {
                        Log.e(TAG, ex9.getMessage());
                    }
                }
                if (stream != null) {
                    stream.close();
                }
            }
            return result;
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    reader.close();
                } catch (IOException ex10) {
                    Log.e(TAG, ex10.getMessage());
                }
            }
            if (0 != 0) {
                try {
                    streamReader.close();
                } catch (IOException ex11) {
                    Log.e(TAG, ex11.getMessage());
                }
            }
            if (0 != 0) {
                try {
                    stream.close();
                } catch (IOException ex12) {
                    Log.e(TAG, ex12.getMessage());
                }
            }
//            throw th;
        }
        return result;
    }
}
