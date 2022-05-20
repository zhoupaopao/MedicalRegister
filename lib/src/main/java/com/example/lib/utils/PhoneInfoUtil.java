package com.example.lib.utils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

//用于存放手机的一些重要信息，如唯一码，取IMEI，取到之后可以放入内存中
public class PhoneInfoUtil {
    public static void saveBitmap(Context context) throws IOException {
        // 创建目录
        //获取内部存储状态
//        String state = Environment.getExternalStorageState();
//        //如果状态不是mounted，无法读写
//        if (!state.equals(Environment.MEDIA_MOUNTED)) {
//            return;
//        }
//        String sdCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
//        File appDir = new File(sdCardDir, "CaChee");
//        if (!appDir.exists()) {
//            appDir.mkdir();
//        }
//        String fileName = "deviceId" + ".txt";//这里是创建一个TXT文件保存我们的UUID
//        File file = new File(appDir, fileName);
//        if (!file.exists()) {
//            file.createNewFile();
//        }
        File file = null;
//    makeRootDirectory("data/data/com.example.filetest");
        try {
            file = new File(getFilesPath(context) + "/deviceFile.txt");
//            if (!file.exists()) {
//                file.mkdir();
//            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //保存android唯一表示符
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(getUUID());
            fw.flush();
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static void writeKey(Context context,String key) throws IOException {
        // 创建目录
        //获取内部存储状态
//        String state = Environment.getExternalStorageState();
//        //如果状态不是mounted，无法读写
//        if (!state.equals(Environment.MEDIA_MOUNTED)) {
//            return;
//        }
//        String sdCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
//        File appDir = new File(sdCardDir, "CaChee");
//        if (!appDir.exists()) {
//            appDir.mkdir();
//        }
//        String fileName = "deviceId" + ".txt";//这里是创建一个TXT文件保存我们的UUID
//        File file = new File(appDir, fileName);
//        if (!file.exists()) {
//            file.createNewFile();
//        }
        File file = null;
//    makeRootDirectory("data/data/com.example.filetest");
        try {
            file = new File(getFilesPath(context) + "/deviceFile.txt");
//            if (!file.exists()) {
//                file.mkdir();
//            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //保存android唯一表示符
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(key);
            fw.flush();
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readKey(Context context) throws IOException {
        // 创建目录
        //获取内部存储状态
//        String state = Environment.getExternalStorageState();
//        //如果状态不是mounted，无法读写
//        if (!state.equals(Environment.MEDIA_MOUNTED)) {
//            return null;
//        }
//        String sdCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
//        File appDir = new File(sdCardDir, "CaChee");
//        if (!appDir.exists()) {
//            appDir.mkdir();
//        }
//        String fileName = "deviceId" + ".txt";//这里是进行读取我们保存文件的名称
//        File file = new File(appDir, fileName);
//        if (!file.exists()) {
//            file.createNewFile();
//        }
        File file = null;
//    makeRootDirectory("data/data/com.example.filetest");
        try {
            file = new File(getFilesPath(context) + "/deviceFile.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        BufferedReader reader = null;
        StringBuilder content = null;
        try {
            FileReader fr = new FileReader(file);
            content = new StringBuilder();
            reader = new BufferedReader(fr);
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    public static String getFilesPath(Context context) {
        String filePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            //外部存储可用
            filePath = Environment.getExternalStorageDirectory().getPath();

//            filePath = context.getExternalFilesDir(null).getPath();//这里获取的是应用内的存储路径
        } else {
            //外部存储不可用
            filePath = context.getFilesDir().getPath();
        }
        return filePath;
    }

    //android重启
    public static void restartSystem() {

//        ShellUtils.execCommand("reboot",false);

        new Thread() {

            @Override


            public void run() {

                super.run();

                Process localProcess = null;

                try {

                    localProcess = Runtime.getRuntime().exec("su");

                    DataOutputStream localDataOutputStream = new DataOutputStream(

                            localProcess.getOutputStream());

// localDataOutputStream.writeBytes("chmod 755 "+ "reboot" + "\n");

                    localDataOutputStream.writeBytes("reboot\n");

// localDataOutputStream.writeBytes("poweroff");

                    localDataOutputStream.flush();

                    localDataOutputStream.close();

//                    localProcess.waitFor();
                    sleep(1000);//停一下

                } catch (Exception e) {

                    e.printStackTrace();

                } finally {

                    if (localProcess != null) {

                        localProcess.destroy();
                    }

                }

            }


        }.start();

    }
}
