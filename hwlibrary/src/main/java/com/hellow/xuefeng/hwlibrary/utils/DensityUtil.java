package com.hellow.xuefeng.hwlibrary.utils;

import android.content.Context;

/**
 * @FileName: com.hiersun.dmbase.utils.DensityUtil.java
 * @author: daincly
 * @date: 2015-12-02 16:21
 * @description: <功能>
 */
public class DensityUtil {
    private final static String TAG = "DensityUtil";
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
