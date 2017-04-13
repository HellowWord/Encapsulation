package com.hellow.xuefeng.hwlibrary.api;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hellow.xuefeng.hwlibrary.debug.L;
import com.hellow.xuefeng.hwlibrary.volley.NetworkError;
import com.hellow.xuefeng.hwlibrary.volley.Response;
import com.hellow.xuefeng.hwlibrary.volley.ServerError;
import com.hellow.xuefeng.hwlibrary.volley.TimeoutError;
import com.hellow.xuefeng.hwlibrary.volley.VolleyError;
import com.hellow.xuefeng.hwlibrary.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * @FileName: com.hiersun.dmbase.api.BaseAPI.java
 * @author: daincly
 * @date: 2014-11-21 
 * @description:
 */
@Deprecated
public abstract class BaseAPI<T extends BaseResponseData> implements Response.ErrorListener,Response.Listener<String>{
    private static final String TAG = "BaseAPI";
    public final static int ERROR_CODE_TIMEOUT = 1;
    public final static int ERROR_CODE_SERVER = 2;
    public final static int ERROR_NETWORK = 3;
    public final static int ERROR_PARSE = 4;
    public final static int METHOD_GET = 0;
    public final static int METHOD_POST = 1;
    private StringRequest mStringRequest;


    protected BaseAPI() {
        mStringRequest = new StringRequest(getMethod(), getAPIUrl(), getHeader(), getParams(), this, this);
    }
    public StringRequest getStringRequest(){
        return mStringRequest;
    }
    /**
     * 设置请求方式 GET or POST
     *
     * @return
     */
    protected abstract int getMethod();

    /**
     * 设置请求地址
     *
     * @return
     */
    protected abstract String getAPIUrl();

    /**
     * 设置请求header
     *
     * @return
     */

    protected abstract Map<String, String> getHeader();


    /**
     * 设置请求参数
     *
     * @return
     */
    protected abstract Map<String, String> getParams();


    protected abstract Class<T> getResponseDataClazz();
    protected abstract void onResponse(T t);

    protected abstract void onErrorResponse(int errorCode,String msg);

    @Override
    public final void onErrorResponse(VolleyError volleyError){
        L.i("BaseAPI error response");
        int errorCode = 0;

        if (volleyError instanceof TimeoutError) {
            errorCode = ERROR_CODE_TIMEOUT;
        } else if (volleyError instanceof ServerError) {
            errorCode = ERROR_CODE_SERVER;
        } else if (volleyError instanceof NetworkError) {
            errorCode = ERROR_NETWORK;
        }
        onErrorResponse(errorCode,"");
    }

    @Override
    public final void onResponse(String responseData) {
        try {

            T t  = new Gson().fromJson(responseData, getResponseDataClazz());
            onResponse(t);
        } catch (JsonSyntaxException e) {
            onErrorResponse(ERROR_PARSE, "");
        }
        onResponse(responseData);
    }





}