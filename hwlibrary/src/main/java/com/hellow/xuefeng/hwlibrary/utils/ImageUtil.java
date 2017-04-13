package com.hellow.xuefeng.hwlibrary.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



public class ImageUtil {

    public static String getCompressedImgPath(String picDir,String sourceImgPath) {
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            Bitmap bmp = BitmapFactory.decodeFile(sourceImgPath, opts);
            opts.inJustDecodeBounds = false;

            int w = opts.outWidth;
            int h = opts.outHeight;
            float standardW = 720f;
            float standardH = 1280f;

            int zoomRatio = 1;
            if (w > h && w > standardW) {
                zoomRatio = (int) (w / standardW);
            } else if (w < h && h > standardH) {
                zoomRatio = (int) (h / standardH);
            }
            if (zoomRatio <= 0)
                zoomRatio = 1;
            opts.inSampleSize = zoomRatio;

            bmp = BitmapFactory.decodeFile(sourceImgPath, opts);

            File compressedImg = new File(picDir + System.currentTimeMillis() + ".jpg");
            FileOutputStream fos = new FileOutputStream(compressedImg);
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            fos.flush();
            fos.close();
            Log.e("size"," "+FileSizeUtil.getFileOrFilesSize(compressedImg.getPath()));

            return compressedImg.getPath();


        } catch (FileNotFoundException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return null;
    }

}
