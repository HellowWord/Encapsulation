package com.hellow.xuefeng.hwlibrary.api;


import com.hellow.xuefeng.hwlibrary.app.BaseApplication;
import com.hellow.xuefeng.hwlibrary.volley.RequestQueue;
import com.hellow.xuefeng.hwlibrary.volley.toolbox.Volley;

/**
 * @FileName: com.hiersun.dmbase.api.APIHelper.java
 * @author: daincly
 * @date: 2014-11-21
 * @description:
 */
@Deprecated
public class APIHelper {
    private final static String TAG = "APIHelper";

    private static APIHelper instance;
    private RequestQueue mRequestQueue;




    private APIHelper(){
        mRequestQueue = new Volley().newRequestQueue(BaseApplication.getContext());
        mRequestQueue.start();
    }


    public void putAPI(BaseAPI api){
        mRequestQueue.add(api.getStringRequest());
    }
}
