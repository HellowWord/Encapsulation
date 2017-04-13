package com.hellow.xuefeng.hwlibrary.upload;

import java.io.File;
import java.util.Map;

/**
 * @FileName: com.hiersun.dmbase.upload.UploadRequest.java
 * @author: daincly
 * @date: 2015-12-17 21:17
 * @description: <功能>
 */
public abstract class UploadRequest {
    private final static String TAG = "UploadRequest";
    protected final static int RESPONSE_CODE_APP_EXCEPTION = -1;
    protected boolean isCancel;
    protected UploadRequest() {
    }

    public void cancel(){
        isCancel = true;
    }

    protected void onProgress(int uploadSize,int totalSize){

    }
    protected abstract Map<String,String> getHeader();
    protected abstract File getFile();
    protected abstract void onBaseErrorResponse(int errorCode,String msg);
    protected abstract void onBaseResponse(String response);
}
