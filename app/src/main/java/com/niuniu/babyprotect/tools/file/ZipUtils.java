package com.niuniu.babyprotect.tools.file;

import android.util.Log;
import com.niuniu.babyprotect.tools.ILog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
public class ZipUtils {
    public static final String TAG = "ZIP";

    public static void UnZipFolder(String zipFileString, String outPathString) throws Exception {
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFileString));
        while (true) {
            ZipEntry zipEntry = inZip.getNextEntry();
            if (zipEntry != null) {
                String szName = zipEntry.getName();
                if (zipEntry.isDirectory()) {
                    String szName2 = szName.substring(0, szName.length() - 1);
                    File folder = new File(outPathString + File.separator + szName2);
                    folder.mkdirs();
                } else {
                    Log.e(TAG, outPathString + File.separator + szName);
                    File file = new File(outPathString + File.separator + szName);
                    if (!file.exists()) {
                        Log.e(TAG, "Create the file:" + outPathString + File.separator + szName);
                        file.getParentFile().mkdirs();
                        file.createNewFile();
                    }
                    FileOutputStream out = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    while (true) {
                        int len = inZip.read(buffer);
                        if (len == -1) {
                            break;
                        }
                        out.write(buffer, 0, len);
                        out.flush();
                    }
                    out.close();
                }
            } else {
                inZip.close();
                return;
            }
        }
    }

    public static void UnZipFolder(String zipFileString, String outPathString, String szName) throws Exception {
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFileString));
        while (true) {
            ZipEntry zipEntry = inZip.getNextEntry();
            if (zipEntry != null) {
                if (zipEntry.isDirectory()) {
                    szName = szName.substring(0, szName.length() - 1);
                    File folder = new File(outPathString + File.separator + szName);
                    folder.mkdirs();
                } else {
                    Log.e(TAG, outPathString + File.separator + szName);
                    File file = new File(outPathString + File.separator + szName);
                    if (!file.exists()) {
                        Log.e(TAG, "Create the file:" + outPathString + File.separator + szName);
                        file.getParentFile().mkdirs();
                        file.createNewFile();
                    }
                    FileOutputStream out = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    while (true) {
                        int len = inZip.read(buffer);
                        if (len == -1) {
                            break;
                        }
                        out.write(buffer, 0, len);
                        out.flush();
                    }
                    out.close();
                }
            } else {
                inZip.close();
                return;
            }
        }
    }

    public static void ZipFolder(String srcFileString, String zipFileString) throws Exception {
        ZipOutputStream outZip = new ZipOutputStream(new FileOutputStream(zipFileString));
        File file = new File(srcFileString);
        Log.i("aaa", "---->" + file.getParent() + "===" + file.getAbsolutePath());
        StringBuilder sb = new StringBuilder();
        sb.append(file.getParent());
        sb.append(File.separator);
        ZipFiles(sb.toString(), file.getName(), outZip);
        outZip.finish();
        outZip.close();
    }

    private static void ZipFiles(String folderString, String fileString, ZipOutputStream zipOutputSteam) throws Exception {
        try {
            if (zipOutputSteam == null) {
                ILog.d("zipOutputSteam", "zipOutputSteam==null");
                return;
            }
            File file = new File(folderString + fileString);
            if (file.isFile()) {
                ZipEntry zipEntry = new ZipEntry(fileString);
                ILog.d("fileName", "filename:" + file.getName());
                if (file.getName().equals("pic.zip")) {
                    return;
                }
                FileInputStream inputStream = new FileInputStream(file);
                zipOutputSteam.putNextEntry(zipEntry);
                byte[] buffer = new byte[4096];
                while (true) {
                    int len = inputStream.read(buffer);
                    if (len != -1) {
                        zipOutputSteam.write(buffer, 0, len);
                    } else {
                        zipOutputSteam.closeEntry();
                        return;
                    }
                }
            } else {
                String[] fileList = file.list();
                if (fileList.length <= 0) {
                    ZipEntry zipEntry2 = new ZipEntry(fileString + File.separator);
                    zipOutputSteam.putNextEntry(zipEntry2);
                    zipOutputSteam.closeEntry();
                }
                for (String str : fileList) {
                    ZipFiles(folderString + fileString + "/", str, zipOutputSteam);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static InputStream UpZip(String zipFileString, String fileString) throws Exception {
        ZipFile zipFile = new ZipFile(zipFileString);
        ZipEntry zipEntry = zipFile.getEntry(fileString);
        return zipFile.getInputStream(zipEntry);
    }

    public static List<File> GetFileList(String zipFileString, boolean bContainFolder, boolean bContainFile) throws Exception {
        List<File> fileList = new ArrayList<>();
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFileString));
        while (true) {
            ZipEntry zipEntry = inZip.getNextEntry();
            if (zipEntry != null) {
                String szName = zipEntry.getName();
                if (zipEntry.isDirectory()) {
                    File folder = new File(szName.substring(0, szName.length() - 1));
                    if (bContainFolder) {
                        fileList.add(folder);
                    }
                } else {
                    File file = new File(szName);
                    if (bContainFile) {
                        fileList.add(file);
                    }
                }
            } else {
                inZip.close();
                return fileList;
            }
        }
    }
}
