package com.niuniu.babyprotect.manager;

import android.os.Environment;
import com.baidubce.BceConfig;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
public class TXTManager {
    public static String rootXMLPath = Environment.getExternalStorageDirectory().getPath() + "/testTXT";
    static String SAVE_PIC_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    static String SAVE_REAL_PATH = SAVE_PIC_PATH + "/babyprotect/";

    public static boolean writeToTxt(String fileName, String content) {
        createDirectory(rootXMLPath);
        File file = new File(rootXMLPath + BceConfig.BOS_DELIMITER + fileName + ".txt");
        try {
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write(content);
            bufferedWriter.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addToTxt(String fileName, String content) {
        createDirectory(rootXMLPath);
        File file = new File(rootXMLPath + BceConfig.BOS_DELIMITER + fileName + ".txt");
        BufferedWriter out = null;
        try {
            try {
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
                out.write(content + "\r\n");
                out.close();
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            } catch (Exception e2) {
                e2.printStackTrace();
                try {
                    out.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                return false;
            }
        } catch (Throwable th) {
            try {
                out.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            throw th;
        }
    }

    public static String readFromTxt(String filePath) {
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File(filePath);
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    stringBuilder.append(line);
                }
                bufferedReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e2) {
                e2.printStackTrace();
                return null;
            }
        }
        return stringBuilder.toString();
    }

    public static void createDirectory(String fileDirectory) {
        File file = new File(fileDirectory);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static String writeTxt(String fileName, String content) {
        File sdCardDir = new File(SAVE_REAL_PATH);
        if (!sdCardDir.exists() && !sdCardDir.mkdirs()) {
            try {
                sdCardDir.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
        try {
            File saveFile = new File(sdCardDir, fileName + ".txt");
            if (!saveFile.exists()) {
                saveFile.createNewFile();
            }
            BufferedWriter bufferedWriter = null;
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
                bufferedWriter.write(content);
                bufferedWriter.close();
                return null;
            } catch (IOException e2) {
                e2.printStackTrace();
                bufferedWriter.close();
                return null;
            }
        } catch (Exception e3) {
            e3.printStackTrace();
            return e3.getMessage();
        }
    }

    public static String writeTxtAdd(String fileName, String content) {
        File sdCardDir = new File(SAVE_REAL_PATH);
        if (!sdCardDir.exists() && !sdCardDir.mkdirs()) {
            try {
                sdCardDir.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
        try {
            File saveFile = new File(sdCardDir, fileName + ".txt");
            if (!saveFile.exists()) {
                saveFile.createNewFile();
            }
            BufferedWriter out = null;
            try {
                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveFile, true)));
                out.write(content + "\r\n");
                out.close();
                try {
                    out.close();
                    return null;
                } catch (IOException e2) {
                    e2.printStackTrace();
                    return e2.getMessage();
                }
            } catch (Exception e3) {
                e3.printStackTrace();
                String message = e3.getMessage();
                try {
                    out.close();
                    return message;
                } catch (IOException e4) {
                    e4.printStackTrace();
                    return e4.getMessage();
                }
            }
        } catch (Exception e5) {
            e5.printStackTrace();
            return e5.getMessage();
        }
    }

    public static String readTxt(String filePath) {
        File file = new File(SAVE_REAL_PATH + filePath + ".txt");
        StringBuilder stringBuilder = new StringBuilder();
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    stringBuilder.append(line);
                }
                bufferedReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e2) {
                e2.printStackTrace();
                return null;
            }
        }
        return stringBuilder.toString();
    }
}
