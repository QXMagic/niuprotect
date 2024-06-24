package com.niu.protect.tools.file;

import android.graphics.Bitmap;
import android.os.Environment;
import com.niu.protect.tools.ILog;
import com.niu.protect.tools.Tools;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
public class FileHelper {
    private String SDPATH = Environment.getExternalStorageDirectory().getPath();
    private boolean hasSD;

    public FileHelper() {
        this.hasSD = false;
        this.hasSD = Environment.getExternalStorageState().equals("mounted");
    }

    public File createSDFile(String fileName) throws IOException {
        File file = new File(this.SDPATH + "/" + fileName);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public String saveImages(String fileName, Bitmap bitmap) {
        File file = new File(this.SDPATH + "/wanplus");
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(this.SDPATH + "/wanplus/" + fileName);
        if (file2.exists()) {
            file2.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file2);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 95, out);
            out.flush();
            out.close();
            ILog.log("已经保存");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return file2.getAbsolutePath();
    }

    public void delf() {
        File file = new File(this.SDPATH + "/" + Tools.filepath);
        deleteFile(file);
    }

    public void deleteFile(File file) {
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                deleteFile(f);
            }
            file.delete();
        }
    }

    public boolean deleteSDFile(String fileName) {
        File file = new File(this.SDPATH + "/" + fileName);
        if (!file.exists()) {
            return false;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            for (File file2 : childFiles) {
                file2.delete();
            }
        }
        return file.delete();
    }

    public void writeSDFile(String str, String fileName) {
        try {
            FileWriter fw = new FileWriter(this.SDPATH + "/" + fileName);
            File f = new File(this.SDPATH + "/" + fileName);
            fw.write(str);
            FileOutputStream os = new FileOutputStream(f);
            new DataOutputStream(os);
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    public String readSDFile(String fileName) {
        StringBuffer sb = new StringBuffer();
        File file = new File(this.SDPATH + "//" + fileName);
        try {
            FileInputStream fis = new FileInputStream(file);
            while (true) {
                int c = fis.read();
                if (c == -1) {
                    break;
                }
                sb.append((char) c);
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return sb.toString();
    }

    public String getSDPATH() {
        return this.SDPATH;
    }

    public boolean hasSD() {
        return this.hasSD;
    }

    public String writeSDImage(String fileName, Bitmap mBitmap) {
        File f = new File(this.SDPATH + "//" + fileName);
        try {
            f.createNewFile();
        } catch (IOException e) {
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 80, fOut);
        try {
            fOut.flush();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e4) {
            e4.printStackTrace();
        }
        return this.SDPATH + "//" + fileName;
    }

    public String writeSDImageJPG(String fileName, Bitmap mBitmap) {
        File f = new File(this.SDPATH + "//" + fileName);
        try {
            f.createNewFile();
        } catch (IOException e) {
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fOut);
        try {
            fOut.flush();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e4) {
            e4.printStackTrace();
        }
        return this.SDPATH + "//" + fileName;
    }

    public String copyFile(String oldPath, String newPath) {
        String newPath2 = this.SDPATH + "//" + Tools.filepath + "/image/" + newPath;
        int bytesum = 0;
        try {
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath2);
                byte[] buffer = new byte[1444];
                while (true) {
                    int byteread = inStream.read(buffer);
                    if (byteread == -1) {
                        break;
                    }
                    bytesum += byteread;
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
        return newPath2;
    }

    public String copyFolder(String oldPath, String newPath) {
        File temp;
        String newPath2 = this.SDPATH + "//" + newPath;
        try {
            new File(newPath2).mkdirs();
            File a = new File(oldPath);
            String[] file = a.list();
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }
                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath2 + "/" + temp.getName().toString());
                    byte[] b = new byte[5120];
                    while (true) {
                        int len = input.read(b);
                        if (len == -1) {
                            break;
                        }
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {
                    copyFolder(oldPath + "/" + file[i], newPath2 + "/"+ file[i]);
                }
            }
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();
        }
        return newPath2;
    }

    public static void deleteFile(final String fileName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(fileName);
                if (file.exists()) {
                    file.delete();
                }
            }
        }).start();
    }
}
