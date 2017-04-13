package com.hellow.xuefeng.hwlibrary.storage;

import android.os.Environment;

import com.hellow.xuefeng.hwlibrary.app.BaseApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by daincly on 2014/11/2.
 */
public class StorageUtils {

    public static boolean hasExtendSdCard() {
        final String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }


    public static String getRootPath() {
        String root = null;
        root= BaseApplication.getContext().getExternalFilesDir(null).getAbsolutePath()+'/';
//        if (StorageUtils.hasExtendSdCard()) {
//            root = Environment.getExternalStorageDirectory().getAbsolutePath();
//            L.i("getExternalStorageDirectory");
//        } else {
//            L.i("getCacheDir");
//            root = BaseApplication.getContext().getCacheDir().getAbsolutePath();// 系统磁盘
//        }
//        root += '/';

        return root;
    }

    public static File getPathFile(String path) {
        File pathFile = new File(getRootPath() + path);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        return pathFile;
    }

    public static File getFile(String path, String name) throws IOException {
        getPathFile(path);
        File file = new File(getRootPath() + path, name);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public static FileOutputStream getFileOS(String path, String name) throws IOException {
        getFile(path, name);
        FileOutputStream fos = null;
        fos = new FileOutputStream(getRootPath() + path + name);
        return fos;

    }

    public static FileInputStream getFileIN(String path, String name) throws IOException {
        getFile(path, name);
        FileInputStream fin = null;
        fin = new FileInputStream(getRootPath() + path + name);
        return fin;

    }
}
